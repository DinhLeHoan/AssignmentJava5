package pc05401.assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import pc05401.assignment.model.StaffModel;
import pc05401.assignment.service.SessionService;

@Controller
public class InvoiceController {
	
	@Autowired
	SessionService session ;
	
	@GetMapping("invoiceDetail")
	public String showDetail() {
		
		return checkStaff("invoiceDetail") ;
	}
	
	@GetMapping("pickInvoice")
	public String showPick() {
		
		return checkStaff("pickInvoice") ;
	}
	
	@GetMapping("historyInvoice")
	public String historyInvoice() {
		
		return checkStaff("historyInvoice") ;
	}
	
	public String checkStaff(String path) {
		StaffModel account = session.get("staff") ;
		if(account == null) {
			return "redirect:/login" ;
		}
		return path ;
	}
}
