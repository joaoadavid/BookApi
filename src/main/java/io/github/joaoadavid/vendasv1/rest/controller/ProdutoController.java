package io.github.joaoadavid.vendasv1.rest.controller;

import io.github.joaoadavid.vendasv1.domain.entity.Cliente;
import io.github.joaoadavid.vendasv1.domain.entity.Produto;
import io.github.joaoadavid.vendasv1.domain.repository.ProdutosRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private ProdutosRepository produtosRepository;

    public ProdutoController(ProdutosRepository produtosRepository) {
        this.produtosRepository = produtosRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto save (@RequestBody @Valid Produto produto){
        return produtosRepository.save(produto);
    }

    @GetMapping("{id}")
    public Produto getProdutoById(@PathVariable Integer id){
        return produtosRepository.findById(id)
                .orElseThrow( () ->  new ResponseStatusException(HttpStatus.NOT_FOUND,"Cliente não foi encontrado"));
    }

    @DeleteMapping("{id}")
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


