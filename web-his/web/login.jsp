<%@ page pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>登入</title>
  <link rel="stylesheet" href="layui/css/layui.css" media="all">
  <script src="layui/layui.js"></script>
  <style>
    html{
      background:#f2f2f2;
      height:100%;
      position: relative;
    }
    .loginBox{
      width:380px;
      margin:110px auto ;
    }
    .layui-header{
      text-align: center;
      padding:20px;
      padding-bottom:0;
      font-size:20px;
    }
    .layui-form{
      padding:20px;
    }
    .layui-input{
      padding-left:38px;
      border-color: #eee;
      border-radius: 2px;
    }
    label.layui-icon{
      position:relative ;
      width:38px;
      height:38px;
      top:28px;
      left:10px;
    }
    .vercode-box{
      position: relative;
      top:12px;
      border:1px solid #ccc;
    }
    .right-link{
      float:right;
      color:#029789;
      position: relative;
      top:5px;
    }
    .copy-footer{
      position: absolute;
      text-align: center;
      width:100%;
      bottom:0;
      padding-bottom:20px;
    }
  </style>
</head>
<body>

  <div class="loginWin" >
	<form action="login" method="post">
      <div class="loginBox" style="background:#fff;opacity:0.9">

        <div class="layui-header">
          <h2>欢迎登录</h2>
          <p style="padding-top:10px;color:red;font-size:14px;height:14px;">
            ${param.f==9?'验证码错误':(param.f==8?'用户名错误':(param.f==7?'密码错误':''))}
          </p>
        </div>

        <div class="layui-form">

          <div class="layui-form-item">
            <label class="layui-icon-user layui-icon layui-icon-username" for="user-login-username"></label>
            <input type="text" name="uname" id="user-login-username" lay-verify="required" placeholder="用户名" class="layui-input">
          </div>

          <div class="layui-form-item">
            <label class="layui-icon layui-icon-password" for="user-login-password"></label>
            <input type="password" name="upass" id="user-login-password" lay-verify="required" placeholder="密码" class="layui-input">
          </div>

          <div class="layui-form-item">
            <div class="layui-row">
              <div class="layui-col-xs7">
                <label class="layui-icon-vercode layui-icon" for="user-login-vercode"></label>
                <input type="text" name="vercode" id="user-login-vercode" lay-verify="required" placeholder="图形验证码" class="layui-input">
              </div>
              <div class="layui-col-xs5">
                <div class="vercode-box" style="margin-left: 10px;">
                  <img src="verifyCode" class="" id="user-get-vercode" onclick="changeCode()">
                </div>
              </div>
            </div>
          </div>

          <div class="layui-form-item" style="margin-bottom: 20px;">
            <input type="checkbox" name="remember" lay-skin="primary" title="记住密码">
            <a href="forget.jsp" target="_blank" class="right-link" style="margin-top: 7px;">忘记密码？</a>
          </div>

          <div class="layui-form-item">
            <button class="layui-btn layui-btn-fluid" lay-submit lay-filter="user-login-submit">登 入</button>
          </div>

        </div>
      </div>
    </form>
    <div class="layui-trans copy-footer">
      <p>&copy; 版权所有：渡一集团</p>
    </div>
  </div>



  <script>
    var v=0;
    var f = true ; //可以切换验证码
    function changeCode(){
      //我们需要通过id，获得img标签，更换img.src属性值，发送新的请求，并获得新的验证码
      //我们想用jquery获得标签
      //layui中内置了jquery，所以就不需要额外引入jquery了
      //layui中的jquery不能直接用，必须声明
      layui.use('jquery',function(){
          var $ = layui.$ ;
          if(f == true){
            $('#user-get-vercode').attr('src','verifyCode?v='+v++);
            f = false ;

            setTimeout(function(){
              f = true ;
            },1000);

          }

      });
    }
  </script>
</body>
</html>