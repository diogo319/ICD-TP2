

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
 * Servlet implementation class ModificarPeca
 */
@WebServlet("/ModificarPeca")
public class ModificarPeca extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		
		String idPecaAlterar = request.getParameter("idPeca");
		
        out.println("<html>");
        out.println("<head><title>Modificar Pe�a</title></head><body><h1>Modificar Pe�a:</h1>");
        
        NodeList pecasHomem = ClienteTCP.Catalogo("Homem");
        
        out.println("<form><select name='idPeca' onchange='this.form.submit()'>");

    	out.println("<option disabled>Homem</option>");
        for(int i = 0; i < pecasHomem.getLength(); i++) {
        	String idPeca = pecasHomem.item(i).getAttributes().getNamedItem("idPe�a").getTextContent();
        	String designacao = pecasHomem.item(i).getAttributes().getNamedItem("Designa��o").getTextContent();
        	out.println("<option value='" + idPeca + "'>" + idPeca + " - " + designacao + "</option>");
        }
        
        NodeList pecasMulher = ClienteTCP.Catalogo("Mulher");
        
        out.println("<option disabled>Mulher</option>");
        for(int i = 0; i < pecasMulher.getLength(); i++) {
        	String idPeca = pecasMulher.item(i).getAttributes().getNamedItem("idPe�a").getTextContent();
        	String designacao = pecasMulher.item(i).getAttributes().getNamedItem("Designa��o").getTextContent();
        	out.println("<option value='" + idPeca + "'>" + idPeca + " - " + designacao + "</option>");
        }
        
        NodeList pecasCrianca = ClienteTCP.Catalogo("Crianca");
        
        out.println("<option disabled>Crian�a</option>");
        for(int i = 0; i < pecasCrianca.getLength(); i++) {
        	String idPeca = pecasCrianca.item(i).getAttributes().getNamedItem("idPe�a").getTextContent();
        	String designacao = pecasCrianca.item(i).getAttributes().getNamedItem("Designa��o").getTextContent();
        	out.println("<option value='" + idPeca + "'>" + idPeca + " - " + designacao + "</option>");
        }
        
        NodeList pecasAcessorios = ClienteTCP.Catalogo("Acessorios");
        
        out.println("<option disabled>Acess�rios</option>");
        for(int i = 0; i < pecasAcessorios.getLength(); i++) {
        	String idPeca = pecasAcessorios.item(i).getAttributes().getNamedItem("idPe�a").getTextContent();
        	String designacao = pecasAcessorios.item(i).getAttributes().getNamedItem("Designa��o").getTextContent();
        	out.println("<option value='" + idPeca + "'>" + idPeca + " - " + designacao + "</option>");
        }
        
        out.println("</select></form>");
        if(idPecaAlterar != null) {
        	//TODO por aqui os campos para alterar
        	Node peca = ClienteTCP.PecaID(idPecaAlterar);
        	String seccao = peca.getAttributes().getNamedItem("Sec��o").getTextContent();
        	String designacao = peca.getAttributes().getNamedItem("Designa��o").getTextContent();
        	String preco = peca.getAttributes().getNamedItem("Pre�o").getTextContent();
        	out.println("<h3>" + designacao + "</h3><form action='AlterarParametros'>");
        	out.println("<input type='hidden' name='idPeca' value='" + idPecaAlterar + "'></input>");
        	out.println("<input type='hidden' name='precoAntigo' value='" + preco + "'></input>");
        	out.println("Pre�o: <input type='number' name='precoNovo' value='" + preco + "'></input>");
            //TODO fazer para acessorios tambem
            if(!seccao.equals("Acessorios")){
            	out.println("<p>Tamanhos:</p>");
            	NodeList tamanhos = ((Element)peca).getElementsByTagName("Tamanho");
            	for(int i = 0; i < tamanhos.getLength(); i++) {
            		String valor = tamanhos.item(i).getAttributes().getNamedItem("Valor").getTextContent();
            		String quantidade = tamanhos.item(i).getAttributes().getNamedItem("Quantidade").getTextContent();
            		out.println("<input type='hidden' name='valor' value='" + valor + "'></input>");
            		out.println("<input type='hidden' name='quantidadeAntiga' value='" + quantidade + "'></input>");
            		out.println(valor + ": <input type='number' name='quantidadeNova' value='" + quantidade + "'></input>");
            	}
            }

            out.println("<br><br><input type='submit' value='Alterar Pe�a'></input></form>");
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
