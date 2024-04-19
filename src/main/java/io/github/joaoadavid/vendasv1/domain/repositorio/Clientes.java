package io.github.joaoadavid.vendasv1.domain.repositorio;

import io.github.joaoadavid.vendasv1.domain.entity.Cliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class Clientes {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

    @Transactional//significa que este método gera uma transação no banco de dados.
    public Cliente salvar (Cliente cliente) {
        entityManager.persist(cliente);
        return cliente;
    }

    @Transactional
    public Cliente atualizar (Cliente cliente) {
      entityManager.merge(cliente);//atualiza os valores do banco
        return cliente;
    };

    @Transactional
    public void deletar (Cliente cliente) {
        if(!entityManager.contains(cliente)){
            cliente = entityManager.merge(cliente);
        }
        entityManager.remove(cliente);
    }
    @Transactional
    public void deletar (Integer id) {
       Cliente cliente = entityManager.find(Cliente.class, id);//busca através da entidade do banco e o identificador para busca
       deletar(cliente);//utiliza o método anterior para deletar o cliente encontrado via id acima
    }

    @Transactional(readOnly = true)//demontra que é apenas uma leitura na busca
    public List<Cliente> buscarPorNome(String nome) {
       String jpql = " select c from Cliente c where c.nome = :nome ";//: serve para definir o parametro
        TypedQuery<Cliente> query = entityManager.createQuery(jpql,Cliente.class);
        query.setParameter("nome", "%" + nome + "%");
        return query.getResultList();
    }
    @Transactional(readOnly = true)
    public List<Cliente> obterTodos() {
        return entityManager
                .createQuery("from Cliente",Cliente.class)
                .getResultList();
    }

}
