package io.github.juarez.VendasApplication.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.juarez.VendasApplication.domain.entity.Cliente;
import io.github.juarez.VendasApplication.domain.entity.ItemPedido;
import io.github.juarez.VendasApplication.domain.entity.Pedido;
import io.github.juarez.VendasApplication.domain.entity.Produto;
import io.github.juarez.VendasApplication.domain.enums.StatusPedido;
import io.github.juarez.VendasApplication.domain.repository.Clientes;
import io.github.juarez.VendasApplication.domain.repository.ItensPedido;
import io.github.juarez.VendasApplication.domain.repository.Pedidos;
import io.github.juarez.VendasApplication.domain.repository.Produtos;
import io.github.juarez.VendasApplication.exception.PedidoNaoEncontradoException;
import io.github.juarez.VendasApplication.exception.RegraNegocioException;
import io.github.juarez.VendasApplication.rest.dto.ItemPedidoDTO;
import io.github.juarez.VendasApplication.rest.dto.PedidoDTO;
import io.github.juarez.VendasApplication.service.PedidoService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService{
    private final Pedidos repository;
    private final Clientes clientesRepository;
    private final Produtos produtosRespository;
    private final ItensPedido ItemsPedidoRepository;

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto) {
        Integer idCliente = dto.getCliente();
        Cliente cliente = clientesRepository.
                findById(idCliente).
                orElseThrow(() -> new RegraNegocioException("Código de cliente inválido"));
        
        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> itemsPedido = converterItems(pedido, dto.getItems());
        repository.save(pedido);
        ItemsPedidoRepository.saveAll(itemsPedido);
        pedido.setItens(itemsPedido);
        return pedido;
    }

    private List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDTO> items){
        if(items.isEmpty()){
            throw new RegraNegocioException("Não é possivel realizar um pedido sem itens.");
        }

        return items
                .stream()
                .map( dto -> {
                    Integer idProduto = dto.getProduto();
                    Produto produto = produtosRespository
                            .findById(idProduto)
                            .orElseThrow(
                                () -> new RegraNegocioException(
                                    "Código de produto inválido. " + idProduto
                                ));

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;
                }).collect(Collectors.toList());

        
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return repository.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {
        repository.
            findById(id)
            .map(pedido -> {
                pedido.setStatus(statusPedido);
                return repository.save(pedido);
            }).orElseThrow(() -> new  PedidoNaoEncontradoException() );
    }

    
}
