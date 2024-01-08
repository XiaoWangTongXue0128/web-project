package com.duyi.his.test;

import com.duyi.his.dao.RoleDao;
import com.duyi.his.dao.impl.RoleDaoImpl;
import com.duyi.his.domain.Role;
import com.duyi.his.service.RoleService;
import com.duyi.his.service.impl.RoleServiceImpl;
import com.duyi.his.util.StringUtil;
import com.duyi.his.vo.PageVO;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoleServiceTest {

    RoleService service = new RoleServiceImpl() ;
    RoleDao dao = new RoleDaoImpl() ;

    @Test
    public void testFindByPage(){
        Map<String,Object> param = new HashMap<>();
        param.put("page",2);
        param.put("rows",2);
        PageVO byPage = service.findByPage(param);

        Assert.assertEquals(2,byPage.getData().size());
        Assert.assertEquals(new Integer(2),byPage.getMax());

        List<Role> roles = (List<Role>) byPage.getData();
        for(Role role :roles){
            System.out.println(role);
        }

    }

    @Test
    public void testFindForEdit(){
        Role role = service.findForEdit(1);
        Assert.assertNotNull(role);
        System.out.println(role);
    }

    @Test
    public void testFindAllForExport(){

        List<Role> roles = service.findAllForExport() ;
        long total = dao.total("");

        Assert.assertEquals(total,roles.size());
        for(Role role : roles){
            System.out.println(role);
        }
    }

    @Test
    public void testSave(){
        Role role = new Role("后勤主任","后勤老大",null,7L,null,null);
        String result = service.save( role );
        Assert.assertTrue( StringUtil.isEmpty(result) ) ;
    }

    @Test
    public void testSaves(){
        List<Role> roles = Arrays.asList(
            new Role("后勤主任1","后勤老大",null,7L,null,null),
            new Role("后勤主任2","后勤老大",null,7L,null,null),
            new Role("后勤主任3","后勤老大",null,7L,null,null),
            new Role("后勤主任4","后勤老大",null,7L,null,null),
            new Role("后勤主任1","后勤老大",null,7L,null,null),
            new Role("后勤主任2","后勤老大",null,7L,null,null)
        ) ;

        String result = service.saves(roles);
        //4成2败
        System.out.println(result);
    }

    @Test
    public void testUpdate(){
        Role role = new Role(1L,"高级管理员","最牛了",null,7L,null,null);
        String result = service.update(role);

        try{
            Assert.assertTrue( StringUtil.isEmpty(result) ) ;
            System.out.println("修改成功");
        }catch(Throwable e){
            System.out.println("修改失败 ：" + result + " 重复");
        }

    }

    @Test
    public void testDelete(){
        service.delete(5L,7L);
    }

    @Test
    public void testDeletes(){
        long[] rids = {5,6,7};
        service.deletes(rids,7);
    }

    @Test
    public void testDisable(){
        service.disable(1L,7L);
    }

    @Test
    public void testEnable(){
        service.enable(1L,7L);
    }



}
