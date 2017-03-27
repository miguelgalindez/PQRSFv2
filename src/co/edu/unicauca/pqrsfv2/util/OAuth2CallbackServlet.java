package co.edu.unicauca.pqrsfv2.util;

import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns={"/oauth2callback", asyncSupported=true) 
public class OAuth2CallbackServlet extends HttpServlet { @Override 
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
      throws IOException, ServletException {
      //Check if the user have rejected 
      String error = req.getParameter("error"); 
      if ((null != error) && ("access_denied".equals(error.trim())) { 
         HttpSession sess = req.getSession(); 
         sess.invalidate(); 
         resp.sendRedirect(req.getContextPath()); 
         return; 
      }
      //OK the user have consented so lets find out about the user 
      AsyncContext ctx = req.startAsync(); 
      ctx.start(new GetUserInfo(req, resp, asyncCtx)); 
   } 
}
