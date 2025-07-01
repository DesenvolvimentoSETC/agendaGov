package com.setc.cronologiaPagamento.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Importe BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Configuração de segurança para a aplicação Spring Boot.
 * Define como as requisições HTTP são protegidas e como a autenticação é gerenciada,
 * incluindo a integração com JWT.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;

    @Autowired
    private UserDetailsService userDetailsService;

    public SecurityConfig() {
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // CORREÇÃO: Usando BCryptPasswordEncoder para codificar senhas.
        // Isso é a MELHOR PRÁTICA para produção.
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura o provedor de autenticação (DaoAuthenticationProvider).
     * Ele usa o UserDetailsService injetado e o PasswordEncoder para verificar as credenciais.
     *
     * @return Uma instância de DaoAuthenticationProvider.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Configura o AuthenticationManager.
     *
     * @param http O objeto HttpSecurity.
     * @return Uma instância de AuthenticationManager.
     * @throws Exception Se ocorrer um erro na configuração.
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(authenticationProvider());
        return authenticationManagerBuilder.build();
    }

    /**
     * Configura a cadeia de filtros de segurança HTTP.
     * Define as regras de autorização para diferentes requisições e a ordem dos filtros.
     *
     * @param http O objeto HttpSecurity para configurar a segurança HTTP.
     * @return Uma instância de SecurityFilterChain.
     * @throws Exception Se ocorrer um erro na configuração.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desabilita CSRF para APIs REST
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll() // Permite acesso a /auth/** (para login) sem autenticação
                        // Permite requisições GET para /agendas/** sem autenticação
                        .requestMatchers(HttpMethod.GET, "/agendas/**").permitAll()
                        // Todas as outras requisições requerem autenticação
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Define a política de sessão como STATELESS (sem estado, para JWT)
                )
                .authenticationProvider(authenticationProvider()) // Define o provedor de autenticação
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // Adiciona o filtro JWT antes do filtro de autenticação de usuário/senha padrão.

        return http.build();
    }
}