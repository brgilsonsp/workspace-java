package br.com.caelum.leilao.dominio.servico;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;

public class Leiloeiro {

	private double maiorValorDeTodos = Double.NEGATIVE_INFINITY;
	private double menorValorDeTodos = Double.POSITIVE_INFINITY;
	private double somaDosLances = 0; 
	private double valorMedioLances;
	private List<Lance> tresMaiores;

	public void avalia(Leilao leilao) {
		int quantidadeLances = leilao.getLances().size();
		
		leilao.getLances().forEach(lance -> {
			if(lance.getValor() > maiorValorDeTodos) maiorValorDeTodos = lance.getValor();
			if(lance.getValor() < menorValorDeTodos) menorValorDeTodos = lance.getValor();
			somaDosLances += lance.getValor();
		});
		valorMedioLances = somaDosLances / quantidadeLances;
		
		tresMaiores = obtemOsTresMaiores(leilao.getLances());
		
	}
	
	private List<Lance> obtemOsTresMaiores(List<Lance> lances){
		List<Lance> maiores = new ArrayList<>(lances);
		Collections.sort(maiores, new Comparator<Lance>() {
			public int compare(Lance l1, Lance l2) {
				if(l1.getValor() < l2.getValor()) return 1;
				if(l1.getValor() > l2.getValor()) return -1;
				return 0;
			}
		});
		
		return maiores.subList(0, maiores.size() > 3 ? 3 : maiores.size());
	}
	
	public List<Lance> getTresMaiores() {
		return tresMaiores;
	}
	
	public double getMaiorValor() {
		return maiorValorDeTodos;
	}
	
	public double getMenorValor() {
		return menorValorDeTodos;
	}
	
	public double getValorMedioLances() {
		return valorMedioLances;
	}
}
