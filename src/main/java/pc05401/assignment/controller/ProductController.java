package pc05401.assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import pc05401.assignment.model.StaffModel;
import pc05401.assignment.service.SessionService;

@Controller
public class ProductController {
	
	@Autowired
	SessionService session;
	
	@GetMapping("productManager") 
	public String showProductManager() {
		
		StaffModel account = session.get("staff") ;
		if(account == null) {
			return "redirect:/login" ;
		}
		if (account.getRole().equals("USER") || account.getRole().equals("CASHIER")) {
			return "redirect:/home" ;
		}
		
		return "productManager" ;
	}
	
}
