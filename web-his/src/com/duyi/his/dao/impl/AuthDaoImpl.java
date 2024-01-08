package com.duyi.his.dao.impl;

import com.duyi.his.dao.AuthDao;
import com.duyi.his.util.SqlSessionFactoryUtil;
import org.duyi.jdbc.SqlSession;

import java.util.Map;

public class AuthDaoImpl implements AuthDao {

    @Override
    public void removeRelationshipForUserAndRole(Long uid) {
        String sql = "delete from t_user_role where uid = #{uid}" ;
        SqlSession session = SqlSessionFactoryUtil.getDefaultFactory().getCurrentSession() ;
        session.delete(sql,uid);
        //session.close();
    }

    @Override
    public void addRelationshipForUserAndRole(Map<String, Object> param) {
        String sql = "insert into t_user_role(uid,rid,create_time,create_uid)" +
                " values(#{uid},#{rid},now(),#{create_uid})" ;
        SqlSession session = SqlSessionFactoryUtil.getDefaultFactory().getCurrentSession();
        session.insert(sql,param);
        //session.close();
    }

    @Override
    public void removeRelationshipForRoleAndFun(Long rid) {
        String sql = "delete from t_role_fun where rid = #{rid}" ;
        SqlSession session = SqlSessionFactoryUtil.getDefaultFactory().getCurrentSession() ;
        session.delete(sql,rid);
        //session.close();
    }

    @Override
    public void addRelationshipForRoleAndFun(Map<String, Object> param) {
        String sql = "insert into t_role_fun(rid,fid,create_time,create_uid)" +
                " values(#{rid},#{fid},now(),#{create_uid})" ;
        SqlSession session = SqlSessionFactoryUtil.getDefaultFactory().getCurrentSession();
        session.insert(sql,param);
        //session.close();
    }
}
