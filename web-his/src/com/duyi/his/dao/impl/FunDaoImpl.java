package com.duyi.his.dao.impl;

import com.duyi.his.dao.FunDao;
import com.duyi.his.domain.Fun;
import com.duyi.his.util.SqlSessionFactoryUtil;
import org.duyi.jdbc.SqlSession;

import java.util.List;
import java.util.Map;

public class FunDaoImpl implements FunDao {
    @Override
    public List<Fun> findAll() {
        String sql = "" +
                " select" +
                "   f.fid , f.fname , f.ftype , f.furl , f.pid , " +
                "   f.auth_flag , f.create_time , f.create_uid, " +
                "   f.update_time , f.update_uid , f.delete_flag, " +
                "   f.yl1 , f.yl2 , f.yl3 , f.yl4, " +
                "   cu.uname create_uname, " +
                "   uu.uname update_uname, " +
                "   ifnull(pf.fname,'主菜单') pname " +
                " from " +
                "   t_fun f inner join t_user cu on f.create_uid = cu.uid " +
                "           left join t_user uu on f.update_uid = uu.uid " +
                "           left join t_fun pf on f.pid = pf.fid " +
                " where f.delete_flag=1 " +
                " order by fid " ;

        SqlSession session = SqlSessionFactoryUtil.getDefaultFactory().getSession(true) ;
        try{
            return session.selectList(sql,null,Fun.class);
        }finally {
            session.close();
        }
    }

    @Override
    public long totalForName(String fname) {
        String sql = "select count(*) from t_fun where fname = #{fname}" ;
        SqlSession session = SqlSessionFactoryUtil.getDefaultFactory().getSession(true);
        try{
            return session.selectOne(sql,fname,Long.class);
        }finally {
            session.close();
        }
    }

    @Override
    public void save(Fun fun) {
        String sql = " insert into t_fun(fname,ftype,furl,auth_flag,pid,create_time," +
                "create_uid,delete_flag,yl1,yl2,yl3,yl4) values(#{fname},#{ftype}," +
                "#{furl},#{auth_flag},#{pid},now(),#{create_uid},1,#{yl1},#{yl2}," +
                "#{yl3},#{yl4})" ;
        SqlSession session = SqlSessionFactoryUtil.getDefaultFactory().getSession(true);
        session.insert(sql,fun);
        session.close();
    }

    @Override
    public Fun findById(Long fid) {
        String sql = "" +
                " select" +
                "   f.fid , f.fname , f.ftype , f.furl , f.pid , " +
                "   f.auth_flag , f.create_time , f.create_uid, " +
                "   f.update_time , f.update_uid , f.delete_flag, " +
                "   f.yl1 , f.yl2 , f.yl3 , f.yl4, " +
                "   ifnull(pf.fname,'主菜单') pname " +
                " from " +
                "   t_fun f left join t_fun pf on f.pid = pf.fid " +
                " where " +
                "   f.delete_flag=1 " +
                "   and f.fid=#{fid} " +
                " order by f.fid " ;
        SqlSession session = SqlSessionFactoryUtil.getDefaultFactory().getSession(true);
        try{
            return session.selectOne(sql,fid,Fun.class);
        }finally {
            session.close();
        }
    }

    @Override
    public long totalForNameAndId(Fun fun) {
        String sql = "select count(*) from t_fun where fid=#{fid} and fname=#{fname}" ;
        SqlSession session = SqlSessionFactoryUtil.getDefaultFactory().getSession();
        try{
            return session.selectOne(sql,fun,Long.class);
        }finally {
            session.close();
        }
    }

    @Override
    public void update(Fun fun) {
        String sql = " update t_fun set fname=#{fname} , furl=#{furl} , ftype=#{ftype}, " +
                " auth_flag=#{auth_flag} , update_time=now() , update_uid=#{update_uid}, " +
                " pid=#{pid} , yl1=#{yl1} , yl2=#{yl2} , yl3=#{yl3} , yl4=#{yl4} " +
                " where fid=#{fid} " ;

        SqlSession session = SqlSessionFactoryUtil.getDefaultFactory().getSession() ;
        session.update(sql,fun) ;
        session.commit();
        session.close();
    }

    @Override
    public List<Fun> findMenus() {
        String sql = "select fid , fname , pid from t_fun where delete_flag=1 and ftype=1 " ;
        SqlSession session = SqlSessionFactoryUtil.getDefaultFactory().getSession();
        try {
            return session.selectList(sql, null, Fun.class);
        }finally {
            session.close();
        }
    }

    @Override
    public long childrenCount(long fid) {
        String sql = "select count(*) from t_fun where delete_flag=1 and pid=#{fid} " ;
        SqlSession session = SqlSessionFactoryUtil.getDefaultFactory().getSession();
        try{
            return session.selectOne(sql,fid,Long.class);
        }finally {
            session.close();
        }
    }

    @Override
    public void delete(Map<String,Object> param) {
        String sql = "update t_fun set delete_flag=2 , update_time=now() , " +
                " update_uid=#{update_uid} where fid=#{fid} ";
        SqlSession session = SqlSessionFactoryUtil.getDefaultFactory().getSession(true);
        session.update(sql,param);
        session.close();
    }

    @Override
    public List<Map> findAllByRole(Long rid) {
        String sql = "" +
                " select" +
                "   f.fid , f.fname , f.ftype , f.furl , f.auth_flag , f.pid , " +
                "   if( (select fid from t_role_fun where fid=f.fid and rid=#{rid}) is null,0,1) LAY_CHECKED" +
                " from" +
                "   t_fun f" +
                " where f.delete_flag=1" ;
        SqlSession session = SqlSessionFactoryUtil.getDefaultFactory().getCurrentSession();
        return session.selectList(sql,rid,Map.class);
    }

    @Override
    public List<Fun> findAllByUser(Long uid) {
        String sql = "" +
                " select " +
                "   * " +
                " from" +
                "   t_fun" +
                " where " +
                "   fid in " +
                "       (select fid from t_role_fun where rid in " +
                "           (select rid from t_user_role " +
                "               where uid=#{uid} " +
                "                     and rid not in (select rid from t_role where delete_flag=1 and yl1=2 ) " +
                "            ) " +
                "       )" ;
        SqlSession session = SqlSessionFactoryUtil.getDefaultFactory().getCurrentSession();

        return session.selectList(sql,uid,Fun.class);
    }
}
