package br.com.javamagazine.daoimpl;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.javamagazine.dao.EmprestimoDao;
import br.com.javamagazine.dao.GenericoDao;
import br.com.javamagazine.entidades.Emprestimo;

public class EmprestimoDaoImpl implements EmprestimoDao, Serializable{
	
	private static final long serialVersionUID = 1L;
	private GenericoDao<Emprestimo, Integer> genericoDao;
	
	@Inject
	EmprestimoDaoImpl(EntityManager entityManager){
		genericoDao = new GenericoDaoImpl<Emprestimo, Integer>(Emprestimo.class, entityManager);
	}
	
	@Override
	public void inserir(Emprestimo emprestimo) {
		genericoDao.inserir(emprestimo);
	}
	
	@Override
	public void atualizar(Emprestimo emprestimo) {
		genericoDao.atualizar(emprestimo);
	}

	@Override
	public List<Emprestimo> listarEmprestimos() {		
		return genericoDao.listarTodos();
	}

}
