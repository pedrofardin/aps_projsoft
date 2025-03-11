package br.insper.produto.produto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    public Produto criarProduto(@RequestBody Produto produto) {
        return produtoService.criarProduto(produto);
    }

    @GetMapping("/{id}")
    public Optional<Produto> buscarPorId(@PathVariable String id) {
        return produtoService.buscarPorId(id);
    }

    @PutMapping("/{id}/diminuir")
    public Produto diminuirEstoque(@PathVariable String id, @RequestParam int quantidade) {
        return produtoService.diminuirEstoque(id, quantidade);
    }

    @GetMapping
    public List<Produto> listarTodos() {
        return produtoService.listarTodos();
    }
}
