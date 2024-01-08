<%@ page pageEncoding="utf-8" %>
<div style="padding:10px;">

    <button style="margin-bottom:10px;" type="button" class="layui-btn layui-btn-normal " id="selectFile">
        <i class="layui-icon layui-icon-upload-drag"></i> 选择文件
    </button>

    <button style="display:none;" type="button" class="layui-btn layui-btn-normal " id="actionBtn"></button>

    <a href="excel/roles.xlsx" style="margin-bottom:10px;" type="button" class="layui-btn " >
        <i class="layui-icon layui-icon-download-circle"></i> 下载模板
    </a>

    <table class="layui-table">
        <colgroup>
            <col width="100">
            <col width="200">
        </colgroup>
        <tbody>
            <tr>
                <td>文件名</td>
                <td id="fileName"></td>
            </tr>
            <tr>
                <td>文件大小</td>
                <td id="fileSize"></td>
            </tr>
            <tr>
                <td>上传进度</td>
                <td id="progress"></td>
            </tr>
        </tbody>
    </table>
</div>

<script>
    layui.use(['upload','jquery','element','table'],function(){
        var upload = layui.upload ;
        var $ = layui.$ ;
        var element = layui.element ;
        var table = layui.table ;

        upload.render({
            elem:'#selectFile',
            auto:false,
            bindAction:'#actionBtn',
            url:'role/import',
            accept:'file',
            exts:'xlsx|xls',
            field:'excel',
            choose:function(obj){
                var files = obj.pushFile();
                obj.preview(function(index,file,result){
                    $('#fileName').html(file.name);
                    $('#fileSize').html(file.size) ;
                    $('#progress').html(
                        '<div class="layui-progress layui-progress-big" lay-filter="demo" lay-showPercent="true">\n' +
                        '  <div class="layui-progress-bar" lay-percent="0%"></div>\n' +
                        '</div>'
                    )
                });
            },
            progress: function(n, elem, res, index){
                var percent = n + '%' //获取进度百分比
                element.progress('demo', percent); //demo就是上述展示进度条位置的那个div的lay-filter="demo"

            },
            done:function(res,index,result){
                //res 就是响应回来的responseVO对象
                //index 多文件上上传时，文件的下标
                //upload 可以实现重新上传方法
                //res就是api指定的jsonStr
                if(res.code==200){
                    layer.alert(res.msg,function(){
                        layer.closeAll();
                        table.reload('roleGrid') ;
                    });
                }
            }
        });


    });
</script>

