package com.example.projeto.service;

import com.example.projeto.model.Cliente;
import com.example.projeto.model.ClienteProduto;
import com.example.projeto.model.Produto;
import com.example.projeto.repository.ClienteProdutoRepository;
import com.example.projeto.repository.ClienteRepository;
import com.example.projeto.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteProdutoService {
    
    private final ClienteProdutoRepository clienteProdutoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    
    public ClienteProdutoService(
            ClienteProdutoRepository clienteProdutoRepository,
            ClienteRepository clienteRepository,
            ProdutoRepository produtoRepository) {
        this.clienteProdutoRepository = clienteProdutoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }
    
    public List<ClienteProduto> listarProdutosPorCliente(Long clienteId) {
        return clienteProdutoRepository.findByClienteId(clienteId);
    }
    
    @Transactional
    public ClienteProduto associarProduto(Long clienteId, Long produtoId, Integer quantidade) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(clienteId);
        Optional<Produto> produtoOpt = produtoRepository.findById(produtoId);
        
        if (clienteOpt.isPresent() && produtoOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            Produto produto = produtoOpt.get();
            
            // Verificamos se o produto já está associado
            for (ClienteProduto cp : cliente.getClienteProdutos()) {
                if (cp.getProduto().getId().equals(produtoId)) {
                    // Se já existe, atualiza a quantidade
                    cp.setQuantidade(quantidade);
                    return clienteProdutoRepository.save(cp);
                }
            }
            
            // Se não existe, cria uma nova associação
            ClienteProduto clienteProduto = new ClienteProduto(cliente, produto, quantidade);
            return clienteProdutoRepository.save(clienteProduto);
        }
        
        throw new RuntimeException("Cliente ou Produto não encontrado");
    }
    
    @Transactional
    public void removerProduto(Long clienteId, Long produtoId) {
        clienteProdutoRepository.deleteByClienteIdAndProdutoId(clienteId, produtoId);
    }
}