package com.duyi.his.dao;

import com.duyi.his.domain.Fun;

import java.util.List;
import java.util.Map;

public interface FunDao {
    public List<Fun> findAll();

    public long totalForName(String fname) ;

    public void save(Fun fun);

    public Fun findById(Long fid) ;

    /**
     * 获得这个指定功能的指定名字的数量
     * 简单来说，就是看看这个fid对应的功能的名字是不是指定的fname名字
     * @param fun {fid , fname}
     * @return  0 表示此次要修改名字。 1 表示此次没有修改名字
     */
    public long totalForNameAndId(Fun fun) ;

    public void update(Fun fun) ;

    public List<Fun> findMenus();

    public long childrenCount(long fid);

    /**
     *
     * @param param {fid , update_uid}
     */
    public void delete(Map<String,Object> param) ;


    /**================角色分配功能逻辑中新增的功能方法=============*/
    public List<Map> findAllByRole(Long rid) ;


    /**================权限控制逻辑中获得所有的权限功能=============*/
    public List<Fun> findAllByUser(Long uid);

}
