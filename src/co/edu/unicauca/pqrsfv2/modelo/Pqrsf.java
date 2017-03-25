package co.edu.unicauca.pqrsfv2.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Pqrsf implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Size(max=8)
	private String codigo;
	private Persona persona;
	@NotNull @Max(value=9)
	private Integer tipoPqrsf;
	@NotNull @Max(value=9)
	private Integer medioRecepcion;
	@NotNull @Size(min=5, max=256)
	private String asunto;
	@NotNull @Size(min=10, max=1024)
	private String descripcion;
	
	private Date fechaCreacion;
	private Date fechaVencimiento;
	private Radicado radicado;
	
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
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}
	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
	public Radicado getRadicado() {
		return radicado;
	}
	public void setRadicado(Radicado radicado) {
		this.radicado = radicado;
	}
	
}
