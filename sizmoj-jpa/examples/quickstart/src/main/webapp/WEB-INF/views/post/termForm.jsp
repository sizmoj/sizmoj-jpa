<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>分类管理</title>
</head>

<body>
	<form id="inputForm" action="${ctx}/post/${action}" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${term.id}"/>
		<fieldset>
			<legend><small>分类管理</small></legend>
			<div class="control-group">
				<label for="task_title" class="control-label">分类名称:</label>
				<div class="controls">
					<input type="text" id="task_title" name="name"  value="${term.name}" class="input-large required" minlength="3"/>
				</div>
			</div>	
			<div class="form-actions">
				<input id="submit_btn" class="btn btn-primary" type="submit" value="提交"/>&nbsp;	
				<input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
			</div>
		</fieldset>
	</form>
	<script>
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#task_title").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate();
		});
	</script>
</body>
</html>

</html>