package io.github.juarez.VendasApplication.exception;

public class PedidoNaoEncontradoException extends RuntimeException{

    public PedidoNaoEncontradoException() {
        super("Pedido não encontrado");
    }

}
