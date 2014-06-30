//角色管理 module

var grid;
var store;

Ext.Loader.setPath('Ext.ux', 'extjs/ux/');
Ext.require([
    'Ext.data.*',
    'Ext.grid.*',
    'Ext.util.*',
    'Ext.tip.QuickTipManager',
    'Ext.ux.data.PagingMemoryProxy',
    'Ext.ux.ProgressBarPager'
]);

Ext.define('modules.my.organization.Role_old', {   //如果这里的名称是 "modules目录名.文件名"  的话， 那么，在页面中就不需要再引入 module的js,  不然要单独引入 如："modules/Department.js"
    extend: 'Ext.ux.desktop.Module',

    //此module 唯一标识
    id:'roleOldModule',

    //初始化    (extjs 默认调用名)
    init : function(){
        this.launcher = {
            text: '角色管理',
            iconCls:'roleOldCls',
            handler : this.createWindow,
            scope: this
        }
    },
   
    //角色信息列表创建
    createroleOldGrid:function() {
    	// example of custom renderer function
        function change(val){
            if(val > 0){
                return '<span style="color:green;">' + val + '</span>';
            }else if(val < 0){
                return '<span style="color:red;">' + val + '</span>';
            }
            return val;
        }

        // example of custom renderer function
        function pctChange(val){
            if(val > 0){
                return '<span style="color:green;">' + val + '%</span>';
            }else if(val < 0){
                return '<span style="color:red;">' + val + '%</span>';
            }
            return val;
        }

    	
    	//1、创建一个 显示类型的样式
		Ext.define('model', {
			extend: 'Ext.data.Model',
			idProperty: 'id',
			fields: [
			   {name: 'id'},
			   {name: 'name'},  // 这里里面参数  type: 'float'  表示，此字段类型为 float ， 也可以 date  等等， 不写就是字符串
			   {name: 'updateAtStr'},
			   {name: 'createAtStr'},
			   {name: 'remark'}
			]        
		});
    	//2、创建 data store,  这个 store 需要提到外面定义，这样方便点击是加载
		store = Ext.create('Ext.data.Store', {
	        model: 'model',
	        remoteSort: true,
	        pageSize: 10,  //每页数量
	        proxy: {
	            type: 'ajax',
	            url: 'organization/role/getPageInfo.html',
	            reader: {  //返回JSON的数据格式 对应 值
	                root: 'items',   //列表的 list 对应JSON中的  items
	                totalProperty: 'totalCount'   //总条数对应JSON中的  totalCount
	            },
	            // sends single sort as multi parameter(单点发送，多参数) ---- ->??? 不太明白！   2013-11-29
	            simpleSortMode: true
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
	        		var roleOldModel = {id:editor.newValues.id, name:editor.newValues.name,remark:editor.newValues.remark};
	        		Ext.Ajax.request({
				        url: 'organization/role/addOrUpdate.html',
				        method:'POST',
				        params: JSON.stringify(roleOldModel),
				        headers:{
				           'Content-Type': 'application/json; charset=utf-8'
				        },
				        success: function(form, action){
				        	var resObj = Ext.JSON.decode(form.responseText,false);  //编译一下服务器返回的json 字符串
				        	if(resObj.isSuccess) {
				        		store.load();  //刷新树
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
	        		var sm = grid.getSelectionModel();
	        		if(sm.getSelection()[0].data.id == "") {
	        			store.remove(sm.getSelection());
	        		}else {
	        			store.load();
	        		}
	        	}
	        }
	    });
		
	    // create the Grid
	    grid = Ext.create('Ext.grid.Panel', {
	        store: store,
            multiSelect: true,  //可以多选择
            columnLines:true,  //列的线显示
            columns: [{
                    text: '角色ID',
                    sortable: false,//此列不可以排序
                    dataIndex: 'id',
                    width: 240,
                    hidden:true  //隐藏此列
                },{
                    text: '角色名',
                    sortable: true,
                    dataIndex: 'name',
                    flex: 1,  //灵活适应大小的意思
                    editor: {
                    	xtype: 'textfield',   //输入框类型  （这里的值可以是 email、 钱、 等等 ， ext 会自动帮忙验证）
                        allowBlank: false,  //是否允许空白 (否)
                        blankText:"角色名不能为空"
                    }
                },{
                    text: '更新时间',
                    sortable: true,//可排序
                    dataIndex: 'updateAtStr',
                    flex: 1
                },{
                    text: '创建时间',
                    sortable: true,//可排序
                    dataIndex: 'createAtStr',
                    flex: 1
                },{
                    text: '备注',
                    sortable: true,//可排序
                    dataIndex: 'remark',
                    flex: 1,
                    editor: {
                    	xtype: 'textfield',   //输入框类型  （这里的值可以是 email、 钱、 等等 ， ext 会自动帮忙验证）
                        allowBlank: true
                    }
                }],
	        stripeRows: true,
	        width:788,
			height:370,
	        frame:true,
	        //顶部导航栏目
            tbar: [{
            	itemId:'addUserButId',
                text: '增加',
                iconCls: 'employee-add',
                handler : function() {
                	rowEditing.cancelEdit();  //取消所有编辑状态
                    // Create a model instance
                    var r = Ext.create('model', {
                    	id: '',
                    	name: '',
                    	updateAt:'',
                    	createAt:'',
                    	remark:''
                    });
                    store.insert(0, r);  //将新行插入到 grid 中第一行的位置
                    rowEditing.startEdit(0, 0);  //将此行改成编辑状态
                }
            	}, {
                    itemId: 'removeButId',
                    text: '删除',
                    iconCls: 'employee-remove',
                    handler: function() {
                        var sm = grid.getSelectionModel();  //获选择中的行
                        rowEditing.cancelEdit();
                        //将选中行的 ID 拼装成数组发送到后台进行删除
                        var smArray = sm.getSelection();
                        var delIdArray = new Array();
                        for(var i=0;i<smArray.length;i++) {
                        	delIdArray.push(smArray[i].data.id);
                        }
                        Ext.Ajax.request({
    				        url: 'organization/role/batDelete.html',
    				        method:'POST',
    				        params: JSON.stringify(delIdArray),
    				        headers:{
    				           'Content-Type': 'application/json; charset=utf-8'
    				        },
    				        success: function(form, action){
    				        	var resObj = Ext.JSON.decode(form.responseText,false);  //编译一下服务器返回的json 字符串
    				        	if(resObj.isSuccess) {
    				        		store.load();  //刷新树
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
                        if (store.getCount() > 0) {
                            sm.select(0);
                        }
                    },
                    disabled: true
                }],
            plugins: [rowEditing],
            listeners: {
                'selectionchange': function(view, records) {
                    grid.down('#removeButId').setDisabled(!records.length);
                }
            },
	        bbar: Ext.create('Ext.PagingToolbar', {
	            pageSize: 10,
	            store: store,
	            displayInfo: true,
	            plugins: Ext.create('Ext.ux.ProgressBarPager', {})
	        })
	    });
	    store.load();
	    return grid;
    },
	    
    //显示此窗口的方法    (extjs 默认调用名) 
    createWindow : function(){
        var desktop = this.app.getDesktop();
        var win = desktop.getWindow('roleOldModule');
        if(!win){  //查看此窗口是否存在，如果不存在，就创建出来，否则直接让他显示
            win = desktop.createWindow({
                id: 'roleOldModule',
                title:'角色管理',
                width:800,
                height:408,
                iconCls: 'notepad',
                animCollapse:false,
                border: false,
                hideMode: 'offsets',
                items: [  //本窗口里面的元素
                      {items:[this.createroleOldGrid()]}
                ]
            });
        }
        win.show();
        return win;
    }   
   
});

