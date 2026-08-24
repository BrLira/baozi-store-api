package br.com.uninter.baozistore.controller;

import br.com.uninter.baozistore.dto.PedidoRequest;
import br.com.uninter.baozistore.exception.ResourceNotFoundException;
import br.com.uninter.baozistore.model.Cliente;
import br.com.uninter.baozistore.model.Pedido;
import br.com.uninter.baozistore.model.Produto;
import br.com.uninter.baozistore.repository.ClienteRepository;
import br.com.uninter.baozistore.repository.PedidoRepository;
import br.com.uninter.baozistore.repository.ProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoController(
            PedidoRepository pedidoRepository,
            ClienteRepository clienteRepository,
            ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }

    @PostMapping
    public ResponseEntity<Pedido> criar(@Valid @RequestBody PedidoRequest dados) {
        Pedido pedido = montarPedido(new Pedido(), dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoRepository.save(pedido));
    }

    @GetMapping
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Pedido consultarPorId(@PathVariable Long id) {
        return buscarPedido(id);
    }

    @PutMapping("/{id}")
    public Pedido atualizar(@PathVariable Long id, @Valid @RequestBody PedidoRequest dados) {
        Pedido pedido = buscarPedido(id);
        return pedidoRepository.save(montarPedido(pedido, dados));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable Long id) {
        pedidoRepository.delete(buscarPedido(id));
        return ResponseEntity.noContent().build();
    }

    private Pedido montarPedido(Pedido pedido, PedidoRequest dados) {
        Cliente cliente = clienteRepository.findById(dados.clienteId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cliente " + dados.clienteId() + " nao encontrado"));
        Produto produto = produtoRepository.findById(dados.produtoId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Produto " + dados.produtoId() + " nao encontrado"));
        pedido.setCliente(cliente);
        pedido.setProduto(produto);
        pedido.setQuantidade(dados.quantidade());
        return pedido;
    }

    private Pedido buscarPedido(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido " + id + " nao encontrado"));
    }
}
