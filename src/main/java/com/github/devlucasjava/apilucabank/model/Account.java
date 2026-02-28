package com.github.devlucasjava.apilucabank.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Table
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToMany
    private List<Transactions> transactionsList;

    private BigDecimal balance =  BigDecimal.ZERO;

    private BigDecimal maxAmountTransaction = BigDecimal.valueOf(1000);

    @Column(nullable = false)
    private boolean isBlocked;
    @Column(nullable = false)
    private LocalDateTime blockedDate;

    @OneToOne(fetch = FetchType.LAZY)
    private Users user;
}
