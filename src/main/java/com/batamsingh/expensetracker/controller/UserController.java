package com.batamsingh.expensetracker.controller;

import com.batamsingh.expensetracker.model.Users;
import com.batamsingh.expensetracker.service.JwtService;
import com.batamsingh.expensetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody Users user) {
        Users registeredUser = service.register(user);

        // automatically log in the user and generate a jwt token
        String token;
        try {
            token = new JwtService().generateToken(registeredUser.getUsername());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("user", registeredUser);
        response.put("token", token);

        return ResponseEntity.ok(response);


//        return service.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody Users user) {

        return service.verify(user);

//        String token = service.verify(user); // Returns JWT token
//        Users authenticatedUser = service.findByUsername(user.getUsername()); // Fetch user details
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("token", token);
//        response.put("user", authenticatedUser);
//        return ResponseEntity.ok(response);

    }

    @GetMapping("/me")
    public ResponseEntity<Users> fetchUser(Principal principal) {
        Users user = service.findByUsername(principal.getName());
        return ResponseEntity.ok(user);
    }


}
