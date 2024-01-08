<%@ page pageEncoding="utf-8" %>
<style>
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

<div class="loginWin" >
    <form id="roleAddForm" method="post" >
        <div class="loginBox" style="background:#fff;opacity:0.9">
            <div class="layui-form" lay-filter="roleAddForm">
                <input type="hidden" name="rid" />
                <div class="layui-form-item">
                    <label class="layui-icon layui-icon-username" for="user-login-rname"></label>
                    <input type="text" name="rname" id="user-login-rname" lay-verify="required" placeholder="角色名称" class="layui-input">
                </div>

                <div class="layui-form-item">
                    <label class="layui-icon layui-icon-survey" for="user-login-description"></label>
                    <input type="text" name="description" id="user-login-description" lay-verify="required" placeholder="角色描述" class="layui-input" >
                </div>

                <div class="layui-form-item">
                    <input type="checkbox" name="yl1" id="yl1" lay-skin="switch" value="1" lay-text="启用|禁用" checked>
                </div>

                <button id="roleSubmitBtn" style="display: none;" lay-submit lay-filter="*">提交</button>
            </div>
        </div>
    </form>
</div>

