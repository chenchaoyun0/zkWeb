<html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/easyui/themes/icon.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
function addauthpas(){
    $('#authForm').form('submit', {  
        url:'zk/queryZnodeInfo',  
        onSubmit: function(){  
            return true;
        },  
        success:function(data){  
            alert(data);
        }  
    }); 
}
</script>
</head>
<body>
<div>
<h3 align="left" style="color: red">${msg}</h3>
<form action="${pageContext.request.contextPath}/operation/zk/queryNodeByAuth" id="authForm" method="POST">
<input type="hidden" name="cacheId" value="${cacheId}"/>
<input type="hidden" name="path" value="${path}"/>
用户：<input class="easyui-validatebox" type="text" value="" data-options="required:true,tipPosition:'right'" name="username" id="username" value=""/>
密码：<input class="easyui-validatebox" type="text" value="" data-options="required:true,tipPosition:'right'" name="password" id="password" value=""/>
<!-- <button onclick="addauthpas()" value="确定">确定</button> -->
<a  href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" name="submit" onclick="document.getElementById('authForm').submit();" >保存</a>
</div>
</body>
</html>