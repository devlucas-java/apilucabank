package com.github.devlucasjava.apilucabank.dto.response;

import com.github.devlucasjava.apilucabank.model.Transactions;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Account response")
public class AccountResponse {

    @Schema(description = "List of transactions")
    private List<Transactions> transactionsList;

    @Schema(description = "Balance total", example = "2500")
    private BigDecimal balance;

    @Schema(description = "Maximum amount of money you can transfer in a transaction", example = "1000")
    private BigDecimal maxAmountTransaction;

    @Schema(description = "User is blocked?", example = "false")
    private boolean isBlockef;

    @Schema(description = "User role", example = "2026-02-27T17:50:18")
    private LocalDateTime blockedDate;
}