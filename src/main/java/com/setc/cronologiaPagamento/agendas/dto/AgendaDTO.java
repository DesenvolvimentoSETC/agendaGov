package com.setc.cronologiaPagamento.agendas.dto;

import lombok.Data;          // Para getters, setters, toString, equals, hashCode
import lombok.NoArgsConstructor; // Para construtor vazio
import lombok.AllArgsConstructor; // Para construtor com todos os campos


import java.time.LocalDate; // Para mapear datas
import java.time.LocalTime; // Para mapear horas

@Data // Anotação do Lombok: Gera automaticamente getters, setters, toString, equals e hashCode.
@NoArgsConstructor // Anotação do Lombok: Gera um construtor sem argumentos.
@AllArgsConstructor // Anotação do Lombok: Gera um construtor com todos os argumentos.
public class AgendaDTO {

    // O ID pode ser enviado (ex: para atualização) ou vir nulo (para criação, onde o DB gera).
    private Long id;

    private LocalDate data; // Corresponde ao campo 'data' da EntidadeAgenda.

    private String descricao; // Corresponde ao campo 'descricao' da EntidadeAgenda.

    private LocalTime hora; // Corresponde ao campo 'hora' da EntidadeAgenda.

    private String local; // Corresponde ao campo 'local' da EntidadeAgenda.

}
