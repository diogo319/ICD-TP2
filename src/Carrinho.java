

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
 * Servlet implementation class Carrinho
 */
@WebServlet("/Carrinho")
public class Carrinho extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
        
        PrintWriter out = response.getWriter();
        
        out.println("<html>");
        out.println("<head><title>Carrinho</title></head>");
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
        
        String[] idPeca = request.getParameterValues("idPeca");
        String[] tamanhos = request.getParameterValues("valor");
        
        for(int i = 0; i < idPeca.length; i++) {
    		Node peca = ClienteTCP.PecaID(idPeca[i]);
    		
    		out.println("<div class='corpo'><h1>" + peca.getAttributes().getNamedItem("Designação").getTextContent() + "</h1></div>");
        	String image = ((Element)peca).getElementsByTagName("Foto").item(0).getTextContent();
        	String descricao = ((Element)peca).getElementsByTagName("Caracteristica").item(0).getTextContent();
        	
        	out.println("<img class='imagens' src='data:image/jpg;base64, " + image + "' width='200px' height='auto'></img>");
        	
        	out.println("<form action='AdicionarPecasCarrinho?idPeca=" + idPeca[i] + "'><br><table id='itemtablesdois'>");
        	
        	String seccao = peca.getAttributes().getNamedItem("Secção").getTextContent();
        	
        	if(!seccao.equals("Acessorios")){
    	    	//NodeList tamanhos = ((Element)peca).getElementsByTagName("Tamanho");
    	    	out.println("<tr><th rowspan='" + tamanhos.length +"'>Tamanhos</th><td align='center'>"+ tamanhos[0] +"</td>");
    	    	out.println("<input type='hidden' name='valor' value='" + tamanhos[0] + "'></input>");
    	    	out.println("<td><input type='number' name='quantidade'></input></td></tr>");
    	    	
    	    	for(int j = 1; j < tamanhos.length; j++) {
    	        	out.println("<tr><td align='center'>"+ tamanhos[j] +"</td>");
        	    	out.println("<input type='hidden' name='valor' value='" + tamanhos[j] + "'></input>");
    	        	out.println("<td><input type='number' name='quantidade'></input></td></tr>");
    	    	}
    			
        	}else {

        		out.println("<input type='hidden' name='valor'></input>");    	    	
	        	out.println("<tr><td align='center'>Quantidade</td>");
	        	out.println("<td><input type='number' name='quantidade'></input></td></tr>");
	    	
        	}
        	
        	out.println("</table><br>");		
        	out.println("<input type='hidden' name='idPeca' value='" + idPeca[i] + "'></input>");
        	out.println("<input class='button' type='submit' value='Adicionar Carrinho'></input></form>");
        }
        
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
