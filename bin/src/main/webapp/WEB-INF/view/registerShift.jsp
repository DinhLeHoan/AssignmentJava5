<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trang nhập hóa đơn</title>
    <link href="https://cdn.lineicons.com/4.0/lineicons.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <style>
        /* CSS Main */
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
        }

        .wrapper {
            display: flex;
        }

        .main {
            min-height: 100vh;
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

        .sidebar-logo>img {
            width: 50%;
            margin-left: 50px;
        }

        .sidebar-logo>h6 {
            color: white;
            margin-left: 50px;
        }

        .main {
            margin-left: 40px;
        }

        .acticle {
            padding-top: 0px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px; /* Adjust margin as needed */
        }

        th, td {
            padding: 12px;
            text-align: center;
            border: 2px solid #ddd;
        }

        /* Responsive styles for smaller screens */
        @media (max-width: 767.98px) {
            table {
                overflow-x: auto;
                display: block;
            }

            th, td {
                white-space: nowrap;
            }
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
                <h6 style="color: white; margin-left: 50px;">${staff.name}</h6>
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
            <div class="acticle text-center">
                <h1>
                    Đăng ký ca
                </h1>
                <div class="container-fluid full-height d-flex flex-column justify-content-between">
                    <div class="row justify-content-center align-items-center">
                        <div class="card col-lg-10 mb-3">
                            <div class="card-header">
                                Danh sách ca làm theo ngày
                            </div>

                            <div class="table-responsive mb-3">
                                <table>
                                    <thead>
                                        <tr>
                                            <th><b>Súp Cần Thơ</b></th>
                                            <th><b>Thứ 2</b></th>
                                            <th><b>Thứ 3</b></th>
                                            <th><b>Thứ 4</b></th>
                                            <th><b>Thứ 5</b></th>
                                            <th><b>Thứ 6</b></th>
                                            <th><b>Thứ 7</b></th>
                                            <th><b>Chủ Nhật</b></th>
                                        </tr>
                                    </thead>

                                    <tbody>
                                        <tr>
                                            <td><b>Lịch 19/2</b></td>
                                            <td rowspan="2"></td>
                                            <td rowspan="2"></td>
                                            <td rowspan="2"></td>
                                            <td rowspan="2"></td>
                                            <td rowspan="2"></td>
                                            <td rowspan="2"></td>
                                            <td rowspan="2"></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td><b>19/2/1</b></td>
                                            <td><b>19/2/2</b></td>
                                            <td><b>19/2/3</b></td>
                                            <td><b>19/2/4</b></td>
                                            <td><b>19/2/5</b></td>
                                            <td><b>19/2/6</b></td>
                                            <td><b>19/2/7</b></td>
                                        </tr>
                                        <tr>
                                            <td rowspan="3"><b>Ca 1 (7h30 - 12h)</b></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                        </tr>
                                        <tr>                                         
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td><b>Hỗ trợ 7h30 - 10h30</b></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td rowspan="2"><b>Ca 2 (12h - 16h)</b></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                        </tr>
                                        <tr>                                         
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td rowspan="3"><b>Ca 3 (16h - 21h30)</b></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                        </tr>
                                        <tr>                                         
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td><b>Hỗ trợ 16h - 19h</b></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td><b>Ghi chú</b></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
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
    </script>
</body>

</html>