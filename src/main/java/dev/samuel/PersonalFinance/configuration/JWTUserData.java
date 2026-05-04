package dev.samuel.PersonalFinance.configuration;

import lombok.Builder;

@Builder
public record JWTUserData(Long id, String name, String email) {
}
