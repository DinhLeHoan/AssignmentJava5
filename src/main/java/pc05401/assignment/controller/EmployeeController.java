package pc05401.assignment.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pc05401.assignment.entity.Staff;
import pc05401.assignment.model.StaffModel;
import pc05401.assignment.repository.StaffRepository;
import pc05401.assignment.service.SessionService;

@Controller
public class EmployeeController {
	
	@Autowired
	SessionService session;
	
	@Autowired
	StaffRepository staffRepository ;
	
	@GetMapping("employeeManager")
	public String showEmployeeManager(Model model) {
		StaffModel account = session.get("staff") ;
		if(account == null) {
			return "redirect:/login" ;
		}
		if (account.getRole().equals("USER") || account.getRole().equals("CASHIER")) {
			return "redirect:/home" ;
		}
		
		model.addAttribute("staffList", staffRepository.findByRoleNot("ADMIN")) ;
		
		return "employeeManager" ;
	}
	
	@GetMapping("employeeUpdate") 
	public String showEmployeeUpdate(@RequestParam("staffId") Integer staffId, Model model) {
		Optional<Staff> staffOptional = staffRepository.findById(staffId);
	    if (staffOptional.isPresent()) {
	        Staff staffEdit = staffOptional.get();
	        model.addAttribute("staffEdit", staffEdit);
	    }
		return "employeeUpdate" ;
	}
	
	@PostMapping("employeeUpdate") 
	public String submitEmployeeUpdate(@RequestParam("staffId") Integer staffId, @RequestParam("username") String username, @RequestParam("password") String password,
			@RequestParam("name") String name, @RequestParam("phone") String phone, @RequestParam("active") boolean active, @RequestParam("role") String role,
			@RequestParam("salary") double salary, Model model) {
		
		Staff staffEdit = staffRepository.findById(staffId).orElseThrow() ;
		
		staffEdit.setUsername(username);
		staffEdit.setPassword(password);
	    staffEdit.setName(name);
	    staffEdit.setPhone(phone);
	    staffEdit.setActive(active);
	    staffEdit.setRole(role);
	    staffEdit.setSalary(salary);
	    staffRepository.save(staffEdit);
		return "redirect:/employeeManager" ;
	}
	
	@GetMapping("/deleteStaff")
    public String deleteStaff(@RequestParam("id") Integer staffId) {
        staffRepository.deleteById(staffId);
        return "redirect:/employeeManager" ;
    }
}
