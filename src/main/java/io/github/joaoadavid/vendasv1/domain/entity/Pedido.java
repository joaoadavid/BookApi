package io.github.joaoadavid.vendasv1.domain.entity;

import io.github.joaoadavid.vendasv1.domain.enums.StatusPedido;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pedido")

public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne //significa muitos para um
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Column(name = "data_pedido")
    private LocalDate dataPedido;

    @Column(name = "total",precision = 20, scale = 2)// precision define as casas decimais armazenadas no banco
     private BigDecimal total;

    @Enumerated(EnumType.STRING)//transforma o enum em string e adiciona a api, visto que ele não está no banco
    @Column(name = "status")
    private StatusPedido statusPedido;

    @OneToMany(mappedBy = "pedido" )
    private List<ItemPedido> itens;

}
