package dev.samuel.PersonalFinance.service;

import dev.samuel.PersonalFinance.DTOs.CategoryRequestDTO;
import dev.samuel.PersonalFinance.DTOs.CategoryResponseDTO;
import dev.samuel.PersonalFinance.exception.BusinessException;
import dev.samuel.PersonalFinance.exception.UserNotFoundException;
import dev.samuel.PersonalFinance.mapper.CategoryMapper;
import dev.samuel.PersonalFinance.model.CategoryModel;
import dev.samuel.PersonalFinance.model.UserModel;
import dev.samuel.PersonalFinance.repository.CategoryRepository;
import dev.samuel.PersonalFinance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Transactional
    public CategoryResponseDTO register(CategoryRequestDTO categoryRequestDTO, Long userId) {
        if (categoryRepository.existsByNameAndUserId(categoryRequestDTO.name(), userId)) {
            throw new BusinessException(categoryRequestDTO.name());
        }

        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        CategoryModel category = categoryMapper.toModel(categoryRequestDTO);
        category.setUser(user);

        return categoryMapper.toDto(categoryRepository.save(category));
    }

    public CategoryResponseDTO findById(Long id, Long userId) {
        return categoryRepository.findByIdAndUserId(id, userId)
                .map(categoryMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException(id));
    }


    public List<CategoryResponseDTO> findAll(Long userId) {
        return categoryRepository.findAllByUserId(userId)
                .stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Transactional
    public CategoryResponseDTO update(Long id, CategoryRequestDTO categoryRequestDTO, Long userId) {
        CategoryModel category = categoryRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new UserNotFoundException(id));

        category.setName(categoryRequestDTO.name());
        category.setDescription(categoryRequestDTO.description());

        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Transactional
    public void delete(Long id, Long userId) {
        CategoryModel category = categoryRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new UserNotFoundException(id));

        categoryRepository.delete(category);
    }




}
