package co.edu.unicauca.pqrsfv2.modelo;

import java.io.Serializable;
import java.util.Date;

public class Orden implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private Pqrsf pqrsf;
	private Funcionario funcionario;
	private Usuario usuario;
	private Date fechaAsignacion;
	private int estado;
	private Date fechaCierre;
	
	public Orden(){
		funcionario=new Funcionario();			
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Pqrsf getPqrsf() {
		return pqrsf;
	}
	public void setPqrsf(Pqrsf pqrsf) {
		this.pqrsf = pqrsf;
	}
	public Funcionario getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Date getFechaAsignacion() {
		return fechaAsignacion;
	}
	public void setFechaAsignacion(Date fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public Date getFechaCierre() {
		return fechaCierre;
	}
	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}	
}
