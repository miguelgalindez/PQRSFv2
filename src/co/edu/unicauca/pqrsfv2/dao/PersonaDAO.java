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
		return generarTiposIdentificacion(con.executeQueryRS(sql));		
	}
	
	public ArrayList<SelectItem> obtnTiposPersona() {
		
		String sql="SELECT * FROM TIPOPERSONA";				
		return generarTiposPersona(con.executeQueryRS(sql));		
	}
	
	
	private ArrayList<SelectItem> generarTiposIdentificacion(ResultSet rs){
		
		if(rs==null)
			return null;
		
		ArrayList<SelectItem> tiposId=new ArrayList<SelectItem>();		
		try {
			while(rs.next()){
				SelectItem tipoId=new SelectItem();
				tipoId.setValue(rs.getInt("TIPIDEID"));
				tipoId.setLabel(rs.getString("TIPIDEDESCRIPCION"));
				tiposId.add(tipoId);
			}
		} catch (SQLException e) {
			System.out.println("ERROR. NO SE PUDO GENERAR LOS TIPOS DE IDENTIFICACION");
			e.printStackTrace();
			tiposId=null;
		} finally{
			con.clean();
		}
			
		return tiposId;
	}
	
	private ArrayList<SelectItem> generarTiposPersona(ResultSet rs){
		
		if(rs==null)
			return null;
		
		ArrayList<SelectItem> tiposPersona=new ArrayList<>();
		
		try {
			while(rs.next()){
				SelectItem tipoPersona=new SelectItem();
				tipoPersona.setValue(rs.getInt("TIPPERID"));
				tipoPersona.setLabel(rs.getString("TIPPERDESCRIPCION"));				
				tiposPersona.add(tipoPersona);
			}
		} catch (SQLException e) {
			System.out.println("ERROR. NO SE PUDO GENERAR LOS TIPOS DE PERSONAS");
			e.printStackTrace();
			tiposPersona=null;			
		} finally {
			con.clean();
		}
					
		return tiposPersona;
	}

}
