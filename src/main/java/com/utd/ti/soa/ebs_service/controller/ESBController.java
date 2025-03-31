package com.utd.ti.soa.ebs_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import com.utd.ti.soa.ebs_service.model.User;
import com.utd.ti.soa.ebs_service.utils.Auth;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/esb")
public class ESBController {
    private final String SECRET_KEY = "aJksd9QzPl+sVdK7vYc/L4dK8HgQmPpQ5K9yApUsj3w=";
    private final Auth auth = new Auth();
    private final WebClient webClient = WebClient.create();

    @PostMapping("/user")
    public ResponseEntity<String> createUser(@RequestHeader("Authorization") String token, @RequestBody User user) {
        if (!auth.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
        }
        try {
            String response = webClient.post()
                    .uri("http://soa_users.railway.internal:3000/api/users/create")
                    .bodyValue(user)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear usuario: " + e.getMessage());
        }
    }

    @GetMapping("/users")
    public ResponseEntity<String> getUsers(@RequestHeader("Authorization") String token) {
        if (!auth.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
        }
        try {
            String response = webClient.get()
                    .uri("http://soa_users.railway.internal:3000/api/users/")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener usuarios: " + e.getMessage());
        }
    }

    @PostMapping("/user/login")
    public ResponseEntity<String> loginUser(@RequestBody Map<String, String> credentials) {
        try {
            String response = webClient.post()
                    .uri("http://soa_users.railway.internal:3000/api/users/login")
                    .bodyValue(credentials)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en el inicio de sesión: " + e.getMessage());
        }
    }

    @PatchMapping("/user/{id}")
    public ResponseEntity<String> updateUser(@RequestHeader("Authorization") String token, @PathVariable String id, @RequestBody Map<String, String> updates) {
        if (!auth.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
        }
        try {
            String response = webClient.patch()
                    .uri("http://soa_users.railway.internal:3000/api/users/" + id)
                    .bodyValue(updates)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar usuario: " + e.getMessage());
        }
    }

    @PutMapping("/user/delete/{id}")
    public ResponseEntity<String> deleteUser(@RequestHeader("Authorization") String token, @PathVariable String id) {
        if (!auth.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
        }
        try {
            String response = webClient.put()
                    .uri("http://soa_users.railway.internal:3000/api/users/delete/" + id)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar usuario: " + e.getMessage());
        }
    }

    
}
