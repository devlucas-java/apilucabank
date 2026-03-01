package com.github.devlucasjava.apilucabank.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Data
public class Authority implements GrantedAuthority {

    @Id
    @ToString.Include
    private String name;

    @Override
    public String getAuthority() {
        return this.name;
    }
}