<!-- EXTJS基础JS -->
<script type="text/javascript" src="./extjs/ext-all-debug.js"></script>
<script type="text/javascript" src="./extjs/ext-lang-zh_CN.js"></script>

<!-- core js -->
<script type="text/javascript" src="./js/core/Module.js"></script>
<script type="text/javascript" src="./js/core/Video.js"></script>
<script type="text/javascript" src="./js/core/Wallpaper.js"></script>
<script type="text/javascript" src="./js/core/FitAllLayout.js"></script>
<script type="text/javascript" src="./js/core/StartMenu.js"></script>
<script type="text/javascript" src="./js/core/TaskBar.js"></script>
<script type="text/javascript" src="./js/core/ShortcutModel.js"></script>
<script type="text/javascript" src="./js/core/Desktop.js"></script>
<script type="text/javascript" src="./js/core/App.js"></script>
<!-- module js -->
<script type="text/javascript" src="./modules/BogusMenuModule.js"></script>
<script type="text/javascript" src="./modules/BogusModule.js"></script>
<script type="text/javascript" src="./modules/Notepad.js"></script>
<script type="text/javascript" src="./modules/SystemStatus.js"></script>
<script type="text/javascript" src="./modules/TabWindow.js"></script>
<script type="text/javascript" src="./modules/VideoWindow.js"></script>
<script type="text/javascript" src="./modules/WallpaperModel.js"></script>
<!-- 自定义的module -->
<script type="text/javascript" src="./modules/my/constant/Constant.js"></script>

<!-- setting js -->
<script type="text/javascript" src="./extjs/desktop/Settings.js"></script>

<!-- 扩展JS -->
<script type="text/javascript" src="./extjs/ux/form/SearchField.js"></script><!-- 搜索组件 -->


<script type="text/javascript">
	Ext.Loader.setConfig({enabled:true});
	Ext.Loader.setPath({
		'Ext.ux.desktop':'js',
		MyDesktop:''
	});
	Ext.require('MyDesktop.App');
	var myDesktopApp;
	Ext.onReady(function(){
		myDesktopApp=new MyDesktop.App();
	});
</script>