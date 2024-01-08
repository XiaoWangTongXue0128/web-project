<%@ page pageEncoding="utf-8" %>
<html>
    <head>
		<meta charset="utf-8"/>
        <base href="<%=request.getContextPath()%>/">
        <link rel="stylesheet" href="layui/css/layui.css"/>
        <script src="layui/layui.js"></script>
        <script src="js/fun.js"></script>
        <style>
            fieldset{padding:10px;border:0;border-top:1px solid #ddd;margin:10px;}
			fieldset legend{font-size:14px;}
        </style>
    </head>
    <body style="padding: 20px;">
        
		<fieldset>
            <legend>功能列表</legend>
        </fieldset>
		
        <!-- 展示数据的位置 -->
        <table id="funGrid" lay-filter="funGrid"></table>

        <!-- 替换功能类别 -->
        <script type="text/html" id="ftypeBox">
            {{#  if(d.ftype==2){            }}
            <span class="layui-badge">按钮</span>
            {{#  }else{                     }}
                <span class="layui-badge layui-bg-green">菜单</span>
            {{#  }                          }}
        </script>


        <!-- 表头工具栏 -->
        <script type="text/html" id="toolbar">
            <button class="layui-btn layui-btn-primary layui-border-blue layui-btn-sm" lay-event="toExpand">
                <i class="layui-icon layui-icon-triangle-d"></i> 全部展开
            </button>
            <button class="layui-btn layui-btn-primary layui-border-blue layui-btn-sm" lay-event="toFold">
                <i class="layui-icon layui-icon-triangle-r"></i> 全部合并
            </button>
            <button class="layui-btn layui-btn-primary layui-border-blue layui-btn-sm" lay-event="toAddRoot">
                <i class="layui-icon layui-icon-add-circle"></i> 新建根级别功能
            </button>
        </script>

        <!-- 行工具栏 -->
        <script type="text/html" id="tool">

            {{# if(d.ftype==1){                 }}
                <button class="layui-btn layui-btn-primary layui-border-blue layui-btn-xs" lay-event="toAddChild">
                    <i class="layui-icon layui-icon-add-circle"></i> 新建子级别功能
                </button>
            {{# }else{                          }}
                <button class="layui-btn layui-btn-primary layui-border-blue layui-btn-xs layui-btn-disabled">
                    <i class="layui-icon layui-icon-add-circle"></i> 新建子级别功能
                </button>
            {{# }                               }}
            <button class="layui-btn layui-btn-primary layui-border-orange layui-btn-xs" lay-event="toEdit">
                <i class="layui-icon layui-icon-edit"></i> 编辑
            </button>

            <button class="layui-btn layui-btn-primary layui-border-red layui-btn-xs" lay-event="toDelete">
                <i class="layui-icon layui-icon-delete"></i> 删除
            </button>

        </script>
    </body>
	


</html>