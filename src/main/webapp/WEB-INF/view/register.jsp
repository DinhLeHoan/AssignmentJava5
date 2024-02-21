<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng ký tài khoản</title>
    <!-- Include Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css">
    <!-- Include FontAwesome CSS for icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        /* Custom CSS for full-height container */
        .full-height {
            min-height: 100vh;
            position: relative;
        }

        /* Custom CSS for footer */
        .footer {
            position: fixed;
            bottom: 0;
            width: 100%;
            background-color: #264653; /* Màu nền mới */
            color: white; /* Màu chữ mới */
            padding: 10px 0;
        }

        /* Custom CSS for icon alignment */
        .icon {
            color: white; /* Màu biểu tượng mới */
            position: absolute;
            top: 70%;
            left: 10px;
            transform: translateY(-50%);
            z-index: 1;
        }

        /* Custom CSS for space below footer */
        .footer-space {
            height: 100px; /* Chiều cao tùy chỉnh để tạo ra khoảng trống */
        }
    </style>
</head>
<body>
    <div class="container-fluid full-height d-flex flex-column justify-content-between">
        <div class="row justify-content-center align-items-center">
            <div class="col-md-8 col-lg-6 col-xl-4">
                <div class="card border-0 p-4">
                    <div class="card-body">
                        <h3 class="h1 text-center mb-4">Đăng ký tài khoản</h3>
                        <form action="/register" method="post">
                            <div class="mb-3 position-relative">
                                <label for="name" class="form-label" style="color: #264653;"><i class="fas fa-user icon" style="color: #264653;"></i>Họ và tên</label>
                                <input type="text" class="form-control ps-5" id="name" name="name">
                            </div>
                            <div class="mb-3 position-relative">
                                <label for="phone" class="form-label" style="color: #264653;"><i class="fas fa-phone-alt icon" style="color: #264653;"></i> Số điện thoại</label>
                                <input type="text" class="form-control ps-5" id="phone" placeholder="Enter your phone number" name="phone">
                            </div>
                            <div class="mb-3 position-relative">
                                <label for="username" class="form-label" style="color: #264653;"><i class="fas fa-user icon" style="color: #264653;"></i> Tên đăng nhập</label>
                                <input type="text" class="form-control ps-5" id="username" placeholder="Enter your username" name="username">
                            </div>
                            <div class="mb-3 position-relative">
                                <label for="password" class="form-label" style="color: #264653;"><i class="fas fa-lock icon" style="color: #264653;"></i> Mật khẩu</label>
                                <input type="password" class="form-control ps-5" id="password" placeholder="Enter your password" name="password">
                            </div>
                            <button type="submit" class="btn btn-primary w-100 mb-3" style="background-color: #264653;">Đăng ký</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <!-- Footer -->
        <div class="footer text-center">
            <div class="container">
                <div class="row">
                    <div class="col">
                        <a href="#" class="text-decoration-none me-3" style="font-size: 20px; color: white;"><i class="fas fa-file-alt" style="font-size: 24px; color: white;"></i> HÓA ĐƠN</a>
                    </div>
                    <div class="col">
                        <a href="#" class="text-decoration-none me-3" style="font-size: 20px; color: white;"><i class="fas fa-dolly" style="font-size: 24px; color: white;"></i> KIỂM KHO</a>
                    </div>
                    <div class="col">
                        <a href="#" class="text-decoration-none me-3" style="font-size: 20px; color: white;"><i class="fas fa-calendar-check" style="font-size: 24px; color: white;"></i> ĐĂNG KÝ CA</a>
                    </div>
                </div>
            </div>
        </div>
        <!-- Footer Space -->
        <div class="footer-space"></div>
    </div>

    <!-- Include Bootstrap JS (optional) -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
    <!-- Include FontAwesome JS (optional) -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/js/all.min.js"></script>
</body>
</html>