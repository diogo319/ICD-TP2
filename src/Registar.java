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
				
		out.println("<body><form action='NovoUtilizador'>");
		out.println("NIF: <input type='text' placeholder='1234' name='nif'></input><br>");
        out.println("Nome: <input type='text' name='nome'></input><br>");
        out.println("Data Nascimento: <input type='date' name='dataNasc' required></input><br>");
        out.println("<input type='submit' value='Registar'></input><br>");
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
