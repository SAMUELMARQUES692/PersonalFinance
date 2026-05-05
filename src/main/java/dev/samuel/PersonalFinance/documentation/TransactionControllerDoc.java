package dev.samuel.PersonalFinance.documentation;

import dev.samuel.PersonalFinance.DTOs.TransactionRequestDTO;
import dev.samuel.PersonalFinance.DTOs.TransactionResponseDTO;
import dev.samuel.PersonalFinance.configuration.JWTUserData;
import dev.samuel.PersonalFinance.enums.TransactionType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Transações", description = "Recurso responsavel pelo gerenciamento das transações financeiras do usuário, cadastrando, editando, deletando e filtrando por tipo, categoria ou período.")
public interface TransactionControllerDoc {



    @Operation(summary = "Busca Todas Transações", description = "Metodo responsavel por buscar todos as transações cadastradas no banco de dados",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Retorna todas as bibliotecas cadastradas", content = @Content(array = @ArraySchema(schema = @Schema(implementation = TransactionResponseDTO.class))))
     ResponseEntity<List<TransactionResponseDTO>> findAll(@AuthenticationPrincipal JWTUserData userData);



    @Operation(summary = "Busca as transações pelo ID", description = "Metodo responsavel por buscar as transações pelo ID",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Transação encontrada com sucesso", content = @Content(schema = @Schema(implementation = TransactionResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Transação não encontrada", content = @Content())
     ResponseEntity<TransactionResponseDTO> findById(@PathVariable Long id, @AuthenticationPrincipal JWTUserData userData);



    @Operation(summary = "Busca as transações pelo Tipo", description = "Metodo responsavel por buscar as transações pelo Tipo",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Transação encontrada com sucesso", content = @Content(schema = @Schema(implementation = TransactionResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Transação não encontrada", content = @Content())
    ResponseEntity<List<TransactionResponseDTO>> findByType(@RequestParam TransactionType type, @AuthenticationPrincipal JWTUserData userData);



    @Operation(summary = "Busca as transações pela Categoria", description = "Metodo responsavel por buscar as transações pela categoria",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Transação encontrada com sucesso", content = @Content(schema = @Schema(implementation = TransactionResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Transação não encontrada", content = @Content())
    ResponseEntity<List<TransactionResponseDTO>> findByCategory(@RequestParam Long categoryId,@AuthenticationPrincipal JWTUserData userData);



    @Operation(summary = "Busca as transações por Periodo", description = "Metodo responsavel por buscar as transações pelo Periodo",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Transação encontrada com sucesso", content = @Content(schema = @Schema(implementation = TransactionResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Transação não encontrada", content = @Content())
    ResponseEntity<List<TransactionResponseDTO>> findByPeriod(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
                                                              @AuthenticationPrincipal JWTUserData userData);



    @Operation(summary = "Salvar Transação", description = "Metodo responsavel por cadastrar e salvar novas transações no banco de dados" ,
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Transação salva com sucesso", content = @Content(schema = @Schema(implementation = TransactionResponseDTO.class)))
     ResponseEntity<TransactionResponseDTO> register(@RequestBody @Valid TransactionRequestDTO transactionRequestDTO, @AuthenticationPrincipal JWTUserData userData);



    @Operation(summary = "Atualizar Transação", description = "Metodo responsavel por atualizar as transações cadastradas no banco de dados",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Transação atualizada com sucesso", content = @Content(schema = @Schema(implementation = TransactionResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Transação não encontrada", content = @Content())
     ResponseEntity<TransactionResponseDTO> update(@PathVariable Long id, @RequestBody @Valid TransactionRequestDTO transactionRequestDTO, @AuthenticationPrincipal JWTUserData userData);



    @Operation(summary = "Deleta Transação por ID", description = "Metodo responsavel por deletar transações pelo ID",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "204", description = "Transação deletada com sucesso", content = @Content())
    @ApiResponse(responseCode = "404", description = "Transação não encontrada", content = @Content())
    ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal JWTUserData userData);

}
