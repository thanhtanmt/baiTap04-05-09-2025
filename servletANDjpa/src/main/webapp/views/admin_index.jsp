<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Quản lý Category</title>
<style>
table {
	border-collapse: collapse;
	width: 80%;
	margin: 20px auto;
}

th, td {
	border: 1px solid #ccc;
	padding: 8px;
	text-align: center;
}

form {
	margin: 0;
}

.form-container {
	width: 80%;
	margin: 20px auto;
}
</style>
</head>
<body>
	<h2 style="text-align: center;">Quản lý Category</h2>
	<h3 class="form-container">Chào mừng
		${sessionScope.account.fullName} đến với webstar</h3>

	<!-- ====== Form thêm mới (CREATE) ====== -->
	<div class="form-container">
		<h3>Thêm Category</h3>
		<form
			action="${pageContext.request.contextPath}/CategoryServlet?action=create"
			method="post">
			<input type="text" name="name" placeholder="Tên category" required>
			<input type="text" name="description" placeholder="Mô tả">
			<button type="submit">Thêm</button>
		</form>
	</div>

	<!-- ====== Bảng hiển thị danh sách (READ) ====== -->
	<table>
		<thead>
			<tr>
				<th>ID</th>
				<th>tên người dùng</th>
				<th>vai trò</th>
				<th>Tên</th>
				<th>Mô tả</th>
				<th>Thao tác</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="cat" items="${category}">
				<tr>
					<td>${cat.categoryId}</td>
					<td>${cat.user.fullName}</td>
					<td><c:choose>
							<c:when test="${cat.user.roleid == 1}">User</c:when>
							<c:when test="${cat.user.roleid == 2}">Manager</c:when>
							<c:when test="${cat.user.roleid == 3}">Admin</c:when>
							<c:otherwise>Không xác định</c:otherwise>
						</c:choose></td>
					<td>${cat.name}</td>
					<td>${cat.description}</td>
					<td>
						<!-- ====== Form sửa (UPDATE) ====== -->
						<form
							action="${pageContext.request.contextPath}/CategoryServlet?action=update"
							method="post" style="display: inline;">
							<input type="hidden" name="categoryId" value="${cat.categoryId}">
							<input type="hidden" name="userid" value="${cat.userId}">
							<input type="text" name="name" value="${cat.name}" required>
							<input type="text" name="description" value="${cat.description}">
							<button type="submit">Sửa</button>
						</form> <!-- ====== Nút xóa (DELETE) ====== -->
						<form
							action="${pageContext.request.contextPath}/CategoryServlet?action=delete"
							method="post" style="display: inline;">
							<input type="hidden" name="categoryId" value="${cat.categoryId}">
							<button type="submit"
								onclick="return confirm('Xóa category này?')">Xóa</button>
						</form>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="text-center mt-3">
		<a href="${pageContext.request.contextPath}/logout">đăng xuất</a>
	</div>

	<!-- Thông báo -->
	<c:if test="${not empty message}">
		<p style="text-align: center; color: green;">${message}</p>
	</c:if>
</body>
</html>
