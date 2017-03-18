package co.edu.unicauca.pqrsfv2.logica;

import java.util.ArrayList;

import javax.faces.model.SelectItem;

public class PqrsfBO{

	/**
	 * 
	 */
		
	public ArrayList<SelectItem> obtnTiposSolicitud(){
		ArrayList<SelectItem> tiposSolicitud=new ArrayList<>();
		
		SelectItem item= new SelectItem();
		item.setValue("P");
		item.setLabel("PETICIÓN");
		tiposSolicitud.add(item);
		item= new SelectItem();
		item.setValue("Q");
		item.setLabel("QUEJA");
		tiposSolicitud.add(item);
		item= new SelectItem();
		item.setValue("R");
		item.setLabel("RECLAMO");
		tiposSolicitud.add(item);
		item= new SelectItem();
		item.setValue("S");
		item.setLabel("SUGERENCIA");
		tiposSolicitud.add(item);

		return tiposSolicitud;
	}
	
	public ArrayList<SelectItem> obtnMediosRecepcion(){
		ArrayList<SelectItem> mediosRecepcion=new ArrayList<>();
		
		SelectItem item=new SelectItem();
		item.setValue("WEB");
		item.setLabel("WEB");
		mediosRecepcion.add(item);
		
		item=new SelectItem();
		item.setValue("EMAIL");
		item.setLabel("EMAIL");
		mediosRecepcion.add(item);
		
		item=new SelectItem();
		item.setValue("TELEFONO");
		item.setLabel("TELEFONO");
		mediosRecepcion.add(item);
		
		item=new SelectItem();
		item.setValue("VERBAL");
		item.setLabel("VERBAL");
		mediosRecepcion.add(item);
		
		return mediosRecepcion;
		
	}	

}
