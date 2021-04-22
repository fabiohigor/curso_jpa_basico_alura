package br.com.alura.loja.testes;

import java.math.BigDecimal;

import javax.persistence.EntityManager;

import br.com.alura.loja.dao.CategoriaDao;
import br.com.alura.loja.dao.ClienteDao;
import br.com.alura.loja.dao.PedidoDao;
import br.com.alura.loja.dao.ProdutoDao;
import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.modelo.Cliente;
import br.com.alura.loja.modelo.ItemPedido;
import br.com.alura.loja.modelo.Pedido;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.util.JPAUtil;

public class CadastroDePedidos {

	public static void main(String[] args) {
		CadastroDeProduto.main(args);
		
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		Cliente cliente = new Cliente("Fabio Higor", "030880");
		ClienteDao clienteDAO = new ClienteDao(em);
		ProdutoDao produtoDAO = new ProdutoDao(em);
		clienteDAO.cadastrar(cliente);
		Produto produtoVO = produtoDAO.buscarPorId(1l);
		clienteDAO.cadastrar(cliente);
		
		Pedido pedidoVO = new Pedido(cliente);
		pedidoVO.adicionarItem(new ItemPedido(10, pedidoVO, produtoVO));
		PedidoDao pedidoDAO = new PedidoDao(em);
		pedidoDAO.cadastrar(pedidoVO);
		
		
		Pedido aux = pedidoDAO.buscarPorId(1l);
		
		em.getTransaction().commit();
		em.close();
		
	}

}
