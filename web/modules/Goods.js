//Ext.Loader.setConfig({enabled: true});

Ext.Loader.setPath('Ext.ux', '/extjs/ux/');

function createUpdateForm(goodsId,typeId,supplyid,goodsnum,goodsname,price,num,producers,remark){
   	var form=Ext.create('Ext.form.Panel',{
		title:'添加商品',
   		defaultType:'textfield',
   		items:[{
   			id:'typeId',
   			name:'typeId',
   			fieldLabel:'商品类型',
   			xtype:'combobox',
   			anchor:'100%',
   			queryMode: 'local',
   			displayField: 'goodstype',
   			valueField:'typeId',
   			value:typeId,
   			emptyText: '请选择',
   			store: new Ext.data.Store({
   				singleton : true, 
   				proxy: {
   					type: 'ajax',
   					url : '/cosmetics/querytypeforCombobox.do', 
   					actionMethods : 'post',
   					reader: {
   						root:'types',
						totalProperty:'totalCount'
   					}
   				}, 
   				fields:['typeId', 'goodstype'], 
					autoLoad:true 
		    })
   		},{
   			id:'supplyid',
   			name:'supplyid',
   			fieldLabel:'供应商',
   			xtype:'combobox',
   			anchor:'100%',
   			queryMode: 'local',
   			displayField: 'supplyname',
   			valueField:'supplyId',
   			emptyText: '请选择',
   			value:supplyid,
   			store: new Ext.data.Store({
   				singleton : true, 
   				proxy: {
   					type: 'ajax',
   					url : '/cosmetics/querySupplyList.do', 
   					actionMethods : 'post',
   					reader: {
   						root:'supplys',
						totalProperty:'totalCount'
   					}
   				}, 
   				fields:['supplyId', 'supplyname'], 
					autoLoad:true 
		    })
   		},{
   			id:'goodsnum',
   			name:'goodsnum',
   			fieldLabel:'商品编号',
   			anchor:'100%',
   			value:goodsnum,
   			allowBlank:false
    	},{
   			id:'goodsname',
   			name:'goodsname',
   			fieldLabel:'商品名称',
   			anchor:'100%',
   			value:goodsname,
   			allowBlank:false
   		},{
   			id:'price',
   			name:'price',
   			fieldLabel:'单价',
   			anchor:'100%',
   			value:price,
   			xtype: 'numberfield',
   			step:0.1
   		},{
   			id:'num',
   			name:'num',
   			fieldLabel:'数量',
   			anchor:'100%',
   			value:num,
   			xtype: 'numberfield',
   			step:1
   		},{
   			id:'producers',
   			name:'producers',
   			fieldLabel:'生产商',
   			value:producers,
   			anchor:'100%'
   		},{
   			id:'remark',
   			name:'remark',
   			xtype:'textarea',
   			value:remark,
   			fieldLabel:'备注',
   			anchor:'100% 50%'
   		}],
   		buttons:[{
   			xtype:'button',
   			text:'修改',
   			anchor:'30%',
   			handler:function(){
   				var f=this.up('form').getForm();
   				typeId=f.findField('typeId').getValue();
   				supplyid=f.findField('supplyid').getValue();
   				goodsnum=f.findField('goodsnum').getValue();
   				goodsname=f.findField('goodsname').getValue();
   				price=f.findField('price').getValue();
   				num=f.findField('num').getValue();
   				producers=f.findField('producers').getValue();
   				remark=f.findField('remark').getValue();
   				Ext.Ajax.request({
   					url:'/cosmetics/updategoods.do',
   					method:'POST',
   					params:{
   						goodsId:goodsId,
   						typeId:typeId,
   						supplyid:supplyid,
   						goodsnum:goodsnum,
   						goodsname:goodsname,
   						price:price,
   						num:num,
   						producers:producers,
   						remark:remark
   					},
   					success:function(response,options){
   						var result=response.responseText;
   						if(result=='updateok\r\n'){
   							Ext.Msg.alert('正确','商品信息修改成功，请关闭后刷新查看！');
   						}
   					}
   				});
   			}
   		}]
   	});
    
    var win=Ext.create('Ext.window.Window',{
 		title:'商品信息修改',
 		width:650,
        height:400,
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

Ext.define('Cosmetics.Goods', {
    extend: 'Ext.ux.desktop.Module',

    requires: [
        
    ],

    id:'goods',

    init : function(){
        this.launcher = {
            text: '商品管理',
            iconCls:'notepad',
            handler : this.createWindow,
            scope: this
        };
    },
    
    //添加用户的form表单
    createadUserForm:function(){
    	var adduserform=Ext.create('Ext.form.Panel',{
			title:'添加商品',
    		defaultType:'textfield',
    		items:[{
    			id:'typeId',
    			name:'typeId',
    			fieldLabel:'商品类型',
    			xtype:'combobox',
    			anchor:'100%',
    			queryMode: 'local',
    			displayField: 'goodstype',
    			valueField:'typeId',
    			emptyText: '请选择',
    			store: new Ext.data.Store({
    				singleton : true, 
    				proxy: {
    					type: 'ajax',
    					url : '/cosmetics/querytypeforCombobox.do', 
    					actionMethods : 'post',
    					reader: {
    						root:'types',
							totalProperty:'totalCount'
    					}
    				}, 
    				fields:['typeId', 'goodstype'], 
 					autoLoad:true 
			    })
    		},{
    			id:'supplyid',
    			name:'supplyid',
    			fieldLabel:'供应商',
    			xtype:'combobox',
    			anchor:'100%',
    			queryMode: 'local',
    			displayField: 'supplyname',
    			valueField:'supplyId',
    			emptyText: '请选择',
    			store: new Ext.data.Store({
    				singleton : true, 
    				proxy: {
    					type: 'ajax',
    					url : '/cosmetics/querySupplyList.do', 
    					actionMethods : 'post',
    					reader: {
    						root:'supplys',
							totalProperty:'totalCount'
    					}
    				}, 
    				fields:['supplyId', 'supplyname'], 
 					autoLoad:true 
			    })
    		},{
    			id:'goodsnum',
    			name:'goodsnum',
    			fieldLabel:'商品编号',
    			anchor:'100%',
    			allowBlank:false
    		},{
    			id:'goodsname',
    			name:'goodsname',
    			fieldLabel:'商品名称',
    			anchor:'100%',
    			allowBlank:false
    		},{
    			id:'price',
    			name:'price',
    			fieldLabel:'单价',
    			anchor:'100%',
    			xtype: 'numberfield',
    			minValue:0,
    			value:0,
    			step:0.1
    		},{
    			id:'num',
    			name:'num',
    			fieldLabel:'数量',
    			anchor:'100%',
    			xtype: 'numberfield',
    			minValue:0,
    			value:1,
    			step:1
    		},{
    			id:'producers',
    			name:'producers',
    			fieldLabel:'生产商',
    			anchor:'100%'
    		},{
    			id:'remark',
    			name:'remark',
    			xtype:'textarea',
    			fieldLabel:'备注',
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
    				var typeId=f.findField('typeId').getValue();
    				var supplyid=f.findField('supplyid').getValue();
    				var goodsnum=f.findField('goodsnum').getValue();
    				var goodsname=f.findField('goodsname').getValue();
    				var price=f.findField('price').getValue();
    				var num=f.findField('num').getValue();
    				var producers=f.findField('producers').getValue();
    				var remark=f.findField('remark').getValue();
    				Ext.Ajax.request({
    					url:'/cosmetics/addgoods.do',
    					method:'POST',
    					params:{
    						typeId:typeId,
    						supplyid:supplyid,
    						goodsname:goodsname,
    						goodsnum:goodsnum,
    						price:price,
    						num:num,
    						producers:producers,
    						remark:remark
    					},
    					success:function(response,options){
    						var result=response.responseText;
    						if(result=='addok\r\n'){
    							Ext.Msg.alert('正确','添加商品成功！');
	            				f.reset();
    						}
    					}
    				});
    			}
    		}]
    	});
    	return adduserform;
    },
    createlevelGrid:function(){
    	//创建model
		Ext.define('GoodsModel',{
			extend: 'Ext.data.Model',
			fields:[
				'goodsId',
			    'typeId',
			    'supplyid',
			    'goodsnum',
			    'goodsname',
			    'price',
			    'num',
			    'producers',
			    'createtime',
			    'remark',
			    'goodstype',
			    'supplyname'
			]
		});
		
		//创建store
		var store=Ext.create('Ext.data.Store',{
			model:'GoodsModel',
			pageSize:5,
			//是否在服务端排序
			remoteSort: true,
			proxy:{
				type:'ajax',
				url:'/cosmetics/querygoodsList.do',
				reader:{
					root:'goods',
					totalProperty:'totalCount'
				}
			}
		});
		
		var grid=Ext.create('Ext.grid.Panel',{
			store:store,
			border: false,
			columnLines: true, 
			disableSelection: false,
			title:'商品维护',
			plugins:[{
				ptype: 'rowexpander',
	            rowBodyTpl : [
	                '<p><b>生产商:</b> {producers}</p>',
	                '<p><b>备注:</b> {remark}</p>'
	            ]
			}],
			columns:[{
				dataIndex:'goodsId',
				name:'goodsId',
				flex: 1,
				hidden:true
			},{
				dataIndex:'typeId',
				name:'typeId',
				hidden:true
			},{
				dataIndex:'supplyid',
				name:'supplyid',
				hidden:true
			},{
				text:'商品编号',
				sortable:false,
				dataIndex:'goodsnum'
			},{
				text:'商品名称',
				sortable:false,
				dataIndex:'goodsname'
			},{
				text:'商品单价',
				sortable:false,
				dataIndex:'price'
			},{
				text:'商品数量',
				sortable:false,
				dataIndex:'num'
			},{
				text:'入库时间',
				sortable:false,
				dataIndex:'createtime'
			},{
				text:'商品类型',
				sortable:false,
				dataIndex:'goodstype'
			},{
				text:'供应商',
				sortable:false,
				dataIndex:'supplyname'
			}],
			bbar: Ext.create('Ext.PagingToolbar', { 
	            store: store, 
	            displayInfo: true, 
	            displayMsg: '显示 {0} - {1} 条，共计 {2} 条', 
	            emptyMsg: "没有数据" 
	        }),
	        tbar:[{
	        	width:200,
	        	fieldLabel:'搜素',
	        	xtype:'searchfield',
	        	labelWidth:50,
	        	store: store 
	        },{
	        	width:100,
	        	xtype:'button',
	        	text:'删除',
	        	handler:function(){
	        		var record=grid.getSelectionModel().getSelection();
	        		if(record.length==0){
	        			Ext.Msg.alert('错误','请选择一条需要删除的记录!');
	        		}else{
	        			var goodsId=record[0].get('goodsId');
	        			Ext.Ajax.request({
	        				url:'/cosmetics/deletegoods.do',
	        				method:'POST',
	        				params:{
	        					goodsId:goodsId
	        				},
	        				success:function(response,options){
	        					var result=response.responseText;
	        					if(result=='deleteok\r\n'){
	        						Ext.Msg.alert('成功','删除商品信息成功！');
	        						store.load();
	        					}
	        				}
	        			});
	        		}
	        	}
	        },{
	        	xtype:'button',
	        	text:'修改',
	        	width:100,
	        	handler:function(){
	        		var record=grid.getSelectionModel().getSelection();
	        		if(record.length==0){
	        			Ext.Msg.alert('错误','请选择一条需要修改的记录!');
	        		}else{
	        			var goodsId=record[0].get('goodsId');
	        			var typeId=record[0].get('typeId');
	        			var supplyid=record[0].get('supplyid');
	        			var goodsnum=record[0].get('goodsnum');
	        			var goodsname=record[0].get('goodsname');
	        			var price=record[0].get('price');
	        			var num=record[0].get('num');
	        			var producers=record[0].get('producers');
	        			var remark=record[0].get('remark');
	        			createUpdateForm(goodsId,typeId,supplyid,goodsnum,goodsname,price,num,producers,remark);
	        		}
	        	}
	        }]
		});
		store.loadPage(1);
		return grid;
    },
    createWindow : function(){
    	var desktop = this.app.getDesktop();
        var win = desktop.getWindow('goods');
        if(!win){
            win = desktop.createWindow({
                id: 'goods',
                title:'商品管理',
                width:700,
                height:400,
                iconCls: 'notepad',
                animCollapse:false,
                border: false,
                hideMode: 'offsets',
                layout:'accordion',
				items:[this.createadUserForm(),this.createlevelGrid()]
            });
        }
        win.show();
        return win;
    }
});

