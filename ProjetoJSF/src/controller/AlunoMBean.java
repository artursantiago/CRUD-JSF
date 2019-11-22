package controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;

import entities.Aluno;
import services.AlunoService;

/**
 * @author artur
 *
 *Classe responsável por fazer o intermédio entre a view e a camada de negócios.
 */
@ManagedBean(name="alunoMBean")
@SessionScoped
public class AlunoMBean {
	
	//Lista de alunos retornados para a listagem
	private List<Aluno> listaAlunos = new ArrayList<Aluno>();

	//Variavel aluno usado para criacao e edicao.
	private Aluno aluno = new Aluno();
	
	//Objeto responsavel pela logica de negocio da aplicação.
	private AlunoService service = new AlunoService();
	
	//Variável dataTable que irá criar, dinamicamente, uma tabela na view.
	private HtmlDataTable dataTable;

	/**
	 * -----Construtor-----
	 * Chamado apenas quando a pagina é carregada pela primeira vez no browser, 
	 * devido ao SessionScoped
	 */
	public AlunoMBean() {
		carregaTabela();
	}
	
	/**
	 * Salva um aluno, caso não seja lançado alguma exceção,então, reinstancia o objeto aluno e retorna para a páginaRetorno;
	 */
	public String save() {
		try {
			service.salvarAluno(aluno);
			this.aluno = new Aluno();
			carregaTabela();
			return "index?faces-redirect=true";
		} catch(IllegalArgumentException exception) {
			exibeErro(exception);
			return null;
		}
	}
	
	/**
	 * Captura o aluno selecionado, caso não seja lançado alguma exceção,então, remove-o, reinstancia o objeto aluno e retorna para a páginaRetorno;
	 */
	public String remove() {
		try {
			aluno = selecionaAluno();
			service.removeAluno(aluno);
			aluno = new Aluno();
			carregaTabela();
			return "index?faces-redirect=true";	
		} catch(IllegalArgumentException exception) {
			exibeErro(exception);
			return null;
		}
	}
	
	/**
	 * Captura o aluno selecionado.
	 */
	public String preEdit() {
		aluno = selecionaAluno();
		return "formulario?faces-redirect=true";
	}
	
	/**
	 *  Carrega a Tabela e chama a página de formulário.
	 */
	public String chamaFormulario() {
		carregaTabela();
		if(aluno.getId() != 0) {
			aluno = new Aluno();
		}
		return "formulario?faces-redirect=true";
	}
	
	/**
	 * Carrega a Tabela, reinstancia o objeto aluno caso ele tenha algum valor e chama a página de listagem.
	 */
	public String chamaIndex() {
		carregaTabela();
		//System.out.println(aluno.getId());
		//if(aluno.getId() != 0) {
			aluno = new Aluno();
		//}
		return "index?faces-redirect=true";
	}
	
	/**
	 * Armazena toda a tabela aluno do banco na Lista.
	 * A List listaAlunos é valor da tag dataTable da view, 
	 * que por sua vez constrói a tabela na página.
	 */
	public void carregaTabela() {
		dataTable = null;
		listaAlunos = service.findAll();
	}
	
	/**
	 * Guarda os dados do aluno selecionado
	 */
	public Aluno selecionaAluno(){
		Aluno alunoSelecionado = (Aluno) dataTable.getRowData();
		return alunoSelecionado;
	}
	
	/**
	 * Método privado responsável pela exibição da mensagem de erro na view.
	 */
	private void exibeErro(Throwable exception) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção.", exception.getMessage()));
	}
	
	//////////////// Getters and Setters ///////////////////////
	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public List<Aluno> getListaAlunos() {
		return listaAlunos;
	}

	public void setListaAlunos(List<Aluno> listaAlunos) {
		this.listaAlunos = listaAlunos;
	}

	public HtmlDataTable getDataTable() {
		return dataTable;
	}

	public void setDataTable(HtmlDataTable dataTable) {
		this.dataTable = dataTable;
	}

	
	
}
