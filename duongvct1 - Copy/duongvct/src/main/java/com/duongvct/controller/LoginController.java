package com.duongvct.controller;

import com.duongvct.constants.RoleConstant;
import com.duongvct.entity.Account;
import com.duongvct.service.impl.AccountServiceImpl;
import com.duongvct.service.impl.RegisterServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

        return "login";
    }
//    @PostMapping("/login")
//    public String login(Account account
//            , @RequestParam(value = "guest", required = false) String guest) {
//        if (guest != null && guest.equals("true")){
//
//            Account newGuest = accountService.findByUsername("guest");
//            if (newGuest == null){
//                Account newAccountGuest = new Account();
//                newAccountGuest.setUsername("guest");
//                newAccountGuest.setPassword("guest");
//                newAccountGuest.setRoles(RoleConstant.ROLE_GUEST);
//                newAccountGuest.setFullname("Guest");
//                registerService.registerAccount(newAccountGuest);
//                return "redirect:/dashboard";
//            } else {
//                account = newGuest;
//            }
//
//        }
//        accountService.loadUserByUsername(account.getUsername());
//        return "redirect:/dashboard";



}
