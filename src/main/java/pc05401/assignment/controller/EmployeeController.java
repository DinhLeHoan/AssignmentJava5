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

	String regex = "^[a-zA-Z0-9]+$";
	String regexPhone = "^0\\d{9}$";
	String regexName = "^[\\p{L} ]+$";
	String error = "" ;
	@Autowired
	SessionService session;

	@Autowired
	StaffRepository staffRepository;

	@GetMapping("employeeManager")
	public String showEmployeeManager(Model model) {
		StaffModel account = session.get("staff");
		if (account == null) {
			return "redirect:/login";
		}
		if (account.getRole().equals("USER") || account.getRole().equals("CASHIER")) {
			return "redirect:/home";
		}

		model.addAttribute("staffList", staffRepository.findByRoleNot("ADMIN"));

		return "employeeManager";
	}

	@GetMapping("employeeUpdate")
	public String showEmployeeUpdate(@RequestParam("staffId") Integer staffId, Model model) {
		Optional<Staff> staffOptional = staffRepository.findById(staffId);
		if (staffOptional.isPresent()) {
			Staff staffEdit = staffOptional.get();
			model.addAttribute("staffEdit", staffEdit);
			model.addAttribute("error", error) ;
		}
		error = "" ;
		return "employeeUpdate";
	}

	@PostMapping("employeeUpdate")
	public String submitEmployeeUpdate(@RequestParam("staffId") Integer staffId,
			@RequestParam("username") String username, @RequestParam("password") String password,
			@RequestParam("name") String name, @RequestParam("phone") String phone,
			@RequestParam("active") boolean active, @RequestParam("role") String role,
			@RequestParam("salary") Double salary, Model model) {

		Staff staffEdit = staffRepository.findById(staffId).orElseThrow();
		
		staffEdit.setUsername(username);
		staffEdit.setPassword(password);
		staffEdit.setName(name);
		staffEdit.setPhone(phone);
		staffEdit.setActive(active);
		staffEdit.setRole(role);
		staffEdit.setSalary(salary);
		switch (validateStaff(staffEdit)) {
		case 0:
			error =  "Vui lòng nhập đầy đủ thông tin !" ;
			return "redirect:/employeeUpdate?staffId="+staffEdit.getStaffId();
		case 1:
			error =  "Tên đăng nhập chỉ gồm chữ cái hoặc số !" ;
			return "redirect:/employeeUpdate?staffId="+staffEdit.getStaffId();
		case 2:
			error = "Tên đăng nhập phải từ 6 - 25 ký tự !" ;
			return "redirect:/employeeUpdate?staffId="+staffEdit.getStaffId();
		case 3:
			error = "Mật khẩu chỉ gồm chữ cái hoặc số !" ;
			return "redirect:/employeeUpdate?staffId="+staffEdit.getStaffId();
		case 4:
			error = "Mật khẩu phải từ 6 - 25 ký tự !" ;
			return "redirect:/employeeUpdate?staffId="+staffEdit.getStaffId();
		case 5:
			error = "Tên chưa hợp lệ !" ;
			return "redirect:/employeeUpdate?staffId="+staffEdit.getStaffId();
		case 6:
			error = "Tên phải từ 6 - 50 ký tự !" ;
			return "redirect:/employeeUpdate?staffId="+staffEdit.getStaffId();
		case 7:
			error =  "Số điện thoại chưa hợp lệ !";
			return "redirect:/employeeUpdate?staffId="+staffEdit.getStaffId();
		case 8:
			error = "Lương phải là số dương !" ;
			return "redirect:/employeeUpdate?staffId="+staffEdit.getStaffId();
		default:
			staffRepository.save(staffEdit);
			return "redirect:/employeeManager";
		}
	}

	@GetMapping("/deleteStaff")
	public String deleteStaff(@RequestParam("id") Integer staffId) {
		Staff staffDel = staffRepository.findById(staffId).orElseThrow();
		staffDel.setActive(false);
		staffRepository.save(staffDel);
		return "redirect:/employeeManager";
	}

	private int validateStaff(Staff staffEdit) {

		if (staffEdit.getUsername().length() == 0 || staffEdit.getPassword().length() == 0
				|| staffEdit.getName().length() == 0 || staffEdit.getPhone().length() == 0) {
			return 0;
		}
		if (!staffEdit.getUsername().matches(regex)) {
			return 1;
		}
		if (staffEdit.getUsername().length() < 6 || staffEdit.getUsername().length() > 25) {
			return 2;
		}
		if (!staffEdit.getPassword().matches(regex)) {
			return 3;
		}
		if (staffEdit.getPassword().length() < 6 || staffEdit.getPassword().length() > 25) {
			return 4;
		}
		if (!staffEdit.getName().matches(regexName)) {
			return 5;
		}
		if (staffEdit.getName().length() < 6 || staffEdit.getName().length() > 50) {
			return 6;
		}
		if (!staffEdit.getPhone().matches(regexPhone)) {
			return 7;
		}
		if (staffEdit.getSalary() <= 0) {
			return 8;
		}

		return -1;
	}
}
