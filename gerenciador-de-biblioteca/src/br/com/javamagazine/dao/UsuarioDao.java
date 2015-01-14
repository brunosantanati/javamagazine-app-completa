package br.com.javamagazine.dao;

import java.util.List;

import br.com.javamagazine.entidades.Usuario;


public interface UsuarioDao {
	List<Usuario> listarUsuarios();
	
	boolean existeUsuarioCadastrado();
	
	Usuario pesquisarUsuario(Usuario usuario);
}