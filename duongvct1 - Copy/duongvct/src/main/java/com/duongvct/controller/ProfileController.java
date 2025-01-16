package com.duongvct.controller;

import com.duongvct.entity.Account;
import com.duongvct.service.impl.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class ProfileController {
    @Autowired
    private AccountServiceImpl accountService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
    @GetMapping("/profile")
    public String getProfile(@AuthenticationPrincipal User user, Model model) {
        Account account = accountService.findByUsername(user.getUsername());
        model.addAttribute("account", account);
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@AuthenticationPrincipal User user,
                                @ModelAttribute Account account,
                                @RequestParam("dob") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dob,
                                Model model) {
        Account currentAccount = accountService.findByUsername(user.getUsername());
        currentAccount.setPhoneNumber(account.getPhoneNumber());
        currentAccount.setFullname(account.getFullname());
        currentAccount.setEmail(account.getEmail());
        currentAccount.setAddress(account.getAddress());
        currentAccount.setDob(account.getDob());

        if (currentAccount.getRoles().getId().equals("ROLE_ADMIN")) {
            currentAccount.setSalary(account.getSalary());
        }

        accountService.save(currentAccount);
        return "redirect:/dashboard";
    }
}