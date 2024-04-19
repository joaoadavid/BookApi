package io.github.joaoadavid.vendasv1;

import io.github.joaoadavid.vendasv1.domain.entity.Cliente;
import io.github.joaoadavid.vendasv1.domain.repositorio.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@SpringBootApplication
public class VendasV1Application {

    @Bean
    public CommandLineRunner initi(@Autowired Clientes clientes) {
        return args -> {

            System.out.println("Salvando clientes...");
            clientes.salvar(new Cliente("João David"));
            clientes.salvar(new Cliente("Outro Cliente"));

           List<Cliente> todosClientes = clientes.obterTodos();
           todosClientes.forEach(System.out::println);

            System.out.println("Atualizando clientes...");
           todosClientes.forEach(c ->{
               c.setNome(c.getNome() + " atualizado");
               clientes.atualizar(c);
            });


           todosClientes = clientes.obterTodos();
           todosClientes.forEach(System.out::println);

           System.out.println("Buscando clientes...");
            clientes.buscarPorNome("Cli").forEach(System.out::println);

           System.out.println("Deletando clientes...");
            clientes.obterTodos().forEach(cliente -> {
                clientes.deletar(cliente);
            });

            todosClientes = clientes.obterTodos();
          if(todosClientes.isEmpty()){
               System.out.println("Nenhum cliente encontrado");
           }else {
               todosClientes.forEach(System.out::println);
            }
        };

    }

    public static void main(String[] args) {
        SpringApplication.run(VendasV1Application.class, args);
    }

}
