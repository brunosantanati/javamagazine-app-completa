package br.com.javamagazine.dao;

import java.util.List;

import br.com.javamagazine.entidades.Leitor;

public interface LeitorDao {
	void inserir(Leitor leitor);
	
	void remover(Leitor leitor);

	void atualizar(Leitor leitor);
	
	Leitor pesquisarPorId(Integer id);

	List<Leitor> listarLeitores();
}