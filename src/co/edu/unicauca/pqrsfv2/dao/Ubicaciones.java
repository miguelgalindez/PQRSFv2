package co.edu.unicauca.pqrsfv2.dao;

import java.util.HashMap;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class Ubicaciones {
	
	@Inject
	PersonaDAO personaDAO;
	@Inject
	PqrsfDAO pqrsfDAO;
	
	private HashMap<Integer, String> todosDepartamentos;
	private HashMap<Integer, String> todosMunicipios;
	 
	@PostConstruct
	private void init() {
		todosMunicipios=personaDAO.obtnTodosMunicipios();
		todosDepartamentos=personaDAO.obtnDepartamentos();
	}
	
	public String obtnNombreMunicipio(Integer idMunicipio){
		return todosMunicipios.get(idMunicipio);
	}
	
	public String obtnNombreDepartamento(Integer idDepartamento){
		return todosDepartamentos.get(idDepartamento);
	}

	public HashMap<Integer, String> getTodosDepartamentos() {
		return todosDepartamentos;
	}

	public void setTodosDepartamentos(HashMap<Integer, String> todosDepartamentos) {
		this.todosDepartamentos = todosDepartamentos;
	}

	public HashMap<Integer, String> getTodosMunicipios() {
		return todosMunicipios;
	}

	public void setTodosMunicipios(HashMap<Integer, String> todosMunicipios) {
		this.todosMunicipios = todosMunicipios;
	}
}
