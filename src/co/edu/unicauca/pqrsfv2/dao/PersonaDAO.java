package co.edu.unicauca.pqrsfv2.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

import co.edu.unicauca.pqrsfv2.conexion.Conexion;

@Stateless
@LocalBean
public class PersonaDAO {
	
	@EJB
	Conexion con;
	public ArrayList<SelectItem> obtnTiposIdentificacion(){		
		String sql="SELECT * FROM TIPOIDENTIFICACION";						
		return generarElementos(con.executeQueryRS(sql), "TIPIDEID", "TIPIDEDESCRIPCION");		
	}
	
	public ArrayList<SelectItem> obtnTiposPersona() {
		String sql="SELECT * FROM TIPOPERSONA";				
		return generarElementos(con.executeQueryRS(sql), "TIPPERID", "TIPPERDESCRIPCION");		
	}
	
	public ArrayList<SelectItem> obtnDepartamentos() {	
		String sql="SELECT * FROM DEPARTAMENTO";				
		return generarElementos(con.executeQueryRS(sql), "DEPTOID", "DEPTONOMBRE");	
	}
	
	public ArrayList<SelectItem> obtnMunicipios(Integer idDepartamento) {
		String sql="SELECT MUNID, MUNNOMBRE FROM MUNICIPIO WHERE DEPTOID="+idDepartamento+" ORDER BY MUNNOMBRE ASC";	
		return generarElementos(con.executeQueryRS(sql), "MUNID", "MUNNOMBRE");
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
