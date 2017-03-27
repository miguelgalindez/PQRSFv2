package co.edu.unicauca.pqrsfv2.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import co.edu.unicauca.pqrsfv2.conexion.Conexion;

@Stateless
@LocalBean
public class DependenciaDAO {
	Conexion con;
	
	public DependenciaDAO(){
		con=new Conexion();
	}
	
	public HashMap<Integer, String> obtnDependencias(){
		String sql="SELECT DEPID, DEPNOMBRE FROM DEPENDENCIA";		
		return generarElementos(con.executeQueryRS(sql), "DEPID", "DEPNOMBRE");			
	}
	
	public HashMap<Integer, String> obtnFuncionarios(Integer idDependencia){
		String sql="SELECT FUNIDENTIFICACION, FUNNOMBRE FROM FUNCIONARIO WHERE DEPID="+idDependencia;
		return generarElementos(con.executeQueryRS(sql), "FUNIDENTIFICACION", "FUNNOMBRE");
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

}
