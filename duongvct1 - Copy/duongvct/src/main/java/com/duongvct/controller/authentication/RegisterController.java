package com.duongvct.controller.authentication;

import com.duongvct.utils.Role;
import com.duongvct.entity.Account;
import com.duongvct.service.impl.AccountServiceImpl;
import com.duongvct.service.impl.RegisterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class RegisterController {
    @Autowired
    private RegisterServiceImpl registerService;

    @Autowired
    private AccountServiceImpl accountService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping("/register")
    public String getRegister(Model model) {
        model.addAttribute("account", new Account());
        return "authentication/register";
    }

    @PostMapping("/register")
    public String postRegister(@ModelAttribute Account account,
                               @RequestParam("retypePassword") String retypePassword,
                               @RequestParam("dob") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dob,
                               Model model) {
        if (!account.getPassword().equals(retypePassword)) {
            model.addAttribute("account", account);
            model.addAttribute("message", "Passwords do not match.");
            return "authentication/register";
        }
        if (accountService.findByUsername(account.getUsername()) != null) {
            model.addAttribute("account", account);
            model.addAttribute("message", "Username is already taken.");
            return "authentication/register";
        }

        account.setDob(dob);
        if (account.getUsername().equals("admin")) {
            account.setRoles(Role.ROLE_ADMIN);
        } else {
            account.setRoles(Role.ROLE_USER);
        }
        account.setActivated(true);

        registerService.registerAccount(account);
        return "redirect:/login";
    }
}