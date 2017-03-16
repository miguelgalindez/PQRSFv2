package control;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;

import modelo.Persona;

@ManagedBean(name="wizard")
@ViewScoped
public class WizardControl implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Persona persona;	
	boolean isLastStep;	
	boolean lastMovementWasNext;	

	public WizardControl(){
		persona=new Persona();		
		lastMovementWasNext=false;
	}
	public boolean getIsLastStep() {
		return isLastStep;
	}

	public void setIsLastStep(boolean isLastStep) {
		this.isLastStep = isLastStep;
	}

	public String progressHandler(FlowEvent event){
		isLastStep=event.getNewStep().equals("confirmacion");
		RequestContext.getCurrentInstance().update("personaWizard:panelBotones");
		if(lastMovementWasNext)
			RequestContext.getCurrentInstance().execute("next();");
		else
			RequestContext.getCurrentInstance().execute("back();");
		
		return event.getNewStep();
	}
	
	public void save(ActionEvent actionEvent){		
		FacesMessage msg= new FacesMessage("Exito", "Usuario guardado con exito. Nombre: "+persona.getNombre()+" Telefono: "+persona.getTelefono());		
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

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}	
}
