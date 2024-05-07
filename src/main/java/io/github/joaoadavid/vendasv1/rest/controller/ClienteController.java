package io.github.joaoadavid.vendasv1.rest.controller;

import io.github.joaoadavid.vendasv1.domain.entity.Cliente;
import io.github.joaoadavid.vendasv1.domain.repository.ClientesRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private ClientesRepository clientesRepository;

    public ClienteController(ClientesRepository clientesRepository) {
        this.clientesRepository = clientesRepository;
    }

    @GetMapping("{id}")
    public Cliente getClientById ( @PathVariable Integer id){
      return clientesRepository.findById(id)
              .orElseThrow( () ->  new ResponseStatusException(HttpStatus.NOT_FOUND,"Cliente não foi encontrado"));
       }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente save (@RequestBody @Valid Cliente cliente){
       return clientesRepository.save(cliente);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
        clientesRepository.findById(id)
                .map(cliente ->
                    {
                        clientesRepository.delete(cliente);
                    return cliente;
                })
                .orElseThrow( () ->  new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Cliente não encontrado"));
    }

    @PutMapping("{id}")
    public void update( @PathVariable Integer id, @RequestBody @Valid Cliente cliente){
        clientesRepository
                .findById(id)
                .map(clienteExistente -> {
                    cliente.setId(clienteExistente.getId());
                    clientesRepository.save(cliente);
                    return clienteExistente;
                       }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND ,"Cliente não encontrado"));
    }
    @GetMapping
    public List<Cliente> find(Cliente filtro){
        ExampleMatcher matcher = ExampleMatcher
                                .matching()
                                .withIgnoreCase()
                                .withStringMatcher(
                                        ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);
        return clientesRepository.findAll(example);
    }
}
