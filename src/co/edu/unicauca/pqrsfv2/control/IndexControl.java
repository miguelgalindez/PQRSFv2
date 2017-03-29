package co.edu.unicauca.pqrsfv2.control;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@SessionScoped
@Named
public class IndexControl implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String selectedAction;
	
	public IndexControl(){
		selectedAction="/admin/acciones/registrarPQRSF.xhtml";
	}
	
	public String getSelectedAction() {
		return selectedAction;
	}
	public void setSelectedAction(String selectedAction) {
		this.selectedAction = selectedAction;
	}
	
	
	

}
