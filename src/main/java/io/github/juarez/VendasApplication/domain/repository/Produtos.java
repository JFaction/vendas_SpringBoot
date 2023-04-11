package io.github.juarez.VendasApplication.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.github.juarez.VendasApplication.domain.entity.Produto;

public interface Produtos extends JpaRepository<Produto, Integer>{

    @Query(value = " select * from Produto c where c.descricao like '%:descricao%' ",nativeQuery = true)
    List<Produto> encontrarPorNome(@Param("descricao") String descricao);

    @Query(" delete from Produto c where c.descricao = :descricao")
    @Modifying
    void deleteByDescricao(String descricao);

    boolean existsByDescricao(String descricao);

}
