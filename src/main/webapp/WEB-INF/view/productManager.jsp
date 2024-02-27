<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Quản lí sản phẩm</title>
<link href="https://cdn.lineicons.com/4.0/lineicons.css"
	rel="stylesheet" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<link rel="stylesheet" type="text/css"
	href="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.8.1/slick.min.css" />
<link rel="stylesheet" type="text/css"
	href="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.8.1/slick-theme.min.css" />
<style>
@import
	url('https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap')
	;

::after, ::before {
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

#sidebar:not(.expand) .sidebar-logo, #sidebar:not(.expand) a.sidebar-link span
	{
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

#sidebar:not(.expand) .sidebar-item:hover .has-dropdown+.sidebar-dropdown
	{
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

#sidebar.expand .sidebar-link[data-bs-toggle="collapse"].collapsed::after
	{
	transform: rotate(45deg);
	transition: all .2s ease-out;
}

.main {
	margin-left: 40px;
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
}

.nav-link {
	border-radius: 10px 10px 0 0;
	/* Sử dụng các giá trị tùy chỉnh của bạn cho viền góc */
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

table {
	width: 100%;
	border-collapse: collapse;
	margin-top: 20px;
	/* Adjust margin as needed */
}

th, td {
	padding: 12px;
	text-align: left;
	border-bottom: 1px solid #ddd;
}

.input-group-text {
	cursor: pointer;
}

/* Responsive styles for smaller screens */
@media ( max-width : 767.98px) {
	table {
		overflow-x: auto;
		display: block;
	}
	th, td {
		white-space: nowrap;
	}
}

/* Thêm lớp CSS mới */
@media ( min-width : 992px) {
	/* Màn hình lớn hơn hoặc bằng 992px */
	.custom-col-lg {
		max-width: calc(100%/ 3);
		/* Chia 3 cột */
	}
}

@media ( min-width : 768px) and (max-width: 991.98px) {
	/* Màn hình từ 768px đến 991.98px */
	.custom-col-md {
		max-width: calc(100%/ 2);
		/* Chia 2 cột */
	}
}

@media ( max-width : 767.98px) {
	.nav-tabs {
		display: flex;
		flex-wrap: nowrap;
		/* Ngăn các phần tử xuống dòng */
		overflow-x: auto;
		/* Cho phép kéo từ phải sang trái */
		-webkit-overflow-scrolling: touch;
		/* Hỗ trợ cuộn mượt trên các thiết bị cảm ứng */
	}
	.nav-item {
		flex: none;
		/* Không sử dụng flexbox trên giao diện điện thoại */
		display: inline-block;
		/* Hiển thị các mục trên cùng một hàng */
	}
	.product {
		margin: -10px;
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
				<c:if test="${staff.role eq 'ADMIN'}">
                    <li class="sidebar-item"><a href="/voucherManager"
                    class="sidebar-link"> <i class="bi bi-database-down"></i> <span>Quản
                            lý voucher</span>
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
				<h1 class="my-5">Quản lí sản phẩm</h1>
				<div class="container-fluid d-flex flex-column">
					<div class="row justify-content-center">
						<div class="col-md-12 col-lg-10 col-xl-8">
							<div class="container">
								<!-- Tabs Navigation -->
								<ul class="nav nav-tabs" id="myTab" role="tablist">
									<li class="nav-item" role="presentation">
										<button class="nav-link active" id="san-pham-tab"
											data-bs-toggle="tab" data-bs-target="#san-pham" type="button"
											role="tab" aria-controls="san-pham" aria-selected="true">Danh
											sách sản phẩm</button>
									</li>
									<li class="nav-item" role="presentation">
										<button class="nav-link" id="loai-san-pham-tab"
											data-bs-toggle="tab" data-bs-target="#loai-san-pham"
											type="button" role="tab" aria-controls="loai-san-pham"
											aria-selected="true">Danh sách loại sản phẩm</button>
									</li>

								</ul>

								<!-- Tabs Content -->
								<div class="tab-content my-3" id="myTabContent">
									<!-- Tab: Súp -->
									<div class="tab-pane fade show active" id="san-pham"
										role="tabpanel" aria-labelledby="san-pham-tab"
										style="height: 450px;">
										<div class="table-responsive" style="height: 80%;">
											<table>
												<tr>
													<th>STT</th>
													<th>Tên sản phẩm</th>
													<th>Loại sản phẩm</th>
													<th>Ghi chú</th>
													<th>Giá Tiền</th>
													<th></th>
												</tr>
												<c:forEach var="item" items="${listItem}">

													<tr>
														<td>${item.productId}</td>
														<td>${item.name}</td>
														<td>${item.tag.name}</td>
														<td>${item.note}</td>
														<td>${item.price}</td>
														<td><a href="/productUpdate?productId=${item.productId}" type="button"
															class="btn btn-success">Sửa</a> <a href="/deleteProduct?productId=${item.productId}" type="button"
															class="btn btn-danger">Xóa</a></td>
													</tr>
												</c:forEach>
												<tr>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td><a href="productAdd" type="button"
														class="btn btn-primary">Thêm sản phẩm</a></td>
												</tr>
											</table>
										</div>
									</div>
								</div>

								<div class="tab-content my-3" id="myTabContent">
									<div class="tab-pane fade show" id="loai-san-pham"
										role="tabpanel" aria-labelledby="loai-san-pham-tab"
										style="height: 450px;">
										<div class="table-responsive" style="height: 80%;">
											<table>
												<tr>
													<th>Tên loại sản phẩm</th>
													<th></th>
												</tr>
												<c:forEach var="tag" items="${tagList}">
												    <tr>
                                                    <td>${tag.name}</td>
                                                    <td><a href="/tagProductUpdate?tagId=${tag.tagId}" type="button"
                                                        class="btn btn-success">Sửa</a> <a href="" type="button"
                                                        class="btn btn-danger">Xóa</a></td>
                                                </tr>
												</c:forEach>
												
												<tr>
													<td></td>
													<td></td>
													<td><a href="tagProductAdd" type="button"
														class="btn btn-primary">Thêm loại sản phẩm</a></td>
												</tr>
											</table>
										</div>
									</div>
								</div>



							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<script
			src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/js/all.min.js"></script>
		<script
			src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
			integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
			crossorigin="anonymous"></script>
		<script>
			document.getElementById("productImage").addEventListener("change",
					function() {
						// Lấy ra tên file đã chọn
						var fileName = this.files[0].name;
						// Hiển thị tên file lên label
						this.nextElementSibling.innerText = fileName;
					});
			const hamBurger = document.querySelector(".toggle-btn");

			hamBurger.addEventListener("click", function() {
				document.querySelector("#sidebar").classList.toggle("expand");
			});
		</script>
</body>

</html>