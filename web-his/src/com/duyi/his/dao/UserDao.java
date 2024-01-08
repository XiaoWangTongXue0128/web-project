package com.duyi.his.dao;

import com.duyi.his.domain.User;

import java.util.List;
import java.util.Map;

public interface UserDao {

    public User findByUname(String uname);

    public void updatePwd(long uid , String upass) ;

    public User findById(long uid);

    /**
     *
     * @param param {uname , phone   }
     * @return
     */
    public long listTotal(Map<String,Object> param);

    /**
     *
     * @param param { uname , phone , start , length }
     * @return
     */
    public List<User> list(Map<String,Object> param);

    public long findTotalByUname(String uname);

    public void save(User user);

    /**
     *
     * @param param {uid,update_uid}
     */
    public void delete(Map<String,Long> param);


    public void update(User user);

    /**
     * 查找所有在册用户
     * @return
     */
    public List<User> findAll();

    /**
     * 重置密码
     * @param param {uid , upass , update_uid}
     */
    public void resetPass(Map<String,Object> param);
}
