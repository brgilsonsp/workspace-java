package br.com.caelum.leilao.dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Leilao {

	private String descricao;
	private List<Lance> lances;
	
	public Leilao(String descricao) {
		this.descricao = descricao;
		this.lances = new ArrayList<Lance>();
	}
	
	public void dobraLance(Usuario usuario) {
		double valorUltimoLance = valorUltimoLanceDoUsuario(usuario);
		if(valorUltimoLance > 0.0) {
			Lance lance = new Lance(usuario, valorUltimoLance * 2);
			this.propoe(lance);
		}
	}
	
	private double valorUltimoLanceDoUsuario(Usuario usuario) {
		for(int i = lances.size() - 1; i >= 0; i--) {
			Lance l= lances.get(i);
			if(l.getUsuario().equals(usuario)) {
				return l.getValor();
			}
		}
		return 0.0;
	}
	
	public void propoe(Lance lance) {
		if(lances.isEmpty() || usuarioPodeDarLance(lance.getUsuario()) )
			lances.add(lance);
	}
	
	private boolean usuarioPodeDarLance(Usuario usuario) {
		int totalLancesDoUsuario = quantidadeLancesDoUsuario(usuario);
		boolean mesmoUsuarioUltimoLance = this.mesmoUsuarioDoUltimoLance(usuario);
		return !mesmoUsuarioUltimoLance && totalLancesDoUsuario < 5;
	}
	
	private int quantidadeLancesDoUsuario(Usuario usuario) {
		int totalLancesDoUsuario = 0;
		for(Lance l : lances) {
			if(usuario.equals(l.getUsuario()))
				totalLancesDoUsuario++;
		}
		return totalLancesDoUsuario;
	}
	
	private boolean mesmoUsuarioDoUltimoLance(Usuario usuario) {
		Usuario usuarioUltimoLance = lances.get(lances.size() - 1).getUsuario();
		return usuario.equals(usuarioUltimoLance);
	}

	public String getDescricao() {
		return descricao;
	}

	public List<Lance> getLances() {
		return Collections.unmodifiableList(lances);
	}

	
	
}
