package com.example.studentapp.studentSB.service;

import com.example.studentapp.studentSB.entity.UserEntity;
import com.example.studentapp.studentSB.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MyUserDetailsServiceTest {

    @InjectMocks
    private MyUserDetailsService myUserDetailsService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testLoadUserByUsername_Success() {
        // Arrange
        UserEntity user = new UserEntity();
        user.setUsername("john");
        user.setPassword("password");

        when(userRepository.findByUsername("john")).thenReturn(user);

        // Act
        var userDetails = myUserDetailsService.loadUserByUsername("john");

        // Assert
        assertNotNull(userDetails);
        assertEquals("john", userDetails.getUsername());
    }

    @Test
    void testLoadUserByUsername_ThrowsException() {
        // Arrange
        when(userRepository.findByUsername("unknown")).thenReturn(null);

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> {
            myUserDetailsService.loadUserByUsername("unknown");
        });
    }
}
