package dev.samuel.PersonalFinance.documentation;

import dev.samuel.PersonalFinance.DTOs.BalanceResponseDTO;
import dev.samuel.PersonalFinance.DTOs.CategoryResponseDTO;
import dev.samuel.PersonalFinance.DTOs.CategorySummaryDTO;
import dev.samuel.PersonalFinance.DTOs.MonthlyReportDTO;
import dev.samuel.PersonalFinance.configuration.JWTUserData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Relatórios", description = "Recurso responsavel pelo gerenciamento dos relatórios financeiros de entradas e saidas por cateroria e periodo, bem como o saldo geral do usuario")
public interface ReportControllerDoc {

    @Operation(summary = "Saldo Atual", description = "Metodo responsavel por calcular o saldo atual do usuario, levando em consideração todas as entradas e saidas cadastradas no banco de dados",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Relatorio gerado com sucesso", content = @Content(schema = @Schema(implementation = BalanceResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Relatorio não gerado", content = @Content())
    ResponseEntity<BalanceResponseDTO> getBalance(@AuthenticationPrincipal JWTUserData userData);



    @Operation(summary = "Relatorio Mensal", description = "Metodo responsavel por calcular o saldo atual do usuario, levando em consideração todas as entradas e saidas cadastradas no banco de dados, filtrando por mes e ano",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Relatorio gerado com sucesso", content = @Content(schema = @Schema(implementation = MonthlyReportDTO.class)))
    @ApiResponse(responseCode = "404", description = "Relatorio não gerado", content = @Content())
    ResponseEntity<MonthlyReportDTO> getMonthlyReport(@RequestParam int month, @RequestParam int year, @AuthenticationPrincipal JWTUserData userData);


    @Operation(summary = "Lista de Gastos Por Categoria", description = "Metodo responsavel por calcular o saldo atual do usuario, levando em consideração todas as entradas e saidas cadastradas no banco de dados, filtrando por categoria",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Relatorio gerado com sucesso", content = @Content(schema = @Schema(implementation = CategorySummaryDTO.class)))
    @ApiResponse(responseCode = "404", description = "Relatorio não gerado", content = @Content())
    ResponseEntity<List<CategorySummaryDTO>> getExpensesByCategory(@AuthenticationPrincipal JWTUserData userData);


    @Operation(summary = "Lista de Gastos Por Categoria e Periodo",
            description = "Metodo responsavel por calcular o saldo atual do usuario, levando em consideração todas as entradas e saidas cadastradas no banco de dados, filtrando por categoria e periodo",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Relatorio gerado com sucesso", content = @Content(schema = @Schema(implementation = CategorySummaryDTO.class)))
    @ApiResponse(responseCode = "404", description = "Relatorio não gerado", content = @Content())
    ResponseEntity<List<CategorySummaryDTO>> getExpensesByCategoryAndPeriod(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                                                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
                                                                            @AuthenticationPrincipal JWTUserData userData);
}
