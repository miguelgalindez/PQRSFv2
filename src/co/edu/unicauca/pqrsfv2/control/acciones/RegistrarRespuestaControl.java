package co.edu.unicauca.pqrsfv2.control.acciones;

import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import co.edu.unicauca.pqrsfv2.modelo.Orden;

@SessionScoped
@Named
public class RegistrarRespuestaControl implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Orden> ordenesAtendiendo;

	public ArrayList<Orden> getOrdenesAtendiendo() {
		return ordenesAtendiendo;
	}

	public void setOrdenesAtendiendo(ArrayList<Orden> ordenesAtendiendo) {
		this.ordenesAtendiendo = ordenesAtendiendo;
	}

	
	
}
