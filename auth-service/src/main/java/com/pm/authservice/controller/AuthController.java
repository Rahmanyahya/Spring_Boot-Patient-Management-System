package com.pm.authservice.controller;

import com.pm.authservice.dto.LoginRequestDTO;
import com.pm.authservice.dto.LoginResponseDTO;
import com.pm.authservice.dto.ResponseDTO;
import com.pm.authservice.helper.ResponseMapper;
import com.pm.authservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth Api", description = "Api documentation for auhentication")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Generate token when user login")
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO<LoginResponseDTO>> login(
            @RequestBody LoginRequestDTO req
    ) {
        Optional<String> tokenOptional =  authService.authenticate(req);

        if (tokenOptional.isEmpty()) {
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = tokenOptional.get();

        return  ResponseEntity.ok().body(ResponseMapper.success("Login Success", new LoginResponseDTO(token)));
    }

    @Operation(summary = "validate token")
    @GetMapping("/validate")
    public ResponseEntity<Void> validateToke(
            @RequestHeader("Authorization") String authHeader
    ) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return  authService.validateToken(authHeader.substring(7))
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
