package co.edu.unicauca.pqrsfv2.control;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import co.edu.unicauca.pqrsfv2.control.acciones.DireccionarPQRSFControl;
import co.edu.unicauca.pqrsfv2.control.acciones.RadicarPqrsfControl;
import co.edu.unicauca.pqrsfv2.control.consultas.ConsultasControl;
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
	ConsultasControl consultasControl;
	private Usuario usuarioAutenticado;
	private String viewToShow;
	

	@PostConstruct
	public void init(){
		this.changeViewToShow("/admin/index.xhtml");
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
		case "/admin/index.xhtml": 
				consultasControl.cargarIndicadores();
				break;
			case "/admin/acciones/radicarPQRSF.xhtml":
				radicarPqrsfControl.cargarPqrsfNoRadicadas();
				break;
			
			case "/admin/acciones/direccionarPQRSF.xhtml":
				direccionarPqrsfControl.cargarPQRSFNoDireccionadas();
				break;
				
			case "/admin/consultas/todasPQRSF.xhtml":				
				consultasControl.obtnTodasOrdenes();								
				break;
			
			case "/admin/consultas/pqrsfAtendidas.xhtml":								
				consultasControl.cargarOrdenesPorEstado(2);
				this.viewToShow="/admin/consultas/todasPQRSF.xhtml";
				break;
			
			case "/admin/consultas/pqrsfEnTramite.xhtml":				
				consultasControl.cargarOrdenesPorEstado(1);				
				this.viewToShow="/admin/consultas/todasPQRSF.xhtml";
				break;
				
			case "/admin/consultas/pqrsfPendientes.xhtml":								
				consultasControl.cargarOrdenesPorEstado(0);				
				this.viewToShow="/admin/consultas/todasPQRSF.xhtml";
				break;
			
			case "/admin/consultas/pqrsfProximasaVencerse.xhtml":								
				consultasControl.cargarOrdenesPorVencimiento(3);				
				this.viewToShow="/admin/consultas/todasPQRSF.xhtml";
				break;
			
			case "/admin/consultas/pqrsfVencidas.xhtml":								
				consultasControl.cargarOrdenesPorVencimiento(-1);				
				this.viewToShow="/admin/consultas/todasPQRSF.xhtml";
				break;
		}		
	}
	
	public String logout(){
		this.usuarioAutenticado=null;
		return "/index.xhtml?faces-redirect=true";
	}
	
	public boolean isAuthorizedUser(String email, String name, String link, String picture){
		String data[]=email.split("@");
		String username=data[0], domain=data[1];
		if(domain.equals("unicauca.edu.co")){
			Usuario usuario=usuarioDAO.obtnUsuario(username);
			Date now=new Date();		
			if(usuario!=null && now.after(usuario.getFechaInicio())){		
				Date fechaFin=usuario.getFechaFin();
				if(fechaFin==null || now.before(fechaFin)){
					usuario.setUsername(username);
					usuario.setNombre(name);						
					usuario.setFoto(picture);				
					usuario.setEnlace(link);
					
					this.usuarioAutenticado=usuario;
					return true;
				}			
			}
		}		
		this.usuarioAutenticado=null;
		return false;
	}

	public Usuario getUsuarioAutenticado() {
		return usuarioAutenticado;
	}

	public void setUsuarioAutenticado(Usuario usuarioAutenticado) {
		this.usuarioAutenticado = usuarioAutenticado;
	}
	
	
}
