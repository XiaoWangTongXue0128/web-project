package com.duyi.his.interceptors;

import com.duyi.his.annotations.Auth;
import org.duyi.mvc.MappingInfo;
import org.duyi.mvc.MvcInterceptorFunction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Set;

public class AuthInterceptor implements MvcInterceptorFunction {
    @Override
    public boolean prev(HttpServletRequest req, HttpServletResponse resp, Object target) throws Exception {
        Set<String> authFlags = (Set<String>) req.getSession().getAttribute("authFlags");

        MappingInfo info = (MappingInfo) target;
        Method method = info.getMethod() ;
        Auth auth = method.getAnnotation(Auth.class);
        if(auth == null){
            //没写这个注解，以后可以报错，现在不管，直接放过
            return true ;
        }else{
            //通过注解获得的权限字符串
            //表达的含义是，只有具备这个权限，才能处理此次请求
            //到底具不具备这个权限呢，就需要判断
            String auth_flag = auth.value() ;

            if(authFlags.contains(auth_flag)){
                //具备这个权限，继续执行请求
                return true ;
            }

            //需要这个权限，但又不具备这个权限，直接反馈
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().write("<h2>权限不足，请联系管理员....<h2>");
        }

        return false ;
    }






    @Override
    public void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {

    }
}
