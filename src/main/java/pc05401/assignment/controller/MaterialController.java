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
		
		
		
		return checkAdmin("checkMaterials") ;
	}
	
	@GetMapping("materialManager")
	public String materialManager() {
		
		
		return checkAdmin("materialManager") ;
	}
	
	@GetMapping("materialAdd")
	public String showMaterialAdd() {
		
		
		return checkAdmin("materialAdd") ;
	}
	
	@GetMapping("materialUpdate")
	public String showMaterialUpdate() {
		
		
		return checkAdmin("materialUpdate") ;
	}
	
	public String checkAdmin(String path) {
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
