package dev.samuel.PersonalFinance.repository;

import dev.samuel.PersonalFinance.enums.TransactionType;
import dev.samuel.PersonalFinance.model.TransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionModel, Long> {

    // busca todas as transações do usuário
    List<TransactionModel> findAllByUserId(Long userId);

    // busca por id garantindo que pertence ao usuário
    Optional<TransactionModel> findByIdAndUserId(Long id, Long userId);

    // filtra por tipo (INCOME ou EXPENSE)
    List<TransactionModel> findAllByUserIdAndType(Long userId, TransactionType type);

    // filtra por categoria
    List<TransactionModel> findAllByUserIdAndCategoryId(Long userId, Long categoryId);

    // filtra por período
    List<TransactionModel> findAllByUserIdAndDateBetween(Long userId, LocalDate start, LocalDate end);
}
