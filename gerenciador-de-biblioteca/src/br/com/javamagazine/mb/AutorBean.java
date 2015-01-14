package br.com.javamagazine.mb;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.javamagazine.dao.AutorDao;
import br.com.javamagazine.entidades.Autor;
import br.com.javamagazine.interceptadores.Transacional;

@Named
@RequestScoped
public class AutorBean {
	
	@Inject
	AutorDao autorDao;
	private Autor autor = new Autor();
	private List<Autor> autores;
	
	@Transacional
	public void salvar(){
		
		if(autor.getId() == null){
			autorDao.inserir(autor);
		}else{
			autorDao.atualizar(autor);
		}
		
		autor = new Autor();
		autores = autorDao.listarAutores();
	}

	@Transacional
	public void remover(Autor autor){
		autorDao.remover(autor);
		autores = autorDao.listarAutores();
	}
	
	@Transacional
	public List<Autor> getAutores(){
		if(autores == null){
			autores = autorDao.listarAutores();
		}
		
		return autores;
	}
	
	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}
}
