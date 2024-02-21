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
	@Autowired
    private StaffRepository staffRepository;

    @GetMapping("login")
    public String showLoginForm() {
    	
        return "login";
    }
    
    @PostMapping("login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        Staff staff = staffRepository.findByUsernameAndPassword(username, password);
        if (staff != null) {
            // Nếu đăng nhập thành công, chuyển hướng đến trang chính
            return "redirect:/home";
        } else {
            // Nếu đăng nhập không thành công, hiển thị thông báo lỗi
            model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng");
            return "login";
        }
    }
    
    @GetMapping("home")
    public String showHome() {
        return "home";
    }
}
