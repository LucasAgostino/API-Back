package com.api.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.api.DTO.UserDto;
import com.api.api.service.Interfaces.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // Endpoint POST para obtener todos los usuarios
    @GetMapping("/get")
    public List<UserDto> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/get/{id}")
    public Optional<UserDto> getById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping("/get/byname")
    public Optional<UserDto> getByEmail(@RequestParam String email) {
        return userService.findByEmail(email);
    }
    
    @GetMapping("/current")
    public Optional<UserDto> getCurrentUser() {
        return userService.getCurrentUserDto();
    }
}

