package pc05401.assignment.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pc05401.assignment.entity.Bill;
import pc05401.assignment.entity.Staff;
import pc05401.assignment.model.StaffModel;
import pc05401.assignment.repository.BillRepository;
import pc05401.assignment.service.SessionService;
import pc05401.assignment.util.CookieUtil;

@Controller
public class HomeController {
	@Autowired
	SessionService session;

	@Autowired
	private BillRepository billRepository;

	@GetMapping("home")
	public String showHome(Model model) {
		
		StaffModel account = session.get("staff") ;
		if(account == null) {
			return "redirect:/login" ;
		}
		
		model.addAttribute("staff", session.get("staff"));

		List<Bill> unpaidBills = billRepository.findAllByIsPaidFalse();
        model.addAttribute("billList", unpaidBills);

		
		return "home";
	}

	@GetMapping("logout")
	public String logout(HttpServletRequest request, HttpServletResponse response, Model model) {
		// Invalidate the session
		session.set("staff", null);

		CookieUtil.deleteCookie(response, "rememberMe");

		return "redirect:/login";
	}
	
	@PostMapping("addNewBill")
	public String addNew() {
		addNewBillBlank();
		return "redirect:/home";
	}
	
	@PostMapping("deleteBill/{billId}")
	public String deleteBill(@PathVariable int billId) {
		billRepository.deleteById(billId);
		return "redirect:/home";
	}

	private Staff convertStaff(StaffModel staffModel) {
		Staff staff = new Staff();
		staff.setActive(staffModel.isActive());
		staff.setName(staffModel.getName());
		staff.setPassword(staffModel.getPassword());
		staff.setPhone(staffModel.getPhone());
		staff.setUsername(staffModel.getUsername());
		staff.setStaffId(staffModel.getStaffId());
		staff.setRole(staffModel.getRole());
		staff.setSalary(staffModel.getSalary());
		return staff;
	}

	private void addNewBillBlank() {
		Bill bill = new Bill();
		bill.setCustomerType("Tại quán");
		bill.setDate(new Date());
		bill.setNote("");
		bill.setStaff(convertStaff(session.get("staff")));
		bill.setIsPaid(false);
		bill.setTotal(0);
		bill.setTotalWithVoucher(0);
		billRepository.save(bill);
	}
}
