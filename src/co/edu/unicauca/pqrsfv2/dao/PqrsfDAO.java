package co.edu.unicauca.pqrsfv2.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.sql.Types;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import co.edu.unicauca.pqrsfv2.conexion.Conexion;
import co.edu.unicauca.pqrsfv2.modelo.Pqrsf;

@Stateless
@LocalBean
public class PqrsfDAO {
	
	@EJB
	Conexion con;
	
	
	public ArrayList<Pqrsf> obtnNoRadicadas() {
		String sql="SELECT PQRSFCODIGO, PERNOMBRES, PERAPELLIDOS, TIPPQRSFID, "+ 
					"PQRSFASUNTO, PQRSFDESCRIPCION, MEDID, PQRSFFECHACREACION "+ 
					"FROM PQRSF NATURAL JOIN PERSONA WHERE RADID IS NULL";

		return cargarNoRadicadas(con.executeQueryRS(sql));		
	}
	
	
	private ArrayList<Pqrsf> cargarNoRadicadas(ResultSet rs) {
		ArrayList<Pqrsf> pqrsfNoRadicadas=new ArrayList<>();
		try {
			while(rs.next()){
				Pqrsf pqrsf=new Pqrsf();				
				
				pqrsf.setCodigo(rs.getString("PQRSFCODIGO"));
				pqrsf.getPersona().setNombres(rs.getString("PERNOMBRES"));
				pqrsf.getPersona().setApellidos(rs.getString("PERAPELLIDOS"));								
				pqrsf.setTipoPqrsf(rs.getInt("TIPPQRSFID"));
				pqrsf.setAsunto(rs.getString("PQRSFASUNTO"));
				pqrsf.setDescripcion(rs.getString("PQRSFDESCRIPCION"));
				pqrsf.setMedioRecepcion(rs.getInt("MEDID"));
				pqrsf.setFechaCreacion(new Date(rs.getDate("PQRSFFECHACREACION").getTime()));
				
				pqrsfNoRadicadas.add(pqrsf);
			}
		} catch (SQLException e) {
			System.out.println("ERROR. NO SE PUDO CARGAR LAS PQRSF NO RADICADAS");
			e.printStackTrace();
			pqrsfNoRadicadas=null;
		} finally{
			con.clean();
		}		
		return pqrsfNoRadicadas;
	}
	
	public ArrayList<Pqrsf> obtnNoDireccionadas() {
		String sql="SELECT PQRSFCODIGO, PERNOMBRES, PERAPELLIDOS, TIPPQRSFID, "+ 
				"PQRSFASUNTO, PQRSFDESCRIPCION, MEDID, PQRSFFECHACREACION, RADFECHA"+ 
				"FROM PQRSF NATURAL JOIN PERSONA NATURAL JOIN RADICADO "+
				"WHERE RADID IS NOT NULL AND PQRSFDIRECCIONADA=0";
		return cargarNoDireccionadas(con.executeQueryRS(sql));
	}

	private ArrayList<Pqrsf> cargarNoDireccionadas(ResultSet rs) {
		ArrayList<Pqrsf> pqrsfNoDireccionadas=new ArrayList<>();
		try {
			while(rs.next()){
				Pqrsf pqrsf=new Pqrsf();				
				
				pqrsf.setCodigo(rs.getString("PQRSFCODIGO"));
				pqrsf.getPersona().setNombres(rs.getString("PERNOMBRES"));
				pqrsf.getPersona().setApellidos(rs.getString("PERAPELLIDOS"));								
				pqrsf.setTipoPqrsf(rs.getInt("TIPPQRSFID"));
				pqrsf.setAsunto(rs.getString("PQRSFASUNTO"));
				pqrsf.setDescripcion(rs.getString("PQRSFDESCRIPCION"));
				pqrsf.setMedioRecepcion(rs.getInt("MEDID"));
				pqrsf.setFechaCreacion(new Date(rs.getDate("PQRSFFECHACREACION").getTime()));
				pqrsf.getRadicado().setFecha(new Date(rs.getDate("RADFECHA").getTime()));
				
				pqrsfNoDireccionadas.add(pqrsf);
			}
		} catch (SQLException e) {
			System.err.println("ERROR. NO SE PUDO CARGAR LAS PQRSF NO DIRECCIONADAS");
			e.printStackTrace();
			pqrsfNoDireccionadas=null;
		}finally{
			con.clean();
		}
		
		return pqrsfNoDireccionadas;
	}


	public HashMap<Integer, String> obtnMediosRecepcion(){
		String sql="SELECT * FROM MEDIORECEPCION";
		return generarElementos(con.executeQueryRS(sql), "MEDID", "MEDDESCRIPCION");			
	}
	
	public HashMap<Integer, String> obtnTiposPqrsf() {
		String sql="SELECT * FROM TIPOPQRSF";
		return generarElementos(con.executeQueryRS(sql), "TIPPQRSFID", "TIPPQRSFDESCRIPCION");
	}
	
	public boolean guardar(Pqrsf pqrsf) {
		ArrayList<Object> parameters=new ArrayList<>();
		ArrayList<Integer> parametersTypes=new ArrayList<>();			
		
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
		
		Object codigo= con.executeFunction(Types.VARCHAR, "REGISTRAR_PQRSF", parameters, parametersTypes);
		if(codigo!=null)
			pqrsf.setCodigo((String)codigo);
		
		return codigo!=null;
	}	
	
	public boolean guardarRadicado(Pqrsf pqrsf) {
		ArrayList<Object> parameters=new ArrayList<>();
		ArrayList<Integer> parametersTypes=new ArrayList<>();
		
		parameters.add(pqrsf.getCodigo()); parametersTypes.add(Types.VARCHAR);
		parameters.add(pqrsf.getRadicado().getId()); parametersTypes.add(Types.VARCHAR);
		parameters.add(pqrsf.getRadicado().getUsuarioQueRadica().getUsername()); parametersTypes.add(Types.VARCHAR);
		parameters.add(pqrsf.getRadicado().getFecha()); parametersTypes.add(Types.DATE);		
				
		return con.executeProcedure("REGISTRAR_RADICADO", parameters, parametersTypes);
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
