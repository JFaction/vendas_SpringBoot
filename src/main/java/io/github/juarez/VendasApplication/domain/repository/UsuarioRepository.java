package io.github.juarez.VendasApplication.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.juarez.VendasApplication.domain.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
    Optional<Usuario> findByLogin(String login);
}
