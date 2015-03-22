package br.com.javamagazine.mb;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.javamagazine.dao.UsuarioDao;
import br.com.javamagazine.entidades.Usuario;


@Named
@RequestScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoBean usuarioLogado;
	@Inject
	private FuncionarioEUsuarioVerificadorBean verificadorBean;
	private boolean opcaoCadastroUsuarioHabilitada;
	@Inject
	private UsuarioDao usuarioDao;
	private Usuario usuario = new Usuario();
	
	public String efetuarLogin(){

		Usuario usuarioEncontrado = usuarioDao.pesquisarUsuario(this.usuario);

		if(usuarioEncontrado != null){
			usuarioLogado.logar(usuarioEncontrado);
			return "principal?faces-redirect=true";
		}else{
			this.usuario = new Usuario();
			return "login?faces-redirect=true";
		}
		
	}
	
	public String efetuarLogout(){
		usuarioLogado.deslogar();
		this.usuario = new Usuario();
		return "login?faces-redirect=true";
	}
	
	public boolean isOpcaoCadastroUsuarioHabilitada() {
		opcaoCadastroUsuarioHabilitada = !verificadorBean.existeFuncionarioEUsuarioCadastrado();
		return opcaoCadastroUsuarioHabilitada; 
	}
	
	public Usuario getUsuario(){
		return this.usuario;
	}
}
