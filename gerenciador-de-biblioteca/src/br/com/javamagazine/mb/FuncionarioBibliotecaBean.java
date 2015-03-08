package br.com.javamagazine.mb;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.javamagazine.dao.FuncionarioBibliotecaDao;
import br.com.javamagazine.entidades.Endereco;
import br.com.javamagazine.entidades.FuncionarioBiblioteca;
import br.com.javamagazine.entidades.Telefone;
import br.com.javamagazine.entidades.Usuario;
import br.com.javamagazine.interceptadores.Transacional;
import br.com.javamagazine.util.Navegador;

@Named
@RequestScoped
public class FuncionarioBibliotecaBean {
	
	@Inject
	private FuncionarioBibliotecaDao funcionarioBibliotecaDao;
	@Inject
	private FuncionarioEUsuarioVerificadorBean verificadorBean;
	private FuncionarioBiblioteca funcionarioBiblioteca = new FuncionarioBiblioteca();
	private Telefone telefoneFixo = new Telefone();
	private Telefone telefoneCelular = new Telefone();
	private List<FuncionarioBiblioteca> funcionariosBiblioteca;
	@Inject
	private Navegador navegador;
	private boolean redirecionarParaLogin;
	private boolean campoAdminDesabilitado;

	FuncionarioBibliotecaBean(){
		funcionarioBiblioteca.setEndereco(new Endereco());
		
		Usuario usuario = new Usuario();
		usuario.setFuncionarioBiblioteca(funcionarioBiblioteca);
		funcionarioBiblioteca.setUsuario(usuario);
	}

	@Transacional
	public void salvar(){
		
		if(isCampoAdminDesabilitado()){
			redirecionarParaLogin = true;
		}
		
		if(funcionarioBiblioteca.getId() == null){
			vincularTelefones();
			funcionarioBibliotecaDao.inserir(funcionarioBiblioteca);
		}else{
			funcionarioBiblioteca.getTelefones().clear();
			vincularTelefones();
			funcionarioBibliotecaDao.atualizar(funcionarioBiblioteca);
		}
		
		telefoneFixo = new Telefone();
		telefoneCelular = new Telefone();
		funcionarioBiblioteca = new FuncionarioBiblioteca();
		funcionariosBiblioteca = funcionarioBibliotecaDao.listarFuncionariosBiblioteca();
	}
	
	public void vincularTelefones(){
		telefoneFixo.setFixo(true);
		funcionarioBiblioteca.getTelefones().add(telefoneFixo);
		funcionarioBiblioteca.getTelefones().add(telefoneCelular);
	}
	
	public void alterar(FuncionarioBiblioteca funcionarioBiblioteca){
		this.funcionarioBiblioteca = funcionarioBiblioteca;
		for(Telefone telefone : funcionarioBiblioteca.getTelefones()){
			if(telefone.isFixo()){
				this.telefoneFixo = telefone;
			}else{
				this.telefoneCelular = telefone;
			}
		}
	}
	
	@Transacional
	public void remover(FuncionarioBiblioteca funcionarioBiblioteca){
		funcionarioBibliotecaDao.remover(funcionarioBiblioteca);
		funcionariosBiblioteca = funcionarioBibliotecaDao.listarFuncionariosBiblioteca();
	}
	
	public void redirecionarParaLogin(){
		if(redirecionarParaLogin)
			navegador.redirecionar("login");
	}
	
	@Transacional
	public List<FuncionarioBiblioteca> getFuncionariosBiblioteca(){
		if(funcionariosBiblioteca == null){
			funcionariosBiblioteca = funcionarioBibliotecaDao.listarFuncionariosBiblioteca();
		}
		
		return funcionariosBiblioteca;
	}
	
	public FuncionarioBiblioteca getFuncionarioBiblioteca() {
		return funcionarioBiblioteca;
	}

	public void setFuncionarioBiblioteca(FuncionarioBiblioteca funcionarioBiblioteca) {
		this.funcionarioBiblioteca = funcionarioBiblioteca;
	}
	
	public Telefone getTelefoneFixo() {
		return telefoneFixo;
	}

	public void setTelefoneFixo(Telefone telefoneFixo) {
		this.telefoneFixo = telefoneFixo;
	}

	public Telefone getTelefoneCelular() {
		return telefoneCelular;
	}

	public void setTelefoneCelular(Telefone telefoneCelular) {
		this.telefoneCelular = telefoneCelular;
	}
	
	public boolean isCampoAdminDesabilitado() {
		campoAdminDesabilitado = !verificadorBean.existeFuncionarioEUsuarioCadastrado();
		return campoAdminDesabilitado;
	}
	
}
