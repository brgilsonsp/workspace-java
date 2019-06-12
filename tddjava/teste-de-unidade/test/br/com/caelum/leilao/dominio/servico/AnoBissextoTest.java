package br.com.caelum.leilao.dominio.servico;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AnoBissextoTest {
	
	@Test
	public void verificaSeOAnoEhBissexto() {
		//Criar cenário
		int valorAnoBissexto = 2016;
		int valorAnoNaoBissexto = 2018;
		AnoBissexto anoBissexto = new AnoBissexto();
		
		//executa operação
		boolean ehAnoBissexto = anoBissexto.ehAnoBissexto(valorAnoBissexto);
		boolean naoEhAnoBissexto = anoBissexto.ehAnoBissexto(valorAnoNaoBissexto);
		
		//executa testes
		assertTrue(ehAnoBissexto);
		assertFalse(naoEhAnoBissexto);
		
	}

}
