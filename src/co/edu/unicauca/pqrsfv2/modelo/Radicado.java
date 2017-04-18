package co.edu.unicauca.pqrsfv2.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

public class Radicado implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotNull
	private String id;
	@NotNull
	private Date fecha;	
	private Usuario usuarioQueRadica;
	
	public Radicado(){
		id=null;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Usuario getUsuarioQueRadica() {
		return usuarioQueRadica;
	}
	public void setUsuarioQueRadica(Usuario usuarioQueRadica) {
		this.usuarioQueRadica = usuarioQueRadica;
	}		
}
