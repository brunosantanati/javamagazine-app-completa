package br.com.javamagazine.dao;

import java.util.List;

import br.com.javamagazine.entidades.Emprestimo;

public interface EmprestimoDao {
	void inserir(Emprestimo emprestimo);
	
	void atualizar(Emprestimo emprestimo);
	
	List<Emprestimo> listarEmprestimos();
}
