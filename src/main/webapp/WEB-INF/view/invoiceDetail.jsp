<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chi tiết hóa đơn</title>
    <link href="https://cdn.lineicons.com/4.0/lineicons.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap');

        ::after,
        ::before {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
        }

        a {
            text-decoration: none;
        }

        li {
            list-style: none;
        }

        h1 {
            font-weight: 600;
            font-size: 1.5rem;
        }

        body {
            font-family: 'Poppins', sans-serif;
            max-height: 100%;
        }

        .btn:hover {
            /* transform: scale(1.2); */
        }

        .wrapper {
            display: flex;
        }

        .main {

            width: 100%;
            overflow: hidden;
            transition: all 0.35s ease-in-out;
            background-color: #fafbfe;
        }

        #sidebar {
            width: 40px;
            min-width: 40px;
            z-index: 1000;
            height: 100%;
            transition: all .25s ease-in-out;
            background-color: #0e2238;
            display: flex;
            flex-direction: column;
            position: fixed;
        }

        #sidebar.expand {
            width: 220px;
            min-width: 220px;
        }

        .toggle-btn {
            background-color: transparent;
            cursor: pointer;
            border: 0;
            padding: 1rem 0.5rem;
        }

        .toggle-btn i {
            font-size: 1.5rem;
            color: #FFF;
        }

        .sidebar-logo {
            margin: auto 0;
        }

        .sidebar-logo a {
            color: #FFF;
            font-size: 1.15rem;
            font-weight: 600;
        }

        #sidebar:not(.expand) .sidebar-logo,
        #sidebar:not(.expand) a.sidebar-link span {
            display: none;
        }

        .sidebar-nav {
            padding: 2rem 0;
            flex: 1 1 auto;
        }

        a.sidebar-link {
            padding: 0.5rem 0.5rem;
            color: #FFF;
            display: block;
            font-size: 0.9rem;
            white-space: nowrap;
            border-left: 3px solid transparent;
        }

        .sidebar-link i {
            font-size: 1.1rem;
            margin-right: .75rem;
        }

        a.sidebar-link:hover {
            background-color: rgba(255, 255, 255, .075);
            border-left: 3px solid #3b7ddd;
        }

        .sidebar-item {
            position: relative;
        }

        #sidebar:not(.expand) .sidebar-item .sidebar-dropdown {
            position: absolute;
            top: 0;
            left: 70px;
            background-color: #0e2238;
            padding: 0;
            min-width: 15rem;
            display: none;
        }

        #sidebar:not(.expand) .sidebar-item:hover .has-dropdown+.sidebar-dropdown {
            display: block;
            max-height: 15em;
            width: 100%;
            opacity: 1;
        }

        #sidebar.expand .sidebar-link[data-bs-toggle="collapse"]::after {
            border: solid;
            border-width: 0 .075rem .075rem 0;
            content: "";
            display: inline-block;
            padding: 2px;
            position: absolute;
            right: 1.5rem;
            top: 1.4rem;
            transform: rotate(-135deg);
            transition: all .2s ease-out;
        }

        #sidebar.expand .sidebar-link[data-bs-toggle="collapse"].collapsed::after {
            transform: rotate(45deg);
            transition: all .2s ease-out;
        }

        .main {
            margin-left: 40px;
        }

        .square-box {
            width: 100%;
            background-color: #ffffff;
            border-radius: 10px;
            box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.623);
        }

        .title-box {
            top: 0;
            left: 0;
            width: 100%;
            font-size: small;
            background-color: #264653;
            color: white;
            text-align: center;
            padding: 10px;
            border-radius: 10px 10px 0 0;
        }

        .square-content {
            font-size: smaller;
        }

        .square-content-money {
            height: 100px;
            padding-top: 40px;
        }

        .product-img {
            object-fit: cover;
            width: 100%;
            height: 100%;
            border-radius: 10px;
        }

        .product-info {
            padding: 10px;
        }

        .product {
            transform: scale(0.8);
            padding: 5pt;
            box-shadow: 0 0 2rem rgba(0, 0, 0, 0.215);
        }

        .total-amount {
            position: fixed;
            bottom: 0;
            right: 0;
            left: 40px;
            /* Width of the sidebar */
            background-color: #ffffff;
        }

        .total-amount h5 {
            font-weight: 600;
            margin-bottom: 0;
        }

        .total-amount span {
            color: #0e2238;
            font-size: 1.2rem;
        }

        .total-amount {
            position: fixed;
            bottom: 0;
            right: 0;
            left: 40px;
            /* Width of the sidebar */
            background-color: #ffffff;
            transition: opacity 0.3s ease-in-out;
            /* Thêm transition để làm mịn việc hiển thị */
            opacity: 1;
            /* Mặc định hiển thị */
            /* pointer-events: none; */
            /* Không tương tác */
        }

        .total-amount.hidden {
            opacity: 0;
            /* Khi ẩn, làm mờ */
            pointer-events: none;
            /* Không tương tác */
        }

        .bill-row-hover:hover {
            cursor: pointer;
        }

        .bi-plus {
            font-size: 60px;
        }

        .product:hover {
            box-shadow: 0 0 2rem rgba(0, 62, 11, 0.502);
        }

        /* Thêm lớp CSS mới */
        @media (min-width: 992px) {

            /* Màn hình lớn hơn hoặc bằng 992px */
            .custom-col-lg {
                max-width: calc(100% / 3);
                /* Chia 3 cột */
            }
        }

        @media (min-width: 768px) and (max-width: 991.98px) {

            /* Màn hình từ 768px đến 991.98px */
            .custom-col-md {
                max-width: calc(100% / 2);
                /* Chia 2 cột */
            }
        }

        @media (max-width: 767.98px) {

            /* Màn hình nhỏ hơn 768px */
            .custom-col-sm {
                max-width: 100%;
                /* Hiển thị 1 cột */
            }

            .product {
                transform: scale(0.8);
                padding: 5pt;
                margin-top: -20px;
                margin-bottom: -20%;
            }
        }
    </style>
</head>

<body>

    <div class="wrapper">
        <aside id="sidebar">
            <div class="d-flex">
                <button class="toggle-btn" type="button">
                    <i class="lni lni-grid-alt"></i>
                </button>
                <div class="sidebar-logo">
                    <a href="/home">Súp Trần Gia</a>
                </div>
            </div>
            <div class="sidebar-logo">
                <img
                    src="https://trangiafnb.com/wp-content/uploads/2023/07/sup-tran-gia.png"
                    style="width: 50%; margin-left: 50px;" alt="">
                <h6 style="color: white; margin-left: 50px;">Võ Hữu Thành</h6>
            </div>
            <div class="sidebar-profile"></div>
            <ul class="sidebar-nav">
                <c:if test="${staff.role eq 'ADMIN' || staff.role eq 'CASHIER'}">
                    <li class="sidebar-item"><a href="/checkMaterials"
                        class="sidebar-link"> <i class="bi bi-house-lock"></i> <span>Quản
                                lý kho</span>
                    </a></li>
                </c:if>
                <c:if test="${staff.role eq 'ADMIN'}">
                    <li class="sidebar-item"><a href="/employeeManager"
                        class="sidebar-link"> <i class="bi bi-people"></i> <span>Quản
                                lý nhân viên</span>
                    </a></li>
                </c:if>
                <c:if test="${staff.role eq 'ADMIN'}">
                    <li class="sidebar-item"><a href="/productManager"
                    class="sidebar-link"> <i class="lni lni-agenda"></i> <span>Quản
                            lý sản phẩm</span>
                </a></li>
                </c:if>
                <li class="sidebar-item"><a href="/registerShift"
                    class="sidebar-link"> <i class="bi bi-calendar-check-fill"></i>
                        <span>Lịch làm việc</span>
                </a></li>
                <li class="sidebar-item"><a href="#" class="sidebar-link">
                        <i class="bi bi-bell"></i> <span>Thông báo</span>
                </a></li>
                <li class="sidebar-item"><a href="/home" class="sidebar-link">
                        <i class="bi bi-receipt-cutoff"></i> <span>Hóa đơn</span>
                </a></li>
                <li class="sidebar-item"><a href="/historyInvoice"
                    class="sidebar-link"> <i class="bi bi-clock-history"></i> <span>Lịch
                            sử hóa đơn</span>
                </a></li>
            </ul>
            <div class="sidebar-footer">


                <a href="#" class="sidebar-link"> <i class="bi bi-gear"></i> <span>Cài
                        đặt</span>
                </a> <a href="/logout" class="sidebar-link"> <i class="lni lni-exit"></i>
                    <span>Logout</span>
                </a>
            </div>
        </aside>
        <div class="main p-3">
            <div class="text-center">
                <h1 class="my-5">
                    Chi tiết hóa đơn
                </h1>
                <div class="container-fluid d-flex flex-column mt-5">
                    <div class="row justify-content-center">
                        <div class="col-xl-5 col-10">
                            <table class="table table-borderless mt-5">
                                <thead>
                                    <tr>
                                        <th scope="col"></th>
                                        <th scope="col"></th>
                                        <th scope="col"></th>
                                        <th scope="col"></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr onclick="" class="bill-row-hover">
                                        <th scope="row">Khách hàng:</th>
                                        <td>Shoppe food</td>
                                        <td class="border border-radius"><i
                                                class="bi bi-file-earmark-person-fill m-2"></i> sửa</td>
                                    </tr>
                                    <tr onclick="" class="bill-row-hover">
                                        <th scope="row">Người bán:</th>
                                        <td>Đức Anh</td>
                                        <td></td>
                                    </tr>
                                    <tr onclick="" class="bill-row-hover">
                                        <th scope="row">Thời gian:</th>
                                        <td>8:38 pm</td>
                                        <td class="border border-radius"><i class="bi bi-clock-fill m-2"></i> sửa</td>
                                    </tr>
                                    <tr onclick="" class="bill-row-hover">
                                        <th scope="row">Tạm tính:</th>
                                        <td>160.000 VND</td>
                                        <td></td>
                                    </tr>
                                    <tr onclick="" class="bill-row-hover">
                                        <th scope="row">Voucher sử dụng:</th>
                                        <td>-6.0000</td>
                                        <td class="border border-radius"><i class="bi bi-cash-stack"></i> chọn</td>
                                    </tr>
                                    <tr onclick="" class="bill-row-hover">
                                        <th scope="row">Tổng tiền:</th>
                                        <td>154.000 VND</td>
                                        <td></td>
                                    </tr>
                                </tbody>
                            </table>
                            <div class="row border product">
                                <div class="col-md-4">
                                    <img src="https://scontent.fsgn2-4.fna.fbcdn.net/v/t39.30808-6/428603185_430935795990313_6912678684582224430_n.jpg?_nc_cat=101&ccb=1-7&_nc_sid=3635dc&_nc_eui2=AeGBSQd7V9XssIrkt76Z1y7GNm3XJEKq5IM2bdckQqrkg5LfvyPRPUWm2bJni2D6b3SHm1gSDW04Oax-i4UJDUx4&_nc_ohc=Ziod3x08f6YAX_e9Akw&_nc_ht=scontent.fsgn2-4.fna&oh=00_AfC1ag--0SLI359ihHYCO9BH9r0c_fhcHuA7hgA9VA_szg&oe=65DAF35A"
                                        alt="Product Image" class="img-fluid product-img">
                                </div>
                                <div class="col-md-8 product-info">
                                    <div class="row">
                                        <div class="col-12">
                                            <h5 class="mb-0">Tên Sản Phẩm</h5>
                                            <p class="text-muted mb-0">Thông tin sản phẩm</p>
                                        </div>
                                    </div>
                                    <div class="row mt-3">
                                        <div class="col-12">
                                            <button class="btn btn-secondary me-2"><i class="fas fa-minus"></i></button>
                                            <span class="me-2">1</span>
                                            <button class="btn btn-secondary me-2"><i class="fas fa-plus"></i></button>
                                            <button class="btn btn-danger me-2"><i class="fas fa-trash"></i>
                                                Xóa</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row border product">
                                <div class="col-md-4">
                                    <img src="https://scontent.fsgn2-4.fna.fbcdn.net/v/t39.30808-6/428603185_430935795990313_6912678684582224430_n.jpg?_nc_cat=101&ccb=1-7&_nc_sid=3635dc&_nc_eui2=AeGBSQd7V9XssIrkt76Z1y7GNm3XJEKq5IM2bdckQqrkg5LfvyPRPUWm2bJni2D6b3SHm1gSDW04Oax-i4UJDUx4&_nc_ohc=Ziod3x08f6YAX_e9Akw&_nc_ht=scontent.fsgn2-4.fna&oh=00_AfC1ag--0SLI359ihHYCO9BH9r0c_fhcHuA7hgA9VA_szg&oe=65DAF35A"
                                        alt="Product Image" class="img-fluid product-img">
                                </div>
                                <div class="col-md-8 product-info">
                                    <div class="row">
                                        <div class="col-12">
                                            <h5 class="mb-0">Tên Sản Phẩm</h5>
                                            <p class="text-muted mb-0">Thông tin sản phẩm</p>
                                        </div>
                                    </div>
                                    <div class="row mt-3">
                                        <div class="col-12">
                                            <button class="btn btn-secondary me-2"><i class="fas fa-minus"></i></button>
                                            <span class="me-2">1</span>
                                            <button class="btn btn-secondary me-2"><i class="fas fa-plus"></i></button>
                                            <button class="btn btn-danger me-2"><i class="fas fa-trash"></i>
                                                Xóa</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row border product">
                                <div class="col-md-4">
                                    <img src="https://scontent.fsgn2-4.fna.fbcdn.net/v/t39.30808-6/428603185_430935795990313_6912678684582224430_n.jpg?_nc_cat=101&ccb=1-7&_nc_sid=3635dc&_nc_eui2=AeGBSQd7V9XssIrkt76Z1y7GNm3XJEKq5IM2bdckQqrkg5LfvyPRPUWm2bJni2D6b3SHm1gSDW04Oax-i4UJDUx4&_nc_ohc=Ziod3x08f6YAX_e9Akw&_nc_ht=scontent.fsgn2-4.fna&oh=00_AfC1ag--0SLI359ihHYCO9BH9r0c_fhcHuA7hgA9VA_szg&oe=65DAF35A"
                                        alt="Product Image" class="img-fluid product-img">
                                </div>
                                <div class="col-md-8 product-info">
                                    <div class="row">
                                        <div class="col-12">
                                            <h5 class="mb-0">Tên Sản Phẩm</h5>
                                            <p class="text-muted mb-0">Thông tin sản phẩm</p>
                                        </div>
                                    </div>
                                    <div class="row mt-3">
                                        <div class="col-12">
                                            <button class="btn btn-secondary me-2"><i class="fas fa-minus"></i></button>
                                            <span class="me-2">1</span>
                                            <button class="btn btn-secondary me-2"><i class="fas fa-plus"></i></button>
                                            <button class="btn btn-danger me-2"><i class="fas fa-trash"></i>
                                                Xóa</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row border product p-5 m-3">
                                <i class="bi bi-plus"></i>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
        <div class="footSpace"></div>
        <div class="total-amount" id="footer">
            <div class="container text-center">
                <div class="row">
                    <a href="/mainInvoice" type="button" class="col btn btn-secondary p-3 m-3">
                        Ghim hóa đơn
                    </a>
                    <div class="col btn btn-success p-3 m-3">
                        Thanh toán ngay
                    </div>
                    <div class="col btn btn-danger p-3 m-3">
                        Hủy hóa đơn
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/js/all.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
        crossorigin="anonymous"></script>
    <script>
        const hamBurger = document.querySelector(".toggle-btn");

        hamBurger.addEventListener("click", function () {
            document.querySelector("#sidebar").classList.toggle("expand");
        });
        const footer = document.getElementById("footer");

        window.addEventListener("scroll", function () {
            // Khoảng cách từ đỉnh trang đến cuối cùng của màn hình hiển thị
            const windowHeight = window.innerHeight;

            // Khoảng cách từ đỉnh trang đến phía dưới của phần total-amount
            const footerDistance = footer.getBoundingClientRect().top;

            // Nếu phần total-amount hiển thị trong màn hình và không nằm ở đỉnh trang
            if (footerDistance < windowHeight && footerDistance > 0) {
                footer.classList.add("hidden"); // Thêm lớp hidden để ẩn phần total-amount
            } else {
                footer.classList.remove("hidden"); // Loại bỏ lớp hidden để hiển thị phần total-amount
            }
        });

        // Xử lý khi cuộn lên
        window.addEventListener("scroll", function () {
            const scrollPosition = window.scrollY;

            // Nếu cuộn lên đến đỉnh trang
            if (scrollPosition === 0) {
                footer.classList.remove("hidden"); // Hiển thị lại phần total-amount
            }
        });
    </script>
</body>

</html>