

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
 * Servlet implementation class AlterarParametros
 */
@WebServlet("/AlterarParametros")
public class AlterarParametros extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		
		String idPeca = request.getParameter("idPeca");
		String precoNovo = request.getParameter("precoNovo");
		String precoAntigo = request.getParameter("precoAntigo");
		String[] valores = request.getParameterValues("valor");
		String[] quantidadesNovas = request.getParameterValues("quantidadeNova");
		String[] quantidadesAntigas = request.getParameterValues("quantidadeAntiga");
		
		if(!precoNovo.equals(precoAntigo)) {
			ClienteTCP.AlterarPreco(idPeca, precoNovo);
		}
		if(valores == null) {
			if(!quantidadesNovas[0].equals(quantidadesAntigas[0])) {
				ClienteTCP.AlterarQuantidadeAcessorio(idPeca, quantidadesNovas[0]);
			}
		}
		else {
			for(int i = 0; i < valores.length; i++) {
				if(!quantidadesNovas[i].equals(quantidadesAntigas[i])) {
					ClienteTCP.AlterarQuantidade(idPeca, valores[i], quantidadesNovas[i]);
				}
			}
		}
        out.println("<html>");
        out.println("<head><title>Peça Modificada</title></head>");
        out.println("<link rel=\"stylesheet\" href=\"css/main.css\" />");
        out.println("<div class=\"cabecalho\">" 
				+"  <img id=\"logo\" src=\"images/RDDSports.png\"/>" 
				+"  <nav>" 
				+"    <ul>" 
				+"      <li><a href='Utilizador?nif=" + ClienteTCP.utilizador.getAttributes().getNamedItem("NIF").getTextContent() + "'>Home</a></li>" 
				+"      <li><a href=\"InserirPeca\">Inserir Peça</a></li>" 
				+"      <li><a href=\"ModificarPeca\">Modificar Peça</a></li>" 
				+"      <li><a href=\"TerminarSessao\">Logout</a></li>" 
				+"    </ul>" 
				+"  </nav>" 
				+"</div><body><div class='corpo'>");

		out.println("<h2>Peça alterada com sucesso.</h2>");
        out.println("</div></body></html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
