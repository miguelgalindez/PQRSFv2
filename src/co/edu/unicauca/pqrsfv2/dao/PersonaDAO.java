package co.edu.unicauca.pqrsfv2.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import co.edu.unicauca.pqrsfv2.conexion.Conexion;
import co.edu.unicauca.pqrsfv2.modelo.Persona;

@Stateless
@LocalBean
public class PersonaDAO {
		
	Conexion con;
	
	public PersonaDAO(){
		con=new Conexion();
	}
	
	public HashMap<Integer, String> obtnTiposIdentificacion(){		
		String sql="SELECT * FROM TIPOIDENTIFICACION";						
		return generarElementos(con.executeQueryRS(sql), "TIPIDEID", "TIPIDEDESCRIPCION");		
	}
	
	public HashMap<Integer, String> obtnTiposPersona() {
		String sql="SELECT * FROM TIPOPERSONA";				
		return generarElementos(con.executeQueryRS(sql), "TIPPERID", "TIPPERDESCRIPCION");		
	}
	
	public HashMap<Integer, String> obtnDepartamentos() {	
		String sql="SELECT * FROM DEPARTAMENTO";				
		return generarElementos(con.executeQueryRS(sql), "DEPTOID", "DEPTONOMBRE");	
	}
	
	public HashMap<Integer, String> obtnMunicipios(Integer idDepartamento) {
		String sql="SELECT MUNID, MUNNOMBRE FROM MUNICIPIO WHERE DEPTOID="+idDepartamento+" ORDER BY MUNNOMBRE ASC";	
		return generarElementos(con.executeQueryRS(sql), "MUNID", "MUNNOMBRE");
	}
	
	
	private HashMap<Integer, String> generarElementos(ResultSet rs, String valueColumnName, String descriptionColumnName){		
		if(rs==null)
			return null;
		
		HashMap<Integer, String> elementos=new HashMap<Integer, String>();		
		try {
			while(rs.next())
				elementos.put(rs.getInt(valueColumnName), rs.getString(descriptionColumnName));			
		} catch (SQLException e) {
			System.out.println("ERROR. NO SE PUDO GENERAR LOS ELEMENTOS");
			e.printStackTrace();
			elementos=null;
		} finally{
			con.clean();
		}		
		return elementos;
	}

	public String obtnNombreMunicipio(Integer idMunicipio) {
		String sql="SELECT MUNNOMBRE FROM MUNICIPIO WHERE MUNID="+idMunicipio;
		ResultSet rs=con.executeQueryRS(sql);
		try {
			while(rs.next()){
				return rs.getString("MUNNOMBRE");
			}
		} catch (SQLException e) {
			System.out.println("ERROR. NO SE PUDO CONSULTAR EL NOMBRE DEL MUNICIPIO");
			e.printStackTrace();			
		} finally{
			con.clean();
		}		
		return "";
	}

	public void buscarPersona(Persona persona) {
		String sql="SELECT MUNID, DEPTOID, TIPPERID, PERNOMBRES, PERAPELLIDOS, PERCORREO, PERDIRECCION, PERTELEFONO, PERCELULAR"
				+ " FROM PERSONA NATURAL JOIN MUNICIPIO" 
				+ " WHERE TIPIDEID="+persona.getTipoIdentificacion()+" AND PERIDENTIFICACION='"+persona.getIdentificacion()+"'";
		ResultSet rs=con.executeQueryRS(sql);
		
		try {
			if(rs.next()){				
				persona.getMunicipio().setId(rs.getInt("MUNID"));
				persona.getMunicipio().setIdDepartamento(rs.getInt("DEPTOID"));
				persona.setTipoPersona(rs.getInt("TIPPERID"));
				persona.setNombres(rs.getString("PERNOMBRES"));
				persona.setApellidos(rs.getString("PERAPELLIDOS"));
				persona.setEmail(rs.getString("PERCORREO"));
				persona.setDireccion(rs.getString("PERDIRECCION"));
				persona.setTelefono(rs.getString("PERTELEFONO"));
				persona.setCelular(rs.getString("PERCELULAR"));
			}			
		} catch (SQLException e) {
			System.err.println("Error al intentar recuperar la información de la persona: tipID: "+persona.getTipoIdentificacion()+" ID: "+persona.getIdentificacion());
			e.printStackTrace();
		}
	}	
}
