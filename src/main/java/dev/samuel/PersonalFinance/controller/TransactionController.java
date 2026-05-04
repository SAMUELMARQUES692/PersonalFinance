package dev.samuel.PersonalFinance.controller;

import dev.samuel.PersonalFinance.DTOs.TransactionRequestDTO;
import dev.samuel.PersonalFinance.DTOs.TransactionResponseDTO;
import dev.samuel.PersonalFinance.configuration.JWTUserData;
import dev.samuel.PersonalFinance.enums.TransactionType;
import dev.samuel.PersonalFinance.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;


    @GetMapping
    public ResponseEntity<List<TransactionResponseDTO>> findAll(@AuthenticationPrincipal JWTUserData userData) {
        return ResponseEntity.ok(transactionService.findAll(userData.id()));
    }

    @GetMapping("{id}")
    public ResponseEntity<TransactionResponseDTO> findById(@PathVariable Long id, @AuthenticationPrincipal JWTUserData userData) {
            return ResponseEntity.ok(transactionService.findById(id, userData.id()));
        }

    @GetMapping("/filter/type")
    public ResponseEntity<List<TransactionResponseDTO>> findByType(@RequestParam TransactionType type, @AuthenticationPrincipal JWTUserData userData) {
        return ResponseEntity.ok(transactionService.findByType(userData.id(), type));
    }

    @GetMapping("/filter/category")
    public ResponseEntity<List<TransactionResponseDTO>> findByCategory(@RequestParam Long categoryId,@AuthenticationPrincipal JWTUserData userData) {
        return ResponseEntity.ok(transactionService.findByCategory(userData.id(), categoryId));
    }

    @GetMapping("/filter/period")
    public ResponseEntity<List<TransactionResponseDTO>> findByPeriod(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
            @AuthenticationPrincipal JWTUserData userData) {
        return ResponseEntity.ok(transactionService.findByPeriod(userData.id(), start, end));
    }

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> register(@RequestBody @Valid TransactionRequestDTO transactionRequestDTO, @AuthenticationPrincipal JWTUserData userData) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.register(transactionRequestDTO, userData.id()));
    }

    @PutMapping("{id}")
    public ResponseEntity<TransactionResponseDTO> update(@PathVariable Long id, @RequestBody @Valid TransactionRequestDTO transactionRequestDTO, @AuthenticationPrincipal JWTUserData userData) {
        return ResponseEntity.ok(transactionService.update(id, transactionRequestDTO, userData.id()));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal JWTUserData userData) {
        transactionService.delete(id, userData.id());
        return ResponseEntity.noContent().build();
    }

}
