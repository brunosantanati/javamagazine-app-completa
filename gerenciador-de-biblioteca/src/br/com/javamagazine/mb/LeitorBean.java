package br.com.javamagazine.mb;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.javamagazine.dao.LeitorDao;
import br.com.javamagazine.entidades.Endereco;
import br.com.javamagazine.entidades.Leitor;
import br.com.javamagazine.entidades.Telefone;
import br.com.javamagazine.interceptadores.Transacional;

@Named
@RequestScoped
public class LeitorBean {
	
	@Inject
	private LeitorDao leitorDao;	
	private Leitor leitor = new Leitor();
	private Telefone telefoneFixo = new Telefone();
	private Telefone telefoneCelular = new Telefone();
	private List<Leitor> leitores;
	
	LeitorBean(){
		leitor.setEndereco(new Endereco());
	}

	@Transacional
	public void salvar(){
		
		if(leitor.getId() == null){
			vincularTelefones();
			leitorDao.inserir(leitor);
		}else{
			leitor.getTelefones().clear();
			vincularTelefones();
			leitorDao.atualizar(leitor);
		}
		
		telefoneFixo = new Telefone();
		telefoneCelular = new Telefone();
		leitor = new Leitor();
	}
	
	public void vincularTelefones(){
		telefoneFixo.setFixo(true);
		leitor.getTelefones().add(telefoneFixo);
		leitor.getTelefones().add(telefoneCelular);
	}
	
	public void alterar(Leitor leitor){
		this.leitor = leitor;
		for(Telefone telefone : leitor.getTelefones()){
			if(telefone.isFixo()){
				this.telefoneFixo = telefone;
			}else{
				this.telefoneCelular = telefone;
			}
		}
	}
	
	@Transacional
	public void remover(Leitor leitor){
		leitorDao.remover(leitor);
		leitores = leitorDao.listarLeitores();
	}
	
	@Transacional
	public List<Leitor> getLeitores(){
		if(leitores == null){
			leitores = leitorDao.listarLeitores();
		}
		
		return leitores;
	}
	
	public Leitor getLeitor() {
		return leitor;
	}

	public void setLeitor(Leitor leitor) {
		this.leitor = leitor;
	}
	
	public Telefone getTelefoneFixo() {
		return telefoneFixo;
	}

	public void setTelefoneFixo(Telefone telefoneFixo) {
		this.telefoneFixo = telefoneFixo;
	}

	public Telefone getTelefoneCelular() {
		return telefoneCelular;
	}

	public void setTelefoneCelular(Telefone telefoneCelular) {
		this.telefoneCelular = telefoneCelular;
	}
	
}
