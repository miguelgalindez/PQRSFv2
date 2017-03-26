package co.edu.unicauca.pqrsfv2.dao;

import java.sql.Types;
import java.util.ArrayList;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import co.edu.unicauca.pqrsfv2.conexion.Conexion;
import co.edu.unicauca.pqrsfv2.modelo.Orden;

@Stateless
@LocalBean
public class OrdenDAO {
	
	@Inject
	Conexion con;

	public boolean direccionarPQRSF(Orden orden) {
		ArrayList<Object> parameters=new ArrayList<>();
		ArrayList<Integer> parametersTypes=new ArrayList<>();
		
		parameters.add(orden.getUsuario().getUsername()); parametersTypes.add(Types.VARCHAR);
		parameters.add(orden.getPqrsf().getCodigo()); parametersTypes.add(Types.VARCHAR);
		parameters.add(orden.getPqrsf().getFechaVencimiento()); parametersTypes.add(Types.DATE);
		parameters.add(orden.getFuncionario().getIdentificacion()); parametersTypes.add(Types.VARCHAR);		
		
		return con.executeProcedure("DIRECCIONAR_PQRSF", parameters, parametersTypes);
	}

	public ArrayList<Orden> obtnTodasOrdenes() {
		
		return null;
	}
	
	

}
