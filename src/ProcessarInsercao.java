

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ProcessarInsercao
 */
@WebServlet("/ProcessarInsercao")
public class ProcessarInsercao extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//TODO
		
		String seccao = request.getParameter("seccao");
		String tipo = request.getParameter("tipo");
		String designacao = request.getParameter("designacao");
		String marca = request.getParameter("marca");
		String descricao = request.getParameter("descricao");
		String preco = request.getParameter("preco");
		String path = request.getParameter("image");
		
		Path myPath = Paths.get(path);
		byte[] mydata = java.nio.file.Files.readAllBytes(myPath);
		String base64 = Base64.getEncoder().encodeToString(mydata);
		//String base64 = DatatypeConverter.printBase64Binary(mydata);
		if(seccao.equals("Acessorios")) {
			tipo = "Acessorios";
		}
		String idPecaInserida = ClienteTCP.AdicionarPeca(seccao, tipo, designacao, marca, descricao, preco, base64);
		if(seccao.equals("Mulher") || seccao.equals("Homem")) {
			if(tipo.equals("Vestuario")) {
				ClienteTCP.AlterarQuantidade(idPecaInserida, "S", "0");
				ClienteTCP.AlterarQuantidade(idPecaInserida, "M", "0");
				ClienteTCP.AlterarQuantidade(idPecaInserida, "L", "0");
				ClienteTCP.AlterarQuantidade(idPecaInserida, "XL", "0");
			}
			else {
				ClienteTCP.AlterarQuantidade(idPecaInserida, "43", "0");
				ClienteTCP.AlterarQuantidade(idPecaInserida, "42", "0");
				ClienteTCP.AlterarQuantidade(idPecaInserida, "41", "0");
				ClienteTCP.AlterarQuantidade(idPecaInserida, "40", "0");
				ClienteTCP.AlterarQuantidade(idPecaInserida, "39", "0");
			}
		}
		else if(seccao.equals("Crianca")) {
			if(tipo.equals("Vestuario")) {
				ClienteTCP.AlterarQuantidade(idPecaInserida, "6/8", "0");
				ClienteTCP.AlterarQuantidade(idPecaInserida, "8/10", "0");
				ClienteTCP.AlterarQuantidade(idPecaInserida, "10/12", "0");
				ClienteTCP.AlterarQuantidade(idPecaInserida, "12/14", "0");
			}
			else {
				ClienteTCP.AlterarQuantidade(idPecaInserida, "35", "0");
				ClienteTCP.AlterarQuantidade(idPecaInserida, "34", "0");
				ClienteTCP.AlterarQuantidade(idPecaInserida, "33", "0");
				ClienteTCP.AlterarQuantidade(idPecaInserida, "32", "0");
				ClienteTCP.AlterarQuantidade(idPecaInserida, "31", "0");
			}
		}
		response.setContentType("text/html");
        
        PrintWriter out = response.getWriter();
        
		out.println("<html>");
        out.println("<head><title>Equipamento Inserido</title></head>");
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
        
        
        
        if(idPecaInserida.equals("")) {

        	out.println("<h1>Erro a inserir equipamento. Tente outra vez.</h1>");
        }
        
        else {
        	out.println("<h1>Equipamento Inserido com Sucesso</h1>");
        }
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
