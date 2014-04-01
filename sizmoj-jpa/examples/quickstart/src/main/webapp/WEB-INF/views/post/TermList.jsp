<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>分类管理</title>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>内容</th></tr></thead>
		<tbody>
		<c:forEach items="${terms}" var="term">
			<tr><td>
					${term.name }
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
		<div><a class="btn" href="${ctx}/post/createTerm">创建任务</a></div>
</body>
</html>

