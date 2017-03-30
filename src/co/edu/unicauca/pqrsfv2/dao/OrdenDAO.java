package co.edu.unicauca.pqrsfv2.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import co.edu.unicauca.pqrsfv2.conexion.Conexion;
import co.edu.unicauca.pqrsfv2.modelo.Orden;
import co.edu.unicauca.pqrsfv2.modelo.Pqrsf;

@Stateless
@LocalBean
public class OrdenDAO {
		
	Conexion con;
	
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

	public ArrayList<Orden> obtnTodasOrdenes(Integer diasParaVencimiento) {
		String sql="SELECT PQRSF.PQRSFCODIGO, PQRSF.TIPPQRSFID, MEDID, PERNOMBRES, PERAPELLIDOS, PQRSFASUNTO, "+
		        "PQRSFDESCRIPCION, RADFECHA, PQRSFESTADO, PQRSFFECHACREACION, "+
		        "PQRSFFECHAVENCIMIENTO, PQRSFFECHACIERRE, FUNNOMBRE, DEPID "+      
		        "FROM PQRSF NATURAL JOIN PERSONA "+
		                    "LEFT OUTER JOIN RADICADO ON PQRSF.RADID=RADICADO.RADID "+
		                    "LEFT OUTER JOIN ORDEN ON PQRSF.PQRSFCODIGO = ORDEN.PQRSFCODIGO "+
		                    "LEFT OUTER JOIN FUNCIONARIO ON ORDEN.FUNIDENTIFICACION = FUNCIONARIO.FUNIDENTIFICACION ";
		if(diasParaVencimiento!=null){
			if(diasParaVencimiento<0)
				sql+="WHERE PQRSFESTADO!=2 AND PQRSFFECHAVENCIMIENTO-SYSDATE<0";
			else
				sql+="WHERE PQRSFESTADO!=2 AND PQRSFFECHAVENCIMIENTO-SYSDATE<="+diasParaVencimiento+" AND PQRSFFECHAVENCIMIENTO-SYSDATE>=0";
		}			
							
		return cargarOrdenes(con.executeQueryRS(sql));
	}
	
	public Orden obtnOrden(String pqrsfCodigo, String perIdentificacion){		
		String sql="SELECT PQRSF.PQRSFCODIGO, PQRSF.TIPPQRSFID, MEDID, PERNOMBRES, PERAPELLIDOS, PQRSFASUNTO, "+
					"PQRSFDESCRIPCION, RADFECHA, PQRSFESTADO, PQRSFFECHACREACION, "+
					"PQRSFFECHAVENCIMIENTO, PQRSFFECHACIERRE, FUNNOMBRE, DEPID "+
					"FROM PQRSF NATURAL JOIN PERSONA "+
                    "LEFT OUTER JOIN RADICADO ON PQRSF.RADID=RADICADO.RADID "+
                    "LEFT OUTER JOIN ORDEN ON PQRSF.PQRSFCODIGO = ORDEN.PQRSFCODIGO "+
                    "LEFT OUTER JOIN FUNCIONARIO ON ORDEN.FUNIDENTIFICACION = FUNCIONARIO.FUNIDENTIFICACION "+
                    "WHERE PQRSFCODIGO='"+pqrsfCodigo+"' AND PERIDENTIFICACION='"+perIdentificacion+"'";
		
		ArrayList<Orden> ordenes=cargarOrdenes(con.executeQueryRS(sql));		
		if(ordenes!=null && ordenes.size()>0){			
			return ordenes.get(0);			
		}		
		return null;
	}

	private ArrayList<Orden> cargarOrdenes(ResultSet rs) {
		ArrayList<Orden> ordenes=new ArrayList<>();
		
		try {
			while(rs.next()){
				Orden orden=new Orden();
				Pqrsf pqrsf=new Pqrsf();
				pqrsf.setCodigo(rs.getString("PQRSFCODIGO"));
				pqrsf.setTipoPqrsf(rs.getInt("TIPPQRSFID"));
				pqrsf.setMedioRecepcion(rs.getInt("MEDID"));
				pqrsf.getPersona().setNombres(rs.getString("PERNOMBRES"));
				pqrsf.getPersona().setApellidos(rs.getString("PERAPELLIDOS"));
				pqrsf.setAsunto(rs.getString("PQRSFASUNTO"));
				pqrsf.setDescripcion(rs.getString("PQRSFDESCRIPCION"));
				pqrsf.getRadicado().setFecha(rs.getDate("RADFECHA"));
				pqrsf.setEstado(rs.getInt("PQRSFESTADO"));
				pqrsf.setFechaCreacion(rs.getDate("PQRSFFECHACREACION"));
				pqrsf.setFechaVencimiento(rs.getDate("PQRSFFECHAVENCIMIENTO"));
				pqrsf.setFechaCierre(rs.getDate("PQRSFFECHACIERRE"));
				orden.setPqrsf(pqrsf);				
				orden.getFuncionario().setNombre(rs.getString("FUNNOMBRE"));
				orden.getFuncionario().getDependencia().setId(rs.getInt("DEPID"));
				
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
}
