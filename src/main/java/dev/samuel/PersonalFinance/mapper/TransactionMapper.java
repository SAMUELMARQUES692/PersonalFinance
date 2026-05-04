package dev.samuel.PersonalFinance.mapper;

import dev.samuel.PersonalFinance.DTOs.TransactionRequestDTO;
import dev.samuel.PersonalFinance.DTOs.TransactionResponseDTO;
import dev.samuel.PersonalFinance.model.TransactionModel;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public TransactionModel toModel(TransactionRequestDTO dto) {
        return TransactionModel.builder()
                .description(dto.description())
                .amount(dto.amount())
                .type(dto.type())
                .date(dto.date())
                .build();

    }

    public TransactionResponseDTO toDto(TransactionModel transaction) {
        return new TransactionResponseDTO(
                transaction.getId(),
                transaction.getDescription(),
                transaction.getAmount(),
                transaction.getType(),
                transaction.getDate(),
                transaction.getCategory() != null ? transaction.getCategory().getName() : null,
                transaction.getCreatedAt()
        );
    }

}
