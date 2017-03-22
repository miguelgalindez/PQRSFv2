package co.edu.unicauca.pqrsfv2.control;

import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import co.edu.unicauca.pqrsfv2.modelo.Pqrsf;
import co.edu.unicauca.pqrsfv2.modelo.Persona;
import co.edu.unicauca.pqrsfv2.dao.PersonaDAO;
import co.edu.unicauca.pqrsfv2.dao.PqrsfDAO;

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
	private Pqrsf pqrsf;
	private Persona persona;
	private ArrayList<SelectItem> tiposPqrsf;	
	private ArrayList<SelectItem> mediosRecepcion;
	private ArrayList<SelectItem> tiposIdentificacion;
	private ArrayList<SelectItem> tiposPersona;
	private ArrayList<SelectItem> departamentos;
	private ArrayList<SelectItem> municipios;	
	private Integer departamento;		
	boolean isLastStep;	
	boolean lastMovementWasNext;
	
	public RegistrarPqrsfControl(){
		inicializarDatos();	
	}
	
	@PostConstruct
	private void init() {
		
		// TODO colocar mensajes para cuando los listados sean null (problemas con la BD)
			
		tiposIdentificacion=personaDAO.obtnTiposIdentificacion();
		tiposIdentificacion.add(0, new SelectItem(null, "Seleccione..."));	
		tiposPersona=personaDAO.obtnTiposPersona();
		tiposPersona.add(0, new SelectItem(null, "Seleccione..."));
		departamentos=personaDAO.obtnDepartamentos();
		departamentos.add(0, new SelectItem(null, "Seleccione..."));
		
		mediosRecepcion=pqrsfDAO.obtnMediosRecepcion();
		mediosRecepcion.add(0, new SelectItem(null, "Seleccione..."));
		tiposPqrsf=pqrsfDAO.obtnTiposPqrsf();
		tiposPqrsf.add(0, new SelectItem(null, "Seleccione..."));
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
	
	public void departamentoStateListener(){		
		if(departamento != null){			
			municipios=personaDAO.obtnMunicipios(departamento);
			municipios.add(0, new SelectItem(null, "Seleccione..."));
		}
		else			
			municipios=null;
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
		municipios=null;		
	}

	public void next(){		
		lastMovementWasNext=true;
		RequestContext.getCurrentInstance().execute("PF('wiz').next();");
		
	}
	
	public void back(){
		lastMovementWasNext=false;
		RequestContext.getCurrentInstance().execute("PF('wiz').back();");
		
	}
	
	public String obtnDescripcion(ArrayList<SelectItem> items, Integer idItem){
		if(idItem!=null){
			for(SelectItem item:items){
				if(item.getValue()==idItem)
					return item.getLabel();			
			}
		}					
		return "";
	}	
			
	public Pqrsf getPqrsf() {
		return pqrsf;
	}

	public void setPqrsf(Pqrsf pqrsf) {
		this.pqrsf = pqrsf;
	}

	public boolean getIsLastStep() {
		return isLastStep;
	}

	public void setIsLastStep(boolean isLastStep) {
		this.isLastStep = isLastStep;
	}


	public ArrayList<SelectItem> getTiposPqrsf() {
		return tiposPqrsf;
	}

	public void setTiposPqrsf(ArrayList<SelectItem> tiposPqrsf) {
		this.tiposPqrsf = tiposPqrsf;
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

	public ArrayList<SelectItem> getDepartamentos() {
		return departamentos;
	}

	public void setDepartamentos(ArrayList<SelectItem> departamentos) {
		this.departamentos = departamentos;
	}

	public ArrayList<SelectItem> getMunicipios() {
		return municipios;
	}

	public void setMunicipios(ArrayList<SelectItem> municipios) {
		this.municipios = municipios;
	}

	public Integer getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Integer departamento) {
		this.departamento = departamento;
	}
		
}
