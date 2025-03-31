package com.utd.ti.soa.ebs_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import com.utd.ti.soa.ebs_service.model.Client;
import com.utd.ti.soa.ebs_service.utils.Auth;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/esb/clients")
public class ClientController {
    private final Auth auth = new Auth();
    private final WebClient webClient = WebClient.create();

    @GetMapping("/")
    public ResponseEntity<String> getClients(@RequestHeader("Authorization") String token) {
        if (!auth.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
        }
        try {
            String response = webClient.get()
                    .uri("soa_clients.railway.internal:3000/api/client/")
                    .header("Authorization", token)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener clientes: " + e.getMessage());
        }
    }

    @PostMapping("/")
    public ResponseEntity<String> createClient(@RequestHeader("Authorization") String token, @RequestBody Client client) {
        if (!auth.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
        }
        try {
            String response = webClient.post()
                    .uri("soa_clients.railway.internal:3000/api/client/")
                    .header("Authorization", token)
                    .bodyValue(client)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear cliente: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateClient(@RequestHeader("Authorization") String token, @PathVariable String id, @RequestBody Map<String, String> updates) {
        if (!auth.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
        }
        try {
            String response = webClient.put()
                    .uri("soa_clients.railway.internal:3000/api/client/" + id)
                    .header("Authorization", token)
                    .bodyValue(updates)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar cliente: " + e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> deleteClient(@RequestHeader("Authorization") String token, @PathVariable String id) {
        if (!auth.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
        }
        try {
            String response = webClient.patch()
                    .uri("soa_clients.railway.internal:3000/api/client/" + id)
                    .header("Authorization", token)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar cliente: " + e.getMessage());
        }
    }
}
