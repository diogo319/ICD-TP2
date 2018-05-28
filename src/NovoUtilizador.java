import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/NovoUtilizador")
public class NovoUtilizador extends HttpServlet {

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
				
		String nif = request.getParameter("nif");
		String nome = request.getParameter("nome");
		String dataNasc = request.getParameter("dataNasc");
		
		out.println("<html><head><title>Registar</title></head>");
				
		out.println("<body>");
		if(ClienteTCP.validarNif(nif) && ClienteTCP.Registar(nif, nome, dataNasc)) {
			
			//ClienteTCP.Login(nif);
			out.println("<h3>Bem-vindo " + nome + "</h3>");
			out.println("<h3><a href='Utilizador?nif=" + nif + "'>Login</a></h3>");
	
		}
		
		else {
			out.println("<h3>Erro a Registar. Tente de Novo.</h3>");
			out.println("<h3><a href='index.html'>Voltar � Home Page</a></h3>");
		}
		
		
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