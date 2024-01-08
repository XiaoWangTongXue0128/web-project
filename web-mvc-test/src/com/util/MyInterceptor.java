package com.util;

import org.duyi.mvc.MvcInterceptorFunction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyInterceptor implements MvcInterceptorFunction {
    /** 请求处理前执行的功能方法 return false 终止后续操作。return true继续后续操作*/
    @Override
    public boolean prev(HttpServletRequest req, HttpServletResponse resp, Object target) throws Exception {
        System.out.println("之前");
        return true;
    }

    /** 请求处理后执行的功能方法 */
    @Override
    public void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        System.out.println("之后");
    }
}
