package co.edu.unicauca.pqrsfv2.consultas.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.ejb.LocalBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import co.edu.unicauca.pqrsfv2.dao.OrdenDAO;
import co.edu.unicauca.pqrsfv2.modelo.Orden;
import co.edu.unicauca.pqrsfv2.modelo.Pqrsf;

@SessionScoped
@LocalBean
@Named
public class TodasPqrsfControl implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	OrdenDAO ordenDAO;
	private ArrayList<Orden> ordenes;

	public void cargarTodasPqrf(){		
		ordenes=ordenDAO.obtnTodasOrdenes();
	}
	
	
	public Integer obtnDiasParaVencimiento(Pqrsf pqrsf){
		Date vencimiento=pqrsf.getFechaVencimiento();
		if(vencimiento!=null)
			return (int)((vencimiento.getTime() - new Date().getTime()) / (1000 * 60 * 60 * 24));																
		else
			return null;		
	}
	
	public String obtnLeyendaDiasParaVencimiento(Pqrsf pqrsf){
		Date vencimiento=pqrsf.getFechaVencimiento();
		if(vencimiento!=null){
			int diasRestantes=(int)((vencimiento.getTime() - new Date().getTime()) / (1000 * 60 * 60 * 24));
			if (diasRestantes<0)
				return "VENCIDA";			
			else{
				if(diasRestantes==0)
					return "VENCE HOY";
				else{
					if(diasRestantes==1)
						return "En 1 d�a";
					else
						return "En "+diasRestantes+" d�as";
				}
			}
		}
		else
			return "";					
	}
		
	public ArrayList<Orden> getOrdenes() {
		return ordenes;
	}

	public void setOrdenes(ArrayList<Orden> ordenes) {
		this.ordenes = ordenes;
	}
}
