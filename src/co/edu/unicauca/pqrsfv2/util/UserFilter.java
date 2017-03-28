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
import javax.servlet.http.HttpSession;

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
	String loginURL;
	
    @Override
    public void init(FilterConfig config) throws ServletException {
        // If you have any <init-param> in web.xml, then you could get them
        // here by config.getInitParameter("name") and assign it as field.
    	loginURL=googleAuthHelper.buildLoginUrl();
    }
    /**
     * <p>Indica si la url recibida como parámetro está contenida en alguna de
     * las urls que forman parte del arreglo de urls que se reciben como parámetro.</p>
     * <p>De una forma más genérica, éste método retorna verdadero si al menos uno de los elementos
     * del arreglo de urls está contenida en la url recibida como parámetro.</p>
     * @param urls arreglo que contiene las urls que se compararán con la url actual 
     * @param url url actual contra la que se comparará cada una de las urls del arreglo
     * @return true si alguna de las cadenas de texto del arreglo <code>urls</code> está contenida en la cadena <code>url</code>. falso en otro caso
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
        HttpSession session = request.getSession();
        
        //Con esta línea se pone todo el contenido en UTF-8
        request.setCharacterEncoding("UTF-8");
               
        String principalURL = googleAuthHelper.getCallbackUri();
        String url = request.getRequestURL().toString();
        
        /*
         * Listado de URLs a las que se puede acceder sin que se haya iniciado sesión.
         * Si un usuario registrado intenta ingresar a una de esta URLs será redireccionado a principal.xhtml
         */
        ArrayList<String> urlsAnomimas = new ArrayList<String>();
        
        urlsAnomimas.add(request.getContextPath()+"/index.xhtml");
        //urlsAnomimas.add(request.getContextPath()+"/recuperarClave.xhtml");
        
        if (
        	  (request.getParameter("code") == null || request.getParameter("state") == null) &&					//Si no hay sesión...
        		(!request.getRequestURI().equals(loginURL)) &&													//Y no está en login...
        		(!this.contains(urlsAnomimas, request.getRequestURI())) &&                                      // Y no es una URL permitida
        		!(url.indexOf(".css.xhtml") > 0) &&																//Y no es ni css ni js ni png.
        		!(url.indexOf(".js.xhtml") > 0) &&
        		!(url.indexOf(".png.xhtml") > 0) &&
        		!(url.indexOf(".jpg.xhtml") > 0) &&
        		!(url.indexOf(".gif.xhtml") > 0)
        	)
        {
        	session.setAttribute("state", googleAuthHelper.getStateToken());
        	
        	if ("partial/ajax".equals(request.getHeader("Faces-Request"))) {
        	    // Entra a esta condición si es una petición AJAX, y redirige con XML.        		        	
        		response.setContentType("text/xml");
            	response.getWriter()
            		.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
            	    .printf("<partial-response><redirect url=\""+loginURL+"\"></redirect></partial-response>", url);
        	}
        	else
        	{
        		//Entra a esta condición si es una petición normal, y redirige.
        		response.sendRedirect(loginURL); // No encontró usuario logueado.
        	}        
        }
        else if (request.getParameter("code") != null && request.getParameter("state") != null && 
        			request.getParameter("state").equals(session.getAttribute("state")
        		) &&
        		!request.getRequestURI().equals(principalURL) &&
        		!(this.contains(urlsAnomimas, request.getRequestURI())) &&
                !(url.indexOf(".css.xhtml") > 0) &&					
                !(url.indexOf(".js.xhtml") > 0) &&
                !(url.indexOf(".png.xhtml") > 0) &&
                !(url.indexOf(".jpg.xhtml") > 0) &&
        		!(url.indexOf(".gif.xhtml") > 0) 
            )
        {
        	session.removeAttribute("state");
        	System.out.println("Autenticacion exitosa");
        	System.out.println(googleAuthHelper.getUserInfoJson(request.getParameter("code")));
        	// Encontró usuario logueado, pero está en index (O ALGÚN OTRO LUGAR QUE NO SEA PRINCIPAL): redirige a /principal.xhtml
        	if ("partial/ajax".equals(request.getHeader("Faces-Request")))
        	{
        	    // Entra a esta condición si es una petición AJAX, y redirige con XML.
        		response.setContentType("text/xml");
            	response.getWriter()
            		.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
            	    .printf("<partial-response><redirect url=\""+principalURL+"\"></redirect></partial-response>", url);
        	}
        	else
        	{
        		//Entra a esta condición si es una petición normal y redirige.
        		response.sendRedirect(principalURL);
        	}
        }
        else
        {
        	if ((url.indexOf(".css.xhtml") > 0) ||					
                (url.indexOf(".js.xhtml") > 0) ||
                (url.indexOf(".png.xhtml") > 0) ||
                (url.indexOf(".jpg.xhtml") > 0) ||
        		(url.indexOf(".gif.xhtml") > 0))
        	{
        		//Cachea los archivos estáticos
        		response.setHeader("Cache-Control", "public"); // HTTP 1.1.
                response.setHeader("Pragma", "cache"); // HTTP 1.0.
                response.setDateHeader("Expires", System.currentTimeMillis() + 900000L); // Expira en 15 minutos
        	}
        	else
        	{
        		//No cachea los archivos XHTML
        		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        		response.setHeader("Connection", "Keep-Alive");
        		response.setDateHeader("Expires", 0); // Proxies.
        	}
        	session.removeAttribute("state");
       		chain.doFilter(req, res); // Encontró usuario logueado (o es un archivo js/css/png) (o es una URL permitida) y continua.
        }
    
    }
    

    @Override
    public void destroy() {
        // If you have assigned any expensive resources as field of
        // this Filter class, then you could clean/close them here.
    }

}