package io.github.joaoadavid.API.domain.repository;

import io.github.joaoadavid.API.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ClientesRepository extends JpaRepository <Cliente, Integer> {//recebe dois tipos de parametros, tipo e id
   @Query(value = "select * from Cliente c Where c.nome like '%:nome%' ", nativeQuery = true)
    List<Cliente> encontrarPorNome(@Param("nome") String nome);

   @Query("delete from Cliente c where c.nome = ':nome' ")
   @Modifying
   void deleteByNome(String nome);

    boolean existsByNome(String nome);

    @Query("select c from Cliente c left join fetch c.pedidos where c.id =:id ")
    Cliente findClienteFetchPedidos(@Param("id") Integer id);


}
