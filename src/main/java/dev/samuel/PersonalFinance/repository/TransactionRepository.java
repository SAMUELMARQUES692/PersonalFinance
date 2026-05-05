package dev.samuel.PersonalFinance.repository;

import dev.samuel.PersonalFinance.enums.TransactionType;
import dev.samuel.PersonalFinance.model.TransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionModel, Long> {

    List<TransactionModel> findAllByUserId(Long userId);
    Optional<TransactionModel> findByIdAndUserId(Long id, Long userId);
    List<TransactionModel> findAllByUserIdAndType(Long userId, TransactionType type);
    List<TransactionModel> findAllByUserIdAndCategoryId(Long userId, Long categoryId);
    List<TransactionModel> findAllByUserIdAndDateBetween(Long userId, LocalDate start, LocalDate end);

    // soma total por tipo
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM TransactionModel t WHERE t.user.id = :userId AND t.type = :type")
    BigDecimal sumByUserIdAndType(@Param("userId") Long userId, @Param("type") TransactionType type);

    // soma por tipo em um período
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM TransactionModel t WHERE t.user.id = :userId AND t.type = :type AND t.date BETWEEN :start AND :end")
    BigDecimal sumByUserIdAndTypeAndPeriod(@Param("userId") Long userId, @Param("type") TransactionType type, @Param("start") LocalDate start, @Param("end") LocalDate end);

    // gastos por categoria
    @Query("SELECT t.category.name, COALESCE(SUM(t.amount), 0) FROM TransactionModel t WHERE t.user.id = :userId AND t.type = 'EXPENSE' GROUP BY t.category.name ORDER BY SUM(t.amount) DESC")
    List<Object[]> sumExpensesByCategoryAndUserId(@Param("userId") Long userId);

    // gastos por categoria em um período
    @Query("SELECT t.category.name, COALESCE(SUM(t.amount), 0) FROM TransactionModel t WHERE t.user.id = :userId AND t.type = 'EXPENSE' AND t.date BETWEEN :start AND :end GROUP BY t.category.name ORDER BY SUM(t.amount) DESC")
    List<Object[]> sumExpensesByCategoryAndUserIdAndPeriod(@Param("userId") Long userId, @Param("start") LocalDate start, @Param("end") LocalDate end);

}
