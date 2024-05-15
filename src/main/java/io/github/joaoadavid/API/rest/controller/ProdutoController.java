package io.github.joaoadavid.API.rest.controller;

import io.github.joaoadavid.API.domain.entity.Produto;
import io.github.joaoadavid.API.domain.repository.ProdutosRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private ProdutosRepository produtosRepository;

    public ProdutoController(ProdutosRepository produtosRepository) {
        this.produtosRepository = produtosRepository;
    }

    @PostMapping@ApiOperation(("Criar produtos"))
    @ApiResponses({@ApiResponse(code = 200, message = "Produto criado com sucesso"),
            @ApiResponse(code = 404, message = "Não foi possivel criar o produto, verifique as informações.")})
    @ResponseStatus(HttpStatus.CREATED)
    public Produto save (@RequestBody @Valid Produto produto){
        return produtosRepository.save(produto);
    }

    @GetMapping("{id}")@ApiOperation(("Obter detalhes de um produto"))
    @ApiResponses({@ApiResponse(code = 200, message = "Produto encontrado com sucesso"),
            @ApiResponse(code = 404, message = "Produto não encontrado para o ID informado")})
    public Produto getProdutoById(@PathVariable Integer id){
        return produtosRepository.findById(id)
                .orElseThrow( () ->  new ResponseStatusException(HttpStatus.NOT_FOUND,"Cliente não foi encontrado"));
    }

    @DeleteMapping("{id}")
    @ApiOperation(("Deletando um cliente"))
    @ApiResponses({@ApiResponse(code = 200, message = "Produto deletado com sucesso"),
            @ApiResponse(code = 404, message = "Produto não encontrado para o ID informado")})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
        produtosRepository.findById(id)
                .map(produto -> {
                   produtosRepository.delete(produto);
                    return produto;
                })
                .orElseThrow( () ->  new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Produto não encontrado"));
    }

    @PutMapping("{id}")
    @ApiOperation(("Atualizando um produto"))
    @ApiResponses({@ApiResponse(code = 200, message = "Produto atualizado com sucesso"),
            @ApiResponse(code = 404, message = "Produto não encontrado para o ID informado")})
    public void update( @PathVariable Integer id,
                        @RequestBody @Valid Produto produto){
        produtosRepository
                .findById(id)
                .map(produtoExistente -> {
                    produto.setId(produtoExistente.getId());
                    produtosRepository.save(produto);
                    return produtoExistente;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND ,"Produto não encontrado"));
    }
    @GetMapping
    @ApiOperation(("Listar todos os produtos"))
    @ApiResponses({@ApiResponse(code = 200, message = "Produto deletado com sucesso"),
            @ApiResponse(code = 404, message = "Nenhum Produto foi encontrado")})
    public List<Produto> find(Produto filtro){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);
        return produtosRepository.findAll(example);
    }
}


