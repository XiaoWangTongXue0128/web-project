<%@ page pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>修改密码</title>
    <%--
        request.getContextPath()  可以获得工程名 /his
     --%>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/layui/css/layui.css" media="all">
    <script src="<%=request.getContextPath()%>/layui/layui.js"></script>
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

        .right-link{
            float:right;
            color:#029789;
            position: relative;
            top:5px;
        }

    </style>
</head>
<body>

<div class="loginWin" >
    <form action="<%=request.getContextPath()%>/updatePwd" method="post">
        <div class="loginBox" style="background:#fff;opacity:0.9">

            <div class="layui-header">
                <h2>修改密码</h2>
                <p style="padding-top:10px;color:red;font-size:14px;height:14px;">
                    ${param.f==9?'两次新密码不一致':''}
                </p>
            </div>

            <div class="layui-form">

                <div class="layui-form-item">
                    <label class="layui-icon layui-icon-user" for="user-login-uname"></label>
                    <input type="text" name="uname" id="user-login-uname" value="${param.uname}" lay-verify="required" placeholder="用户名" readonly class="layui-input" >

                </div>


                <div class="layui-form-item">
                    <label class="layui-icon layui-icon-password" for="user-login-npassword"></label>
                    <input type="password" name="upass" id="user-login-npassword" lay-verify="required" placeholder="新密码" class="layui-input">
                </div>

                <div class="layui-form-item">
                    <label class="layui-icon layui-icon-password" for="user-login-repassword"></label>
                    <input type="password" name="repass" id="user-login-repassword" lay-verify="required" placeholder="确认密码" class="layui-input">
                </div>


                <div class="layui-form-item">
                    <button class="layui-btn layui-btn-fluid" lay-submit lay-filter="user-login-submit">确  认</button>
                </div>

            </div>
        </div>
    </form>

</div>

</body>
</html>