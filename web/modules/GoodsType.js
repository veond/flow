//Ext.Loader.setConfig({enabled: true});

Ext.Loader.setPath('Ext.ux', '/extjs/ux/');
Ext.define('Cosmetics.GoodsType', {
    extend: 'Ext.ux.desktop.Module',

    requires: [
        
    ],

    id:'goodstype',

    init : function(){
        this.launcher = {
            text: '商品类型管理',
            iconCls:'notepad',
            handler : this.createWindow,
            scope: this
        };
    },
    
    //添加用户的form表单
    createadUserForm:function(){
    	var adduserform=Ext.create('Ext.form.Panel',{
			title:'添加商品类型',
    		defaultType:'textfield',
    		items:[{
    			id:'goodstype',
    			name:'goodstype',
    			fieldLabel:'会员类型',
    			anchor:'100%',
    			allowBlank:false
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
    				var goodstype=f.findField('goodstype').getValue();
    				Ext.Ajax.request({
    					url:'/cosmetics/addgoodstype.do',
    					method:'POST',
    					params:{
    						goodstype:goodstype
    					},
    					success:function(response,options){
    						var result=response.responseText;
    						if(result=='addok\r\n'){
    							Ext.Msg.alert('正确','添加商品类型成功！');
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
		Ext.define('TypeModel',{
			extend: 'Ext.data.Model',
			fields:[
			    'typeId',
			    'goodstype'
			]
		});
		
		//创建store
		var store=Ext.create('Ext.data.Store',{
			model:'TypeModel',
			pageSize:5,
			//是否在服务端排序
			remoteSort: true,
			proxy:{
				type:'ajax',
				url:'/cosmetics/findgoodstypelist.do',
				reader:{
					root:'types',
					totalProperty:'totalCount'
				}
			}
		});
		
		//定义行编辑器
		var rowEditing=Ext.create('Ext.grid.plugin.RowEditing',{
			clicksToMoveEditor:1,
			autoCancel:true,
			listeners:{
				edit:function(editor,e){
					if(editor.newValues.goodstype!=editor.originalValues.goodstype){
						Ext.Ajax.request({
							url:'/cosmetics/updategoodstype.do',
							params:{
								typeId:editor.newValues.typeId,
								goodstype:editor.newValues.goodstype
							},
							success:function(response,options){
								var result=response.responseText;
								if(result=='updateok\r\n'){
									Ext.Msg.alert('成功','修改商品类型成功！');									
								}
							},
							failure:function(response,options){
								alert(response.responseText);
							}
						});
					}
				}
			}
		});
		var smrow=Ext.create('Ext.selection.CheckboxModel');
		var grid=Ext.create('Ext.grid.Panel',{
			store:store,
			border: false,
			columnLines: true, 
			disableSelection: false,
			selModel :smrow,
			title:'商品类型维护',
			plugins:[rowEditing],
			columns:[{
				dataIndex:'typeId',
				name:'typeId',
				flex: 1,
				hidden:true
			},{
				text:'商品类型',
				width:200,
				sortable:false,
				dataIndex:'goodstype',
				editor:{
					allowBlank:false
				}
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
	        }]
		});
		store.loadPage(1);
		return grid;
    },
    createWindow : function(){
    	var desktop = this.app.getDesktop();
        var win = desktop.getWindow('goodstype');
        if(!win){
            win = desktop.createWindow({
                id: 'goodstype',
                title:'商品类型管理',
                width:400,
                height:300,
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

