package services;

import java.util.List;

import entities.Aluno;
import repositories.AlunoRepository;

/**
 * 
 * @author artur
 *Classe responsável por realizar a validação das regras de negócio
 *da aplicação.
 */
public class AlunoService {
	
	
	private AlunoRepository repository;
	
	public AlunoService() {
		this.repository = new AlunoRepository();
	}
	
	public AlunoService(AlunoRepository repository) {
		this.repository = repository;
	}
	
	/**
	 * Metodo que salva ou edita um aluno.
	 */
	public void salvarAluno(Aluno aluno) {
		if(aluno.getId() > 0) {
			editaAluno(aluno);
		}
		else {
			salvaAluno(aluno);
		}
	}
	
	/**
	 * Método que remove um aluno
	 */
	public void removeAluno(Aluno aluno) {
		
		if(aluno == null || aluno.getId() == 0) {
			throw new IllegalArgumentException("Objeto aluno não persistido.");
		}
		aluno.validar();
		//AlunoRepository repository = new AlunoRepository();
		repository.remove(aluno);
	}
	
	/**
	 * Metodo que busca todos os dados da tabela
	 */

	public List<Aluno> findAll() {
		//AlunoRepository repository = new AlunoRepository();
		return repository.findAll();
	}
	
	
	public AlunoRepository getRepository() {
		return repository;
	}

	public void setRepository(AlunoRepository repository) {
		this.repository = repository;
	}
	
	//////////////// metodos privados ///////////////////////


	/**
	 * Método privado responsável por salvar um novo aluno após ser validado.
	 */
	private void salvaAluno(Aluno aluno) {
		aluno.validar();
		validaAluno(aluno);
		//AlunoRepository repository = new AlunoRepository();
		repository.save(aluno);
	}
	
	/**
	 * Método privado responsável por atualizar um aluno após ser validado.
	 */
	private void editaAluno(Aluno aluno) {
		aluno.validar();
		validaAluno(aluno);
		//AlunoRepository repository = new AlunoRepository();
		repository.update(aluno);	
	}


	/**
	 * Método privado para validação dos dados do aluno.
	 */
	private void validaAluno(Aluno aluno) {
		/**
		 * Verifica se já exixte um aluno do banco com a matrícula do novo aluno se estiver salvando um novo aluno.
		 */
		int alunoIdDoBanco = repository.getAlunoIDByMatricula(aluno.getMatricula());
		
		if((aluno.getId() != alunoIdDoBanco)) {
			throw new IllegalArgumentException("Já existe um aluno com essa matrícula.");
		}
	}
}