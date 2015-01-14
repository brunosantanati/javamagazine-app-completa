package br.com.javamagazine.mb;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.javamagazine.dao.EditoraDao;
import br.com.javamagazine.entidades.Editora;
import br.com.javamagazine.entidades.Endereco;
import br.com.javamagazine.entidades.Telefone;
import br.com.javamagazine.interceptadores.Transacional;

@Named
@RequestScoped
public class EditoraBean {
	
	@Inject
	private EditoraDao editoraDao;	
	private Editora editora = new Editora();
	private Telefone telefoneFixo = new Telefone();
	private Telefone telefoneCelular = new Telefone();
	private List<Editora> editoras;
	
	EditoraBean(){
		editora.setEndereco(new Endereco());
	}

	@Transacional
	public void salvar(){
		
		if(editora.getId() == null){
			vincularTelefones();
			editoraDao.inserir(editora);
		}else{
			editora.getTelefones().clear();
			vincularTelefones();
			editoraDao.atualizar(editora);
		}
		
		telefoneFixo = new Telefone();
		telefoneCelular = new Telefone();
		editora = new Editora();
	}
	
	public void vincularTelefones(){
		telefoneFixo.setFixo(true);
		editora.getTelefones().add(telefoneFixo);
		editora.getTelefones().add(telefoneCelular);
	}
	
	public void alterar(Editora editora){
		this.editora = editora;
		for(Telefone telefone : editora.getTelefones()){
			if(telefone.isFixo()){
				this.telefoneFixo = telefone;
			}else{
				this.telefoneCelular = telefone;
			}
		}
	}
	
	@Transacional
	public void remover(Editora editora){
		editoraDao.remover(editora);
		editoras = editoraDao.listarEditoras();
	}
	
	@Transacional
	public List<Editora> getEditoras(){
		if(editoras == null){
			editoras = editoraDao.listarEditoras();
		}
		
		return editoras;
	}
	
	public Editora getEditora() {
		return editora;
	}

	public void setEditora(Editora editora) {
		this.editora = editora;
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
