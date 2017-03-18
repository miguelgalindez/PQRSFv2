package co.edu.unicauca.pqrsfv2.control;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import co.edu.unicauca.pqrsfv2.logica.PersonaBO;
import co.edu.unicauca.pqrsfv2.logica.PqrsfBO;
import co.edu.unicauca.pqrsfv2.modelo.Pqrsf;
import co.edu.unicauca.pqrsfv2.modelo.Persona;


@ManagedBean(name="registrarPqrsfControl")
@ViewScoped()
public class RegistrarPqrsfControl implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private PqrsfBO pqrsfBO;
	private PersonaBO personaBO;
	private Pqrsf solicitud;
	private Persona persona;
	private ArrayList<SelectItem> tiposSolicitud;	
	private ArrayList<SelectItem> mediosRecepcion;
	private ArrayList<SelectItem> tiposIdentificacion;
	private ArrayList<SelectItem> tiposPersona;
	
	
	boolean isLastStep;	
	boolean lastMovementWasNext;
	
	public RegistrarPqrsfControl(){
		lastMovementWasNext=false;
		pqrsfBO=new PqrsfBO();
		persona=new Persona();
		
		tiposSolicitud=pqrsfBO.obtnTiposSolicitud();
		mediosRecepcion=pqrsfBO.obtnMediosRecepcion();
		tiposIdentificacion=personaBO.obtnTiposIdentificacion();
		tiposPersona=personaBO.obtnTiposPersona();
	}

	

	public String progressHandler(FlowEvent event){
		isLastStep=event.getNewStep().equals("confirmacion");
		RequestContext.getCurrentInstance().update("registroPqrsfForm:panelBotones");
		if(lastMovementWasNext)
			RequestContext.getCurrentInstance().execute("next();");
		else
			RequestContext.getCurrentInstance().execute("back();");
		
		return event.getNewStep();
	}
	
	public void save(ActionEvent actionEvent){		
		FacesMessage msg= new FacesMessage("Exito", "Usuario guardado con exito. Nombre: Telefono: ");		
        FacesContext.getCurrentInstance().addMessage(null, msg);        
	}
	
	public void next(){		
		lastMovementWasNext=true;
		RequestContext.getCurrentInstance().execute("PF('wiz').next();");
		
	}
	
	public void back(){
		lastMovementWasNext=false;
		RequestContext.getCurrentInstance().execute("PF('wiz').back();");
		
	}	
	
	
	
	public Pqrsf getSolicitud() {
		return solicitud;
	}
	public void setSolicitud(Pqrsf solicitud) {
		this.solicitud = solicitud;
	}
		
	public boolean getIsLastStep() {
		return isLastStep;
	}

	public void setIsLastStep(boolean isLastStep) {
		this.isLastStep = isLastStep;
	}



	public ArrayList<SelectItem> getTiposSolicitud() {
		return tiposSolicitud;
	}



	public void setTiposSolicitud(ArrayList<SelectItem> tiposSolicitud) {
		this.tiposSolicitud = tiposSolicitud;
	}



	public ArrayList<SelectItem> getMediosRecepcion() {
		return mediosRecepcion;
	}



	public void setMediosRecepcion(ArrayList<SelectItem> mediosRecepcion) {
		this.mediosRecepcion = mediosRecepcion;
	}



	public ArrayList<SelectItem> getTiposIdentificacion() {
		return tiposIdentificacion;
	}



	public void setTiposIdentificacion(ArrayList<SelectItem> tiposIdentificacion) {
		this.tiposIdentificacion = tiposIdentificacion;
	}



	public ArrayList<SelectItem> getTiposPersona() {
		return tiposPersona;
	}



	public void setTiposPersona(ArrayList<SelectItem> tiposPersona) {
		this.tiposPersona = tiposPersona;
	}



	public Persona getPersona() {
		return persona;
	}



	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	
	
}
