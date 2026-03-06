package com.github.devlucasjava.apilucabank.config;

import com.github.devlucasjava.apilucabank.model.Authority;
import com.github.devlucasjava.apilucabank.model.Role;
import com.github.devlucasjava.apilucabank.repository.AuthorityRepository;
import com.github.devlucasjava.apilucabank.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


// for create roles and authorities for developer

//@Configuration
public class DataInitializer {

    //@Bean
    CommandLineRunner initData(RoleRepository roleRepository, AuthorityRepository authorityRepository) {
        return args -> {

            // Criar authorities
            List<String> authorityNames = List.of(
                    "MANAGE_USER", "READ", "WRITE",
                    "CHAT_USER", "CHAT_ENTERPRISE",
                    "ADMIN_DELETE", "SEND_TRANSACTIONS",
                    "CANCEL_TRANSACTION", "MANAGE_TRANSACTION"
            );

            List<Authority> authorities = authorityNames.stream()
                    .map(name -> Authority.builder().name(name).build())
                    .toList();

            authorityRepository.saveAll(authorities);

            // Criar roles e atribuir authorities
            Role admin = Role.builder()
                    .name("ADMIN")
                    .authorities(authorities) // ADMIN recebe todas
                    .build();

            Role superadmin = Role.builder()
                    .name("SUPERADMIN")
                    .authorities(authorities) // SUPERADMIN recebe todas
                    .build();

            Role user = Role.builder()
                    .name("USER")
                    .authorities(authorities.stream()
                            .filter(a -> List.of("READ", "WRITE", "CHAT_USER").contains(a.getName()))
                            .toList())
                    .build();

            Role enterprise = Role.builder()
                    .name("ENTERPRISE")
                    .authorities(authorities.stream()
                            .filter(a -> List.of("READ", "WRITE", "CHAT_ENTERPRISE",
                                    "SEND_TRANSACTIONS", "CANCEL_TRANSACTION",
                                    "MANAGE_TRANSACTION").contains(a.getName()))
                            .toList())
                    .build();

            roleRepository.saveAll(List.of(admin, superadmin, user, enterprise));

            System.out.println("Roles e Authorities inicializadas com sucesso!");
        };
    }
}