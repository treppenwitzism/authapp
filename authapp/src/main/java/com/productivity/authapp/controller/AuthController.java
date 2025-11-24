package com.productivity.authapp.controller;

import com.productivity.authapp.model.User;
import com.productivity.authapp.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    // Serve the login/signup HTML page
    @GetMapping("/")
    public String index() {
        return "index"; // returns index.html from templates folder
    }

    // Handle login
    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestBody User user) {
        return authService.login(user);
    }

    // Handle signup
    @PostMapping("/signup")
    @ResponseBody
    public String signup(@RequestBody User user) {
        return authService.signUp(user);
    }
}