package repositories;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import entities.Aluno;

/**
 * 
 * @author artur
 * Classe responsável por manipular as tabelas do banco de dados.
 *
 */
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
	 * Retorna uma lista dos objetos da tabela Aluno
	 * a partir de uma query pré-estabelecida.
	 */
	@SuppressWarnings("unchecked")
	public List<Aluno> findAll() {
		manager = createEntityManager();
		manager.getTransaction().begin();
		Query consulta = manager.createQuery("select a.id, a.matricula, p.name, a.anoDeEntrada from entities.Aluno a INNER JOIN entities.Pessoa as p ON a.pessoa.id = p.id");
		List<Object> listaTeste = consulta.getResultList();
		List<Aluno> lista = new ArrayList<Aluno>();
		for(Object o: listaTeste) {
			Aluno aluno = new Aluno();
			Object[] obj = (Object[]) o;
			aluno.setId(Integer.parseInt(String.valueOf(obj[0])));
			aluno.setMatricula(String.valueOf(obj[1]));
			aluno.getPessoa().setName(String.valueOf(obj[2]));
			aluno.setAnoDeEntrada(String.valueOf(obj[3]));
			lista.add(aluno);
		}
		manager.getTransaction().commit();
		manager.close();
		return lista;
    }
	
	/**
	 * Retorna uma lista com o id do aluno com a matricula informada.
	 */
	@SuppressWarnings("unchecked")
	public int getAlunoIDByMatricula(String matricula) {
		int alunoIdDoBanco = -1;
		manager = createEntityManager();
		Query consulta = manager.createQuery("select a.id from entities.Aluno a INNER JOIN entities.Pessoa as p ON a.pessoa.id = p.id AND a.matricula = '" + matricula + "'");
		List<Integer> listaIDs = consulta.getResultList();
		if(!listaIDs.isEmpty()) {
			alunoIdDoBanco = listaIDs.get(0);
	
		}
		return alunoIdDoBanco;
	}
}
