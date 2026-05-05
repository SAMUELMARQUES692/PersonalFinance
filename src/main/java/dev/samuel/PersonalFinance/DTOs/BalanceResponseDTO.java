package dev.samuel.PersonalFinance.DTOs;

import java.math.BigDecimal;

public record BalanceResponseDTO(
        BigDecimal totalIncome,
        BigDecimal totalExpense,
        BigDecimal balance
) {}
