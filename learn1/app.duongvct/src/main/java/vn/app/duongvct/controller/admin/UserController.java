package vn.app.duongvct.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.app.duongvct.domain.User;
import vn.app.duongvct.service.UploadService;
import vn.app.duongvct.service.UserService;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@Controller
public class UserController {
    private final UserService userService;
    private final UploadService uploadService;

    public UserController(UserService userService, UploadService uploadService) {
        this.userService = userService;
        this.uploadService = uploadService;
    }

    @GetMapping("")
    public String getHomePage(Model model) {
        List<User> users = this.userService.getAllUsersByEmail("1@gmail.com");
        String message =  this.userService.handleHello();
        model.addAttribute("eric", message);
        return "hello";
    }

    @GetMapping("/admin/user")
    public String getUserPage(Model model) {
        List<User> users = this.userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/user/show";
    }

    @GetMapping("/admin/user/create")
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @PostMapping("/admin/user/create")
    public String createUser(Model model, @ModelAttribute User hoidanit, @RequestParam("hoidanitFile")MultipartFile file) {
        String avatar = this.uploadService.handleSaveUploadFile(file, "avatar");
        System.out.println(avatar);
//        this.userService.handleSaveUser(hoidanit);
        return "redirect:/admin/user";
    }
    @GetMapping("/admin/user/{id}")
    public String getUserDetailPage(Model model, @PathVariable Long id) {
        User user = this.userService.getUserById(id);
        model.addAttribute("user", user);
        return "admin/user/detail";
    }
    @GetMapping("/admin/user/update/{id}")
    public String updateUserDetailPage(Model model, @PathVariable Long id) {
        User user = this.userService.getUserById(id);
        model.addAttribute("user", user);
        return "admin/user/update";
    }
    @PostMapping("/admin/user/update")
    public String postUpdateUser(Model model, @ModelAttribute("user") User user) {
        User currentUser = this.userService.getUserById(user.getId());
        if (currentUser != null) {
            currentUser.setAddress(user.getAddress());
            currentUser.setPhone(user.getPhone());
            currentUser.setFullName(user.getFullName());
            this.userService.handleSaveUser(currentUser);
        }
        return "redirect:/admin/user";
    }
    @GetMapping("/admin/user/delete/{id}")
    public String deleteUserPage(Model model, @PathVariable Long id) {
        model.addAttribute("id", id);
        User user = new User();
        user.setId(id);
        model.addAttribute("newUser",user);
        return "admin/user/delete";
    }

    @PostMapping("/admin/user/delete")
    public String postDeleteUserPage(Model model, @ModelAttribute("newUser") User user) {
        this.userService.deleteUserById(user.getId());
        return "redirect:/admin/user";
    }
}
