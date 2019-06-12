package br.com.caelum.leilao.dominio.servico;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PalindromoTest {

	@Test
	public void deveRetornarVerdadeiroParaUmPalindromo() {
		//Criação do cenário
		String frase = "Anotaram a data da maratona";
		
		//Execução da unidade
		boolean ehPalindromo = new Palindromo().ehPalindromo(frase);
		
		//Execução do teste
		assertTrue(ehPalindromo);
	}
	
	@Test
	public void retornaFalsoParaUmaFraseNaoPalindromo() {
		//Criação do cenário
		String frase = "Não é um palindromo";
		
		//Execução da unidade
		boolean ehPalindromo = new Palindromo().ehPalindromo(frase);
		
		//Execuçao do teste
		assertFalse(ehPalindromo);
	}
}
