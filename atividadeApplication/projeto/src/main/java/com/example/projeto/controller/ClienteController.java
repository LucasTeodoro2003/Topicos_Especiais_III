package com.example.projeto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.projeto.model.Cliente;
import com.example.projeto.service.ClienteProdutoService;
import com.example.projeto.service.ClienteService;
import com.example.projeto.service.ProdutoService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;
    private final ProdutoService produtoService;
    private final ClienteProdutoService clienteProdutoService;

    @Autowired
    public ClienteController(
            ClienteService clienteService,
            ProdutoService produtoService,
            ClienteProdutoService clienteProdutoService) {
        this.clienteService = clienteService;
        this.produtoService = produtoService;
        this.clienteProdutoService = clienteProdutoService;
    }

    @GetMapping
    public String index() {
        return "redirect:/clientes/listar";
    }

    @GetMapping("/listar")
    public String listarClientes(Model model) {
        List<Cliente> clientes = clienteService.listarClientes();
        model.addAttribute("clientes", clientes);
        return "clientes/listar";
    }

    @GetMapping("/cadastrar")
    public String formCadastrarCliente(Model model) {
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("produtos", produtoService.listarProdutos());
        return "clientes/cadastrar";
    }

    @PostMapping("/cadastrar")
    public String cadastrarCliente(@ModelAttribute Cliente cliente,
            BindingResult result) {
        if (result.hasErrors()) {
            return "clientes/cadastrar";
        }

        clienteService.salvarCliente(cliente);
        return "redirect:/clientes/listar";
    }

    @GetMapping("/{id}")
    public String detalharCliente(@PathVariable Long id, Model model) {
        Optional<Cliente> clienteOpt = clienteService.buscarPorId(id);
        if (clienteOpt.isEmpty()) {
            return "redirect:/clientes/listar";
        }

        Cliente cliente = clienteOpt.get();
        model.addAttribute("cliente", cliente);
        model.addAttribute("produtos", produtoService.listarProdutos());
        model.addAttribute("clienteProdutos", clienteProdutoService.listarProdutosPorCliente(id));
        return "clientes/detalhes";
    }

    @PostMapping("/{id}/excluir")
    public String excluirCliente(@PathVariable Long id) {
        clienteService.deletarCliente(id);
        return "redirect:/clientes/listar";
    }

    @PostMapping("/{id}/produto/adicionar")
    public String adicionarProdutoAoCliente(
            @PathVariable Long id,
            @RequestParam Long produtoId,
            @RequestParam Integer quantidade) {

        clienteProdutoService.associarProduto(id, produtoId, quantidade);
        return "redirect:/clientes/" + id;
    }

    @PostMapping("/{clienteId}/produto/{produtoId}/remover")
    public String removerProdutoDoCliente(
            @PathVariable Long clienteId,
            @PathVariable Long produtoId) {

        clienteProdutoService.removerProduto(clienteId, produtoId);
        return "redirect:/clientes/" + clienteId;
    }

    @GetMapping("/{id}/editar")
    public String editarCliente(@PathVariable Long id, Model model) {
        Optional<Cliente> clienteOpt = clienteService.buscarPorId(id);
        if (clienteOpt.isEmpty()) {
            return "redirect:/clientes/listar";
        }

        model.addAttribute("cliente", clienteOpt.get());
        return "clientes/cadastrar";
    }

    @PostMapping("/{id}/editar")
    public String atualizarCliente(@PathVariable Long id, @ModelAttribute Cliente cliente, BindingResult result) {
        if (result.hasErrors()) {
            return "clientes/cadastrar";
        }

        clienteService.salvarCliente(cliente);
        return "redirect:/clientes/listar";
    }
}