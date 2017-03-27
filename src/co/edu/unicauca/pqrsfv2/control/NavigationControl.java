package co.edu.unicauca.pqrsfv2.control;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import co.edu.unicauca.pqrsfv2.consultas.control.TodasPqrsfControl;
import co.edu.unicauca.pqrsfv2.modelo.Usuario;

@SessionScoped
@Named
public class NavigationControl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	RadicarPqrsfControl radicarPqrsfControl;
	@Inject
	DireccionarPQRSFControl direccionarPqrsfControl;
	@Inject
	TodasPqrsfControl todasPqrsfControl;
	private Usuario usuarioAutenticado;
	private String viewToShow;
	
	public NavigationControl(){
		viewToShow="/admin/index.xhtml";
		
		// TODO - Falta logica de autenticacion y autorizacion
		usuarioAutenticado=new Usuario();
		usuarioAutenticado.setUsername("miguelgalindez");
	}

	public String getViewToShow() {
		return viewToShow;
	}

	public void setViewToShow(String viewToShow) {
		this.viewToShow = viewToShow;
	}
	
	public void changeViewToShow(String viewToShow) {
		this.viewToShow = viewToShow;
		
		switch(viewToShow){
			case "/admin/acciones/radicarPQRSF.xhtml":
				radicarPqrsfControl.cargarPqrsfNoRadicadas();
				break;
			
			case "/admin/acciones/direccionarPQRSF.xhtml":
				direccionarPqrsfControl.cargarPQRSFNoDireccionadas();
				break;
				
			case "/admin/consultas/todasPQRSF.xhtml":
				todasPqrsfControl.cargarTodasPqrf();
				break;
		}		
	}
	
	public void logout(){
		
	}

	public Usuario getUsuarioAutenticado() {
		return usuarioAutenticado;
	}

	public void setUsuarioAutenticado(Usuario usuarioAutenticado) {
		this.usuarioAutenticado = usuarioAutenticado;
	}
	
	
}
