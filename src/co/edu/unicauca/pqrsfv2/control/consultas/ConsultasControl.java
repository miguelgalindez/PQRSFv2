package co.edu.unicauca.pqrsfv2.control.consultas;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import javax.ejb.LocalBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import co.edu.unicauca.pqrsfv2.control.acciones.DireccionarPQRSFControl;
import co.edu.unicauca.pqrsfv2.control.acciones.RadicarPqrsfControl;
import co.edu.unicauca.pqrsfv2.dao.PqrsfDAO;
import co.edu.unicauca.pqrsfv2.modelo.Pqrsf;

@SessionScoped
@LocalBean
@Named
public class ConsultasControl implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	PqrsfDAO pqrsfDAO;
	// para la vista de todasPQRSF
	@Inject
	RadicarPqrsfControl radicarPqrsfControl; 
	@Inject
	DireccionarPQRSFControl direccionarPqrsfControl; 
	private ArrayList<Pqrsf> pqrsfs;
	private String selectedAction;
	private String tituloConsulta;
	private boolean esConsultaPorEstado;
	private int estadoSeleccionado;
	private boolean esConsultaPorVencimiento;
	private boolean esConsultaDeTodas;
	private Pqrsf selectedPqrsf;
	
	
	// para la vista de Buscar PQRSF
	private String identificacionPersona;
	private String codigoPqrsf;
	private Pqrsf pqrsf;
	// para los indicadores del index
	private int numeroPqrsfVencidas;
	private int numeroPqrsfProximasVencerse;
	private int numeroPqrsfSinRadicar;
	private int numeroPqrsfSinDireccionar;
	private int numeroPqrsfAtendidas;
	private int numeroPqrsfEnTramite;
	private int numeroPqrsfPendientes;
	
	
	public ConsultasControl(){
		selectedAction="Ver";
		pqrsf=null;
	}
	
	
	public void cargarPqrsfsPorVencimiento(int diasParaVencimiento){
		this.esConsultaPorVencimiento();		
		pqrsfs=pqrsfDAO.obtnPqrsfsPorVencimiento(diasParaVencimiento);
		if(diasParaVencimiento==-1)
			this.setTituloConsulta("PQRSFs vencidas");
		else
			this.setTituloConsulta("PQRSFs próximas a vencerse");
	}
	
	public void cargarPqrsfsPorEstado(Integer estado){
		this.esConsultaPorEstado();
		if(estado!=null)
			estadoSeleccionado=estado;		
		pqrsfs=pqrsfDAO.obtnPqrsfsPorEstado(estadoSeleccionado);
		switch (estadoSeleccionado) {
			case 0:
				this.setTituloConsulta("PQRSFs Pendientes");
				break;
			case 1:
				this.setTituloConsulta("PQRSFs en Trámite");
				break;
			case 2:
				this.setTituloConsulta("PQRSFs Atendidas");
				break;
		}
	}	
	
	public void obtnTodasPqrsfs(){
		this.esConsultaDeTodas();
		this.setTituloConsulta("Todas las PQRSFs");
		pqrsfs=pqrsfDAO.obtnTodasPqrsfs();
	}
	
	public void changeSelectedAction(String action){
		selectedAction=action;
	}
	
	public Integer obtnDiasParaVencimiento(Pqrsf pqrsf){
		Date vencimiento=pqrsf.getFechaVencimiento();
		if(vencimiento!=null)
			return (int)((vencimiento.getTime() - new Date().getTime()) / (1000 * 60 * 60 * 24));																
		else
			return null;		
	}
	
	public String obtnLeyendaDiasParaVencimiento(Pqrsf pqrsf){
		Date vencimiento=pqrsf.getFechaVencimiento();
		if(vencimiento!=null){
			int diasRestantes=(int)((vencimiento.getTime() - new Date().getTime()) / (1000 * 60 * 60 * 24));
			if (diasRestantes<0)
				return "VENCIDA";			
			else{
				if(diasRestantes==0)
					return "VENCE HOY";
				else{
					if(diasRestantes==1)
						return "En 1 día";
					else
						return "En "+diasRestantes+" días";
				}
			}
		}
		else
			return "";					
	}
	
	public void cargarIndicadores(){
		HashMap<String, Integer> indicadores=pqrsfDAO.cargarIndicadores();
		if(indicadores!=null){
			
			numeroPqrsfVencidas=indicadores.get("numeroPqrsfVencidas");
			numeroPqrsfProximasVencerse=indicadores.get("numeroPqrsfProximasVencerse");
			numeroPqrsfSinRadicar=indicadores.get("numeroPqrsfSinRadicar");
			numeroPqrsfSinDireccionar=indicadores.get("numeroPqrsfSinDireccionar");
			numeroPqrsfAtendidas=indicadores.get("numeroPqrsfAtendidas");
			numeroPqrsfEnTramite=indicadores.get("numeroPqrsfEnTramite");
			numeroPqrsfPendientes=indicadores.get("numeroPqrsfPendientes");
		}
		else{
			numeroPqrsfVencidas=0;
			numeroPqrsfProximasVencerse=0;
			numeroPqrsfSinRadicar=0;
			numeroPqrsfSinDireccionar=0;
			numeroPqrsfAtendidas=0;
			numeroPqrsfEnTramite=0;
			numeroPqrsfPendientes=0;
		}
	}
	
	public void obtnPqrsf(){		
		pqrsf=pqrsfDAO.buscarPqrsf(codigoPqrsf, identificacionPersona);		
	}
	
	public void obtnPqrsfPorCodigo(){		
		pqrsf=pqrsfDAO.buscarPqrsf(codigoPqrsf, null);		
	}
	
	public String formatearFecha(Date fecha){
		if(fecha!=null){
			SimpleDateFormat sdf=new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("es","es_CO"));
			return sdf.format(fecha);
		}
		return "";		
	}		

	public ArrayList<Pqrsf> getPqrsfs() {
		return pqrsfs;
	}

	public void setPqrsfs(ArrayList<Pqrsf> pqrsfs) {
		this.pqrsfs = pqrsfs;
	}

	public String getSelectedAction() {
		return selectedAction;
	}

	public void setSelectedAction(String selectedAction) {
		this.selectedAction = selectedAction;
	}

	public String getIdentificacionPersona() {
		return identificacionPersona;
	}

	public void setIdentificacionPersona(String identificacionPersona) {
		this.identificacionPersona = identificacionPersona;
	}

	public String getCodigoPqrsf() {
		return codigoPqrsf;
	}

	public void setCodigoPqrsf(String codigoPqrsf) {
		this.codigoPqrsf = codigoPqrsf;
	}

	public Pqrsf getPqrsf() {
		return pqrsf;
	}

	public void setPqrsf(Pqrsf pqrsf) {
		this.pqrsf = pqrsf;
	}


	public String getTituloConsulta() {
		return tituloConsulta;
	}

	public void setTituloConsulta(String tituloConsulta) {
		this.tituloConsulta = tituloConsulta;
	}

	
	public int getNumeroPqrsfVencidas() {
		return numeroPqrsfVencidas;
	}

	public void setNumeroPqrsfVencidas(int numeroPqrsfVencidas) {
		this.numeroPqrsfVencidas = numeroPqrsfVencidas;
	}

	public int getNumeroPqrsfProximasVencerse() {
		return numeroPqrsfProximasVencerse;
	}

	public void setNumeroPqrsfProximasVencerse(int numeroPqrsfProximasVencerse) {
		this.numeroPqrsfProximasVencerse = numeroPqrsfProximasVencerse;
	}

	public int getNumeroPqrsfSinRadicar() {
		return numeroPqrsfSinRadicar;
	}

	public void setNumeroPqrsfSinRadicar(int numeroPqrsfSinRadicar) {
		this.numeroPqrsfSinRadicar = numeroPqrsfSinRadicar;
	}

	public int getNumeroPqrsfSinDireccionar() {
		return numeroPqrsfSinDireccionar;
	}

	public void setNumeroPqrsfSinDireccionar(int numeroPqrsfSinDireccionar) {
		this.numeroPqrsfSinDireccionar = numeroPqrsfSinDireccionar;
	}

	public int getNumeroPqrsfAtendidas() {
		return numeroPqrsfAtendidas;
	}

	public void setNumeroPqrsfAtendidas(int numeroPqrsfAtendidas) {
		this.numeroPqrsfAtendidas = numeroPqrsfAtendidas;
	}

	public int getNumeroPqrsfEnTramite() {
		return numeroPqrsfEnTramite;
	}

	public void setNumeroPqrsfEnTramite(int numeroPqrsfEnTramite) {
		this.numeroPqrsfEnTramite = numeroPqrsfEnTramite;
	}

	public int getNumeroPqrsfPendientes() {
		return numeroPqrsfPendientes;
	}

	public void setNumeroPqrsfPendientes(int numeroPqrsfPendientes) {
		this.numeroPqrsfPendientes = numeroPqrsfPendientes;
	}


	public boolean getEsConsultaPorEstado() {
		return esConsultaPorEstado;
	}


	public void esConsultaPorEstado() {
		esConsultaPorEstado=true;
		esConsultaDeTodas=esConsultaPorVencimiento=false;
	}


	public boolean getEsConsultaPorVencimiento() {
		return esConsultaPorVencimiento;
	}


	public void esConsultaPorVencimiento() {
		esConsultaPorVencimiento=true;
		esConsultaDeTodas=esConsultaPorEstado=false;
	}


	public boolean getEsConsultaDeTodas() {
		return esConsultaDeTodas;
	}

	public void esConsultaDeTodas() {
		esConsultaDeTodas=true;
		esConsultaPorEstado=esConsultaPorVencimiento=false;
	}

	public int getEstadoSeleccionado() {
		return estadoSeleccionado;
	}

	public void setEstadoSeleccionado(int estadoSeleccionado) {
		this.estadoSeleccionado = estadoSeleccionado;
	}

	public Pqrsf getSelectedPqrsf() {
		return selectedPqrsf;
	}

	public void setSelectedPqrsf(Pqrsf selectedPqrsf) {
		this.selectedPqrsf = selectedPqrsf;
		
		switch(selectedAction){
			case "Ver":
				codigoPqrsf=selectedPqrsf.getCodigo();				
				this.obtnPqrsfPorCodigo();
				break;					
			
			case "Radicar":
				radicarPqrsfControl.setSelectedPqrsf(selectedPqrsf);				
				break;
			
			case "Direccionar":
				direccionarPqrsfControl.setSelectedPqrsf(selectedPqrsf);
				break;				
		}
	}
	
	public DefaultStreamedContent imprimirPqrsf(){
		radicarPqrsfControl.setSelectedPqrsf(selectedPqrsf);
		return radicarPqrsfControl.imprimirPQRSF();
	}	
}
