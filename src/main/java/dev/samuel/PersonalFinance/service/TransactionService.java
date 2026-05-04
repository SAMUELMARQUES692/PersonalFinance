package dev.samuel.PersonalFinance.service;

import dev.samuel.PersonalFinance.DTOs.TransactionRequestDTO;
import dev.samuel.PersonalFinance.DTOs.TransactionResponseDTO;
import dev.samuel.PersonalFinance.enums.TransactionType;
import dev.samuel.PersonalFinance.exception.CategoryNotFoundException;
import dev.samuel.PersonalFinance.exception.TransactionNotFoundException;
import dev.samuel.PersonalFinance.exception.UserNotFoundException;
import dev.samuel.PersonalFinance.mapper.TransactionMapper;
import dev.samuel.PersonalFinance.model.TransactionModel;
import dev.samuel.PersonalFinance.repository.CategoryRepository;
import dev.samuel.PersonalFinance.repository.TransactionRepository;
import dev.samuel.PersonalFinance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final TransactionMapper transactionMapper;

    public List<TransactionResponseDTO> findAll(Long userId) {
        return transactionRepository.findAllByUserId(userId)
                .stream()
                .map(transactionMapper::toDto)
                .toList();
    }

    public TransactionResponseDTO findById(Long id, Long userId) {
        return transactionRepository.findByIdAndUserId(id, userId)
                .map(transactionMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada"));
    }

    public List<TransactionResponseDTO> findByType(Long userId, TransactionType type) {
        return transactionRepository.findAllByUserIdAndType(userId, type)
                .stream()
                .map(transactionMapper::toDto)
                .toList();
    }

    public List<TransactionResponseDTO> findByCategory(Long userId, Long categoryId) {
        return transactionRepository.findAllByUserIdAndCategoryId(userId, categoryId)
                .stream()
                .map(transactionMapper::toDto)
                .toList();
    }

    public List<TransactionResponseDTO> findByPeriod(Long userId, LocalDate start, LocalDate end) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Data de início deve ser anterior à data de fim");
        }
        return transactionRepository.findAllByUserIdAndDateBetween(userId, start, end)
                .stream()
                .map(transactionMapper::toDto)
                .toList();
    }

    @Transactional
    public TransactionResponseDTO register(TransactionRequestDTO transactionRequestDTO, Long userId) {
        categoryRepository.findByIdAndUserId(transactionRequestDTO.categoryId(), userId)
                .orElseThrow(() -> new CategoryNotFoundException(transactionRequestDTO.categoryId()));

        TransactionModel savedTransaction = transactionMapper.toModel(transactionRequestDTO);
        savedTransaction.setUser(userRepository.getReferenceById(userId));
        savedTransaction.setCategory(categoryRepository.getReferenceById(transactionRequestDTO.categoryId()));

        return transactionMapper.toDto(transactionRepository.save(savedTransaction));
    }

    @Transactional
    public TransactionResponseDTO update(Long id, TransactionRequestDTO dto, Long userId) {
        TransactionModel transaction = transactionRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new TransactionNotFoundException(id));

        // valida se a nova categoria pertence ao usuário
        categoryRepository.findByIdAndUserId(dto.categoryId(), userId)
                .orElseThrow(() -> new CategoryNotFoundException(dto.categoryId()));

        transaction.setDescription(dto.description());
        transaction.setAmount(dto.amount());
        transaction.setType(dto.type());
        transaction.setDate(dto.date());
        transaction.setCategory(categoryRepository.getReferenceById(dto.categoryId()));

        return transactionMapper.toDto(transactionRepository.save(transaction));
    }

    @Transactional
    public void delete(Long id, Long userId) {
        TransactionModel transaction = transactionRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        transactionRepository.delete(transaction);
    }


}
