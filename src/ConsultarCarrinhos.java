

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Servlet implementation class ConsultarCarrinhos
 */
@WebServlet("/ConsultarCarrinhos")
public class ConsultarCarrinhos extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
        
        PrintWriter out = response.getWriter();
        out.println("<html>");

		out.println("<head><title>Carrinhos Por Aprovar</title><style>\r\n" + 
				"table, th, td {\r\n" + 
				"    border: 1px solid black;\r\n" + 
				"    border-collapse: collapse;\r\n" + 
				"}\r\n" + 
				"th, td {\r\n" + 
				"    padding: 5px;\r\n" + 
				"}\r\n" + 
				"</style></head>");
		out.println("<link rel=\"stylesheet\" href=\"css/main.css\" />");
		
		out.println("<body><h1>Carrinhos por aprovar:</h1>");
		out.println("<form action='AprovarCarrinhos'>");
		NodeList carrinhos = ClienteTCP.mostrarTodosCarrinhos();
		for(int i = 0; i < carrinhos.getLength(); i++) {
			out.println("<h2>" + carrinhos.item(i).getAttributes().getNamedItem("nif").getTextContent() + ":</h2>");
			out.println("<input type='checkbox' name='nif' value='" + carrinhos.item(i).getAttributes().getNamedItem("nif").getTextContent() +"'></input>");
			out.println("<table><tr><th>ID</th><th>Tamanho</th><th>Quantidade</th></tr>");
			NodeList pecasCarrinho = carrinhos.item(i).getChildNodes();
			for(int j = 0; j < pecasCarrinho.getLength(); j++) {
				String idPeca = pecasCarrinho.item(j).getAttributes().getNamedItem("ID").getTextContent();
				String tamanho = pecasCarrinho.item(j).getAttributes().getNamedItem("Tamanho").getTextContent();
				String quantidade = pecasCarrinho.item(j).getAttributes().getNamedItem("Quantidade").getTextContent();
				//Node peca = ClienteTCP.PecaByID(idPeca);
				out.println("<tr><th>" + idPeca +"</th><th>" + tamanho + "</th><th>" + quantidade + "</th></tr>");
				//out.println("<h1>" + peca.getAttributes().getNamedItem("Designação").getTextContent() + "</h1>");
			}
			out.println("</table>");
			
			out.println("<br>");
		}
		out.println("<input type='submit' value='Aprovar'></input>");
		out.println("<form></body></html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
