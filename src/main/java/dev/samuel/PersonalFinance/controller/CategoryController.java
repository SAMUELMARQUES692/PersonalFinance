package dev.samuel.PersonalFinance.controller;

import dev.samuel.PersonalFinance.DTOs.CategoryRequestDTO;
import dev.samuel.PersonalFinance.DTOs.CategoryResponseDTO;
import dev.samuel.PersonalFinance.configuration.JWTUserData;
import dev.samuel.PersonalFinance.documentation.CategoryControllerDoc;
import dev.samuel.PersonalFinance.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories")
public class CategoryController implements CategoryControllerDoc {

    private final CategoryService categoryService;

    @PostMapping("/register")
    public ResponseEntity<CategoryResponseDTO> register(@RequestBody @Valid CategoryRequestDTO categoryRequestDTO, @AuthenticationPrincipal JWTUserData userData) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.register(categoryRequestDTO, userData.id()));
    }

    @GetMapping("/{id}")
     public ResponseEntity<CategoryResponseDTO> findById(@PathVariable Long id, @AuthenticationPrincipal JWTUserData userData) {
        return ResponseEntity.ok(categoryService.findById(id, userData.id()));
    }

    @GetMapping
     public ResponseEntity<List<CategoryResponseDTO>> findAll(@AuthenticationPrincipal JWTUserData userData) {
        return ResponseEntity.ok(categoryService.findAll(userData.id()));
    }

    @PutMapping("/{id}")
     public ResponseEntity<CategoryResponseDTO> update(@RequestBody @Valid CategoryRequestDTO categoryRequestDTO, @PathVariable Long id, @AuthenticationPrincipal JWTUserData userData) {
        return ResponseEntity.ok(categoryService.update(id, categoryRequestDTO, userData.id()));
    }

    @DeleteMapping("{id}")
     public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal JWTUserData userData) {
        categoryService.delete(id, userData.id());
        return ResponseEntity.noContent().build();
     }

}
