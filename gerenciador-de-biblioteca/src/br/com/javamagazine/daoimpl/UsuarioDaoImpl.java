package br.com.javamagazine.daoimpl;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.javamagazine.dao.GenericoDao;
import br.com.javamagazine.dao.UsuarioDao;
import br.com.javamagazine.entidades.Usuario;

public class UsuarioDaoImpl implements UsuarioDao, Serializable {

	private static final long serialVersionUID = 1L;
	private GenericoDao<Usuario, Integer> genericoDao;
	private EntityManager entityManager;
	
	@Inject
	UsuarioDaoImpl(EntityManager entityManager){
		genericoDao = new GenericoDaoImpl<Usuario, Integer>(Usuario.class, entityManager);
		this.entityManager = entityManager;
	}

	@Override
	public List<Usuario> listarUsuarios() {
		return genericoDao.listarTodos();
	}

	@Override
	public boolean existeUsuarioCadastrado() {
		List<Usuario> listaDeUsuarios = listarUsuarios();
		
		if(listaDeUsuarios != null && listaDeUsuarios.size() > 0){
			return true;
		}
		
		return false;
	}

	@Override
	public Usuario pesquisarUsuario(Usuario usuario) {
		Usuario usuarioEncontrado = null;
		
		Query query = entityManager.createQuery("select u from Usuario u where u.usuario = :usuarioParam and u.senha = :senhaParam")
				.setParameter("usuarioParam", usuario.getUsuario())
				.setParameter("senhaParam", usuario.getSenha());

		try{
			usuarioEncontrado = (Usuario)query.getSingleResult();
		}catch(NoResultException e){
			// não faz nada, se não achar o usuário deixa retornar null
		}

		return usuarioEncontrado;
	}

}