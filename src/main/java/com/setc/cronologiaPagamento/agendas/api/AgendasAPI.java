package com.setc.cronologiaPagamento.agendas.api;

import com.setc.cronologiaPagamento.agendas.dto.AgendaDTO;
import com.setc.cronologiaPagamento.agendas.service.AgendaService; // Importa o seu NOVO serviço AgendaService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; // Para retornar códigos de status HTTP
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity; // Para construir respostas HTTP mais flexíveis
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import com.setc.cronologiaPagamento.agendas.service.JwtService;
import com.setc.cronologiaPagamento.agendas.dto.AuthRequest;
import com.setc.cronologiaPagamento.agendas.dto.AuthResponse;


import java.util.List;
import java.util.Optional; // Para lidar com resultados que podem não existir

@RestController // Esta anotação combina @Controller e @ResponseBody, simplificando as APIs REST.
@RequestMapping(value = "/agendas", produces = MediaType.APPLICATION_JSON_VALUE) // Define o caminho base para todos os endpoints e o tipo de retorno padrão.
public class AgendasAPI {

    @Autowired // Injeta a instância do AgendaService.
    private AgendaService agendaService; // Agora injetamos o serviço que interage com o DB via JPA.

    @PostMapping
    public ResponseEntity<AgendaDTO> criar(@RequestBody AgendaDTO agendaDTO) { // @Valid ativa as validações definidas no AgendaDTO
        AgendaDTO novaAgenda = agendaService.createAgenda(agendaDTO);
        return new ResponseEntity<>(novaAgenda, HttpStatus.CREATED); // Retorna 201 Created
    }

    @PutMapping("/{agendaId}")
    public ResponseEntity<AgendaDTO> atualizar(@PathVariable("agendaId") Long agendaId,
                                               @RequestBody AgendaDTO agendaDTO) {
        try {
            AgendaDTO agendaAtualizada = agendaService.updateAgenda(agendaId, agendaDTO);
            return new ResponseEntity<>(agendaAtualizada, HttpStatus.OK); // Retorna 200 OK
        } catch (RuntimeException e) { // No AgendaService, lançamos RuntimeException para "not found"
            // Em uma aplicação real, você pode ter exceções personalizadas para lidar melhor com diferentes erros.
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Retorna 404 Not Found
        }
    }

    @GetMapping
    public List<AgendaDTO> getall() { // Não usamos ResponseEntity aqui para mostrar uma alternativa simples, mas ResponseEntity é geralmente preferível.
        return agendaService.getAllAgendas();
    }

    @GetMapping("/{agendaId}")
    public ResponseEntity<AgendaDTO> getById(@PathVariable("agendaId") Long agendaId) {
        Optional<AgendaDTO> agenda = agendaService.getAgendaById(agendaId);
        return agenda.map(value -> new ResponseEntity<>(value, HttpStatus.OK)) // Se encontrar, retorna 200 OK
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // Se não encontrar, retorna 404 Not Found
    }

    @DeleteMapping("/{agendaId}")
    public ResponseEntity<Void> deletar(@PathVariable("agendaId") Long agendaId) {
        agendaService.deleteAgenda(agendaId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Retorna 204 No Content
    }
}