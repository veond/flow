//Ext.Loader.setConfig({enabled: true});

Ext.Loader.setPath('Ext.ux', '/extjs/ux/');
Ext.define('Cosmetics.MemberLevel', {
    extend: 'Ext.ux.desktop.Module',

    requires: [
        
    ],

    id:'memberlevel',

    init : function(){
        this.launcher = {
            text: '会员级别管理',
            iconCls:'notepad',
            handler : this.createWindow,
            scope: this
        };
    },
    
    //添加用户的form表单
    createadUserForm:function(){
    	var adduserform=Ext.create('Ext.form.Panel',{
			title:'添加会员级别',
    		defaultType:'textfield',
    		items:[{
    			id:'level',
    			name:'level',
    			fieldLabel:'会员级别名称',
    			anchor:'100%',
    			allowBlank:false
    		},{
    			id:'discount',
    			name:'discount',
    			fieldLabel:'折扣',
    			anchor:'100%',
    			xtype: 'numberfield',
    			value:9,
    			minValue:0,
    			maxValue:10,
    			step:0.1,
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
    				var level=f.findField('level').getValue();
    				var discount=f.findField('discount').getValue();
    				Ext.Ajax.request({
    					url:'/cosmetics/addmemberLevel.do',
    					method:'POST',
    					params:{
    						level:level,
    						discount:discount
    					},
    					success:function(response,options){
    						var result=response.responseText;
    						if(result=='addok\r\n'){
    							Ext.Msg.alert('正确','添加会员级别成功！');
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
		Ext.define('LeveLModel',{
			extend: 'Ext.data.Model',
			fields:[
			    'levelId',
			    'level',
			    'discount'
			]
		});
		
		//创建store
		var store=Ext.create('Ext.data.Store',{
			model:'LeveLModel',
			pageSize:5,
			//是否在服务端排序
			remoteSort: true,
			proxy:{
				type:'ajax',
				url:'/cosmetics/findlevellist.do',
				reader:{
					root:'levels',
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
					if(editor.newValues.level!=editor.originalValues.level || editor.newValues.discount!=editor.originalValues.discount){
						Ext.Ajax.request({
							url:'/cosmetics/updatelevel.do',
							params:{
								levelId:editor.newValues.levelId,
								level:editor.newValues.level,
								discount:editor.newValues.discount
							},
							success:function(response,options){
								var result=response.responseText;
								if(result=='addok\r\n'){
									Ext.Msg.alert('成功','修改会员级别成功！');									
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

		var grid=Ext.create('Ext.grid.Panel',{
			store:store,
			border: false,
			columnLines: true, 
			disableSelection: false,
			title:'会员级别维护',
			plugins:[rowEditing],
			columns:[{
				dataIndex:'levelId',
				name:'levelId',
				flex: 1,
				hidden:true
			},{
				text:'会员级别',
				anchor:'30%',
				sortable:false,
				dataIndex:'level',
				editor:{
					allowBlank:false
				}
			},{
				text:'会员折扣',
				anchor:'30%',
				sortable:true,
				dataIndex:'discount',
				editor:{
					xtype: 'numberfield',
					step:0.1,
					minValue:0,
					maxValue:10,
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
	        	width:300,
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
        var win = desktop.getWindow('memberlevel');
        if(!win){
            win = desktop.createWindow({
                id: 'memberlevel',
                title:'会员级别管理',
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

