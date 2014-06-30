//Ext.Loader.setConfig({enabled: true});

Ext.Loader.setPath('Ext.ux', '/extjs/ux/');
Ext.define('Cosmetics.Member', {
    extend: 'Ext.ux.desktop.Module',

    requires: [
        
    ],

    id:'member',

    init : function(){
        this.launcher = {
            text: '会员管理',
            iconCls:'notepad',
            handler : this.createWindow,
            scope: this
        };
    },
    //添加用户的form表单
    createaddmemberForm:function(){
    	var adduserform=Ext.create('Ext.form.Panel',{
			title:'添加会员',
    		defaultType:'textfield',
    		items:[{
    			id:'membernum',
    			name:'membernum',
    			fieldLabel:'会员卡号',
    			anchor:'100%',
    			allowBlank:false
    		},{
    			id:'membername',
    			name:'membername',
    			fieldLabel:'会员姓名',
    			anchor:'100%',
    			allowBlank:false
    		},{
    			id:'membertel',
    			name:'membertel',
    			fieldLabel:'电话',
    			anchor:'100%',
    			allowBlank:false
    		},{
    			id:'levelId',
    			name:'levelId',
    			fieldLabel:'会员级别',
    			xtype:'combobox',
    			anchor:'100%',
    			queryMode: 'local',
    			displayField: 'level',
    			valueField:'levelId',
    			emptyText: '请选择',
    			store: new Ext.data.Store({
    				singleton : true, 
    				proxy: {
    					type: 'ajax',
    					url : '/cosmetics/findlevellisttoCombo.do', 
    					actionMethods : 'post',
    					reader: {
    						root:'levels',
							totalProperty:'totalCount'
    					}
    				}, 
    				fields:['levelId', 'level'], 
 					autoLoad:true 
			    })
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
    				var membername=f.findField('membername').getValue();
    				var membertel=f.findField('membertel').getValue();
    				var levelId=f.findField('levelId').getValue();
    				Ext.Ajax.request({
    					url:'/cosmetics/addmember.do',
    					method:'POST',
    					params:{
    						membername:membername,
    						membertel:membertel,
    						levelId:levelId
    					},
    					success:function(response,options){
    						var result=response.responseText;
    						if(result=='addok\r\n'){
    							Ext.Msg.alert('正确','添加会员成功！');
	            				f.reset();
    						}
    					}
    				});
    			}
    		}]
    	});
    	return adduserform;
    },
    createMemberGrid:function(){
    	//创建model
		Ext.define('MemberModel',{
			extend: 'Ext.data.Model',
			fields:[
			    'memberId',
			    'membernum',
			    'membername',
			    'membertel',
			    'level',
			    'discount',
			    'integral'
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
				url:'/cosmetics/findmemberlist.do',
				reader:{
					root:'members',
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
					if(editor.newValues.membername!=editor.originalValues.membername || editor.newValues.membertel!=editor.originalValues.membertel ||editor.newValues.level!=editor.originalValues.level){
						Ext.Ajax.request({
							url:'/cosmetics/updatemember.do',
							method:'POST',
							params:{
								memberId:editor.newValues.memberId,
								membernum:editor.newValues.membernum,
								membername:editor.newValues.membername,
								membertel:editor.newValues.membertel,
								levelId:editor.newValues.level
							},
							success:function(response,options){
								var result=response.responseText;
	    						if(result=='updateok\r\n'){
	    							Ext.Msg.alert('成功','修改会员信息成功！');
		            				store.load();
	    						}
							}
						})
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
			title:'会员维护',
			plugins:[rowEditing],
			selModel :smrow,
			columns:[{
				dataIndex:'memberId',
				name:'memberId',
				flex: 1,
				hidden:true
			},{
				text:'会员卡号',
				dataIndex:'membernum'
			},{
				text:'会员姓名',
				anchor:'30%',
				sortable:false,
				dataIndex:'membername',
				editor:{
					allowBlank:false
				}
			},{
				text:'会员电话',
				anchor:'30%',
				dataIndex:'membertel',
				sortable:false,
				editor:{
					allowBlank:false
				}
			},{
				text:'会员级别',
				dataIndex:'level',
				editor:{
					xtype:'combobox',
					queryMode: 'local',
	    			displayField: 'level',
	    			valueField:'levelId',
	    			emptyText: '请选择',
					store: new Ext.data.Store({
    				singleton : true, 
    				proxy: {
    					type: 'ajax',
    					url : '/cosmetics/findlevellisttoCombo.do', 
    					actionMethods : 'post',
    					reader: {
    						root:'levels',
							totalProperty:'totalCount'
    					}
    				}, 
    				fields:['levelId', 'level'], 
 					autoLoad:true 
			    })
				}
			},{
				text:'折扣',
				sortable:true,
				dataIndex:'discount'
			},{
				text:'积分',
				sortable:true,
				dataIndex:'integral'
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
	        			Ext.Msg.alert('错误','请选择需要删除的会员！');
	        		}else{
	        			var memberIds="";
	        			for(var i=0;i<record.length;i++){
	        				memberIds+=record[i].get('memberId');
	        				if(i<record.length-1){
	        					memberIds+=",";
	        				}
	        			}
	        			Ext.Ajax.request({
	        				url:'/cosmetics/deletemembers.do',
	        				method:'POST',
	        				params:{
	        					memberIds:memberIds
	        				},
	        				success:function(response,options){
	        					var result=response.responseText;
	    						if(result=='deleteok\r\n'){
	    							Ext.Msg.alert('成功','删除会员信息成功！');
		            				store.load();
	    						}
	        				}
	        			})
	        		}
	        	}
	        }]
		});
		store.loadPage(1);
		return grid;
    },
    createWindow : function(){
    	var desktop = this.app.getDesktop();
        var win = desktop.getWindow('member');
        if(!win){
            win = desktop.createWindow({
                id: 'member',
                title:'会员管理',
                width:550,
                height:350,
                iconCls: 'notepad',
                animCollapse:false,
                border: false,
                hideMode: 'offsets',
                layout:'accordion',
				items:[this.createaddmemberForm(),this.createMemberGrid()]
            });
        }
        win.show();
        return win;
    }
});

