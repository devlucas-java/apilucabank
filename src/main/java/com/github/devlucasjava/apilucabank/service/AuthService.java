package com.github.devlucasjava.apilucabank.service;


import com.github.devlucasjava.apilucabank.dto.request.LoginRequest;
import com.github.devlucasjava.apilucabank.dto.request.RegisterRequest;
import com.github.devlucasjava.apilucabank.dto.response.AuthResponse;
import com.github.devlucasjava.apilucabank.exception.CustomAuthenticationException;
import com.github.devlucasjava.apilucabank.exception.InternalServerErrorException;
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
        if (usersRepository.findByEmail(request.getEmail()).isPresent()){
            throw new ResourceConflictException("Email");
        }
        if (usersRepository.findByPassaport(request.getPassaport()).isPresent()){
            throw new ResourceConflictException("Passaport");
        }
        Role role = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new InternalServerErrorException("Error in registering role"));

        Users users = Users.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .passaport(request.getPassaport())
                .birthDate(request.getBirthDate())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();
        usersRepository.save(users);
        String token = jwtService.generateToken(users);

        return AuthResponse.builder()
                .email(users.getEmail())
                .role(users.getRole().toString())
                .expiresIn(jwtService.jwtExpiration)
                .tokenType("Beaver")
                .accessToken(token).build();
    };


    public AuthResponse authenticate(LoginRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()
                )
        );
        Users users = usersRepository.findByEmailOrPasaport(request.getLogin())
                .orElseThrow(() -> new CustomAuthenticationException("User not found"));

        final String token = jwtService.generateToken(users);

        return AuthResponse.builder()
                .email(users.getEmail())
                .role(users.getRole().toString())
                .expiresIn(jwtService.jwtExpiration)
                .accessToken(token)
                .tokenType("Beaver").build();
    }
}
