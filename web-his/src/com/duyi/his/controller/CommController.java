package com.duyi.his.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.DES;
import cn.hutool.extra.mail.MailUtil;
import cn.hutool.poi.excel.ExcelUtil;
import com.duyi.his.domain.User;
import com.duyi.his.service.UserService;
import com.duyi.his.service.impl.UserServiceImpl;
import com.duyi.his.util.CommonUtil;
import com.duyi.his.util.LoginUtil;
import org.duyi.mvc.MvcFile;
import org.duyi.mvc.annotations.RequestMapping;
import org.duyi.mvc.annotations.RequestParam;

import javax.servlet.ServletContext;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.UUID;

public class CommController extends HttpServlet {

    private static final int VERCODE_ERROR = 9 ;
    private static final int UNAME_ERROR = 8 ;
    private static final int UPASS_ERROR = 7 ;
    private static final DES des = SecureUtil.des();

    private UserService service = new UserServiceImpl() ;

    @RequestMapping("/verifyCode")
    public void verifyCode(HttpServletResponse resp , HttpSession session) throws IOException {
        //生成验证码
        //我们可以利用java  swing 自己画一个验证码
        //但实际工作中，我们都是第三方工具库—— 这里为推荐一个好用的工具库——hutool
        //生成一个验证码图片（内存），并设置其长和宽
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(120, 40,4,30);
        //获得生成的验证码内容
        String code = lineCaptcha.getCode() ;
        //此次生成的验证码，需要在后面登录操作时，进行验证
        //验证用户输入的验证码和生成的验证码是否一致
        //需要将这个验证码装入session，就可以在后面的登录请求中获取验证码，并检测
        session.setAttribute("code",code);

        //我们的验证码的图片不需要存储在本地，而是需要响应给浏览器
        OutputStream os = resp.getOutputStream();
        lineCaptcha.write(os);
        os.flush();

    }

    @RequestMapping("/login")
    public String login(@RequestParam("uname") String uname
            , @RequestParam("upass") String upass
            , @RequestParam("vercode") String vercode
            , HttpSession session
            , @RequestParam("remember") String remember
            , HttpServletRequest request
            , HttpServletResponse response
    ){
        //实际开发中，其实在controller端，还要对参数作一个为空的判断


        //检测验证码
        String code = (String) session.getAttribute("code");
        if(!vercode.equals(code)){
            //验证码错误，重新刷新登录页面
            return reloadLoginUrl(VERCODE_ERROR);
        }

        //检测账号+密码
        //1. 账号+密码一起检测  以账号和密码作为条件查找符合条件的记录
        //2. 先根据账号找， 再判断密码
        User user = service.findByUname(uname);
        if(user == null){
            //根据uname没有找到用户，用户名错误，重新刷新页面
            return reloadLoginUrl(UNAME_ERROR);
        }

        //代码执行至此，表示根据用户名找到了用户，需要检测密码
        upass = CommonUtil.md5.digestHex(upass);
        if(!user.getUpass().equals(upass)){
            //密码错误，重新刷新登录页面
            return reloadLoginUrl(UPASS_ERROR);
        }

        //代码执行至此，表示登录检测成功，可以进入主界面
        //登录成功的同时，还需要将登录的信息存入session，因为后面的请求操作中会用到。
        //session.setAttribute("loginUser",user);
        LoginUtil.addLoginUser(user,session);

        //此时还需要根据用户是否勾选了“记住密码”，为接下来的自动登录做一些准备
        executeAutoLogin(remember,user.getUid(),request,response);


        return "logSuccess.jsp" ;
    }

    /**
     * 登录成功后，对自动登录做操作做一些提前的准备
     * @param remember
     */
    private void executeAutoLogin(String remember,long uid,HttpServletRequest request,HttpServletResponse response){
        if(remember == null || "".equals(remember) ){
            //没有勾选"记住密码" 拉倒
            return ;
        }
        //勾选了"记住密码"，所以就需要做准备了
        //1 生成一个状态码，令牌码
        String tokenId = UUID.randomUUID().toString().replace("-","");
        //11ac23213214-32847329432
        tokenId += "-" + System.currentTimeMillis();
        //2 将其通过cookie响应给浏览器，未来浏览器每次请求都可以通过cookie传递这个令牌码
        //  找到登录信息，完成自动登录 （令牌码本身可以携带登录信息，一定要加密）
        Cookie cookie = new Cookie("tokenId",tokenId);
        cookie.setMaxAge(60*60*24*7);
        //cookie.setMaxAge(30);
        response.addCookie(cookie);
        //3 将登录信息与令牌码配对存储起来
        //  application(服务器缓存) , redis(缓存) , 数据库
        //  application - ServletContext
        ServletContext application = request.getServletContext();
        application.setAttribute(tokenId,uid);

    }

    @RequestMapping("/exit")
    public String exit(HttpSession session){
        session.invalidate();
        return "login.jsp" ;
    }

    private String reloadLoginUrl(int code){
        return "login.jsp?f="+code;
    }


    @RequestMapping("/forget")
    public String forget(@RequestParam("uname") String uname,HttpServletRequest request){
        User user = service.findByUname(uname);
        if(user == null){
            return "/forget.jsp?f=9" ;
        }else{
            //用户信息正确
            //向用户信息中的邮箱发送一个邮件
            //  未来这个用户可以根据这个邮件 访问 修改密码页面  提供一个超链接（<a>）
            //  在这个修改页面，需要默认显示当前这个用户信息，所以链接应该携带用户信息（用户名）
            //  因为有这个信息，所以这个用户信息需要加密
            //除此以外，还需要考虑一个问题，就是这个链接不能永远生效，需要有一个有效期
            //  有效期可以人为固定，比如30分钟。
            //  这就需要传递当前时间，以供后面判断是否超时
            //从操作过程看，通过邮件发送连接，可以访问修改密码页面
            //  但需要在这之前，判断链接是否过期，并为用户信息解密，这些操作应该在controlelr方法中
            //  所以这个链接请求不能是一个网页请求，需要是一个操作资源的请求
            //还需要注意一个问题
            //  这个请求是在程序生成的，但最终不是在程序中发送
            //  会随着邮件存储在我们的邮箱中，未来也是在邮箱中点击连接
            //  如何确保在邮箱中点击连接，可以访问我们的程序呢
            //  这就需要提供完整的url链接 http://localhost:8080/his/updatePwd.htm?p=xxx
            //  但这样还有问题
            //      未来我们的程序不一定部署在本机，端口也不一定是8080，程序名也不一定是his
            //      可以利用request的一些api获得这些信息
            //链接结构搞定后，参数还需要加密
            //  可以使用hutool工具自带的加密功能
            //利用hutool实现邮件的发送
            //  1. 额外引入javax.mail依赖jar （利用它可以实现原生方式发送邮件）
            //  2. 按照文档定义一个配置文件
            //       文档中的一些提示是基于后面要讲maven程序
            //       maven程序中有src/main/resources  (三个目录的路径)
            //       这个路径就相当于我们现在程序的src目录
            //       文档中要求在src/main/resources中创建一个子文件夹config，创建mail.setting
            //       现在需要在我们程序的src中创建config及mail.setting
            //  3. 配置内容建议参考文档中的完整配置
            //     host 配置邮箱服务器  stmp.qq.com  表示通过qq邮箱发邮件
            //     port 邮箱服务器端口有  默认25
            //     from 发送邮件的那个邮箱 （我的邮箱，应该和host匹配，qq） 373728744@qq.com
            //     user 用户名，必须邮箱名前缀 373728744
            //     pass 密码，不是正常登陆邮箱的密码
            //         如果要通过第三方java程序发送邮件的话，需要在邮箱中，开启发送方式
            //         开启这个方式时，会要求发送一个短信，并获得一套密码
            //         邮箱主页->上面->设置->账户
            //  4. java代码实现发邮件
            //         指定目标邮箱（发给谁），标题，内容， boolean是否是html内容
            String ip = request.getServerName() ;
            int port = request.getServerPort();
            String root = request.getContextPath() ;
            String param = user.getUname()+";"+System.currentTimeMillis();

            param = des.encryptBase64(param);

            String href = "http://"+ip+":"+port+root+"/updatePwd.htm?p="+param ;

            MailUtil.send(user.getMail(), "找回密码", "<h3>点击下面的链接来重置密码：</h3><a href='"+href+"'>"+href+"</a>", true);

            return "/mailTip.jsp?tip=邮件已发送，请及时查收...";
        }
    }

    @RequestMapping("/updatePwd.htm")
    public String updatePwdView(@RequestParam("p") String p){
        //判断是否过期
        //用户信息解密
        //uid;time
        String param = des.decryptStr(p);

        String[] array = param.split(";");
        if(array.length != 2){
            //参数格式不正确，无法进行后续的修改密码
            return "/mailTip.jsp?tip=错误链接参数..." ;
        }

        String uname = array[0];
        long time = Long.parseLong(array[1]);

        long curr = System.currentTimeMillis() ;
        //30分钟超时
        if( (curr-time)/1000/60 > 30 ){
            return "/mailTip.jsp?tip=链接过期，请重新发送邮件...";
        }

        return "/updatePwd.jsp?uname="+uname;
    }


    @RequestMapping("/updatePwd")
    public String updatePwd(@RequestParam("uname") String uname , @RequestParam("upass") String upass
            , @RequestParam("repass") String repass, HttpSession session){

        if(uname==null || "".equals(uname)){

        }

        if(upass==null || "".equals(upass)){

        }

        if(repass==null || "".equals(repass)){

        }

        //原密码正确，接下来判断两次新密码是否一致，也可以在前端测试
        if(!upass.equals(repass)){
            //两次密码不一致
            return "/updatePwd.jsp?f=9" ;
        }

        User user = service.findByUname(uname);
        service.updatePwd(user.getUid(),upass);


        return "/mailTip.jsp?tip=密码重置成功..." ;
    }

}
