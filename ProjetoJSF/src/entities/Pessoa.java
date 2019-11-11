package entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "pessoa", schema = "comum")
public class Pessoa implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Coluna Id da tabela pessoa no banco de dados.
	 * Definida para ser gerada automaticamente a partir de uma sequencia pessoa_seq.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pessoa_seq")
	@SequenceGenerator(name = "pessoa_seq", sequenceName = "pessoa_seq", schema = "comum", allocationSize = 1)
	private int id;
	
	/**
	 * Coluna name da tabela pessoa no banco de dados.
	 */
	@Column(name = "name")
	private String name;
	
	/**
	 * Relação Um-para-Muitos com a tabela aluno, ou seja, uma pessoa pode ter vários vínculos como aluno.
	 */
	@OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<Aluno> aluno;
	
	////////////////Getters and Setters///////////////////////
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Aluno> getAluno() {
		return aluno;
	}
	public void setAluno(List<Aluno> aluno) {
		this.aluno = aluno;
	}
	
	
}
