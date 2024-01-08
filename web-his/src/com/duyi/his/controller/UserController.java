package com.duyi.his.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.extra.pinyin.PinyinUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.duyi.his.annotations.Auth;
import com.duyi.his.domain.User;
import com.duyi.his.service.UserService;
import com.duyi.his.service.impl.UserServiceImpl;
import com.duyi.his.util.CommonUtil;
import com.duyi.his.util.StringUtil;
import com.duyi.his.util.TipUtil;
import com.duyi.his.vo.PageVO;
import org.duyi.mvc.ModelAndView;
import org.duyi.mvc.MvcFile;
import org.duyi.mvc.annotations.RequestMapping;
import org.duyi.mvc.annotations.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserController {

    private static final int DEFAULT_PAGE = 1 ;
    private static final int DEFAULT_ROWS = 10 ;

    private UserService service = new UserServiceImpl() ;

    @RequestMapping("/user/updatePwd")
    public String updatePwd(@RequestParam("opass") String opass , @RequestParam("upass") String upass
            , @RequestParam("repass") String repass, HttpSession session){

        if(opass==null || "".equals(opass)){

        }

        if(upass==null || "".equals(upass)){

        }

        if(repass==null || "".equals(repass)){

        }

        User user = (User) session.getAttribute("loginUser");
        opass = CommonUtil.md5.digestHex(opass);
        upass = CommonUtil.md5.digestHex(upass);
        repass = CommonUtil.md5.digestHex(repass);

        if(!user.getUpass().equals(opass)){
            //原密码不正确
            return "/view/user/updatePwd.jsp?f=9" ;
        }
        //原密码正确，接下来判断新旧密码是否一致
        if (opass.equals(upass)) {
            return "/view/user/updatePwd.jsp?f=8" ;
        }
        //原密码正确，接下来判断两次新密码是否一致，也可以在前端测试
        if(!upass.equals(repass)){
            //两次密码不一致
            return "/view/user/updatePwd.jsp?f=7" ;
        }
        //修改密码了
        user.setUpass(upass);
        service.updatePwd(user.getUid(),upass);

        return "/view/user/updatePwdSuccess.jsp" ;
    }

    @RequestMapping("/user/list")
    public ModelAndView list(@RequestParam("page") Integer page ,@RequestParam("rows") Integer rows
                     ,@RequestParam("uname") String uname ,@RequestParam("phone") String phone){
        if(page == null){
            page = DEFAULT_PAGE ;
        }
        if(rows == null){
            rows = DEFAULT_ROWS ;
        }
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("page",page);
        param.put("rows",rows);
        param.put("uname",uname);
        param.put("phone",phone);

        PageVO vo = service.list(param);
        //转发访问网页并携带数据
        //我们的mvc框架如何实现上述需求
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/view/user/list.jsp");
        mv.setAttribute("vo",vo);
        return mv ;
    }

    @RequestMapping("/user/save")
    public ModelAndView save(User user,HttpSession session){
        ModelAndView mv = new ModelAndView();
        //需要先来一组为空判断
        if(StringUtil.isEmpty(user.getUname())){
            mv.setViewName("/view/user/add.jsp?f=uname");
            return mv;
        }

        User loginUser = (User) session.getAttribute("loginUser");

        String result = service.save(user,loginUser.getUid());

        if(StringUtil.isEmpty(result)){
            //执行正确，可以显示一个提示页面
            mv.setViewName("/view/user/addSuccess.jsp");
            return mv;
        }else{
            //有问题，重新显示保存页面，并提示
            mv.setViewName("/view/user/add.jsp?f="+result);
            mv.setAttribute("user",user);
            return mv;

        }
    }

    @Auth("com:duyi:his:sys:user:delete")
    @RequestMapping("/user/delete")
    public String delete(@RequestParam("uid") int uid,HttpSession session){
        //dao中删除时，就是对delete_flag做了一个修改，同时还需要更新这条记录的修改时间和修改人
        //修改时间就是系统时间，修改人就是当前登录操作的人，这个人在session中
        User loginUser = (User) session.getAttribute("loginUser");

        Map<String,Long> param = new HashMap<>();
        param.put("uid",(long)uid);
        param.put("update_uid",loginUser.getUid());

        try{
            service.delete(param);
            //删除成功
            return TipUtil.tip("删除成功","/user/list");
        }catch (Exception e){
            //删除失败
            return "/commonTip.jsp?msg=删除失败&url=/user/list";
        }

    }

    @RequestMapping("/user/deletes")
    public String deletes(@RequestParam("uids") String uids,HttpSession session){
        User loginUser = (User) session.getAttribute("loginUser");

        Map<String,Object> param = new HashMap<String,Object>();
        param.put("uids",uids);
        param.put("update_uid",loginUser.getUid());

        service.deletes(param);

        return TipUtil.tip("删除成功","/user/list");
    }

    @RequestMapping("/user/editSelect")
    public ModelAndView editSelect(@RequestParam("uid") Long uid){
        User user = service.findById(uid);

        //将数据携带到编辑页面，默认展示
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/view/user/edit.jsp");
        mv.setAttribute("user",user);
        return mv ;
    }

    @RequestMapping("/user/update")
    public ModelAndView update(User user,HttpSession session){
        ModelAndView mv = new ModelAndView();
        //需要先补全一些数据
        //缺少修改人的信息，当前登录操作的用户，存在session中
        User loginUser = (User) session.getAttribute("loginUser");
        user.setUpdate_uid(loginUser.getUid());

        String result = service.update(user);

        if(StringUtil.isEmpty(result)){
            //执行正确，可以显示一个提示页面
            mv.setViewName(TipUtil.tip("修改成功","/user/list"));
            return mv;
        }else{
            //有问题，重新显示保存页面，并提示
            mv.setViewName("/view/user/edit.jsp?f="+result);
            mv.setAttribute("user",user);
            return mv;

        }
    }


    @RequestMapping("/user/import")
    public String imports(@RequestParam("excel") MvcFile excel,HttpSession session){


       //利用hutool+poi读取excel内容
        InputStream is = excel.getInputStream() ;

        //获得一个Reader读取工具，准备从指定的（文件或）inputStream读取
        ExcelReader reader = ExcelUtil.getReader(is);

        //设置excel列标题与对象的属性之间的对应关系
        reader.addHeaderAlias("用户名","uname");
        reader.addHeaderAlias("真实姓名","truename");
        reader.addHeaderAlias("年龄","age");
        reader.addHeaderAlias("性别","sex");
        reader.addHeaderAlias("电话","phone");
        reader.addHeaderAlias("邮箱","mail");

        List<User> users = reader.readAll(User.class);


        User loginUser = (User) session.getAttribute("loginUser");
        String tip = service.saves(users,loginUser.getUid());
        return TipUtil.tip(tip,"/user/list");
    }


    @RequestMapping("/user/export")
    public void export(HttpServletResponse response) throws IOException {
        //获取数据
        List<User> users = service.findAll() ;

        //利用hutool工具将数据写入excel（文件，缓存）
        //true 表示是2007以后的excel.xlsx  false(默认) 表示是2003的excel.xls
        ExcelWriter writer = ExcelUtil.getWriter(true);

        //设置首行标题名称
        writer.addHeaderAlias("uid","编号");
        writer.addHeaderAlias("uname","用户名");
        writer.addHeaderAlias("zjm","助记码");
        writer.addHeaderAlias("truename","真实姓名");
        writer.addHeaderAlias("age","年龄");
        writer.addHeaderAlias("sex","性别");
        writer.addHeaderAlias("phone","电话");
        writer.addHeaderAlias("mail","邮箱");
        writer.addHeaderAlias("create_time","创建时间");
        writer.addHeaderAlias("create_uname","创建人");
        writer.addHeaderAlias("update_time","修改时间");
        writer.addHeaderAlias("update_uname","修改人");
        //只写出设置别名的字段
        writer.setOnlyAlias(true);

        writer.write(users,true);

        //下载到浏览器
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Content-Disposition","attachment;filename="+new String("用户列表.xlsx".getBytes(),"iso-8859-1"));
        OutputStream out = response.getOutputStream();
        writer.flush(out,true);
        writer.close();
    }


    @RequestMapping("/user/resetPass")
    public String resetPass(@RequestParam("uid") long uid,HttpSession session){
        User loginUser = (User) session.getAttribute("loginUser");

        service.resetPass(uid,loginUser.getUid());

        return TipUtil.tip("密码重置成功","/user/list");
    }
}
