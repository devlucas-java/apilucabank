package com.github.devlucasjava.apilucabank.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "transaction_history")
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, updatable = false)
    private LocalDateTime timeStampSender;
    @Column(nullable = false, updatable = false)
    private LocalDateTime timeStampReceiver;

    @Column(updatable = false)
    private String description;

    private String descriptionAdmin;

    @Column(nullable = false, updatable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusEnum status;

    @ManyToOne(fetch = FetchType.LAZY)
    private Users sender;
    @ManyToOne(fetch = FetchType.LAZY)
    private Users receiver;
}
