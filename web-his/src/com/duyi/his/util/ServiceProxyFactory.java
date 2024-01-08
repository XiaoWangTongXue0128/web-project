package com.duyi.his.util;

import org.duyi.jdbc.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 为service创建代理
 * service的代理具有这样的代理
 *  1. 可以正常调用service的方法
 *  2. 可以在service方法的调用前后，创建session，处理session事务，关闭session
 */
public class ServiceProxyFactory {

    public static <T> T getProxy(Class<T> serviceImp){
        try {
            //创建真正的service对象
            Object service = serviceImp.newInstance() ;

            return (T) Proxy.newProxyInstance(
                    serviceImp.getClassLoader(),
                    serviceImp.getInterfaces(),
                    new InvocationHandler() {
                        private Object target = service;
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            //controller调用service(代理)的任何方法时
                            //最终都会执行这个invoke方法
                            //方法的Method参数，就表示到底调用的是代理中哪个方法，进入的当前invoke
                            //利用这个method参数，调用真正的目标方法
                            //args存储的即使在controller中，调用service时传递的参数（集合）

                            SqlSession session = SqlSessionFactoryUtil.getDefaultFactory().getCurrentSession();
                            try{
                                //调用service方法
                                Object result = method.invoke(target,args);
                                session.commit();
                                return result;
                            }catch (Exception e){
                                session.rollback();
                                throw e ;
                            }finally{
                               SqlSessionFactoryUtil.getDefaultFactory().closeSession();
                            }
                        }
                    }
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
