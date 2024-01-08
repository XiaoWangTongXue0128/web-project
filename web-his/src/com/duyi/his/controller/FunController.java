package com.duyi.his.controller;


import com.duyi.his.domain.Fun;
import com.duyi.his.domain.User;
import com.duyi.his.service.FunService;
import com.duyi.his.service.impl.FunServiceImpl;
import com.duyi.his.util.StringUtil;
import com.duyi.his.vo.LayPageVO;
import com.duyi.his.vo.MenuVO;
import com.duyi.his.vo.ResponseVO;
import org.duyi.mvc.annotations.RequestMapping;
import org.duyi.mvc.annotations.RequestParam;
import org.duyi.mvc.annotations.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

public class FunController {

    private FunService service = new FunServiceImpl();

    @RequestMapping("/fun/list")
    @ResponseBody
    public LayPageVO list(){
        //查找所有的功能信息
        List<Fun> funs = service.findAll() ;

        LayPageVO vo = new LayPageVO();
        vo.setData(funs);
        vo.setCode(0);
        vo.setMsg("ok");

        return vo ;
    }

    @RequestMapping("/fun/save")
    @ResponseBody
    public ResponseVO save(Fun fun, HttpSession session){
        //补充创建人id
        User user = (User) session.getAttribute("loginUser");
        fun.setCreate_uid(user.getUid());

        String result = service.save(fun);
        if(StringUtil.isEmpty(result)){
            //保存成功
            return new ResponseVO(0,"保存成功") ;
        }else{
            //保存失败
            return new ResponseVO(1,"功能名称重复") ;
        }
    }

    @RequestMapping("/fun/edit")
    @ResponseBody
    public Fun edit(@RequestParam("fid") Long fid){
        return service.findById(fid);
    }

    @RequestMapping("/fun/update")
    @ResponseBody
    public ResponseVO update(Fun fun,HttpSession session){
        User user = (User) session.getAttribute("loginUser");
        fun.setUpdate_uid( user.getUid() );

        String result = service.update(fun);

        if(StringUtil.isEmpty(result)){
            //成功
            return new ResponseVO(0,"修改成功");
        }else{
            //失败，return fname , 功能名重复
            return new ResponseVO(1,"功能名称重复") ;
        }

    }


    @RequestMapping("/fun/parentMenuList")
    @ResponseBody
    public List<MenuVO> parentMenuList(@RequestParam("curr_fid") Long curr_fid){
        return service.findParentMenuForChange(curr_fid);
    }

    @RequestMapping("/fun/childrenCount")
    @ResponseBody
    public long childrenCount(@RequestParam("fid") Long fid){
        return service.childrenCount(fid);
    }

    @RequestMapping("/fun/delete")
    @ResponseBody
    public ResponseVO delete(@RequestParam("fid") Long fid,HttpSession session){
        User user = (User) session.getAttribute("loginUser");
        service.delete(fid,user.getUid());
        return new ResponseVO(0,"删除成功") ;
    }
}
