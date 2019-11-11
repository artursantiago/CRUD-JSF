package repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import entities.Aluno;

public class AlunoRepository extends AbstractRepository{
	
	EntityManager manager;

	public AlunoRepository() {
		super();
		this.manager = null;
	}
	
	/**
	 * Persiste o objeto aluno no Banco de Dados.
	 */
	public void save(Aluno aluno) {
		manager = createEntityManager();
		manager.getTransaction().begin();
		manager.persist(aluno);
		manager.persist(aluno.getPessoa());
		manager.getTransaction().commit();
		manager.close();
	}
	
	/**
	 * Retorna uma lista de todos os objetos da tabela Aluno
	 * a partir de uma query pré-estabelecida.
	 */
	@SuppressWarnings("unchecked")
	public List<Aluno> findAll() {
		manager = createEntityManager();
		manager.getTransaction().begin();
		Query consulta = manager.createQuery("from entities.Aluno");
		List<Aluno> lista= consulta.getResultList();
		manager.getTransaction().commit();
		manager.close();
		return lista;
    }
	
	/**
	 * Remove o objeto aluno do banco de dados.
	 */
	public void remove(Aluno alunoSelecionado) { 
		manager = createEntityManager();
		manager.getTransaction().begin();
		Aluno aluno = manager.merge(alunoSelecionado);
		manager.remove(aluno);
		manager.getTransaction().commit();
		manager.close();
	}
	
	/**
	 * Atualiza os dados de um aluno que já existe no banco de dados.
	 */
	public void update(Aluno alunoSelecionado) { 
		manager = createEntityManager();
		manager.getTransaction().begin();
		manager.merge(alunoSelecionado);
		manager.getTransaction().commit();
		manager.close();
	}	
	
	/**
	 * CRIAR UM MÉTODO PARA PROCURAR UM ALUNO POR ID
	 */
}
