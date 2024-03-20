<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Lịch sử hóa đơn</title>
<link href="https://cdn.lineicons.com/4.0/lineicons.css"
	rel="stylesheet" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
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
	max-height: 100%;
}

.btn:hover {
	transform: scale(1.2);
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
	pointer-events: none;
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

.history-invoice {
	margin-bottom: 20px;
}

.history-invoice .card-header {
	background-color: #3b7ddd;
	color: white;
	font-weight: bold;
	padding: 10px 20px;
	border-radius: 10px 10px 0 0;
}

.history-invoice .card-body {
	background-color: #f8f9fa;
	padding: 20px;
	border-radius: 0 0 10px 10px;
}

.history-invoice .card-body .row {
	align-items: center;
}

.history-invoice .card-body .col-2 {
	font-size: 1.5rem;
	color: #3b7ddd;
}

.history-invoice .card-body .col-8 {
	font-size: 0.9rem;
}

.history-invoice .card-body .col-2:last-child {
	font-size: 0.8rem;
	color: #6c757d;
	text-align: right;
}

.history-invoice:hover {
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
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
	.bi-basket3-fill {
		transform: scale(1.2);
	}
	.history-invoice .row .col-12 {
		font-size: 10px;
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
			<h1 class="text-center my-5">Lịch sử hóa đơn</h1>
			<div class="container-fluid d-flex flex-column mt-5">
				<div class="row">
					<div class="col-lg-8 border py-3 mb-3">
						<h5>
							<b>KẾT QUẢ BÁN HÀNG HÔM NAY</b>
						</h5>
						<div class="row">
							<div class="col-lg-4">
								<div class="row">
									<div class="col-lg-2 col-md-2 col-2 px-3 py-4">
										<span><i
											class="bi bi-currency-dollar h4 text-white bg-danger rounded-circle py-1 px-2"></i></span>
									</div>
									<div class="col-lg-10 col-md-10 col-10">
										<b class="m-3">${countBill} đơn đã xong</b>
										<c:choose>
											<c:when test="${totalUp}">
												<p class="h3 text-success m-0">
													<b class="mx-3">${totalBillToday}</b> <span
														class="h5 text-success"><i
														class="bi bi-arrow-up-short mx-3"></i> ${percentChange}%</span>
												</p>
											</c:when>
											<c:when test="${totalDown}">
												<p class="h3 text-danger m-0">
													<b class="mx-3">${totalBillToday}</b> <span
														class="h5 text-danger"><i
														class="bi bi-arrow-down-short mx-3"></i> ${percentChange}%</span>
												</p>
											</c:when>
											<c:otherwise>
												<p class="h3 m-0">
													<b class="mx-3">${totalBillToday}</b> <span class="h5"><i
														class="bi bi-arrow-right-short mx-3"></i></span>
												</p>
											</c:otherwise>
										</c:choose>
										<p class="m-3">Hôm qua : ${totalYesterday}</p>
									</div>


								</div>
							</div>
							<div class="col-lg-4 border">
								<div class="row">
									<div class="col-lg-2 col-md-2 col-2 px-3 py-4">
										<span><i
											class="bi bi-pencil h4 text-white bg-success rounded-circle py-1 px-2"></i></span>
									</div>
									<div class="col-lg-10 col-md-10 col-10">
										<b class="m-3">${pinnedBill} đơn đang phục vụ</b>
										<p class="h3 text-success m-0">
											<b class="mx-3">${pinnedBill}</b>
										</p>
									</div>
								</div>
							</div>
							<div class="col-lg-4">
								<div class="row">
									<div class="col-lg-2 col-md-2 col-2 px-3 py-4">
										<span><i
											class="bi bi-person h4 text-white bg-primary rounded-circle py-1 px-2"></i></span>
									</div>
									<div class="col-lg-10 col-md-10 col-10">
										<b class="m-3">Khách hàng</b>
										<p class="h3 text-primary m-0">
											<b class="mx-3">${countBill}</b>
										</p>
										<p class="m-3">Hôm qua : ${countBillYesterday}</p>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-lg-4 mb-3">
						<img
							src="https://img.freepik.com/free-psd/sup-banner-template-design_23-2149408275.jpg?w=1380&t=st=1708587063~exp=1708587663~hmac=00a75693027e03d8db131450c90d378f1730960744e5f29d4c5dc4b33770cce4"
							alt="" style="width: 100%; height: 200px;">
					</div>
				</div>

				<div class="row">
					<div class="col-10 col-lg-8 border py-3 mb-3">
						<div class="row">
							<div class="col-lg-8">
								<p class="h5">
									<b>DOANH SỐ HÔM NAY</b> <span class="h5 text-primary"><i
										class="bi bi-arrow-right h6 text-dark"></i>${totalBillToday}</span>
								</p>

							</div>

						</div>

						<div class="container">
							<!-- Tabs Navigation -->
							<ul class="nav nav-tabs" id="myTab" role="tablist">
								<li class="nav-item" role="presentation">
									<button class="nav-link" id="day-tab" data-bs-toggle="tab"
										data-bs-target="#day" type="button" role="tab"
										aria-controls="day" aria-selected="true">Theo Ngày</button>
								</li>

								<li class="nav-item" role="presentation">
									<button class="nav-link" id="week-tab" data-bs-toggle="tab"
										data-bs-target="#week" type="button" role="tab"
										aria-controls="week" aria-selected="false">Theo Tuần</button>
								</li>
							</ul>

							<div class="tab-content mt-3" id="myTabContent">
								<div class="tab-pane" id="day" role="tabpanel"
									aria-labelledby="day-tab">
									<canvas id="dailyRevenueChart" width="400" height="300"></canvas>
								</div>
							</div>

							<div class="tab-content mt-3" id="myTabContent">
								<div class="tab-pane" id="week" role="tabpanel"
									aria-labelledby="week-tab">
									<canvas id="weeklyRevenueChart" width="400" height="300"></canvas>
								</div>
							</div>


							<div class="tab-content mt-3" id="myTabContent">
								<div class="tab-pane" id="day" role="tabpanel"
									aria-labelledby="day-tab"></div>
							</div>

						</div>

					</div>

					<div class="col-10 col-lg-4 mb-3">
						<div class="card history-invoice">
							<div class="card-header">Lịch sử hóa đơn</div>
							<div class="card-body">
								<div class="card-body">
									<c:if test="${billTodayNull == 'True'}">
										<p>Hôm nay chưa có đơn hàng nào</p>
									</c:if>
									<c:forEach var="btoday" items="${billIdsToday}"
										varStatus="loop">
										<div class="row my-3">
											<c:url var="billDetailUrl" value="/historyInvoice/${btoday}" />
											<a href="${billDetailUrl}" class="col-2"><i
												class="bi bi-basket3-fill"></i></a>
											<div class="col-10 col-lg-10 align-self-center">Trần Gia
												vừa bán đơn hàng với giá trị có mã là ${btoday}</div>
										</div>
									</c:forEach>
								</div>
							</div>
						</div>
					</div>


				</div>
			</div>
		</div>
	</div>
	<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/js/all.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
		crossorigin="anonymous"></script>
	<script>
		const dailyRevenueData = ${totalByDay};
		// Khởi tạo biểu đồ cột theo ngày
		const dailyCtx = document.getElementById('dailyRevenueChart')
				.getContext('2d');
		const dailyChart = new Chart(dailyCtx, {
			type : 'bar',
			data : {
				labels : [ 'Hôm nay', 'Hôm qua', '3 Ngày trước', '4 Ngày trước', '5 Ngày trước',
						'6 Ngày trước', '7 Ngày trước', '8 Ngày trước', '9 Ngày trước', '10 Ngày trước' ],
				datasets : [ {
					label : 'Doanh thu theo ngày',
					data : dailyRevenueData,
					backgroundColor : 'rgba(75, 192, 192, 0.6)',
					borderColor : 'rgba(75, 192, 192, 1)',
					borderWidth : 1
				} ]
			},
			options : {
				scales : {
					y : {
						beginAtZero : true,
						max : 5000000, // Giới hạn trục y tại 10 triệu đồng
						ticks : {
							stepSize : 500000
						// Bước giá trị của trục y
						}
					},
					x : {
						grid : {
							display : false
						// Ẩn dải lưới trên trục x
						}
					}
				}
			}
		});
		const weeklyRevenueData = ${totalOfLastTenWeeks};
		// Khởi tạo biểu đồ cột theo tuần
		const weeklyCtx = document.getElementById('weeklyRevenueChart')
				.getContext('2d');
		const weeklyChart = new Chart(weeklyCtx, {
			type : 'bar',
			data : {
				labels : [ 'Tuần 1', 'Tuần 2', 'Tuần 3', 'Tuần 4', 'Tuần 5',
						'Tuần 6', 'Tuần 7', 'Tuần 8', 'Tuần 9', 'Tuần 10' ],
				datasets : [ {
					label : 'Doanh thu theo tuần',
					data : weeklyRevenueData,
					backgroundColor : 'rgba(255, 99, 132, 0.6)',
					borderColor : 'rgba(255, 99, 132, 1)',
					borderWidth : 1
				} ]
			},
			options : {
				scales : {
					y : {
						beginAtZero : true,
						max : 15000000, // Giới hạn trục y tại 15 triệu đồng
						ticks : {
							stepSize : 1000000
						// Bước giá trị của trục y
						}
					},
					x : {
						grid : {
							display : false
						// Ẩn dải lưới trên trục x
						}
					}
				}
			}
		});
		const hamBurger = document.querySelector(".toggle-btn");

		hamBurger.addEventListener("click", function() {
			document.querySelector("#sidebar").classList.toggle("expand");
		});
		const footer = document.getElementById("footer");

		document.addEventListener('DOMContentLoaded', function() {
		});
		
		 document.addEventListener('DOMContentLoaded', function() {
		        // Kích hoạt tab "Theo Ngày"
		        document.getElementById('day-tab').classList.add('active');

		        // Hiển thị phần tử div chứa biểu đồ theo ngày
		        document.getElementById('day').classList.add('show', 'active');
		    });
	</script>
</body>

</html>