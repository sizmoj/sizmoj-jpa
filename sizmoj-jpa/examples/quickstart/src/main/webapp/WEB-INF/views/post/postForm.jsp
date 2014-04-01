<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>文章管理</title>
</head>

<body>
	<form id="inputForm" action="${ctx}/post/${action}" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${post.id}"/>
		<fieldset>
			<legend><small>文章管理</small></legend>
			<div class="control-group">
				<label for="task_title" class="control-label">文章标题:</label>
				<div class="controls">
					<input type="text" id="task_title" name="postTitle"  value="${post.postTitle}" class="input-large required" minlength="3"/>
				</div>
			</div>
			<div class="control-group">
				<label for="task_title" class="control-label">文章URL:</label>
				<div class="controls">
					<input type="text" id="task_title" name="url"  value="${post.url}" class="input-large required" minlength="3"/>
				</div>
			</div>
			<div class="control-group">
				<label for="task_title" class="control-label">文章标签:</label>
				<div class="controls">
					<input type="text" id="task_title" name="tagsString"  value="${post.tagsString}" class="input-large" minlength="3"/>
				</div>
			</div>
			<div class="control-group">
				<label for="task_title" class="control-label">文章分类:</label>
				<div class="controls">
					<select name="term.id">
						<c:forEach items="${terms}" var="term">
							<option value="${term.id }" <c:if test="${post.term.id eq term.id}">selected="selected"</c:if>>${term.name}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="control-group">
				<label for="description" class="control-label">文章内容:</label>
				<div class="controls">
					<textarea id="description" name="content" class="input-large">${post.content}</textarea>
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
