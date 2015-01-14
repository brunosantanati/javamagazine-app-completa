package br.com.javamagazine.daoimpl;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.javamagazine.dao.FuncionarioBibliotecaDao;
import br.com.javamagazine.dao.GenericoDao;
import br.com.javamagazine.entidades.FuncionarioBiblioteca;

public class FuncionarioBibliotecaDaoImpl implements FuncionarioBibliotecaDao, Serializable {

	private static final long serialVersionUID = 1L;
	private GenericoDao<FuncionarioBiblioteca, Integer> genericoDao;
	
	@Inject
	FuncionarioBibliotecaDaoImpl(EntityManager entityManager){
		genericoDao = new GenericoDaoImpl<FuncionarioBiblioteca, Integer>(FuncionarioBiblioteca.class, entityManager);
	}
	
	@Override
	public void inserir(FuncionarioBiblioteca funcionarioBiblioteca) {
		genericoDao.inserir(funcionarioBiblioteca);
	}

	@Override
	public void remover(FuncionarioBiblioteca funcionarioBiblioteca) {
		genericoDao.remover(funcionarioBiblioteca);
		
	}

	@Override
	public void atualizar(FuncionarioBiblioteca funcionarioBiblioteca) {
		genericoDao.atualizar(funcionarioBiblioteca);		
	}

	@Override
	public List<FuncionarioBiblioteca> listarFuncionariosBiblioteca() {
		return genericoDao.listarTodos();
	}

	@Override
	public boolean existeFuncionarioCadastrado() {
		List<FuncionarioBiblioteca> listaDeFuncionarios = listarFuncionariosBiblioteca();
		
		if(listaDeFuncionarios != null && listaDeFuncionarios.size() > 0){
			return true;
		}
		
		return false;
	}

}