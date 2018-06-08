

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
        out.println("<body>");
        
        String[] idPeca = request.getParameterValues("idPeca");
        String[] tamanhos = request.getParameterValues("valor");
        
        for(int i = 0; i < idPeca.length; i++) {
    		Node peca = ClienteTCP.PecaID(idPeca[i]);
    		
    		out.println("<h1>" + peca.getAttributes().getNamedItem("Designação").getTextContent() + "</h1>");
        	String image = ((Element)peca).getElementsByTagName("Foto").item(0).getTextContent();
        	String descricao = ((Element)peca).getElementsByTagName("Caracteristica").item(0).getTextContent();
        	String preco = peca.getAttributes().getNamedItem("Preço").getTextContent();
        	out.println("<img src='data:image/jpg;base64, " + image + "' width='200px' height='auto'></img>");
        	
        	out.println("<form action='AdicionarPecasCarrinho?idPeca=" + idPeca[i] + "'><br><table>");
        	out.println("<tr><th>Preço</th><td align='center'>" + preco + "</td></tr>");
        	
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
    			
        	}
        	
        	out.println("</table><br>");		
        	out.println("<input type='hidden' name='idPeca' value='" + idPeca[i] + "'></input>");
        	out.println("<input type='submit' value='Adicionar Carrinho'></input></form>");
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
