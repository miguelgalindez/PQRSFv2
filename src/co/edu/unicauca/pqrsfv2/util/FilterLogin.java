package co.edu.unicauca.pqrsfv2.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/admin/*")
public class FilterLogin extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String CLIENT_ID = "client id here"; 
	private static final String CLIENT_SECRET = "client secret here";
	private static final GoogleAuthHelper helper = new GoogleAuthHelper();
	   
	@Override 	   
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
	      //Configure 
		HttpSession session = req.getSession();		
		if (req.getParameter("code") == null || req.getParameter("state") == null) {
				
			session.setAttribute("state", helper.getStateToken());
			res.sendRedirect(helper.buildLoginUrl()); 			
		}
		else{
			if (req.getParameter("code") != null && req.getParameter("state") != null && req.getParameter("state").equals(session.getAttribute("state"))){
				session.removeAttribute("state");
			}
		}		
	} 

}
