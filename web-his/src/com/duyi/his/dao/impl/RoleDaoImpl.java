package com.duyi.his.dao.impl;

import com.duyi.his.dao.RoleDao;
import com.duyi.his.domain.Role;
import com.duyi.his.util.SqlSessionFactoryUtil;
import com.duyi.his.util.StringUtil;
import org.duyi.jdbc.SqlSession;

import java.util.List;
import java.util.Map;

public class RoleDaoImpl implements RoleDao {
    @Override
    public long total(String rname) {
        String sql = "select count(*) from t_role where delete_flag=1 " ;

        if(StringUtil.isNotEmpty(rname)){
            //有过滤条件
            sql += " and rname like concat(#{rname},'%') ";
        }

        SqlSession session = SqlSessionFactoryUtil.getDefaultFactory().getSession(true);
        try{
            return session.selectOne(sql,rname,Long.class);
        }finally {
            session.close();
        }
    }

    @Override
    public List<Role> findAll(Map<String, Object> params) {
        /*
            查询时，角色表中只有角色自己的信息 已经创建人和修改人id
            查询结果要求包含创建人和修改人 名字（name）
            角色有没有，用户表有，所以可以去用户表查询
                方式一： 可以利用子查询 。 之前用户列表中就是如此实现。  效率不高，不推荐
                方式二： 关联查询. 注意同名字段，要为其起别名
         */
        String sql = "" +
                " select " +
                "   r.rid , r.rname , r.description , r.create_time , r.create_uid " +
                "   ,r.update_time , r.update_uid , r.delete_flag , r.yl1 , r.yl2 " +
                "   ,cu.uname as create_uname " +
                "   ,uu.uname as update_uname "+
                " from " +
                "   t_role r " +
                "       left join t_user cu on cu.uid = r.create_uid " +
                "       left join t_user uu on uu.uid = r.update_uid " +
                " where " +
                "   r.delete_flag = 1 " ;
        String rname = (String) params.get("rname");
        if(StringUtil.isNotEmpty(rname)){
            //传递了rname条件
            sql += " and r.rname like concat(#{rname},'%') " ;
        }

        sql += " order by ifnull(r.update_time,r.create_time) desc " ;

        sql += " limit #{start} , #{length} " ;

        SqlSession session = SqlSessionFactoryUtil.getDefaultFactory().getSession();
        try {
            return session.selectList(sql,params,Role.class);
        }finally {
            session.close();
        }
    }

    @Override
    public void save(Role role) {
        String sql = "" +
                " insert into t_role(rname,description,create_time" +
                "   , create_uid,delete_flag,yl1,yl2) " +
                " values(#{rname} , #{description} , now() , #{create_uid} " +
                "   , 1 , #{yl1} , #{yl2} )" ;

        SqlSession session = SqlSessionFactoryUtil.getDefaultFactory().getSession(true);
        session.insert(sql,role);
        session.close();
    }

    @Override
    public void update(Role role) {
        String sql = "" +
                " update t_role set rname=#{rname} , description=#{description} " +
                " , update_time=now() , update_uid=#{update_uid} " +
                " , yl1=#{yl1} , yl2=#{yl2} " +
                " where rid=#{rid} " ;

        SqlSession session = SqlSessionFactoryUtil.getDefaultFactory().getSession(true);
        session.update(sql,role);
        session.close();
    }

    @Override
    public void delete(Map<String,Object> params) {
        String sql = "" +
                " update t_role set delete_flag=2 , update_time=now()" +
                " , update_uid=#{update_uid} " +
                " where rid=#{rid} " ;

        SqlSession session = SqlSessionFactoryUtil.getDefaultFactory().getSession(true);
        session.update(sql,params);
        session.close();

    }

    @Override
    public Role findById(long rid) {
        String sql = "" +
                " select " +
                "   r.rid , r.rname , r.description , r.create_time , r.create_uid " +
                "   ,r.update_time , r.update_uid , r.delete_flag , r.yl1 , r.yl2 " +
                "   ,cu.uname as create_uname " +
                "   ,uu.uname as update_uname "+
                " from " +
                "   t_role r " +
                "       left join t_user cu on cu.uid = r.create_uid " +
                "       left join t_user uu on uu.uid = r.update_uid " +
                " where " +
                "   r.delete_flag = 1 " +
                "   and rid = #{rid} " ;

        SqlSession session = SqlSessionFactoryUtil.getDefaultFactory().getSession(true);
        try {
            return session.selectOne(sql, rid, Role.class);
        }finally {
            session.close();
        }
    }

    @Override
    public long totalByRname(String rname) {
        String sql = "select count(*) from t_role where rname = #{rname} " ;
        SqlSession session = SqlSessionFactoryUtil.getDefaultFactory().getSession(true);
        try{
            return session.selectOne(sql,rname,Long.class);
        }finally {
            session.close();
        }
    }

    @Override
    public List<Map> findAllByUser(long uid) {
        String sql = "" +
                " select " +
                "   r.rid , r.rname , r.description , r.yl1, " +
                "   ifnull(ur.uid,0) LAY_CHECKED " +
                " from " +
                "   t_role r left join t_user_role ur " +
                "                      on r.rid = ur.rid and ur.uid=#{uid} " +
                " where delete_flag=1 " +
                " order by ur.uid desc , r.rid " ;

        SqlSession session = SqlSessionFactoryUtil.getDefaultFactory().getSession();
        try{
            return session.selectList(sql,uid,Map.class);
        }finally {
            session.close();
        }
    }
}
