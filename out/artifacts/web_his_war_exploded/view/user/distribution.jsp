<%@ page pageEncoding="utf-8" %>
<!-- 分配角色的模板 -->
<div class="distribution-box" style="padding:10px;">

    <!-- 替换状态字段的内容，将原来1或2 改成现在的启用或禁用 -->
    <script type="text/html" id="yl1Column">
        {{#  if(d.yl1==1){     }}
        <span class="layui-badge layui-bg-green">启用</span>
        {{#  }else{            }}
        <span  class="layui-badge" >禁用</span>
        {{#  }                 }}
    </script>

    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">用户编号：</label>
            <div class="layui-input-inline" style="width: 120px;">
                <input type="text" id="distribution_uno" readonly value="" class="layui-input">
            </div>
        </div>

        <div class="layui-inline">
            <label class="layui-form-label">用户名：</label>
            <div class="layui-input-inline" style="width: 120px;">
                <input type="text" id="distribution_uname" readonly  value="" class="layui-input">
            </div>
        </div>

        <div class="layui-inline">
            <label class="layui-form-label">真实姓名：</label>
            <div class="layui-input-inline" style="width: 120px;">
                <input type="text" id="distribution_truename" readonly  value="" class="layui-input">
            </div>
        </div>
    </div>

    <hr/>

    <table id="roleGrid" lay-filter="roleGrid"></table>

</div>