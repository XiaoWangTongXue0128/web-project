package com.duyi.his.service.impl;

import com.duyi.his.dao.RoleDao;
import com.duyi.his.dao.impl.RoleDaoImpl;
import com.duyi.his.domain.Role;
import com.duyi.his.service.RoleService;
import com.duyi.his.vo.PageVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoleServiceImpl implements RoleService {
    private RoleDao dao = new RoleDaoImpl();

    @Override
    public PageVO findByPage(Map<String, Object> params) {
        /*
        * 分页过滤查询
        * 分页查询需要 page ， rows
        * 先确保page是有效的， 在上限与下限中间
        * */

        int page = (int) params.get("page");
        int rows = (int) params.get("rows");
        String rname = (String) params.get("rname");

        long total = dao.total(rname);
        int max = (int)Math.ceil(1.0*total/rows) ;
        page = Math.min(max , page) ;

        page = Math.max(1,page) ;

        int start = (page-1)*rows ;
        params.put("start",start) ;
        params.put("length",rows) ;

        List<Role> roles = dao.findAll(params);

        // 0 - 9   10-19
        // length=10
        int end = start+rows-1 ;
        //此时params中包含{page , rows , rname , start , end}
        return new PageVO(roles,total,rows,page,max,start,end,params);
    }

    @Override
    public String save(Role role) {
        long total = dao.totalByRname(role.getRname());
        if(total > 0){
            //角色名称已经存在
            return "rname" ;
        }
        dao.save(role);
        return "" ;
    }

    @Override
    public String update(Role role) {
        //在rname被修改的情况下，要判断新的rname的唯一性
        Role old = dao.findById(role.getRid());
        if( !old.getRname().equals(role.getRname())
            && dao.totalByRname(role.getRname()) > 0 ){
            //新改的rname和其他角色信息的名称重复
            return "rname" ;
        }
        dao.update(role);
        return "" ;
    }

    @Override
    public void delete(long rid, long update_uid) {
        Map<String,Object> params = new HashMap<>();
        params.put("rid",rid);
        params.put("update_uid",update_uid);
        dao.delete(params);
    }

    @Override
    public void deletes(long[] rids, long update_uid) {
        Map<String,Object> params = new HashMap<>();
        params.put("update_uid",update_uid);
        for(long rid : rids){
            params.put("rid",rid);
           dao.delete(params);
        }
    }

    @Override
    public void enable(long rid,long update_uid) {
        //将yl1改成1
        Role role = dao.findById(rid);
        role.setYl1("1");
        role.setUpdate_uid(update_uid);
        dao.update(role);
    }

    @Override
    public void disable(long rid,long update_uid) {
        //将yl1改成2
        Role role = dao.findById(rid);
        role.setYl1("2");
        role.setUpdate_uid(update_uid);
        dao.update(role);
    }

    @Override
    public Role findForEdit(long rid) {
        return dao.findById(rid);
    }

    @Override
    public String saves(List<Role> roles) {
        //存储成功数量
        int count1 = 0 ;
        //存储失败的数量
        int count2 = 0 ;
        String tip = "" ;
        for(Role role : roles){
            long total = dao.totalByRname(role.getRname());
            if(total > 0){
                //当前角色信息的名称重复，不能保存
                count2++ ;
                tip+="【"+role.getRname()+"】名称重复\\n" ;
                continue;
            }
            //角色名称可用，就可以正常保存
            count1++ ;
            dao.save(role);
        }

        tip = "成功保存【"+count1+"】条记录，失败【"+count2+"】记录\\n" + tip ;

        return tip ;
    }

    @Override
    public List<Role> findAllForExport() {
        /*
            dao中并没有提供专门查找所有记录的方法
            虽然有一个叫findAll方法，但是用来分页过滤查询的
            我们就稍微处理一下参数，利用这个方法获得所有的记录
         */
        Map<String,Object> params = new HashMap<>();
        params.put("start",0);
        params.put("length",Integer.MAX_VALUE) ;
        return dao.findAll(params);
    }
}
