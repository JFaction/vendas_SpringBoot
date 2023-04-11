package io.github.juarez.VendasApplication.service;

import java.util.Optional;

import io.github.juarez.VendasApplication.domain.entity.Pedido;
import io.github.juarez.VendasApplication.domain.enums.StatusPedido;
import io.github.juarez.VendasApplication.rest.dto.PedidoDTO;

public interface PedidoService {
    Pedido salvar(PedidoDTO dto);
    Optional<Pedido> obterPedidoCompleto(Integer id);
    void atualizaStatus(Integer id, StatusPedido statusPedido);
}
