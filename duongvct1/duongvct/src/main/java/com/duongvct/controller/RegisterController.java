package com.duongvct.controller;

import com.duongvct.constants.RoleConstant;
import com.duongvct.entity.Account;
import com.duongvct.service.impl.RegisterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {
    @Autowired
    private RegisterServiceImpl registerService;

    @GetMapping("/register")
    public String getRegister(Model model) {
        model.addAttribute("account", new Account());
        return "register";
    }

    @PostMapping("/register")
    public String postRegister(@ModelAttribute Account account, @RequestParam("retypePassword") String retypePassword, @RequestParam("role") String role, Model model) {
        if (!account.getPassword().equals(retypePassword)) {
            model.addAttribute("account", account);
            model.addAttribute("message", "Passwords do not match.");
            return "register";
        }

        account.setActivated(true);

        // Set the role based on the selected value
        if (role.equals("ROLE_USER")) {
            account.setRoles(RoleConstant.ROLE_USER);
        } else if (role.equals("ROLE_GUEST")) {
            account.setRoles(RoleConstant.ROLE_GUEST);
        }

        registerService.registerAccount(account);
        return "redirect:/login";
    }
}