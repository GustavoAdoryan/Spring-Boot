package com.fag.spring.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fag.spring.models.Produto;

@Service
public class ProdutoService {

    private final List<Produto> produtos = new ArrayList<>();
    private Long idCounter = 1L;

    public List<Produto> listarProdutos() {
        return produtos;
    }

    public Produto adicionarProduto(Produto produto) {
        produto.setId(idCounter++);
        produtos.add(produto);
        return produto;
    }

    public Produto buscarProdutoPorId(Long id) {
        return produtos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Produto editarProduto(Long id, Produto produto) {
        Produto existingProduto = buscarProdutoPorId(id);
        if (existingProduto == null) {
            throw new IllegalArgumentException("Produto não encontrado");
        }

        existingProduto.setNome(produto.getNome());
        existingProduto.setPreco(produto.getPreco());
        existingProduto.setTipo(produto.getTipo());

        return existingProduto;
    }

    public boolean excluirProduto(Long id) {
        Optional<Produto> produtoOptional = produtos.stream()
                .filter(produto -> produto.getId().equals(id))
                .findFirst();
        if (produtoOptional.isPresent()) {
            produtos.remove(produtoOptional.get());
            return true;
        }
        return false;
    }
}
