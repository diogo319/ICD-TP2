import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Utilizador")
public class Utilizador extends HttpServlet {
	
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
        out.println("<html>");
        String nif = request.getParameter("nif");
        ClienteTCP.Login(nif);
       
		out.println("<head><title>" + nif + "</title></head>");
		out.println("<link rel=\"stylesheet\" href=\"css/main.css\" />");
		out.println("<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\">");
		
        if(ClienteTCP.utilizador != null) {
            String nome = ClienteTCP.utilizador.getAttributes().getNamedItem("Nome").getTextContent();
			String tipoUtilizador = ClienteTCP.utilizador.getChildNodes().item(0).getNodeName();
			
			if(tipoUtilizador.equals("Cliente")){
				
				out.println("<div class=\"cabecalho\">" 
						+"  <img id=\"logo\" src=\"images/RDDSports.png\"/>" 
						+"  <nav>" 
						+"    <ul>" 
						+"      <li><a id=\"active\">Home</a></li>" 
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
				
				out.println("<body><div class='corpo'><h2>Bem-vindo "+ nome + "</<h2></div");
			}
			
			else if(ClienteTCP.utilizador.getChildNodes().item(0).getAttributes().getNamedItem("Local").getTextContent().equals("Loja")) {
				
				
				out.println("<div class=\"cabecalho\">" 
						+"  <img id=\"logo\" src=\"images/RDDSports.png\"/>" 
						+"  <nav>" 
						+"    <ul>" 
						+"      <li><a id=\"active\">Home</a></li>" 
						+"      <li><a href=\"InserirPeca\">Inserir Peça</a></li>" 
						+"      <li><a href=\"ModificarPeca\">Modificar Peça</a></li>" 
						+"      <li><a href=\"TerminarSessao\">Logout</a></li>" 
						+"    </ul>" 
						+"  </nav>" 
						+"</div>");		
				
				out.println("<body><div class='corpo'><h2>Bem-vindo "+ nome + "</h2></div>");

			}
			
			else if(ClienteTCP.utilizador.getChildNodes().item(0).getAttributes().getNamedItem("Local").getTextContent().equals("Caixa")) {
				
				out.println("<div class=\"cabecalho\">" 
						+"  <img id=\"logo\" src=\"images/RDDSports.png\"/>" 
						+"  <nav>" 
						+"    <ul>" 
						+"      <li><a id=\"active\">Home</a></li>" 
						+"      <li><a href=\"ConsultarCarrinhos\">Carrinhos</a></li>" 
						+"      <li><a href=\"TerminarSessao\">Logout</a></li>" 
						+"    </ul>" 
						+"  </nav>" 
						+"</div>");	
				
				out.println("<body><div class='corpo'><h2>Bem-vindo "+ nome + "</h2></div>");
			}
	
		}
		else  {
			out.println("<div class=\"cabecalho\"><img id=\"logo\" src=\"images/RDDSports.png\"/>"
	        		+ "<nav><ul><li><a id=\"active\" href=\"Login\">Login</a></li><li><a href=\"Registar\">Registar</a>"
	        		+ "</li></ul></nav></div>");
			
			out.println("<body><div class='corpo'><h2>NIF inexistente no sistema.</h2><h2><a href='index.html'>Voltar à Home Page</a></h2></div>");
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
