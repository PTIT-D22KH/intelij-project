package com.duongvct.controller;

import com.duongvct.entity.Account;
import com.duongvct.service.impl.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    @Autowired
    private AccountServiceImpl accountService;
    @GetMapping(value = {"", "/dashboard", "/"})
    public String getDashboard(@AuthenticationPrincipal User user, Model model) {
        Account account = accountService.findByUsername(user.getUsername());
        model.addAttribute("account", account);
        System.out.println(account.getRoles().getId());
        if (account.getRoles().getId().equals("ROLE_GUEST")) {
            model.addAttribute("message", "You are logged in as a guest. Please log in as a user to access the dashboard.");
            return "login";
        }
        return "dashboard";
    }
}
