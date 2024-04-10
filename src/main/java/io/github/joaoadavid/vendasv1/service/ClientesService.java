package io.github.joaoadavid.vendasv1.service;

import io.github.joaoadavid.vendasv1.model.Cliente;
import io.github.joaoadavid.vendasv1.repositpry.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientesService {

    private ClientesRepository repository;

    @Autowired
    public ClientesService(ClientesRepository repository){
        this.repository = repository;
    }

    public void salvarCliente(Cliente cliente){
        validarCliente(cliente);
        this.repository.persistir(cliente);
    }

    public void validarCliente(Cliente cliente){
        //ap´lica validações
    }
}
