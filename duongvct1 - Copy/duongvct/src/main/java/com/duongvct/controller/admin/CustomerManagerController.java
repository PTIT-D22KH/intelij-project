package com.duongvct.controller.admin;

import com.duongvct.entity.Account;
import com.duongvct.service.impl.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin/customer")
public class CustomerManagerController {
    @Autowired
    private AccountServiceImpl accountService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
    @GetMapping("")
    public String getCustomerManager(Model model) {
        List<Account> temp = accountService.findAll();
        List<Account> res = new ArrayList<>();
        for (Account account : temp) {
            System.out.println(account);
            if (account.getRoles().getId().equals("ROLE_USER")) {
                res.add(account);
            }
        }
        model.addAttribute("accounts", res);
        return "admin/customer/customer-management";
    }

    @GetMapping("/edit/{id}")
    public String editCustomer(@PathVariable Long id, Model model) {
        Account account = accountService.findById(id);
        model.addAttribute("account", account);
        return "admin/customer/edit-customer";
    }

    @PostMapping("/edit/{id}")
    public String updateCustomer(@ModelAttribute Account account, @PathVariable Long id, @RequestParam("dob") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dob) {
        Account existingAccount = accountService.findById(id);
        existingAccount.setFullname(account.getFullname());
        existingAccount.setEmail(account.getEmail());
        existingAccount.setAddress(account.getAddress());
        existingAccount.setDob(dob);
        if (existingAccount.getRoles().getId().equals("ROLE_ADMIN") || existingAccount.getRoles().getId().equals("ROLE_EMPLOYEE")) {
            existingAccount.setSalary(account.getSalary());
        }
        accountService.save(existingAccount);
        return "redirect:/admin/customer";
    }
}
