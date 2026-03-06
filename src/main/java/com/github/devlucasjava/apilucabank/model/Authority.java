package com.github.devlucasjava.apilucabank.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Authority implements GrantedAuthority {

    @Id
    @ToString.Include
    private String name;

    @Override
    public String getAuthority() {
        return this.name;
    }
}