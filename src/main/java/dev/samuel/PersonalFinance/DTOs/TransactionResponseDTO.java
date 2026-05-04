package dev.samuel.PersonalFinance.DTOs;

import dev.samuel.PersonalFinance.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record TransactionResponseDTO(
        Long id,
        String description,
        BigDecimal amount,
        TransactionType type,
        LocalDate date,
        String categoryName,
        LocalDateTime createdAt
) {}
