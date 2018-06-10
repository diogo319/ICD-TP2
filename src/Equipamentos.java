

import java.io.IOException;
import java.io.PrintWriter;

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
        out.println("<head><title>Equipamentos " + seccao + "</title></head>");
        out.println("<link rel=\"stylesheet\" href=\"css/main.css\" />");
		out.println("<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\">");
		
		
		if(seccao.equals("Acessorios")) {
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
					+"      <li><a id='active'>Acessórios</a></li>" 
					+"      <li><a href=\"AdicionarPecasCarrinho?idPeca=vazio\">Carrinho</a></li>" 
					+"      <li><a href=\"TerminarSessao\">Logout</a></li>" 
					+"    </ul>" 
					+"  </nav>" 
					+"</div>");

	        out.println("<body>");
        }else {
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
        }
		

        NodeList pecas = ClienteTCP.Catalogo(seccao);

    	System.out.println(pecas.getLength());
        for(int i = 0; i < pecas.getLength(); i++) {
        	String idPeca = pecas.item(i).getAttributes().getNamedItem("idPeça").getTextContent();
        	//out.println("<h1><a href='PaginaEquipamento?idPeca=" + idPeca + "'>" + pecas.item(i).getAttributes().getNamedItem("Designação").getTextContent() + "</a></h1>");
        	String image = ((Element)pecas.item(i)).getElementsByTagName("Foto").item(0).getTextContent();
        	String descricao = ((Element)pecas.item(i)).getElementsByTagName("Caracteristica").item(0).getTextContent();
        	String preco = pecas.item(i).getAttributes().getNamedItem("Preço").getTextContent() + " &euro;";
        	out.println("<table id='imagetables'><td><a href='PaginaEquipamento?idPeca=" + idPeca + "'><img src='data:image/jpg;base64, " + image + "' width='200px' height='auto'></img></a></td>");
        	out.println("<td><table id='itemtables'><tr><th>Descrição</th><td>" + descricao + "</td></tr>");
        	out.println("<tr><th>Preço</th><td align='center'>" + preco + "</td></tr>");
        	
        	if(!seccao.equals("Acessorios")) {
        		
        		NodeList tamanhos = ((Element)pecas.item(i)).getElementsByTagName("Tamanho");
            	out.println("<tr><th rowspan='" + tamanhos.getLength() +"'>Tamanhos</th><td align='center'>"+ tamanhos.item(0).getAttributes().getNamedItem("Valor").getTextContent() +"</td>");
            	
            	for(int j = 1; j < tamanhos.getLength(); j++) {
                	out.println("<tr><td align='center'>"+ tamanhos.item(j).getAttributes().getNamedItem("Valor").getTextContent() +"</td>");
            	}
        	}
        	
        	
        	out.println("</table></td></table>");
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
