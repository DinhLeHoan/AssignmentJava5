package pc05401.assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pc05401.assignment.service.SessionService;
import pc05401.assignment.util.CookieUtil;

@Controller
public class HomeController {
    @Autowired
    SessionService session;

    @GetMapping("home")
    public String showHome(Model model) {
        model.addAttribute("staff", session.get("staff"));
        return "home";
    }

    @GetMapping("logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Model model) {
        // Invalidate the session
        session.set("staff", null);

        CookieUtil.deleteCookie(response, "rememberMe");

        return "redirect:/login";
    }
}
