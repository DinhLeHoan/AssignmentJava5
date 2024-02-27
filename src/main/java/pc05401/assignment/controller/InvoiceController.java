package pc05401.assignment.controller;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import pc05401.assignment.entity.Bill;
import pc05401.assignment.entity.BillDetail;
import pc05401.assignment.entity.Product;
import pc05401.assignment.model.ProductInfo;
import pc05401.assignment.model.StaffModel;
import pc05401.assignment.repository.BillDetailRepository;
import pc05401.assignment.repository.BillRepository;
import pc05401.assignment.repository.StaffRepository;
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
		List<Integer> billIdToday = billRepository.findBillIdsToday();

		if (billIdToday.isEmpty()) {
			model.addAttribute("billTodayNull", "True");
		} else {
			model.addAttribute("billIdToday", billIdToday);
		}

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
		
		model.addAttribute("productInfos",productInfos);
		model.addAttribute("bill", bill);

		return checkStaff("InvoiceEdit");
	}

	public String checkStaff(String path) {
		StaffModel account = session.get("staff");
		if (account == null) {
			return "redirect:/login";
		}
		return path;
	}
}
