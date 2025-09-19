<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Đăng nhập</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css"/>
</head>
<body>
<div class="container mt-5" style="max-width: 400px;">
    <h2 class="text-center">Đăng nhập</h2>

    <!-- Hiển thị thông báo lỗi nếu có -->
    <c:if test="${alert != null}">
        <div class="alert alert-danger">${alert}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/login" method="post">
        <div class="form-group">
            <label for="username">Tài khoản</label>
            <input type="text" id="username" name="username" class="form-control" placeholder="Nhập username">
        </div>

        <div class="form-group">
            <label for="password">Mật khẩu</label>
            <input type="password" id="password" name="password" class="form-control" placeholder="Nhập password">
        </div>

        <div class="form-check mb-3">
            <input type="checkbox" class="form-check-input" id="remember" name="remember">
            <label class="form-check-label" for="remember">Ghi nhớ đăng nhập</label>
        </div>

        <button type="submit" class="btn btn-primary btn-block">Đăng nhập</button>

        <%-- <div class="text-center mt-3">
            <a href="${pageContext.request.contextPath}/register">Chưa có tài khoản? Đăng ký</a>
        </div>
        <div class="text-center mt-3">
            <a href="${pageContext.request.contextPath}/forgotpassword">Quên mật khẩu</a>
        </div> --%>
    </form>
</div>
</body>
</html>
