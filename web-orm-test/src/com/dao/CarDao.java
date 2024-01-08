package com.dao;

import com.domain.Car;
import duyi.jdbc.SqlSession;
import duyi.jdbc.SqlSessionFactory;

import java.util.List;

public class CarDao {
    public void save(Car car){
        String sql = "insert into t_car values(null,#{cname},#{color},#{price})" ;
        SqlSessionFactory factory = new SqlSessionFactory();
        //手动处理事务的session 。 factory.getSession(true) 获得自动处理事务的session
        SqlSession session = factory.getSession() ;
        session.insert(sql,car);
        //需要手动提交
        session.commit();
    }

    public void findAll(){
        String sql = "select * from t_car" ;
        SqlSessionFactory factory = new SqlSessionFactory( );
        SqlSession session = factory.getSession() ;
        //Car.class 将查询结果中的每条记录，组成一个Car对象 按照表字段=对象属性
        List<Car> cars = session.selectList(sql,null,Car.class);
        for(Car car : cars){
            System.out.println(car);
        }
    }
}
