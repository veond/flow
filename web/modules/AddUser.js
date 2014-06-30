//Ext.Loader.setConfig({enabled: true});

Ext.Loader.setPath('Ext.ux', '/extjs/ux/');
var adduserform;
var usergrid;

function cretaeRoleWin(){
    	//创建model
		Ext.define('RoleModel',{
			extend: 'Ext.data.Model',
			fields:[
			    'roleId',
			    'role'
			]
		});
		
		//创建store
		var store=Ext.create('Ext.data.Store',{
			model:'RoleModel',
			pageSize:5,
			//是否在服务端排序
			remoteSort: true,
			proxy:{
				type:'ajax',
				url:'/cosmetics/findrolelist.do',
				reader:{
					root:'roles',
					totalProperty:'totalCount'
				}
			}
		});
		//定义复选框
		var smrow=Ext.create('Ext.selection.CheckboxModel');
		var rolegrid=Ext.create('Ext.grid.Panel',{
			store:store,
			border: false,
			columnLines: true, 
			disableSelection: false,
			selModel :smrow,
			columns:[{
				dataIndex:'roleId',
				name:'roleId',
				flex: 1,
				hidden:true
			},{
				text:'角色',
				anchor:'30%',
				sortable:false,
				dataIndex:'role'
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
		var win=Ext.create('Ext.window.Window',{
			title:'角色',
            width:400,
            height:300,
            border: false,
			resizable:false,
			closable :true,
            animCollapse:false,
            hideMode: 'offsets',
            layout: 'fit',
            modal: true,
            items: [rolegrid],
            close:function(){
            	var record=rolegrid.getSelectionModel().getSelection();
            	if(record.length==0){
            		Ext.Msg.alert('错误','请选择一个角色！');
        			return;
            	}else{
            		roleids="(";
            		for(var i=0;i<record.length;i++){
            			roleids+="'"+record[i].get('roleId')+"',";
            		}
            		roleids=roleids.substring(0,roleids.length-1)+")";
            		Ext.Ajax.request({
            			url:'/cosmetics/addroletoUser.do',
            			method:'POST',
            			params:{
            				adminuserId:seleceadminuserId,
            				roleId:roleids
            			},
            			success:function(response,options){
            				if(response.responseText=='addok\r\n'){
            					Ext.Msg.alert('成功','角色分配成功，请重新打开用户管理界面查看！');
            				}
            			}
            		});
            		win.hide();            		
            	}
            }
		});
		win.show();
		return true;
}

Ext.define('Cosmetics.AddUser', {
    extend: 'Ext.ux.desktop.Module',

    requires: [
        
    ],

    id:'adduser',

    init : function(){
        this.launcher = {
            text: '添加用户',
            iconCls:'notepad',
            handler : this.createWindow,
            scope: this
        };
    },
    
    //添加用户的form表单
    createadUserForm:function(){
    	var adduserform=Ext.create('Ext.form.Panel',{
    		id:'adduserformpanel',
    		defaultType:'textfield',
    		title:'添加用户',
    		items:[{
    			id:'adminusername',
    			name:'adminusername',
    			fieldLabel:'登录名',
    			anchor:'100%',
    			allowBlank:false
    		},{
    			id:'adminpassword',
    			name:'adminpassword',
    			fieldLabel:'登录密码',
    			inputType:'password',
    			anchor:'100%',
    			allowBlank:false
    		},{
    			id:'adminrealname',
    			name:'adminrealname',
    			fieldLabel:'用户姓名',
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
    				var adminusername=f.findField('adminusername').getValue();
    				var adminpassword=f.findField('adminpassword').getValue();
    				var adminrealname=f.findField('adminrealname').getValue();
    				if(f.isValid()){
    					Ext.Ajax.request({
    						url:'/cosmetics/addadminuser.do',
    						method:'POST',
    						params:{
    							adminusername:adminusername,
    							adminpassword:adminpassword,
    							adminrealname:adminrealname
    						},
    						success:function(response,options){
    							var result=response.responseText;
    							if(result=='haveuser\r\n'){
    								Ext.Msg.alert('错误','该登录用户已经存在，不能重复添加！');
    							}else if(result=='addok\r\n'){
    								Ext.Msg.alert('正确','添加成功！');
	            					f.reset();
    							}
    						}
    					});
    				}
    			}
    		}]
    	});
    	return adduserform;
    },
    //用户信息查看与维护的grid
    createuserinfoGrid:function(){
    	//创建model
		Ext.define('AdminUserModel',{
			extend: 'Ext.data.Model',
			fields:[
			    'adminuserId',
			    'adminusername',
			    'adminrealname',
			    'role'
			]
		});
		
		//创建store
		var store=Ext.create('Ext.data.Store',{
			model:'AdminUserModel',
			pageSize:10,
			//是否在服务端排序
			remoteSort: true,
			proxy:{
				type:'ajax',
				url:'/cosmetics/queryuserList.do',
				reader:{
					root:'users',
					totalProperty:'totalCount'
				}
			}
		});
		var usergrid=Ext.create('Ext.grid.Panel',{
			width:500,
			height:300,
			title:'用户管理',
			store:store,
			border: false,
			columnLines: true, 
			disableSelection: false,
			columns:[
			new Ext.grid.RowNumberer(),
			{
				dataIndex:'adminuserId',
				name:'adminuserId',
				flex: 1,
				hidden:true
			},{
				text:'登录名',
				anchor:'30%',
				sortable:false,
				dataIndex:'adminusername'
			},{
				text:'姓名',
				sortable:false,
				dataIndex:'adminrealname'
			},{
				text:'角色',
				anchor:'30%',
				sortable:false,
				dataIndex:'role'
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
	        	text:'角色分配',
	        	width:80,
	        	handler:function(){
	        		var record=usergrid.getSelectionModel().getSelection();
	        		if(record.length==0){
	        			Ext.Msg.alert('错误','请选择一个用户！');
        				return;
	        		}else{
	        			seleceadminuserId=record[0].get('adminuserId');
	        			cretaeRoleWin()
	        		}
	        	}
	        }]
		});
		store.loadPage(1);
		return usergrid;
    },
    createWindow : function(){
    	var desktop = this.app.getDesktop();
        var win = desktop.getWindow('adduser');
        if(!win){
            win = desktop.createWindow({
                id: 'adduser',
                title:'添加用户',
                width:400,
                height:300,
                iconCls: 'notepad',
                animCollapse:false,
                border: false,
                hideMode: 'offsets',
                layout:'accordion',
                items: [this.createadUserForm(),this.createuserinfoGrid()]
            });
        }
        win.show();
        return win;
    }
});

