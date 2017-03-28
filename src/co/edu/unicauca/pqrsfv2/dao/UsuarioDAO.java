package co.edu.unicauca.pqrsfv2.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import co.edu.unicauca.pqrsfv2.conexion.Conexion;
import co.edu.unicauca.pqrsfv2.modelo.Usuario;

@Stateless
@LocalBean
public class UsuarioDAO {
	
	Conexion con;
	
	public UsuarioDAO(){
		con=new Conexion();
	}
	public Usuario obtnUsuario(String username){
		String sql="SELECT USUROL, USUFECHAINICIO, USUFECHAFIN "+
				"FROM USUARIO WHERE LOWER(USUUSUARIO)='"+username.toLowerCase()+"'";
		Usuario usuario=null;
		ResultSet rs=con.executeQueryRS(sql);
		if(rs!=null){
			try {
				if(rs.next()){				
					usuario=new Usuario();					
					usuario.setRol(rs.getString("USUROL"));
					usuario.setFechaInicio(rs.getDate("USUFECHAINICIO"));
					usuario.setFechaFin(rs.getDate("USUFECHAFIN"));								
				}			
			} catch (SQLException e) {
				System.out.println("ERROR. NO SE PUDO GENERAR LOS ELEMENTOS");
				e.printStackTrace();			
			} finally{
				con.clean();
			}
		}
		return usuario;		
	}
}
