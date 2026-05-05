package dev.samuel.PersonalFinance.DTOs;

import java.math.BigDecimal;
import java.util.List;

public record MonthlyReportDTO(
        int month,
        int year,
        BigDecimal totalIncome,
        BigDecimal totalExpense,
        BigDecimal balance,
        List<CategorySummaryDTO> expensesByCategory
) {
}
