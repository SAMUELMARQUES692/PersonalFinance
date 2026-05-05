package dev.samuel.PersonalFinance.documentation;

import dev.samuel.PersonalFinance.DTOs.CategoryRequestDTO;
import dev.samuel.PersonalFinance.DTOs.CategoryResponseDTO;
import dev.samuel.PersonalFinance.configuration.JWTUserData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Categorias", description = "Recurso responsavel pelo gerenciamento das categorias, onde o usuario pode criar, atualizar, deletar e listar suas categorias")
public interface CategoryControllerDoc {

    @Operation(summary = "Salvar Categoria", description = "Metodo responsavel por cadastrar e salvar novas categorias no banco de dados" ,
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Categoria salva com sucesso", content = @Content(schema = @Schema(implementation = CategoryResponseDTO.class)))
    ResponseEntity<CategoryResponseDTO> register(@RequestBody @Valid CategoryRequestDTO categoryRequestDTO, @AuthenticationPrincipal JWTUserData userData);


    @Operation(summary = "Busca as categorias pelo ID", description = "Metodo responsavel por buscar as categorias pelo ID",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Categoria encontrada com sucesso", content = @Content(schema = @Schema(implementation = CategoryResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Categoria não encontrada", content = @Content())
    ResponseEntity<CategoryResponseDTO> findById(Long id, @AuthenticationPrincipal JWTUserData userData);

    @Operation(summary = "Busca Categorias", description = "Metodo responsavel por buscar todas as categorias cadastradas no banco de dados",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Retorna todas as categorias cadastradas", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryResponseDTO.class))))
    ResponseEntity<List<CategoryResponseDTO>> findAll(@AuthenticationPrincipal JWTUserData userData);

    @Operation(summary = "Atualizar Categorias", description = "Metodo responsavel por atualizar as categorias cadastradas no banco de dados",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso", content = @Content(schema = @Schema(implementation = CategoryResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Categoria não encontrada", content = @Content())
    ResponseEntity<CategoryResponseDTO> update(@RequestBody @Valid CategoryRequestDTO categoryRequestDTO, @PathVariable Long id, @AuthenticationPrincipal JWTUserData userData);

    @Operation(summary = "Deleta Categorias por ID", description = "Metodo responsavel por deletar categorias pelo ID",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "204", description = "Categoria deletada com sucesso", content = @Content())
    @ApiResponse(responseCode = "404", description = "Categoria não encontrada", content = @Content())
    ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal JWTUserData userData);

}
