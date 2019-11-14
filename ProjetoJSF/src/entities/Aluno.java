package entities;

import java.io.Serializable;
import java.util.Calendar;

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

/**
 * @author artur
 *
 *Classe responsável por mapear a tabela aluno do banco de dados.
 *
 */
@Entity
@Table(name = "aluno", schema = "graduacao")
public class Aluno implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	 * O Id é definido para ser gerado automaticamente a partir de uma sequencia aluno_seq.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "aluno_seq")
	@SequenceGenerator(name = "aluno_seq", sequenceName = "aluno_seq", schema = "graduacao", allocationSize = 1)
	private int id;
	
	/**
	 * Coluna matricula do banco. Deve ter no máximo 15 caracteres e não pode
	 * existir dois alunos com a mesma matrícula.
	 */
	@Column(name = "matricula")
	private String matricula;
	
	/**
	 * Coluna ano_de_entrada do banco. Deve ter no máximo 4 caracteres e 
	 * não deve ser inserido um ano menor que o ano atual.
	 */
	@Column(name = "ano_de_entrada")
	private String anoDeEntrada;
	
	/**
	 * Coluna id_pessoa do banco de dados.
	 * É uma chave estrangeira para a coluna id da tabela pessoa.
	 * Possui uma relação Muitos-para-um, ou seja, podem existir varios vínculos de aluno para uma única pessoa.
	 */
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_pessoa", referencedColumnName = "id", nullable = false)
	private Pessoa pessoa = new Pessoa();
	
	
	/**
	 * Valida as regras que se aplicão aos atributos desse aluno.
	 */
	public void validar() {
		/**
		 * Verifica se os campos estão vazios.
		 */
		if(this.getMatricula() == "") {
			throw new IllegalArgumentException("Informe a matricula do aluno.");
		}
		if(this.getAnoDeEntrada() == "") {
			throw new IllegalArgumentException("Informe o ano de entrada do aluno.");
		}
		if(this.getMatricula() == null || this.getAnoDeEntrada() == null) {
			throw new IllegalArgumentException("Objeto aluno não persistido");
		}

		/**
		 * Verifica se o ano tem 4 caracteres.
		 */
		if(this.getAnoDeEntrada().length() != 4) {
			throw new IllegalArgumentException("O ano de entrada deve ter 4 caracteres.");
		}
		
		/**
		 * Verifica se o ano é anterior ao ano atual.
		 */
		Calendar hoje = Calendar.getInstance();
		if(Integer.parseInt(this.getAnoDeEntrada()) < hoje.get(Calendar.YEAR)) {
			throw new IllegalArgumentException("Informe um ano maior ou igual ao ano atual.");
		}
		
		/**
		 * Verifica se a matricula tem mais do que 15 caracteres.
		 */
		if(this.getMatricula().length() > 15) {
			throw new IllegalArgumentException("A matrícula do aluno deve ter no máximo 15 caracteres.");
		}
		
		/**
		 * Valida os dados da classe Pessoa desse aluno.
		 */
		this.getPessoa().validar();
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
