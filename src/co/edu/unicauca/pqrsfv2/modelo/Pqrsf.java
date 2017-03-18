package co.edu.unicauca.pqrsfv2.modelo;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class Pqrsf implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Persona solicitante;
	@NotNull
	private String tipoSolicitud;
	@NotNull
	private String medioRecepcion;
	@NotNull
	private String asunto;
	@NotNull
	private String descripcion;
	
	public Persona getSolicitante() {
		return solicitante;
	}
	public void setSolicitante(Persona solicitante) {
		this.solicitante = solicitante;
	}
	public String getTipoSolicitud() {
		return tipoSolicitud;
	}
	public void setTipoSolicitud(String tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}
	public String getMedioRecepcion() {
		return medioRecepcion;
	}
	public void setMedioRecepcion(String medioRecepcion) {
		this.medioRecepcion = medioRecepcion;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}			
}
