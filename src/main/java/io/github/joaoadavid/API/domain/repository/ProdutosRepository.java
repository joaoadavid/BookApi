package io.github.joaoadavid.API.domain.repository;

import io.github.joaoadavid.API.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutosRepository extends JpaRepository<Produto, Integer> {
}
