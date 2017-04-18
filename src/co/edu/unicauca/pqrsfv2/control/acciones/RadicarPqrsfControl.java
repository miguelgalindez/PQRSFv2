package co.edu.unicauca.pqrsfv2.control.acciones;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.ejb.LocalBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import co.edu.unicauca.pqrsfv2.control.ModalRespuestaControl;
import co.edu.unicauca.pqrsfv2.control.NavigationControl;
import co.edu.unicauca.pqrsfv2.control.consultas.ConsultasControl;
import co.edu.unicauca.pqrsfv2.dao.PqrsfDAO;
import co.edu.unicauca.pqrsfv2.dao.ValoresDAO;
import co.edu.unicauca.pqrsfv2.modelo.Pqrsf;
import co.edu.unicauca.pqrsfv2.modelo.Radicado;
import co.edu.unicauca.pqrsfv2.util.DocxManipulator;

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
	@Inject
	ValoresDAO valoresDAO;
	@Inject
	DocxManipulator docxManipulator;
	@Inject
	ConsultasControl consultasControl;
	
	private ArrayList<Pqrsf> pqrsfNoRadicadas;
	private Pqrsf selectedPqrsf;
	private String selectedAction;
	private Radicado radicado;
	
	private String tempDir;

	
	public void changeSelectedAction(String action){
		selectedAction=action;
		if(action.equals("Ver")){
			consultasControl.setCodigoPqrsf(selectedPqrsf.getCodigo());
			consultasControl.setIdentificacionPersona(selectedPqrsf.getPersona().getIdentificacion());
			consultasControl.obtnOrden();
		}
	}
			
	public RadicarPqrsfControl(){
		pqrsfNoRadicadas=new ArrayList<>();
		tempDir="";
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
			modalRespuestaControl.configurar("Operación exitosa", "La PQRSF ha sido radicada satisfactoriamente");						
			pqrsfNoRadicadas.remove(selectedPqrsf);
			inicializarDatos();			
		}
		else
			modalRespuestaControl.configurar("Error", "No se pudo radicar la PQRSF. Si el problema persiste, por favor comunicarse con la DivTIC.");
		
		ctx.execute("$('#modalRespuesta').modal('toggle');");
		
	}
	
	public DefaultStreamedContent imprimirPQRSF(){		
		Map<String,String> datos=new HashMap<>();
		SimpleDateFormat sdf=new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("es","es_CO"));		
		datos.put("tipperDescripcion", valoresDAO.obtnDescripcion(valoresDAO.getTiposPersona(), selectedPqrsf.getPersona().getTipoPersona()));
		datos.put("tipideDescripcion", valoresDAO.obtnDescripcion(valoresDAO.getTiposIdentificacion(), selectedPqrsf.getPersona().getTipoIdentificacion()));
		datos.put("perIdentificacion", selectedPqrsf.getPersona().getIdentificacion());
		datos.put("perNombres", selectedPqrsf.getPersona().getNombres());
		datos.put("perApellidos", selectedPqrsf.getPersona().getApellidos());
		datos.put("perCorreo", selectedPqrsf.getPersona().getEmail());
		datos.put("perTelefono", selectedPqrsf.getPersona().getTelefono());
		datos.put("perCelular", selectedPqrsf.getPersona().getCelular());
		datos.put("perDireccion", selectedPqrsf.getPersona().getDireccion());
		datos.put("munNombre", selectedPqrsf.getPersona().getMunicipio().getNombre());
		datos.put("deptoNombre", valoresDAO.obtnDescripcion(valoresDAO.getDepartamentos(), selectedPqrsf.getPersona().getMunicipio().getIdDepartamento()));
		datos.put("pqrsfCodigo", selectedPqrsf.getCodigo());
		datos.put("pqrsfFechaCreacion", sdf.format(selectedPqrsf.getFechaCreacion()));
		datos.put("tipPqrsfDescripcion", valoresDAO.obtnDescripcion(valoresDAO.getTiposPqrsf(), selectedPqrsf.getTipoPqrsf()));
		datos.put("medDescripcion", valoresDAO.obtnDescripcion(valoresDAO.getMediosRecepcion(), selectedPqrsf.getMedioRecepcion()));
		datos.put("pqrsfAsunto", selectedPqrsf.getAsunto());
		datos.put("pqrsfDescripcion", selectedPqrsf.getDescripcion());
		
		tempDir=docxManipulator.generateDocx("radicarPqrsfImprimir.docx", datos);				
		
		if(tempDir!=null){									
			try {
				InputStream stream= new FileInputStream(tempDir+"radicarPqrsfImprimir.docx");
				DefaultStreamedContent file=new DefaultStreamedContent(stream, "application/vnd.openxmlformats-officedocument.wordprocessingml.document", selectedPqrsf.getCodigo()+".docx");							
				return file;
			} catch (FileNotFoundException e) {		
				e.printStackTrace();
			}				      
		}		
		return null;        
	}
	
	public void deleteTempDir(){
		if(tempDir!=null && tempDir.equals("")==false){
			docxManipulator.deleteTempData(new File(tempDir));
			tempDir="";
		}
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
