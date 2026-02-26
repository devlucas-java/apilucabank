package com.github.devlucasjava.apilucabank.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.awt.*;

@Entity
@Table(name = "admin")
@Data
public class Admin extends Person {
    //  This is necessary because the admin cannot make transactions for anyone.
    //  Isso é necessário porque o administrador não pode efetuar transações para ninguém.
}
