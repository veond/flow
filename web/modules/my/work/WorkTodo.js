/***** 待办列表 工作流 *******/

Ext.define('modules.my.work.WorkTodo', {   
    extend: 'Ext.ux.desktop.Module',
    //此module 唯一标识
    id:'todoModel',
    //初始化    (extjs 默认调用名)
    init : function(){
        this.launcher = {
            text: '待办列表',
            handler : this.createWindow,
            scope: this
        }
    },
    
    //分页列表
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
        function renderTopic(value, p, record) {
        	if(record.data.status == WORK_VALUE_FLOW_STATUS_ACTIVITY) {
		        return Ext.String.format(
		            '<a href="'+baseUrl+'work/engine/item/'+record.data.workItemId+'.html" target="_blank" style="text-decoration:none;">处理</a>'
		        );
        	}else {
        		return Ext.String.format(
    				"已处理"
		        ); 
        	}
        }
    	//1、创建一个 显示类型的样式
		Ext.define('model', {
			extend: 'Ext.data.Model',
			idProperty: 'id',
			fields: [
			   {name: 'id'},
			   {name: 'workItemName'},  // 这里里面参数  type: 'float'  表示，此字段类型为 float ， 也可以 date  等等， 不写就是字符串
			   {name: 'workItemId'},
			   {name: 'updateAtStr'},
			   {name: 'createAtStr'},
			   {name: 'status'},
			   {name: 'remark'}
			]        
		});
    	//2、创建 data store, 
		var store = Ext.create('Ext.data.Store', {
	        model: 'model',
	        remoteSort: true,
	        pageSize: 10,  //每页数量
	        proxy: {
	        	actionMethods:'POST',
	            type: 'ajax',
	            url: 'work/engine/todo/pageInfo.html',
	            reader: {  //返回JSON的数据格式 对应 值
	                root: 'items',   //列表的 list 对应JSON中的  items
	                totalProperty: 'totalCount'   //总条数对应JSON中的  totalCount
	            },
	            // sends single sort as multi parameter(单点发送，多参数) ---- ->??? 不太明白！   2013-11-29
	            simpleSortMode: true
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
                    text: '待办名称',
                    sortable: true,
                    dataIndex: 'workItemName',
                    flex: 1  //灵活适应大小的意思
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
                    flex: 1
                },{
                    text: '操作',
                    sortable: true,//可排序
                    dataIndex: 'status',
                    flex: 1,
                    align: 'center',
                    renderer: renderTopic,
                    sortable: false
                }],
	        stripeRows: true,
	        width:788,
			height:370,
	        frame:true,
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
        var win = desktop.getWindow('todoModel');  //查看此窗口是否已经存在
        if(!win){
            win = desktop.createWindow({
                id: 'todoModel',
                title:'待办流程',
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

