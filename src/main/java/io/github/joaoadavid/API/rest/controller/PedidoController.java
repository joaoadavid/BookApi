package io.github.joaoadavid.API.rest.controller;

import io.github.joaoadavid.API.domain.entity.ItemPedido;
import io.github.joaoadavid.API.domain.entity.Pedido;
import io.github.joaoadavid.API.domain.enums.StatusPedido;
import io.github.joaoadavid.API.rest.dto.AtualizacaoStatusPedidoDTO;
import io.github.joaoadavid.API.rest.dto.InformacaoItemPedidoDTO;
import io.github.joaoadavid.API.rest.dto.InformacoesPedidoDTO;
import io.github.joaoadavid.API.rest.dto.PedidoDTO;
import io.github.joaoadavid.API.service.PedidoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @ApiOperation(("Criando um pedido"))
    @ApiResponses({@ApiResponse(code = 200, message = "Pedido criado com sucesso"),
            @ApiResponse(code = 404, message = "Não foi possivel criar o pedido")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer save(@RequestBody @Valid PedidoDTO dto){
        Pedido pedido = pedidoService.salvar(dto);
        return pedido.getId();
    }

    @ApiOperation(("Atualiza status de um pedido"))
    @ApiResponses({@ApiResponse(code = 200, message = "Status do pedido atualizado com sucesso"),
            @ApiResponse(code = 404, message = "Pedido não encontrado para o ID informado")})
    @PatchMapping("{id}")
    public void updateStatus(@RequestBody AtualizacaoStatusPedidoDTO atualizacaoStatusPedido,
                             @PathVariable Integer id){

        String novoStatus = atualizacaoStatusPedido.getNovoStatus();
        pedidoService.atualizaStatus(id, StatusPedido.valueOf(novoStatus));
    }

    @ApiOperation(("Listando informações de um pedido"))
    @ApiResponses({@ApiResponse(code = 200, message = " Pedido encontrado com sucesso"),
            @ApiResponse(code = 404, message = "Pedido não encontrado para o ID informado")})
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
