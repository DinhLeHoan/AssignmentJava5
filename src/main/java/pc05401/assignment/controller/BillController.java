package pc05401.assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import pc05401.assignment.model.StaffModel;
import pc05401.assignment.repository.BillRepository;
import pc05401.assignment.service.SessionService;

@Controller
public class BillController {
	@Autowired
	SessionService session;

	@Autowired
	private BillRepository billRepository;

	@GetMapping("bill/view/{billId}")
	public String viewBill(@PathVariable int billId, Model model) {
		StaffModel account = session.get("staff") ;
		if(account == null) {
			return "redirect:/login" ;
		}
		model.addAttribute("staff", session.get("staff"));

		return "invoiceDetail";
	}

}
