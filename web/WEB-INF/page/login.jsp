<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录页</title>
<script type="text/javascript" src="${baseUrl }js/jquery-2.1.0.min.js"></script>
</head>
<body style="background: gray;">

<div style="margin-top: 80px; width: 100%" align="center">
	<form action="${baseUrl }login.html" method="post">
		登录名：<input type="text" name="username" id="username" maxlength="15" value="qwe123"/> <p> 
		密&nbsp;&nbsp;码：<input type="password" id="userpass" name="userpass" value="123456" maxlength="15"/><p>
		<input id="loginBut" type="submit" value="登录" /> &nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" value="退出" onclick="javascipt:window.close()" /> 
	</form>
	
	<script type="text/javascript">
		
		
	
	</script>	
</div>

</body>
</html>