package br.com.alura.loja.modelo;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author fabio.nascimento
 *
 */
@Entity
@Table(name = "categoriasComChaveComposta")
public class CategoriaComChaveComposta {

	@EmbeddedId
	private CategoriaComChaveCompostaId categoriaID;

	public CategoriaComChaveComposta() {
	}
	
	public CategoriaComChaveComposta(String nome) {
		this.categoriaID = new CategoriaComChaveCompostaId(nome, "XPTO");
	}


	public String getNome() {
		return this.categoriaID.getNome();
	}

	public String getTipo() {
		return this.categoriaID.getTipo();
	}

}
