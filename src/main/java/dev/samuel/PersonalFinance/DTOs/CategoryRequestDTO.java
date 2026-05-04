package dev.samuel.PersonalFinance.DTOs;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequestDTO(
        @NotBlank(message = "Nome é obrigatório")
        String name,

        String description) {
}
