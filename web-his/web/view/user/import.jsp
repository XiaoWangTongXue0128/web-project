<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>导入用户</title>
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

    </style>
</head>
<body>

<div class="loginWin" >
    <form action="<%=request.getContextPath()%>/user/import" method="post" enctype="multipart/form-data">
        <div class="loginBox" style="background:#fff;opacity:0.9">

            <div class="layui-header">
                <h2>导入用户</h2>
                <p style="padding-top:10px;color:red;font-size:14px;height:14px;">
                    <c:if test="${param.f=='uname'}">用户名重复</c:if>
                    <c:if test="${param.f=='zjm'}">助记码重复</c:if>
                    <c:if test="${param.f=='phone'}">电话重复</c:if>
                    <c:if test="${param.f=='mail'}">邮箱重复</c:if>
                </p>
            </div>

            <div class="layui-form">

                <div class="layui-form-item">
                    <input id="excel" onchange="toShowPath()" type="file" name="excel" style="width:120px;height:40px;background:red;position:absolute;opacity:0;"/>

                    <button type="button" class="layui-btn layui-btn-normal layui-btn-sm">
                        <i class="layui-icon layui-icon-upload-drag"></i>    选择上传文件
                    </button>

                    <span id="file_path_msg" style="margin-left:20px;"></span>
                </div>

                <div class="layui-form-item">
                    <a href="<%=request.getContextPath()%>/excel/users.xlsx" class="layui-btn layui-btn-primary layui-border-blue layui-btn-sm">
                        <i class="layui-icon layui-icon-download-circle"></i>    模板下载
                    </a>
                </div>


                <div class="layui-form-item">
                    <button class="layui-btn layui-btn-fluid" lay-submit lay-filter="user-login-submit">上  传</button>
                </div>

            </div>
        </div>
    </form>

</div>
    <script>


        function toShowPath(){
            layui.use('jquery',function(){
                var $ = layui.$ ;

                var value = $('#excel').val();

                var i = value.lastIndexOf("\\");
                value = value.substring(i+1);

                $('#file_path_msg').html(value);
            });
        }

    </script>
</body>
</html>