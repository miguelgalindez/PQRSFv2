package co.edu.unicauca.pqrsfv2.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.sql.Types;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import co.edu.unicauca.pqrsfv2.conexion.Conexion;
import co.edu.unicauca.pqrsfv2.modelo.Municipio;
import co.edu.unicauca.pqrsfv2.modelo.Orden;
import co.edu.unicauca.pqrsfv2.modelo.Persona;
import co.edu.unicauca.pqrsfv2.modelo.Pqrsf;
import co.edu.unicauca.pqrsfv2.modelo.Radicado;
import co.edu.unicauca.pqrsfv2.modelo.Usuario;

@Stateless
@LocalBean
public class PqrsfDAO {
		
	Conexion con;
	
	public enum Consulta {
		BUSCAR_PQRSF, PQRSFS_POR_VENCIMIENTO, PQRSFS_POR_ESTADO, TODAS_PQRSFS
	}
	
	public PqrsfDAO(){
		con=new Conexion();
	}
	
	public ArrayList<Pqrsf> obtnNoRadicadas() {
		String sql="SELECT PQRSFCODIGO, TIPPQRSFID, TIPPERID, TIPIDEID, PERIDENTIFICACION, PERNOMBRES, PERAPELLIDOS, PERCORREO, "+ 
					"PERTELEFONO, PERCELULAR, PERDIRECCION, MUNNOMBRE, DEPTOID, PQRSFASUNTO, PQRSFDESCRIPCION, MEDID, PQRSFFECHACREACION "+ 
					"FROM PQRSF NATURAL JOIN PERSONA NATURAL JOIN MUNICIPIO "+
					"WHERE RADID IS NULL";

		return cargarNoRadicadas(con.executeQueryRS(sql));		
	}
	
	
	private ArrayList<Pqrsf> cargarNoRadicadas(ResultSet rs) {
		ArrayList<Pqrsf> pqrsfNoRadicadas=new ArrayList<>();
		try {
			while(rs.next()){
				Pqrsf pqrsf=new Pqrsf();				
				Persona persona=new Persona();
				Municipio municipio=new Municipio();
				pqrsf.setCodigo(rs.getString("PQRSFCODIGO"));
				pqrsf.setTipoPqrsf(rs.getInt("TIPPQRSFID"));
				persona.setTipoPersona(rs.getInt("TIPPERID"));
				persona.setTipoIdentificacion(rs.getInt("TIPIDEID"));
				persona.setIdentificacion(rs.getString("PERIDENTIFICACION"));
				persona.setNombres(rs.getString("PERNOMBRES"));
				persona.setApellidos(rs.getString("PERAPELLIDOS"));
				persona.setEmail(rs.getString("PERCORREO"));
				persona.setTelefono(rs.getString("PERTELEFONO"));
				persona.setCelular(rs.getString("PERCELULAR"));
				persona.setDireccion(rs.getString("PERDIRECCION"));
				municipio.setNombre(rs.getString("MUNNOMBRE"));
				municipio.setIdDepartamento(rs.getInt("DEPTOID"));
				pqrsf.setTipoPqrsf(rs.getInt("TIPPQRSFID"));
				pqrsf.setAsunto(rs.getString("PQRSFASUNTO"));
				pqrsf.setDescripcion(rs.getString("PQRSFDESCRIPCION"));
				pqrsf.setMedioRecepcion(rs.getInt("MEDID"));
				pqrsf.setFechaCreacion(rs.getDate("PQRSFFECHACREACION"));
				
				persona.setMunicipio(municipio);
				pqrsf.setPersona(persona);
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
		String sql="SELECT PQRSFCODIGO, PERIDENTIFICACION, PERNOMBRES, PERAPELLIDOS, TIPPQRSFID, "+ 
				"PQRSFASUNTO, PQRSFDESCRIPCION, MEDID, PQRSFFECHACREACION, RADFECHA "+ 
				"FROM PQRSF NATURAL JOIN PERSONA NATURAL JOIN RADICADO "+
				"WHERE RADID IS NOT NULL AND PQRSFESTADO=0";
		return cargarNoDireccionadas(con.executeQueryRS(sql));
	}

	private ArrayList<Pqrsf> cargarNoDireccionadas(ResultSet rs) {
		ArrayList<Pqrsf> pqrsfNoDireccionadas=new ArrayList<>();
		try {
			while(rs.next()){
				Pqrsf pqrsf=new Pqrsf();				
				Persona persona=new Persona();
				pqrsf.setCodigo(rs.getString("PQRSFCODIGO"));
				persona.setIdentificacion(rs.getString("PERIDENTIFICACION"));
				persona.setNombres(rs.getString("PERNOMBRES"));
				persona.setApellidos(rs.getString("PERAPELLIDOS"));
				pqrsf.setPersona(persona);
				pqrsf.setTipoPqrsf(rs.getInt("TIPPQRSFID"));
				pqrsf.setAsunto(rs.getString("PQRSFASUNTO"));
				pqrsf.setDescripcion(rs.getString("PQRSFDESCRIPCION"));
				pqrsf.setMedioRecepcion(rs.getInt("MEDID"));
				pqrsf.setFechaCreacion(rs.getDate("PQRSFFECHACREACION"));
				pqrsf.getRadicado().setFecha(rs.getDate("RADFECHA"));
				
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
		parameters.add(pqrsf.getPersona().getMunicipio().getId()); parametersTypes.add(Types.NUMERIC);
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
	
	/* ----------------------------------------------------------------------------------------------------------------  */	
	

	public boolean direccionarPQRSF(Pqrsf pqrsf, String identificacionFuncionarioResponsable, String usernameUsuarioQueRadica) {
		ArrayList<Object> parameters=new ArrayList<>();
		ArrayList<Integer> parametersTypes=new ArrayList<>();
		
		parameters.add(usernameUsuarioQueRadica); parametersTypes.add(Types.VARCHAR);
		parameters.add(pqrsf.getCodigo()); parametersTypes.add(Types.VARCHAR);
		parameters.add(pqrsf.getFechaVencimiento()); parametersTypes.add(Types.DATE);
		parameters.add(identificacionFuncionarioResponsable); parametersTypes.add(Types.VARCHAR);		
		
		return con.executeProcedure("DIRECCIONAR_PQRSF", parameters, parametersTypes);
	}

	public ArrayList<Pqrsf> obtnPqrsfsPorVencimiento(int diasParaVencimiento) {
		String sql="SELECT PQRSF.PQRSFCODIGO, PQRSF.TIPPQRSFID, MEDID, PERNOMBRES, PERAPELLIDOS, PQRSFASUNTO, "+
		        "PQRSFDESCRIPCION, RADFECHA, PQRSFESTADO, PQRSFFECHACREACION, "+
		        "PQRSFFECHAVENCIMIENTO, PQRSFFECHACIERRE, RADICADO.RADID, RADICADO.USUUSUARIO "+		        
		        "FROM PQRSF NATURAL JOIN PERSONA "+
		                    "LEFT OUTER JOIN RADICADO ON PQRSF.RADID=RADICADO.RADID";		                    
				
			if(diasParaVencimiento<0)
				sql+="WHERE PQRSFESTADO!=2 AND SYSDATE>PQRSFFECHAVENCIMIENTO";
			else
				sql+="WHERE PQRSFESTADO!=2 AND SYSDATE<=PQRSFFECHAVENCIMIENTO AND PQRSFFECHAVENCIMIENTO-SYSDATE<="+diasParaVencimiento;
						
		return cargarPqrsfs(con.executeQueryRS(sql), Consulta.PQRSFS_POR_VENCIMIENTO);
	}
	
	public ArrayList<Pqrsf> obtnPqrsfsPorEstado(int estado) {
		String sql="SELECT PQRSF.PQRSFCODIGO, PQRSF.TIPPQRSFID, MEDID, PERNOMBRES, PERAPELLIDOS, PQRSFASUNTO, "+
		        "PQRSFDESCRIPCION, RADFECHA, PQRSFESTADO, PQRSFFECHACREACION, "+
		        "PQRSFFECHAVENCIMIENTO, PQRSFFECHACIERRE, RADICADO.RADID, RADICADO.USUUSUARIO "+		        
		        "FROM PQRSF NATURAL JOIN PERSONA "+
		                    "LEFT OUTER JOIN RADICADO ON PQRSF.RADID=RADICADO.RADID "+		                    
				"WHERE PQRSFESTADO="+estado;
		
		// si se van a consultar las que estan en tramite O PENDIENTES que no se incluyan las que ya estan vencidas
		if(estado!=2)
			sql+= " AND SYSDATE<=PQRSFFECHAVENCIMIENTO";
		
		return cargarPqrsfs(con.executeQueryRS(sql), Consulta.PQRSFS_POR_ESTADO);
	}
	
	public ArrayList<Pqrsf> obtnTodasPqrsfs(){
		String sql="SELECT PQRSF.PQRSFCODIGO, PQRSF.TIPPQRSFID, MEDID, PERNOMBRES, PERAPELLIDOS, PQRSFASUNTO, "+
		        "PQRSFDESCRIPCION, RADFECHA, PQRSFESTADO, PQRSFFECHACREACION, "+
		        "PQRSFFECHAVENCIMIENTO, PQRSFFECHACIERRE, RADICADO.RADID, RADICADO.USUUSUARIO"+		        
		        "FROM PQRSF NATURAL JOIN PERSONA "+
		                    "LEFT OUTER JOIN RADICADO ON PQRSF.RADID=RADICADO.RADID ";		                    			
				
		return cargarPqrsfs(con.executeQueryRS(sql), Consulta.TODAS_PQRSFS);
	}
	
	public Pqrsf buscarPqrsf(String pqrsfCodigo, String perIdentificacion){
		String sql="SELECT PQRSF.PQRSFCODIGO, PQRSF.TIPPQRSFID, MEDID, PERNOMBRES, PERAPELLIDOS, PQRSFASUNTO, "+
				"TIPPERID, PERIDENTIFICACION, TIPIDEID, PERCORREO, PERDIRECCION, PERTELEFONO, PERCELULAR, PERSONA.MUNID, DEPTOID, "+
				"PQRSFDESCRIPCION, RADFECHA, PQRSFESTADO, PQRSFFECHACREACION, "+
				"PQRSFFECHAVENCIMIENTO, PQRSFFECHACIERRE, FUNNOMBRE, ORDFECHAASIGNACION, FUNCORREO, FUNTELEFONO, DEPID, "+
				"RADICADO.RADID, RADICADO.USUUSUARIO "+
				"FROM PQRSF NATURAL JOIN PERSONA "+
				"LEFT OUTER JOIN MUNICIPIO ON PERSONA.MUNID=MUNICIPIO.MUNID "+
                "LEFT OUTER JOIN RADICADO ON PQRSF.RADID=RADICADO.RADID ";                
    
		if(perIdentificacion!=null)
			sql+="WHERE PQRSF.PQRSFCODIGO='"+pqrsfCodigo+"' AND PERIDENTIFICACION='"+perIdentificacion+"'";			
		else
			sql+="WHERE PQRSF.PQRSFCODIGO='"+pqrsfCodigo+"'"; 
		
		ArrayList<Pqrsf> pqrsfs=cargarPqrsfs(con.executeQueryRS(sql), Consulta.BUSCAR_PQRSF);		
		if(pqrsfs!=null && pqrsfs.size()>0){			
			return pqrsfs.get(0);			
		}		
		return null;
	}

	private ArrayList<Pqrsf> cargarPqrsfs(ResultSet rs, Consulta consulta) {
		ArrayList<Pqrsf> pqrsfs=new ArrayList<>();
		
		try {
			while(rs.next()){				
				Pqrsf pqrsf=new Pqrsf();				
				
				pqrsf.setCodigo(rs.getString("PQRSFCODIGO"));
				pqrsf.setTipoPqrsf(rs.getInt("TIPPQRSFID"));
				pqrsf.setMedioRecepcion(rs.getInt("MEDID"));				
				pqrsf.setAsunto(rs.getString("PQRSFASUNTO"));
				pqrsf.setDescripcion(rs.getString("PQRSFDESCRIPCION"));										
				pqrsf.setEstado(rs.getInt("PQRSFESTADO"));
				pqrsf.setFechaCreacion(rs.getDate("PQRSFFECHACREACION"));
				pqrsf.setFechaVencimiento(rs.getDate("PQRSFFECHAVENCIMIENTO"));
				pqrsf.setFechaCierre(rs.getDate("PQRSFFECHACIERRE"));
				
				/* TODO -  algunas pueden requerir por lo menos el nombre y el apellido del solicitante. Revisar el sql que 
				ejecuta cada funcion de acuerdo con lo que solamente necesite */
				
				pqrsfs.add(pqrsf);				
			}
		}catch (SQLException e) {
			System.out.println("ERROR. NO SE PUDO CARGAR LAS PQRSFS");
			e.printStackTrace();
			pqrsfs=null;
		} finally{
			con.clean();
		}
		return pqrsfs;
	}
	
	public Pqrsf cargarDetallesPqrsf(Pqrsf pqrsf){
		String sql="";
		/*
			Persona persona=new Persona();					
			persona.setTipoPersona(rs.getInt("TIPPERID"));
			persona.setTipoIdentificacion(rs.getInt("TIPIDEID"));
			persona.setIdentificacion(rs.getString("PERIDENTIFICACION"));					
			persona.setNombres(rs.getString("PERNOMBRES"));
			persona.setApellidos(rs.getString("PERAPELLIDOS"));
			persona.setEmail(rs.getString("PERCORREO"));
			persona.setDireccion(rs.getString("PERDIRECCION"));
			persona.setTelefono(rs.getString("PERTELEFONO"));
			persona.setCelular(rs.getString("PERCELULAR"));										
			Municipio municipio=new Municipio();
			municipio.setId(rs.getInt("MUNID"));
			municipio.setIdDepartamento(rs.getInt("DEPTOID"));
			persona.setMunicipio(municipio);					
			pqrsf.setPersona(persona);
			
			Radicado radicado=new Radicado();
			radicado.setId(rs.getString("RADID"));
			radicado.setFecha(rs.getDate("RADFECHA"));
			radicado.setUsuarioQueRadica(new Usuario(rs.getString("USUUSUARIO")));
			pqrsf.setRadicado(radicado);
			*/
		return pqrsf;
	}
	
	private ArrayList<Orden> obtnOrdenesPqrsf(String codigoPqrsf){
		// TODO - Implementar funcion para obtener las ordenes asociadas a cierta Pqrsf
		return null;
	}

	public HashMap<String, Integer> cargarIndicadores() {		
		return con.ejecutarProcedimientoIndicadores();
		
	}
}
