package dev.samuel.PersonalFinance.service;

import dev.samuel.PersonalFinance.DTOs.BalanceResponseDTO;
import dev.samuel.PersonalFinance.DTOs.CategorySummaryDTO;
import dev.samuel.PersonalFinance.DTOs.MonthlyReportDTO;
import dev.samuel.PersonalFinance.enums.TransactionType;
import dev.samuel.PersonalFinance.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReportService {

    private final TransactionRepository transactionRepository;

    public BalanceResponseDTO getBalance(Long userId) {
        BigDecimal totalIncome = transactionRepository.sumByUserIdAndType(userId, TransactionType.INCOME);
        BigDecimal totalExpense = transactionRepository.sumByUserIdAndType(userId, TransactionType.EXPENSE);
        BigDecimal balance = totalIncome.subtract(totalExpense);

        return new BalanceResponseDTO(totalIncome, totalExpense, balance);
    }

    public MonthlyReportDTO getMonthlyReport(Long userId, int month, int year) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        BigDecimal totalIncome = transactionRepository.sumByUserIdAndTypeAndPeriod(userId, TransactionType.INCOME, start, end);
        BigDecimal totalExpense = transactionRepository.sumByUserIdAndTypeAndPeriod(userId, TransactionType.EXPENSE, start, end);

        List<CategorySummaryDTO> expensesByCategory = transactionRepository
                .sumExpensesByCategoryAndUserIdAndPeriod(userId, start, end)
                .stream()
                .map(record -> new CategorySummaryDTO((String) record[0], (BigDecimal) record[1]))
                .toList();

        return new MonthlyReportDTO(month, year, totalIncome, totalExpense, totalIncome.subtract(totalExpense), expensesByCategory);
    }

    public List<CategorySummaryDTO> getExpensesByCategory(Long userId) {

        return transactionRepository.sumExpensesByCategoryAndUserId(userId)
                .stream()
                .map(record -> new CategorySummaryDTO((String) record[0], (BigDecimal) record[1]))
                .toList();
    }

    public List<CategorySummaryDTO> getExpensesByCategoryAndPeriod(Long userId, LocalDate start, LocalDate end) {

        return transactionRepository.sumExpensesByCategoryAndUserIdAndPeriod(userId, start, end)
                .stream()
                .map(record -> new CategorySummaryDTO((String) record[0], (BigDecimal) record[1]))
                .toList();
    }

}
