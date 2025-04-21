package cn.edu.xjtlu.readingnotes.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import cn.edu.xjtlu.readingnotes.user.User;
import cn.edu.xjtlu.readingnotes.user.UserRepo;
import cn.edu.xjtlu.readingnotes.util.UserInfo;


@Controller
public class UserViewController {

    @Autowired
    private UserRepo repo;
    
    @GetMapping("/login")
    public String formLogin() {
        return "login";
    }
    
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public String viewProfile(
            @AuthenticationPrincipal Object principal, Model model) {
        final Long id = ((UserInfo) principal).getId();
        final User user = repo.findById(id).orElseThrow();
        model.addAttribute("user", user);
        return "profile";
    }
    
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String administrate(Model model) {
        model.addAttribute("users", repo.findAll());
        return "admin";
    }
    
}
