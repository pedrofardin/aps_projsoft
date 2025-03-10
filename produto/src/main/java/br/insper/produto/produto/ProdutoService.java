package br.insper.produto.produto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto criarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Optional<Produto> buscarPorId(String id) {
        return produtoRepository.findById(id);
    }

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public Produto diminuirEstoque(String id, int quantidade) {
        Optional<Produto> produtoOpt = produtoRepository.findById(id);
        if (produtoOpt.isPresent()) {
            Produto produto = produtoOpt.get();
            produto.diminuirEstoque(quantidade);
            return produtoRepository.save(produto);
        } else {
            throw new RuntimeException("Produto não encontrado.");
        }
    }
}
