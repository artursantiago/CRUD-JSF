package controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlDataTable;

import entities.Aluno;
import services.AlunoService;

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
		String paginaRetorno = service.salvarAluno(aluno);
		if(paginaRetorno != "formulario") {
			this.aluno = new Aluno();
			carregaTabela();
		}
		return paginaRetorno;
	}
	
	/**
	 * Captura o aluno selecionado, caso não seja lançado alguma exceção,então, remove-o, reinstancia o objeto aluno e retorna para a páginaRetorno;
	 */
	public String remove() {
		aluno = selecionaAluno();
		String paginaRetorno = service.removeAluno(aluno);
		if(paginaRetorno != "formulario") {
			aluno = new Aluno();
			carregaTabela();
		}
		return "index?faces-redirect=true";	
	}
	
	/**
	 * Captura o aluno selecionado.
	 */
	public String preEdit() {
		aluno = selecionaAluno();
		// aluno = repository.findById(aluno.getId());
		return "formulario?faces-redirect=true";
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
	 *  Carrega a Tabela e chama a página de formulário.
	 */
	public String chamaFormulario() {
		carregaTabela();
		return "formulario?faces-redirect=true";
	}
	
	/**
	 * Carrega a Tabela, reinstancia o objeto aluno caso ele tenha algum valor e chama a página de listagem.
	 */
	public String chamaIndex() {
		carregaTabela();
		if(aluno.getId() != 0) {
			aluno = new Aluno();
		}
		return "index?faces-redirect=true";
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
