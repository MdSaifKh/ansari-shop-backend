package com.ansarishop.app.controller;

import com.ansarishop.app.entity.User;
import com.ansarishop.app.service.UserService;
import com.ansarishop.app.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/common")
@Validated
public class ShopController {
    @Autowired
    private UserServiceImpl userService;
    @GetMapping("/home")
    public String home(){
        return "Welcome home";
    }
    @PostMapping("/user")
    public ResponseEntity<User> saveUser(@RequestBody User user){
        User savedUser = userService.saveUser(user);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUser(){
        return ResponseEntity.ok(userService.getAll());
    }
}
