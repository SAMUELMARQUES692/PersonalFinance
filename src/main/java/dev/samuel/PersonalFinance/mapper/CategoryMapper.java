package dev.samuel.PersonalFinance.mapper;

import dev.samuel.PersonalFinance.DTOs.CategoryRequestDTO;
import dev.samuel.PersonalFinance.DTOs.CategoryResponseDTO;
import dev.samuel.PersonalFinance.model.CategoryModel;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryModel toModel(CategoryRequestDTO dto) {
        return CategoryModel.builder()
                .name(dto.name())
                .description(dto.description())
                .build();

    }

    public CategoryResponseDTO toDto(CategoryModel category) {
        return new CategoryResponseDTO(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getCreatedAt()
        );
    }

}
