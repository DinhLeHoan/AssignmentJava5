package pc05401.assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pc05401.assignment.entity.Staff;
import pc05401.assignment.repository.StaffRepository;

@Controller
public class LoginController {

	String regex = "^[a-zA-Z0-9]+$";

	@Autowired
	private StaffRepository staffRepository;

	@GetMapping("login")
	public String showLoginForm() {

		return "login";
	}

	@PostMapping("login")
	public String login(@RequestParam("username") String username, @RequestParam("password") String password,
			Model model) {

		switch (validateForm(username, password)) {
			case 0:
				model.addAttribute("error", "Vui lòng nhập đủ thông tin !");
				return "login";
			case 1:
				model.addAttribute("error", "Tên đăng nhập chỉ gồm chữ cái hoặc số !");
				return "login";
			case 2:
				model.addAttribute("error", "Tên đăng nhập phải từ 6 - 25 ký tự !");
				return "login";
			case 3:
				model.addAttribute("error", "Mật khẩu chỉ gồm chữ cái hoặc số !");
				return "login";
			case 4:
				model.addAttribute("error", "Mật khẩu phải từ 6 - 25 ký tự !");
				return "login";
			default:
				Staff staff = staffRepository.findByUsernameAndPassword(username, password);
				if (staff != null) {
					return "redirect:/home";
				} else {
					model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng");
					return "login";
				}
		}

	}

	@GetMapping("home")
	public String showHome() {
		return "home";
	}

	public int validateForm(String username, String password) {
		if (username.length() == 0 || password.length() == 0) {
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
		if (password.length() < 6 || username.length() > 25) {
			return 4;
		}
		return -1;
	}
	
}
