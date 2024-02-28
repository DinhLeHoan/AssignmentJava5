package pc05401.assignment.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.nimbusds.oauth2.sdk.ParseException;

import jakarta.servlet.http.HttpServletRequest;
import pc05401.assignment.entity.Bill;
import pc05401.assignment.model.ProductInfo;
import pc05401.assignment.model.StaffModel;
import pc05401.assignment.repository.BillDetailRepository;
import pc05401.assignment.repository.BillRepository;
import pc05401.assignment.service.SessionService;

@Controller
public class InvoiceController {

	@Autowired
	SessionService session;

	@Autowired
	BillDetailRepository billDetailRepository;

	@Autowired
	BillRepository billRepository;

	@GetMapping("invoiceDetail")
	public String showDetail() {

		return checkStaff("invoiceDetail");
	}

	@GetMapping("pickInvoice")
	public String showPick() {

		return checkStaff("pickInvoice");
	}

	@GetMapping("historyInvoice")
	public String historyInvoice(Model model) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		Date date = null;
		Date dateYesterday = null;
		double totalToday = 0;
		double totalYesterday = 0;
		// Khởi tạo Map để lưu trữ danh sách id của các hóa đơn theo từng ngày và tổng
		// số tiền của từng ngày
		Map<String, List<Integer>> billIdsByDay = new LinkedHashMap<>();
		List<Double> totalByDay = new ArrayList<>();

		// Tạo một đối tượng Calendar và thiết lập ngày tháng năm hiện tại
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);

		// Khởi tạo danh sách id của ngày hiện tại
		List<Integer> billIdsToday = new ArrayList<>();
		
		try {
			date = formatter.parse(formatter.format(currentDate));
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		
		// Lấy danh sách id của hóa đơn cho ngày hiện tại
		List<Bill> billsToday = getBillsByDate(date);
		int countBill =0;

		for (Bill bill : billsToday) {
			billIdsToday.add(bill.getBillId());
			totalToday += bill.getTotalWithVoucher();
			countBill++;
		}
		
		model.addAttribute("billIdsToday",billIdsToday);
		model.addAttribute("countBill",countBill);
		model.addAttribute("totalBillToday",totalToday);
		System.out.println();

		//Tính ngày hôm qua:
		// Tính ngày hôm qua bằng cách trừ 1 ngày từ ngày hiện tại
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		Date yesterdayDate = calendar.getTime();

		// Khởi tạo danh sách id của hóa đơn cho ngày hôm qua
		List<Integer> billIdsYesterday = new ArrayList<>();

		try {
		    // Gán lại giá trị cho biến dateYesterday
		    dateYesterday = formatter.parse(formatter.format(yesterdayDate));
		} catch (java.text.ParseException e) {
		    e.printStackTrace();
		}

		// Lấy danh sách id của hóa đơn cho ngày hôm qua
		List<Bill> billsYesterday = getBillsByDate(dateYesterday);

		// Tính tổng số tiền của các hóa đơn trong ngày hôm qua và thêm id vào danh sách
		for (Bill bill : billsYesterday) {
		    billIdsYesterday.add(bill.getBillId());
		    totalYesterday += bill.getTotalWithVoucher();
		}

		// Thêm ngày hôm qua và danh sách id của hóa đơn tương ứng vào model
		model.addAttribute("billIdsYesterday", billIdsYesterday);
		model.addAttribute("totalYesterday", totalYesterday);
		if(billsYesterday.size() != 0) {
			model.addAttribute("countBillYesterday",billsYesterday.size());
		}else {
			model.addAttribute("countBillYesterday",0);
		}
		List<String> dateStrings = new ArrayList<>();
		
		// Lặp qua 10 ngày trước từ ngày hiện tại
		for (int i = 0; i < 10; i++) {
		    // Lấy ngày trong vòng lặp
		    Date currentDateMinusI = calendar.getTime();
		    String dateString = formatter.format(currentDateMinusI);

		    // Kiểm tra nếu là ngày hôm nay thì thêm "Hôm nay", ngược lại thêm chuỗi ngày tháng năm
		    if (i == 0) {
		        dateStrings.add("Hôm nay");
		    } else {
		        dateStrings.add(dateString);
		    }
		    
		    // Gán lại giá trị cho biến date trong vòng lặp
		    try {
		        // Gán lại giá trị cho biến date trong vòng lặp
		        date = formatter.parse(formatter.format(currentDateMinusI));
		    } catch (java.text.ParseException e) {
		        e.printStackTrace();
		    }

		    // Lấy danh sách id của hóa đơn cho ngày hiện tại
		    List<Bill> bills = getBillsByDate(date);

		    // Khởi tạo danh sách id cho ngày hiện tại và tổng số tiền cho ngày hiện tại
		    List<Integer> billIds = new ArrayList<>();
		    double total = 0;
		    for (Bill bill : bills) {
		        billIds.add(bill.getBillId());
		        // Tính tổng số tiền của các hóa đơn trong ngày
		        total += bill.getTotalWithVoucher();
		    }

		    // Thêm danh sách id của ngày hiện tại vào Map
		    billIdsByDay.put(dateString, billIds);
		    // Thêm tổng số tiền của ngày hiện tại vào danh sách tổng số tiền
		    totalByDay.add(total);

		    // Trừ 1 ngày từ ngày hiện tại
		    calendar.add(Calendar.DAY_OF_YEAR, -1);
		}
		//So sánh hôm nay và hôm qua

		double percentChange = 0.0;
		if (totalYesterday != 0) {
		    percentChange = ((totalToday - totalYesterday) / totalYesterday) * 100;
		}
		
		// Đặt giá trị cho attribute totalUp hoặc totalDown dựa vào phần trăm tăng/giảm
		if (percentChange > 0) {
		    model.addAttribute("totalUp", true);
		} else {
		    model.addAttribute("totalDown", true);
		}
		
		// Tạo một đối tượng DecimalFormat để làm tròn percentChange đến 2 chữ số sau dấu thập phân
		DecimalFormat df = new DecimalFormat("#.##");
		double percentChangeRounded = Double.parseDouble(df.format(percentChange));
		
		model.addAttribute("percentChange", percentChangeRounded);
		// Đưa danh sách các chuỗi ngày tháng năm vào Attribute của model
		model.addAttribute("dateStrings", dateStrings);
		 // In ra giá trị của totalByDay để kiểm tra
		model.addAttribute("billIdsByDay", billIdsByDay);
		
		model.addAttribute("totalByDay", totalByDay);
		
		
		
		// Khởi tạo danh sách để lưu trữ tổng của 10 tuần
		List<Integer> totalOfLastTenWeeks = new ArrayList<>();

		// Lặp qua 10 tuần trước từ ngày hiện tại
		for (int i = 0; i < 10; i++) {
		    // Tính ngày bắt đầu của tuần hiện tại
		    calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
		    calendar.add(Calendar.WEEK_OF_YEAR, -i);

		    // Lấy ngày đầu tiên của tuần trong vòng lặp
		    Date currentWeekFirstDay = calendar.getTime();

		    // Tính tổng của các hóa đơn trong tuần hiện tại
		    int totalOfCurrentWeek = 0;
		    for (int j = 0; j < 7; j++) {
		    	
		    	try {
			        // Gán lại giá trị cho biến date trong vòng lặp
			        date = formatter.parse(formatter.format(currentWeekFirstDay));
			    } catch (java.text.ParseException e) {
			        e.printStackTrace();
			    }

		        List<Bill> bills = getBillsByDate(date);
		        for (Bill bill : bills) {
		            totalOfCurrentWeek += bill.getTotalWithVoucher();
		        }
		        calendar.add(Calendar.DAY_OF_YEAR, 1);
		        currentWeekFirstDay = calendar.getTime();
		    }

		    // Thêm tổng của tuần hiện tại vào danh sách tổng của 10 tuần
		    totalOfLastTenWeeks.add(totalOfCurrentWeek);
		}

		// Đưa danh sách tổng của 10 tuần vào model
		model.addAttribute("totalOfLastTenWeeks", totalOfLastTenWeeks);
		
		List<Bill> pinnedBill = billRepository.findByIsPaid(false);
		model.addAttribute("pinnedBill", pinnedBill.size());
		
		return checkStaff("historyInvoice");
	}

	
	
	
	@GetMapping("historyInvoice/{id}")
	public String historyInvoice(@PathVariable int id, Model model) {
		Bill bill = billRepository.findById(id).orElse(null);

		if (bill == null) {
			return "redirect:/error";
		}

		List<Integer[]> resultList = billRepository.findProductsAndCountItById(id);
		List<ProductInfo> productInfos = new ArrayList<>();

		for (Integer[] result : resultList) {
			int product = result[0]; // Sản phẩm
			int count = result[1]; // Số lượng

			ProductInfo pro = new ProductInfo(product, count);
			productInfos.add(pro);
		}

		model.addAttribute("productInfos", productInfos);
		model.addAttribute("bill", bill);

		return checkStaff("InvoiceEdit");
	}

	@GetMapping("historyInvoice/{id}/{func}/{productId}")
	public String historyInvoice(@PathVariable int id, @PathVariable String func, @PathVariable int productId,
			HttpServletRequest request, Model model) {
		Bill bill = billRepository.findById(id).orElse(null);

		if (bill == null) {
			return "redirect:/error";
		}

		if (func.equalsIgnoreCase("deleteInvoice")) {
			billDetailRepository.deleteByBill_BillId(id);
			return checkStaff("historyInvoice");
		} else {
			return checkStaff("InvoiceEdit");
		}
	}

	public List<Bill> getBillsByDate(Date date) {
		return billRepository.findByDate(date);
	}

	public String checkStaff(String path) {
		StaffModel account = session.get("staff");
		if (account == null) {
			return "redirect:/login";
		}
		return path;
	}
}
