

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Servlet implementation class Equipamentos
 */
@WebServlet("/Equipamentos")
public class Equipamentos extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
        
        PrintWriter out = response.getWriter();
        String seccao = request.getParameter("seccao");
        out.println("<html>");
        out.println("<head><title>Equipamentos " + seccao + "</title><style>\r\n"+
        		"table, th, td {\r\n" +
        		"border: 1px solid black;\r\n" +
        		"border-collapse: collapse;\r\n" +
        		"}\r\n" +
        		"th, td {\r\n" +
        		"padding: 5px;\r\n" +
        		"}\r\n" +
        		"</style></head>");
        out.println("<body>");
        NodeList pecas = ClienteTCP.Catalogo(seccao);
        
        for(int i = 0; i < pecas.getLength(); i++) {
        	out.println("<h1>" + pecas.item(i).getAttributes().getNamedItem("Designação").getTextContent() + "</h1>");
        	String image = ((Element)pecas.item(i)).getElementsByTagName("Foto").item(0).getTextContent();
        	String descricao = ((Element)pecas.item(i)).getElementsByTagName("Caracteristica").item(0).getTextContent();
        	String preco = pecas.item(i).getAttributes().getNamedItem("Preço").getTextContent();
        	out.println("<img src='data:image/jpg;base64, " + image + "' width='200px' height='auto'></img>");
        	out.println("<table><tr><th>Descrição</th><td>" + descricao + "</td></tr>");
        	out.println("<tr><th>Preço</th><td align='center'>" + preco + "</td></tr>");
        	
        	NodeList tamanhos = ((Element)pecas.item(i)).getElementsByTagName("Tamanho");
        	out.println("<tr><th rowspan='" + tamanhos.getLength() +"'>Tamanhos</th><td align='center'>"+ tamanhos.item(0).getAttributes().getNamedItem("Valor").getTextContent() +"</td>");
        	out.println("<td><input type='checkbox' name='valor'></input></td></tr>");
        	
        	for(int j = 1; j < tamanhos.getLength(); j++) {
            	out.println("<tr><td align='center'>"+ tamanhos.item(j).getAttributes().getNamedItem("Valor").getTextContent() +"</td>");
            	out.println("<td><input type='checkbox' name='valor'></input></td></tr>");
        	}
        	
        	out.println("</table><br>");
        }
        
        out.println("<br><input type='submit' value='Adicionar Carrinho'></input>");
        
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
