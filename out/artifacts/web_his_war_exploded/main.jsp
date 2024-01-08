<%@ page pageEncoding="utf-8" %>
<html>
    <head>
		<meta charset="utf-8" />
        <title>主页</title>
        <link rel="stylesheet" href="layui/css/layui.css"/>
        <base href="<%=request.getContextPath()%>/">
        <script src="layui/layui.js"></script>
        <style>
            .layui-tree-txt , .layui-tree-txt a{
                color:#eee;
            }
        </style>
    </head>
    <body>
    <div class="layui-layout layui-layout-admin">

        <div class="layui-header">
            <div class="layui-logo layui-hide-xs layui-bg-black">HIS医院信息系统</div>
            <ul class="layui-nav layui-layout-right">
                <li class="layui-nav-item layui-hide layui-show-md-inline-block" style="margin-right:10px;">
                    <a href="javascript:;">
                        <img src="http://tva1.sinaimg.cn/crop.0.0.118.118.180/5db11ff4gw1e77d3nqrv8j203b03cweg.jpg" class="layui-nav-img">
                        欢迎：${sessionScope.loginUser.uname}
                    </a>
                    <dl class="layui-nav-child">
                        <dd><a href="view/user/updatePwd.jsp" target="content">修改密码</a></dd>
                        <dd><a href="javascript:toExit()">退出</a></dd>
                    </dl>
                </li>
                
            </ul>
        </div>

        <div class="layui-side layui-bg-black  layui-side-menu">
            <!-- <div id="test1" class="demo-tree demo-tree-box"></div> -->
			<!-- 侧边菜单 -->
     
     
          <div class="layui-side-scroll">
          <ul id="menuBox" class="layui-nav layui-nav-tree"   lay-shrink="all" lay-filter="layadmin-system-side-menu" >
            <!-- 原来是静态数据，直接写在html中 -->
            <!-- 现在是动态数据，从后台获得数据，利用js写在html -->
			
          </ul>
		  </div>

      
        </div>

        <div class="layui-body">
            <!-- 内容主体区域 -->
            <iframe width="100%" height="100%" frameborder="0" name="content" src="home.jsp" ></iframe>
		
        </div>

        <div class="layui-footer">
            <!-- 底部固定区域 -->
           <p align="center">&copy; 版权所有：渡一集团</p>
        </div>
    </div>

    </body>

    <script>
        function toExit(){
            var f = confirm('是否确认退出');
            if(f){
                //点击了确定，需要注销
                location.href='exit' ;
            }
        }


        //网页加载完毕时执行
        layui.use(['jquery','element'],function(){
            var $ = layui.$ ;
            var element = layui.element ;

            //后台获得权限菜单
            $.post('<%=request.getContextPath()%>/auth/authMenus',{},function(menus){
                showRootMenu(menus);

                //需要重新渲染一下页面 类似之前（引入的复选框，下拉框都需要重新渲染）
                element.init();
            },'json');

            function showRootMenu(menus){
                var $ul = $('#menuBox') ;
                //第一次循环菜单，循环一定都是根菜单
                for(var i=0;i<menus.length;i++){
                    var rootMenu = menus[i] ;
                    //新创建一个li，每一个根菜单都是一个li
                    var $li = $('<li class="layui-nav-item"></li>');
                    var hrefStr='' ;
                    if(rootMenu.furl && rootMenu.furl!=''){
                        //有url,点击菜单需要发送请求
                        //'href="xxxxx.jsp"'
                        hrefStr='href="<%=request.getContextPath()%>/'+rootMenu.furl+'"';
                    }else{
                        hrefStr='href="javascript:;"'
                    }
                    var $a = $('<a '+hrefStr+' target="content"><i class="layui-icon '+rootMenu.icon+'"></i> <cite>'+rootMenu.fname+'</cite></a>')
                    $li.append($a);
                    $ul.append($li);
                    var children = rootMenu.children;
                    if(children && children.length > 0){
                        //有子菜单
                        showChildMenu(children,$a);
                    }
                }
            }

            //将子菜单展示在指定的target目标下面
            function showChildMenu(children,$target){
                //每一组子菜单需要放在一个 <dl>中
                var $dl = $('<dl class="layui-nav-child"></dl>');
                $target.after($dl);
                for(var i=0;i<children.length;i++){
                    var menu = children[i];
                    var $dd = $('<dd></dd>');
                    var hrefStr='' ;
                    if(menu.furl && menu.furl!=''){
                        //有url,点击菜单需要发送请求
                        //'href="xxxxx.jsp"'
                        hrefStr='href="<%=request.getContextPath()%>/'+menu.furl+'"';
                        console.log(hrefStr);
                    }else{
                        hrefStr='href="javascript:;"'
                    }
                    var $a = $('<a '+hrefStr+' target="content"><i class="layui-icon"></i><cite>'+menu.fname+'</cite></a>')
                    $dd.append($a);
                    $dl.append($dd);
                    var children2 = menu.children;
                    if(children2 && children2.length > 0){
                        //有子菜单
                        showChildMenu(children2,$a);
                    }
                }
            }

        });

    </script>
</html>