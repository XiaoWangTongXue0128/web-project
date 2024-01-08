package com.duyi.his.filter;

import com.duyi.his.util.LoginUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter({"/login.jsp","/login"})
public class AutoLoginFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(LoginUtil.isAutoLogin(request,response)){
            //执行至此，证明返回的是true，证明已经自动登录,直接访问主页
            request.getRequestDispatcher("/main.jsp").forward(request,response);
            return ;
        }
        //要去登录，又不能自动登录，请求放过，放过去手动登录
        chain.doFilter(request,response);
    }
}
