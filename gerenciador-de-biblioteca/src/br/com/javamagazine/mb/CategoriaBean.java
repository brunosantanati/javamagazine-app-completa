package br.com.javamagazine.mb;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.javamagazine.dao.CategoriaDao;
import br.com.javamagazine.entidades.Categoria;
import br.com.javamagazine.interceptadores.Transacional;

@Named
@RequestScoped
public class CategoriaBean {
	
	@Inject
	CategoriaDao categoriaDao;
	private Categoria categoria = new Categoria();
	private List<Categoria> categorias;

	@Transacional
	public void salvar(){
		
		if(categoria.getId() == null){
			categoriaDao.inserir(categoria);
		}else{
			categoriaDao.atualizar(categoria);
		}
		
		categoria = new Categoria();
		categorias = categoriaDao.listarCategorias();
	}
	
	@Transacional
	public void remover(Categoria categoria){
		categoriaDao.remover(categoria);
		categorias = categoriaDao.listarCategorias();
	}
	
	@Transacional
	public List<Categoria> getCategorias(){
		if(categorias == null){
			categorias = categoriaDao.listarCategorias();
		}
		
		return categorias;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
}
