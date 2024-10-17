package com.example.studentapp.studentSB.controller;

import com.example.studentapp.studentSB.dto.AuthenticationRequest;
import com.example.studentapp.studentSB.dto.AuthenticationResponse;
import com.example.studentapp.studentSB.entity.UserEntity;
import com.example.studentapp.studentSB.repository.UserRepository;
import com.example.studentapp.studentSB.service.MyUserDetailsService;
import com.example.studentapp.studentSB.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;



@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserEntity user) {
        // Check if user already exists
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save the user to the database
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");

        try {
            // Validate input
            if (username == null || password == null) {
                return ResponseEntity.badRequest().body("Username and password must be provided.");
            }

            logger.info("Attempting to log in user: {}", username);

            // Find the user by username
            UserEntity user = userRepository.findByUsername(username);
            if (user == null) {
                logger.warn("Login failed: User not found for username: {}", username);
                return ResponseEntity.badRequest().body("Invalid username or password");
            }

            // Validate the password
            if (!passwordEncoder.matches(password, user.getPassword())) {
                logger.warn("Login failed: Incorrect password for username: {}", username);
                return ResponseEntity.badRequest().body("Invalid username or password");
            }

            // Generate token if authentication is successful
            String token = jwtUtil.generateToken(username);
            logger.info("Login successful for user: {}", username);

            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Login error for user: {}. Exception: {}", username, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request.");
        }
    }

}
