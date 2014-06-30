<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>


<script type="text/javascript" src="http://www.118100.cn/v5/js/jquery.js"></script>

</head>
<body>



<script type="text/javascript">
//数据
var data = {fd_id:'123',fd_name:'xxxx'};
$.ajax({  
    url: "dept/addff.html",  
    contentType: "application/json; charset=utf-8",  
    type: "POST",  
    dataType: "json",  
    data: JSON.stringify(data),
    success: function(data) { 
    	alert(data);
    }
});

</script>	




</body>
</html>