package br.com.alura.loja.testes;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.dao.ClienteDao;
import br.com.alura.loja.dao.PedidoDao;
import br.com.alura.loja.dao.ProdutoDao;
import br.com.alura.loja.modelo.Cliente;
import br.com.alura.loja.modelo.ItemPedido;
import br.com.alura.loja.modelo.Pedido;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.util.JPAUtil;
import br.com.alura.loja.vo.RelatorioDeVendasVO;

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
		Produto produto2VO = produtoDAO.buscarPorId(2l);
		clienteDAO.cadastrar(cliente);

		Pedido pedidoVO = new Pedido(cliente);
		pedidoVO.adicionarItem(new ItemPedido(10, pedidoVO, produtoVO));
		pedidoVO.adicionarItem(new ItemPedido(2, pedidoVO, produto2VO));
		PedidoDao pedidoDAO = new PedidoDao(em);
		pedidoDAO.cadastrar(pedidoVO);

		Pedido aux = pedidoDAO.buscarPorId(1l);
		em.getTransaction().commit();
		BigDecimal valorTotal = pedidoDAO.valorTotalVendido();
		System.out.println("Valor TOtal:" + valorTotal);

		System.out.println("TOTAL DE VENDAS");
		List<Object[]> relatorio = pedidoDAO.relatorioDeVendas();

		relatorio.forEach(r -> {
			System.out.println(r[0]);
			System.out.println(r[1]);
			System.out.println(r[2]);
		});

		System.out.println("TOTAL DE VENDAS COM VO");
		List<RelatorioDeVendasVO> relatorioVO = pedidoDAO.relatorioDeVendasVO();

		relatorioVO.forEach(System.out::println);
		aux = pedidoDAO.buscarPedidoComCliente(1l);
		
		em.close();
		System.out.println("****"+aux.getCliente().getNome());
		
	}

}
