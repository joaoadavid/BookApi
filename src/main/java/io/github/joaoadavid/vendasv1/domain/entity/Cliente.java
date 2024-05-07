package io.github.joaoadavid.vendasv1.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cliente")
public class Cliente {

    @Id//significa que é um id
    @GeneratedValue(strategy = GenerationType.AUTO)//estratégia para gerar id automatico
    @Column(name = "id")//mostra que é uma coluna e mapeia para o banco
    private Integer id;

    @Column(name = "nome", length = 100)//define o número de caracteres para a coluna
    @NotEmpty(message = " {campo.nome.obrigatorio}")
    private String nome;

    @Column(name = "cpf", length = 11)
    @NotEmpty(message = " {campo.cpf.obrigatorio}")
    @CPF(message = "{Informe um CPF válido.}") //existe a notation CPF
    private String cpf;

    @JsonIgnore
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private Set<Pedido> pedidos;

    public Cliente(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }
}
