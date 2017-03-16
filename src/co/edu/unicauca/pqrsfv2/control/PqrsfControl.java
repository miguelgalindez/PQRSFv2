package co.edu.unicauca.pqrsfv2.control;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import co.edu.unicauca.pqrsfv2.modelo.Solicitud;


@ManagedBean(name="pqrsfControl")
@ViewScoped()
public class PqrsfControl implements Serializable{
	
	private Solicitud solicitud;
	
}
