package co.edu.unicauca.pqrsfv2.control;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import co.edu.unicauca.pqrsfv2.control.acciones.DireccionarPQRSFControl;
import co.edu.unicauca.pqrsfv2.control.acciones.RadicarPqrsfControl;
import co.edu.unicauca.pqrsfv2.control.consultas.TodasPqrsfControl;
import co.edu.unicauca.pqrsfv2.dao.UsuarioDAO;
import co.edu.unicauca.pqrsfv2.modelo.Usuario;

@SessionScoped
@Named
public class NavigationControl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	UsuarioDAO usuarioDAO;
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
		usuarioAutenticado=null;
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
	
	public boolean isAuthorizedUser(String email, String name, String link, String picture){
		
		Usuario usuario=usuarioDAO.obtnUsuario(email.split("@")[0]);
		if(usuario!=null){
			usuario.setNombre(name);			
			
			if(usuario.getFoto()==null || usuario.getEnlace()==null){
				usuario.setFoto(picture);
				usuario.setEnlace(link);				
				// TODO - Guardar cambios en la BD
			}
			this.usuarioAutenticado=usuario;
			return true;
		}
		return false;
	}

	public Usuario getUsuarioAutenticado() {
		return usuarioAutenticado;
	}

	public void setUsuarioAutenticado(Usuario usuarioAutenticado) {
		this.usuarioAutenticado = usuarioAutenticado;
	}
	
	
}
