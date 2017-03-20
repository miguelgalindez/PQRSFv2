package co.edu.unicauca.pqrsfv2.logica;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

import co.edu.unicauca.pqrsfv2.dao.PersonaDAO;

@Stateless
@LocalBean
public class PersonaBO {
	
	@EJB
	PersonaDAO personaDAO;
	
	public ArrayList<SelectItem> obtnTiposPersona(){	
		return personaDAO.obtnTiposPersona();
	}
	
	public ArrayList<SelectItem> obtnTiposIdentificacion(){		
		return personaDAO.obtnTiposIdentificacion();		
	}		
}
