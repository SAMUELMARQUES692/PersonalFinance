package dev.samuel.PersonalFinance.documentation;

import dev.samuel.PersonalFinance.DTOs.UserRegisterDTO;
import dev.samuel.PersonalFinance.DTOs.UserResponseDTO;
import dev.samuel.PersonalFinance.records.LoginRequest;
import dev.samuel.PersonalFinance.records.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Autenticação", description = "Recurso responsavel pela autenticação de usuarios para as requisições da API sejam feitas")
public interface AuthControllerDoc {

    @Operation(summary = "Login Usuario", description = "Metodo responsavel pelo login e geração de token")
    @ApiResponse(responseCode = "200", description = "Login executado com sucesso", content = @Content(schema = @Schema(implementation = LoginResponse.class)))
    @ApiResponse(responseCode = "401", description = "Usuario ou senha invalidos", content = @Content())
     ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request);

    @Operation(summary = "Salvar Usuario", description = "Metodo responsavel por cadastrar e salvar novos usuarios no banco de dados")
    @ApiResponse(responseCode = "201", description = "Usuario salvo com sucesso", content = @Content(schema = @Schema(implementation = UserResponseDTO.class)))
     ResponseEntity<UserResponseDTO> register(@RequestBody @Valid UserRegisterDTO userDTO);

}
