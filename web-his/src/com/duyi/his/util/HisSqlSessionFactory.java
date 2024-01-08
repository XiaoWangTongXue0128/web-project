package com.duyi.his.util;

import org.duyi.jdbc.SqlSession;
import org.duyi.jdbc.SqlSessionFactory;

import java.io.InputStream;

public class HisSqlSessionFactory extends SqlSessionFactory {

    private static final ThreadLocal<SqlSession> curr = new ThreadLocal<>();

    public HisSqlSessionFactory() {
    }

    public HisSqlSessionFactory(String filename) {
        super(filename);
    }

    public HisSqlSessionFactory(InputStream is) {
        super(is);
    }

    /**
     * 获得当前线程提供的SqlSession
     * 只要调用当前这个方法的线程是同一个线程，就可以获得同一个SqlSession
     * @return
     */
    @Override
    public SqlSession getCurrentSession(){
        SqlSession session = curr.get();
        if(session == null){
            //当前这个线程还没有对应的SqlSession，创建一个
            session = this.getSession(false);
            curr.set(session);
        }
        return session ;
    }

    @Override
    public void closeSession() {
        SqlSession session = curr.get();
        if(session == null){
            return ;
        }
        curr.remove();
        session.close();
    }
}
