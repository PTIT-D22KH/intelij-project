package com.duongvct.controller.authentication;

import com.duongvct.utils.Role;
import com.duongvct.entity.Account;
import com.duongvct.service.impl.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ChangePasswordController {
    @Autowired
    private AccountServiceImpl accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/change-password")
    public String getChangePassword(Model model, @AuthenticationPrincipal User user) {
        Account account = accountService.findByUsername(user.getUsername());
        if (account.getRoles() == Role.ROLE_EMPLOYEE && account.isFirstLogin() ) {
            model.addAttribute("message", "Please change your password. You are currently using the default password.");
        }
        return "authentication/change-password";
    }

    @PostMapping("/change-password")
    public String postChangePassword(@AuthenticationPrincipal User user,
                                     @RequestParam("currentPassword") String currentPassword,
                                     @RequestParam("newPassword") String newPassword,
                                     @RequestParam("confirmPassword") String confirmPassword,
                                     Model model) {
        Account account = accountService.findByUsername(user.getUsername());

        if (!passwordEncoder.matches(currentPassword, account.getPassword())) {
            model.addAttribute("message", "Current password is incorrect.");
            return "authentication/change-password";
        }

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("message", "New passwords do not match.");
            return "authentication/change-password";
        }

        account.setPassword(passwordEncoder.encode(newPassword));
        account.setFirstLogin(false);
        accountService.save(account);

        model.addAttribute("message", "Password changed successfully.");
        return "redirect:/change-password";
    }
}