package com.setc.cronologiaPagamento.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // Indica que esta é uma classe de configuração Spring
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Aplica as configurações CORS a todos os caminhos da API ("/**")
                .allowedOrigins("http://localhost:4200", "http://localhost:3000", "http://localhost:8081", "http://127.0.0.1:9090") // <-- Adicione os origins do seu frontend aqui
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos HTTP permitidos
                .allowedHeaders("*") // Permite todos os cabeçalhos nas requisições
                .allowCredentials(true) // Permite o envio de cookies de credenciais
                .maxAge(3600); // Tempo em segundos que o resultado da pré-verificação (preflight) pode ser armazenado em cache
    }
}