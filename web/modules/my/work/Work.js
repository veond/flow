//工作流管理 module   

Ext.define('modules.my.work.Work', {   //如果这里的名称是 "modules目录名.文件名"  的话， 那么，在页面中就不需要再引入 module的js,  不然要单独引入 如："modules/workModule.js"
    extend: 'Ext.ux.desktop.Module',
    //此module 唯一标识
    id:'workModule',
    //初始化    (extjs 默认调用名)
    init : function(){
        this.launcher = {
            text: '工作项管理',
            handler : this.createWindow,
            scope: this
        }
    },
    
    //创建此窗口的方法    (extjs 默认调用名) 
    createWindow : function(){
    	//左边模型列表
    	var workModelStore = Ext.create('Ext.data.TreeStore', {    		
	    	proxy: {
	            type: 'ajax',
	            url: 'work/engine/getModelTree.html'
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
    	var workModelList = Ext.create('Ext.tree.Panel', {
    	    title: '工作流模型列表',
    	    width: 180,
    	    height: 221,
    	    frame:true,
    	    store: workModelStore,
    	    rootVisible: false,
    	    useArrows: true 
	   });
	
    	var workItemSelectedStore = new Ext.data.Store({
			singleton : true, 
			proxy: {
				type: 'ajax',
				url : 'work/engine/getAllWorkItem_selected.html', 
				actionMethods : 'post'
			}, 
			fields:['optionValue', 'optionName'], 
			autoLoad:true 
		});
    	
    	//窗口里面的输入框
    	var addWorkItem = Ext.create("Ext.form.Panel", {
			defaultType:'textfield', 
			items:[{
				id:'itemName',
				name:'itemName',
				fieldLabel:'节点名称',
				allowBlank:false
			},{//0：开始 1： 结束 2：连接线 3：手工活动
                xtype: 'combobox',
                fieldLabel: '关联模型',
                id: 'workModel',
                name: 'workModel',
                store: new Ext.data.Store({
					singleton : true, 
					proxy: {
						type: 'ajax',
						url : 'work/engine/getAllWorkModel_selected.html', 
						actionMethods : 'post'
					}, 
					fields:['optionValue', 'optionName'], 
					autoLoad:true 
				}),
                valueField: 'optionValue',   //值对应的字段
                displayField: 'optionName',  //显示的字段
                typeAhead: true,
                queryMode: 'local',
                emptyText: '请选择节点模型..'
            },{//0：开始 1： 结束 2：连接线 3：手工活动
                xtype: 'combobox',
                fieldLabel: '节点类型',
                id: 'itemType',
                name: 'itemType',
                store: Ext.create('Ext.data.ArrayStore', {
                    fields: ['abbr', 'state'],   //下面数据的字段定义
                    data : [['0','开始'],['1','结束'],['2','连接线'],['3','手工活动']]
                }),
                valueField: 'abbr',   //值对应的字段
                displayField: 'state',  //显示的字段
                typeAhead: true,
                queryMode: 'local',
                emptyText: '请选择节点类型..'
            },{
				id:'itemCondition',
				name:'itemCondition',
				fieldLabel:'节点条件',
				allowBlank:true
			},{
				id:'partakeUser',
				name:'partakeUser',
				fieldLabel:'参与者',
				allowBlank:true
			},{
				xtype: 'combobox',
                fieldLabel: '前置节点',
                id: 'preposeItem',
                name: 'preposeItem',
                store: workItemSelectedStore,
                valueField: 'optionValue',   //值对应的字段
                displayField: 'optionName',  //显示的字段
                typeAhead: true,
                queryMode: 'local',
                emptyText: '请选择前置节点..'
			},{
				xtype: 'combobox',
                fieldLabel: '后置节点',
                id: 'postpositionItem',
                name: 'postpositionItem',
                store: workItemSelectedStore,
                valueField: 'optionValue',   //值对应的字段
                displayField: 'optionName',  //显示的字段
                typeAhead: true,
                queryMode: 'local',
                emptyText: '请选择后置节点..'
			}],
			buttons:[{
				text:'重置',//用户增加窗口
				handler:function() {
					this.up('form').getForm().reset();
				}
			},{
				text:'增加',  //用户增加窗口
				handler:function() {
					var thisForm = this.up('form').getForm();
					
					var itemName = thisForm.findField("itemName").getValue();
					var workModel = thisForm.findField("workModel").getValue();
					if(workModel == null) {
						Ext.Msg.alert("错误", "请选择工作模型");
						return false;						
					}
					var itemType = thisForm.findField("itemType").getValue();
					if(itemType == null) {
						Ext.Msg.alert("错误", "请选择工作项类型");
						return false;						
					}
					var itemCondition = thisForm.findField("itemCondition").getValue();
					var partakeUser = thisForm.findField("partakeUser").getValue();
					var preposeItem = thisForm.findField("preposeItem").getValue();
					var postpositionItem = thisForm.findField("postpositionItem").getValue();
					
					var itemModel = {name:itemName, workModelId:workModel, itemType:itemType, itemCondition:itemCondition,partakeUser:partakeUser, 
							preposeList_:[{preposeId:preposeItem}], postpositionList_:[{postpositionId:postpositionItem}]}; 
					
					Ext.Ajax.request({
				        url: 'work/engine/addOrUpdateWorkItem.html',
				        method:'POST',
				        params: JSON.stringify(itemModel),
				        headers:{
				           'Content-Type': 'application/json; charset=utf-8'
				        },
				        success: function(form, action){
				        	var resObj = Ext.JSON.decode(form.responseText,false);
				        	Ext.Msg.alert('信息', resObj.msg);
				        	if(resObj.isSuccess) {
				        		workModelStore.load();  //刷新树
				        	}
				        },
				        failure: function(form, action){
				        	var resObj = Ext.JSON.decode(form.responseText,false);
				        	Ext.Msg.alert('出错了', resObj.msg);
				        }
			        });
				}
			}]
		});
    	
        var desktop = this.app.getDesktop();//拿到桌面窗口
        var win = desktop.getWindow('workModule');  //查看此窗口是否已经存在
        if(!win){
            win = desktop.createWindow({
                id: 'workModule',
                title:'工作项增加',
                width:453,
                height:258,
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
                          {items:[workModelList]},
                          {items:[addWorkItem]}
                        ]
            });
        }
        win.show();
        return win;
    }   
   
});

