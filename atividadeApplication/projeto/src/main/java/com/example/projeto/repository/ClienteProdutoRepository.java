package com.example.projeto.repository;

import com.example.projeto.model.ClienteProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClienteProdutoRepository extends JpaRepository<ClienteProduto, Long> {
    List<ClienteProduto> findByClienteId(Long clienteId);
    
    void deleteByClienteIdAndProdutoId(Long clienteId, Long produtoId);
}