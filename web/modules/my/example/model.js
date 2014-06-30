/******** 桌面创建应该的最基础的模型  ************/

Ext.define('modules.my.work.Work', {   
    extend: 'Ext.ux.desktop.Module',
    //此module 唯一标识
    id:'modelId',
    //初始化    (extjs 默认调用名)
    init : function(){
        this.launcher = {
            text: '桌面显示名称',
            handler : this.createWindow,
            scope: this
        }
    },
    
    //创建此窗口的方法    (extjs 默认调用名) 
    createWindow : function(){
        var desktop = this.app.getDesktop();//拿到桌面窗口
        var win = desktop.getWindow('modelId');  //查看此窗口是否已经存在
        if(!win){
            win = desktop.createWindow({
                id: 'modelId',
                title:'窗口标题',
                width:453,
                height:258,
                iconCls: 'notepad',
                animCollapse:false,
                border: false,
                hideMode: 'offsets',
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

