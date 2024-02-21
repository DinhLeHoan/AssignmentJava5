<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Login Form</title>
<!-- Include Bootstrap CSS -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css">
<style>
/* Custom CSS for full-height container */
.full-height {
	height: 100vh;
}
</style>
</head>
<body>
	<div
		class="container-fluid full-height d-flex flex-column justify-content-center">
		<div class="row justify-content-center">
			<div class="col-md-8 col-lg-6 col-xl-4">
				<div class="card border-0 p-4">
					<div class="card-body">
						<div class="mb-2 text-center">
							<p class="text-muted">Đang xử lí thông tin...</p>
						</div>
						<h3 class="h1 text-center mb-4" style="color: #264653;">Đăng
							nhập</h3>
						<form action="login" method="post">
							<div class="mb-2">
								<label for="username" class="form-label" style="color: #264653;">Username</label>
								<input type="text" class="form-control" name="username"
									id="username" placeholder="Enter your username">
							</div>
							<div class="mb-3">
								<label for="password" class="form-label" style="color: #264653;">Password</label>
								<input type="password" class="form-control" name="password"
									id="password" placeholder="Enter your password">
							</div>
							<div class="row mb-1">
								<div class="col">
									<div class="form-check">
										<input type="checkbox" class="form-check-input"
											id="rememberMe"> <label class="form-check-label"
											for="rememberMe" style="color: #264653;">Nhớ mật khẩu</label>
									</div>
								</div>
							</div>
							<c:if test="${not empty error}">
								<div style="color: red">${error}</div>
							</c:if>
							<button type="submit" class="btn btn-primary w-100 mb-4"
								style="background-color: #264653;">Đăng Nhập</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Include Bootstrap JS (optional) -->
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>

</body>
</html>
