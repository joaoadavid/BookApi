package io.github.joaoadavid.vendasv1.domain.repository;

import io.github.joaoadavid.vendasv1.domain.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsPedidoRepository extends JpaRepository <ItemPedido, Integer> {
}
