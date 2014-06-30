<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="${baseUrl }"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


<%@ include file="../plugin/extjs_css_plugin.jsp" %>
<%@ include file="../plugin/extjs_js_plugin.jsp" %>


<title>工作流中间件 --- 欢迎 【${currentUser.username}】</title>
</head>
<body>

	<input type="hidden" id="adminuserId" value="${currentUser.id }">
	<input type="hidden" id="adminrealname" value="${currentUser.username }">
	
	<!-- 存放信息的隐藏区 -->
	<input type="hidden" id="thisDepartmentId" value="">


</body>
</html>