<%@ page pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>忘记密码</title>
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
	<form action="forget" method="post">
      <div class="loginBox" style="background:#fff;opacity:0.9">

        <div class="layui-header">
          <h2>找回密码</h2>
          <p style="padding-top:10px;color:red;font-size:14px;height:14px;">
            ${param.f==9?'用户信息有误或用户不存在':''}
          </p>
        </div>

        <div class="layui-form">

          <div class="layui-form-item">
            <label class="layui-icon-user layui-icon layui-icon-username" for="user-login-username"></label>
            <input type="text" name="uname" id="user-login-username" lay-verify="required" placeholder="用户名 / 电话 / 邮箱" class="layui-input">
          </div>

          <div class="layui-form-item">
            <button class="layui-btn layui-btn-fluid" lay-submit lay-filter="user-login-submit">发  送</button>
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