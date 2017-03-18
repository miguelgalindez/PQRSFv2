package co.edu.unicauca.pqrsfv2.logica;

import java.util.ArrayList;

import javax.faces.model.SelectItem;

public class PersonaBO {
	
	public ArrayList<SelectItem> obtnTiposPersona(){
		ArrayList<SelectItem> tiposPersona=new ArrayList<>();
		
		SelectItem tipoPersona=new SelectItem();
		tipoPersona.setValue("ESTUDIANTE");
		tipoPersona.setLabel("ESTUDIANTE");
		tiposPersona.add(tipoPersona);
		
		tipoPersona=new SelectItem();
		tipoPersona.setValue("DOCENTE");
		tipoPersona.setLabel("DOCENTE");
		tiposPersona.add(tipoPersona);
		
		tipoPersona=new SelectItem();
		tipoPersona.setValue("ADMINISTRATIVO");
		tipoPersona.setLabel("ADMINISTRATIVO");
		tiposPersona.add(tipoPersona);
		
		tipoPersona=new SelectItem();
		tipoPersona.setValue("CONTRATISTA");
		tipoPersona.setLabel("CONTRATISTA");
		tiposPersona.add(tipoPersona);
		
		return tiposPersona;
	}
	
	public ArrayList<SelectItem> obtnTiposIdentificacion(){
		ArrayList<SelectItem> tiposIdentificacion=new ArrayList<>();
		
		SelectItem item=new SelectItem();
		item.setValue("CC");
		item.setLabel("CEDULA DE CIUDADANIA");
		tiposIdentificacion.add(item);
		
		item=new SelectItem();
		item.setValue("TI");
		item.setLabel("TARJETA DE IDENTIDAD");
		tiposIdentificacion.add(item);
		return tiposIdentificacion;
		
	}		
}
