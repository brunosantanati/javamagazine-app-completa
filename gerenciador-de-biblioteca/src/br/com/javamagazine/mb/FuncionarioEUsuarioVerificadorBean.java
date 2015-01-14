package br.com.javamagazine.mb;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.javamagazine.dao.FuncionarioBibliotecaDao;
import br.com.javamagazine.dao.UsuarioDao;

@Named
@RequestScoped
public class FuncionarioEUsuarioVerificadorBean {
	
	@Inject
	private FuncionarioBibliotecaDao funcionarioBibliotecaDao;
	@Inject
	private UsuarioDao usuarioDao;
	
	public boolean existeFuncionarioEUsuarioCadastrado(){
		return (funcionarioBibliotecaDao.existeFuncionarioCadastrado() && usuarioDao.existeUsuarioCadastrado());
	}
}
