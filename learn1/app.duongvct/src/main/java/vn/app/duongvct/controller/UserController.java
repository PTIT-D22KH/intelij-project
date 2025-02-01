package vn.app.duongvct.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.app.duongvct.domain.User;
import vn.app.duongvct.service.UserService;

@Controller
public class UserController {
    private UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String getHomePage(Model model) {
        String message =  userService.handleHello();
        model.addAttribute("eric", message);
        return "hello";
    }

    @GetMapping("/admin/user")
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @PostMapping("/admin/user/create")
    @ResponseBody
    public String createUser(Model model, @ModelAttribute User hoidanit) {
        System.out.println(hoidanit);
        return "Hello";
    }
}
