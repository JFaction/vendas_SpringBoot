package io.github.juarez.VendasApplication.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.juarez.VendasApplication.domain.entity.ItemPedido;

public interface ItensPedido extends JpaRepository<ItemPedido, Integer>{
    
}
