package com.github.devlucasjava.apilucabank.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Authority {
    @Id
    private String name;
}