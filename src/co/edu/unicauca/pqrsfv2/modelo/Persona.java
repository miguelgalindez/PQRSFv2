package co.edu.unicauca.pqrsfv2.modelo;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Persona implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NotNull @Max(value=99)
	private Integer tipoPersona;
	@NotNull @Max(value=99)
	private Integer tipoIdentificacion;
	@NotNull @Size(min=4, max=32)
	private String identificacion;
	@NotNull @Size(min=2, max=64)
	private String nombres;
	@NotNull @Size(min=4, max=64)
	private String apellidos;
	
	@NotNull @Size(max=64) @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
	             message="Correo electr�nico no v�lido")
	private String email;
	@Size(max=32)	
	private String telefono;
	@NotNull @Size(min=10, max=16, message="N�mero de celular no v�lido. Debe tener de 10 a 16 d�gitos.")
	private String celular;
	
	@NotNull @Size(min=4, max=128)
	private String direccion;
	
	@NotNull @Max(value=9999)
	private Integer municipio;
	
	public Persona(){
		municipio=null;
		tipoPersona=tipoIdentificacion=null;
	}
			
	public Integer getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(Integer tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public Integer getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	public void setTipoIdentificacion(Integer tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Integer getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Integer municipio) {
		this.municipio = municipio;
	}
	
	
}
