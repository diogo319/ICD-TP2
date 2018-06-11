import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Registar")
public class Registar extends HttpServlet {

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
				
		out.println("<html><head><title>Registar</title></head>");
		out.println("<link rel=\"stylesheet\" href=\"css/main.css\" />");
		
		out.println("<div class=\"cabecalho\"><img id=\"logo\" src=\"images/RDDSports.png\"/>"
        		+ "<nav><ul><li><a href=\"Login\">Login</a></li><li><a id=\"active\">Registar</a>"
        		+ "</li></ul></nav></div>");
				
		out.println("<body><form id=\"loginForm\" action='NovoUtilizador'>");
        out.println("<input class=\"input\" type='text' placeholder='Insira o seu NIF' name='nif' required></input>");
        out.println("<input class=\"input\" type='text' placeholder='Primeiro e Ultimo Nome' name='nome' required></input>");
        out.println("<input  class=\"input\" type='date' placeholder='Data de Nascimento' name='dataNasc' required></input>");
        out.println("<input class=\"button\" type='submit' value='Registar'></input>");
        out.println("</form>");

        out.println("</body>");
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
