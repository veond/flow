//Ext.Loader.setConfig({enabled: true});

Ext.Loader.setPath('Ext.ux', '/extjs/ux/');

function createUpdateFormWin(supplyId,supplyname,suppyperson,supplytel,supplyaddress,remark){
	var win
	var form=Ext.create('Ext.form.Panel',{
   		defaultType:'textfield',
   		items:[{
   			id:'supplyname',
   			name:'supplyname',
   			fieldLabel:'供应商名称',
   			anchor:'100%',
   			value:supplyname,
   			allowBlank:false
   		},{
   			id:'suppyperson',
   			name:'suppyperson',
   			fieldLabel:'供应商联系人',
   			anchor:'100%',
   			value:suppyperson,
   			allowBlank:false
   		},{
   			id:'supplytel',
   			name:'supplytel',
   			fieldLabel:'供应商电话',
   			anchor:'100%',
   			value:supplytel,
   			allowBlank:false
   		},{
   			id:'supplyaddress',
   			name:'supplyaddress',
   			fieldLabel:'供应商地址',
   			value:supplyaddress,
   			anchor:'100%'
   		},{
   			id:'remark',
   			name:'remark',
   			fieldLabel:'备注',
   			xtype:'textarea',
   			value:remark,
   			anchor:'100% 50%'
   		}],
   		buttons:[{
   			xtype:'button',
   			text:'修改',
   			anchor:'30%',
   			handler:function(){
   				var f=this.up('form').getForm();
   				supplyname=f.findField('supplyname').getValue();
   				suppyperson=f.findField('suppyperson').getValue();
   				supplytel=f.findField('supplytel').getValue();
   				supplyaddress=f.findField('supplyaddress').getValue();
   				remark=f.findField('remark').getValue();
   				Ext.Ajax.request({
   					url:'/cosmetics/updatesupply.do',
   					method:'POST',
   					params:{
   						supplyId:supplyId,
   						supplyname:supplyname,
   						suppyperson:suppyperson,
   						supplytel:supplytel,
   						supplyaddress:supplyaddress,
   						remark:remark
   					},
   					success:function(response,options){
   						var result=response.responseText;
   						if(result=='updateok\r\n'){
   							Ext.Msg.alert('正确','供应商信息修改成功，请关闭后刷新查看！');
   						}
   					}
   				});
   			}
   		}]
    });
 	win=Ext.create('Ext.window.Window',{
 		title:'供应商信息修改',
 		width:550,
        height:350,
 		border: false,
		resizable:false,
		closable :true,
        animCollapse:false,
        hideMode: 'offsets',
        layout: 'fit',
        modal: true,
        items:[form]	
 	})
 	win.show();
}

Ext.define('Cosmetics.Supply', {
    extend: 'Ext.ux.desktop.Module',

    requires: [
		'Ext.ux.RowExpander'
    ],

    id:'supply',

    init : function(){
        this.launcher = {
            text: '供应商管理',
            iconCls:'notepad',
            handler : this.createWindow,
            scope: this
        };
    },
    //添加用户的form表单
    createaddsupplyForm:function(){
    	var adduserform=Ext.create('Ext.form.Panel',{
			title:'添加供应商',
    		defaultType:'textfield',
    		items:[{
    			id:'supplyname',
    			name:'supplyname',
    			fieldLabel:'供应商名称',
    			anchor:'100%',
    			allowBlank:false
    		},{
    			id:'suppyperson',
    			name:'suppyperson',
    			fieldLabel:'供应商联系人',
    			anchor:'100%',
    			allowBlank:false
    		},{
    			id:'supplytel',
    			name:'supplytel',
    			fieldLabel:'供应商电话',
    			anchor:'100%',
    			allowBlank:false
    		},{
    			id:'supplyaddress',
    			name:'supplyaddress',
    			fieldLabel:'供应商地址',
    			anchor:'100%'
    		},{
    			id:'remark',
    			name:'remark',
    			fieldLabel:'备注',
    			xtype:'textarea',
    			anchor:'100% 50%'
    		}],
    		buttons:[{
    			xtype:'button',
    			text:'重置',
    			anchor:'30%',
    			handler:function(){
    				this.up('form').getForm().reset();
    			}
    		},{
    			xtype:'button',
    			text:'添加',
    			anchor:'30%',
    			handler:function(){
    				var f=this.up('form').getForm();
    				var supplyname=f.findField('supplyname').getValue();
    				var suppyperson=f.findField('suppyperson').getValue();
    				var supplytel=f.findField('supplytel').getValue();
    				var supplyaddress=f.findField('supplyaddress').getValue();
    				var remark=f.findField('remark').getValue();
    				Ext.Ajax.request({
    					url:'/cosmetics/addsupply.do',
    					method:'POST',
    					params:{
    						supplyname:supplyname,
    						suppyperson:suppyperson,
    						supplytel:supplytel,
    						supplyaddress:supplyaddress,
    						remark:remark
    					},
    					success:function(response,options){
    						var result=response.responseText;
    						if(result=='addok\r\n'){
    							Ext.Msg.alert('正确','添加供应商信息成功！');
	            				f.reset();
    						}
    					}
    				});
    			}
    		}]
    	});
    	return adduserform;
    },
    createSupplyGrid:function(){
    	//创建model
		Ext.define('MemberModel',{
			extend: 'Ext.data.Model',
			fields:[
			    'supplyId',
			    'supplyname',
			    'suppyperson',
			    'supplytel',
			    'supplyaddress',
			    'remark'
			]
		});
		
		//创建store
		var store=Ext.create('Ext.data.Store',{
			model:'MemberModel',
			pageSize:5,
			//是否在服务端排序
			remoteSort: true,
			proxy:{
				type:'ajax',
				url:'/cosmetics/querySupplyList.do',
				reader:{
					root:'supplys',
					totalProperty:'totalCount'
				}
			}
		});
		
		var grid=Ext.create('Ext.grid.Panel',{
			store:store,
			border: false,
			columnLines: true, 
			disableSelection: false,
			title:'供应商信息维护',
			plugins:[{
				ptype: 'rowexpander',
	            rowBodyTpl : [
	                '<p><b>供应商地址:</b> {supplyaddress}</p>',
	                '<p><b>备注:</b> {remark}</p>'
	            ]
			}],
			collapsible: true,
        	animCollapse: false,
			//selModel :smrow,
			columns:[{
				dataIndex:'supplyId',
				name:'supplyId',
				flex: 1,
				hidden:true
			},{
				text:'供应商名称',
				anchor:'30%',
				sortable:false,
				dataIndex:'supplyname'
			},{
				text:'供应商联系人',
				anchor:'30%',
				dataIndex:'suppyperson',
				sortable:false
			},{
				text:'供应商电话',
				dataIndex:'supplytel'
			}],
			bbar: Ext.create('Ext.PagingToolbar', { 
	            store: store, 
	            displayInfo: true, 
	            displayMsg: '显示 {0} - {1} 条，共计 {2} 条', 
	            emptyMsg: "没有数据" 
	        }),
	        tbar:[{
	        	width:300,
	        	fieldLabel:'搜素',
	        	xtype:'searchfield',
	        	labelWidth:50,
	        	store: store 
	        },{
	        	xtype:'button',
	        	text:'删除',
	        	width:100,
	        	handler:function(){
	        		var record=grid.getSelectionModel().getSelection();
	        		if(record.length==0){
	        			Ext.Msg.alert('错误','请选择需要删除的供应商信息！');
	        		}else{
	        			var supplyId=record[0].get('supplyId');
	        			Ext.Ajax.request({
	        				url:'/cosmetics/deletesupply.do',
	        				method:'POST',
	        				params:{
	        					supplyId:supplyId
	        				},
	        				success:function(response,options){
	        					var result=response.responseText;
	    						if(result=='deleteok\r\n'){
	    							Ext.Msg.alert('成功','删除供应商信息成功！');
		            				store.load();
	    						}
	        				}
	        			})
	        		}
	        	}
	        },{
	        	xtype:'button',
	        	text:'修改',
	        	width:100,
	        	handler:function(){
	        		var record=grid.getSelectionModel().getSelection();
	        		if(record.length==0){
	        			Ext.Msg.alert('错误','请选择需要删除的供应商信息！');
	        		}else{
	        			var supplyId=record[0].get('supplyId');
	        			var supplyname=record[0].get('supplyname');
	        			var suppyperson=record[0].get('suppyperson');
	        			var supplytel=record[0].get('supplytel');
	        			var supplyaddress=record[0].get('supplyaddress');
	        			var remark=record[0].get('remark');
	        			createUpdateFormWin(supplyId,supplyname,suppyperson,supplytel,supplyaddress,remark);
	        		}
	        	}
	        }]
		});
		store.loadPage(1);
		return grid;
    },
    createWindow : function(){
    	var desktop = this.app.getDesktop();
        var win = desktop.getWindow('supply');
        if(!win){
            win = desktop.createWindow({
                id: 'supply',
                title:'供应商管理',
                width:550,
                height:350,
                iconCls: 'notepad',
                animCollapse:false,
                border: false,
                hideMode: 'offsets',
                layout:'accordion',
				items:[this.createaddsupplyForm(),this.createSupplyGrid()]
            });
        }
        win.show();
        return win;
    }
});

