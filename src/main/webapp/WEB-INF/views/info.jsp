<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<jsp:include page="head.jsp"></jsp:include>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
td { font-size: 13px }
th {
     font-size: 13px;
}
body {
	margin-left: 10px;
}
</style>

<script type="text/javascript">
	$(function(){
		$('#ff').form({
			success:function(data){
				$.messager.alert('提示', data);
			}
		});
		
	});
</script>
</head>
<body>
    <form id="ff" action="zk/saveData" method="post">
    <input type="hidden" value="${path}" name="path" />
    <input type="hidden" value="${cacheId}" name="cacheId" />
	<div style="height:310px; overflow-y:scroll;">
		<table>
			<tr>
				<td><label >data：</label></td>
				<td>
				<textarea rows="5" cols="40" name="data">${zkNodeData.data}
				</textarea>
			</tr>
			<tr>
				<td><label >czxid：</label></td>
				<td>${zkNodeData.czxid}</td>
			</tr>
			<tr>
				<td><label >mzxid：</label></td>
				<td>${zkNodeData.mzxid }</td>
			</tr>
			<tr>
				<td><label >ctime：</label></td>
				<td>${zkNodeData.ctime }</td>
			</tr>
			<tr>
				<td><label >mtime：</label></td>
				<td>${zkNodeData.mtime }</td>
			</tr>
			<tr>
				<td><label >version：</label></td>
				<td>${zkNodeData.version }</td>
			</tr>
			<tr>
				<td><label >cversion：</label></td>
				<td>${zkNodeData.cversion }</td>
			</tr>
			<tr>
				<td><label >aversion：</label></td>
				<td>${zkNodeData.aversion }</td>
			</tr>
			<tr>
				<td><label >ephemeralOwner：</label></td>
				<td>${zkNodeData.ephemeralOwner }</td>
			</tr>
			<tr>
				<td><label >dataLength：</label></td>
				<td>${zkNodeData.dataLength }</td>
			</tr>
			<tr>
				<td><label >numChildren：</label></td>
				<td>${zkNodeData.numChildren }</td>
			</tr>
			<tr>
				<td><label >pzxid：</label></td>
				<td>${zkNodeData.pzxid }</td>
			</tr>
				    <th>Access Controll List</th>
				    <table style="border: 1px solid;">
				        <tr style="color: red;font-weight: bold;">
				            <td>scheme</td>
				            <td>&nbsp;</td>
				            <td>id</td>
				            <td>&nbsp;</td>
				            <td>permission</td>
				            <td>&nbsp;</td>
				            <td>操作</td>
				        </tr>
				        <c:forEach items="${zkNodeData.aclDataList }" var="acl">
				            <tr>
				                <td>${acl.scheme }</td>
				                <td>&nbsp;</td>
				                <td>${fn:substring(acl.id,0,fn:indexOf(acl.id, ':'))}</td>
				                <td>&nbsp;</td>
				                <td>${acl.permission }</td>
				                <td>&nbsp;</td>
				                <td>删除</td>
				            </tr>
				        </c:forEach>
				    </table>
					
		</table>
	</div>
	<br/>
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="javascript:$('#ff').submit();">保存</a>
	</form>
	<%-- 	${data } ${czxid } ${mzxid } ${ctime } ${mtime } ${version } ${cversion
	} ${aversion } ${ephemeralOwner } ${dataLength } ${numChildren }
	${pzxid } --%>

</body>
</html>