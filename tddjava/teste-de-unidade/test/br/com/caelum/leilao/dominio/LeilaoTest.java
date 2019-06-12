package br.com.caelum.leilao.dominio;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LeilaoTest {
	
	@Test
	public void deveReceberUmLance() {
		Leilao leilao = new Leilao("Samsung Galaxy Note 9");
		assertEquals(0, leilao.getLances().size());
		
		leilao.propoe(new Lance(new Usuario("Carlos"), 1800.25));
		
		assertEquals(1, leilao.getLances().size());
		assertEquals(1800.25, leilao.getLances().get(0).getValor(), 0.00001);
	}
	
	@Test
	public void deveReceberVariosLances() {
		//Cria cenário
		Leilao leilao = new Leilao("Samsung Galaxy Note 9");
		
		//executa testes
		assertEquals(0, leilao.getLances().size());
		
		//executa operação
		leilao.propoe(new Lance(new Usuario("Carlos"), 1800.25));
		leilao.propoe(new Lance(new Usuario("Fernanda"), 1900));
		leilao.propoe(new Lance(new Usuario("Bolsonaro"), 2200));
		leilao.propoe(new Lance(new Usuario("Jhon Hus"), 1750.85));
		
		//executa testes
		assertEquals(4, leilao.getLances().size());
		assertEquals(1800.25, leilao.getLances().get(0).getValor(), 0.00001);
		assertEquals(1750.85, leilao.getLances().get(leilao.getLances().size() - 1).getValor(), 0.00001);
	}
	
	@Test
	public void naoDeveAceitarDoisLancesSeguidosDoMesmoUsuario() {
		
		Leilao leilao = new Leilao("Samsung Galaxy Note 9");
		assertEquals(0, leilao.getLances().size());
		
		//executa operação
		leilao.propoe(new Lance(new Usuario("Bolsonaro"), 1900));
		leilao.propoe(new Lance(new Usuario("Bolsonaro"), 2200));
		leilao.propoe(new Lance(new Usuario("Jhon Hus"), 1750.85));
		
		//executa testes
		assertEquals(2, leilao.getLances().size());
		assertEquals(1900, leilao.getLances().get(0).getValor(), 0.00001);
		assertEquals(1750.85, leilao.getLances().get(leilao.getLances().size() - 1).getValor(), 0.00001);
	}
	
	@Test
	public void naoDeveAceitarMaisDoQueCincoLancesPorUsuario() {
		//Cria cenário
		Leilao leilao = new Leilao("Samsung Galaxy Note 9");
		assertEquals(0, leilao.getLances().size());
		
		Usuario bolsonaro = new Usuario("Bolsonaro");
		Usuario hus = new Usuario("Jhon Hus");
		
		//executa operação
		leilao.propoe(new Lance(bolsonaro, 150.00));
		leilao.propoe(new Lance(hus, 548.65));
		leilao.propoe(new Lance(bolsonaro, 746.36));
		leilao.propoe(new Lance(hus, 142.98));
		leilao.propoe(new Lance(bolsonaro, 985.25));
		leilao.propoe(new Lance(hus, 1425.96));
		leilao.propoe(new Lance(bolsonaro, 879.65));
		leilao.propoe(new Lance(hus, 854.96));
		leilao.propoe(new Lance(bolsonaro, 47.96));
		leilao.propoe(new Lance(hus, 2365.85));
		
		
		leilao.propoe(new Lance(bolsonaro, 4758.96));
		
		//executa os teste
		assertEquals(10, leilao.getLances().size());
		assertEquals(2365.85, leilao.getLances().get(leilao.getLances().size() - 1).getValor(), 0.00001);
		
	}
	
	@Test
	public void deveDobrarOUltimoLanceDoUsuario() {
		//Cria cenário
		Leilao leilao = new Leilao("Samsung Galaxy Note 9");
		assertEquals(0, leilao.getLances().size());
		
		Usuario bolsonaro = new Usuario("Bolsonaro");
		Usuario hus = new Usuario("Jhon Hus");
		
		//executa operação
		leilao.propoe(new Lance(bolsonaro, 150.00));
		leilao.propoe(new Lance(hus, 548.65));
		leilao.propoe(new Lance(bolsonaro, 400.0));
		leilao.propoe(new Lance(hus, 485.96));
		leilao.dobraLance(bolsonaro);
		
		//executa os teste
		assertEquals(5, leilao.getLances().size());
		assertEquals(800.0, leilao.getLances().get(leilao.getLances().size() - 1).getValor(), 0.00001);
	}
	
	@Test
	public void naoDeveCriarLanceParaUsuarioQueNaoDeuLanceETentaDobrarLance() {
		//Cria cenário
		Leilao leilao = new Leilao("Samsung Galaxy Note 9");
		assertEquals(0, leilao.getLances().size());
		
		Usuario bolsonaro = new Usuario("Bolsonaro");
		Usuario hus = new Usuario("Jhon Hus");
		Usuario martin = new Usuario("Martinho Lutero");
		
		//executa operação
		leilao.propoe(new Lance(bolsonaro, 150.00));
		leilao.propoe(new Lance(hus, 548.65));
		leilao.propoe(new Lance(bolsonaro, 400.0));
		leilao.propoe(new Lance(hus, 485.96));
		leilao.dobraLance(martin);
		
		//executa testes
		assertEquals(4, leilao.getLances().size());
		assertEquals(485.96, leilao.getLances().get(leilao.getLances().size() -1 ).getValor(), 0.00001);
	}

}
