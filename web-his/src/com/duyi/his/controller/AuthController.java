package com.duyi.his.controller;

import com.duyi.his.domain.User;
import com.duyi.his.service.AuthService;
import com.duyi.his.service.impl.AuthServiceImpl;
import com.duyi.his.util.ServiceProxyFactory;
import com.duyi.his.vo.AuthMenuVO;
import com.duyi.his.vo.DistributionFunVO;
import com.duyi.his.vo.DistributionRoleVO;
import com.duyi.his.vo.ResponseVO;
import org.duyi.mvc.annotations.RequestMapping;
import org.duyi.mvc.annotations.RequestParam;
import org.duyi.mvc.annotations.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

public class AuthController {

    //private AuthService service = new AuthServiceImpl();
    private AuthService service = ServiceProxyFactory.getProxy(AuthServiceImpl.class);

    @RequestMapping("/auth/distributionInfoForRole")
    @ResponseBody
    public DistributionRoleVO distributionInfoForRole(@RequestParam("uid") Long uid){
        return service.findDistributionInfoForRole(uid);
    }

    @RequestMapping("/auth/distributionRole")
    @ResponseBody
    public ResponseVO distributionRole(
            @RequestParam("uid") Long uid ,
            @RequestParam("ridStr") String ridStr
            ,HttpSession session){
        User user = (User) session.getAttribute("loginUser");

        try{
            service.distributionRole(uid,ridStr,user.getUid());
            return new ResponseVO(0,"分配成功");
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseVO(1,"未知错误");
        }
    }

    @RequestMapping("/auth/distributionInfoForFun")
    @ResponseBody
    public DistributionFunVO distributionInfoForFun(@RequestParam("rid") Long rid){
        return service.findDistributionInfoForFun(rid) ;
    }

    @RequestMapping("/auth/distributionFun")
    @ResponseBody
    public ResponseVO distributionFun(
            @RequestParam("rid") Long rid,
            @RequestParam("fidStr") String fidStr,
            HttpSession session){
        User user = (User) session.getAttribute("loginUser");
        try{
            service.distributionFun(rid,fidStr,user.getUid());
            return new ResponseVO(0,"分配成功");
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseVO(1,"未知错误");
        }
    }

    @RequestMapping("/auth/authMenus")
    @ResponseBody
    public List<AuthMenuVO> authMenus(HttpSession session){

        return (List<AuthMenuVO>) session.getAttribute("authMenus");
    }
}
