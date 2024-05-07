package io.github.joaoadavid.vendasv1.domain.repository;

import io.github.joaoadavid.vendasv1.domain.entity.Cliente;
import io.github.joaoadavid.vendasv1.domain.entity.Pedido;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PedidosRepository extends JpaRepository <Pedido, Integer> {

    List<Pedido> findByCliente(Cliente cliente);

    @Query("select p  from Pedido p left join fetch p.itens where p.id = :id")
    Optional<Pedido> findByIdFetchItens(Integer id);

}
