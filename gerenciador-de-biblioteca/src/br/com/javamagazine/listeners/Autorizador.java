package br.com.javamagazine.listeners;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.inject.Inject;

import br.com.javamagazine.mb.FuncionarioEUsuarioVerificadorBean;
import br.com.javamagazine.mb.UsuarioLogadoBean;
import br.com.javamagazine.util.Navegador;

public class Autorizador implements PhaseListener {

	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoBean usuarioLogado;
	@Inject
	FacesContext context;
	@Inject
	private Navegador navegador;
	@Inject
	private FuncionarioEUsuarioVerificadorBean verificadorBean;

	@Override
	public void afterPhase(PhaseEvent event) {
		
		if(usuarioLogado.isLogado()){
			
			if("/funcionario_biblioteca.xhtml".equals(context.getViewRoot().getViewId()) &&
					!usuarioLogado.getUsuario().isAdmin()){
				navegador.redirecionar("menu_principal");
			}
			
		}else{
			
			if("/login.xhtml".equals(context.getViewRoot().getViewId())){
				return;
			}
			
			if("/funcionario_biblioteca.xhtml".equals(context.getViewRoot().getViewId()) && 
					!verificadorBean.existeFuncionarioEUsuarioCadastrado()){
				return;
			}
					
			navegador.redirecionar("login");
			
		}
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		// não é necessário fazer nada antes da fase Restore View
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}