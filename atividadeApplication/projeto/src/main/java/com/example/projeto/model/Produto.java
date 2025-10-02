package com.example.projeto.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "produtos")
public class Produto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Integer quantidade;
    private BigDecimal valor;
    
    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL)
    private List<ClienteProduto> clienteProdutos = new ArrayList<>();

    public Produto() {
    }

    public Produto(String nome, Integer quantidade, BigDecimal valor) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getValor() {
        return valor;
    }
    
    public List<ClienteProduto> getClienteProdutos() {
        return clienteProdutos;
    }
    
    public void setClienteProdutos(List<ClienteProduto> clienteProdutos) {
        this.clienteProdutos = clienteProdutos;
    }
}