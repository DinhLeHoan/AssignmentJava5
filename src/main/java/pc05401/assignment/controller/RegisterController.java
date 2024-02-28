package pc05401.assignment.controller;

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
public class RegisterController {
	
	@Autowired
	SessionService session;
	
	String regex = "^[a-zA-Z0-9]+$";
	String regexPhone = "^0\\d{9}$";
	String regexName = "^[\\p{L} ]+$";

	@Autowired
	private StaffRepository staffRepository;

	@GetMapping("register")
	public String showRegistrationForm() {
		
		return checkStaff("register");
	}
	
	@GetMapping("registerShift")
	public String showRegisterShift() {
		
		return checkStaff("registerShift");
	}

	@PostMapping("register")
	public String register(@RequestParam("username") String username, @RequestParam("password") String password,
			@RequestParam("name") String name, @RequestParam("phone") String phone, Model model) {

		switch (validateForm(username, password, name, phone)) {
		case 0:
			model.addAttribute("error", "Vui lòng nhập đầy đủ thông tin !");
			return "register";
		case 1:
			model.addAttribute("error", "Tên đăng nhập chỉ gồm chữ cái hoặc số !");
			return "register";
		case 2:
			model.addAttribute("error", "Tên đăng nhập phải từ 6 - 25 ký tự !");
			return "register";
		case 3:
			model.addAttribute("error", "Mật khẩu chỉ gồm chữ cái hoặc số !");
			return "register";
		case 4:
			model.addAttribute("error", "Mật khẩu phải từ 6 - 25 ký tự !");
			return "register";
		case 5:
			model.addAttribute("error", "Tên chưa hợp lệ !");
			return "register";
		case 6:
			model.addAttribute("error", "Tên phải từ 6 - 50 ký tự !");
			return "register";
		case 7:
			model.addAttribute("error", "Số điện thoại chưa hợp lệ !");
			return "register";
		case 8:
			model.addAttribute("error", "Không được nhập quá 250 ký tự !");
			return "register";
		default:
			try {
				if (staffRepository.findByUsername(username) != null) {
					model.addAttribute("error", "Username đã tồn tại !");
					return "register";
				}

				if (staffRepository.findByPhone(phone) != null) {
					model.addAttribute("error", "Số điện thoại đã được sử dụng !");
					return "register";
				}

				Staff staff = new Staff();
				staff.setUsername(username);
				staff.setPassword(password);
				staff.setName(name);
				staff.setActive(true);
				staff.setRole("USER");
				staff.setSalary(0.0);
				staff.setPhone(phone);
				staffRepository.save(staff);
				return "redirect:/employeeManager";
			} catch (Exception e) {
				return "register";
			}
		}

	}

	public int validateForm(String username, String password, String name, String phone) {
		if (username.length() == 0 || password.length() == 0 || name.length() == 0 || phone.length() == 0) {
			return 0 ;
		}
		if (!username.matches(regex)) {
			return 1;
		}
		if (username.length() < 6 || username.length() > 25) {
			return 2;
		}
		if (!password.matches(regex)) {
			return 3;
		}
		if (password.length() < 6 || password.length() > 25) {
			return 4;
		}
		if (!name.matches(regexName)) {
			return 5;
		}
		if (name.length() < 6 || name.length() > 50) {
			return 6;
		}
		if (!phone.matches(regexPhone)) {
			return 7;
		}
		if (username.length() > 250 || password.length() > 250 || name.length() > 250) {
			return 8 ;
		}
		
		return -1;
	}
	
	public String checkStaff(String path) {
		
		StaffModel account = session.get("staff") ;
		if(account == null) {
			return "redirect:/login" ;
		}
		if (account.getRole().equals("USER") || account.getRole().equals("CASHIER")) {
			return "redirect:/home" ;
		}
		
		return path ;
	}
}
