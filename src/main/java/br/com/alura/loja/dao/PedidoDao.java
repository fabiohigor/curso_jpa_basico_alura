package br.com.alura.loja.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.modelo.Pedido;
import br.com.alura.loja.vo.RelatorioDeVendasVO;

public class PedidoDao {

	private EntityManager em;

	public PedidoDao(EntityManager em) {
		this.em = em;
	}

	public void cadastrar(Pedido pedido) {
		this.em.persist(pedido);
	}

	public void atualizar(Pedido pedido) {
		this.em.merge(pedido);
	}

	public void remover(Pedido pedido) {
		pedido = em.merge(pedido);
		this.em.remove(pedido);
	}

	public Pedido buscarPorId(Long id) {
		return this.em.find(Pedido.class, id);
	}

	public BigDecimal valorTotalVendido() {
		String jpql = "SELECT SUM(p.valorTotal) FROM Pedido p";
		return this.em.createQuery(jpql, BigDecimal.class).getSingleResult();
	}

	public List<Object[]> relatorioDeVendas(){
		String jpql =  "SELECT produto.nome, " 
					 + "SUM(item.quantidade), "
					 + "MAX(pedido.data) "
					 + "FROM Pedido pedido "
					 + "JOIN pedido.itens item "
					 + "JOIN item.produto produto "
					 + "GROUP BY produto.nome "
					 + "ORDER BY  item.quantidade DESC";
		return em.createQuery(jpql, Object[].class)
				.getResultList();
	
	}
	
	public List<RelatorioDeVendasVO> relatorioDeVendasVO(){
		String jpql =  "SELECT new br.com.alura.loja.vo.RelatorioDeVendasVO (produto.nome, " 
					 + "SUM(item.quantidade), "
					 + "MAX(pedido.data)) "
					 + "FROM Pedido pedido "
					 + "JOIN pedido.itens item "
					 + "JOIN item.produto produto "
					 + "GROUP BY produto.nome "
					 + "ORDER BY  item.quantidade DESC";
		return em.createQuery(jpql, RelatorioDeVendasVO.class)
				.getResultList();
	
	}
	
//	JOIN FETCH deve ser usado quando relacionamento for do tipo LAZY e precisa de um atributo relacionado
//	Assim se faz necessario adicionar essa clausula
//	Se o EntityManager ainda estiver aberto ele faz a busca, caso contrario, se faz necessario ter a consulta personalizada
	public Pedido buscarPedidoComCliente(Long id) {
		return em.createQuery("SELECT p FROM Pedido p JOIN FETCH p.cliente WHERE p.id = :id", Pedido.class)
				.setParameter("id", id)
				.getSingleResult();
	}

}
