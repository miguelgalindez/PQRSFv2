package co.edu.unicauca.pqrsfv2.conexion;

import java.io.StringReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.sql.Types;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.sql.DataSource;

/**
 * Clase encargada de la capa de datos, interctua entre la aplicación y la base
 * de datos
 * 
 * @author DesarrolloPr
 * @version
 */

public class Conexion implements HttpSessionBindingListener {
	private DataSource pool;
	private Connection conn;
	private Statement stmt;
	ResultSet rs;
	private PreparedStatement pstmt;	
	CallableStatement cs;
	private DataSource driverManagerDataSource;	
	private String nombreDS;

	public Conexion(){
		nombreDS="pqrsfv2DS";
		conn = null;
		stmt = null;
		rs = null;
		pstmt = null;
		cs=null;
	}

	public void iniciarDataSource() {

		try {
			Context envCtx = (Context) new InitialContext().lookup("java:");
			driverManagerDataSource = (DataSource) envCtx.lookup("jboss/jdbc/"+ nombreDS);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	
	@SuppressWarnings({ "rawtypes" })
	public ResultSet executeQueryRS(String sql, ArrayList parametros) {
		iniciarDataSource();
		rs = null;
		try {
			conn = driverManagerDataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			if (parametros.size() > 0)
				for (int i = 0; i < parametros.size(); i++) {
					pstmt.setString(i + 1, parametros.get(i).toString());
				}
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return rs;
	}

	
	public ResultSet executeQueryRS(String sql) {
		iniciarDataSource();		

		try {
			conn = driverManagerDataSource.getConnection();	
			stmt = conn.createStatement();			
			rs = stmt.executeQuery(sql);
			return rs;
			
		}catch (SQLException e) {
			e.printStackTrace();			
			this.clean();			
			return null;
		}				
	}
	
	public Object executeFunction(int returnType, String functionName, ArrayList<Object> parameters, ArrayList<Integer> parametersTypes){
		String sql=getCallSignature(true, "PKG_PQRSFV2", functionName, parameters.size());		
		Object retorno;
		iniciarDataSource();
		try{
			conn=driverManagerDataSource.getConnection();
			cs=conn.prepareCall(sql);	
			cs.registerOutParameter(1, returnType);			
			applyParameters(cs, parameters, parametersTypes, true);
			cs.execute();
			retorno=cs.getObject(1);
		}
		catch (SQLException e) {
			System.err.println("ERROR. Problemas al invocar la funcion almacenada: "+functionName);
			e.printStackTrace();
			retorno=null;
		}
		finally{
			this.clean();
		}
		
		return retorno;
	}
	
	public String getCallSignature(boolean isFunction, String packageName, String objectName, int numberOfArgs){		
		String callSignature;
		if(isFunction)		
			callSignature="{?= call "+packageName+"."+objectName+"(";
		else
			callSignature="{call "+packageName+"."+objectName+"(";
		
		String args="";
		for(int i=0;i<numberOfArgs;i++)
			args+="?,";
		args=args.substring(0, args.length()-1);						
		
		callSignature+=args+")}";		
		return callSignature;
	}
	
	public boolean executeProcedure(String procedureName, ArrayList<Object> parameters, ArrayList<Integer> parametersTypes) {	
		iniciarDataSource();		
		String sql=getCallSignature(false, "PKG_PQRSFV2", procedureName, parameters.size());
		boolean successCall = false;

		try {
			conn = driverManagerDataSource.getConnection();			
			cs = conn.prepareCall(sql);
			applyParameters(cs, parameters, parametersTypes, false);
			cs.execute();			
			successCall=true;
		} catch (SQLException e) {
			System.err.println("Error llamando al procedimiento almacenado utilizando conexion.executeCall");
			e.printStackTrace();						
		} finally {
			this.clean();
		}
		return successCall;
	}
	
	private void applyParameters(CallableStatement cs, ArrayList<Object> parametros, ArrayList<Integer> tipos, boolean isFunction){		
		try{
			int aux, initialParameterIndex;			
			if(isFunction)
				initialParameterIndex=2;
			else
				initialParameterIndex=1;
			
			for (int i = 0; i < parametros.size(); i++) {
				aux = (Integer) tipos.get(i);				
				if (aux == Types.VARCHAR) {
					cs.setString(i+initialParameterIndex, (String) parametros.get(i));
				} else if (aux == Types.INTEGER) {
					cs.setInt(i+initialParameterIndex, (Integer) parametros.get(i));
				} else if (aux == Types.NULL) {
					cs.setNull(i+initialParameterIndex, Types.INTEGER);
				} else if (aux == Types.NUMERIC) {
					cs.setInt(i+initialParameterIndex, (Integer) parametros.get(i));
				} else if (aux == Types.CHAR) {
					cs.setInt(i+initialParameterIndex, ((String) parametros.get(i)).charAt(0));
				} else if (aux == Types.FLOAT) {
					cs.setFloat(i+initialParameterIndex, ((Float) parametros.get(i)));
				} else if (aux == Types.DOUBLE) {
					cs.setDouble(i+initialParameterIndex, ((Double) parametros.get(i)));
				} else if (aux == Types.DATE) {
					if ((java.util.Date) parametros.get(i) != null) {
						cs.setDate(i+initialParameterIndex, new java.sql.Date(
								((java.util.Date) parametros.get(i)).getTime()));
					} else {
						// Si es null, inserte null en la BD.
						cs.setDate(i+initialParameterIndex, null);
					}
				} else if (aux == Types.TIMESTAMP) {
					if ((java.util.Date) parametros.get(i) != null) {
						Timestamp x = new Timestamp(
								((java.util.Date) parametros.get(i)).getTime());
						cs.setTimestamp(i+initialParameterIndex, x);

					} else {
						cs.setTimestamp(i+initialParameterIndex, null);						
					}
				} else if (aux == Types.CLOB) {					
					StringReader sr = new StringReader((String)parametros.get(i));
					cs.setClob(i+initialParameterIndex, sr);

				} else if (aux == Types.OTHER) {
					cs.registerOutParameter((String) parametros.get(i),
							Types.VARCHAR);
				}
			}
		}
		catch(SQLException ex){
			this.clean();
		}
		
	}

  
	public void clean(){
		try {
			if(rs!=null)
				rs.close();
			if(stmt!=null)
				stmt.close();
			if(cs!=null)
				cs.close();
			if(conn!=null)
				conn.close(); 			
		}
		catch(Exception e1){
			System.out.println("ERROR. NO SE PUEDO LIMPIAR LA CONEXION (CERRRAR LOS RECURSOS)");
			e1.printStackTrace();			
		}		
	}

	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		//System.err.println("connBean: in the valueBound method");
		try {
			if (conn == null || conn.isClosed()) {
				conn = pool.getConnection();
				stmt = conn.createStatement();
			}
		} catch (SQLException e) {
			conn = null;
		}
	}
	
	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		try {
			conn.close();
		} catch (SQLException e) {
		} finally {
			conn = null;
		}
	}
	
	public String getNombreDS() {
		return nombreDS;
	}
	
	public void setNombreDS(String nombreDS) {
		this.nombreDS = nombreDS;
	}
}
