package io.github.joaoadavid.vendasv1.rest.controller;

import io.github.joaoadavid.vendasv1.domain.entity.ItemPedido;
import io.github.joaoadavid.vendasv1.domain.entity.Pedido;
import io.github.joaoadavid.vendasv1.domain.enums.StatusPedido;
import io.github.joaoadavid.vendasv1.rest.dto.AtualizacaoStatusPedidoDTO;
import io.github.joaoadavid.vendasv1.rest.dto.InformacaoItemPedidoDTO;
import io.github.joaoadavid.vendasv1.rest.dto.InformacoesPedidoDTO;
import io.github.joaoadavid.vendasv1.rest.dto.PedidoDTO;
import io.github.joaoadavid.vendasv1.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer save(@RequestBody @Valid PedidoDTO dto){
        Pedido pedido = pedidoService.salvar(dto);
        return pedido.getId();
    }

    @PatchMapping("{id}")
    public void updateStatus(@RequestBody AtualizacaoStatusPedidoDTO atualizacaoStatusPedido,
                             @PathVariable Integer id){

        String novoStatus = atualizacaoStatusPedido.getNovoStatus();
        pedidoService.atualizaStatus(id, StatusPedido.valueOf(novoStatus));
    }

    @GetMapping("{id}")
    public InformacoesPedidoDTO getById(@PathVariable Integer id){
        return pedidoService
                .obterPedidoCompleto(id)
                .map(p -> converter(p))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CREATED, "Pedido não encontrado"));

    }

    private InformacoesPedidoDTO converter(Pedido pedido){
        return InformacoesPedidoDTO
                .builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .statusPedido(pedido.getStatusPedido().name())
                .listaDeItems(converter(pedido.getItens()))
                .build();
    }

    private List<InformacaoItemPedidoDTO> converter(List<ItemPedido> itens){
        if(CollectionUtils.isEmpty(itens)){
            return Collections.emptyList();
        }
        return itens.stream().map(
                item -> InformacaoItemPedidoDTO
                .builder().descricaoProduto(item.getProduto().getDescricao())
                        .precoUnitario(item.getProduto().getPreco())
                        .quantidade(item.getQuantidade())
                        .build()).collect(Collectors.toList());
    }
}
