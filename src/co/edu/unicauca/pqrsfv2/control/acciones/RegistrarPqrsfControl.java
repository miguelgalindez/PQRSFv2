package co.edu.unicauca.pqrsfv2.control.acciones;

import java.io.Serializable;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import co.edu.unicauca.pqrsfv2.modelo.Pqrsf;
import co.edu.unicauca.pqrsfv2.modelo.Persona;
import co.edu.unicauca.pqrsfv2.control.ModalRespuestaControl;
import co.edu.unicauca.pqrsfv2.dao.PersonaDAO;
import co.edu.unicauca.pqrsfv2.dao.PqrsfDAO;
import co.edu.unicauca.pqrsfv2.dao.ValoresDAO;

@Named("registrarPqrsfControl")
@SessionScoped
public class RegistrarPqrsfControl implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	PersonaDAO personaDAO;
	@EJB
	PqrsfDAO pqrsfDAO;
	@Inject
	ModalRespuestaControl modalRespuestaControl;
	@Inject
	ValoresDAO valoresDAO;
	private Pqrsf pqrsf;
	private Persona persona;
	private Integer departamento;		
	boolean isLastStep;	
	boolean lastMovementWasNext;
	
	public RegistrarPqrsfControl(){
		inicializarDatos();	
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
	
	public void guardarPqrsf(ActionEvent actionEvent){
		RequestContext ctx=RequestContext.getCurrentInstance();
		
		pqrsf.setPersona(persona);		
		boolean success=pqrsfDAO.guardar(pqrsf);					
		modalRespuestaControl.operacionExitosa(success);
		
		if(success){			
			modalRespuestaControl.configurar("Registro exitoso", "La PQRSF ha sido registrada con el código "+ pqrsf.getCodigo());
			isLastStep=false;
			//ctx.execute("$('#registroPqrsfForm').trigger('reset')");
			inicializarDatos();
		}
		else
			modalRespuestaControl.configurar("Error", "No se pudo registrar la PQRSF. Si el problema persiste, por favor comunicarse con la DivTIC.");
		
		ctx.execute("$('#modalRespuesta').modal('toggle');");
	}
	
	private void inicializarDatos() {
		lastMovementWasNext=false;		
		persona=new Persona();
		pqrsf=new Pqrsf();
		isLastStep=false;
		departamento=null;
		
	}

	public void next(){		
		lastMovementWasNext=true;
		RequestContext.getCurrentInstance().execute("PF('wiz').next();");
		
	}
	
	public void back(){
		lastMovementWasNext=false;
		RequestContext.getCurrentInstance().execute("PF('wiz').back();");
		
	}
		
	public Pqrsf getPqrsf() {
		return pqrsf;
	}

	public void setPqrsf(Pqrsf pqrsf) {
		this.pqrsf = pqrsf;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Integer getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Integer departamento) {
		this.departamento = departamento;
	}

	public boolean getIsLastStep() {
		return isLastStep;
	}

	public void setIsLastStep(boolean isLastStep) {
		this.isLastStep = isLastStep;
	}

	public boolean getIsLastMovementWasNext() {
		return lastMovementWasNext;
	}

	public void setIsLastMovementWasNext(boolean lastMovementWasNext) {
		this.lastMovementWasNext = lastMovementWasNext;
	}				
}
