package com.duyi.his.service;

import com.duyi.his.domain.Role;
import com.duyi.his.vo.PageVO;

import java.util.List;
import java.util.Map;

public interface RoleService {

    /**
     * 分页过滤查询 <br/>
     * 需要调用者传递分页条件  page (int) , rows (int) <br/>
     * 需要调用者传递过滤条件  rname <br/>
     *
     * 会查询获得对应的结果 List<Role> <br/>
     * 因为是分页查询，所以还会获得一些与分页相关的信息 <br/>
     *      {page , rows , total , max , start , end} <br/>
     *      这些信息也一同返回 <br/>
     *
     *
     * @param params { page (notnull), rows (notnull) , rname (nullable) }
     * @return {list , page , rows , total , max , start , end}
     */
    PageVO findByPage(Map<String,Object> params);


    /**
     * 保存角色信息
     * @param role （不包含rid ， update_time , update_uid）
     *              {rname , description（nullable） , create_uid , create_time(nullable)
     *              , yl1（nullable） , yl2（nullable）}
     * @return null or "" 保存成功 <br/>
     *         "rname" 角色名称重复
     */
    String save(Role role);

    /**
     * 修改角色信息
     * @param role {rid , rname , description , update_time(nullable) , update_uid
     *             , yl1 , yl2}
     * @return null or "" 修改成功<br/>
     *         "rname" 角色名称重复
     */
    String update(Role role);

    /**
     * 删除角色信息
     * @param rid
     * @param update_uid 修改人编号
     */
    void delete(long rid,long update_uid) ;

    /**
     * 批量删除角色
     * @param rids 要删除的角色编号 （要求使用者传递数组）
     * @param update_uid 修改人编号
     */
    void deletes(long[] rids , long update_uid);

    /**
     * 启用某一个角色
     * @param rid
     * @deprecated
     */
    void enable(long rid,long update_uid) ;

    /**
     * 禁用某一个角色
     * @param rid
     * @deprecated
     */
    void disable(long rid,long update_uid);

    /**
     * 根据id查找单条记录，为了去编辑
     * @param rid
     * @return
     */
    Role findForEdit(long rid);

    /**
     * 为了导出，查询所有的角色信息
     * @return
     */
    List<Role> findAllForExport();

    /**
     * 批量保存
     * @param roles (每一个role都需要包含create_uid)
     * @return 具体保存结果提示
     *         "成功保存【x】条记录，失败【y】记录\n【xxx1】名称重复\n【xxx2】名称重复\n"
     *
     */
    String saves(List<Role> roles);

}
