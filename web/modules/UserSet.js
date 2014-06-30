var htmlcode;
Ext.define('Cosmetics.UserSet', {
    extend: 'Ext.ux.desktop.Module',

    requires: [
        'Ext.data.TreeStore',
        'Ext.layout.container.Accordion',
        'Ext.toolbar.Spacer',
        'Ext.tree.Panel'
    ],

    id:'userset',

    init : function(){
        this.launcher = {
            text: '个人设置',
            iconCls:'notepad',
            handler : this.createWindow,
            scope: this
        };
    },
	
	createupdatepasswordPanel:function(){
		var form = Ext.create('Ext.form.Panel', {
            id:'updatepasswordPanel',
            title: '修改密码',
            lines:false,
            autoScroll:true,
            defaultType: 'textfield',
            items:[{
	        	fieldLabel: '原先密码',
	            name: 'oldsaleuserpassword',
	            inputType:'password',
	            id:'oldsaleuserpassword',
	            minLength:6,
	            maxLength:16,
	            anchor:'100%',
	            allowBlank:false
	        },{
	        	fieldLabel: '新密码',
	            name: 'saleuserpassword',
	            inputType:'password',
	            id:'saleuserpassword',
	            minLength:6,
	            maxLength:16,
	            anchor:'100%',
	            allowBlank:false
	        },{
	        	fieldLabel: '确认新密码',
	            name: 'saleuserpassword2',
	            inputType:'password',
	            id:'saleuserpassword2',
	            minLength:6,
	            maxLength:16,
	            anchor:'100%',
	            allowBlank:false
	        }],
	        buttons:[{
	        	xtype:'button',
	        	anchor:'40%',
	        	text:'重置',
	        	handler:function(){
	        		this.up('form').getForm().reset();
	        	}
	        },{
	        	text:'修改',
	        	anchor:'40%',
	        	handler:function(){
	        		var f=this.up('form').getForm();
	        		if(f.isValid()){
	        			var oldsaleuserpassword=f.findField('oldsaleuserpassword').getValue();
	            		var saleuserpassword=f.findField('saleuserpassword').getValue();
	            		var saleuserpassword2=f.findField('saleuserpassword2').getValue();
	            		if(saleuserpassword!=saleuserpassword2){
	            			Ext.Msg.alert('错误','两次输入的密码不一致！');
	            			f.reset();
	            		}else{
	            			Ext.Ajax.request({
	            				url:'/cosmetics/updateadminuserpassword.do',
	            				method:'POST',
	            				params:{
	            					oldadminpassword:oldsaleuserpassword,
	            					adminpassword:saleuserpassword
	            				},
	            				success:function(response,options){
	            					var result=response.responseText;
	            					if(result=='ok\r\n'){
	            						Ext.Msg.alert('正确','修改成功！');
	            						f.reset();
	            					}else if(result=='passworderror\r\n'){
	            						Ext.Msg.alert('错误','原先的密码输入不正确！');
	            						f.reset();
	            					}else if(result=='usererror\r\n'){
	            						Ext.Msg.alert('错误','当前网络不正常，请稍后访问!');
		        					form.reset();
	            					}
	            				},
		        				failure:function(response,options){
		        					alert(response.responseText)
		        					Ext.Msg.alert('错误','当前网络不正常，请稍后访问!');
		        					form.reset();
		        				}
	            			});
	            		}
	        		}
	        	}
	        }]
        });
        return form;
	},
	
	createupdaterealname:function(){
		var form=Ext.create('Ext.form.Panel', {
			title: '修改用户名',
            lines:false,
            autoScroll:true,
            defaultType: 'textfield',
            items:[{
            	fieldLabel: '用户名',
	            name: 'adminrealname',
	            id:'adminrealname',
	            anchor:'100%',
	            allowBlank:false
            }],
            buttons:[{
	        	xtype:'button',
	        	anchor:'40%',
	        	text:'重置',
	        	handler:function(){
	        		this.up('form').getForm().reset();
	        	}
	        },{
	        	text:'修改',
	        	anchor:'40%',
	        	handler:function(){
	        		var f=this.up('form').getForm();
	        		var adminrealname=f.findField('adminrealname').getValue();
	        		if(adminrealname==''){
	        			Ext.Msg.alert('错误','请输入用户名再提价!');
	        		}else{
	        			Ext.Ajax.request({
	            				url:'/cosmetics/updateadminrealname.do',
	            				method:'POST',
	            				params:{
	            					adminrealname:adminrealname
	            				},
	            				success:function(response,options){
	            					var result=response.responseText;
	            					if(result=='ok\r\n'){
	            						Ext.Msg.alert('正确','修改成功！');
	            						f.reset();
	            					}else if(result=='passworderror\r\n'){
	            						Ext.Msg.alert('错误','原先的密码输入不正确！');
	            						f.reset();
	            					}else if(result=='usererror\r\n'){
	            						Ext.Msg.alert('错误','当前网络不正常，请稍后访问!');
		        					form.reset();
	            					}
	            				},
		        				failure:function(response,options){
		        					alert(response.responseText)
		        					Ext.Msg.alert('错误','当前网络不正常，请稍后访问!');
		        					form.reset();
		        				}
	            			});
	        		}
	        	}
	        }]
		});
		return form;
	},
	
    createWindow : function(){
    	
        var desktop = this.app.getDesktop();
        var win = desktop.getWindow('userset');
        if(!win){
            win = desktop.createWindow({
                id: 'userset',
                title:'个人设置',
                width: 250,
                height: 350,
                iconCls: 'accordion',
                animCollapse: false,
                constrainHeader: true,
                bodyBorder: true,
                border: false,
                layout: 'accordion',
                items: [
					this.createupdatepasswordPanel(),
                    this.createupdaterealname()
				]
            });
        }
        win.show();
        return win;
    }
});

