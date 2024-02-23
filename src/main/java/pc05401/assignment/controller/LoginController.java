package pc05401.assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pc05401.assignment.dao.StaffDAO;
import pc05401.assignment.entity.Staff;
import pc05401.assignment.repository.StaffRepository;
import pc05401.assignment.service.SessionService;
import pc05401.assignment.util.CookieUtil;

@Controller
public class LoginController {
	String regex = "^[a-zA-Z0-9]+$";

	@Autowired
	private StaffRepository staffRepository;

	private StaffDAO staffDAO = new StaffDAO();

	@Autowired
	SessionService session;

	@GetMapping("login")
	public String showLoginForm(HttpServletRequest request) {
	    Staff staff;
	    Cookie rememberMeCookie = CookieUtil.getCookie(request, "rememberMe");
	    if (rememberMeCookie != null) {
	        String usernameStaff = rememberMeCookie.getValue();
	        System.out.println(usernameStaff);
	        if (usernameStaff != null && !usernameStaff.isBlank()) {
	            staff = staffRepository.findByUsername(usernameStaff);
	            if (staff != null) {
	                session.set("staff", staffDAO.convertToModel(staff));
	                return "redirect:/home";
	            }
	        }
	    }

	    return "login";
	}


	@PostMapping("login")
	public String login(@RequestParam("username") String username, @RequestParam("password") String password,
			@RequestParam(name = "rememberMe", defaultValue = "false") boolean isRemember, HttpServletResponse response,
			Model model, HttpServletRequest request) {

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
				if (!staff.isActive()) {
					model.addAttribute("error", "Tài khoản đã khóa, xin liên hệ quản lý");
					return "login";
				}

				session.set("staff", staffDAO.convertToModel(staff));
				if (isRemember) {
					CookieUtil.deleteCookie(response, "rememberMe");
					CookieUtil.createCookie(response, "rememberMe", staffDAO.convertToModel(staff).getUsername(), true,
							60 * 60 * 24);
				}
				return "redirect:/home";
			} else {
				model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng");
				return "login";
			}
		}
	   
	}

	public int validateForm(String username, String password) {
		if (username.length() == 0 || password.length() == 0) {
			return 0;
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