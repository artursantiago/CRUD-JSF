package entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "aluno", schema = "graduacao")
public class Aluno implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Coluna Id da tabela aluno no banco de dados.
	 * Definida para ser gerada automaticamente a partir de uma sequencia aluno_seq.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "aluno_seq")
	@SequenceGenerator(name = "aluno_seq", sequenceName = "aluno_seq", schema = "graduacao", allocationSize = 1)
	private int id;
	
	/**
	 * Coluna matricula da tabela aluno no banco de dados.
	 */
	@Column(name = "matricula")
	private String matricula;
	
	/**
	 * Coluna ano_de_entrada da tabela aluno no banco de dados.
	 */
	@Column(name = "ano_de_entrada")
	private String anoDeEntrada;
	
	/**
	 * Coluna id_pessoa da tabela aluno no banco de dados.
	 * A coluna é uma chave estrangeira para a coluna id da tabela pessoa.
	 * Possui uma relação Muitos-para-um, ou seja, podem existir varios vínculos de aluno para uma única pessoa.
	 */
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_pessoa", referencedColumnName = "id", nullable = false)
	private Pessoa pessoa = new Pessoa();

	/**
	 * Valida se os dados do aluno foram preenchidos para salvar
	 */
	public void validar() {
		/**
		 * Verifica se os campos estão vazios.
		 */
		if(this.getMatricula() == "") {
			throw new IllegalArgumentException("Informe a matricula do aluno.");
		}
		if(this.getPessoa().getName() == "") {
			throw new IllegalArgumentException("Informe o nome do aluno.");
		}
		if(this.getAnoDeEntrada() == "") {
			throw new IllegalArgumentException("Informe o ano de entrada do aluno.");
		}
		if(this.getMatricula() == null || this.getPessoa().getName() == null || this.getAnoDeEntrada() == null) {
			throw new IllegalArgumentException("Objeto aluno não persistido");
		}

	}
	
	
	////////////////Getters and Setters///////////////////////
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getAnoDeEntrada() {
		return anoDeEntrada;
	}

	public void setAnoDeEntrada(String anoDeEntrada) {
		this.anoDeEntrada = anoDeEntrada;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
	
}
