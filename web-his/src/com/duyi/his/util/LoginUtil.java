package com.duyi.his.util;

import com.duyi.his.dao.AuthDao;
import com.duyi.his.dao.FunDao;
import com.duyi.his.dao.UserDao;
import com.duyi.his.dao.impl.AuthDaoImpl;
import com.duyi.his.dao.impl.FunDaoImpl;
import com.duyi.his.dao.impl.UserDaoImpl;
import com.duyi.his.domain.Fun;
import com.duyi.his.domain.User;
import com.duyi.his.vo.AuthMenuVO;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LoginUtil {

    private static UserDao dao = new UserDaoImpl();
    private static FunDao funDao = new FunDaoImpl();

    public static boolean isAutoLogin(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        if(cookies == null){
            return false ;
        }
        for(Cookie cookie : cookies){
            if("tokenId".equals( cookie.getName() ) ){
                //找到了cookie信息
                String tokenId = cookie.getValue();
                long time = Long.parseLong( tokenId.split("-")[1] ) ;

                //先找对应的登录信息
                Long uid = (Long) request.getServletContext().getAttribute(tokenId);
                if(uid == null){
                    //没有根据tokenId找到登录信息
                    //1. 伪造的token
                    //2. 服务器重启了，缓存信息小时
                    //统一提示  自动登录过期
                    response.setHeader("auto-login-info","auto login fail，cache invalid");
                    return false ;
                }
                //找到了登录信息，但还要考虑一下时间问题
                long curr = System.currentTimeMillis() ;
                long day = (curr - time)/1000/60/60/24 ;
                if(day > 7){
                    //过期，不能自动登录
                    response.setHeader("auto-login-info","auto login fail，tokenId expire");
                    return false ;
                }
                //既找到了登录信息，由可以使用，自动登录
                //要将登录信息存入session
                User user = dao.findById(uid);
                if(user == null){
                    response.setHeader("auto-login-info","auto login fail，user not exist");
                    return false ;
                }
                addLoginUser(user,request.getSession());
                return true ;
            }
        }

        //没有自动登录的cookie信息，不能自动登录
        response.setHeader("auto-login-info","auto login fail，tokenId invalid");
        return false ;
    }

    public static void addLoginUser(User user, HttpSession session){
        session.setAttribute("loginUser",user);


        //额外增加当前用户的权限信息（装入session）
        //所谓用户的权限信息，就是根据用户找到其分配的角色，从而找到其具有功能
        //先找用户具有的所有权限功能
        List<Fun> authFuns = funDao.findAllByUser(user.getUid());

        List<AuthMenuVO> authMenuVOS = handleAuthMenu(authFuns);
        session.setAttribute("authMenus",authMenuVOS);

        //接下来要找到所有的权限标识，进行HashSet存储，未来单一数据查找时速度快
        Set<String> authFlags = loadAuthFlag(authFuns);
        session.setAttribute("authFlags",authFlags);
    }

    private static Set<String> loadAuthFlag(List<Fun> authFuns){
        Set<String> auths = new HashSet<>();
        for(Fun fun : authFuns){
            auths.add(fun.getAuth_flag());
        }
        return auths ;
    }

    /**
     * 在所有的权限功能中，获得其中的功能菜单，并按照子父关系存储
     */
    private static List<AuthMenuVO> handleAuthMenu(List<Fun> authFuns){
        return handlerAuthMenuPreLevel(-1L,authFuns);
    }

    private static List<AuthMenuVO> handlerAuthMenuPreLevel(Long pid,List<Fun> authFuns){
        List<AuthMenuVO> authMenuVOS = new ArrayList<>();
        for(Fun fun : authFuns){
            if(fun.getFtype().equals(1) && fun.getPid().equals(pid)){
                //找到了这一层的一个菜单，组成AuthMenuVO
                AuthMenuVO authMenuVO = new AuthMenuVO();
                authMenuVO.setFname(fun.getFname());
                authMenuVO.setFurl(fun.getFurl());
                authMenuVO.setIcon(fun.getYl1());
                //需要找到当前这个fun菜单的子级菜单
                List<AuthMenuVO> children = handlerAuthMenuPreLevel(fun.getFid(), authFuns);
                authMenuVO.setChildren(children);
                authMenuVOS.add(authMenuVO);
            }
        }
        return authMenuVOS;
    }
}
