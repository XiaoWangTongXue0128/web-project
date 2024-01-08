package com.duyi.his.test;

import com.duyi.his.dao.RoleDao;
import com.duyi.his.dao.impl.RoleDaoImpl;
import static junit.framework.TestCase.*;

import com.duyi.his.domain.Role;
import org.junit.Test;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 准备使用junit测试工具
 */
public class RoleDaoTest {
/*
    @BeforeClass
    public static void t1(){
        System.out.println("before");
    }

    @AfterClass
    public static void t2(){
        System.out.println("after");
    }
*/

    RoleDao dao = new RoleDaoImpl();

    @Test
    public void testTotal(){
        long total = dao.total(null);
        assertEquals(4,total);
    }

    @Test
    public void testList(){
        Map<String,Object> param = new HashMap<>();
        param.put("start",0);
        param.put("length",2);
        param.put("rname","");
        List<Role> roles = dao.findAll(param);
        assertEquals(2,roles.size());
        for(Role role : roles){
            System.out.println(role);
        }
    }


    @Test
    public void testSave(){
        Role role = new Role("科室主任","科室里最牛",null,1L,null,null);
        dao.save(role);
    }

    @Test
    public void testUpdate(){
        Role role = new Role(4L,"科室主任","科室里面最最牛的",null,7L,null,null);
        dao.update(role);
    }

    @Test
    public void testDelete(){
        Map<String,Object> param = new HashMap<>();
        param.put("rid",4);
        param.put("update_uid",1);
        dao.delete(param);
    }


}
