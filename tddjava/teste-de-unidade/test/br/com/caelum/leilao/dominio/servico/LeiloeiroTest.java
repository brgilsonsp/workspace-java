package br.com.caelum.leilao.dominio.servico;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.Test;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;

public class LeiloeiroTest {
	
	@Test
	public void deveRetornarLeilaoEmOrdemCrescente() {
		//Criar Cenário
		Usuario joao = new Usuario("João");
		Usuario maria = new Usuario("Maria");
		Usuario abraao = new Usuario("Abraão");
		
		Leilao leilao = new Leilao("Viagem espacial");
		
		double valorMaria = 123.45; 
		double valorJoao = 543.21;
		double valorAbraao = 324.56;
		double media = (valorMaria + valorJoao + valorAbraao) / 3;
		
		Lance lance1 = new Lance(maria, valorMaria);
		Lance lance2 = new Lance(joao, valorJoao);
		Lance lance3 = new Lance(abraao, valorAbraao);
		
		leilao.propoe(lance1);
		leilao.propoe(lance2);
		leilao.propoe(lance3);
		
		//executa a ação
		Leiloeiro leiloeiro = new Leiloeiro();
		leiloeiro.avalia(leilao);
		
		//executa teste
		assertEquals(valorMaria, leiloeiro.getMenorValor(), 0.0001);
		assertEquals(valorJoao, leiloeiro.getMaiorValor(), 0.0001);
		assertEquals(media, leiloeiro.getValorMedioLances(), 0.0001);
	}
	
	@Test
	public void deveEndenterLeilaoComApenasUmLance() {
		//Criar Cenário
		Usuario joao = new Usuario("João");
		double valorJoao = 200.0;
		
		Lance lance = new Lance(joao, valorJoao);
		Leilao leilao = new Leilao("Viagem espacial");
		leilao.propoe(lance);
		
		//executa ação
		Leiloeiro leiloeiro = new Leiloeiro();
		leiloeiro.avalia(leilao);
		
		//executa teste
		assertEquals(valorJoao, leiloeiro.getMaiorValor(), 0.00001);
		assertEquals(valorJoao, leiloeiro.getMenorValor(), 0.00001);
	}
	
	@Test
	public void devePegarOsTresMaioresLances() {
		//Criar Cenário
		Usuario joao = new Usuario("João");
		Usuario maria = new Usuario("Maria");
		Usuario abraao = new Usuario("Abraão");
		
		Leilao leilao = new Leilao("Viagem espacial");
		
		double valorMaria = 999.99;
		double valorMaria1 = 54.65;
		double valorMaria2 = 12.65;
		double valorJoao = 999.98;
		double valorJoao2 = 99.8;
		double valorJoao1 = 99.8;
		double valorAbraao = 999.97;
		double valorAbraao1 = 99.27;
		double valorAbraao2 = 99.37;
		
		Lance lance1 = new Lance(maria, valorMaria);
		Lance lance2 = new Lance(maria, valorMaria1);
		Lance lance3 = new Lance(maria, valorMaria2);
		Lance lance4 = new Lance(joao, valorJoao);
		Lance lance5 = new Lance(joao, valorJoao1);
		Lance lance6 = new Lance(joao, valorJoao2);
		Lance lance7 = new Lance(abraao, valorAbraao);
		Lance lance8 = new Lance(abraao, valorAbraao1);
		Lance lance9 = new Lance(abraao, valorAbraao2);
		
		leilao.propoe(lance1);
		leilao.propoe(lance2);
		leilao.propoe(lance3);
		leilao.propoe(lance4);
		leilao.propoe(lance5);
		leilao.propoe(lance6);
		leilao.propoe(lance7);
		leilao.propoe(lance8);
		leilao.propoe(lance9);
		
		//executa ação
		Leiloeiro leiloeiro = new Leiloeiro();
		leiloeiro.avalia(leilao);
		List<Lance> tresMaiores = leiloeiro.getTresMaiores();
		
		//executa os testes
		assertEquals(3, tresMaiores.size());
		assertEquals(valorMaria, tresMaiores.get(0).getValor());
		assertEquals(valorJoao, tresMaiores.get(1).getValor());
		assertEquals(valorAbraao, tresMaiores.get(2).getValor());
		
	}
	
	@Test
	public void devePegarOsUnidosDoisLancesEfetuados() {
		
		//Criar Cenário
		Usuario joao = new Usuario("João");
		Usuario maria = new Usuario("Maria");
		
		Leilao leilao = new Leilao("Viagem espacial");
		
		double valorMaria = 999.99;
		double valorJoao = 54.65;
		
		Lance lance1 = new Lance(maria, valorMaria);
		Lance lance2 = new Lance(joao, valorJoao);
		
		leilao.propoe(lance1);
		leilao.propoe(lance2);
		
		//executa ação
		Leiloeiro leiloeiro = new Leiloeiro();
		leiloeiro.avalia(leilao);
		List<Lance> doisLances = leiloeiro.getTresMaiores();
		
		assertEquals(2, doisLances.size());
		assertEquals(valorMaria, doisLances.get(0).getValor(), 0.00001);
		assertEquals(valorJoao, doisLances.get(1).getValor(), 0.00001);
		
	}
	
	@Test
	public void devePegarListaVaziaQuandoNaoTemLance() {
		//Criar cenário
		Leilao leilao = new Leilao("Viagem espacial");
		
		//executa ação
		Leiloeiro leiloeiro = new Leiloeiro();
		leiloeiro.avalia(leilao);
		List<Lance> listaVazia = leiloeiro.getTresMaiores();
		
		assertEquals(0, listaVazia.size());
	}

}
