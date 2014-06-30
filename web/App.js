/*

This file is part of Ext JS 4

Copyright (c) 2011 Sencha Inc

Contact:  http://www.sencha.com/contact

GNU General Public License Usage
This file may be used under the terms of the GNU General Public License version 3.0 as published by the Free Software Foundation and appearing in the file LICENSE included in the packaging of this file.  Please review the following information to ensure the GNU General Public License version 3.0 requirements will be met: http://www.gnu.org/copyleft/gpl.html.

If you are unsure which license is appropriate for your use, please contact the sales department at http://www.sencha.com/contact.

*/
/*!
 * Ext JS Library 4.0
 * Copyright(c) 2006-2011 Sencha Inc.
 * licensing@sencha.com
 * http://www.sencha.com/license
 */

Ext.define('MyDesktop.App', {
    extend: 'Ext.ux.desktop.App',

    //1 这里引用出自己的组件 (窗口), 然后在需要的地址显示出来 (如：桌面、快速启动栏 .....)
    requires: [
        'Ext.window.MessageBox',
        'Ext.ux.desktop.ShortcutModel',
		'BeifengBPM.Notepad',
        'MyDesktop.SystemStatus',
        'MyDesktop.Settings',        
        'modules.my.Department',
        'modules.my.organization.Role',
        'modules.my.organization.Role_old',
        'modules.my.work.Work',
        'modules.my.work.Leave',
        'modules.my.work.WorkTodo'
    ],

    //初始化方法 
    init: function() {
        // custom logic before getXYZ methods get called...

        this.callParent();
		this.desktop.initShortcut()
        // now ready...
    },
    //2、new 出具体对象
    getModules : function(){
        return [
			new BeifengBPM.Notepad(),
			new modules.my.Department(),
			new modules.my.organization.Role(),
			new modules.my.organization.Role_old(),
			new modules.my.work.Work(),
			new modules.my.work.Leave(),
			new modules.my.work.WorkTodo()
        ];
    },

    getDesktopConfig: function () {
        var me = this, ret = me.callParent();

        return Ext.apply(ret, {
            //cls: 'ux-desktop-black',

            contextMenuItems: [   //右键元素设置
                { text: '桌面设置', handler: me.onSettings, scope: me }
            ],
            //3 桌面图标显示
            shortcuts: Ext.create('Ext.data.Store', {
                model: 'Ext.ux.desktop.ShortcutModel',
                data: [
                    { name: '记事本', iconCls: 'mynotepad-shortcut', module: 'notepad' },
                    { name: '部门管理', iconCls: 'cpu-shortcut', module: 'department' },
                    { name: '角色管理', iconCls: 'memberLevel-shortcut', module: 'roleOldModule' },
                    { name: '角色信息', iconCls: 'accordion-shortcut', module: 'roleModule' },
                    { name: '工作流增加', iconCls: 'accordion-shortcut', module: 'workModule' },
                    { name: '请假流程', iconCls: 'accordion-shortcut', module: 'leaveModel' },
                    { name: '待办列表', iconCls: 'accordion-shortcut', module: 'todoModel' }
                ]
            }),

            wallpaper: 'extjs/desktop/resources/Blue-Sencha.jpg',
            wallpaperStretch: false
        });
    },

    // config for the start menu
    getStartConfig : function() {
        var me = this, ret = me.callParent();
		var adminrealname=document.getElementById('adminrealname').value;
        return Ext.apply(ret, {
            title: adminrealname,
            iconCls: 'user',
            height: 300,
            toolConfig: {
                width: 100,
                items: [
                    {
                        text:'设置',
                        iconCls:'settings',
                        handler: me.onSettings,
                        scope: me
                    },
                    '-',
                    {
                        text:'退出',
                        iconCls:'logout',
                        handler: me.onLogout,
                        scope: me
                    }
                ]
            }
        });
    },

    getTaskbarConfig: function () {
        var ret = this.callParent();

        return Ext.apply(ret, {
            quickStart: [  //快速启动元素配制
                { name: '记事本', iconCls: 'notepad', module: 'notepad' }  
            ],
            trayItems: [
                { xtype: 'trayclock', flex: 1 }
            ]
        });
    },

    onLogout: function () {
        Ext.Msg.confirm('Logout', 'Are you sure you want to logout?');
    },

    onSettings: function () {
        var dlg = new MyDesktop.Settings({
            desktop: this.desktop
        });
        dlg.show();
    }
});

