package io.github.joaoadavid.API.rest.controller;

import io.github.joaoadavid.API.domain.entity.Cliente;
import io.github.joaoadavid.API.domain.repository.ClientesRepository;
import io.swagger.annotations.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api("Clientes")
@RequestMapping("/api/clientes")
public class ClienteController {

    private ClientesRepository clientesRepository;

    public ClienteController(ClientesRepository clientesRepository) {
        this.clientesRepository = clientesRepository;
    }


    @GetMapping("{id}")
    @ApiOperation(("Obter detalhes de um cliente"))
    @ApiResponses({@ApiResponse(code = 200, message = "Cliente encontrado"),
    @ApiResponse(code = 404, message = "Cliente não encontrado para o ID informado")})
    public Cliente getClientById ( @PathVariable Integer id){
      return clientesRepository.findById(id)
              .orElseThrow( () ->  new ResponseStatusException(HttpStatus.NOT_FOUND,"Cliente não foi encontrado"));
       }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(("Salva novo cliente"))
    @ApiResponses({@ApiResponse(code = 201, message = "Cliente salvo com sucesso"),
            @ApiResponse(code = 404, message = "Erro de validação")})
    public Cliente save (@RequestBody @Valid Cliente cliente){
       return clientesRepository.save(cliente);
    }

    @DeleteMapping("{id}")
    @ApiOperation(("Deletando um cliente"))
    @ApiResponses({@ApiResponse(code = 200, message = "Cliente deletado com sucesso"),
            @ApiResponse(code = 404, message = "Cliente não encontrado para o ID informado")})
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
    @ApiOperation(("Atualizar as informações do cliente"))
    @ApiResponses({@ApiResponse(code = 200, message = "Cliente atualizado com sucesso"),
            @ApiResponse(code = 404, message = "Cliente não encontrado para o ID informado")})
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
    @ApiOperation(("Listar todos os clientes"))
    @ApiResponses({@ApiResponse(code = 200, message = "Cliente encontrado"),
            @ApiResponse(code = 404, message = "Nenhum cliente encontrado")})
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
