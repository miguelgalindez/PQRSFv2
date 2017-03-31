package co.edu.unicauca.pqrsfv2.util;

import java.io.IOException;
import java.util.ArrayList;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.json.JSONObject;
import co.edu.unicauca.pqrsfv2.control.NavigationControl;

/**
 * Clase que determina la redireccion de la navegacion en caso de que expire la sesion
 * 
 * @version 1.0 15 Abr 2013
 * @author DuX
 * @author Ing. Victor Zapata
 * 
 */

public class UserFilter implements Filter {
	
	@Inject
	GoogleAuthHelper googleAuthHelper;
	@Inject
	NavigationControl navigationControl;
	String loginURL;
	
    @Override
    public void init(FilterConfig config) throws ServletException {
        // If you have any <init-param> in web.xml, then you could get them
        // here by config.getInitParameter("name") and assign it as field.
    	loginURL=googleAuthHelper.buildLoginUrl();
    }
    /**
     * <p>Indica si la url recibida como par�metro est� contenida en alguna de
     * las urls que forman parte del arreglo de urls que se reciben como par�metro.</p>
     * <p>De una forma m�s gen�rica, �ste m�todo retorna verdadero si al menos uno de los elementos
     * del arreglo de urls est� contenida en la url recibida como par�metro.</p>
     * @param urls arreglo que contiene las urls que se comparar�n con la url actual 
     * @param url url actual contra la que se comparar� cada una de las urls del arreglo
     * @return true si alguna de las cadenas de texto del arreglo <code>urls</code> est� contenida en la cadena <code>url</code>. falso en otro caso
     */
    private boolean contains(ArrayList<String> urls, String url){
    	if(urls == null || url==null) return false;
    	for (String tmp : urls) if(tmp !=null && url.contains(tmp)) return true;
    	return false;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;                       
        request.setCharacterEncoding("UTF-8");
               
        String principalURI = "/admin/principal.xhtml";
        String url = request.getRequestURL().toString();
        String urlForbidden=request.getContextPath()+"/forbidden.xhtml";
        
        ArrayList<String> urlsAnomimas = new ArrayList<String>();       
        urlsAnomimas.add(request.getContextPath()+"/index.html");
        urlsAnomimas.add(request.getContextPath()+"/index.xhtml");
        urlsAnomimas.add(urlForbidden);                      
        
        if(request.getRequestURI().equals(loginURL) || this.contains(urlsAnomimas, request.getRequestURI())
    		|| (url.indexOf(".css.xhtml") > 0) 																
    		|| (url.indexOf(".js.xhtml") > 0) 
    		|| (url.indexOf(".png.xhtml") > 0) 
    		|| (url.indexOf(".jpg.xhtml") > 0)
    		|| (url.indexOf(".svg.xhtml") > 0)
    		|| (url.indexOf(".woff2.xhtml") > 0)
    		|| (url.indexOf(".woff.xhtml") > 0)
    		|| (url.indexOf(".ttf.xhtml") > 0)
    		|| (url.indexOf(".gif.xhtml") > 0)){
    			chain.doFilter(req, res);
    	}
        else{
        	if (navigationControl.getUsuarioAutenticado()==null && request.getParameter("code") == null){        		        
            	if ("partial/ajax".equals(request.getHeader("Faces-Request"))) {
            	    // Entra a esta condici�n si es una petici�n AJAX, y redirige con XML.        		        	
            		response.setContentType("text/xml");
                	response.getWriter()
                		.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
                	    .printf("<partial-response><redirect url=\""+loginURL+"\"></redirect></partial-response>", url);
            	}
            	else{            
            		response.sendRedirect(loginURL);
            	}        
            }
        	else{
        		if(navigationControl.getUsuarioAutenticado()==null){
	        			    			
	    			JSONObject jsonObject = new JSONObject(googleAuthHelper.getUserInfoJson(request.getParameter("code")));
	    			boolean authorizedUser=navigationControl.isAuthorizedUser(jsonObject.getString("email"), jsonObject.getString("name"), jsonObject.getString("link"), jsonObject.getString("picture"));
	    			
	    			if(authorizedUser == false)	    				
	    				response.sendRedirect(urlForbidden);
	    			
	    			else{
	    				
	    				if (!request.getRequestURI().equals(principalURI) && !(this.contains(urlsAnomimas, request.getRequestURI()))){
							
							// Encontr� usuario logueado, pero est� en index (O ALG�N OTRO LUGAR QUE NO SEA PRINCIPAL): redirige a /principal.xhtml				
							if ("partial/ajax".equals(request.getHeader("Faces-Request"))){
							    // Entra a esta condici�n si es una petici�n AJAX, y redirige con XML.
								response.setContentType("text/xml");
						    	response.getWriter()
						    		.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
						    	    .printf("<partial-response><redirect url=\""+request.getContextPath()+principalURI+"\"></redirect></partial-response>", url);
							}
							else{
				        		//Entra a esta condici�n si es una petici�n normal y redirige.
				        		response.sendRedirect(request.getContextPath() +principalURI);
				        	}
	    				}	    				    			    			
					}	    				    				    				
	        	}
        		chain.doFilter(req, res);
        	}               
        }    
    }
    
    
	/*String googleError=request.getParameter("error");
	if (googleError!=null){
		System.err.println("Autenticacion fallida. Error reportado por Google: "+googleError);
		response.sendRedirect(loginURL);
	}
	  */

    @Override
    public void destroy() {
        // If you have assigned any expensive resources as field of
        // this Filter class, then you could clean/close them here.
    }

}