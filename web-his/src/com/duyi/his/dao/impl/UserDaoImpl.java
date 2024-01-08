package com.duyi.his.dao.impl;

import com.duyi.his.dao.UserDao;
import com.duyi.his.domain.User;
import com.duyi.his.util.SqlSessionFactoryUtil;
import com.duyi.his.util.StringUtil;
import org.duyi.jdbc.SqlSession;
import org.duyi.jdbc.SqlSessionFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDaoImpl implements UserDao {

    /**
     * 包含了用户表本身的所有字段，以及关联的两个字段create_uname,update_uname
     * 其中用户表本身的字段都有[u.]前缀
     * 也就是需要在使用当前片段时，为查询的用户表设置别名 u
     */
    static final String ALL_COLUMN_FRAGMENT =
            "   u.*," +
            "   ifnull((select uname from t_user where uid=u.create_uid),'系统管理员') as create_uname , " +
            "   ifnull((select uname from t_user where uid=u.update_uid),'') as update_uname ";



    @Override
    public User findByUname(String uname) {
        String sql = "" +
                "select " +
                "   uid,uname,zjm,upass,phone,mail,sex,age, " +
                "   create_time,create_uid,update_time,update_uid, " +
                "   delete_flag,yl1,yl2,yl3,yl4 " +
                "from " +
                "   t_user " +
                "where " +
                "   uname=#{uname} or zjm=#{uname} or phone=#{uname} or mail=#{uname}" ;

        //SqlSessionFactory factory = new SqlSessionFactory();
        SqlSession session = SqlSessionFactoryUtil.getDefaultFactory().getSession(true);
        Map<String,String> param = new HashMap<String,String>();
        //为了规避底层可能会存在的一些不足，在一个参数需要使用多次的情况下，需要组成map和对象
        param.put("uname",uname);
        return session.selectOne(sql,param,User.class);
    }

    @Override
    public void updatePwd(long uid, String upass) {
        String sql = "update t_user set upass=#{upass} , update_time=now() , update_uid=#{uid} where uid = #{uid}" ;

        SqlSessionFactory factory = new SqlSessionFactory();
        SqlSession session = factory.getSession(true);
        Map<String,Object> param = new HashMap<>();
        //为了规避底层可能会存在的一些不足，在一个参数需要使用多次的情况下，需要组成map和对象
        param.put("uid",uid);
        param.put("upass",upass);
        session.update(sql,param);
        session.close();
    }

    @Override
    public User findById(long uid) {
        String sql = "" +
                " select " +
                "   uid,uname,zjm,truename,upass,phone,mail,sex,age, " +
                "   create_time,create_uid,update_time,update_uid, " +
                "   delete_flag,yl1,yl2,yl3,yl4 "  +
                " from t_user where uid = #{uid}";

        //SqlSessionFactory factory = new SqlSessionFactory();
        SqlSession session = SqlSessionFactoryUtil.getDefaultFactory().getSession(true);
        Map<String,Long> param = new HashMap<>();
        //为了规避底层可能会存在的一些不足，在一个参数需要使用多次的情况下，需要组成map和对象
        param.put("uid",uid);
        return session.selectOne(sql,param,User.class);
    }

    @Override
    public long listTotal(Map<String, Object> param) {
        String sql = "" +
                " select " +
                "   count(*) " +
                " from " +
                "   t_user u " +
                " where u.delete_flag = 1 ";

        sql = appendSqlWhere(sql,param);

        //SqlSessionFactory factory = new SqlSessionFactory();
        SqlSession session = SqlSessionFactoryUtil.getDefaultFactory().getSession(true);

        return session.selectOne(sql,param,Long.class);
    }

    @Override
    public List<User> list(Map<String, Object> param) {
        //注意，此次查询的结果是需要展示在页面上
        //     在页面展示时，除了要展示用户自己的信息（一条记录）以外
        //     还要展示具体的创建人和修改人。
        //     而数据库中存储的都是创建人id和修改人的id
        //     按照正常逻辑思考，记录中只有创建人id，我们只需要用根据这个id找一下对应的人，就是知道创建人是谁了
        /*
            select * from t_user
            sql中的* 等价于 java中的u  表示一条用户记录

            for(User u : users){
                用当前这条记录的uid在通过select语句查询一下对应的name
                select uname from t_user where uid = u.getUid();
            }
        */
        String sql = "" +
                " select " +
                "   u.*," +
                "   ifnull((select uname from t_user where uid=u.create_uid),'系统管理员') as create_uname , " +
                "   ifnull((select uname from t_user where uid=u.update_uid),'') as update_uname " +
                " from " +
                "   t_user u " +
                " where u.delete_flag = 1 ";

        sql = appendSqlWhere(sql,param);

        //做一个排序，我们想按照时间从前向后排序
        //但有一个问题： 我们有2个时间 create_time , update_time
        //对于一条记录而言，update > create
        //但一条记录不一定有update
        //所以我们想要的是，有update就用update排序，没有update就用create排序
        sql += " order by ifnull(u.update_time,u.create_time) desc ";

        sql += " limit #{start} , #{length}";

        //SqlSessionFactory factory = new SqlSessionFactory();
        SqlSession session = SqlSessionFactoryUtil.getDefaultFactory().getSession(true);

        return session.selectList(sql,param,User.class);
    }


    private String appendSqlWhere(String sql , Map<String,Object> param){
        String uname = (String) param.get("uname");
        if(StringUtil.isNotEmpty(uname)){
            //有uname这个条件 可能用户名，也可能是助记码
            sql += " and (u.uname like concat(#{uname},'%') or u.zjm like concat(#{uname},'%')) ";
        }

        String phone = (String) param.get("phone");
        if(StringUtil.isNotEmpty(phone)){
            sql += " and u.phone like concat(#{phone},'%') ";
        }
        return sql ;
    }

    @Override
    public long findTotalByUname(String uname){
        String sql = "" +
                "select " +
                "  count(*) " +
                "from " +
                "   t_user " +
                "where " +
                "   uname=#{uname} or zjm=#{uname} or phone=#{uname} or mail=#{uname}" ;

        //SqlSessionFactory factory = new SqlSessionFactory();
        SqlSession session = SqlSessionFactoryUtil.getDefaultFactory().getSession(true);
        Map<String,String> param = new HashMap<String,String>();
        //为了规避底层可能会存在的一些不足，在一个参数需要使用多次的情况下，需要组成map和对象
        param.put("uname",uname);
        return session.selectOne(sql,param,Long.class);
    }

    @Override
    public void save(User user) {
        String sql = "insert into t_user(uname,zjm,upass,truename,age,sex " +
                ",phone,mail,create_uid,create_time,delete_flag,yl1,yl2,yl3,yl4 ) " +
                " values(#{uname},#{zjm},#{upass},#{truename},#{age},#{sex},#{phone} " +
                ",#{mail},#{create_uid},now(),#{delete_flag},#{yl1},#{yl2},#{yl3} " +
                ",#{yl4})" ;
        //SqlSessionFactory factory = new SqlSessionFactory();
        SqlSession session = SqlSessionFactoryUtil.getDefaultFactory().getSession(true);
        session.insert(sql,user);
        session.close();
    }

    @Override
    public void delete(Map<String, Long> param) {
        String sql = "update t_user set delete_flag=2,update_time=now()" +
                ",update_uid=#{update_uid} where uid=#{uid}";

        SqlSession session = SqlSessionFactoryUtil.getDefaultFactory().getSession(true);
        session.update(sql,param);
        session.close();
    }

    @Override
    public void update(User user) {
        String sql = "update t_user set truename=#{truename},age=#{age}" +
                ",sex=#{sex},phone=#{phone},mail=#{mail},update_time=now()" +
                ",update_uid=#{update_uid},yl1=#{yl1},yl2=#{yl2},yl3=#{yl3}" +
                ",yl4=#{yl4} where uid=#{uid}" ;

        SqlSession session = SqlSessionFactoryUtil.getDefaultFactory().getSession(true) ;
        session.update(sql,user);
        session.close();
    }

    @Override
    public List<User> findAll() {
        String sql = "select " +
                ALL_COLUMN_FRAGMENT +
                " from t_user u where u.delete_flag = 1" ;

        SqlSession session = SqlSessionFactoryUtil.getDefaultFactory().getSession(true);
        try {
            return session.selectList(sql,null,User.class);
        }finally {
            session.close();
        }
    }

    @Override
    public void resetPass(Map<String, Object> param) {
        String sql = "update t_user set upass=#{upass},update_time=now(),update_uid=#{update_uid} where uid=#{uid}" ;

        SqlSession session = SqlSessionFactoryUtil.getDefaultFactory().getSession(true);
        session.update(sql,param);
        session.close();
    }
}
