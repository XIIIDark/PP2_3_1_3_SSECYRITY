package ru.kata.spring.boot_security.demo.controller;



import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;


import javax.persistence.Id;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/admin")
    public String printWelcome(ModelMap model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }

    @GetMapping(value = "/delete")
    public String delete(Long id) {
        userService.deleteUserByID(id);
        return "redirect:/admin";
    }

    @GetMapping(value = "/update")
    public String update(ModelMap modelMap, Long id) {
        modelMap.addAttribute("user", userService.getUserByID(id));
        return "update";
    }

    @PostMapping(value = "/save")
    public String save(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @GetMapping(value = "/add")
    public String create(ModelMap modelMap) {
        modelMap.addAttribute("user", new User())
                .addAttribute("allRoles", new ArrayList<Role>(Arrays.asList(
                        new Role(1L, "ROLE_ADMIN"),
                        new Role(2L,"ROLE_USER")
                        )));

        return "add";
    }

    @GetMapping(value = "/user")
    public String userPage(HttpSession httpSession) {
        httpSession.getAttribute("user");
        System.out.println(httpSession.getAttribute("user"));
        return "user";
    }

}
