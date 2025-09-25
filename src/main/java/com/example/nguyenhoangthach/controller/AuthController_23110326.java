package com.example.nguyenhoangthach.controller;

import com.example.nguyenhoangthach.entity.Users_23110326;
import com.example.nguyenhoangthach.service.SessionService_23110326;
import com.example.nguyenhoangthach.service.UserService_23110326;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class AuthController_23110326 {

    @Autowired
    private UserService_23110326 userService;


    @Autowired
    private SessionService_23110326 sessionService;

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String email,
                             @RequestParam String fullname,
                             @RequestParam(required = false) Integer phone,
                             @RequestParam String password,
                             @RequestParam String confirmPassword,
                             RedirectAttributes redirectAttributes) {
        
        if (!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Mật khẩu xác nhận không khớp!");
            return "redirect:/register";
        }

        if (userService.findByEmail(email).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Email đã tồn tại!");
            return "redirect:/register";
        }

        Users_23110326 user = new Users_23110326();
        user.setEmail(email);
        user.setFullname(fullname);
        user.setPhone(phone);
        user.setPasswd(password);
        user.setSignup_date(LocalDateTime.now());
        user.setIs_admin(false);

        userService.saveUser(user);
        redirectAttributes.addFlashAttribute("success", "Đăng ký thành công! Vui lòng đăng nhập.");
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                       @RequestParam String password,
                       HttpSession session,
                       RedirectAttributes redirectAttributes) {
        
        Optional<Users_23110326> userOpt = userService.findByEmail(username);
        
        if (userOpt.isPresent()) {
            Users_23110326 user = userOpt.get();
            if (password.equals(user.getPasswd())) {
                // Cập nhật thời gian đăng nhập cuối
                user.setLast_login(LocalDateTime.now());
                userService.saveUser(user);
                
                // Lưu thông tin user vào session
                sessionService.setAttribute(session, "currentUser", user);
                sessionService.setAttribute(session, "isAdmin", user.getIs_admin());
                
                return "redirect:/home";
            }
        }
        
        redirectAttributes.addFlashAttribute("error", "Email hoặc mật khẩu không đúng!");
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        sessionService.invalidate(session);
        return "redirect:/login?logout";
    }
}