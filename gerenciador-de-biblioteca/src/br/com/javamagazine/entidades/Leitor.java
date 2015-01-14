package br.com.javamagazine.entidades;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Leitor extends Pessoa{
	
	private String turma;
	private String matricula;
	@OneToMany(cascade={CascadeType.ALL})
	@JoinColumn(name="id_leitor")
	private List<Telefone> telefones = new ArrayList<Telefone>();
	@OneToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="id_endereco")
	private Endereco endereco;
	
	public String getTurma() {
		return turma;
	}
	public void setTurma(String turma) {
		this.turma = turma;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public List<Telefone> getTelefones() {
		return telefones;
	}
	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
}
