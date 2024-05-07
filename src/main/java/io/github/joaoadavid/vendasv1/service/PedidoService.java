package io.github.joaoadavid.vendasv1.service;

import io.github.joaoadavid.vendasv1.domain.entity.Pedido;
import io.github.joaoadavid.vendasv1.domain.enums.StatusPedido;
import io.github.joaoadavid.vendasv1.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {

    Pedido salvar(PedidoDTO dto);

    Optional<Pedido> obterPedidoCompleto(Integer id);

    void atualizaStatus(Integer id, StatusPedido statusPedido);

}
