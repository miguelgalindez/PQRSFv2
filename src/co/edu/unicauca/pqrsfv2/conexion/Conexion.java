package co.edu.unicauca.pqrsfv2.conexion;

import java.io.StringReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
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

@Stateless
@LocalBean
public class Conexion implements HttpSessionBindingListener {
	private DataSource pool;
	private Connection conn = null;
	private Statement stmt = null;
	ResultSet rs = null;
	private PreparedStatement pstmt = null;
	private ResultSetMetaData rsmd = null;
	private DataSource driverManagerDataSource;	
	private String nombreDS;

	/**
	 * Constructor de la Clase ConexionBD. Se encarga de Asignar DataSource
	 * enviado como parametro
	 * 
	 * @param ds
	 *            DataSource Para la conexion
	 */	
	
	public Conexion(){
		nombreDS="pqrsfv2DS";
	}

	/**
	 * Inicia el dataSource Carga el contexto de la aplicacion del usuario
	 * general
	 */
	public void iniciarDataSource() {

		try {
			Context envCtx = (Context) new InitialContext().lookup("java:");
			driverManagerDataSource = (DataSource) envCtx.lookup("jboss/jdbc/"+ nombreDS);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Funcion encargada de ejecutar la sentecia sql sin el paso de parametros
	 * 
	 * @param sql
	 *            String de la setencia a ejecutar
	 * @return ArrayList contendor de la informacion solicitada a la base de
	 *         datos
	 */
	@SuppressWarnings("rawtypes")
	public ArrayList executeQuery(String sql) {
		iniciarDataSource();
		Connection conn1 = null;
		ResultSet rs = null;
		ArrayList<ArrayList<String>> a1 = new ArrayList<ArrayList<String>>();
		try {
			conn1 = driverManagerDataSource.getConnection();
			stmt = conn1.createStatement();
			rs = stmt.executeQuery(sql);
			rsmd = rs.getMetaData();
			int countColumn = rsmd.getColumnCount();
			@SuppressWarnings("unused")
			int i = 0;
			while (rs.next()) {
				i++;
				ArrayList<String> a2 = new ArrayList<String>();
				for (int j = 1; j <= countColumn; j++) {
					a2.add(rs.getString(j));
				}
				a1.add(a2);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
				}
			if (conn1 != null)
				try {
					conn1.close();
				} catch (SQLException e) {
				}
		}
		return a1;
	}

	/**
	 * Funcion encargada de ejecutar la sentecia sql con variosParmetros
	 * 
	 * @param sql
	 *            sentencia select a ejecutar
	 * @param parametros
	 *            enviados a la sentencia
	 * @return ArrayList con la informacion consultada
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public ArrayList executeQuery(String sql, ArrayList parametros) {

		iniciarDataSource();
		Connection conn1 = null;
		ResultSet rs = null;
		ArrayList a1 = new ArrayList();

		try {
			conn1 = driverManagerDataSource.getConnection();
			pstmt = conn1.prepareStatement(sql);
			if (parametros.size() > 0)
				for (int i = 0; i < parametros.size(); i++) {
					pstmt.setString(i + 1, (String) parametros.get(i));
				}
			rs = pstmt.executeQuery();
			rsmd = rs.getMetaData();
			int countColumn = rsmd.getColumnCount();
			int i = 0;
			while (rs.next()) {
				i++;
				ArrayList a2 = new ArrayList();

				for (int j = 1; j <= countColumn; j++) {
					a2.add(rs.getString(j));
				}
				a1.add(a2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
				}
			if (conn1 != null)
				try {
					conn1.close();
				} catch (SQLException e) {
				}
		}
		return a1;

	}

	/**
	 * Funcion encargada de ejecutar la sentecia sql con variosParmetros
	 * 
	 * @param sql
	 *            sentencia select a ejecutar
	 * @param parametros
	 *            enviados a la sentencia
	 * @return ResultSet con la informacion consultada
	 */
	@SuppressWarnings({ "rawtypes" })
	public ResultSet executeQueryRS(String sql, ArrayList parametros) {
		iniciarDataSource();
		ResultSet rs = null;
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
		/*
		 * finally { if (conn1 != null) try { conn1.close(); } catch
		 * (SQLException e) { } }
		 */
		return rs;
	}
	
	/**
	 * Funcion de PRUEBA
	 * 
	 * @param sql
	 *            sentencia select a ejecutar
	 * @param parametros
	 *            enviados a la sentencia
	 * @return ResultSet con la informacion consultada
	 */
	@SuppressWarnings({ "rawtypes" })
	public ResultSet executeQueryRSAjustes(String sql, ArrayList parametros) {

		iniciarDataSource();
		ResultSet rs = null;

		try {
			conn = driverManagerDataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			if (parametros.size() > 0)
				for (int i = 0; i < parametros.size(); i++) {
					pstmt.setInt(i +1, (int) parametros.get(i));
				}
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		/*
		 * finally { if (conn1 != null) try { conn1.close(); } catch
		 * (SQLException e) { } }
		 */
		return rs;
	}

	/**
	 * Funcion encargada de ejecutar la sentecia sql sin el paso de parametros
	 * 
	 * @param sql
	 *            String de la setencia a ejecutar
	 * @return ResultSet contendor de la informacion solicitada a la base de
	 *         datos
	 */

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

	/**
	 * Funcion encargada de ejecutar la sentecia sql con variosParmetros
	 * 
	 * @param sql
	 *            Sentencia select enviada
	 * @param parametros
	 *            parametros enviados a la sentencia TIPO VARCHAR
	 * @param tipos
	 *            Tipos de los parametros enviados
	 */
	@SuppressWarnings({ "rawtypes" })
	public boolean executeCall(String sql, ArrayList parametros, ArrayList tipos) {
		Connection conn1 = null;
		ResultSet rs = null;
		iniciarDataSource();
		// System.out.println("SQL: "+sql);
		boolean successCall = false;

		try {
			conn1 = driverManagerDataSource.getConnection();
			CallableStatement cs = null;
			cs = conn1.prepareCall(sql);
			int aux = 0;
			for (int i = 0; i < parametros.size(); i++) {
				aux = (Integer) tipos.get(i);
				//System.out.println("\tParametro: "+parametros.get(i));
				// cs.registerOutParameter(i+1,aux);
				if (aux == java.sql.Types.VARCHAR) {
					cs.setString(i + 1, (String) parametros.get(i));
				} else if (aux == java.sql.Types.INTEGER) {
					cs.setInt(i + 1, (Integer) parametros.get(i));
				} else if (aux == java.sql.Types.NULL) {
					cs.setNull(i + 1, java.sql.Types.INTEGER);
				} else if (aux == java.sql.Types.NUMERIC) {
					cs.setInt(i + 1, (Integer) parametros.get(i));
				} else if (aux == java.sql.Types.CHAR) {
					cs.setInt(i + 1, ((String) parametros.get(i)).charAt(0));
				} else if (aux == java.sql.Types.FLOAT) {
					cs.setFloat(i + 1, ((Float) parametros.get(i)));
				} else if (aux == java.sql.Types.DOUBLE) {
					cs.setDouble(i + 1, ((Double) parametros.get(i)));
				} else if (aux == java.sql.Types.ARRAY) {
					cs.setArray(i + 1, ((oracle.sql.ARRAY) parametros.get(i)));
				} else if (aux == java.sql.Types.DATE) {
					if ((java.util.Date) parametros.get(i) != null) {
						cs.setDate(i + 1, new java.sql.Date(
								((java.util.Date) parametros.get(i)).getTime()));
					} else {
						// Si es null, inserte null en la BD.
						cs.setDate(i + 1, null);

						/**
						 * No usar esta pieza de código. Hace imposible poner
						 * fechas en null en la base de datos, y algunas veces
						 * es necesario. La lógica de esto debe estar en alguna
						 * capa superior, no en la capa de conexión. Att, Dux.
						 */
						/*
						 * java.util.Date today = new java.util.Date();
						 * java.sql.Date sqlToday = new java.sql.Date(
						 * today.getTime()); cs.setDate(i + 1, sqlToday);
						 */
					}
				} else if (aux == java.sql.Types.TIMESTAMP) {
					if ((java.util.Date) parametros.get(i) != null) {
						Timestamp x = new Timestamp(
								((java.util.Date) parametros.get(i)).getTime());
						cs.setTimestamp(i + 1, x);

					} else {
						cs.setTimestamp(i + 1, null);
						/**
						 * No usar esta pieza de código. Hace imposible poner
						 * fechas en null en la base de datos, y algunas veces
						 * es necesario. La lógica de esto debe estar en alguna
						 * capa superior, no en la capa de conexión. Att, LUCHO.
						 * (Este comentario se lo copie a DUX)
						 */
						//java.util.Date today = new java.util.Date();
						//java.sql.Date sqlToday = new java.sql.Date(today.getTime());
						//Timestamp x = new Timestamp((sqlToday).getTime());
						

					}
				} else if (aux == java.sql.Types.CLOB) {
					//cs.setClob(i + 1, ((oracle.sql.CLOB) parametros.get(i)));
					//DUX Recibe un StringReader que después convierte a Clob.
					StringReader sr = new StringReader((String)parametros.get(i));
					cs.setClob(i + 1, sr);

				} else if (aux == java.sql.Types.OTHER) {
					cs.registerOutParameter((String) parametros.get(i),
							java.sql.Types.VARCHAR);
				}
			}
			cs.execute();
			successCall = true;
			return successCall;
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error llamando al procedimiento almacenado utilizando conexion.executeCall");
			return successCall;
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
				}
			if (conn1 != null)
				try {
					conn1.close();
				} catch (SQLException e) {
				}
		}
	}

	
	/**
	 * Funcion encargada de ejecutar la sentecia sql con variosParmetros en BATCH
	 * Se le pasa un SQL que se va a repetir, y los dos Arraylists bidimensionales.
	 * Inserta con addBatch y executeBatch
	 * 
	 * @param sql
	 *            Sentencia select enviada
	 * @param parametros
	 *            parametros enviados a la sentencia TIPO VARCHAR
	 * @param tipos
	 *            Tipos de los parametros enviados
	 */
	
	public boolean executeCallBatch(String sql, ArrayList<ArrayList<Object>> parametrosArray, ArrayList<ArrayList<Integer>> tiposArray) {
	
		int contadorArray = 0;
		//ArrayList<Object> parametros;
		ArrayList<Integer> tipos;
		
		Connection conn1 = null;
		ResultSet rs = null;
		iniciarDataSource();
		// System.out.println("SQL: "+sql);
		//boolean successCall = false;
		CallableStatement cs = null;
		int i = 0;

		try
		{
			conn1 = driverManagerDataSource.getConnection();
			cs = conn1.prepareCall(sql);
		}
		catch (SQLException e)
		{
			System.err.println("Error al crear el CallableStatement o la conexión.");
		}
		
		for (ArrayList<Object> parametros : parametrosArray)
		{
			//parametros = parametrosArray.get(contadorArray);
			tipos = tiposArray.get(contadorArray);
			contadorArray++;
			i = 0;
		
			try {
				int aux = 0;
				for (i = 0; i < parametros.size(); i++) {
					aux = (Integer) tipos.get(i);
					// System.out.println("\tParametro: "+parametros.get(i));
					// cs.registerOutParameter(i+1,aux);
					if (aux == java.sql.Types.VARCHAR) {
						cs.setString(i + 1, (String) parametros.get(i));
					} else if (aux == java.sql.Types.INTEGER) {
						cs.setInt(i + 1, (Integer) parametros.get(i));
					} else if (aux == java.sql.Types.NULL) {
						cs.setNull(i + 1, java.sql.Types.INTEGER);
					} else if (aux == java.sql.Types.NUMERIC) {
						cs.setInt(i + 1, (Integer) parametros.get(i));
					} else if (aux == java.sql.Types.CHAR) {
						cs.setInt(i + 1, ((String) parametros.get(i)).charAt(0));
					} else if (aux == java.sql.Types.FLOAT) {
						cs.setFloat(i + 1, ((Float) parametros.get(i)));
					} else if (aux == java.sql.Types.DOUBLE) {
						cs.setDouble(i + 1, ((Double) parametros.get(i)));
					} else if (aux == java.sql.Types.ARRAY) {
						cs.setArray(i + 1, ((oracle.sql.ARRAY) parametros.get(i)));
					} else if (aux == java.sql.Types.DATE) {
						if ((java.util.Date) parametros.get(i) != null) {
							cs.setDate(i + 1, new java.sql.Date(
									((java.util.Date) parametros.get(i)).getTime()));
						} else {
							cs.setDate(i + 1, null);
						}
					} else if (aux == java.sql.Types.TIMESTAMP) {
						if ((java.util.Date) parametros.get(i) != null) {
							Timestamp x = new Timestamp(
									((java.util.Date) parametros.get(i)).getTime());
							cs.setTimestamp(i + 1, x);
	
						} else {
							java.util.Date today = new java.util.Date();
							java.sql.Date sqlToday = new java.sql.Date(
									today.getTime());
							Timestamp x = new Timestamp((sqlToday).getTime());
							cs.setTimestamp(i + 1, x);
	
						}
					} else if (aux == java.sql.Types.CLOB) {
						StringReader sr = new StringReader((String)parametros.get(i));
						cs.setClob(i + 1, sr);
	
					} else if (aux == java.sql.Types.OTHER) {
						cs.registerOutParameter((String) parametros.get(i),
								java.sql.Types.VARCHAR);
					}
				}
				cs.addBatch();
				//successCall = true;
			} catch (SQLException e) {
				e.printStackTrace();
				System.err.println("Error llamando al procedimiento almacenado utilizando conexion.executeCall");
			}
		}
		try
		{
			cs.executeBatch();
			cs.close();
		}
		catch (SQLException e)
		{
			//System.err.println("Error al ejecutar batch");
			e.printStackTrace();
		}
		catch (Exception e)
		{
			//Otra cosa
			System.err.println("Excepcion en ejecutar batch");
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e){
				}
			if (conn1 != null)
				try {
					conn1.close();
				} catch (SQLException e) {
				}
		}
		return false;
	}

	

	/**
	 * Funcion encargada de ejecutar la sentecia sql con variosParmetros
	 * 
	 * @param sql
	 *            Sentencia select enviada
	 * @param parametros
	 *            parametros enviados a la sentencia TIPO VARCHAR
	 * @param tipos
	 *            Tipos de los parametros enviados
	 */
	@SuppressWarnings({ "rawtypes" })
	public String executeCallRespuesta(String sql, ArrayList parametros,
			ArrayList tipos, int posicion) {
		//System.out.println("caLLRESPUESTA");
		Connection conn1 = null;
		ResultSet rs = null;
		iniciarDataSource();
		try {
			conn1 = driverManagerDataSource.getConnection();
			CallableStatement cs = null;
			cs = conn1.prepareCall(sql);
			int aux = 0;
			for (int i = 0; i < parametros.size(); i++) {
				aux = (Integer) tipos.get(i);
				
				//System.err.print("parámetro " + i +": "+parametros.get(i) + " : ");
				// cs.registerOutParameter(i + 1, aux);
				if (aux == java.sql.Types.VARCHAR) {
					cs.setString(i + 1, (String) parametros.get(i));
				} else if (aux == java.sql.Types.INTEGER) {
					cs.setInt(i + 1, (Integer) parametros.get(i));
				} else if (aux == java.sql.Types.NUMERIC) {
					cs.setInt(i + 1, (Integer) parametros.get(i));
				} else if (aux == java.sql.Types.DOUBLE) {
					cs.setDouble(i + 1, (Double) parametros.get(i));
				} else if (aux == java.sql.Types.CHAR) {
					cs.setInt(i + 1, ((String) parametros.get(i)).charAt(0));
				} else if (aux == java.sql.Types.DATE) {
					if ((java.util.Date) parametros.get(i) != null)
						cs.setDate(i + 1, new java.sql.Date(
								((java.util.Date) parametros.get(i)).getTime()));
					else {
						cs.setDate(i + 1, null);
						/*
						 * java.util.Date today = new java.util.Date();
						 * java.sql.Date sqlToday = new java.sql.Date(
						 * today.getTime()); cs.setDate(i + 1, sqlToday);
						 */
					}

				} else if (aux == java.sql.Types.OTHER) {
					cs.registerOutParameter((String) parametros.get(i),
							java.sql.Types.VARCHAR);
				}
			}
			//System.out.println("Insertando parámetro de retorno: ("+ posicion + ")");
			cs.registerOutParameter(posicion, java.sql.Types.VARCHAR);
			cs.execute();
			return cs.getString(posicion);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
				}
			if (conn1 != null)
				try {
					conn1.close();
				} catch (SQLException e) {
				}
		}
		return null;
	}
	
	/**
	 * Funcion encargada de ejecutar la sentecia sql con variosParmetros pero retorna un INTEGER
	 * 
	 * @param sql
	 *            Sentencia select enviada
	 * @param parametros
	 *            parametros enviados a la sentencia TIPO VARCHAR
	 * @param tipos
	 *            Tipos de los parametros enviados
	 */
	@SuppressWarnings({ "rawtypes" })
	public Integer executeCallRespuestaInteger(String sql, ArrayList parametros, ArrayList tipos, int posicion) {
		Connection conn1 = null;
		ResultSet rs = null;
		iniciarDataSource();
		try {
			conn1 = driverManagerDataSource.getConnection();
			CallableStatement cs = null;
			cs = conn1.prepareCall(sql);
			int aux = 0;
			for (int i = 0; i < parametros.size(); i++) {
				aux = (Integer) tipos.get(i);
				
				Object val = parametros.get(i);
				if(val==null){
					cs.setNull(i + 1, aux);
				}else{

					//System.err.print("parámetro " + i +": "+parametros.get(i) + " : ");
					// cs.registerOutParameter(i + 1, aux);
					if (aux == java.sql.Types.VARCHAR) {
						cs.setString(i + 1, (String) parametros.get(i));
					} else if (aux == java.sql.Types.INTEGER) {
						cs.setInt(i + 1, (Integer) parametros.get(i));
					} else if (aux == java.sql.Types.NUMERIC) {
						cs.setInt(i + 1, (Integer) parametros.get(i));
					} else if (aux == java.sql.Types.CHAR) {
						cs.setInt(i + 1, ((String) parametros.get(i)).charAt(0));
					} else if (aux == java.sql.Types.DATE) {
						if ((java.util.Date) parametros.get(i) != null)
							cs.setDate(i + 1, new java.sql.Date(
									((java.util.Date) parametros.get(i)).getTime()));
						else {
							cs.setDate(i + 1, null);
							/*
							 * java.util.Date today = new java.util.Date();
							 * java.sql.Date sqlToday = new java.sql.Date(
							 * today.getTime()); cs.setDate(i + 1, sqlToday);
							 */
						}

					} else if (aux == java.sql.Types.OTHER) {
						cs.registerOutParameter((String) parametros.get(i),
								java.sql.Types.INTEGER);
					}
				}

			}
			cs.registerOutParameter(posicion, java.sql.Types.INTEGER);
			cs.execute();
			return cs.getInt(posicion);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
				}
			if (conn1 != null)
				try {
					conn1.close();
				} catch (SQLException e) {
				}
		}
		return null;
	}
    
   /** 
    * Funcion encargada de ejecutar la sentecia sql con variosParmetros 
    *  
    * @param sql 
    *            Sentencia select enviada 
    * @param parametros 
    *            parametros enviados a la sentencia TIPO VARCHAR 
    * @param tipos 
    *            Tipos de los parametros enviados 
    * @param posiciones
    * 			 Arreglo de posiciones de las variables de salida del procedimiento almacenado 
    */ 
   @SuppressWarnings({ "rawtypes" }) 
   public ArrayList<Object> executeCallRespuesta(String sql, ArrayList parametros, ArrayList tipos, ArrayList<Integer> posiciones) { 
       Connection conn1 = null; 
       ResultSet rs = null; 
       iniciarDataSource(); 
       try { 
           conn1 = driverManagerDataSource.getConnection(); 
           CallableStatement cs = null; 
           cs = conn1.prepareCall(sql); 
           int aux = 0; 
           for (int i = 0; i < parametros.size(); i++) { 
               aux = (Integer) tipos.get(i); 
               // cs.registerOutParameter(i + 1, aux); 
               if (aux == java.sql.Types.VARCHAR) { 
                   cs.setString(i + 1, (String) parametros.get(i)); 
               } else if (aux == java.sql.Types.INTEGER) { 
                   cs.setInt(i + 1, (Integer) parametros.get(i)); 
               } else if (aux == java.sql.Types.NUMERIC) { 
                   cs.setInt(i + 1, (Integer) parametros.get(i)); 
               } else if (aux == java.sql.Types.CHAR) { 
                   cs.setInt(i + 1, ((String) parametros.get(i)).charAt(0)); 
               } else if (aux == java.sql.Types.DATE) { 
                   if ((java.util.Date) parametros.get(i) != null) 
                       cs.setDate(i + 1, new java.sql.Date( 
                               ((java.util.Date) parametros.get(i)).getTime())); 
                   else { 
                       cs.setDate(i + 1, null); 
                       /* 
                        * java.util.Date today = new java.util.Date(); 
                        * java.sql.Date sqlToday = new java.sql.Date( 
                        * today.getTime()); cs.setDate(i + 1, sqlToday); 
                        */ 
                   } 
                    
               } else if (aux == java.sql.Types.OTHER) { 
                   cs.registerOutParameter((String) parametros.get(i), 
                           java.sql.Types.VARCHAR); 
               } 
                
           } 
           for (Integer posicion : posiciones) { 
               cs.registerOutParameter(posicion, (Integer)tipos.get(posicion-1)); 
           } 
            
           cs.execute(); 
            
           ArrayList<Object> resultados = new ArrayList<>(); 
           for (Integer posicion : posiciones) { 
               resultados.add(cs.getObject(posicion)); 
           } 
            
           return resultados; 
            
       } catch (SQLException e) { 
           e.printStackTrace(); 
       } finally { 
           if (rs != null) 
               try { 
                   rs.close(); 
               } catch (SQLException e) { 
               } 
           if (conn1 != null) 
               try { 
                   conn1.close(); 
               } catch (SQLException e) { 
               } 
       } 
       return null; 
   } 
	
	/**
	 * Notifica el objeto que se está obligado a una sesión y define el período
	 * de sesiones.
	 * 
	 * @param event
	 *            La identificacion del evento a crear
	 */
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

	/**
	 * Notifica el objeto que está siendo independiente de una sesión y se
	 * identifica el período de sesiones.
	 * 
	 * @param event
	 *            La identificacion del evento a crear
	 */
	public void valueUnbound(HttpSessionBindingEvent event) {
		try {
			conn.close();
		} catch (SQLException e) {
		} finally {
			conn = null;
		}
	}

	/* METODOS GET Y SET DE LA CLASE */
	public Connection getConexion() {
		try {
			// En caso que no se haya inicializado la conexión, entonces se invoca el método que inicia el datasource
			if(this.driverManagerDataSource==null) this.iniciarDataSource();
			
			return this.driverManagerDataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ResultSetMetaData getMetaData() {
		return rsmd;
	}

	public void close() {
		try {
			if(!this.conn.isClosed())this.conn.close();			    
		}catch (NullPointerException e){
			//Error de Null Pointer, la conexión ya fue cerrada.
		}		
		catch (SQLException e)
		{
			System.err.println("Error SQL al cerrar la conexión.");
		}
	}


  /** 
   * Ejecuta un procedimiento almacenado cuyo valor de retorno es un {@link ResultSet} 
   * que contiene el conjunto de datos obtenido 
   * @param sql contenido SQL del procedimiento a ejecutar 
   * @param parametros lista de parametros necesarios para la ejecución del procedimiento almacenado 
   * @return 
   * @throws SQLException  
   */ 
  public ResultSet executeCallRS(String sql,ArrayList<Object> parametros) throws SQLException{ 
       
      // Conexión para la ejecución de la consulta y ResultSet para los datos de retorno obtenidos 
      ResultSet resultado=null; 
      this.iniciarDataSource(); 
       
         // Conectar y Preparar la consulta 
         //conexion = this.getConexion(); 
      this.conn = driverManagerDataSource.getConnection(); 
         CallableStatement vSql = this.conn.prepareCall(sql); 
          
          
         // Establecer los parámetros, el primer valor es de retorno y corresponde al cursor que se asignará al resultset 
         vSql.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);  
         if(parametros!=null && parametros.size()>0){ 
          for(int i=0;i<parametros.size();i++) vSql.setObject(i+2,parametros.get(i)); 
      }     
          
         // Ejecutar la consulta 
         vSql.execute(); 
       
         // Asignación de los resultados de la consulta 
      resultado = (ResultSet)vSql.getObject(1); 
                   
      return resultado;     
    }

	public String getNombreDS() {
		return nombreDS;
	}
	
	public void setNombreDS(String nombreDS) {
		this.nombreDS = nombreDS;
	} 
  
	public void clean(){
		try {
			if(rs!=null)
				rs.close();
			stmt.close(); 
			conn.close(); 			
		}
		catch(Exception e1){
			System.out.println("ERROR. NO SE PUEDO LIMPIAR LA CONEXION (CERRRAR LOS RECURSOS)");
			e1.printStackTrace();			
		}
	}

}
