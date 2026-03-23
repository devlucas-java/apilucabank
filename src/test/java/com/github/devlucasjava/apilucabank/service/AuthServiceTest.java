package com.github.devlucasjava.apilucabank.service;

import com.github.devlucasjava.apilucabank.dto.request.LoginRequest;
import com.github.devlucasjava.apilucabank.dto.request.RegisterRequest;
import com.github.devlucasjava.apilucabank.dto.response.AuthResponse;
import com.github.devlucasjava.apilucabank.exception.CustomAuthException;
import com.github.devlucasjava.apilucabank.exception.InternalErrorServerException;
import com.github.devlucasjava.apilucabank.exception.ResourceConflictException;
import com.github.devlucasjava.apilucabank.model.Role;
import com.github.devlucasjava.apilucabank.model.Users;
import com.github.devlucasjava.apilucabank.repository.RoleRepository;
import com.github.devlucasjava.apilucabank.repository.UsersRepository;
import com.github.devlucasjava.apilucabank.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

        @InjectMocks
        private AuthService authService;

        @Mock
        private RoleRepository roleRepository;

        @Mock
        private UsersRepository usersRepository;

        @Mock
        private PasswordEncoder passwordEncoder;

        @Mock
        private AuthenticationManager authenticationManager;

        @Mock
        private JwtService jwtService;

        private Users user;
        private Role role;
        private RegisterRequest registerRequest;
        private LoginRequest loginRequest;

    @BeforeEach
    void setup() {
        user = new Users();
        user.setId(UUID.randomUUID());
        user.setEmail("lucas@lucas.com");
        user.setPassport("PS123456789");
        user.setPassword("hash-fake");

        role = new Role();
        role.setName("USER");

        user.setRole(role);

        registerRequest = new RegisterRequest();
        registerRequest.setEmail("lucas@lucas.com");
        registerRequest.setPassport("PS123456789");
        registerRequest.setPassword("123456");

        loginRequest = new LoginRequest();
        loginRequest.setLogin("lucas@lucas.com");
        loginRequest.setPassword("123456");
    }

        @Test
        void shouldRegisterSuccessfully() {
            when(usersRepository.findByEmail(registerRequest.getEmail()))
                    .thenReturn(Optional.empty());
            when(usersRepository.findByPassport(registerRequest.getPassport()))
                    .thenReturn(Optional.empty());
            when(roleRepository.findByName("USER"))
                    .thenReturn(Optional.of(role));
            when(passwordEncoder.encode(any()))
                    .thenReturn("hash-fake");
            when(usersRepository.save(any()))
                    .thenReturn(user);
            when(jwtService.generateToken(any()))
                    .thenReturn("token");

            AuthResponse response = authService.register(registerRequest);

            assertNotNull(response);
            assertEquals("token", response.getAccessToken());

            verify(passwordEncoder).encode(registerRequest.getPassword());
            verify(jwtService).generateToken(any());
        }

        @Test
        void shouldThrowWhenRoleNotFound() {
            when(usersRepository.findByEmail(any())).thenReturn(Optional.empty());
            when(usersRepository.findByPassport(any())).thenReturn(Optional.empty());
            when(roleRepository.findByName("USER"))
                    .thenReturn(Optional.empty());

            assertThrows(InternalErrorServerException.class,
                    () -> authService.register(registerRequest));
        }

        @Test
        void shouldThrowWhenEmailAlreadyExists() {
            when(usersRepository.findByEmail(registerRequest.getEmail()))
                    .thenReturn(Optional.of(user));

            assertThrows(ResourceConflictException.class,
                    () -> authService.register(registerRequest));
        }

        @Test
        void shouldThrowWhenPassportAlreadyExists() {
            when(usersRepository.findByEmail(registerRequest.getEmail()))
                    .thenReturn(Optional.empty());
            when(usersRepository.findByPassport(registerRequest.getPassport()))
                    .thenReturn(Optional.of(user));

            assertThrows(ResourceConflictException.class,
                    () -> authService.register(registerRequest));
        }

        @Test
        void shouldAuthenticateSuccessfully() {
            when(jwtService.generateToken(any())).thenReturn("token");
            when(usersRepository.findByEmailOrPassport(loginRequest.getLogin()))
                    .thenReturn(Optional.of(user));

            AuthResponse response = authService.authenticate(loginRequest);

            assertNotNull(response);
            assertEquals("token", response.getAccessToken());

            verify(authenticationManager).authenticate(any());
            verify(jwtService).generateToken(user);
        }

        @Test
        void shouldThrowWhenAuthenticationFails() {
            doThrow(new RuntimeException())
                    .when(authenticationManager)
                    .authenticate(any());

            assertThrows(CustomAuthException.class,
                    () -> authService.authenticate(loginRequest));
        }

        @Test
        void shouldThrowWhenUserNotFoundAfterAuth() {
            when(usersRepository.findByEmailOrPassport(any()))
                    .thenReturn(Optional.empty());

            assertThrows(CustomAuthException.class,
                    () -> authService.authenticate(loginRequest));
        }
    }
