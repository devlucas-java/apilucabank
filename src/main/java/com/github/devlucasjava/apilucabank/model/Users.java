package com.github.devlucasjava.apilucabank.model;

import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;

import java.math.BigDecimal;
import java.util.List;

public class Users extends Person {


    @OneToMany
    private List<Transactions> transactionsList;

    private BigDecimal balance =  BigDecimal.ZERO;

    private BigDecimal maxAmountTransaction = BigDecimal.valueOf(1000);




}
