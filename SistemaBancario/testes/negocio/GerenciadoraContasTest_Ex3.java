package negocio;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GerenciadoraContasTest_Ex3 {

	private GerenciadoraContas gerContas;
	
	@Test
	public void testTransfereValor() {

		/* ========== Montagem do cenário ========== */
		
		// criando alguns clientes
		double saldoInicialConta01 = 200;
		double saldoInicialConta02 = 0;
		ContaCorrente conta01 = new ContaCorrente(1, saldoInicialConta01, true);
		ContaCorrente conta02 = new ContaCorrente(2, saldoInicialConta02, true);
		
		// inserindo os clientes criados na lista de clientes do banco
		List<ContaCorrente> contasDoBanco = new ArrayList<>();
		contasDoBanco.add(conta01);
		contasDoBanco.add(conta02);
		
		gerContas = new GerenciadoraContas(contasDoBanco);

		/* ========== Execução ========== */
		gerContas.transfereValor(1, 100, 2);
		
		/* ========== Verificações ========== */
		assertThat(conta01.getSaldo(), is(saldoInicialConta01 - 100));
		assertThat(conta02.getSaldo(), is(saldoInicialConta02 + 100));
	}

}
