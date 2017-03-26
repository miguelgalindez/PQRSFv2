package co.edu.unicauca.pqrsfv2.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import co.edu.unicauca.pqrsfv2.dao.DependenciaDAO;
import co.edu.unicauca.pqrsfv2.dao.PqrsfDAO;
import co.edu.unicauca.pqrsfv2.modelo.Orden;
import co.edu.unicauca.pqrsfv2.modelo.Pqrsf;

@SessionScoped
@Named
public class DireccionarPQRSFControl implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private PqrsfDAO pqrsfDAO;
	@Inject
	private DependenciaDAO dependenciaDAO;
	private ArrayList<Pqrsf> pqrsfNoDireccionadas;
	private Pqrsf selectedPqrsf;
	private Orden orden;
	private String selectedAction;
	private Integer idDependenciaSeleccionada;
	
	public DireccionarPQRSFControl(){
		inicializarDatos();
	}
	
	public void cargarPQRSFNoDireccionadas(){
		pqrsfNoDireccionadas=pqrsfDAO.obtnNoDireccionadas();
	}

	public void changeSelectedAction(String action){
		selectedAction=action;
	}
	
	public HashMap<Integer, String> obtnFuncionarios(){
		if (idDependenciaSeleccionada==null)
			return new HashMap<Integer, String>();
		else
			return dependenciaDAO.obtnFuncionarios(idDependenciaSeleccionada);
	}
	
	private void inicializarDatos(){
		orden=new Orden();
		idDependenciaSeleccionada=null;
		selectedAction=null;
		selectedPqrsf=null;
	}
	
	public ArrayList<Pqrsf> getPqrsfNoDireccionadas() {
		return pqrsfNoDireccionadas;
	}

	public void setPqrsfNoDireccionadas(ArrayList<Pqrsf> pqrsfNoDireccionadas) {
		this.pqrsfNoDireccionadas = pqrsfNoDireccionadas;
	}

	public String getSelectedAction() {
		return selectedAction;
	}

	public void setSelectedAction(String selectedAction) {
		this.selectedAction = selectedAction;
	}

	public Pqrsf getSelectedPqrsf() {
		return selectedPqrsf;
	}

	public void setSelectedPqrsf(Pqrsf selectedPqrsf) {
		this.selectedPqrsf = selectedPqrsf;
	}

	public Orden getOrden() {
		return orden;
	}

	public void setOrden(Orden orden) {
		this.orden = orden;
	}

	public Integer getIdDependenciaSeleccionada() {
		return idDependenciaSeleccionada;
	}

	public void setIdDependenciaSeleccionada(Integer idDependenciaSeleccionada) {
		this.idDependenciaSeleccionada = idDependenciaSeleccionada;
	}
}
