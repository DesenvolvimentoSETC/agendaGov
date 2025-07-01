package com.setc.cronologiaPagamento.agendas.service;

import com.setc.cronologiaPagamento.persistence.EntidadeAgenda; // Importa a sua Entidade JPA
import com.setc.cronologiaPagamento.persistence.repository.AgendaRepository; // Importa o seu Repositório JPA
import com.setc.cronologiaPagamento.agendas.dto.AgendaDTO; // Importa o seu DTO
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.BeanUtils; // Utilitário para copiar propriedades entre objetos
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Classe de Serviço para a entidade Agenda.
 * Contém a lógica de negócio para manipulação de agendas,
 * atuando como intermediário entre o controlador (API) e o repositório (persistência).
 */
@Service // Indica ao Spring que esta é uma classe de serviço e deve ser gerenciada pelo contêiner de inversão de controle.
public class AgendaService {

    @Autowired // Realiza a injeção de dependência do AgendaRepository. O Spring injetará uma instância de AgendaRepository aqui.
    private AgendaRepository agendaRepository;

    public List<AgendaDTO> getAllAgendas() {
        List<EntidadeAgenda> agendas = agendaRepository.findAll(); // Busca todas as entidades do banco de dados
        // Converte a lista de entidades (EntidadeAgenda) para uma lista de DTOs (AgendaDTO)
        return agendas.stream()
                .map(this::convertToDTO) // Para cada EntidadeAgenda, chama o método privado convertToDTO
                .collect(Collectors.toList()); // Coleta os resultados em uma nova lista
    }

    public Optional<AgendaDTO> getAgendaById(Long id) {
        // Busca a entidade por ID e, se encontrada, a converte para DTO.
        return agendaRepository.findById(id) // Retorna um Optional<EntidadeAgenda>
                .map(this::convertToDTO); // Se a entidade estiver presente, aplica a conversão para DTO
    }

    public AgendaDTO createAgenda(AgendaDTO agendaDTO) {
        // Converte o DTO recebido da API para a entidade que será salva no banco de dados
        EntidadeAgenda agendaEntity = convertToEntity(agendaDTO);
        agendaEntity = agendaRepository.save(agendaEntity); // Salva a entidade no banco de dados. O ID será preenchido após esta operação.
        // Converte a entidade salva (agora com o ID gerado) de volta para DTO antes de retornar.
        return convertToDTO(agendaEntity);
    }

    public AgendaDTO updateAgenda(Long id, AgendaDTO agendaDTO) {
        // Busca a agenda existente pelo ID. Se não encontrar, lança uma exceção.
        EntidadeAgenda existingAgenda = agendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agenda não encontrada com o ID: " + id)); // Em aplicações reais, use uma exceção de negócio mais específica (ex: ResourceNotFoundException)

        // Copia as propriedades do DTO para a entidade existente, excluindo o campo 'id'.
        // Isso evita que o ID seja sobrescrito com um valor nulo ou incorreto do DTO.
        BeanUtils.copyProperties(agendaDTO, existingAgenda, "id");
        existingAgenda = agendaRepository.save(existingAgenda); // Salva as alterações da entidade no banco de dados.
        return convertToDTO(existingAgenda); // Retorna o DTO da agenda atualizada.
    }

    public void deleteAgenda(Long id) {
        agendaRepository.deleteById(id); // Deleta a entidade pelo seu ID.
    }

    private AgendaDTO convertToDTO(EntidadeAgenda agenda) {
        AgendaDTO agendaDTO = new AgendaDTO();
        // Copia as propriedades da entidade para o DTO.
        BeanUtils.copyProperties(agenda, agendaDTO);
        return agendaDTO;
    }

    private EntidadeAgenda convertToEntity(AgendaDTO agendaDTO) {
        EntidadeAgenda agenda = new EntidadeAgenda();
        // Copia as propriedades do DTO para a entidade.
        BeanUtils.copyProperties(agendaDTO, agenda);
        return agenda;
    }

    @Service
    public class JwtService {
        private static final String SECRET = "secreta-chave-jwt";

        public String generateToken(String username) {
            return Jwts.builder()
                    .setSubject(username)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1h
                    .signWith(SignatureAlgorithm.HS256, SECRET)
                    .compact();
        }
    }
}