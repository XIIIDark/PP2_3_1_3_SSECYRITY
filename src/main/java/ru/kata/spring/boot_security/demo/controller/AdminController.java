package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final RoleService roleService;
    private final UserService userService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping
    public String printWelcome(ModelMap model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles",roleService.getAllRoles());
        model.addAttribute("newUser", new User());
        return "admin";
    }

    @PostMapping(value = "/delete/{id}")
    public String delete( @PathVariable Long id) {
        userService.deleteUserByID(id);
        return "redirect:/admin";
    }

    @PostMapping(value = "/save")
    public String save(@ModelAttribute("user") User user) {
        userService.createUser(user);
        return "redirect:/admin";
    }

    @PostMapping(value = "/update/{id}")
    public String saveUpdate(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/admin";
    }



}
