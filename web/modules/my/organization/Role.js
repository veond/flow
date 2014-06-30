//角色管理 module   

/*** 定义全局变量，这里定义的变量在整个页面里面是通用的 (要注意, 听说可以用 命名空间的方式解决，暂时不清楚，先这样) 现在的规则是：目录+文件名+变量名  (organization 缩写成 org) ****/
var org_Role_roleId; 
var org_Role_userGridStore; 
var org_Role_userGrid; 

//自定义一个 modules.my.organization.Role 类
Ext.define('modules.my.organization.Role', {   //如果这里的名称是 "modules目录名.文件名"  的话， 那么，在页面中就不需要再引入 module的js,  不然要单独引入 如："modules/roleModule.js"
    extend: 'Ext.ux.desktop.Module',
    //此module 唯一标识
    id:'roleModule',
    //初始化    (extjs 默认调用名)
    init : function(){
        this.launcher = {
            text: '角色管理',
            iconCls:'department',
            handler : this.createWindow,
            scope: this
        }
    },
    
    //获得角色树组件 的方法
    createRoleTree:function() {
    	var roleStore = Ext.create('Ext.data.TreeStore', {    		
	    	proxy: {
	            type: 'ajax',
	            url: 'organization/role/get_tree.html'
	        },
	        //排序方式
	        sorters: [{
	            property: 'leaf',
	            direction: 'ASC'
	        }, {
	            property: 'text',
	            direction: 'ASC'
	        }]			    	    
    	});
    	//根据上面的数据，创建出树组件
    	return Ext.create('Ext.tree.Panel', {
    	    title: '角色列表',
    	    width: 200,
    	    height: 370,
    	    frame:true,
    	    store: roleStore,
    	    rootVisible: false,
    	    useArrows: true, 
    	    listeners:{
    	    	//为每个节点绑定click事件,  通过 record 获得每个节点里面的属性值(就是服务器传过来的JSON)
    	    	itemclick:function(obj, record, item, index, e, eOpts) {
    	    		//角色点击操作
    	    		//1、将当前的点击的角色ID， 存放到一个隐藏框中
    	    		org_Role_roleId = record.data.id;
    	    		org_Role_userGridStore.load();
    	    		//2、用户 grid 中的分配按钮可用
    	    		org_Role_userGrid.down('#allotUserToRole').setDisabled(false);
    	    	}
    	    } 
    	});
    },
    
    //用户列表信息窗口
    createUserPanel:function() {
    	//1、创建一个 显示类型的样式
		Ext.define('org_Role_userGridModel', {
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
		org_Role_userGridStore = Ext.create('Ext.data.Store', {
	        model: 'org_Role_userGridModel',
	        remoteSort: true,
	        pageSize: 10,  //每页数量
	        proxy: {
	            type: 'ajax',
	            url: 'organization/user/getPageInfoByRole.html',
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
	        		Ext.apply(org_Role_userGridStore.proxy.extraParams, {roleId:org_Role_roleId});	 //需要传的role id
	        	}	        	
	        }
	    });
		//3、 创建出 grid
		org_Role_userGrid = Ext.create('Ext.grid.Panel', {
            title:'用户信息列表',
            store: org_Role_userGridStore,
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
                    flex: 1
                },{
                    text: '登录名',
                    sortable: true,//可排序
                    dataIndex: 'loginname',
                    flex: 1
                },{
                    text: '用户角色',
                    sortable: true,//可排序
                    dataIndex: 'roleName',
                    flex: 1
                },{
                    text: '所属角色',
                    sortable: true,//可排序
                    dataIndex: 'deptName',
                    flex: 1
                }],
            stripeRows: true,
            width:585,
			height:370,
            frame:true,
            //顶部导航栏目
            tbar: [{
            	itemId:'allotUserToRole',
                text: '分配用户',
                iconCls: 'employee-add',
                handler : function() {
                	//分配窗口显示
	        		var allotUserStore = Ext.create('Ext.data.TreeStore', {
				        proxy: {
				            type: 'ajax',
				            url: 'organization/user/getUserTreeInDept.html'
				        },
				        listeners:{  //在 extjs 里面，多数操作都是在监听器里面做的 （观察者模式吧） 
				        	beforeload:function( store, operation, eOpts){  //在数据加载之前
				        		//在数据加载之前，自己需要向后台传送的数据, 加在这里
				        		Ext.apply(allotUserStore.proxy.extraParams, {roleId:org_Role_roleId});	 //需要传的role id
				        	}	        	
				        },
				        sorters: [{
				            property: 'leaf',
				            direction: 'ASC'
				        }, {
				            property: 'text',
				            direction: 'ASC'
				        }]
				    });
	        		var win;
	        		var allotUserTree=Ext.create('Ext.tree.Panel',{
	        			store: allotUserStore,
			        	rootVisible: false,
				        useArrows: true,
				        frame: true,
				        dockedItems: [{
				            xtype: 'toolbar',
				            items: {
				                text: '确定',
				                handler: function(){
				                    var records = allotUserTree.getView().getChecked();
				                    var ids = new Array();
				                    for(var i=0;i<records.length;i++){
				                    	ids.push(records[i].get('id'));
				                    }
				                    Ext.Ajax.request({
				                    	url:'organization/user/allotUserToRole.html',
				                    	method:'POST',
				                    	params:{
				                    		ids:ids,
				                    		roleId:org_Role_roleId
				                    	},
				                    	success:function(form, action){
				                    		var resObj = Ext.JSON.decode(form.responseText,false);
								        	if(resObj.isSuccess) {
								        		org_Role_userGridStore.load();
								        		win.close();
								        	}else {
									        	Ext.Msg.alert('信息', resObj.msg);
								        	}
				                    	},
				                    	failure:function(response,options){
				                    		Ext.Msg.alert('错误','服务器异常，请稍后分配！');
				                    	}
				                    });
				                }
				            }
				        }]
	        		})
	        		win=Ext.create('Ext.window.Window',{
	        			width:400,
	        			height:500,
	        			title:'选择用户',
	        			layout:'fit',
	        			items:[allotUserTree]
	        		});
	        		win.show();
                },
                disabled: true
            }, {
                itemId: 'removeButId',
                text: '删除',
                iconCls: 'employee-remove',
                handler: function() {
                    var sm = org_Role_userGrid.getSelectionModel();  //获选择中的行
                    //将选中行的 ID 拼装成数组发送到后台进行删除
                    var smArray = sm.getSelection();
                    var delIdArray = new Array();
                    for(var i=0;i<smArray.length;i++) {
                    	delIdArray.push(smArray[i].data.id);
                    }
                    Ext.Ajax.request({
				        url: 'organization/user/removeRole.html',
				        method:'POST',
				        params: JSON.stringify(delIdArray),
				        headers:{
				           'Content-Type': 'application/json; charset=utf-8'
				        },
				        success: function(form, action){
				        	var resObj = Ext.JSON.decode(form.responseText,false);  //编译一下服务器返回的json 字符串
				        	if(resObj.isSuccess) {
				        		org_Role_userGridStore.load();  //刷新树
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
                    if (org_Role_userGridStore.getCount() > 0) {
                        sm.select(0);
                    }
                },
                disabled: true
            }],
            // 分页的按钮 （最下面导航栏目）
            bbar: Ext.create('Ext.PagingToolbar', {
                store: this.userGridStore,
                displayInfo: true,
                displayMsg: '当前从第{0}到{1}条    总数 {2}条',
                emptyMsg: "无内容显示",
            }),
            listeners: {
                'selectionchange': function(view, records) {
                	org_Role_userGrid.down('#removeButId').setDisabled(!records.length);
                }
            }
        });
    	//加载第一页内容
		org_Role_userGridStore.loadPage(1);
		return org_Role_userGrid;
    },
    
    //创建此窗口的方法    (extjs 默认调用名) 
    createWindow : function(){
        var desktop = this.app.getDesktop();//拿到桌面窗口
        var win = desktop.getWindow('roleModule');  //查看此窗口是否已经存在
        if(!win){
        	//初始化 tree 和 grid
            this.roleTree = this.createRoleTree();
            this.userGrid = this.createUserPanel();
            
            win = desktop.createWindow({
                id: 'roleModule',
                title:'角色管理',
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
                  {items:[this.createRoleTree()]},
                  {items:[this.createUserPanel()]}
                ]
            });
        }
        win.show();
        return win;
    }   
   
});

