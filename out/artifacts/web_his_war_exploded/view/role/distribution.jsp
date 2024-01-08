<%@ page pageEncoding="utf-8" %>
<!-- 分配功能的模板 -->
<div class="distribution-box" style="padding:10px;">

    <!-- 替换功能类型字段的内容，将原来1或2 改成现在的菜单和按钮 -->
    <script type="text/html" id="ftypeColumn">
        {{#  if(d.ftype==1){     }}
        <span class="layui-badge layui-bg-green">菜单</span>
        {{#  }else{            }}
        <span  class="layui-badge" >按钮</span>
        {{#  }                 }}
    </script>

    <!-- 表头工具栏 -->
    <script type="text/html" id="toolbar">
        <button class="layui-btn layui-btn-primary layui-border-blue layui-btn-sm" lay-event="toExpand">
            <i class="layui-icon layui-icon-triangle-d"></i> 全部展开
        </button>
        <button class="layui-btn layui-btn-primary layui-border-blue layui-btn-sm" lay-event="toFold">
            <i class="layui-icon layui-icon-triangle-r"></i> 全部合并
        </button>
    </script>

    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">角色编号：</label>
            <div class="layui-input-inline" style="width: 120px;">
                <input type="text" id="distribution_rid" readonly value="" class="layui-input">
            </div>
        </div>

        <div class="layui-inline">
            <label class="layui-form-label">角色名称：</label>
            <div class="layui-input-inline" style="width: 120px;">
                <input type="text" id="distribution_rname" readonly  value="" class="layui-input">
            </div>
        </div>

    </div>

    <hr/>

    <table id="funGrid" lay-filter="funGrid"></table>

</div>