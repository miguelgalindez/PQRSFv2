package co.edu.unicauca.pqrsfv2.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Types;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;
import co.edu.unicauca.pqrsfv2.conexion.Conexion;
import co.edu.unicauca.pqrsfv2.modelo.Pqrsf;

@Stateless
@LocalBean
public class PqrsfDAO {
	
	@EJB
	Conexion con;
	
	public ArrayList<SelectItem> obtnMediosRecepcion(){
		String sql="SELECT * FROM MEDIORECEPCION";
		return generarElementos(con.executeQueryRS(sql), "MEDID", "MEDDESCRIPCION");			
	}
	
	public ArrayList<SelectItem> obtnTiposPqrsf() {
		String sql="SELECT * FROM TIPOPQRSF";
		return generarElementos(con.executeQueryRS(sql), "TIPPQRSFID", "TIPPQRSFDESCRIPCION");
	}
	
	public boolean guardar(Pqrsf pqrsf) {
		ArrayList<Object> parameters=new ArrayList<>();
		ArrayList<Integer> parametersTypes=new ArrayList<>();
		
		// Agregando el tipo del retorno de la funcion
		parameters.add(""); parametersTypes.add(Types.VARCHAR);
		
		parameters.add(pqrsf.getPersona().getTipoIdentificacion()); parametersTypes.add(Types.NUMERIC);
		parameters.add(pqrsf.getPersona().getIdentificacion()); parametersTypes.add(Types.VARCHAR);
		parameters.add(pqrsf.getPersona().getTipoPersona()); parametersTypes.add(Types.NUMERIC);
		parameters.add(pqrsf.getPersona().getNombres()); parametersTypes.add(Types.VARCHAR);
		parameters.add(pqrsf.getPersona().getApellidos()); parametersTypes.add(Types.VARCHAR);
		parameters.add(pqrsf.getPersona().getEmail()); parametersTypes.add(Types.VARCHAR);
		parameters.add(pqrsf.getPersona().getDireccion()); parametersTypes.add(Types.VARCHAR);
		parameters.add(pqrsf.getPersona().getTelefono()); parametersTypes.add(Types.VARCHAR);
		parameters.add(pqrsf.getPersona().getCelular()); parametersTypes.add(Types.VARCHAR);
		parameters.add(pqrsf.getPersona().getMunicipio()); parametersTypes.add(Types.NUMERIC);
		parameters.add(pqrsf.getMedioRecepcion()); parametersTypes.add(Types.NUMERIC);
		parameters.add(pqrsf.getTipoPqrsf()); parametersTypes.add(Types.NUMERIC);
		parameters.add(pqrsf.getAsunto()); parametersTypes.add(Types.VARCHAR);
		parameters.add(pqrsf.getDescripcion()); parametersTypes.add(Types.VARCHAR);
		
		Object codigo= con.executeFunction("REGISTRAR_PQRSF", parameters, parametersTypes);
		if(codigo!=null)
			pqrsf.setCodigo((String)codigo);
		
		return codigo!=null;
	}	
	
	private ArrayList<SelectItem> generarElementos(ResultSet rs, String value, String label){		
		if(rs==null)
			return null;
		
		ArrayList<SelectItem> elementos=new ArrayList<SelectItem>();		
		try {
			while(rs.next()){
				SelectItem tipoId=new SelectItem();
				tipoId.setValue(rs.getInt(value));
				tipoId.setLabel(rs.getString(label));
				elementos.add(tipoId);
			}
		} catch (SQLException e) {
			System.out.println("ERROR. NO SE PUDO GENERAR LOS ELEMENTOS");
			e.printStackTrace();
			elementos=null;
		} finally{
			con.clean();
		}		
		return elementos;
	}
}
