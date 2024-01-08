<%--
  Created by IntelliJ IDEA.
  User: wang
  Date: 2023/8/7
  Time: 14:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
    <head>
        <title>Title</title>
        <link rel="stylesheet" type="text/css" href="layui/css/layui.css" />
        <script src="layui/layui.js"></script>
    </head>
    <body>

    <table id="demo" lay-filter="test"></table>

    <script src="/layui/layui.js"></script>
    <script>
        layui.use('table', function(){
            var table = layui.table;

            //第一个实例
            table.render({
                elem:'#demo'  // 指定展示表格数据的位置
                ,url:'cars'      // 利用ajax请求，向服务端请求表格数据
                ,cols:[[
                    {title:'汽车编号',field:'cno'},
                    {title:'汽车名称',field:'cname'},
                    {title:'汽车颜色',field:'color'},
                    {title:'汽车价格',field:'price'}
                ]]
            });

        });
    </script>
    </body>
</html>
