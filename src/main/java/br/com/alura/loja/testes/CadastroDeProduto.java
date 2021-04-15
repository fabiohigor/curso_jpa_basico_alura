package br.com.alura.loja.testes;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.dao.CategoriaDao;
import br.com.alura.loja.dao.ProdutoDao;
import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.util.JPAUtil;

public class CadastroDeProduto {
	
	public static void main(String[] args) {
		cadastrar();
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		ProdutoDao produtoDAO = new ProdutoDao(em);
		
		Produto pId = produtoDAO.buscarPorId(1l);
		System.out.println(pId.toString());
		
		List<Produto> produtos = produtoDAO.buscarNomeDaCategoria("TV");
		produtos.forEach(pTodos -> System.out.println(pTodos.toString()));
		
		
		BigDecimal precoProduto = produtoDAO.buscarPrecoDoProdutoComNome("Xiaomi Redmi");
		System.out.println(precoProduto);
		em.getTransaction().commit();
		em.close();
	}

	private static void cadastrar() {
		Categoria celulares = new Categoria("CELULARES");
		Categoria televisor = new Categoria("TV");
		Produto celular = new Produto("Xiaomi Redmi", "Muito legal", new BigDecimal("800"), celulares );
		Produto televisao = new Produto("42 polegadas", "Samsung", new BigDecimal("2000"), televisor );
		
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDao produtoDao = new ProdutoDao(em);
		CategoriaDao categoriaDao = new CategoriaDao(em);
		
		em.getTransaction().begin();
		categoriaDao.cadastrar(televisor);
		categoriaDao.cadastrar(celulares);
		produtoDao.cadastrar(celular);
		produtoDao.cadastrar(televisao);
		
		em.getTransaction().commit();
		em.close();
	}

}
