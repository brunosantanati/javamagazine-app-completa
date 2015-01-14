package br.com.javamagazine.daoimpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.javamagazine.dao.GenericoDao;
import br.com.javamagazine.dao.LivroDao;
import br.com.javamagazine.entidades.Emprestimo;
import br.com.javamagazine.entidades.Livro;

public class LivroDaoImpl implements LivroDao, Serializable {
	
	private static final long serialVersionUID = 1L;
	private GenericoDao<Livro, Integer> genericoDao;
	
	@Inject
	LivroDaoImpl(EntityManager entityManager){
		genericoDao = new GenericoDaoImpl<Livro, Integer>(Livro.class, entityManager);
	}
	
	@Override
	public void inserir(Livro livro) {
		genericoDao.inserir(livro);
	}
	
	@Override
	public void remover(Livro livro) {
		genericoDao.remover(livro);		
	}
	
	@Override
	public void atualizar(Livro livro) {
		genericoDao.atualizar(livro);		
	}
	
	@Override
	public Livro pesquisarPorId(Integer id) {
		return genericoDao.pesquisarPorID(id);
	}

	@Override
	public List<Livro> listarLivros() {
		return genericoDao.listarTodos();
	}

	@Override
	public List<Livro> listarLivrosDisponiveisParaEmprestimo() {
		
		List<Livro> todosLivros = listarLivros();
		List<Livro> livrosDisponiveis = new ArrayList<Livro>();
		
		for(Livro livro : todosLivros){
	
			List<Emprestimo> emprestimos = livro.getEmprestimos();
			
			boolean todosEmprestimosTemDataDevolucao = true;
			
			for(Emprestimo emprestimo : emprestimos){
				if(emprestimo.getDataDevolucao() == null)
					todosEmprestimosTemDataDevolucao = false;
			}
			
			if(todosEmprestimosTemDataDevolucao)
				livrosDisponiveis.add(livro);
		}
		
		return livrosDisponiveis;
	}
	
	@Override
	public List<Livro> listarLivrosEmprestados() {
		
		List<Livro> todosLivros = listarLivros();		
		List<Livro> livrosEmprestados = new ArrayList<Livro>();
		
		for(Livro livro : todosLivros){
				
			List<Emprestimo> emprestimos = livro.getEmprestimos();
			
			for(Emprestimo emprestimo : emprestimos){
				if(emprestimo.getDataDevolucao() == null)
					livrosEmprestados.add(livro);
			}
		}
		
		return livrosEmprestados;
	}
}
