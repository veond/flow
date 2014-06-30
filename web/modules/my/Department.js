//部门管理 module   
var store;
var userGridStore;
var userGrid; 
var moveDeptId;  //用户拖拽移动至目标的部门ID
Ext.define('modules.my.Department', {   //如果这里的名称是 "modules目录名.文件名"  的话， 那么，在页面中就不需要再引入 module的js,  不然要单独引入 如："modules/Department.js"
    extend: 'Ext.ux.desktop.Module',

    //此module 要加载的组件
    requires: [
        'Ext.form.field.HtmlEditor'
    ],

    //此module 唯一标识
    id:'department',

    //初始化    (extjs 默认调用名)
    init : function(){
        this.launcher = {
            text: '部门管理',
            iconCls:'department',
            handler : this.createWindow,
            scope: this
        }
    },
   
    //获得部门树组件
    createDeptTree:function() {
    	   	
    	store = Ext.create('Ext.data.TreeStore', {    		
	    	proxy: {
	            type: 'ajax',
	            url: 'organization/dept/get_tree.html'
            	//url: 'users.json'
	        },
	        sorters: [{
	            property: 'leaf',
	            direction: 'ASC'
	        }, {
	            property: 'text',
	            direction: 'ASC'
	        }]			    	    
    	});
    	//根据上面的数据，创建出树组件
    	var deptTree = Ext.create('Ext.tree.Panel', {
    	    title: '部门列表',
    	    width: 200,
    	    height: 370,
    	    frame:true,
    	    store: store,
    	    rootVisible: false,
    	    useArrows: true, 
    	    viewConfig: {  //让此部门树能接受拖动
	            listeners: {
                     render: function (tree) { //鼠标在此树上释放时事件
                         var dropTarget = new Ext.dd.DropTarget(tree.el, {
                             ddGroup: 'gridtotree',
                             copy: false,
                             notifyDrop: function (dragSource, event, data) {
                             	var id="";
                             	for(var i=0;i<data.records.length;i++){
                             		id+="'"+data.records[i].data.id+"'";
                             		if(i<data.records.length-1){
                             			id+=",";
                             		}
                             	}
                             	Ext.Ajax.request({
                             		url:'organization/user/updateUsersDept.html',
                             		method:'POST',
                             		params:{
                             			userIds:id,
                             			moveDeptId:moveDeptId
                             		},
                             		success: function(form, action){
							        	var resObj = Ext.JSON.decode(form.responseText,false);
							        	if(resObj.isSuccess) {
							        		//刷新树
							        		userGridStore.load();
							        	}else {
								        	Ext.Msg.alert('信息', resObj.msg);
							        	}
							        },
							        failure: function(form, action){
							        	var resObj = Ext.JSON.decode(form.responseText,false);
							        	Ext.Msg.alert('出错了', resObj.msg);
							        }
                             	});
                             	return true;
                             }
                         });
                     }
                }
	        },
    	    listeners:{
    	    	//鼠标进入树上节点的事件(为 拖拽 的功能提供服务)
    	    	itemmouseenter:function(view,record,item,index,e,eOpts ){
	        		moveDeptId=record.data.id;
	        	},
    	    	//为每个节点绑定click事件,  通过 record 获得每个节点里面的属性值(就是服务器传过来的JSON)
    	    	itemclick:function(obj, record, item, index, e, eOpts) {
    	    		//部门点击操作
    	    		//1、将当前的点击的部门ID， 存放到一个隐藏框中
    	    		document.getElementById('thisDepartmentId').value=record.data.id;
    	    		userGridStore.load();
    	    		
    	    		//2、用户 grid 中的增加按钮可用
    	    		userGrid.down('#addUserButId').setDisabled(false);
    	    	},
    	    	itemcontextmenu:function(view,record,item,index,e,eOpts ){
	        		e.preventDefault();
	        		e.stopEvent();
	        		//创建菜单
	        		var node=Ext.create('Ext.menu.Menu',{
	        			items:[{
	        				text:'添加部门',
	        				handler:function(){
	        					var parentId=record.data.id;
	        					Ext.Msg.prompt('添加部门','请输入部门名称：',function(bn,inpTxt){
	        						if(bn=='ok'){
	        							if(inpTxt==''){
	        								Ext.Msg.alert('错误','请输入部门名称！');
	        							}else{
	        								if(parentId == "") {
	        									parentId = "root";	        									
	        								}
	        								var dept = {name:inpTxt, parentId:parentId};
	        								//发送增加请求
	        								Ext.Ajax.request({
	        							        url: 'organization/dept/add.html',
	        							        method:'POST',
	        							        params: JSON.stringify(dept),
	        							        headers:{
	        							           'Content-Type': 'application/json; charset=utf-8'
	        							        },
	        							        success: function(form, action){
	        							        	var resObj = Ext.JSON.decode(form.responseText,false);
	        							        	Ext.Msg.alert('信息', resObj.msg);
	        							        	if(resObj.isSuccess) {
	        							        		//刷新树
	        							        		store.load();
	        							        	}
	        							        },
	        							        failure: function(form, action){
	        							        	var resObj = Ext.JSON.decode(form.responseText,false);
	        							        	Ext.Msg.alert('出错了', resObj.msg);
	        							        }
	        						        });
	        								
	        							}
	        						}
	        					});
	        				}
	        			},{
	        				text:'修改部门',
	        				handler:function(){
	        					var id=record.data.id;
	        					var text=record.data.text;
	        					Ext.Msg.prompt('修改部门','请输入部门名称：',function(bn,inpTxt){
	        						if(bn=='ok'){
	        							if(inpTxt==''){
	        								Ext.Msg.alert('错误','请输入部门名称！');
	        							}else{
	        								if(inpTxt!=text){
	        									var dept = {name:inpTxt, id:id};
	        									Ext.Ajax.request({
	        								        url: 'organization/dept/updateName.html',
	        								        method:'POST',
	        								        params: JSON.stringify(dept),
	        								        headers:{
	        								           'Content-Type': 'application/json; charset=utf-8'
	        								        },
	        								        success: function(form, action){
	        								        	var resObj = Ext.JSON.decode(form.responseText,false);
	        								        	Ext.Msg.alert('信息', resObj.msg);
	        								        	if(resObj.isSuccess) {
	        								        		//刷新树
	        								        		store.load();
	        								        	}
	        								        },
	        								        failure: function(form, action){
	        								        	var resObj = Ext.JSON.decode(form.responseText,false);
	        								        	Ext.Msg.alert('出错了', resObj.msg);
	        								        }
	        							        });	        									
	        								}
	        							}
	        						}
	        					});
	        				}
	        			},{
	        				text:'删除部门',
	        				handler:function(){
	        					var id=record.data.id;
	        					Ext.Msg.confirm('删除部门', '您是否确认要删除部门?',function(bn){
	        						if(bn=='yes'){
    									Ext.Ajax.request({
    								        url: 'organization/dept/delete.html?fd_id='+id,
    								        method:'POST',
    								        headers:{
    								           'Content-Type': 'application/json; charset=utf-8'
    								        },
    								        success: function(form, action){
    								        	var resObj = Ext.JSON.decode(form.responseText,false);    								        	
    								        	if(resObj.isSuccess) {
    								        		//刷新树
    								        		store.load();
    								        	}else {
    								        		Ext.Msg.alert('出错了', resObj.msg);    								        		
    								        	}
    								        },
    								        failure: function(form, action){
    								        	var resObj = Ext.JSON.decode(form.responseText,false);
    								        	Ext.Msg.alert('出错了', resObj.msg);
    								        }
    							        });	        							
	        						}
	        					});
	        				}
	        			},{
	        				xtype: 'menuseparator' //中间横线
	        			},{
	        				text:'增加用户',
	        				handler:function(){//用户增加窗口, 新开窗口
	        					var addUserWindow;	        					
	        					var addUserForm = Ext.create("Ext.form.Panel", {
	        						defaultType:'textfield', 
	        						items:[{
	        							id:'userName',
	        							name:'userName',
	        							fieldLabel:'用户名',
	        							allowBlank:false
	        						},{
	        							id:'loginName',
	        							name:'loginName',
	        							fieldLabel:'登录名',
	        							allowBlank:false
	        						}],
	        						buttons:[{
	        							text:'重置',//用户增加窗口
	        							handler:function() {
	        								this.up('form').getForm().reset();
	        							}
	        						},{
	        							text:'增加',  //用户增加窗口
	        							handler:function() {
	        								var userAddForm = this.up('form').getForm();
	        								var userName = userAddForm.findField("userName").getValue();
	        								var loginName = userAddForm.findField("loginName").getValue();
	        								var deptId = record.data.id;
	        								var userModel = {deptId:deptId,username:userName,loginname:loginName};
	        								if(userName == "" || loginName=="") {
	        									Ext.Msg.alert("ERROR","不能有空值...");
	        									return ;
	        								}
	        								Ext.Ajax.request({
        								        url: 'organization/user/addOrUpdate.html',
        								        method:'POST',
        								        params: JSON.stringify(userModel),
        								        headers:{
        								           'Content-Type': 'application/json; charset=utf-8'
        								        },
        								        success: function(form, action){
        								        	var resObj = Ext.JSON.decode(form.responseText,false);
        								        	Ext.Msg.alert('信息', resObj.msg);
        								        	if(resObj.isSuccess) {
        								        		addUserWindow.close();
        								        		store.load();  //刷新树
        								        	}
        								        },
        								        failure: function(form, action){
        								        	var resObj = Ext.JSON.decode(form.responseText,false);
        								        	Ext.Msg.alert('出错了', resObj.msg);
        								        }
        							        });
	        							}
	        						}]
	        					})	        					
	        					//将上面的panel 放在 窗口中
	        					addUserWindow= Ext.create("Ext.window.Window", {
	        						title: '增加用户',
	        					    height: 130,
	        					    width: 300,
	        					    layout: 'fit',
	        					    madol: true, 
	        					    items: addUserForm
	        					}); 
	        					addUserWindow.show(); //显示窗口
	        					
	        				}	        				
	        			}]
	        		})
	        		//让菜单展现
	        		node.showAt(e.getXY());
    	    	}
    	
    	
    	
    	    } 
    	});
    	
    	//返回树组件
    	return deptTree;
    },
    
    //用户列表信息窗口
    createUserPanel:function() {
    	//1、创建一个 显示类型的样式
		Ext.define('userGridModel', {
			extend: 'Ext.data.Model',
			idProperty: 'id',
			fields: [
			   {name: 'id'},
			   {name: 'username'},  // 这里里面参数  type: 'float'  表示，此字段类型为 float ， 也可以 date  等等， 不写就是字符串
			   {name: 'loginname'},
			   {name: 'deptName'},
			   {name: 'roleName'}
			]        
		});
    	//2、创建 data store,  这个 store 需要提到外面定义，这样方便点击是加载
		userGridStore = Ext.create('Ext.data.Store', {
	        model: 'userGridModel',
	        remoteSort: true,
	        pageSize: 10,  //每页数量
	        proxy: {
	            type: 'ajax',
	            url: 'organization/user/getPageInfo.html',
	            reader: {  //返回JSON的数据格式 对应 值
	                root: 'items',   //列表的 list 对应JSON中的  items
	                totalProperty: 'totalCount'   //总条数对应JSON中的  totalCount
	            },
	            // sends single sort as multi parameter(单点发送，多参数) ---- ->??? 不太明白！   2013-11-29
	            simpleSortMode: true
	        },
	        listeners:{  //在 extjs 里面，多数操作都是在监听器里面做的 （观察者模式吧） 
	        	beforeload:function( store, operation, eOpts){  //在数据加载之前
	        		//在数据加载之前，自己需要向后台传送的数据, 加在这里
	        		var thisDeptId = document.getElementById('thisDepartmentId').value;
	        		Ext.apply(userGridStore.proxy.extraParams, {deptId:thisDeptId});	 //需要传的dept id        		
	        	}	        	
	        }
	    });
		
		//2.2、  双击编辑功能
		var rowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
	        clicksToMoveEditor: 1,
	        autoCancel: false,
	        listeners:{
	        	//确定事件
	        	edit:function(editor, e, eOpts){
	        		//部门ID
	        		var thisDeptId = document.getElementById('thisDepartmentId').value;
	        		var userModel = {id:editor.newValues.id, deptId:thisDeptId,username:editor.newValues.username,loginname:editor.newValues.loginname};
	        		Ext.Ajax.request({
				        url: 'organization/user/addOrUpdate.html',
				        method:'POST',
				        params: JSON.stringify(userModel),
				        headers:{
				           'Content-Type': 'application/json; charset=utf-8'
				        },
				        success: function(form, action){
				        	var resObj = Ext.JSON.decode(form.responseText,false);  //编译一下服务器返回的json 字符串
				        	if(resObj.isSuccess) {
				        		userGridStore.load();  //刷新树
				        	}else {
				        		Ext.Msg.alert('信息', resObj.msg);
				        		rowEditing.startEdit(editor.rowIdx,editor.colIdx);  //将此行继续保持编辑状态
				        	}
				        },
				        failure: function(form, action){  //异常信息
				        	var resObj = Ext.JSON.decode(form.responseText,false);
				        	Ext.Msg.alert('出错了', resObj.msg);
				        }
			        });	        		
	        	},
	        	//取消事件
	        	canceledit:function(editor, e, eOpts){
	        		//当ID值没有时代表新加， 并点的取消，所以将选择中的先要移除掉
	        		var sm = userGrid.getSelectionModel();
	        		if(sm.getSelection()[0].data.id == "") {
	        			userGridStore.remove(sm.getSelection());
	        		}else {
	        			userGridStore.load();
	        		}
	        	}
	        }
	    });
				
		//3、 创建出 grid
    	userGrid = Ext.create('Ext.grid.Panel', {
            title:'用户信息列表',
            store: userGridStore,
            multiSelect: true,  //可以多选择
            columnLines:true,  //列的线显示
            columns: [{
                    text: '用户ID',
                    sortable: false,//此列不可以排序
                    dataIndex: 'id',
                    width: 240,
                    hidden:true  //隐藏此列
                },{
                    text: '用户名',
                    sortable: true,
                    dataIndex: 'username',
                    flex: 1,  //灵活适应大小的意思
                    editor: {
                    	xtype: 'textfield',   //输入框类型  （这里的值可以是 email、 钱、 等等 ， ext 会自动帮忙验证）
                        allowBlank: false,  //是否允许空白 (否)
                        blankText:"用户名不能为空"
                    }
                },{
                    text: '登录名',
                    sortable: true,//可排序
                    dataIndex: 'loginname',
                    flex: 1,
                    editor: {
                    	xtype: 'textfield',   //输入框类型  （这里的值可以是 email、 钱、 等等 ， ext 会自动帮忙验证）
                        allowBlank: false,  //是否允许空白 (否)
                        blankText:"登录名不能为空"
                    }
                },{
                    text: '用户角色',
                    sortable: true,//可排序
                    dataIndex: 'roleName',
                    flex: 1
                },{
                    text: '所属部门',
                    sortable: true,//可排序
                    dataIndex: 'deptName',
                    flex: 1
                }],
            stripeRows: true,
            width:585,
			height:370,
            frame:true,
            viewConfig: {  //让此 grid 可以进拖动
	            plugins: [
	                Ext.create('Ext.grid.plugin.DragDrop', {
	                    ddGroup: 'gridtotree',
	                    enableDrop: true
	                })
                ]
	        },
            //顶部导航栏目
            tbar: [{
            	itemId:'addUserButId',
                text: '增加',
                iconCls: 'employee-add',
                handler : function() {
                	rowEditing.cancelEdit();
                    // Create a model instance
                    var r = Ext.create('userGridModel', {
                    	id: '',
                    	username: '',
                    	loginname: ''
                    });
                    userGridStore.insert(0, r);  //将新行插入到 grid 中第一行的位置
                    rowEditing.startEdit(0, 0);  //将此行改成编辑状态
                },
                disabled: true
            }, {
                itemId: 'removeButId',
                text: '删除',
                iconCls: 'employee-remove',
                handler: function() {
                    var sm = userGrid.getSelectionModel();  //获选择中的行
                    rowEditing.cancelEdit();
                    //将选中行的 ID 拼装成数组发送到后台进行删除
                    var smArray = sm.getSelection();
                    var delIdArray = new Array();
                    for(var i=0;i<smArray.length;i++) {
                    	delIdArray.push(smArray[i].data.id);
                    }
                    Ext.Ajax.request({
				        url: 'organization/user/batDelete.html',
				        method:'POST',
				        params: JSON.stringify(delIdArray),
				        headers:{
				           'Content-Type': 'application/json; charset=utf-8'
				        },
				        success: function(form, action){
				        	var resObj = Ext.JSON.decode(form.responseText,false);  //编译一下服务器返回的json 字符串
				        	if(resObj.isSuccess) {
				        		userGridStore.load();  //刷新树
				        	}else {
				        		Ext.Msg.alert('信息', resObj.msg);
				        	}
				        },
				        failure: function(form, action){  //异常信息
				        	var resObj = Ext.JSON.decode(form.responseText,false);
				        	Ext.Msg.alert('出错了', resObj.msg);
				        }
			        });
                    //将第一行改为选中状态
                    if (userGridStore.getCount() > 0) {
                        sm.select(0);
                    }
                },
                disabled: true
            }],
            plugins: [rowEditing],
            // 分页的按钮 （最下面导航栏目）
            bbar: Ext.create('Ext.PagingToolbar', {
                store: userGridStore,
                displayInfo: true,
                displayMsg: '当前从第{0}到{1}条    总数 {2}条',
                emptyMsg: "无内容显示",
            }),
            listeners: {
                'selectionchange': function(view, records) {
                    userGrid.down('#removeButId').setDisabled(!records.length);
                }
            }
        });
    	
    	//加载第一页内容
		userGridStore.loadPage(1);
		return userGrid;
    },
    
    //显示此窗口的方法    (extjs 默认调用名) 
    createWindow : function(){
        var desktop = this.app.getDesktop();
        var win = desktop.getWindow('department');
        if(!win){
            win = desktop.createWindow({
                id: 'department',
                title:'部门管理',
                width:800,
                height:408,
                iconCls: 'notepad',
                animCollapse:false,
                border: false,
                hideMode: 'offsets',
                layout: {
                    type: 'table',
                    // The total column count must be specified here
                    columns: 2
                },
                items: [  //本窗口里面的元素
                  {items:[this.createDeptTree()]},
                  {items:[this.createUserPanel()]}
                ]
            });
        }
        win.show();
        return win;
    }   
   
});

