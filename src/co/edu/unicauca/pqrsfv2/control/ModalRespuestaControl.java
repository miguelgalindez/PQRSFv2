package co.edu.unicauca.pqrsfv2.control;

import javax.ejb.LocalBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;


@Named("modalRespuestaControl")
@LocalBean
@SessionScoped
public class ModalRespuestaControl implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mensaje;
	private String titulo;
	private String proximoDestino;
	
	// modal-danger modal-success
	private String tipo;
	
	public ModalRespuestaControl(){
		tipo="modal-success";
		proximoDestino="";
	}
	
	public void cerrar(){
		if(proximoDestino.equals("")==false){
			// Redireccionar
		}		
	}
	
	public void operacionExitosa(boolean success) {
		if(success)
			tipo="modal-success";
		else
			tipo="modal-danger";			
	}
	
	public void configurar(String titulo, String mensaje){
		this.titulo=titulo;
		this.mensaje=mensaje;
	}
	
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
