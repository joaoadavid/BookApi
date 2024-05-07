package io.github.joaoadavid.vendasv1.domain.repository;

import io.github.joaoadavid.vendasv1.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutosRepository extends JpaRepository<Produto, Integer> {
}
