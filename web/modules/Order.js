//Ext.Loader.setConfig({enabled: true});

Ext.Loader.setPath('Ext.ux', '/extjs/ux/');
function createorderWin(result,data,index){
	var memberId="";
	var membernum="";
	var membername=""
	var membertel="";
	var discount="";
	if(result=='nomember\r\n'){
		
	}else{
		var s=result.split(',');
		memberId=s[0];
		membernum=s[1];
		membername=s[2];
		membertel=s[3];
		discount=s[4];
	}
	var goodsnum="";
	var goodsname="";
	var goodsprice="0";
	var totalprice="";
	for(var i=0;i<index;i++){
		goodsnum+=data[i][0];
		goodsname+=data[i][1]+":"+data[i][3];
		goodsprice=parseFloat(goodsprice)+parseFloat(data[i][4])
		if(i<index-1){
			goodsnum+=",";
			goodsname+=",";
		}
	}
	if(discount!=""){
		var a=(parseFloat(discount)*parseFloat(goodsprice));
		totalprice=parseFloat(parseFloat(a)/10);
	}else{
		totalprice=goodsprice;
	}
	var form=Ext.create('Ext.form.Panel',{
		defaultType:'textfield',
		items:[{
			id:'membernum',
			name:'membernum',
			anchor:'100%',
			readOnly:true,
			fieldLabel:'会员卡号',
			value:membernum
		},{
			id:'membername',
			name:'membername',
			anchor:'100%',
			readOnly:true,
			fieldLabel:'会员姓名',
			value:membername
		},{
			id:'membertel',
			name:'membertel',
			anchor:'100%',
			readOnly:true,
			fieldLabel:'会员电话',
			value:membertel
		},{
			id:'discount',
			name:'discount',
			fieldLabel:'折扣',
			anchor:'100%',
			readOnly:true,
			value:discount
		},{
			id:'goodsprice',
			name:'goodsprice',
			fieldLabel:'总价格',
			value:goodsprice,
			anchor:'100%',
			readOnly:true
		},{
			id:'goodsname',
			name:'goodsname',
			fieldLabel:'商品',
			value:goodsname,
			anchor:'100%',
			readOnly:true
		},{
			id:'totalprice',
			name:'totalprice',
			fieldLabel:'折扣价格',
			value:totalprice,
			anchor:'100%',
			readOnly:true
		},{
			id:'money',
			name:'money',
			fieldLabel:'实收金额',
			anchor:'100%'
		}],
		buttons:[{
			text:'结算',
			handler:function(){
				var f=this.up('form').getForm();
				var newgoodsname=f.findField('goodsname').getValue();
				var newgoodsprice=f.findField('goodsprice').getValue()+"";
				var actualprice=f.findField('totalprice').getValue()+"";
				var money=parseFloat(f.findField('money').getValue());
				Ext.Ajax.request({
					url:'/cosmetics/addorder.do',
					method:'POST',
					params:{
						memberId:memberId,
						goodsname:newgoodsname,
						totalprice:newgoodsprice,
						discount:discount,
						actualprice:actualprice,
						orderperson:membername,
						goodsnum:goodsnum
					},
					success:function(response,options){
						var result=response.responseText;
						if(result=='addok\r\n'){
							var m=money-parseFloat(f.findField('totalprice').getValue());
							Ext.Msg.alert('成功','结算成功，找零'+m+"元");
						}
					}
				})
			}
		}]
	});
	
	var win=Ext.create('Ext.window.Window',{
 		title:'订单确认',
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
Ext.define('Cosmetics.Order', {
    extend: 'Ext.ux.desktop.Module',

    requires: [
        
    ],

    id:'order',

    init : function(){
        this.launcher = {
            text: '订单管理',
            iconCls:'notepad',
            handler : this.createWindow,
            scope: this
        };
    },
    
    createadUserForm:function(){
    	var index=0;
    	var data=new Array();
    	for(var i=0;i<10;i++){          
        	data[i]=new Array();    
          	for(var j=0;j<5;j++){     
            	data[i][j]=""; 
        	}
    	}
    	var store = Ext.create('Ext.data.ArrayStore', {
	        fields: [
	           {name: 'goodsnum'},
	           {name: 'goodsname'},
	           {name: 'goodsprice'},
	           {name: 'num'},
	           {name: 'totalprice'}
	        ],
	        data: data
	    });
    	var form=Ext.create('Ext.form.Panel',{
    		defaultType:'textfield',
    		items:[{
    			id:'goodsnum',
    			name:'goodsnum',
    			anchor:'100%',
    			fieldLabel:'商品编号'
    		},{
    			id:'num',
    			name:'num',
    			fieldLabel:'购买数量',
    			anchor:'100%',
	   			value:1,
	   			minValue:0,
	   			xtype: 'numberfield',
	   			step:1
    		}],
    		buttons:[{
    			text:'重置',
    			handler:function(){
    				index=0;
    				for(var i=0;i<10;i++){          
			        	data[i]=new Array();    
			          	for(var j=0;j<5;j++){     
			            	data[i][j]=""; 
			        	}
			    	}
			    	store.load();
    			}
    		},{
    			text:'添加',
    			handler:function(){
    				var f=this.up('form').getForm();
    				var goodsnum=f.findField('goodsnum').getValue();
    				var num=f.findField('num').getValue();
    				Ext.Ajax.request({
    					url:'/cosmetics/findgoodsbynum.do',
    					method:'POST',
    					params:{
    						goodsnum:goodsnum,
    						num:num
    					},
    					success:function(response,options){
    						var result=response.responseText;
    						if(result=='errorgoodsnum'){
    							Ext.Msg.alert('错误','商品编号错误，没有对应的商品，请重新输入！');
    							this.up('form').getForm().reset();
    							return;
    						}else{
    							var s=result.split(',');
	    						var goodsnum=s[0];
	    						var flag=false;
	    						var flagindex=0;
	   							for(var i=0;i<index;i++){
	    							var datagoodsnum=data[i][0];
	    							if(goodsnum==datagoodsnum){
	    								flag=true;
	    								flagindex=i;
	    								break;
	    							}
	    						}
	    						if(flag){
	    							data[flagindex][0]=s[0];
	    							data[flagindex][1]=s[1];
	    							data[flagindex][2]=s[2];
	    							data[flagindex][3]=parseInt(data[flagindex][3])+parseInt(s[3]);
	    							data[flagindex][4]=parseFloat(data[flagindex][4])+parseFloat(s[4]);
	    						}else{
	    							for(var j=0;j<s.length;j++){
			    						data[index][j]=s[j];
			    					}
			    					index=index+1;
	    						}
	    						store.load();
    						}
    					}
    				});
    			}
    		}]
    	});
    	var grid = Ext.create('Ext.grid.Panel', {
        	store: store,
        	stateful: true,
	        columns: [{
	                text     : '商品编号',
	                flex     : 1,
	                sortable : false,
	                dataIndex: 'goodsnum'
	            },{
	                text     : '商品名称',
	                sortable : false,
	                dataIndex: 'goodsname'
	            },{
	                text     : '单价',
	                sortable : false,
	                dataIndex: 'goodsprice'
	            },{
	                text     : '数量',
	                sortable : false,
	                dataIndex: 'num'
	            },{
	                text     : '总价格',
	                width    : 85,
	                sortable : false,
	                dataIndex: 'totalprice'
	            },{
	            	xtype: 'actioncolumn',
                	width: 50,
                	items: [{
                		icon   : '/extjs/shared/icons/fam/delete.gif',  // Use a URL in the icon config
	                    tooltip: '删除',
	                    handler: function(grid, rowIndex, colIndex) {
	                    	if(rowIndex<index){
	                    		for(var i=0;i<5;i++){
		                        	data[rowIndex][i]="";
		                        }
		                        for(var i=rowIndex;i<index;i++){
		                        	for(var j=0;j<5;j++){
		                        		data[i][j]=data[i+1][j];
		                        	}
		                        }
		                        index=index-1;
		                        store.load();
	                    	}else{
	                    		Ext.Msg.alert('错误','请选择正确的行删除！');
	                    		return;
	                    	}
	                    }
                	}]
	            }]
	    });
	    var orderpanel=Ext.create('Ext.form.Panel',{
	    	defaultType:'textfield',
	    	items:[{
	    		id:'membernum',
	    		name:'membernum',
	    		anchor:'100%',
	    		fieldLabel:'会员卡号'
	    	},{
	    		id:'membertel',
	    		name:'membertel',
	    		anchor:'100%',
	    		fieldLabel:'会员电话'
	    	}],
	    	buttons:[{
	    		text:'结算',
	    		handler:function(){
	    			if(index==0){
	    				Ext.Msg.alert('错误','请输入至少一样商品！');
	    				return;
	    			}else{
	    				var f=this.up('form').getForm();
		    			var membernum=f.findField('membernum').getValue();
		    			var membertel=f.findField('membertel').getValue();
		    			Ext.Ajax.request({
		    				url:'/cosmetics/quermemberbynumortel.do',
		    				params:{
		    					membernum:membernum,
		    					membertel:membertel
		    				},
		    				success:function(response,options){
		    					var result=response.responseText;
		    					createorderWin(result,data,index);
		    				}
		    			});
	    			}
	    		}
	    	}]
	    });
    	var panel=Ext.create('Ext.panel.Panel',{
    		title:'购买商品',
    		items:[{
    			items:[form]
    		},{
    			items:[grid]
    		},{
    			items:[orderpanel]
    		}]
    	})
		return panel;
    },
    
    createlevelGrid:function(){
    	//创建model
		Ext.define('GoodsModel',{
			extend: 'Ext.data.Model',
			fields:[
				'goodsId',
			    'typeId',
			    'supplyid',
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
	        			var goodsname=record[0].get('goodsname');
	        			var price=record[0].get('price');
	        			var num=record[0].get('num');
	        			var producers=record[0].get('producers');
	        			var remark=record[0].get('remark');
	        			createUpdateForm(goodsId,typeId,supplyid,goodsname,price,num,producers,remark);
	        		}
	        	}
	        }]
		});
		store.loadPage(1);
		return grid;
    },
    createWindow : function(){
    	var desktop = this.app.getDesktop();
        var win = desktop.getWindow('order');
        if(!win){
            win = desktop.createWindow({
                id: 'order',
                title:'订单管理',
                width:700,
                height:600,
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

