package co.edu.unicauca.pqrsfv2.control;

import java.io.Serializable;
import java.util.ArrayList;

import javax.ejb.LocalBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import co.edu.unicauca.pqrsfv2.dao.PqrsfDAO;
import co.edu.unicauca.pqrsfv2.modelo.Pqrsf;

@SessionScoped
@LocalBean
@Named
public class RadicarPqrsfControl implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private PqrsfDAO pqrsfDAO;
	private ArrayList<Pqrsf> pqrsfNoRadicadas;	
	public RadicarPqrsfControl(){
		pqrsfNoRadicadas=new ArrayList<>();
	}
	
	public void cargarPqrsfNoRadicadas(){
		pqrsfNoRadicadas=pqrsfDAO.obtnNoRadicadas();
	}

	public ArrayList<Pqrsf> getPqrsfNoRadicadas() {
		return pqrsfNoRadicadas;
	}

	public void setPqrsfNoRadicadas(ArrayList<Pqrsf> pqrsfNoRadicadas) {
		this.pqrsfNoRadicadas = pqrsfNoRadicadas;
	}
}
