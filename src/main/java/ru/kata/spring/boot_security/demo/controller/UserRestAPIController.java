package ru.kata.spring.boot_security.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.exceptions.MyNFUEx;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserRestAPIController {

    private final UserService userService;
    private final RoleService roleService;

    public UserRestAPIController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Long id) {

        User user = userService.getUserByID(id);

        if (user == null) {
            throw new MyNFUEx("User by id=" + id + " not found");
        }

        return user;
    }

    @GetMapping("/roles")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

}
