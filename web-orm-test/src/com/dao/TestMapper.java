package com.dao;

import duyi.jdbc.SqlSession;
import duyi.jdbc.SqlSessionFactory;

public class TestMapper {
    public static void main(String[] args) {
        SqlSessionFactory factory = new SqlSessionFactory( );
        SqlSession session = factory.getSession(true) ;
        ICarDao dao =session.getMapper(ICarDao.class);
        dao.delete(2);
    }
}
