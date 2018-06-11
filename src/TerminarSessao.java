import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/TerminarSessao")
public class TerminarSessao extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException 
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		ClienteTCP.utilizador = null;
		
		out.println("<html><head><title>Sessão Terminada</title></head>");
		out.println("<link rel=\"stylesheet\" href=\"css/main.css\" />");
        
        out.println("<div class=\"cabecalho\"><img id=\"logo\" src=\"images/RDDSports.png\"/>"
        		+ "<nav><ul><li><a href=\"Login\">Login</a></li><li><a href=\"Registar\">Registar</a>"
        		+ "</li></ul></nav></div>");
		
		out.println("</html>");
	}
	
	@Override
    public void doPost(HttpServletRequest request,
                      HttpServletResponse response)
        throws IOException, ServletException
    {
        doGet(request, response);
    }
}
