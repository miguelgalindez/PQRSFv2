package co.edu.unicauca.pqrsfv2.modelo;

import java.io.Serializable;
import java.sql.Date;

public class Radicado implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private Date fecha;
	
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
	
	
}
