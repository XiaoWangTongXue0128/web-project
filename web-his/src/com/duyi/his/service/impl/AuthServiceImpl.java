package com.duyi.his.service.impl;

import com.duyi.his.dao.AuthDao;
import com.duyi.his.dao.FunDao;
import com.duyi.his.dao.RoleDao;
import com.duyi.his.dao.UserDao;
import com.duyi.his.dao.impl.AuthDaoImpl;
import com.duyi.his.dao.impl.FunDaoImpl;
import com.duyi.his.dao.impl.RoleDaoImpl;
import com.duyi.his.dao.impl.UserDaoImpl;
import com.duyi.his.domain.Role;
import com.duyi.his.domain.User;
import com.duyi.his.service.AuthService;
import com.duyi.his.util.SqlSessionFactoryUtil;
import com.duyi.his.util.StringUtil;
import com.duyi.his.vo.DistributionFunVO;
import com.duyi.his.vo.DistributionRoleVO;
import org.duyi.jdbc.SqlSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthServiceImpl implements AuthService {

    private UserDao userDao = new UserDaoImpl() ;
    private RoleDao roleDao = new RoleDaoImpl() ;
    private FunDao funDao = new FunDaoImpl() ;
    private AuthDao authDao = new AuthDaoImpl();

    @Override
    public DistributionRoleVO findDistributionInfoForRole(Long uid) {

        //需要用户的原始数据
        User user = userDao.findById(uid);

        //获得角色相关信息
        //  所有的角色信息
        //  上一次分配的角色信息
        //      在网页中的使用，就是一个勾选效果
        //      我们需要的是通过上一次分配的角色信息，来确定某一个角色的勾选状态
        //      如果只需要在每一个角色信息的基础上增加一个够选择状态
        //      可以使用一个新的sql就完成，新的dao方法
        List<Map> roles = roleDao.findAllByUser(uid);

        return new DistributionRoleVO(user , roles) ;
    }

    @Override
    public void distributionRole(Long uid, String ridStr,Long create_uid) {
        //先移除之前的分配关系
        authDao.removeRelationshipForUserAndRole(uid);

        //再添加新的分配关系（自然也包括之前）
        if(StringUtil.isEmpty(ridStr)){
            return ;
        }
        Map<String,Object> param = new HashMap<>();
        param.put("uid",uid);
        param.put("create_uid",create_uid);
        String[] ridArray = ridStr.split(",");
        int count = 0 ;
        for(String rid : ridArray){
            if(count == ridArray.length-1){
               // throw new RuntimeException("人造异常：数据库连接超时");
            }
            param.put("rid",Long.parseLong(rid));
            authDao.addRelationshipForUserAndRole(param);
            count++ ;
        }

    }

    @Override
    public DistributionFunVO findDistributionInfoForFun(Long rid) {
        Role role = roleDao.findById(rid);
        List<Map> funs = funDao.findAllByRole(rid);
        return new DistributionFunVO(role,funs);
    }

    @Override
    public void distributionFun(Long rid, String fidStr, Long create_uid) {
        //先删除之前的分配关系
        authDao.removeRelationshipForRoleAndFun(rid);
        //再重新添加新的分配关系
        if(StringUtil.isEmpty(fidStr)){
            return ;
        }
        String[] fidArray = fidStr.split(",");
        Map<String,Object> param = new HashMap<>();
        param.put("rid",rid);
        param.put("create_uid",create_uid);
        for(String fid:fidArray){
            param.put("fid",Long.parseLong(fid));
            authDao.addRelationshipForRoleAndFun(param);
        }
    }
}
