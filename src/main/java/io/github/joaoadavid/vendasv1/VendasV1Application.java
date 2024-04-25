package io.github.joaoadavid.vendasv1;

import io.github.joaoadavid.vendasv1.domain.entity.Cliente;
import io.github.joaoadavid.vendasv1.domain.entity.Pedido;
import io.github.joaoadavid.vendasv1.domain.repository.Clientes;
import io.github.joaoadavid.vendasv1.domain.repository.Pedidos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@SpringBootApplication
public class VendasV1Application {

    @Bean
    public CommandLineRunner initi(@Autowired Clientes clientes,
                                   @Autowired Pedidos pedidos) {
        return args -> {

            System.out.println("Salvando clientes...");
            Cliente fulano = new Cliente("Fulano");
            clientes.save(fulano);

            Pedido pedido = new Pedido();
            pedido.setCliente(fulano);
            pedido.setDataPedido(LocalDate.now());
            pedido.setTotal(BigDecimal.valueOf(100));
            pedidos.save(pedido);

            Cliente cliente = clientes.findClienteFetchPedidos(fulano.getId());
            System.out.println(cliente);
            System.out.println(cliente.getPedidos());

            List<Cliente> result = clientes.encontrarPorNome("João");
            result.forEach(System.out::println);

            boolean existe = clientes.existsByNome("Idris Elba");
            System.out.println("Existe um cliente com nome Idris ? " + existe);

            pedidos.findByCliente(fulano).forEach(System.out::println);

        };

    }

    public static void main(String[] args) {
        SpringApplication.run(VendasV1Application.class, args);
    }

}
