<%@ page pageEncoding="utf-8" %>
<html>
    <head>
		<meta charset="utf-8" />
        <base href="<%=request.getContextPath()%>/">
        <link rel="stylesheet" href="layui/css/layui.css"/>
        <script src="layui/layui.js"></script>

		<style>
			.layui-table{margin:0;}
			.layui-form-checkbox[lay-skin=primary] i{top:6px;}
			fieldset{padding:10px;border:0;border-top:1px solid #ddd;margin:10px;}
			fieldset legend{font-size:14px;}
		</style>
    </head>
    <body style="padding: 20px;" class="layui-fluid">
		<fieldset>
			<legend>角色列表</legend>
		</fieldset>
		
		<!-- =====================表格===================== -->
		<table id="roleGrid" class="layui-table" lay-filter="roleGrid"></table>

        
		<!-- 表格数据行中的按钮 -->
		<script type="text/html" id="dataBtns">
            <button class="layui-btn layui-btn-primary layui-border-orange  layui-btn-xs" lay-event="toEdit">
				<i class="layui-icon">&#xe642;</i>    编辑
			</button>
			<button  class="layui-btn layui-btn-primary layui-border-red  layui-btn-xs" lay-event="toDelete">
				<i class="layui-icon">&#xe640;</i>    删除
			</button>
            <button  class="layui-btn layui-btn-primary layui-border-blue  layui-btn-xs" lay-event="toDistribution">
                <i class="layui-icon layui-icon-set"></i>    分配功能
            </button>
		</script>
		
		<!-- ===============表格头部过滤组件+按钮组件===================== -->
        <script type="text/html" id="titleBtns">
            <div class="layui-inline">
                <label style="width:100px;" class="layui-form-label">角色名称：</label>
                <div class="layui-input-inline" style="width: 120px;">
                    <input  type="text" id="rname" class="layui-input">
                </div>
            </div>

            <div class="layui-inline">
                <button  class="layui-btn layui-btn-primary layui-border-blue layui-btn-sm" lay-event="toQuery">
                    <i class="layui-icon">&#xe615;</i>    查询
                </button>

                <button  class="layui-btn layui-btn-primary layui-border-blue layui-btn-sm" lay-event="toClearQuery">
                    <i class="layui-icon">&#xe615;</i>    清空查询
                </button>
				
				<a class="layui-btn layui-btn-primary layui-border-blue layui-btn-sm" id="addBtn" lay-event="toAdd">
					<i class="layui-icon">&#xe61f;</i>    新建
				</a>

                <a class="layui-btn layui-btn-primary layui-border-blue layui-btn-sm"  lay-event="toDeletes">
                    <i class="layui-icon layui-icon-delete"></i>    批量删除
                </a>

                <a  class="layui-btn layui-btn-primary layui-border-blue layui-btn-sm" lay-event="toImport">
                    <i class="layui-icon">&#xe681;</i>    导入
                </a>

                <a href="role/export" class="layui-btn layui-btn-primary layui-border-blue layui-btn-sm">
                    <i class="layui-icon">&#xe67d;</i>    导出
                </a>
            </div>
        </script> 

        <!-- 替换状态字段的内容，将原来1或2 改成现在的启用或禁用 -->
        <script type="text/html" id="yl1Column">
            {{#  if(d.yl1==1){     }}
                <span class="layui-badge layui-bg-green">启用</span>
            {{#  }else{            }}
                <span  class="layui-badge" >禁用</span>
            {{#  }                 }}
        </script>


		
    </body>

    <script>
        layui.config({
            base: 'treetable-lay/'
        }).use(['table','jquery','layer','form','treeTable'],function(){
            var table = layui.table;
			var $ = layui.$ ;
			var layer = layui.layer ;
			var form = layui.form ;
			var treeTable = layui.treeTable ;

			//执行渲染
			table.render({
			  elem: '#roleGrid' //指定原始表格元素选择器（推荐id选择器）
			  ,height:'full-120'
			  ,cols: [[
				  {type:'checkbox',width:'4%'},
				  {title:'序号',width:'4%',type:'numbers'},
				  {title:'角色名称',width:'10%',field:'rname'},
				  {title:'角色描述',width:'12%',field:'description'},
				  {title:'角色状态',width:'8%',field:'yl1',templet:'#yl1Column'},
				  {title:'创建人',width:'8%',field:'create_uname'},
				  {title:'创建时间',width:'11%',field:'create_time',templet:dateFormat},
				  {title:'修改人',width:'8%',field:'update_uname'},
                  {title:'修改时间',width:'11%',field:'update_time',templet:dateFormat},
                  {width:'24%',templet:'#dataBtns',title:'操作'}
			  ]] //设置表头
			  ,url:'role/list'
			  //,page:true       //开启了自带的分页栏，每次请求都会自动携带page + rows参数
              ,page:{
			      limit:10
                }
              //,where:{rname:$('runame').val(),x:100}
			  ,toolbar:'#titleBtns'
              ,defaultToolbar:[]
			});

            table.on('toolbar(roleGrid)',function(obj){
                switch(obj.event){
                    case "toQuery" : toQuery() ;break ;
                    case "toClearQuery":toClearQuery();break ;
                    case "toAdd":toAdd() ;break ;
                    case "toDeletes": toDeletes();break ;
                    case "toImport" : toImport();break ;
                }
            })

            function toClearQuery(){
                $('#rname').val('');
                toQuery();
            }

            function toAdd(){
                //打开一个弹出层，这个弹出层长什么样呢？需要一些配置
                $.post('view/role/add.jsp',null,function(view){
                    layer.open({
                        title:'角色新建', //设置标题
                        type:'1',        //设置弹出层类型（异步的，两个网页在一个窗口中，可以互相访问）
                        area:[400,350],  //设置窗口大小的
                        content:view,
                        success:function(){//弹出层展示完毕时，回调的函数
                            form.render(); //更新全部
                        },
                        btn:['确定','取消'],
                        yes:function(){
                            //点击确定按钮时触发事件
                            //触发表单提交
                            //原来我们都是用鼠标点击按钮，现在等于用代码点击按钮。
                            $('#roleSubmitBtn').click();
                        },
                        btn2:function(){
                            //点击取消按钮时触发的事件，未来还有btn3,btn4....
                            alert('取消')
                        }
                    });
                });
            }

            function toDeletes(){
                var result = table.checkStatus('roleGrid') ;
                if(result.data.length == 0){
                    //没有选择要删除的记录
                    layer.alert('请选择要删除的记录',{icon:0});
                    return ;
                }
                //选择了要删除的记录，可以删除
                layer.confirm('是否确定删除',{icon:3},function(){
                    //点击了确定
                    var rids = '' ;
                    var rows = result.data ;
                    for(var i=0;i<rows.length ;i++){
                        var row = rows[i];
                        rids += row.rid+',' ;
                    }
                    //此时结尾多了一个逗号
                    rids = rids.substring(0,rids.length-1);
                    $.post('role/deletes',{rids:rids},function(responseVO){
                        if(responseVO.code == 0){
                            layer.alert(responseVO.msg,{icon:6},function(){
                                table.reload('roleGrid');
                                layer.closeAll();
                            })
                        }else{
                            layer.alert(responseVO.msg,{icon:5});
                        }
                    },'json');
                })
            }

            function toImport(){
                $.post('view/role/import.jsp',null,function(view){
                    layer.open({
                        title:'导入角色',
                        type:1,
                        area:[400,300],
                        content:view,
                        btn:['确定','取消'],
                        yes:function(){
                            $('#actionBtn').click();
                        }
                    });
                });
            }



            //设置表单提交事件，当表单提交时，触发该事件，先表单组件验证，验证通过执行回调函数
            form.on('submit(*)',function(data){
                if(!data.field.yl1){
                    //没有这个yl1
                    data.field.yl1=2
                }

                //自定义ajax异步请求
                //实现编辑操作时，这部分代码要有所变化
                //因为添加和修改用的是同一个模板
                //点击同一个按钮发送不同的请求，根据rid区分
                var url ;
                if(data.field.rid){
                    //修改
                    url="role/update"
                }else{
                    //保存
                    url="role/save";
                    //为了避免后端获取到rid="" 无法转换成long的rid。
                    //我们可以移除这个rid参数
                    //也可以给rid赋予一个数字的值（反正后端也不用）
                    delete data.field.rid ;
                }

                $.post(url,data.field,function(responseVO){
                    var code = responseVO.code ;
                    var msg = responseVO.msg ;
                    if(code == 0){
                        //操作成功，给出提示，带有图标，确定后关闭窗口，刷新表格
                        layer.alert(msg,{icon:6},function(){
                            //点击提示框的确定按钮
                            layer.closeAll();
                            table.reload('roleGrid');
                        })
                    }else{
                        //操作失败，给出提示，带有图标
                        layer.alert(msg,{icon:5});
                    }
                },'json');

                //终止表单提交 （默认是同步的）
                return false ;
            });

            table.on('tool(roleGrid)',function(obj){
                //obj.data就是当前点击按钮所在行的行数据
                switch(obj.event){
                    case "toEdit":  toEdit(obj.data.rid); break ;
                    case "toDelete":toDelete(obj.data.rid);break ;
                    case "toDistribution" : toDistribution(obj.data.rid);break ;
                }
            });

            function toEdit(rid){
                $.post('role/edit',{rid:rid},function(role){

                    $.post('view/role/add.jsp',null,function(view){
                        layer.open({
                            title:'角色编辑', //设置标题
                            type:'1',        //设置弹出层类型（异步的，两个网页在一个窗口中，可以互相访问）
                            area:[400,350],  //设置窗口大小的
                            content:view,
                            success:function(){//弹出层展示完毕时，回调的函数
                                //将原始数据显示在表单中，处理yl1
                                form.val('roleAddForm',role);

                                if(role.yl1 == 2){
                                    //禁用，删除<input>复选框中的checked属性
                                    $('#yl1').prop('checked',false);
                                }

                                form.render(); //更新全部
                            },
                            btn:['确定','取消'],
                            yes:function(){
                                //点击确定按钮时触发事件
                                //触发表单提交
                                //原来我们都是用鼠标点击按钮，现在等于用代码点击按钮。
                                $('#roleSubmitBtn').click();
                            },
                            btn2:function(){
                                //点击取消按钮时触发的事件，未来还有btn3,btn4....
                                alert('取消')
                            }
                        });
                    });

                },'json')
            }

            function toDelete(rid){
                layer.confirm('是否确认删除',{icon:3},function(){
                    //点击确定按钮后的操作

                    $.post('role/delete',{'rid':rid},function(responseVO){
                        if(responseVO.code == 0){
                            layer.alert(responseVO.msg,{icon:6},function(){
                                table.reload('roleGrid');
                                layer.closeAll();
                            });
                        }else{
                            layer.alert(responseVO.msg,{icon:5});
                        }
                    },'json');

                })

            }

            function toDistribution(rid){
                //先后端查询所需要的记录
                //当前角色信息
                //所有的功能信息（含，上一次分配的状态，默认勾选状态）
                var role  ;
                var funs  ;
                var view ;
                var tt ;
                $.ajax({
                    async:false,
                    url:'auth/distributionInfoForFun',
                    type:'post',
                    data:{'rid':rid},
                    dataType:'json',
                    success:function(obj){
                        role = obj.role ;
                        funs = obj.funs ;
                    }
                });

                //根据获取的数据实现页面设计与展示
                $.ajax({
                    async:false,
                    url:'view/role/distribution.jsp',
                    type:'get',
                    success:function($view){
                        view = $view ;
                    }
                });

                layer.open({
                    title:'分配功能',
                    type:1,
                    area:[800,600],
                    content:view,
                    btn:['确定分配','取消'],
                    yes:function(){
                        distributionSubmit();
                    },
                    success:function(){
                        //装载数据
                        funTableInit();
                    }
                });

                function funTableInit(){
                    $('#distribution_rid').val(role.rid);
                    $('#distribution_rname').val(role.rname);

                    tt = treeTable.render({
                        elem:'#funGrid',
                        height:'300',
                        data:funs,
                        toolbar:'#toolbar',
                        cols:[[
                            {type:'checkbox',width:'10%'},
                            {title:'编号',field:'fid',width:'10%'},
                            {title:'名称',field:'fname',width:'60%'},
                            {title:'类别',field:'ftype',templet:'#ftypeColumn',width:'20%'}
                        ]],
                        tree:{
                            isPidData:true,
                            idName:'fid',
                            pidName:'pid',
                            iconIndex:2
                        }
                    });

                    treeTable.on('toolbar(funGrid)',function(obj){
                        switch(obj.event){
                            case "toExpand" : toExpand();break ;
                            case "toFold" : toFold();break ;
                        }
                    });

                    function toExpand(){
                        tt.expandAll();
                    }

                    function toFold(){
                        tt.foldAll();
                    }

                }

                function distributionSubmit(){
                    //将所有选中的功能id，组成字符串"1,2,3,4,5"
                    var rows = tt.checkStatus();
                    var fidStr = '' ;
                    for(var i=0;i<rows.length;i++){
                        var row = rows[i];
                        fidStr+=row.fid+',';
                    }
                    $.post('auth/distributionFun',{'rid':rid,'fidStr':fidStr},function(responseVO){
                        var code = responseVO.code ;
                        var msg = responseVO.msg ;
                        if(code == 0){
                            layer.alert(msg,{icon:6},function(){
                                layer.closeAll();
                            });
                        }else{
                            layer.alert(msg,{icon:5});
                        }
                    },'json');
                }

            }


        });


        function dateFormat(d){
           var date = new Date(d.create_time);

           var year = date.getFullYear() ;
           var month = date.getMonth()+1 ;
           var day = date.getDate() ;
           month = month<10?'0'+month:month ;
           day = day<10?'0'+day:day ;

           return year+"-"+month+"-"+day ;
        }

        function toQuery(){
            layui.use(['jquery','table'],function(){
                var $ = layui.$ ;
                var table = layui.table ;

                var rname = $('#rname').val();

                table.reload('roleGrid',{
                    where:{rname:rname}
                    ,done:function(){
                        $('#rname').val(rname);
                    }
                });
            });
        }


    </script>
</html>