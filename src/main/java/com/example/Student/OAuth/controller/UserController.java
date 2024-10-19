package com.example.Student.OAuth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
public class UserController {

    @GetMapping("/user")
    public Map<String, Object> getUser(Authentication authentication) {
        if (authentication == null) {
            throw new IllegalStateException("Authentication is null. Please login.");
        }

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> userAttributes = oAuth2User.getAttributes();

        String name = (String) userAttributes.get("name"); // Adjust according to your OAuth provider
        String email = (String) userAttributes.get("email"); // Adjust according to your OAuth provider

        return Map.of(
                "name", name,
                "email", email,
                "attributes", userAttributes
        );
    }
}
