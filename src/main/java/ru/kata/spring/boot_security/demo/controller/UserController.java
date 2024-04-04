package ru.kata.spring.boot_security.demo.controller;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class UserController {

    @GetMapping("/")
    public String getIndex() {
        return "pages/index";
    }

}
