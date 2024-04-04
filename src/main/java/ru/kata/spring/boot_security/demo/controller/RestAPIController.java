package ru.kata.spring.boot_security.demo.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.exceptions.MyNFUEx;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestAPIController {

    private UserService userService;

    private RoleService roleService;

    public RestAPIController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {


        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Long id) {

        User user = userService.getUserByID(id);

        if (user == null) {
            throw new MyNFUEx("User by id=" + id + " not found");
        }

        return user;
    }

    @PostMapping("/users")
    public User createNewUser(@RequestBody User user) {

        return userService.createUser(user);
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {

        return userService.updateUser(user);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable Long id) {
        System.out.println(id);
        userService.deleteUserByID(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/roles")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

}
