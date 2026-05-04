package dev.samuel.PersonalFinance.mapper;

import dev.samuel.PersonalFinance.DTOs.UserRegisterDTO;
import dev.samuel.PersonalFinance.DTOs.UserResponseDTO;
import dev.samuel.PersonalFinance.model.UserModel;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserModel toModel(UserRegisterDTO dto) {
        return UserModel.builder()
                .name(dto.name())
                .email(dto.email())
                .password(dto.password())
                .build();
    }

    public UserResponseDTO toDto(UserModel user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getCreatedAt()
        );
    }
}