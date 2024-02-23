package pc05401.assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import pc05401.assignment.model.StaffModel;
import pc05401.assignment.service.SessionService;

@Controller
public class MaterialController {
	
	@Autowired
	SessionService session;

	@GetMapping("checkMaterials")
	public String checkMaterials() {
		
		
		
		return checkStaff("checkMaterials") ;
	}
	
	@GetMapping("materialManager")
	public String materialManager() {
		
		
		return checkStaff("materialManager") ;
	}
	
	public String checkStaff(String path) {
		StaffModel account = session.get("staff") ;
		if(account == null) {
			return "redirect:/login" ;
		}
		if (account.getRole().equals("USER")) {
			return "redirect:/home" ;
		}
		return path;
	}
	
}
