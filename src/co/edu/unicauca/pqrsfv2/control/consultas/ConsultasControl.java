package co.edu.unicauca.pqrsfv2.control.consultas;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.ejb.LocalBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import co.edu.unicauca.pqrsfv2.dao.OrdenDAO;
import co.edu.unicauca.pqrsfv2.modelo.Orden;
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
	OrdenDAO ordenDAO;
	// para la vista de todasPQRSF
	private Integer diasParaVencimiento;
	private ArrayList<Orden> ordenes;
	private String selectedAction;
	private String tituloConsulta;
	// para la vista de Buscar PQRSF
	private String identificacionPersona;
	private String codigoPqrsf;
	private Orden orden;
	// para los indicadores del index
	private int numeroVencidas;
	private int numeroPqrsfProximasVencerse;
	private int numeroPqrsfSinRadicar;
	private int numeroPqrsfSinDireccionar;
	private int numeroPqrsfAtendidas;
	private int numeroPqrsfEnTramite;
	private int numeroPqrsfPendientes;
	
	
	public ConsultasControl(){
		selectedAction="Ver";
		orden=null;
	}

	public void cargarTodasPqrf(){		
		ordenes=ordenDAO.obtnTodasOrdenes(diasParaVencimiento);		
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
		boolean success=ordenDAO.cargarIndicadores(numeroVencidas, numeroPqrsfProximasVencerse, numeroPqrsfSinRadicar, numeroPqrsfSinDireccionar, 
									numeroPqrsfAtendidas, numeroPqrsfEnTramite, numeroPqrsfPendientes);
		if(success==false){
			numeroVencidas=0;
			numeroPqrsfProximasVencerse=0;
			numeroPqrsfSinRadicar=0;
			numeroPqrsfSinDireccionar=0;
			numeroPqrsfAtendidas=0;
			numeroPqrsfEnTramite=0;
			numeroPqrsfPendientes=0;
		}
	}
	
	public void obtnOrden(){		
		orden=ordenDAO.obtnOrden(codigoPqrsf, identificacionPersona);		
	}
	
	public String formatearFecha(Date fecha){
		SimpleDateFormat sdf=new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("es","es_CO"));
		return sdf.format(fecha);
	}
		
	public ArrayList<Orden> getOrdenes() {
		return ordenes;
	}

	public void setOrdenes(ArrayList<Orden> ordenes) {
		this.ordenes = ordenes;
	}

	public String getSelectedAction() {
		return selectedAction;
	}

	public void setSelectedAction(String selectedAction) {
		this.selectedAction = selectedAction;
	}

	public Integer getDiasParaVencimiento() {
		return diasParaVencimiento;
	}

	public void setDiasParaVencimiento(Integer diasParaVencimiento) {
		this.diasParaVencimiento = diasParaVencimiento;
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

	public Orden getOrden() {
		return orden;
	}

	public void setOrden(Orden orden) {
		this.orden = orden;
	}

	public String getTituloConsulta() {
		return tituloConsulta;
	}

	public void setTituloConsulta(String tituloConsulta) {
		this.tituloConsulta = tituloConsulta;
	}

	public int getNumeroVencidas() {
		return numeroVencidas;
	}

	public void setNumeroVencidas(int numeroVencidas) {
		this.numeroVencidas = numeroVencidas;
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
}