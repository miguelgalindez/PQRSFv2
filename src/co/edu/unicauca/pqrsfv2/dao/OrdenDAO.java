package co.edu.unicauca.pqrsfv2.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;

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
public class OrdenDAO {
		
	Conexion con;
	
	public enum Consulta {
		BUSCAR_ORDEN, ORDENES_POR_VENCIMIENTO, ORDENES_POR_ESTADO, TODAS_ORDENES
	}
	
	public OrdenDAO(){
		con=new Conexion();
	}

	public boolean direccionarPQRSF(Orden orden) {
		ArrayList<Object> parameters=new ArrayList<>();
		ArrayList<Integer> parametersTypes=new ArrayList<>();
		
		parameters.add(orden.getUsuario().getUsername()); parametersTypes.add(Types.VARCHAR);
		parameters.add(orden.getPqrsf().getCodigo()); parametersTypes.add(Types.VARCHAR);
		parameters.add(orden.getPqrsf().getFechaVencimiento()); parametersTypes.add(Types.DATE);
		parameters.add(orden.getFuncionario().getIdentificacion()); parametersTypes.add(Types.VARCHAR);		
		
		return con.executeProcedure("DIRECCIONAR_PQRSF", parameters, parametersTypes);
	}

	public ArrayList<Orden> obtnOrdenesPorVencimiento(int diasParaVencimiento) {
		String sql="SELECT PQRSF.PQRSFCODIGO, PQRSF.TIPPQRSFID, MEDID, PERNOMBRES, PERAPELLIDOS, PQRSFASUNTO, "+
		        "PQRSFDESCRIPCION, RADFECHA, PQRSFESTADO, PQRSFFECHACREACION, "+
		        "PQRSFFECHAVENCIMIENTO, PQRSFFECHACIERRE, FUNNOMBRE, ORDFECHAASIGNACION, FUNCORREO, FUNTELEFONO, DEPID, "+
		        "RADICADO.RADID, RADICADO.USUUSUARIO "+
		        "FROM PQRSF NATURAL JOIN PERSONA "+
		                    "LEFT OUTER JOIN RADICADO ON PQRSF.RADID=RADICADO.RADID "+
		                    "LEFT OUTER JOIN ORDEN ON PQRSF.PQRSFCODIGO = ORDEN.PQRSFCODIGO "+
		                    "LEFT OUTER JOIN FUNCIONARIO ON ORDEN.FUNIDENTIFICACION = FUNCIONARIO.FUNIDENTIFICACION ";		                    
				
			if(diasParaVencimiento<0)
				sql+="WHERE PQRSFESTADO!=2 AND PQRSFFECHAVENCIMIENTO-SYSDATE<0";
			else
				sql+="WHERE PQRSFESTADO!=2 AND PQRSFFECHAVENCIMIENTO-SYSDATE<="+diasParaVencimiento+" AND PQRSFFECHAVENCIMIENTO-SYSDATE>=0";
						
		return cargarOrdenes(con.executeQueryRS(sql), Consulta.ORDENES_POR_VENCIMIENTO);
	}
	
	public ArrayList<Orden> obtnOrdenesPorEstado(int estado) {
		String sql="SELECT PQRSF.PQRSFCODIGO, PQRSF.TIPPQRSFID, MEDID, PERNOMBRES, PERAPELLIDOS, PQRSFASUNTO, "+
		        "PQRSFDESCRIPCION, RADFECHA, PQRSFESTADO, PQRSFFECHACREACION, "+
		        "PQRSFFECHAVENCIMIENTO, PQRSFFECHACIERRE, FUNNOMBRE, ORDFECHAASIGNACION, FUNCORREO, FUNTELEFONO, DEPID, "+
		        "RADICADO.RADID, RADICADO.USUUSUARIO "+
		        "FROM PQRSF NATURAL JOIN PERSONA "+
		                    "LEFT OUTER JOIN RADICADO ON PQRSF.RADID=RADICADO.RADID "+
		                    "LEFT OUTER JOIN ORDEN ON PQRSF.PQRSFCODIGO = ORDEN.PQRSFCODIGO "+
		                    "LEFT OUTER JOIN FUNCIONARIO ON ORDEN.FUNIDENTIFICACION = FUNCIONARIO.FUNIDENTIFICACION "+
				"WHERE PQRSFESTADO="+estado;
		
		// si se van a consultar las que estan en tramite que no se incluyan las que ya estan vencidas
		if(estado==1)
			sql+= " AND PQRSFFECHAVENCIMIENTO-SYSDATE>=0";
		
		return cargarOrdenes(con.executeQueryRS(sql), Consulta.ORDENES_POR_ESTADO);
	}
	
	public ArrayList<Orden> obtnTodasOrdenes(){
		String sql="SELECT PQRSF.PQRSFCODIGO, PQRSF.TIPPQRSFID, MEDID, PERNOMBRES, PERAPELLIDOS, PQRSFASUNTO, "+
		        "PQRSFDESCRIPCION, RADFECHA, PQRSFESTADO, PQRSFFECHACREACION, "+
		        "PQRSFFECHAVENCIMIENTO, PQRSFFECHACIERRE, FUNNOMBRE, ORDFECHAASIGNACION, FUNCORREO, FUNTELEFONO, DEPID, "+
		        "RADICADO.RADID, RADICADO.USUUSUARIO "+
		        "FROM PQRSF NATURAL JOIN PERSONA "+
		                    "LEFT OUTER JOIN RADICADO ON PQRSF.RADID=RADICADO.RADID "+
		                    "LEFT OUTER JOIN ORDEN ON PQRSF.PQRSFCODIGO = ORDEN.PQRSFCODIGO "+
		                    "LEFT OUTER JOIN FUNCIONARIO ON ORDEN.FUNIDENTIFICACION = FUNCIONARIO.FUNIDENTIFICACION ";				
				
		return cargarOrdenes(con.executeQueryRS(sql), Consulta.TODAS_ORDENES);
	}
	
	public Orden buscarOrden(String pqrsfCodigo, String perIdentificacion){
		String sql="SELECT PQRSF.PQRSFCODIGO, PQRSF.TIPPQRSFID, MEDID, PERNOMBRES, PERAPELLIDOS, PQRSFASUNTO, "+
					"TIPPERID, PERIDENTIFICACION, TIPIDEID, PERCORREO, PERDIRECCION, PERTELEFONO, PERCELULAR, PERSONA.MUNID, DEPTOID, "+
					"PQRSFDESCRIPCION, RADFECHA, PQRSFESTADO, PQRSFFECHACREACION, "+
					"PQRSFFECHAVENCIMIENTO, PQRSFFECHACIERRE, FUNNOMBRE, ORDFECHAASIGNACION, FUNCORREO, FUNTELEFONO, DEPID, "+
					"RADICADO.RADID, RADICADO.USUUSUARIO "+
					"FROM PQRSF NATURAL JOIN PERSONA "+
					"LEFT OUTER JOIN MUNICIPIO ON PERSONA.MUNID=MUNICIPIO.MUNID "+
                    "LEFT OUTER JOIN RADICADO ON PQRSF.RADID=RADICADO.RADID "+
                    "LEFT OUTER JOIN ORDEN ON PQRSF.PQRSFCODIGO = ORDEN.PQRSFCODIGO "+
                    "LEFT OUTER JOIN FUNCIONARIO ON ORDEN.FUNIDENTIFICACION = FUNCIONARIO.FUNIDENTIFICACION "+
                    "WHERE PQRSFCODIGO='"+pqrsfCodigo+"' AND PERIDENTIFICACION='"+perIdentificacion+"'";			
		
		ArrayList<Orden> ordenes=cargarOrdenes(con.executeQueryRS(sql), Consulta.BUSCAR_ORDEN);		
		if(ordenes!=null && ordenes.size()>0){			
			return ordenes.get(0);			
		}		
		return null;
	}

	private ArrayList<Orden> cargarOrdenes(ResultSet rs, Consulta consulta) {
		ArrayList<Orden> ordenes=new ArrayList<>();
		
		try {
			while(rs.next()){
				Orden orden=new Orden();
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
				orden.setPqrsf(pqrsf);	
				
				if(consulta.equals(Consulta.BUSCAR_ORDEN)){
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
				}
				
				orden.getFuncionario().setNombre(rs.getString("FUNNOMBRE"));
				orden.getFuncionario().getDependencia().setId(rs.getInt("DEPID"));
				orden.setFechaAsignacion(rs.getDate("ORDFECHAASIGNACION"));
				orden.getFuncionario().setCorreo(rs.getString("FUNCORREO"));
				orden.getFuncionario().setTelefono(rs.getString("FUNTELEFONO"));
				
				ordenes.add(orden);				
			}
		}catch (SQLException e) {
			System.out.println("ERROR. NO SE PUDO CARGAR LAS ORDENES");
			e.printStackTrace();
			ordenes=null;
		} finally{
			con.clean();
		}
		return ordenes;
	}

	public HashMap<String, Integer> cargarIndicadores() {		
		return con.ejecutarProcedimientoIndicadores();
		
	}
	
}
