package co.edu.unicauca.pqrsfv2.control;

import java.io.Serializable;
import java.util.ArrayList;

import javax.ejb.LocalBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import co.edu.unicauca.pqrsfv2.dao.PqrsfDAO;
import co.edu.unicauca.pqrsfv2.modelo.Pqrsf;
import co.edu.unicauca.pqrsfv2.modelo.Radicado;

@SessionScoped
@LocalBean
@Named
public class RadicarPqrsfControl implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private PqrsfDAO pqrsfDAO;
	private ArrayList<Pqrsf> pqrsfNoRadicadas;
	private Pqrsf selectedPqrsf;
	private String selectedAction;
	private Radicado radicado;
	
	public void changeSelectedAction(String action){
		selectedAction=action;
	}
	
	public RadicarPqrsfControl(){
		pqrsfNoRadicadas=new ArrayList<>();
		selectedAction="radicar";
		radicado=new Radicado();
	}
	
	public void cargarPqrsfNoRadicadas(){
		pqrsfNoRadicadas=pqrsfDAO.obtnNoRadicadas();
	}

	public ArrayList<Pqrsf> getPqrsfNoRadicadas() {
		return pqrsfNoRadicadas;
	}

	public void setPqrsfNoRadicadas(ArrayList<Pqrsf> pqrsfNoRadicadas) {
		this.pqrsfNoRadicadas = pqrsfNoRadicadas;
	}

	public Pqrsf getSelectedPqrsf() {
		return selectedPqrsf;
	}

	public void setSelectedPqrsf(Pqrsf selectedPqrsf) {
		this.selectedPqrsf = selectedPqrsf;
	}

	public Radicado getRadicado() {
		return radicado;
	}

	public void setRadicado(Radicado radicado) {
		this.radicado = radicado;
	}

	public String getSelectedAction() {
		return selectedAction;
	}

	public void setSelectedAction(String selectedAction) {
		this.selectedAction = selectedAction;
	}
		
}
