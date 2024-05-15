package io.github.joaoadavid.API.domain.repository;

import io.github.joaoadavid.API.domain.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsPedidoRepository extends JpaRepository <ItemPedido, Integer> {
}
