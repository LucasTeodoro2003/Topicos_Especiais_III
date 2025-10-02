package com.example.projeto.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clientes")
public class Cliente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Integer idade;
    
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClienteProduto> clienteProdutos = new ArrayList<>();

    public Cliente() {}

    public Cliente(String nome, Integer idade) {
        this.nome = nome;
        this.idade = idade;
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

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public Integer getIdade() {
        return idade;
    }
    
    public List<ClienteProduto> getClienteProdutos() {
        return clienteProdutos;
    }
    
    public void setClienteProdutos(List<ClienteProduto> clienteProdutos) {
        this.clienteProdutos = clienteProdutos;
    }
    
    public void adicionarProduto(Produto produto, Integer quantidade) {
        ClienteProduto clienteProduto = new ClienteProduto(this, produto, quantidade);
        clienteProdutos.add(clienteProduto);
    }
    
    public void removerProduto(Produto produto) {
        clienteProdutos.removeIf(cp -> cp.getProduto().getId().equals(produto.getId()));
    }
}