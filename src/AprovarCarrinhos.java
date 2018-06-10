

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.NodeList;

/**
 * Servlet implementation class AprovarCarrinhos
 */
@WebServlet("/AprovarCarrinhos")
public class AprovarCarrinhos extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
        
        PrintWriter out = response.getWriter();
		
        out.println("<html>");
       
		out.println("<head><title>Carrinhos Aprovados</title></head><body>");
		out.println("<link rel=\"stylesheet\" href=\"css/main.css\" />");
		
		String[] nifs = request.getParameterValues("nif");

		out.println("<h1>Clientes com carrinhos aprovados:</h1>");
		
		for(int i = 0; i < nifs.length; i++) {
			ClienteTCP.AprovarCarrinho(nifs[i]);
			out.println("<h2>" + nifs[i] + "</h2>");

		}
		
		out.println("<h3><a href='ConsultarCarrinhos'>Voltar à página dos Carrinhos por Aprovar</a></h3>");
		
		out.println("</body></html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
