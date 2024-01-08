package com.duyi.his.dao;

import com.duyi.his.domain.Role;

import java.util.List;
import java.util.Map;

public interface RoleDao {

    /**=========================角色列表===========================*/
    /**
     * 获得记录总数
     * 在过滤分页查询前，获得此次过滤分页查询的记录总数
     * 获得记录总数时，需要按照过滤条件查询
     * 需要传递过滤条件 ： rname （String nullable)
     * @param  rname
     * @return 记录总数
     */
    long total(String rname);

    /**
     * 过滤分页查询
     * 需要传递过滤条件 + 分页条件 （多个——需要组成map）
     *  过滤条件： rname (String nullable)
     *  分页条件： start (int notnull) , length (int notnull)
     * @param params {start , length , rname}
     * @return  角色信息列表（包含create_uname , update_uname）
     */
    List<Role> findAll(Map<String,Object> params);
    /**===========================================================*/


    /**=========================角色保存===========================*/
    /**
     * 保存角色信息
     * @param role { rname , descrption ,  create_time (nullable) , create_uid
     *              , delete_flag (nullable) , yl1 , yl2  }
     */
    void save(Role role);
    /**===========================================================*/

    /**=========================角色编辑===========================*/
    /**
     * 修改角色信息
     * @param role { rid , rname , description ,  update_uid , update_time (nullable)
     *              , yl1 , yl2}
     */
    void update(Role role) ;
    /**==========================================================*/


    /**=========================角色删除===========================*/
    /**
     * 删除角色信息
     * 需要传递要删除角色的编号rid
     * 还要传递修改人的编号 update_uid
     * 需要组成map
     * @param params { rid , update_uid }
     */
    void delete(Map<String,Object> params);
    /**===========================================================*/


    /**=========================角色单一记录查询===========================*/
    /**
     * 根据主键查询
     * @param rid
     * @return
     */
    Role findById(long rid) ;
    /**===========================================================*/


    /**
     * 根据角色名称查询获得同名记录的数量（可以用来判断唯一性）
     * @param rname
     * @return
     */
    long totalByRname(String rname);


    /**=============权限分配模块分析中新增的数据交互==================*/
    public List<Map> findAllByUser(long uid);



    /**===========================================================*/
}
