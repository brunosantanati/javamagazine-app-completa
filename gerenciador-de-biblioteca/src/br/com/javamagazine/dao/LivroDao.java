package br.com.javamagazine.dao;

import java.util.List;

import br.com.javamagazine.entidades.Livro;

public interface LivroDao {
	void inserir(Livro livro);
	
	void remover(Livro livro);
	
	void atualizar(Livro livro);
	
	Livro pesquisarPorId(Integer id);
	
	List<Livro> listarLivros();
	
	List<Livro> listarLivrosDisponiveisParaEmprestimo();
	
	List<Livro> listarLivrosEmprestados();
}
