package com.restaurantdelivery.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.restaurantdelivery.dto.request.LoginRequest;
import com.restaurantdelivery.dto.request.RegisterUserRequest;
import com.restaurantdelivery.dto.request.ResetPasswordRequest;
import com.restaurantdelivery.dto.response.UserResponse;
import com.restaurantdelivery.entity.User;
import com.restaurantdelivery.enums.UserRole;
import com.restaurantdelivery.exception.UnauthorizedException;
import com.restaurantdelivery.mapper.AuthMapper;
import com.restaurantdelivery.repository.UserRepository;
import com.restaurantdelivery.security.JwtUtil;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
 
    @Mock
    private UserRepository userRepository;
 
    @Mock
    private PasswordEncoder passwordEncoder;
 
    @Mock
    private AuthenticationManager authenticationManager;
 
    @Mock
    private JwtUtil jwtUtil;
 
    @Mock
    private AuthMapper authMapper;
 
    @InjectMocks
    private AuthService authService;
    
    //SUCCESS
    @Test
    void registerUser_success() {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setEmail("test@gmail.com");
        request.setPassword("password");
     
        User user = new User();
        user.setEmail("test@gmail.com");
     
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPwd");
        when(authMapper.toUserEntity(request, "encodedPwd")).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(authMapper.toUserResponse(user)).thenReturn(new UserResponse());
     
        ResponseEntity<?> response = authService.registerUser(request);
     
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
    
    @Test
    void login_success() {
        LoginRequest request = new LoginRequest();
        request.setEmail("admin@gmail.com");
        request.setPassword("password");
     
        User user = new User();
        user.setEmail("admin@gmail.com");
        user.setRole(UserRole.ADMIN);
     
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        when(jwtUtil.generateToken(user.getEmail(), user.getRole().name()))
                .thenReturn("jwt-token");
     
        ResponseEntity<?> response = authService.login(request);
     
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    
    @Test
    void resetPassword_success() {
        ResetPasswordRequest request = new ResetPasswordRequest();
        request.setUserId(1L);
        request.setCurrentPassword("oldPwd");
        request.setNewPassword("newPwd");
     
        User user = new User();
        user.setPassword("encodedOldPwd");
     
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("oldPwd", "encodedOldPwd")).thenReturn(true);
        when(passwordEncoder.encode("newPwd")).thenReturn("encodedNewPwd");
     
        ResponseEntity<?> response = authService.resetPassword(request);
     
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    
    //FAILURE
    @Test
    void registerUser_emailAlreadyExists() {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setEmail("test@gmail.com");
     
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);
     
        assertThrows(RuntimeException.class,
                () -> authService.registerUser(request));
    }
    
    
    @Test
    void login_userNotFound() {
        LoginRequest request = new LoginRequest();
        request.setEmail("unknown@gmail.com");
     
        when(userRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.empty());
     
        assertThrows(UsernameNotFoundException.class,
                () -> authService.login(request));
    }
    
    @Test
    void resetPassword_wrongCurrentPassword() {
        ResetPasswordRequest request = new ResetPasswordRequest();
        request.setUserId(1L);
        request.setCurrentPassword("wrongPwd");
     
        User user = new User();
        user.setPassword("encodedPwd");
     
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongPwd", "encodedPwd"))
                .thenReturn(false);
     
        assertThrows(UnauthorizedException.class,
                () -> authService.resetPassword(request));
    }
}
