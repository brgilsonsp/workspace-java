package gp.betta.telematica.oi.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Pesquisa implements Serializable{

	private static final long serialVersionUID = 8778040766782840301L;
	
	@JsonProperty(value = "MATRICULA_ATD")
	private String matricula_atd;
	
	@JsonProperty(value = "SKILL")
	private String skill;
	
	@JsonProperty(value = "DAC")
	private String dac;
	
	@JsonProperty(value = "EMPRESA")
	private String empresa;
	
	
	public String getMatricula_atd() {
		return matricula_atd;
	}
	public void setMatricula_atd(String matricula_atd) {
		this.matricula_atd = matricula_atd;
	}
	public String getSkill() {
		return skill;
	}
	public void setSkill(String skill) {
		this.skill = skill;
	}
	public String getDac() {
		return dac;
	}
	public void setDac(String dac) {
		this.dac = dac;
	}
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dac == null) ? 0 : dac.hashCode());
		result = prime * result + ((empresa == null) ? 0 : empresa.hashCode());
		result = prime * result + ((matricula_atd == null) ? 0 : matricula_atd.hashCode());
		result = prime * result + ((skill == null) ? 0 : skill.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pesquisa other = (Pesquisa) obj;
		if (dac == null) {
			if (other.dac != null)
				return false;
		} else if (!dac.equals(other.dac))
			return false;
		if (empresa == null) {
			if (other.empresa != null)
				return false;
		} else if (!empresa.equals(other.empresa))
			return false;
		if (matricula_atd == null) {
			if (other.matricula_atd != null)
				return false;
		} else if (!matricula_atd.equals(other.matricula_atd))
			return false;
		if (skill == null) {
			if (other.skill != null)
				return false;
		} else if (!skill.equals(other.skill))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Pesquisa: [matricula_atd=" + matricula_atd + ", skill=" + skill + ", dac=" + dac + ", empresa=" + empresa
				+ "]";
	}
	
}
