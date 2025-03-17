package br.insper.produto.service;

import br.insper.produto.produto.Produto;
import br.insper.produto.produto.ProdutoRepository;
import br.insper.produto.produto.ProdutoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTests {

    @InjectMocks
    private ProdutoService produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    @Test
    void test_listarTodosQuandoNaoHaProdutos() {
        Mockito.when(produtoRepository.findAll()).thenReturn(new ArrayList<>());

        List<Produto> produtos = produtoService.listarTodos();

        Assertions.assertEquals(0, produtos.size());
    }

    @Test
    void test_criarProdutoComSucesso() {
        Produto produto = new Produto();
        produto.setId("1");
        produto.setNome("Produto Teste");
        produto.setPreco(15.0);
        produto.setQuantidadeEstoque(50);

        Mockito.when(produtoRepository.save(produto)).thenReturn(produto);

        Produto produtoCriado = produtoService.criarProduto(produto);

        Assertions.assertEquals("Produto Teste", produtoCriado.getNome());
        Assertions.assertEquals(15.0, produtoCriado.getPreco());
        Assertions.assertEquals(50, produtoCriado.getQuantidadeEstoque());
    }
//    teste para verificar se o produto foi criado

    @Test
    void test_buscarProdutoPorIdComSucesso() {
        Produto produto = new Produto();
        produto.setId("1");
        produto.setNome("Produto Teste");
        produto.setPreco(15.0);
        produto.setQuantidadeEstoque(50);

        Mockito.when(produtoRepository.findById("1")).thenReturn(Optional.of(produto));

        Optional<Produto> produtoEncontrado = produtoService.buscarPorId("1");

        Assertions.assertTrue(produtoEncontrado.isPresent());
        Assertions.assertEquals("Produto Teste", produtoEncontrado.get().getNome());
    }

    @Test
    void test_buscarProdutoPorIdInexistente() {
        Mockito.when(produtoRepository.findById("99")).thenReturn(Optional.empty());

        Optional<Produto> produtoEncontrado = produtoService.buscarPorId("99");

        Assertions.assertFalse(produtoEncontrado.isPresent());
    }

    @Test
    void test_diminuirEstoqueComSucesso() {
        Produto produto = new Produto();
        produto.setId("1");
        produto.setNome("Produto Teste");
        produto.setPreco(15.0);
        produto.setQuantidadeEstoque(50);

        Mockito.when(produtoRepository.findById("1")).thenReturn(Optional.of(produto));
        Mockito.when(produtoRepository.save(Mockito.any(Produto.class))).thenReturn(produto);

        Produto produtoAtualizado = produtoService.diminuirEstoque("1", 10);

        Assertions.assertEquals(40, produtoAtualizado.getQuantidadeEstoque());
    }

    @Test
    void test_diminuirEstoqueProdutoNaoEncontrado() {
        Mockito.when(produtoRepository.findById("99")).thenReturn(Optional.empty());

        RuntimeException exception = Assertions.assertThrows(
                RuntimeException.class,
                () -> produtoService.diminuirEstoque("99", 10));

        Assertions.assertEquals("Produto não encontrado.", exception.getMessage());
    }

    @Test
    void test_diminuirEstoqueQuantidadeInsuficiente() {
        Produto produto = new Produto();
        produto.setId("1");
        produto.setNome("Produto Teste");
        produto.setPreco(15.0);
        produto.setQuantidadeEstoque(5);

        Mockito.when(produtoRepository.findById("1")).thenReturn(Optional.of(produto));

        RuntimeException exception = Assertions.assertThrows(
                RuntimeException.class,
                () -> produtoService.diminuirEstoque("1", 10));

        Assertions.assertEquals("Quantidade em estoque insuficiente.", exception.getMessage());
    }
}