package br.com.javamagazine.daoimpl;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.javamagazine.dao.CategoriaDao;
import br.com.javamagazine.dao.GenericoDao;
import br.com.javamagazine.entidades.Categoria;

public class CategoriaDaoImpl implements CategoriaDao, Serializable{
	
	private static final long serialVersionUID = 1L;
	private GenericoDao<Categoria, Integer> genericoDao;
	
	@Inject
	CategoriaDaoImpl(EntityManager entityManager){
		genericoDao = new GenericoDaoImpl<Categoria, Integer>(Categoria.class, entityManager);
	}
	
	@Override
	public void inserir(Categoria categoria) {
		genericoDao.inserir(categoria);
	}
	
	@Override
	public void remover(Categoria categoria) {
		genericoDao.remover(categoria);		
	}
	
	@Override
	public void atualizar(Categoria categoria) {
		genericoDao.atualizar(categoria);		
	}
	
	@Override
	public Categoria pesquisarPorId(Integer id){
		return genericoDao.pesquisarPorID(id);
	}

	@Override
	public List<Categoria> listarCategorias() {
		return genericoDao.listarTodos();
	}

}
