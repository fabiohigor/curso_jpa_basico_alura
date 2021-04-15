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
		buscarPorID();

	}

	private static void buscarPorID() {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		ProdutoDao produtoDAO = new ProdutoDao(em);

		Produto pId = produtoDAO.buscarPorId(1l);
		System.out.println("Busca por ID: " + pId.toString());

		em.getTransaction().commit();
		em.close();

	}

	private static void buscarTodos() {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		ProdutoDao produtoDAO = new ProdutoDao(em);

		List<Produto> pTodos = produtoDAO.buscarTodos();
		pTodos.forEach(p -> System.out.println(p.toString()));

		em.getTransaction().commit();
		em.close();

	}

	private static void buscarNome() {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		ProdutoDao produtoDAO = new ProdutoDao(em);

		List<Produto> pTodos = produtoDAO.buscarNome("Xiaomi Redmi");
		pTodos.forEach(p -> System.out.println(p.toString()));

		em.getTransaction().commit();
		em.close();

	}

	private static void buscarNomeDaCategoria() {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		ProdutoDao produtoDAO = new ProdutoDao(em);

		List<Produto> pTodos = produtoDAO.buscarNomeDaCategoria("CELULARES");
		pTodos.forEach(p -> System.out.println(p.toString()));

		em.getTransaction().commit();
		em.close();

	}

	private static void buscarPrecoDoProdutoComNome() {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		ProdutoDao produtoDAO = new ProdutoDao(em);

		BigDecimal preco = produtoDAO.buscarPrecoDoProdutoComNome("Xiaomi Redmi");
		System.out.println("Preço: " + preco);
		em.getTransaction().commit();
		em.close();

	}

	private static void atualizarProduto() {

//		  Fluxo padrão para update no JPA 
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		ProdutoDao produtoDAO = new ProdutoDao(em);

		Produto pId = produtoDAO.buscarPorId(1l);
		System.out.println("Produto Inicial: " + pId.toString());
		pId.setNome("Descrição Atualizada!!!");
		produtoDAO.atualizar(pId);
		pId = produtoDAO.buscarPorId(1l);
		System.out.println("Produto Final atualizado: " + pId.toString());

		em.getTransaction().commit();
		em.close();

		/*
		 * // Nesse exemplo vou inserir uma possivel persistencia com limpeza de conexão
		 * antes do prazo, ai vou ter que reabrir // a transaction para de fato
		 * atualizar
		 * 
		 * EntityManager em = JPAUtil.getEntityManager(); em.getTransaction().begin();
		 * ProdutoDao produtoDAO = new ProdutoDao(em);
		 * 
		 * Produto pId = produtoDAO.buscarPorId(1l);
		 * System.out.println("Produto Inicial: " + pId.toString());
		 * pId.setNome("Descrição Atualizada!!!"); produtoDAO.atualizar(pId);
		 * 
		 * // Fim do salvar padrão // busco novamente o registro e altero seu nome, e
		 * mando salvar com o flush, o clear vem pra limpar a transaction pId =
		 * produtoDAO.buscarPorId(1l); System.out.println("Produto Final atualizado: " +
		 * pId.toString()); em.flush(); //comito o registro em.clear();//limpo a conexão
		 * 
		 * pId = em.merge(pId); // esse merge serve para reabrir a transaction
		 * pId.setNome("Depois do close");
		 * 
		 * em.persist(pId); // mandando gravar na mão o registro no banco pId =
		 * produtoDAO.buscarPorId(1l); System.out.println("Close: " + pId.toString());
		 * // imprimindo registro atualizando com a conexão reaberta!
		 * em.getTransaction().commit(); em.close();
		 */

	}

	private static void cadastrar() {
		EntityManager em = JPAUtil.getEntityManager();
		Categoria celulares = new Categoria("CELULARES");
		Categoria televisor = new Categoria("TV");
		Produto celular = new Produto("Xiaomi Redmi", "Muito legal", new BigDecimal("800"), celulares);
		Produto televisao = new Produto("42 polegadas", "Samsung", new BigDecimal("2000"), televisor);

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
