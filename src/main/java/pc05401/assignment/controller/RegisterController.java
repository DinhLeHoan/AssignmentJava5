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
public class RegisterController {
	@Autowired
    private StaffRepository staffRepository;

    @GetMapping("register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("register")
    public String register(@RequestParam("username") String username, @RequestParam("password") String password,
                           @RequestParam("name") String name, @RequestParam("phone") String phone) {
        Staff staff = new Staff();
        staff.setUsername(username);
        staff.setPassword(password);
        staff.setName(name);
        staff.setActive(true);
        staff.setRole("USER");
        staff.setSalary(0);
        staff.setPhone(phone);
        staffRepository.save(staff);
        
        return "redirect:/login";
    }
}
