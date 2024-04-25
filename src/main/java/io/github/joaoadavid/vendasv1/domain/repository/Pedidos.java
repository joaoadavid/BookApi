package io.github.joaoadavid.vendasv1.domain.repository;

import io.github.joaoadavid.vendasv1.domain.entity.Cliente;
import io.github.joaoadavid.vendasv1.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Pedidos extends JpaRepository <Pedido, Integer> {

    List<Pedido> findByCliente(Cliente cliente);

}
