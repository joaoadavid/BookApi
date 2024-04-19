package io.github.joaoadavid.vendasv1.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cliente")
public class Cliente {

    @Id//significa que é um id
    @GeneratedValue(strategy = GenerationType.AUTO)//estratégia para gerar id automatico
    @Column(name = "id")//mostra que é uma coluna e mapeia para o banco
    private Integer id;

    @Column(name = "nome", length = 100)//define o número de caracteres para a coluna
    private String nome;

    public Cliente() {
    }

    public Cliente(String nome) {
        this.nome = nome;
    }

    public Cliente(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
