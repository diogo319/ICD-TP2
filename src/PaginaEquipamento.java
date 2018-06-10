

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
        out.println("<link rel=\"stylesheet\" href=\"css/main.css\" />");
		out.println("<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\">");

		out.println("<div class=\"cabecalho\">" 
				+"  <img id=\"logo\" src=\"images/RDDSports.png\"/>" 
				+"  <nav>" 
				+"    <ul>" 
				+"      <li><a href='Utilizador?nif=" + ClienteTCP.utilizador.getAttributes().getNamedItem("NIF").getTextContent() + "'>Home</a></li>" 
				+"      <li><div class=\"dropdown\"><button class=\"dropbtn\">Equipamentos <i class=\"fa fa-caret-down\"></i></button>" 
				+"        <div class=\"dropdown-content\">" 
				+"          <a href=\"Equipamentos?seccao=Homem\">Homem</a>" 
				+"          <a href=\"Equipamentos?seccao=Mulher\">Mulher</a>" 
				+"          <a href=\"Equipamentos?seccao=Crianca\">Criança</a>" 
				+"        </div>" 
				+"      </div>"
				+"      </li>" 
				+"      <li><a href=\"Equipamentos?seccao=Acessorios\">Acessórios</a></li>" 
				+"      <li><a href=\"AdicionarPecasCarrinho?idPeca=vazio\">Carrinho</a></li>" 
				+"      <li><a href=\"TerminarSessao\">Logout</a></li>" 
				+"    </ul>" 
				+"  </nav>" 
				+"</div>");

		
        out.println("<body>");
		String idPeca = request.getParameter("idPeca");
		
		Node peca = ClienteTCP.PecaID(idPeca);
		
		//out.println("<h1>" + peca.getAttributes().getNamedItem("Designação").getTextContent() + "</h1>");
    	String image = ((Element)peca).getElementsByTagName("Foto").item(0).getTextContent();
    	String descricao = ((Element)peca).getElementsByTagName("Caracteristica").item(0).getTextContent();
    	String preco = peca.getAttributes().getNamedItem("Preço").getTextContent();
    	out.println("<div class='imagens'><img src='data:image/jpg;base64, " + image + "' width='200px' height='auto'></img></div>");
    	
    	out.println("<form action='Carrinho'><br><table id='itemtablesdois'><tr><th>Descrição</th><td>" + descricao + "</td></tr>");
    	out.println("<tr><th>Preço</th><td align='center'>" + preco + "&euro;" + "</td></tr>");
    	
    	String seccao = peca.getAttributes().getNamedItem("Secção").getTextContent();
    	
    	if(!seccao.equals("Acessorios")){
	    	NodeList tamanhos = ((Element)peca).getElementsByTagName("Tamanho");
	    	out.println("<tr><th rowspan='" + tamanhos.getLength() +"'>Tamanhos</th><td align='center'>"+ tamanhos.item(0).getAttributes().getNamedItem("Valor").getTextContent() +"</td>");
	    	out.println("<td><input type='checkbox' name='valor' value='" + tamanhos.item(0).getAttributes().getNamedItem("Valor").getTextContent() + "'></input></td></tr>");
	    	
	    	for(int j = 1; j < tamanhos.getLength(); j++) {
	        	out.println("<tr><td align='center'>"+ tamanhos.item(j).getAttributes().getNamedItem("Valor").getTextContent() +"</td>");
	        	out.println("<td><input type='checkbox' name='valor' value='" + tamanhos.item(j).getAttributes().getNamedItem("Valor").getTextContent() + "'></input></td></tr>");
	    	}
			
    	}
    	else {
    		out.println("");
    	}
    	
    	out.println("</table>");		
    	out.println("<input type='hidden' name='idPeca' value='" + idPeca + "'></input>");
    	out.println("<input class='button2' type='submit' value='Adicionar Carrinho' ></input>");
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
