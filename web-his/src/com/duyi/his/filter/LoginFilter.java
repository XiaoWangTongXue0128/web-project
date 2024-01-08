package com.duyi.his.filter;

import com.duyi.his.domain.User;
import com.duyi.his.util.LoginUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 登录认证的过滤器（过程中检票）
 */
@WebFilter("/*")
public class LoginFilter extends HttpFilter {

    String[] excludes = new String[]{"login.jsp","login","timeout.jsp"
            ,"exit","*.js","*.css","*.png","*.jpg","verifyCode"
            ,"*.eot","*.svg","*.ttf","*.woff","*.woff2","forget.jsp","forget"
            ,"updatePwd.htm","mailTip.jsp","updatePwd"};

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //做一些验证，在某些操作请求或页面访问之前，来检测是否存在登录信息
        //但有些操作请求和页面访问是不需要进行登录认证，可以直接访问（放过请求）
        //比如  login.jsp,login,timeout.jsp,exit,verifyCode,*.js , *.css , *.png
        //要想看看此次请求是否是一个需要排除在外的请求，那么就需要先获得此次请求

        // http://localhost:8080/web_his/view/login.jsp
        //request.getRequestURL();
        // /web_his/view/login.jsp
        //request.getRequestURI();
        // /view/login.jsp
        String path = request.getServletPath();
        for(String exclude : excludes){
            if(path.startsWith("/")){
                //去掉斜杠
                path = path.substring(1);
            }
            if(exclude.startsWith("*")){
                //做一个后缀的统配   *.js --> .js
                exclude = exclude.substring(1);
                if(path.endsWith(exclude)){
                    //判断成立，当前这个path请求是不需要登录验证的，直接放过
                    chain.doFilter(request,response);
                    return ;
                }
            }else{
                //没有统配，完全匹配
                if(exclude.equals(path)){
                    //判断成立，不需要验证，直接放过
                    chain.doFilter(request,response);
                    return ;
                }
            }

        }

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loginUser");
        if(user != null) {
            //如果验证通过，放过请求，就绪完成这次的请求操作
            chain.doFilter(request, response);
        }else{
            //原来，验证没有通过，就直接访问登录页，重新登录
            //现在有了这个自动登录，先不着急去登录页，先看看能不能自动登录
            if(LoginUtil.isAutoLogin(request,response)){
                //执行至此，证明返回的是true，证明已经自动登录,请求放过
                chain.doFilter(request,response);
                return ;
            }
            //不能自动登录，只能重新访问登录页，手动登录。
            //验证没有通过，给提示，重新登录
            request.getRequestDispatcher("/timeout.jsp").forward(request,response);
        }
    }
}
