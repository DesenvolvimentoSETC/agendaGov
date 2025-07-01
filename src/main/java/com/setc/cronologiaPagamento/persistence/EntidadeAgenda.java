package com.setc.cronologiaPagamento.persistence;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "agenda")
public class EntidadeAgenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false, columnDefinition = "text")
    private String descricao;

    @Column(nullable = false)
    private LocalTime hora;

    @Column(nullable = false, length = 300)
    private String local;

    public EntidadeAgenda() {}

    public EntidadeAgenda(LocalDate data, String descricao, LocalTime hora, String local) {
        this.data = data;
        this.descricao = descricao;
        this.hora = hora;
        this.local = local;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    @Override
    public String toString() {
        return "EntidadeAgenda{" +
                "id=" + id +
                ", data=" + data +
                ", descricao='" + descricao + '\'' +
                ", hora=" + hora +
                ", local='" + local + '\'' +
                '}';
    }
}
