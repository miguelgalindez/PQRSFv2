package co.edu.unicauca.pqrsfv2.control.acciones;

import java.io.Serializable;
import java.util.ArrayList;
import javax.ejb.LocalBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

import co.edu.unicauca.pqrsfv2.control.ModalRespuestaControl;
import co.edu.unicauca.pqrsfv2.control.NavigationControl;
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
	@Inject
	NavigationControl navigationControl;
	@Inject
	ModalRespuestaControl modalRespuestaControl;
	private ArrayList<Pqrsf> pqrsfNoRadicadas;
	private Pqrsf selectedPqrsf;
	private String selectedAction;
	private Radicado radicado;
	
	public void changeSelectedAction(String action){
		selectedAction=action;
	}
	
	public RadicarPqrsfControl(){
		pqrsfNoRadicadas=new ArrayList<>();
		inicializarDatos();		
	}
	
	public void cargarPqrsfNoRadicadas(){
		pqrsfNoRadicadas=pqrsfDAO.obtnNoRadicadas();
	}
	
	public void radicarPQRSF(){
		RequestContext ctx=RequestContext.getCurrentInstance();
		radicado.setUsuarioQueRadica(navigationControl.getUsuarioAutenticado());
		selectedPqrsf.setRadicado(radicado);
		boolean success=pqrsfDAO.guardarRadicado(selectedPqrsf);
		modalRespuestaControl.operacionExitosa(success);
		if(success){
			ctx.execute("PF('dialog').hide();");			
			modalRespuestaControl.configurar("Operaci�n exitosa", "La PQRSF ha sido radicada satisfactoriamente");						
			pqrsfNoRadicadas.remove(selectedPqrsf);
			inicializarDatos();			
		}
		else
			modalRespuestaControl.configurar("Error", "No se pudo radicar la PQRSF. Si el problema persiste, por favor comunicarse con la DivTIC.");
		
		ctx.execute("$('#modalRespuesta').modal('toggle');");
		
	}
	
	private void inicializarDatos() {
		selectedPqrsf=null;
		radicado=new Radicado();
		selectedAction="Radicar";
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