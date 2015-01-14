package br.com.javamagazine.daoimpl;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.javamagazine.dao.EditoraDao;
import br.com.javamagazine.dao.GenericoDao;
import br.com.javamagazine.entidades.Editora;

public class EditoraDaoImpl implements EditoraDao, Serializable {

	private static final long serialVersionUID = 1L;
	private GenericoDao<Editora, Integer> genericoDao;
	
	@Inject
	EditoraDaoImpl(EntityManager entityManager){
		genericoDao = new GenericoDaoImpl<Editora, Integer>(Editora.class, entityManager);
	}

	@Override
	public void inserir(Editora editora) {
		genericoDao.inserir(editora);
	}

	@Override
	public void remover(Editora editora) {
		genericoDao.remover(editora);
		
	}

	@Override
	public void atualizar(Editora editora) {
		genericoDao.atualizar(editora);		
	}
	
	@Override
	public Editora pesquisarPorId(Integer id){
		return genericoDao.pesquisarPorID(id);
	}

	@Override
	public List<Editora> listarEditoras() {
		return genericoDao.listarTodos();
	}

}
