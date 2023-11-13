package com.fag.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fag.spring.Services.ProdutoService;
import com.fag.spring.models.Produto;

@Controller
@RequestMapping("/")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/")
    public String listarProdutos(Model model) {
        model.addAttribute("produtos", produtoService.listarProdutos());
        return "listaProdutos";
    }

    @GetMapping("/adicionar")
    public String exibirFormularioAdicao(Model model) {
        model.addAttribute("produto", new Produto());
        return "adicionaProdutos";
    }

    @PostMapping("/adicionar")
    public String adicionarProduto(@ModelAttribute Produto produto, RedirectAttributes redirectAttributes) {
        produtoService.adicionarProduto(produto);
        redirectAttributes.addFlashAttribute("mensagem", "Produto adicionado com sucesso");
        return "redirect:/";
    }

    @GetMapping("/editar/{id}")
    public String exibirFormularioEdicao(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Produto produto = produtoService.buscarProdutoPorId(id);
        if (produto == null) {
            redirectAttributes.addFlashAttribute("erro", "Produto não encontrado");
            return "redirect:/";
        }
        model.addAttribute("produto", produto);
        return "editaProdutos";
    }

    @PostMapping("/editar/{id}")
    public String editarProduto(@PathVariable Long id, @ModelAttribute Produto produto,
            RedirectAttributes redirectAttributes) {
        Produto existingProduto = produtoService.editarProduto(id, produto);
        if (existingProduto == null) {
            redirectAttributes.addFlashAttribute("erro", "Produto não encontrado");
            return "redirect:/";
        }
        redirectAttributes.addFlashAttribute("mensagem", "Produto editado com sucesso");
        return "redirect:/";
    }

    @GetMapping("/excluir/{id}")
    public String exibirPaginaExclusao(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Produto produto = produtoService.buscarProdutoPorId(id);
        if (produto == null) {
            redirectAttributes.addFlashAttribute("erro", "Produto não encontrado");
            return "redirect:/";
        }

        model.addAttribute("produto", produto);
        return "confirmaExclusao";
    }

    @GetMapping("/confirmaExclusao")
    public String confirmaExclusao() {
        return "confirmaExclusao";
    }

    @PostMapping("/excluir/{id}")
    public String excluirProduto(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        Produto produto = produtoService.buscarProdutoPorId(id);
        if (produto == null) {
            redirectAttributes.addFlashAttribute("erro", "Produto não encontrado");
            return "redirect:/";
        }

        boolean sucesso = produtoService.excluirProduto(id);
        if (!sucesso) {
            redirectAttributes.addFlashAttribute("erro", "Falha ao excluir o produto");
        } else {
            redirectAttributes.addFlashAttribute("mensagem", "Produto excluído com sucesso");
        }
        return "redirect:/";
    }

}
