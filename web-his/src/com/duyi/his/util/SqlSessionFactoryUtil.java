package com.duyi.his.util;

import org.duyi.jdbc.SqlSessionFactory;

public class SqlSessionFactoryUtil {

    private static SqlSessionFactory defaultFactory ;
    private static SqlSessionFactory mysqlFactory ;
    private static SqlSessionFactory oracleFactory ;

    static{
        //defaultFactory = new SqlSessionFactory() ;
        //mysqlFactory = new SqlSessionFactory("mysql.properties") ;
        //oracleFactory = new SqlSessionFactory("oracle.properties") ;

        defaultFactory = new HisSqlSessionFactory();
    }

    public static SqlSessionFactory getDefaultFactory(){
        return defaultFactory;
    }

    public static SqlSessionFactory getMysqlFactory(){
        return mysqlFactory;
    }

    public static SqlSessionFactory getOracleFactory(){
        return oracleFactory;
    }



}
