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
    <form id="funAddForm" method="post" >
        <div class="loginBox" style="background:#fff;opacity:0.9">
            <div class="layui-form" lay-filter="funAddForm">
                <input type="hidden" name="fid" />

                <div class="layui-form-item">
                    <label class="layui-icon layui-icon-spread-left" for="user-login-fname"></label>
                    <input type="text" name="fname" id="user-login-fname" lay-verify="required" placeholder="功能名称" class="layui-input">
                </div>

                <div class="layui-form-item">
                    <label class="layui-icon layui-icon-tabs" for="user-login-ftype" style="z-index: 999"> </label>
                    <select name="ftype" id="user-login-ftype" lay-verify="required">
                        <option value="">功能类别</option>
                        <option value="1" >菜单</option>
                        <option value="2" >按钮</option>
                    </select>
                </div>

                <div class="layui-form-item">
                    <label class="layui-icon layui-icon-link" for="user-login-furl"></label>
                    <input type="text" name="furl" id="user-login-furl" placeholder="功能链接" class="layui-input">
                </div>

                <div class="layui-form-item">
                    <label class="layui-icon layui-icon-flag" for="auth_flag"></label>
                    <input type="text" name="auth_flag" id="auth_flag" lay-verify="required" placeholder="功能权限" class="layui-input">
                </div>

                <div class="layui-form-item">
                    <label class="layui-icon layui-icon-spread-left" for="pname"></label>
                    <input type="hidden" name="pid" id="pid"/>
                    <input type="text" name="pname" id="pname" lay-verify="required" readonly class="layui-input">
                </div>

                <div class="layui-form-item">
                    <a onclick="selectIcon()" class="layui-btn layui-btn-primary layui-border-blue">
                        <i class="layui-icon layui-icon-table"></i> 选择图标
                    </a>
                    <a class="layui-btn layui-btn-normal" >
                        <i id="iconBox" class="layui-icon layui-icon-user"></i>
                    </a>
                    <input type="hidden" id="yl1" name="yl1" />
                </div>



                <button id="funSubmitBtn" style="display: none;" lay-submit lay-filter="*">提交</button>
            </div>
        </div>
    </form>
</div>

