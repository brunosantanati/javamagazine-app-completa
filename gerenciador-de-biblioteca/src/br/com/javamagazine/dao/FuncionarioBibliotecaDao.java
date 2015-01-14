package br.com.javamagazine.dao;

import java.util.List;

import br.com.javamagazine.entidades.FuncionarioBiblioteca;

public interface FuncionarioBibliotecaDao {
	void inserir(FuncionarioBiblioteca funcionarioBiblioteca);
	
	void remover(FuncionarioBiblioteca funcionarioBiblioteca);

	void atualizar(FuncionarioBiblioteca funcionarioBiblioteca);

	List<FuncionarioBiblioteca> listarFuncionariosBiblioteca();
	
	boolean existeFuncionarioCadastrado();
}