package com.github.joao.disaster_response.api.controller;

import com.github.joao.disaster_response.domain.model.Usuario;
import com.github.joao.disaster_response.domain.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<String> registrar(@RequestBody Usuario usuario) {
        String token = authService.registrar(usuario);
        return ResponseEntity.status(201).body(token);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> credenciais) {
        String token = authService.login(
                credenciais.get("email"),
                credenciais.get("senha")
        );
        return ResponseEntity.ok(token);
    }
}