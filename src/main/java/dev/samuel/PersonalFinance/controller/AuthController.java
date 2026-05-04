package dev.samuel.PersonalFinance.controller;

import dev.samuel.PersonalFinance.DTOs.UserRegisterDTO;
import dev.samuel.PersonalFinance.DTOs.UserResponseDTO;
import dev.samuel.PersonalFinance.configuration.TokenService;
import dev.samuel.PersonalFinance.exception.UsernameOrPasswordInvalidException;
import dev.samuel.PersonalFinance.model.UserModel;
import dev.samuel.PersonalFinance.records.LoginRequest;
import dev.samuel.PersonalFinance.records.LoginResponse;
import dev.samuel.PersonalFinance.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {

        try {

            UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(request.email(), request.password());
            Authentication authentication = authenticationManager.authenticate(userAndPass);

            UserModel userModel = (UserModel) authentication.getPrincipal();

            String token = tokenService.generateToken(userModel);

            return ResponseEntity.ok(new LoginResponse(token));

        }catch (BadCredentialsException | InternalAuthenticationServiceException exception) {
            throw new UsernameOrPasswordInvalidException("User or password are invalid");
        }

    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody @Valid UserRegisterDTO userDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(userDTO));
    }

}
