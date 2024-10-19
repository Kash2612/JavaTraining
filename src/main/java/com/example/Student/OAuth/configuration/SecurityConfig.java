package com.example.Student.OAuth.configuration;

import com.example.Student.OAuth.service.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Define URL authorization rules
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/students/**").authenticated()
                        .requestMatchers("/user").authenticated()// Protect student endpoints
                        .anyRequest().permitAll() // Allow other requests
                )
                // Enable OAuth2 login functionality
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/oauth2/authorization/google")
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )        // Redirect to Google login
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                )
                // Exception handling for unauthenticated users
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/oauth2/authorization/google")) // Redirect to Google login if unauthenticated
                )
                // Logout configuration
                .logout(logout -> logout
                        .logoutSuccessUrl("/") // Redirect to home after logout
                        .permitAll()
                );

        return http.build();
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
            // Map JWT claims to authorities if necessary
            return jwt.getClaimAsStringList("scope").stream()
                    .map(scope -> new SimpleGrantedAuthority(scope))
                    .collect(Collectors.toList());
        });
        return jwtAuthenticationConverter;
    }


//    @Bean
//    public UserDetailsService userDetailsService() {
//        // This is for testing, but will be overridden when OAuth2 login is used
//        return new InMemoryUserDetailsManager(
//                User.withDefaultPasswordEncoder() // Not recommended for production
//                        .username("user")
//                        .password("password")
//                        .roles("USER")
//                        .build()
//        );
//    }

    @Bean
    public JwtDecoder jwtDecoder() {
        String issuerUri = "https://accounts.google.com"; // Google's issuer URI
        return NimbusJwtDecoder.withJwkSetUri("https://www.googleapis.com/oauth2/v3/certs").build();
    }


//    GITHUB


//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
//                        .requestMatchers("/students/**").authenticated() // Protect student endpoints
//                        .anyRequest().permitAll() // Allow other requests
//                )
//                .oauth2Login(oauth2 -> oauth2
//                        .loginPage("/oauth2/authorization/github") // Redirect to GitHub login
//                )
//                .logout(logout -> logout
//                        .logoutSuccessUrl("/") // Redirect to home after logout
//                        .permitAll()
//                )
//                .build();
//    }
}
