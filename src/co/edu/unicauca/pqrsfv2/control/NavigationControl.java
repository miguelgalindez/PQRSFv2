package co.edu.unicauca.pqrsfv2.control;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@SessionScoped
@Named
public class NavigationControl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	RadicarPqrsfControl radicarPqrsfControl;
	private String viewToShow;
	
	public NavigationControl(){
		viewToShow="/admin/index.xhtml";
	}

	public String getViewToShow() {
		return viewToShow;
	}

	public void setViewToShow(String viewToShow) {
		this.viewToShow = viewToShow;
	}
	
	public void changeViewToShow(String viewToShow) {
		this.viewToShow = viewToShow;
		if(viewToShow.equals("/admin/acciones/radicarPQRSF.xhtml")){
			radicarPqrsfControl.cargarPqrsfNoRadicadas();
		}
	}
	
	public void logout(){
		
	}
}
