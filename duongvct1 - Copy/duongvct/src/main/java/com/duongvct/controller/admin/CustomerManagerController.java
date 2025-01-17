package com.duongvct.controller.admin;

import com.duongvct.entity.Account;
import com.duongvct.service.impl.AccountServiceImpl;
import com.duongvct.service.impl.RegisterServiceImpl;
import com.duongvct.utils.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Autowired
    private RegisterServiceImpl registerService;


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping("")
    public String getCustomerManager(@RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "size", defaultValue = "1") int size,
                                     @RequestParam(value = "sortField", defaultValue = "id") String sortField,
                                     @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
                                     @RequestParam(value = "searchColumn", required = false) String searchColumn,
                                     @RequestParam(value = "searchValue", required = false) String searchValue,
                                     Model model) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(sortDir), sortField);
        Page<Account> accounts;
//        List<Account> temp = accountService.findAll();
//        List<Account> res = new ArrayList<>();
        Page<Account> temp = accountService.findAll(pageable);
        if (searchColumn != null && searchValue != null) {
            accounts = accountService.searchCustomers(searchColumn, searchValue, pageable);
        } else {
            accounts = accountService.findByRole(Role.ROLE_USER, pageable);
        }


        model.addAttribute("accounts", accounts);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", accounts.getTotalPages());
        model.addAttribute("totalItems", accounts.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        return "admin/customer/customer-management";
    }

    @GetMapping("/add")
    public String addCustomer(Model model) {
        model.addAttribute("account", new Account());
        return "admin/customer/add-customer";
    }

    @PostMapping("/add")
    public String addCustomer(@ModelAttribute Account account, @RequestParam("dob") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dob, Model model) {
        if (accountService.findByUsername(account.getUsername()) != null) {
            model.addAttribute("account", account);
            model.addAttribute("message", "Username is already taken.");
            return "admin/customer/add-customer";
        }
        account.setDob(dob);
        account.setRoles(Role.ROLE_USER);
        registerService.registerAccount(account);
        return "redirect:/admin/customer";
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

    @PostMapping("/delete")
    public String deleteSelected(@RequestParam("selectedIds") List<Long> selectedIds) {
        for (Long id : selectedIds) {
            accountService.deleteById(id);
        }
        return "redirect:/admin/customer";
    }

    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        accountService.deleteById(id);
        return "redirect:/admin/customer";
    }
}
