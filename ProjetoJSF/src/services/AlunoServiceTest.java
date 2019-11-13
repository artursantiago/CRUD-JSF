package services;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;

import entities.Aluno;

public class AlunoServiceTest {
	
	// private Aluno aluno;
	
	@InjectMocks
	private AlunoService service;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	/**
	@Before	
	private void criaAluno() {
		this.aluno = new Aluno();
	}
 	*/
 	
	
	/**
	 * Campos do formulário escritos corretamente.
	 */
	@Test
	public void testMatriculaNaoNulaNaoVaziaMenorOuIgualQueQuinzeCaracteres() {
		Aluno aluno = new Aluno("2021010138", "2021", "Lucas");
		service.salvarAluno(aluno);
	}
	
	
	////////Testes Matrículas/////////
	
	/**
	 * Testa se um matricula null está sendo submetida.
	 */
	@Test
	@Ignore
	public void testMatriculaNula() {
		Aluno aluno = new Aluno(null, "2019", "Artur");
		//Mockito.when(service.salvarAluno(aluno)).thenThrow(new IllegalArgumentException("Objeto aluno não persistido."));
		
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Objeto aluno não persistido");
	}
	
	/**
	 * Testa se uma matrícula vazia está sendo submetida.
	 */
	@Test
	public void testMatriculaVazia() {
		Aluno aluno = new Aluno("", "2019", "Artur");
		service.salvarAluno(aluno);
		
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Informe a matricula do aluno.");
	}
	
	/**
	 * Testa se a matricula já existe no banco de dados.
	 */
	@Test
	@Ignore
	public void testMatriculaJaExisteNoBanco() {
		Aluno aluno = new Aluno("20191012", "2019", "Artur"); //Matricula Já existe no banco de dados
		service.salvarAluno(aluno);
		
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("");
	}
	
	/**
	 * Testa se a matricula possui mais do que 15 caracteres.
	 */
	@Test
	public void testMatriculaMaiorQueQuinzeCaracteres() {
		Aluno aluno = new Aluno("202001020304050", "2020", "Mateus"); //Matricula com 16 caracteres
		service.salvarAluno(aluno);
		
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("");
	}
	
	///////Testes AnoDeEntrada///////
	
	/**
	 * Testa se o anoDeEntrada é do tipo null.
	 */
	@Test
	@Ignore
	public void testAnoDeEntradaNulo() {
		Aluno aluno = new Aluno("2021010138", null, "Artur");
		
		service.salvarAluno(aluno);
		
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("");
	}
	
	/**
	 * Testa se o anoDeEntrada está vázio.
	 */
	@Test
	@Ignore
	public void testAnoDeEntradaVazio() {
		Aluno aluno = new Aluno("2021010138", "", "Mateus");
		service.salvarAluno(aluno);
		
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("");
	}
	
	/**
	 * Testa se o anoDeEntrada possui menos do que 4 caracteres.
	 */
	@Test
	@Ignore
	public void testAnoDeEntradaMenorQueQuatroCaracteres() {
		Aluno aluno = new Aluno("2021010138", "999", "Lucas");
		service.salvarAluno(aluno);
		
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("");
	}
	
	/**
	 * Testa se o anoDeEntrada possui mais do que 4 caracteres.
	 */
	@Test
	@Ignore
	public void testAnoDeEntradaMaiorQueQuatroCaracteres() {
		Aluno aluno = new Aluno("2021010138", "10000", "Joao");
		service.salvarAluno(aluno);
		
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("");	
	}
	
	/**
	 * Testa se o anoDeEntrada é um ano com 4 caracteres menor que o ano atual.
	 */
	@Test
	@Ignore
	public void testAnoDeEntradaComQuatroCaracteresMenorQueAnoAtual() {
		Aluno aluno = new Aluno("2021010138", "2018", "Jeremias");
		service.salvarAluno(aluno);
		
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("");		
	}
	
	////////////Tests Nome/////////////
	
	/**
	 * Testa se o nome é do tipo null.
	 */
	@Test
	@Ignore
	public void testNomeNulo() {
		Aluno aluno = new Aluno("2022010138", "2022", null);
		service.salvarAluno(aluno);
		
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("");
	}
	
	/**
	 * Testa se o nome está vazio.
	 */
	@Test
	@Ignore
	public void testNomeVazio() {
		Aluno aluno = new Aluno("2021010138", "2021", "");
		service.salvarAluno(aluno);
		
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("");
	}
	
	/**
	 * Testa se o nome possui mais do que 100 caracteres.
	 */
	@Test(expected=IllegalArgumentException.class)
	@Ignore
	public void testNomeMaiorQueCem() {
		Aluno aluno = new Aluno("2020010138", "2020", "Matheus Santiago José da Silva Cavalcanti de Melo Paiva de Souza Santanna de Paula Ferreira Quintiliano Junior");
		service.salvarAluno(aluno);
		
		//exception.expect(IllegalArgumentException.class);
		//exception.expectMessage("");
	}
	
}
