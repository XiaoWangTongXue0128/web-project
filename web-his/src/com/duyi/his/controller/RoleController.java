package com.duyi.his.controller;


import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.duyi.his.domain.Role;
import com.duyi.his.domain.User;
import com.duyi.his.service.RoleService;
import com.duyi.his.service.impl.RoleServiceImpl;
import com.duyi.his.util.CommonUtil;
import com.duyi.his.util.StringUtil;
import com.duyi.his.vo.LayPageVO;
import com.duyi.his.vo.PageVO;
import com.duyi.his.vo.ResponseVO;
import org.duyi.mvc.MvcFile;
import org.duyi.mvc.annotations.RequestMapping;
import org.duyi.mvc.annotations.RequestParam;
import org.duyi.mvc.annotations.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RoleController {

    private RoleService service = new RoleServiceImpl();

    @RequestMapping("/role/list")
    @ResponseBody
    public LayPageVO list(@RequestParam("page") Integer page ,@RequestParam("limit") Integer limit , @RequestParam("rname") String rname){
        if(StringUtil.isEmpty(page)){
            page = CommonUtil.DEFAULT_PAGE;
        }
        if(StringUtil.isEmpty(limit)){
            limit = CommonUtil.DEFAULT_ROWS;
        }

        //实现分页过滤查询功能
        Map<String,Object> params = new HashMap<>();
        params.put("page",page);
        params.put("rows",limit);
        params.put("rname",rname);
        PageVO pageVO = service.findByPage(params);

        LayPageVO vo = new LayPageVO() ;
        vo.setData(pageVO.getData());
        vo.setCount(pageVO.getTotal());
        vo.setMsg("没有任何记录");
        vo.setCode(0);

        return vo ;
    }

    /**
     *
     * @param role {rname , description , yl1}
     */
    @RequestMapping("/role/save")
    @ResponseBody
    public ResponseVO save(Role role, HttpSession session){
        User user = (User) session.getAttribute("loginUser");
        role.setCreate_uid(user.getUid());

        String result = service.save(role);

        if(StringUtil.isEmpty(result)){
            //保存成功
            return new ResponseVO(0,"保存成功");
        }else{
            //保存失败
            return new ResponseVO(1,"角色名称重复") ;
        }
    }


    @RequestMapping("/role/edit")
    @ResponseBody
    public Role edit(@RequestParam("rid") Long rid){
        Role role = service.findForEdit(rid);
        return role ;
    }

    @RequestMapping("/role/update")
    @ResponseBody
    public ResponseVO update(Role role,HttpSession session){

        User user = (User) session.getAttribute("loginUser");
        role.setUpdate_uid(user.getUid());

        String result = service.update(role);
        if(StringUtil.isEmpty(result)){
            //保存成功
            return new ResponseVO(0,"保存成功");
        }else{
            //保存失败
            return new ResponseVO(1,"角色名称重复") ;
        }
    }

    @RequestMapping("/role/delete")
    @ResponseBody
    public ResponseVO delete(@RequestParam("rid") Long rid,HttpSession session){
        User user = (User) session.getAttribute("loginUser");
        service.delete(rid,user.getUid());
        return new ResponseVO(0,"删除成功") ;
    }

    @RequestMapping("/role/deletes")
    @ResponseBody
    public ResponseVO deletes(@RequestParam("rids") String rids,HttpSession session){

        if(StringUtil.isEmpty(rids)){
            return new ResponseVO(1,"没有指定要删除的信息") ;
        }

        User user = (User) session.getAttribute("loginUser");
        // 将rids --> ridArray[]
        // "1,2,3,4,5" --> {1,2,3,4,5}
        String[] ridArray = rids.split(",");
        long[] ridLongArray = new long[rids.length()] ;
        int i = 0 ;
        for(String rid : ridArray){
            ridLongArray[i++] = Long.parseLong(rid);
        }
        service.deletes(ridLongArray,user.getUid());

        return new ResponseVO(0,"删除成功") ;
    }


    @RequestMapping("/role/import")
    @ResponseBody
    public ResponseVO imports(@RequestParam("excel") MvcFile excel,HttpSession session){
        User user = (User) session.getAttribute("loginUser");
        long uid = user.getUid() ;

        InputStream is = excel.getInputStream() ;
        ExcelReader reader = ExcelUtil.getReader(is);

        reader.addHeaderAlias("角色名称","rname");
        reader.addHeaderAlias("角色描述","description");
        reader.addHeaderAlias("角色状态","yl1");

        List<Role> roles = reader.readAll(Role.class);

        //为读取到的每一个角色信息补充create_uid
        for(Role role : roles){
            role.setCreate_uid(uid);
            role.setYl1( role.getYl1().equals("启用")?"1":"2" );
        }

        String result = service.saves(roles);

        return new ResponseVO(200,result) ;
    }

    @RequestMapping("/role/export")
    public void export(HttpServletResponse response) throws IOException {
        List<Role> roles = service.findAllForExport();

        //写入缓存中，是2007 xlsx版本
        ExcelWriter writer = ExcelUtil.getWriter(true);

        writer.addHeaderAlias("rid","角色编号");
        writer.addHeaderAlias("rname","角色名称");
        writer.addHeaderAlias("description","角色描述");
        writer.addHeaderAlias("yl1","角色状态");
        writer.addHeaderAlias("create_time","创建时间");
        writer.addHeaderAlias("create_uname","创建人");
        writer.addHeaderAlias("update_time","修改时间");
        writer.addHeaderAlias("update_uname","修改人");

        //将roles集合中每一个role对象的yl1属性值，从1和2改成启用和禁用
        roles = roles
                .stream()
                .map((role)->{ role.setYl1(role.getYl1().equals("1")?"启用":"禁用");return role ;})
                .collect(Collectors.toList());

        writer.setOnlyAlias(true);

        writer.write(roles,true);

        //将缓冲中excel内容响应给浏览器，形成下载

        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition","attachment;filename=roles.xls");
        OutputStream out=response.getOutputStream();

        writer.flush(out, true);
    }
}
