package com.setc.cronologiaPagamento.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Importe BCryptPasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration // Indica que esta classe é uma fonte de definições de beans para o contexto de aplicação.
public class UserDetailsServiceConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        // Crie uma instância de BCryptPasswordEncoder para codificar a senha.
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // Define os detalhes do usuário em memória.
        UserDetails user = User.withUsername("setcTRS")
                // Codifica a senha "admin" usando BCryptPasswordEncoder.
                // Em um ambiente de produção, as senhas seriam recuperadas já codificadas de um banco de dados.
                .password(passwordEncoder.encode("SeTc@3694"))
                .roles("ADMIN") // Atribui o papel "ADMIN" ao usuário.
                .build(); // Constrói o objeto UserDetails.

        // Retorna uma instância de InMemoryUserDetailsManager com o usuário definido.
        // Em um ambiente de produção, este seria substituído por um serviço que busca usuários de um banco de dados.
        return new InMemoryUserDetailsManager(user);
    }
}