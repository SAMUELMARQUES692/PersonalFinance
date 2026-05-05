package dev.samuel.PersonalFinance.DTOs;

import java.math.BigDecimal;

public record CategorySummaryDTO(
        String categoryName,
        BigDecimal totalAmount

) {
}
