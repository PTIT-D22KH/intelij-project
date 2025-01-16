package com.duongvct.controller.authentication;

import com.duongvct.entity.Account;
import com.duongvct.service.impl.AccountServiceImpl;
import com.duongvct.service.impl.RegisterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class LoginController {
    @Autowired
    private AccountServiceImpl accountService;
    @Autowired
    private RegisterServiceImpl registerService;


    @GetMapping("/login")
    public String login(Model model, @RequestParam(value = "logout", required = false) String logout, @RequestParam(value = "error", required = false) String error) {
        model.addAttribute("account", new Account());
        if (logout != null && logout.equals("true")){
            model.addAttribute("message", "You have been logged out successfully.");
        }
        if (error != null && error.equals("true")){
            model.addAttribute("message", "Invalid username or password.");
        }
        if (error != null && error.equals("inactivated")){
            model.addAttribute("message", "Account is not activated. Please contact admin");
        }

        return "authentication/login";
    }

}
