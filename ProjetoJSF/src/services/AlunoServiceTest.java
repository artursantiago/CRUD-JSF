package services;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import entities.Aluno;
import repositories.AlunoRepository;

@RunWith(MockitoJUnitRunner.class)
public class AlunoServiceTest {
	
	private Aluno aluno;
	
	@Mock
	private AlunoRepository repository;
	
	private AlunoService service;
	
	@Before	
	public void criaAluno() {
		this.aluno = new Aluno();
		this.service = new AlunoService(this.repository);
	}
	
	//@Rule 
	//public MockitoRule mockitoRule = MockitoJUnit.rule();

	@Rule
	public ExpectedException exception = ExpectedException.none();
	

	/**
	 * Campos do formulário escritos corretamente.
	 */
	@Test
	public void testMatriculaNaoNulaNaoVaziaMenorOuIgualQueQuinzeCaracteres() {
		aluno.setMatricula("2021010138");
		aluno.setAnoDeEntrada("2021");
		aluno.getPessoa().setName("Lucas");
		
		service.salvarAluno(aluno);
	}
	
	
	////////Testes Matrículas/////////
	
	/**
	 * Testa se um matricula null está sendo submetida.
	 */
	@Test
	public void testMatriculaNula() {
		aluno.setMatricula(null);
		aluno.setAnoDeEntrada("2019");
		aluno.getPessoa().setName("Artur");
		
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Objeto aluno não persistido");
		
		service.salvarAluno(aluno);
	}
	
	/**
	 * Testa se uma matrícula vazia está sendo submetida.
	 */
	@Test
	public void testMatriculaVazia() {
		aluno.setMatricula("");
		aluno.setAnoDeEntrada("2019");
		aluno.getPessoa().setName("Artur");
		
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Informe a matricula do aluno.");
		
		service.salvarAluno(aluno);
	}
	
	/**
	 * Testa se a matricula já existe no banco de dados quando um aluno está sendo editado.
	 */
	@Test
	public void testAtualizarAlunoMatriculaJaExisteNoBanco() {
		aluno.setId(2);
		aluno.setMatricula("20191012");
		aluno.setAnoDeEntrada("2021");
		aluno.getPessoa().setName("Lucas");
		
		List<Integer> listaAlunosIDs = new ArrayList<Integer>();
		listaAlunosIDs.add(3);
		
		when(repository.getAlunoIDByMatricula(aluno.getMatricula())).thenReturn(listaAlunosIDs);
		
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Já existe um aluno com essa matrícula.");
		
		service.salvarAluno(aluno);
	}
	
	
	/**
	 * Testa se a matricula já existe no banco de dados quando um novo aluno está sendo persistido.
	 */
	@Test
	public void testSalvarNovoAlunoMatriculaJaExisteNoBanco() {
		aluno.setMatricula("20191012");
		aluno.setAnoDeEntrada("2021");
		aluno.getPessoa().setName("Lucas");
		
		List<Integer> listaAlunosIDs = new ArrayList<Integer>();
		listaAlunosIDs.add(3);
		
		when(repository.getAlunoIDByMatricula(aluno.getMatricula())).thenReturn(listaAlunosIDs);
		
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Já existe um aluno com essa matrícula.");
		
		service.salvarAluno(aluno);
	}
	
	
	/**
	 * Testa se a matricula possui mais do que 15 caracteres.
	 */
	@Test
	public void testMatriculaMaiorQueQuinzeCaracteres() {
		aluno.setMatricula("20200102030405012");
		aluno.setAnoDeEntrada("2020");
		aluno.getPessoa().setName("Mateus");
		
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("A matrícula do aluno deve ter no máximo 15 caracteres.");
		
		service.salvarAluno(aluno);
	}
	
	///////Testes AnoDeEntrada///////
	
	/**
	 * Testa se o anoDeEntrada é do tipo null.
	 */
	@Test
	public void testAnoDeEntradaNulo() {
		aluno.setMatricula("2021010138");
		aluno.setAnoDeEntrada(null);
		aluno.getPessoa().setName("Artur");
		
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Objeto aluno não persistido");
		
		service.salvarAluno(aluno);
	}
	
	/**
	 * Testa se o anoDeEntrada está vázio.
	 */
	@Test
	public void testAnoDeEntradaVazio() {
		aluno.setMatricula("2021010138");
		aluno.setAnoDeEntrada("");
		aluno.getPessoa().setName("Mateus");
		
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Informe o ano de entrada do aluno.");
		
		service.salvarAluno(aluno);
	}
	
	/**
	 * Testa se o anoDeEntrada não possui 4 caracteres.
	 */
	@Test
	public void testAnoDeEntradaDiferenteDeQuatroCaracteres() {
		aluno.setMatricula("2021010138");
		aluno.setAnoDeEntrada("999");
		aluno.getPessoa().setName("Lucas");
		
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("O ano de entrada deve ter 4 caracteres.");
		
		service.salvarAluno(aluno);
	}
	
	
	/**
	 * Testa se o anoDeEntrada é um ano com 4 caracteres menor que o ano atual.
	 */
	@Test
	public void testAnoDeEntradaComQuatroCaracteresMenorQueAnoAtual() {
		aluno.setMatricula("2021010138");
		aluno.setAnoDeEntrada("2018");
		aluno.getPessoa().setName("Jeremias");
		
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Informe um ano maior ou igual ao ano atual.");	
		
		service.salvarAluno(aluno);		
	}
	
	////////////Tests Nome/////////////
	
	/**
	 * Testa se o nome é do tipo null.
	 */
	@Test
	public void testNomeNulo() {
		aluno.setMatricula("2021010138");
		aluno.setAnoDeEntrada("2022");
		aluno.getPessoa().setName(null);
		
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Objeto aluno não persistido");
		
		service.salvarAluno(aluno);
	}
	
	/**
	 * Testa se o nome está vazio.
	 */
	@Test
	public void testNomeVazio() {
		aluno.setMatricula("2021010138");
		aluno.setAnoDeEntrada("2021");
		aluno.getPessoa().setName("");
		
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Informe o nome do aluno.");
		
		service.salvarAluno(aluno);
	}
	
	/**
	 * Testa se o nome possui mais do que 100 caracteres.
	 */
	@Test
	public void testNomeMaiorQueCem() {
		aluno.setMatricula("2021010138");
		aluno.setAnoDeEntrada("2020");
		aluno.getPessoa().setName("Matheus Santiago José da Silva Cavalcanti de Melo Paiva de Souza Santanna de Paula Ferreira Quintiliano Junior");
		
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("O nome do Aluno deve ter no máximo 100 caracteres.");
		
		service.salvarAluno(aluno);
	}
	
} 