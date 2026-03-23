package com.github.devlucasjava.apilucabank.config;

import com.github.devlucasjava.apilucabank.model.Authority;
import com.github.devlucasjava.apilucabank.model.Role;
import com.github.devlucasjava.apilucabank.model.Users;
import com.github.devlucasjava.apilucabank.repository.AuthorityRepository;
import com.github.devlucasjava.apilucabank.repository.RoleRepository;
import com.github.devlucasjava.apilucabank.repository.UsersRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;


// for create roles and authorities for developer
@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(RoleRepository roleRepository, AuthorityRepository authorityRepository,
    UsersRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {

           if (!roleRepository.findByName("USER").isPresent() && !roleRepository.findByName("ADMIN").isPresent()){
                List<String> authorityNames = List.of(
                        "MANAGE_USER", "ACCOUNT",
                        "USER","ADMIN","ENTERPRISE",
                        "CHAT_USER", "CHAT_ENTERPRISE",
                        "ADMIN_DELETE", "SEND_TRANSACTIONS",
                        "CANCEL_TRANSACTION", "MANAGE_TRANSACTION"
                );

                List<Authority> authorities = authorityNames.stream()
                        .map(name -> Authority.builder().name(name).build())
                        .toList();

                authorityRepository.saveAll(authorities);

                Role admin = Role.builder()
                        .name("ADMIN")
                        .authorities(authorities)
                        .build();

                Role user = Role.builder()
                        .name("USER")
                        .authorities(authorities.stream()
                                .filter(a -> List.of("ACCOUNT","USER", "SEND_TRANSACTIONS", "CHAT_USER").contains(a.getName()))
                                .toList())
                        .build();

                Role enterprise = Role.builder()
                        .name("ENTERPRISE")
                        .authorities(authorities.stream()
                                .filter(a -> List.of("ACCOUNT", "CHAT_ENTERPRISE",
                                        "SEND_TRANSACTIONS","ENTERPRISE", "CANCEL_TRANSACTION",
                                        "MANAGE_TRANSACTION").contains(a.getName()))
                                .toList())
                        .build();

                roleRepository.saveAll(List.of(admin, user, enterprise));

                System.out.println("Create Roles and Authorities successfully!");
            }

            if (userRepository.findByEmail("admin@admin.com").isPresent()) {
                System.out.println("Admin already exists.");
                return;
            }

            Role adminRole = roleRepository.findByName("ADMIN")
                    .orElseThrow(() -> new RuntimeException("Role ADMIN not found"));

            Users admin = Users.builder()
                    .firstName("Admin")
                    .lastName("System")
                    .email("admin@admin.com")
                    .password(passwordEncoder.encode("@Admin123"))
                    .birthDate(LocalDate.of(2004, 8, 14))
                    .passport("ADMIN000")
                    .role(adminRole)
                    .isActive(true)
                    .isLocked(false)
                    .build();

            userRepository.save(admin);

            System.out.println("Admin user created successfully!");
        };
    }
}