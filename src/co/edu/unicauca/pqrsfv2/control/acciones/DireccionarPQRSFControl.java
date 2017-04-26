package co.edu.unicauca.pqrsfv2.control.acciones;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

import co.edu.unicauca.pqrsfv2.control.ModalRespuestaControl;
import co.edu.unicauca.pqrsfv2.control.NavigationControl;
import co.edu.unicauca.pqrsfv2.control.consultas.ConsultasControl;
import co.edu.unicauca.pqrsfv2.dao.DependenciaDAO;
import co.edu.unicauca.pqrsfv2.dao.PqrsfDAO;
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
	@Inject
	private NavigationControl navigationControl;
	@Inject
	private ModalRespuestaControl modalRespuestaControl;
	@Inject
	private ConsultasControl consultasControl;
	private ArrayList<Pqrsf> pqrsfNoDireccionadas;
	private Pqrsf selectedPqrsf;
	private String selectedAction;
	
	private String identificacionFuncionarioResponsable;
	private Integer idDependenciaSeleccionada;
	
	public DireccionarPQRSFControl(){
		pqrsfNoDireccionadas=new ArrayList<>();
		inicializarDatos();
	}
	
	public void cargarPQRSFNoDireccionadas(){
		pqrsfNoDireccionadas=pqrsfDAO.obtnNoDireccionadas();
	}

	public void changeSelectedAction(String action){
		selectedAction=action;		
	}
	
	public HashMap<Integer, String> obtnFuncionarios(Integer idDependencia){
		if (idDependencia==null)
			return new HashMap<Integer, String>();
		else
			return dependenciaDAO.obtnFuncionarios(idDependencia);
	}
	
	private void inicializarDatos(){		
		selectedAction="Direccionar";			
		idDependenciaSeleccionada=null;
	}
	
	public void direccionarPQRSF(){
		RequestContext ctx=RequestContext.getCurrentInstance();				
		boolean success=pqrsfDAO.direccionarPQRSF(selectedPqrsf, identificacionFuncionarioResponsable, navigationControl.getUsuarioAutenticado().getUsername());
		modalRespuestaControl.operacionExitosa(success);
		
		if(success){
			ctx.execute("PF('dialog').hide();");			
			modalRespuestaControl.configurar("Operaci�n exitosa", "La PQRSF ha sido direccionada satisfactoriamente");
			// Para que funcionen las Tablas de las Consultas (todas, proximas y vencidas)
			selectedPqrsf.setEstado(1);
			pqrsfNoDireccionadas.remove(selectedPqrsf);
			inicializarDatos();			
		}
		else{
			// Para que funcionen las Tablas de las Consultas (todas, proximas y vencidas)
			selectedPqrsf.setEstado(0);
			modalRespuestaControl.configurar("Error", "No se pudo direccionar la PQRSF. Si el problema persiste, por favor comunicarse con la DivTIC.");
		}
		
		ctx.execute("$('#modalRespuesta').modal('toggle');");			
		
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
		if(selectedAction.equals("Ver")){
			consultasControl.setCodigoPqrsf(selectedPqrsf.getCodigo());
			consultasControl.obtnOrdenPorCodigoPqrsf();
		}
	}

	public Integer getIdDependenciaSeleccionada() {
		return idDependenciaSeleccionada;
	}

	public void setIdDependenciaSeleccionada(Integer idDependenciaSeleccionada) {
		this.idDependenciaSeleccionada = idDependenciaSeleccionada;
	}

	public String getIdentificacionFuncionarioResponsable() {
		return identificacionFuncionarioResponsable;
	}

	public void setIdentificacionFuncionarioResponsable(String identificacionFuncionarioResponsable) {
		this.identificacionFuncionarioResponsable = identificacionFuncionarioResponsable;
	}
	
	
}
