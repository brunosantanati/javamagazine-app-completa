package br.com.javamagazine.dao;

import java.util.List;

import br.com.javamagazine.entidades.Autor;

public interface AutorDao {
	void inserir(Autor autor);

	void remover(Autor autor);

	void atualizar(Autor autor);

	Autor pesquisarPorId(Integer id);
	
	List<Autor> listarAutores();
}
