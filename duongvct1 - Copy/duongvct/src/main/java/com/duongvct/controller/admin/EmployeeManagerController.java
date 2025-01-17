package com.duongvct.controller.admin;

import com.duongvct.service.impl.RegisterServiceImpl;
import com.duongvct.utils.Role;
import com.duongvct.entity.Account;
import com.duongvct.service.impl.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin/employee")
public class EmployeeManagerController {
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

//    @GetMapping("")
//    public String getEmployeeManager(@RequestParam(value = "searchColumn", required = false) String searchColumn,
//                                     @RequestParam(value = "searchValue", required = false) String searchValue, Model model) {
//        List<Account> temp = accountService.findAll();
//        List<Account> res = new ArrayList<>();
//        if (searchColumn != null && searchValue != null) {
//            res = accountService.searchEmployees(searchColumn, searchValue);
//        } else {
//            for (Account account : temp) {
//                if (account.getRoles().getId().equals("ROLE_EMPLOYEE")) {
//                    res.add(account);
//                }
//            }
//        }
//
//        model.addAttribute("accounts", res);
//        return "admin/employee/employee-management";
//    }

    @GetMapping("")
    public String getEmployeeManager(@RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "size", defaultValue = "1") int size,
                                     @RequestParam(value = "sortField", defaultValue = "id") String sortField,
                                     @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
                                     @RequestParam(value = "searchColumn", required = false) String searchColumn,
                                     @RequestParam(value = "searchValue", required = false) String searchValue,
                                     Model model) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDir), sortField));
        Page<Account> accounts;
        if (searchColumn != null && searchValue != null) {
            accounts = accountService.searchEmployees(searchColumn, searchValue, pageable);
        } else {
            accounts = accountService.findByRole(Role.ROLE_EMPLOYEE, pageable);
        }
        model.addAttribute("accounts", accounts);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", accounts.getTotalPages());
        model.addAttribute("totalItems", accounts.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        return "admin/employee/employee-management";
    }

    @GetMapping("/edit/{id}")
    public String editEmployee(@PathVariable Long id, Model model) {
        Account account = accountService.findById(id);
        model.addAttribute("account", account);
        return "admin/employee/edit-employee";
    }

    @PostMapping("/edit/{id}")
    public String updateEmployee(@ModelAttribute Account account, @PathVariable Long id, @RequestParam("dob") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dob) {
        Account existingAccount = accountService.findById(id);
        existingAccount.setFullname(account.getFullname());
        existingAccount.setEmail(account.getEmail());
        existingAccount.setAddress(account.getAddress());
        existingAccount.setDob(dob);
        existingAccount.setSalary(account.getSalary());
        accountService.save(existingAccount);
        return "redirect:/admin/employee";
    }

    @PostMapping("/delete")
    public String deleteSelected(@RequestParam("selectedIds") List<Long> selectedIds) {
        for (Long id : selectedIds) {
            accountService.deleteById(id);
        }
        return "redirect:/admin/employee";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        accountService.deleteById(id);
        return "redirect:/admin/employee";
    }
    @GetMapping("/add")
    public String addEmployeeForm(Model model) {
        model.addAttribute("account", new Account());
        return "admin/employee/add-employee";
    }

    @PostMapping("/add")
    public String addEmployee(@ModelAttribute Account account, @RequestParam("dob") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dob, Model model) {
        if (accountService.findByUsername(account.getUsername()) != null) {
            model.addAttribute("account", account);
            model.addAttribute("message", "Username is already taken.");
            return "admin/employee/add-employee";
        }
        account.setDob(dob);
        account.setRoles(Role.ROLE_EMPLOYEE);
        registerService.registerAccount(account);
        return "redirect:/admin/employee";
    }

    @GetMapping("/deactivate/{id}")
    public String deactivateEmployee(@PathVariable Long id) {
        Account account = accountService.findById(id);
        account.setActivated(!account.isActivated());
        accountService.save(account);
        return "redirect:/admin/employee";
    }
}