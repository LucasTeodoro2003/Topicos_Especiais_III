package com.example.projeto.controller;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.projeto.model.Cliente;
import com.example.projeto.service.ClienteService;

import org.springframework.http.HttpStatus;

@Controller
@RequestMapping("/clientes")
public class ClienteWebController {

    private final ClienteService clienteService;

    public ClienteWebController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // Mapeia GET /clientes → redireciona para /clientes/listar
    @GetMapping
    public String index() {
        return "redirect:/clientes/listar";
    }

    // 1. Página de cadastro
    @GetMapping("/cadastrar")
    public String exibirFormCadastro(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "clientes/form";
    }

    @PostMapping("/cadastrar")
    public String cadastrarCliente(
            @Valid @ModelAttribute("cliente") Cliente cliente,
            BindingResult result,
            RedirectAttributes ra) {

        if (result.hasErrors()) {
            // repopula o objeto no formulário em caso de erro
            return "clientes/form";
        }
        clienteService.salvarCliente(cliente);
        ra.addFlashAttribute("success", "Cliente cadastrado com sucesso!");
        return "redirect:/clientes/listar";
    }

    // 2. Página de listagem
    @GetMapping("/listar")
    public String listarClientes(Model model) {
        model.addAttribute("lista", clienteService.listarClientes());
        return "clientes/lista";
    }

    // 3. Detalhes e exclusão
    @GetMapping("/{id}")
    public String detalhesCliente(@PathVariable Long id, Model model) {
        Cliente c = clienteService.buscarPorId(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Cliente não encontrado, id: " + id
            ));
        model.addAttribute("cliente", c);
        return "clientes/detalhe";
    }

    @PostMapping("/{id}/excluir")
    public String excluirCliente(@PathVariable Long id, RedirectAttributes ra) {
        clienteService.deletarCliente(id);
        ra.addFlashAttribute("success", "Cliente excluído com sucesso!");
        return "redirect:/clientes/listar";
    }
    // 4. Edição
    @GetMapping("/{id}/editar")
    public String exibirFormEdicao(@PathVariable Long id, Model model) {
        Cliente c = clienteService.buscarPorId(id)
            .orElseThrow(() -> new ResponseStatusException( 
                HttpStatus.NOT_FOUND, "Cliente não encontrado, id: " + id
            ));
        model.addAttribute("cliente", c);
        return "clientes/form";
    }
}