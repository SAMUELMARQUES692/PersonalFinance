package dev.samuel.PersonalFinance.DTOs;

import dev.samuel.PersonalFinance.enums.UserRole;

import java.time.LocalDateTime;

public record UserResponseDTO(
                        Long id,
                        String name,
                        String email,
                        UserRole role,
                        LocalDateTime createdAt) {
        }
