package br.com.javamagazine.util;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

public class Navegador {

	@Inject
	private NavigationHandler navigationHandler;
	@Inject
	private FacesContext facesContext;
	
	public void forwardTo(String url) {
		navigationHandler.handleNavigation(facesContext, null, url);
		facesContext.renderResponse();
	}
	
}
