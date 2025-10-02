package com.example.projeto.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "clientes_produtos")
public class ClienteProduto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    
    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;
    
    private Integer quantidade;
    
    @Column(name = "data_associacao")
    private LocalDateTime dataAssociacao;
    
    public ClienteProduto() {
        this.dataAssociacao = LocalDateTime.now();
    }
    
    public ClienteProduto(Cliente cliente, Produto produto, Integer quantidade) {
        this.cliente = cliente;
        this.produto = produto;
        this.quantidade = quantidade;
        this.dataAssociacao = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDateTime getDataAssociacao() {
        return dataAssociacao;
    }

    public void setDataAssociacao(LocalDateTime dataAssociacao) {
        this.dataAssociacao = dataAssociacao;
    }
}