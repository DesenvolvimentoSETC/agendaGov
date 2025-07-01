package com.setc.cronologiaPagamento.agendas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank; // Para validação, se você for usar


/**
 * DTO (Data Transfer Object) para requisições de autenticação.
 * Usado para receber o nome de usuário e senha na requisição de login.
 */
@Data // Anotação do Lombok para gerar getters, setters, toString, equals e hashCode
@NoArgsConstructor // Anotação do Lombok para gerar construtor sem argumentos
@AllArgsConstructor // Anotação do Lombok para gerar construtor com todos os argumentos
public class AuthRequest {

    private String username;

    @NotBlank(message = "A senha não pode ser vazia")
    private String password;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
