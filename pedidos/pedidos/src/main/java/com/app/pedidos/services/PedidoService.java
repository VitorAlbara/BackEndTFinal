package com.app.pedidos.services;

import com.app.pedidos.models.Cliente;
import com.app.pedidos.models.Pedido;
import com.app.pedidos.repositories.ClienteRepository;
import com.app.pedidos.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public Pedido criarPedido(Long clienteId, Pedido pedido) {
        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(() -> new RuntimeException("Cliente não encontrado!"));
        pedido.setCliente(cliente);
        return pedidoRepository.save(pedido);
    }

    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    public Pedido buscarPedidoPorId(Long id) {
        return pedidoRepository.findById(id).orElseThrow(() -> new RuntimeException("Pedido não encontrado!"));
    }

    public Pedido editarPedido(Long id, Pedido pedidoAtualizado) {
        Pedido pedido = buscarPedidoPorId(id);
        pedido.setDescricao(pedidoAtualizado.getDescricao());
        pedido.setValor(pedidoAtualizado.getValor());
        pedido.setStatus(pedidoAtualizado.getStatus());
        return pedidoRepository.save(pedido);
    }

    public void removerPedido(Long id) {
        Pedido pedido = buscarPedidoPorId(id);
        if (pedido.getStatus().equals("Enviado") || pedido.getStatus().equals("Entregue")) {
            throw new RuntimeException("Pedidos enviados ou entregues não podem ser removidos!");
        }
        pedidoRepository.delete(pedido);
    }
}