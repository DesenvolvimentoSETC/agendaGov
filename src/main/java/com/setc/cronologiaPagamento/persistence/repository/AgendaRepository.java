package com.setc.cronologiaPagamento.persistence.repository;

import com.setc.cronologiaPagamento.persistence.EntidadeAgenda; // Importa a sua Entidade JPA que criamos
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate; // Necessário para métodos de busca por data
import java.time.LocalTime; // Necessário para métodos de busca por hora
import java.util.List;
import java.util.Optional; // Para métodos findById e outros que podem não retornar um resultado


@Repository // Indica ao Spring que esta interface é um componente de repositório e deve ser gerenciada.
public interface AgendaRepository extends JpaRepository<EntidadeAgenda, Long> {

    @Override
    <S extends EntidadeAgenda> S save(S entidade);

    @Override
    Optional<EntidadeAgenda> findById(Long id);

    @Override
    List<EntidadeAgenda> findAll();

    /**
     * Deleta uma entidade Agenda por seu ID.
     *
     * @param id O ID da agenda a ser deletada.
     */
    @Override
    void deleteById(Long id);

    /**
     * Verifica se uma entidade Agenda com o ID especificado existe.
     *
     * @param id O ID da agenda a ser verificada.
     * @return true se a agenda existe, false caso contrário.
     */
    @Override
    boolean existsById(Long id);

    /**
     * Retorna o número de entidades Agenda disponíveis.
     *
     * @return O número de agendas.
     */
    @Override
    long count();


    // --- Métodos de consulta personalizados (criados pelo Spring Data JPA através do nome do método) ---


    List<EntidadeAgenda> findByData(LocalDate data);


    List<EntidadeAgenda> findByDescricaoContainingIgnoreCase(String descricao);


    List<EntidadeAgenda> findByLocalContainingIgnoreCase(String local);


    Optional<EntidadeAgenda> findByDataAndHora(LocalDate data, LocalTime hora);


    List<EntidadeAgenda> findByDataAndHoraAndLocalContainingIgnoreCase(LocalDate data, LocalTime hora, String local);


    List<EntidadeAgenda> findByDataAfter(LocalDate data);


    List<EntidadeAgenda> findByDataBefore(LocalDate data);
}