

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Servlet implementation class PaginaEquipamento
 */
@WebServlet("/PaginaEquipamento")
public class PaginaEquipamento extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("text/html");
        
        PrintWriter out = response.getWriter();
		
        out.println("<html>");
        out.println("<head><title>Equipamento</title><style>\r\n"+
        		"table, th, td {\r\n" +
        		"border: 1px solid black;\r\n" +
        		"border-collapse: collapse;\r\n" +
        		"}\r\n" +
        		"th, td {\r\n" +
        		"padding: 5px;\r\n" +
        		"}\r\n" +
        		"</style></head>");
        out.println("<body>");
		String idPeca = request.getParameter("idPeca");
		
		Node peca = ClienteTCP.PecaID(idPeca);
		
		out.println("<h1>" + peca.getAttributes().getNamedItem("Designação").getTextContent() + "</h1>");
    	String image = ((Element)peca).getElementsByTagName("Foto").item(0).getTextContent();
    	String descricao = ((Element)peca).getElementsByTagName("Caracteristica").item(0).getTextContent();
    	String preco = peca.getAttributes().getNamedItem("Preço").getTextContent();
    	out.println("<img src='data:image/jpg;base64, " + image + "' width='200px' height='auto'></img>");
    	
    	out.println("<form action='Carrinho'><br><table><tr><th>Descrição</th><td>" + descricao + "</td></tr>");
    	out.println("<tr><th>Preço</th><td align='center'>" + preco + "</td></tr>");
    	
    	String seccao = peca.getAttributes().getNamedItem("Secção").getTextContent();
    	
    	if(!seccao.equals("Acessorios")){
	    	NodeList tamanhos = ((Element)peca).getElementsByTagName("Tamanho");
	    	out.println("<tr><th rowspan='" + tamanhos.getLength() +"'>Tamanhos</th><td align='center'>"+ tamanhos.item(0).getAttributes().getNamedItem("Valor").getTextContent() +"</td>");
	    	out.println("<td><input type='checkbox' name='valor' value='" + tamanhos.item(0).getAttributes().getNamedItem("Valor").getTextContent() + "'></input></td></tr>");
	    	
	    	for(int j = 1; j < tamanhos.getLength(); j++) {
	        	out.println("<tr><td align='center'>"+ tamanhos.item(j).getAttributes().getNamedItem("Valor").getTextContent() +"</td>");
	        	out.println("<td><input type='checkbox' name='valor' value='" + tamanhos.item(j).getAttributes().getNamedItem("Valor").getTextContent() + "'" + tamanhos.item(j).getAttributes().getNamedItem("Valor").getTextContent() + "'></input></td></tr>");
	    	}
			
    	}
    	else {
    		out.println("");
    	}
    	
    	out.println("</table><br>");		
    	out.println("<input type='hidden' name='idPeca' value='" + idPeca + "'></input>");
    	out.println("<input type='submit' value='Adicionar Carrinho'></input>");
		out.println("</form></body></html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
