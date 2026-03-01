package com.github.devlucasjava.apilucabank.service;


import com.github.devlucasjava.apilucabank.dto.mapper.AuthMapper;
import com.github.devlucasjava.apilucabank.dto.mapper.RegisterMapper;
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
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {

        if (usersRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResourceConflictException("Email already registered");
        }
        if (usersRepository.findByPassport(request.getPassport()).isPresent()) {
            throw new ResourceConflictException("Passport already registered");
        }
         final Role role = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new InternalErrorServerException("Default role not configured"));

        Users user = RegisterMapper.toUsers(request);
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        usersRepository.save(user);

        final String token = jwtService.generateToken(user);

        AuthResponse response = AuthMapper.toAuthResponse(user);
        response.setAccessToken(token);
        response.setExpiresIn(jwtService.jwtExpiration);

        return response;
    }


    public AuthResponse authenticate(LoginRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()
                )
        );
        Users users = usersRepository.findByEmailOrPassport(request.getLogin())
                .orElseThrow(() -> new CustomAuthException("User not found"));

        final String token = jwtService.generateToken(users);
        AuthResponse response = AuthMapper.toAuthResponse(users);
        response.setAccessToken(token);
        response.setExpiresIn(jwtService.jwtExpiration);
        return response;
    }
}
