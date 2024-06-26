package io.github.joaoadavid.API.service;

import io.github.joaoadavid.API.domain.entity.Pedido;
import io.github.joaoadavid.API.domain.enums.StatusPedido;
import io.github.joaoadavid.API.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {

    Pedido salvar(PedidoDTO dto);

    Optional<Pedido> obterPedidoCompleto(Integer id);

    void atualizaStatus(Integer id, StatusPedido statusPedido);

}
