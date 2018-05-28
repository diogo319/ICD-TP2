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
        if(ClienteTCP.utilizador != null) {
            String nome = ClienteTCP.utilizador.getAttributes().getNamedItem("Nome").getTextContent();
			String tipoUtilizador = ClienteTCP.utilizador.getChildNodes().item(0).getNodeName();
			out.println("<body><h3>Bem-vindo "+ nome + "</<h3>");
			if(tipoUtilizador.equals("Cliente")){

		        out.println("<h3>Equipamentos Masculinos</h3>");
		        out.println("<h3>Equipamentos Femininos</h3>");
		        out.println("<h3>Equipamentos Crian�a</h3>");
		        out.println("<h3>Acess�rios</h3>");
		        out.println("<h3>Ver Carrinho de Compras</h3>");
		        out.println("<h3><a href='TerminarSessao'>Terminar Sess�o</a></h3>");

			}
			
			else if(ClienteTCP.utilizador.getChildNodes().item(0).getAttributes().getNamedItem("Local").getTextContent().equals("Loja")) {
				
				out.println("<h3>Adicionar nova pe�a</h3>");
				out.println("<h3>Modificar pre�o de pe�a existente</h3>");
				out.println("<h3>Modificar quantidade de pe�a existente</h3>");
				out.println("<h3><a href='TerminarSessao'>Terminar Sess�o</a></h3>");
				
			}
			
			else if(ClienteTCP.utilizador.getChildNodes().item(0).getAttributes().getNamedItem("Local").getTextContent().equals("Caixa")) {
				
				out.println("<h3>Consultar carrinhos de compras por aprovar</h3>");
				out.println("<h3><a href='TerminarSessao'>Terminar Sess�o</a></h3>");
				
			}
	
		}
		else  {
			out.println("<body><h3>NIF inexistente no sistema.</h3><h3><a href='index.html'>Voltar � Home Page</a></h3>");
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
