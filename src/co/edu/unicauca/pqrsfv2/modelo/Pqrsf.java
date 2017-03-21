package co.edu.unicauca.pqrsfv2.modelo;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Pqrsf implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String codigo;
	private Persona persona;
	@NotNull
	private Integer tipoPqrsf;
	@NotNull
	private Integer medioRecepcion;
	@NotNull @Size(min=5)
	private String asunto;
	@NotNull @Size(min=10)
	private String descripcion;
	
	
	public Integer getTipoPqrsf() {
		return tipoPqrsf;
	}
	public void setTipoPqrsf(Integer tipoPqrsf) {
		this.tipoPqrsf = tipoPqrsf;
	}
	public Integer getMedioRecepcion() {
		return medioRecepcion;
	}
	public void setMedioRecepcion(Integer medioRecepcion) {
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
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
}
