package com.example.Student.OAuth.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // Delegate to the default user service
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // Log user attributes
        System.out.println("OAuth2 User Attributes: " + oAuth2User.getAttributes());

        // Extract user attributes
        Map<String, Object> userAttributes = oAuth2User.getAttributes();
        String name = (String) userAttributes.get("name");
        String email = (String) userAttributes.get("email");

        // Check for missing attributes
        if (name == null || email == null) {
            throw new OAuth2AuthenticationException(new OAuth2Error("invalid_token", "Missing required attributes: name or email", null));
        }

        // Log the access token (if needed)
        String accessToken = userRequest.getAccessToken().getTokenValue();
        System.out.println("Access Token: " + accessToken);

        // Create a DefaultOAuth2User with roles
        return new DefaultOAuth2User(
                Collections.singletonList(() -> "ROLE_USER"), // Set roles
                userAttributes, // Use user attributes from OAuth2 provider
                "email" // Attribute to be used as the primary identifier
        );
    }
}