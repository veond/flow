/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.39
 * Generated at: 2014-04-18 07:52:15 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.page;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class main_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(2);
    _jspx_dependants.put("/WEB-INF/page/../plugin/extjs_js_plugin.jsp", Long.valueOf(1397007839406L));
    _jspx_dependants.put("/WEB-INF/page/../plugin/extjs_css_plugin.jsp", Long.valueOf(1366021182476L));
  }

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<base href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${baseUrl }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("\"/>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!-- EXTJSåºç¡CSS -->\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"./extjs/desktop/css/ext-all.css\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"./extjs/desktop/css/desktop.css\" />\r\n");
      out.write("\r\n");
      out.write('\r');
      out.write('\n');
      out.write("<!-- EXTJSåºç¡JS -->\r\n");
      out.write("<script type=\"text/javascript\" src=\"./extjs/ext-all-debug.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"./extjs/ext-lang-zh_CN.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<!-- core js -->\r\n");
      out.write("<script type=\"text/javascript\" src=\"./js/core/Module.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"./js/core/Video.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"./js/core/Wallpaper.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"./js/core/FitAllLayout.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"./js/core/StartMenu.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"./js/core/TaskBar.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"./js/core/ShortcutModel.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"./js/core/Desktop.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"./js/core/App.js\"></script>\r\n");
      out.write("<!-- module js -->\r\n");
      out.write("<script type=\"text/javascript\" src=\"./modules/BogusMenuModule.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"./modules/BogusModule.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"./modules/Notepad.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"./modules/SystemStatus.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"./modules/TabWindow.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"./modules/VideoWindow.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"./modules/WallpaperModel.js\"></script>\r\n");
      out.write("<!-- èªå®ä¹çmodule -->\r\n");
      out.write("<script type=\"text/javascript\" src=\"./modules/my/constant/Constant.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<!-- setting js -->\r\n");
      out.write("<script type=\"text/javascript\" src=\"./extjs/desktop/Settings.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<!-- æ©å±JS -->\r\n");
      out.write("<script type=\"text/javascript\" src=\"./extjs/ux/form/SearchField.js\"></script><!-- æç´¢ç»ä»¶ -->\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\tExt.Loader.setConfig({enabled:true});\r\n");
      out.write("\tExt.Loader.setPath({\r\n");
      out.write("\t\t'Ext.ux.desktop':'js',\r\n");
      out.write("\t\tMyDesktop:''\r\n");
      out.write("\t});\r\n");
      out.write("\tExt.require('MyDesktop.App');\r\n");
      out.write("\tvar myDesktopApp;\r\n");
      out.write("\tExt.onReady(function(){\r\n");
      out.write("\t\tmyDesktopApp=new MyDesktop.App();\r\n");
      out.write("\t});\r\n");
      out.write("</script>");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<title>工作流中间件 --- 欢迎 【");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${currentUser.username}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("】</title>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\r\n");
      out.write("\t<input type=\"hidden\" id=\"adminuserId\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${currentUser.id }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("\">\r\n");
      out.write("\t<input type=\"hidden\" id=\"adminrealname\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${currentUser.username }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("\">\r\n");
      out.write("\t\r\n");
      out.write("\t<!-- 存放信息的隐藏区 -->\r\n");
      out.write("\t<input type=\"hidden\" id=\"thisDepartmentId\" value=\"\">\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
