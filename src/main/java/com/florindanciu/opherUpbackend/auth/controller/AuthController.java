package com.florindanciu.opherUpbackend.auth.controller;

import com.florindanciu.opherUpbackend.auth.dto.LoginRequest;
import com.florindanciu.opherUpbackend.auth.dto.SignupRequest;
import com.florindanciu.opherUpbackend.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.authenticateUser(loginRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        if (authService.registerUser(signUpRequest)) {
            return ResponseEntity.ok("AppUser registered successfully!");
        }
        return ResponseEntity
                .badRequest()
                .body("Error: Username or Email is already taken!");
    }
}
