package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import entities.Aluno;
import repositories.AlunoRepository;

/**
 * 
 * @author sinfo-anexo
 *
 */
public class AlunoService {
	
	/**
	 * Metodo que salva ou edita um aluno.
	 */
	public String salvarAluno(Aluno aluno) {
		if(aluno.getId() > 0) {
			return editaAluno(aluno);
		}
		else {
			return salvaAluno(aluno);
		}
	}
	
	/**
	 * Método que remove um aluno
	 */
	public String removeAluno(Aluno aluno) {
		try {
			/**
			 * Verifica se o objeto aluno já foi persistido ou não.
			 */
			if(aluno == null || aluno.getId() == 0) {
				throw new IllegalArgumentException("Objeto aluno não persistido.");
			}
			
			aluno.validar();
			AlunoRepository repository = new AlunoRepository();
			repository.remove(aluno);
			return "index?faces-redirect=true";
		}
		catch(IllegalArgumentException e) {
			return exibeErro(e);
		}
	}
	
	/**
	 * Metodo que busca todos os dados da tabela
	 */
	public List<Aluno> findAll() {
		AlunoRepository repository = new AlunoRepository();
		return repository.findAll();
	}
	
	
	
	//////////////// metodos privados ///////////////////////
	
	/**
	 * Método privado responsável por salvar um novo aluno após ser validado.
	 */
	private String salvaAluno(Aluno aluno) {
		
		try {
			aluno.validar();
			validaAlunoRegrasNegocios(aluno);
			AlunoRepository repository = new AlunoRepository();
			repository.save(aluno);
			return "index?faces-redirect=true";
		}
		catch(IllegalArgumentException e) {
			return exibeErro(e);
		}		
	}
	
	/**
	 * Método privado responsável por atualizar um aluno após ser validado.
	 */
	private String editaAluno(Aluno aluno) {
		try {
			aluno.validar();
			validaAlunoRegrasNegocios(aluno);
			AlunoRepository repository = new AlunoRepository();
			repository.update(aluno);	
			return "index?faces-redirect=true";
		}
		catch(IllegalArgumentException e) {
			return exibeErro(e);
		}
	}

	/**
	 * Método privado responsável pela exibição da mensagem de erro na view.
	 */
	private String exibeErro(Throwable e) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção.", e.getMessage()));
        return "formulario";
	}
	
	/**
	 * Método privado para validação dos dados do aluno.
	 */
	private void validaAlunoRegrasNegocios(Aluno aluno) {

		/**
		 * Verifica se o tamanho do ano informado é igual a 4 e se não é um ano maior que o atual.
		 */
		Calendar hoje = Calendar.getInstance();
		if(Integer.parseInt(aluno.getAnoDeEntrada()) > hoje.get(Calendar.YEAR)) {
			throw new IllegalArgumentException("Informe um ano menor ou igual ao ano atual.");
		}
		
		/**
		 * Verifica se já exixte um aluno do banco com a matrícula do novo aluno se estiver salvando um novo aluno.
		 */
		List<Aluno> listaAlunos = new ArrayList<Aluno>();
		AlunoService service = new AlunoService();
		listaAlunos = service.findAll();
		for(Aluno alunoDaLista: listaAlunos) {
			if( (Long.parseLong(aluno.getMatricula()) == Long.parseLong(alunoDaLista.getMatricula()) && (aluno.getId() != alunoDaLista.getId())) ) {
				throw new IllegalArgumentException("Já existe um aluno com essa matrícula.");
			}
		}
		
		/**
		 * Verifica o tamanho do nome do aluno.
		 */
		if(aluno.getPessoa().getName().length() > 100) {
			throw new IllegalArgumentException("O nome do Aluno deve ter no máximo 100 caracteres.");
		}
		
		/**
		 * Verifica se o ano tem 4 caracteres.
		 */
		if(aluno.getAnoDeEntrada().length() != 4) {
			throw new IllegalArgumentException("O ano de entrada deve ter 4 caracteres.");
		}
		
		/**
		 * 
		 */
		if(aluno.getMatricula().length() > 15) {
			throw new IllegalArgumentException("A matrícula do aluno deve ter no máximo 15 caracteres.");
		}
	}
}
