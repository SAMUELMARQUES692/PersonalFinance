package dev.samuel.PersonalFinance.controller;

import dev.samuel.PersonalFinance.DTOs.BalanceResponseDTO;
import dev.samuel.PersonalFinance.DTOs.CategorySummaryDTO;
import dev.samuel.PersonalFinance.DTOs.MonthlyReportDTO;
import dev.samuel.PersonalFinance.configuration.JWTUserData;
import dev.samuel.PersonalFinance.documentation.ReportControllerDoc;
import dev.samuel.PersonalFinance.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/report")
public class ReportController implements ReportControllerDoc {

    private final ReportService reportService;

    @GetMapping("/balance")
    public ResponseEntity<BalanceResponseDTO> getBalance(@AuthenticationPrincipal JWTUserData userData) {
        return ResponseEntity.ok(reportService.getBalance(userData.id()));
    }

    @GetMapping("/monthly")
    public ResponseEntity<MonthlyReportDTO> getMonthlyReport(@RequestParam int month, @RequestParam int year, @AuthenticationPrincipal JWTUserData userData) {
        return ResponseEntity.ok(reportService.getMonthlyReport(userData.id(), month, year));
    }

    @GetMapping("/category/expenses")
    public ResponseEntity<List<CategorySummaryDTO>> getExpensesByCategory(@AuthenticationPrincipal JWTUserData userData) {
        return ResponseEntity.ok(reportService.getExpensesByCategory(userData.id()));
    }

    @GetMapping("/category/expenses/period")
    public ResponseEntity<List<CategorySummaryDTO>> getExpensesByCategoryAndPeriod(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                                                                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
                                                                                   @AuthenticationPrincipal JWTUserData userData) {
        return ResponseEntity.ok(reportService.getExpensesByCategoryAndPeriod(userData.id(), start, end));

    }

}
