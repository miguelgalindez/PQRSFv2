package co.edu.unicauca.pqrsfv2.dao;

import java.io.Serializable;
import java.util.HashMap;
import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

@Stateless
@LocalBean
@Named
public class ValoresDAO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	PersonaDAO personaDAO;
	@Inject
	PqrsfDAO pqrsfDAO;
	@Inject
	DependenciaDAO dependenciaDAO;
	private HashMap<Integer, String> tiposPqrsf;	
	private HashMap<Integer, String> mediosRecepcion;
	private HashMap<Integer, String> tiposIdentificacion;
	private HashMap<Integer, String> tiposPersona;
	private HashMap<Integer, String> departamentos;
	private HashMap<Integer, String> dependencias;
	private HashMap<Integer, String> estados;
	
	@PostConstruct
	private void init() {
		tiposIdentificacion=personaDAO.obtnTiposIdentificacion();
		tiposPersona=personaDAO.obtnTiposPersona();					
		mediosRecepcion=pqrsfDAO.obtnMediosRecepcion();		
		tiposPqrsf=pqrsfDAO.obtnTiposPqrsf();
		departamentos=personaDAO.obtnDepartamentos();
		dependencias=dependenciaDAO.obtnDependencias();		
		cargarEstados();
	}
	
	private void cargarEstados() {
		estados=new HashMap<>();
		estados.put(0, "PENDIENTE");
		estados.put(1, "EN TRAMITE");
		estados.put(2, "ATENDIDA");		
	}

	public HashMap<Integer, String> obtnMunicipios(Integer idDepartamento){
		if(idDepartamento!=null)
			return personaDAO.obtnMunicipios(idDepartamento);
		else
			return null;
	}
	
	public String obtnDescripcion(HashMap<Integer, String> valores, Integer idValor){
		return valores.get(idValor);
	}
	
	public String obtnNombreMunicipio(Integer idMunicipio){
		return personaDAO.obtnNombreMunicipio(idMunicipio);
	}
	
	public HashMap<Integer, String> getTiposPqrsf() {
		return tiposPqrsf;
	}

	public void setTiposPqrsf(HashMap<Integer, String> tiposPqrsf) {
		this.tiposPqrsf = tiposPqrsf;
	}

	public HashMap<Integer, String> getMediosRecepcion() {
		return mediosRecepcion;
	}

	public void setMediosRecepcion(HashMap<Integer, String> mediosRecepcion) {
		this.mediosRecepcion = mediosRecepcion;
	}

	public HashMap<Integer, String> getTiposIdentificacion() {
		return tiposIdentificacion;
	}

	public void setTiposIdentificacion(HashMap<Integer, String> tiposIdentificacion) {
		this.tiposIdentificacion = tiposIdentificacion;
	}

	public HashMap<Integer, String> getTiposPersona() {
		return tiposPersona;
	}

	public void setTiposPersona(HashMap<Integer, String> tiposPersona) {
		this.tiposPersona = tiposPersona;
	}

	public HashMap<Integer, String> getDepartamentos() {
		return departamentos;
	}

	public void setDepartamentos(HashMap<Integer, String> departamentos) {
		this.departamentos = departamentos;
	}

	public HashMap<Integer, String> getDependencias() {
		return dependencias;
	}

	public void setDependencias(HashMap<Integer, String> dependencias) {
		this.dependencias = dependencias;
	}

	public HashMap<Integer, String> getEstados() {
		return estados;
	}

	public void setEstados(HashMap<Integer, String> estados) {
		this.estados = estados;
	}
	
}
