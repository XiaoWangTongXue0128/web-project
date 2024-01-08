package com.duyi.his.service;

import com.duyi.his.domain.User;
import com.duyi.his.vo.PageVO;

import java.util.List;
import java.util.Map;

public interface UserService {

    public User findByUname(String uname) ;

    public void updatePwd(long uid,String upass );

    /**
     *
     * @param param {
     *                 page(not-null),
     *                 rows(not-null),
     *                 uname(nullable),
     *                 phone(nullable)
     *             }
     */
    public PageVO list(Map<String,Object> param);

    /**
     *
     * @param user
     * @return null or "" 表示保存成功
     *         "uname" 表示用户名重复  张珊1  张山2
     *         "zjm"   表示助记码重复
     *         "phone" , "mail"
     */
    public String save(User user,long create_uid) ;

    /**
     *
     * @param param  {uid , update_uid}
     */
    public void delete(Map<String,Long> param);

    /**
     *
     * @param param {uids , update_uid}
     */
    public void deletes(Map<String,Object> param);


    public User findById(long uid) ;

    /**
     * 无法修改用户的用户名（助记码）
     * @param user
     * @return      null or "" 修改成功
     *              “phone” 表示电话重复
     *              “main” 表示邮箱重复
     */
    public String update(User user);

    /**
     * 批量保存
     * @param users
     * @param create_uid
     * @return
     */
    public String saves(List<User> users,long create_uid);

    /**
     * 查找所有在册记录（未删除）
     * @return
     */
    public List<User> findAll();

    /**
     * 重置密码
     * @param uid           目标用户id
     * @param update_uid    操作者id
     */
    public void resetPass(long uid , long update_uid);
}
