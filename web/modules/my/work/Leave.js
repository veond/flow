/***** 请假列表 工作流 *******/

Ext.define('modules.my.work.Leave', {   
    extend: 'Ext.ux.desktop.Module',
    //此module 唯一标识
    id:'leaveModel',
    //初始化    (extjs 默认调用名)
    init : function(){
        this.launcher = {
            text: '请假流程',
            handler : this.createWindow,
            scope: this
        }
    },
    
    //请假流程分页列表
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
			   {name: 'msg'},  // 这里里面参数  type: 'float'  表示，此字段类型为 float ， 也可以 date  等等， 不写就是字符串
			   {name: 'startDate'},
			   {name: 'startDateStr_',type:'date',dateFormat:'Y-m-d H:i:s'},
			   {name: 'endDate'},
			   {name: 'endDateStr_',type:'date',dateFormat:'Y-m-d H:i:s'},
			   {name: 'leaveDay'},
			   {name: 'leaveUserId'},
			   {name: 'leaveUserName'},
			   {name: 'processInstance'},
			   {name: 'updateAtStr'},
			   {name: 'createAtStr'},
			   {name: 'remark'}
			]        
		});
    	//2、创建 data store, 
		var store = Ext.create('Ext.data.Store', {
	        model: 'model',
	        remoteSort: true,
	        pageSize: 10,  //每页数量
	        proxy: {
	            type: 'ajax',
	            url: 'work/leave/getPageInfo.html',
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
	        		var leaveObj = {id:editor.newValues.id, leaveDay:editor.newValues.leaveDay, msg:editor.newValues.msg,remark:editor.newValues.remark, startDate:editor.newValues.startDateStr_, endDate:editor.newValues.endDateStr_};
	        		Ext.Ajax.request({
				        url: 'work/leave/addOrUpdate.html',
				        method:'POST',
				        params: JSON.stringify(leaveObj),
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
                    text: 'ID',
                    sortable: false,//此列不可以排序
                    dataIndex: 'id',
                    width: 240,
                    hidden:true  //隐藏此列
                },{
                    text: '请假信息',
                    sortable: true,
                    dataIndex: 'msg',
                    flex: 1,  //灵活适应大小的意思
                    editor: {
                    	xtype: 'textfield',   //输入框类型  （这里的值可以是 email、 钱、 等等 ， ext 会自动帮忙验证）
                        allowBlank: false,  //是否允许空白 (否)
                        blankText:"请假信息不能为空"
                    }
                },{
                	xtype: 'datecolumn',
                    header: '起始日期',
                    dataIndex: 'startDateStr_',
                    width: 120,
                    renderer:Ext.util.Format.dateRenderer('Y-m-d H:i:s'),
                    editor: {
                        xtype: 'datefield',
                        allowBlank: false,
                        format: 'Y-m-d H:i:s',
                        minValue: '2014-01-01 00:00:00',
                        maxValue: '2020-01-01 00:00:00'
                    }
                },{
                	xtype: 'datecolumn',
                	header: '结束日期',
                	sortable: true,//可排序
                	dataIndex: 'endDateStr_',
                	width: 120,
                	renderer:Ext.util.Format.dateRenderer('Y-m-d H:i:s'),
                	editor: {
                		xtype: 'datefield',
                        allowBlank: false,
                        format: 'Y-m-d H:i:s',
                        minValue: '2014-01-01 00:00:00',
                    	maxValue: '2020-01-01 00:00:00'
                    }
                },{
                	text: '请假总天数',
                	sortable: true,//可排序
                	dataIndex: 'leaveDay',
                	flex: 1,
                	editor: {                    	
                    	xtype: 'numberfield',
                        allowBlank: false,
                        minValue: 0.5,
                        maxValue: 50,
                        step:0.5
                    }
                },{
                	text: '请假者ID',
                	sortable: true,//可排序
                	dataIndex: 'leaveUserId',
                	flex: 1,
                	hidden:true
                },{
                	text: '请假者名称',
                	sortable: true,//可排序
                	dataIndex: 'leaveUserName',
                	flex: 1
                },{
                	text: '关联实例ID',
                	sortable: true,//可排序
                	dataIndex: 'processInstance',
                	flex: 1
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
                    	msg: '',
                    	startDate: '',
                    	endDate: '',
                    	leaveDay: '',
                    	leaveUserId: '',
                    	leaveUserName: '',
                    	processInstance: '',
                    	updateAt:'',
                    	createAt:'',
                    	remark:''
                    });
                    store.insert(0, r);  //将新行插入到 grid 中第一行的位置
                    rowEditing.startEdit(0, 0);  //将此行改成编辑状态
                }
        	}],
            plugins: [rowEditing],
            listeners: {
                'selectionchange': function(view, records) {
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
    
    //创建此窗口的方法    (extjs 默认调用名) 
    createWindow : function(){
        var desktop = this.app.getDesktop();//拿到桌面窗口
        var win = desktop.getWindow('leaveModel');  //查看此窗口是否已经存在
        if(!win){
            win = desktop.createWindow({
                id: 'leaveModel',
                title:'请假流程',
                width:798,
                height:400,
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

