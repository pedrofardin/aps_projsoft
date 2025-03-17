package br.insper.produto.controller;

import br.insper.produto.produto.Produto;
import br.insper.produto.produto.ProdutoController;
import br.insper.produto.produto.ProdutoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ProdutoControllerTests {

	@InjectMocks
	private ProdutoController produtoController;

	@Mock
	private ProdutoService produtoService;

	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
		this.mockMvc = MockMvcBuilders
				.standaloneSetup(produtoController)
				.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
				.build();
	}

	@Test
	void testListarTodos() throws Exception {
		List<Produto> produtos = Arrays.asList(
				new Produto("1", "Produto A", 10.0, 100),
				new Produto("2", "Produto B", 20.0, 50)
		);

		ObjectMapper objectMapper = new ObjectMapper();

		Mockito.when(produtoService.listarTodos()).thenReturn(produtos);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/produto"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(produtos)));
	}

	@Test
	void testBuscarPorId() throws Exception {
		Produto produto = new Produto("1", "Produto A", 10.0, 100);
		ObjectMapper objectMapper = new ObjectMapper();

		Mockito.when(produtoService.buscarPorId("1")).thenReturn(java.util.Optional.of(produto));

		mockMvc.perform(MockMvcRequestBuilders.get("/api/produto/1"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(produto)));
	}

	@Test
	void testCriarProduto() throws Exception {
		Produto produto = new Produto("1", "Produto A", 10.0, 100);
		ObjectMapper objectMapper = new ObjectMapper();

		Mockito.when(produtoService.criarProduto(Mockito.any(Produto.class))).thenReturn(produto);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/produto")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(produto)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(produto)));
	}

	@Test
	void testDiminuirEstoque() throws Exception {
		Produto produto = new Produto("1", "Produto A", 10.0, 98);
		ObjectMapper objectMapper = new ObjectMapper();

		Mockito.when(produtoService.diminuirEstoque("1", 2)).thenReturn(produto);

		mockMvc.perform(MockMvcRequestBuilders.put("/api/produto/1/diminuir?quantidade=2"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(produto)));
	}
}
