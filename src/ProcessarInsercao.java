

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
import javax.xml.bind.DatatypeConverter;

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
		String idPecaInserida = ClienteTCP.AdicionarPeca(seccao, tipo, designacao, marca, descricao, preco, base64);
		
		response.setContentType("text/html");
        
        PrintWriter out = response.getWriter();
        
		out.println("<html>");
        out.println("<head><title>Equipamento Inserido</title></head>");
        out.println("<body>");
        
        
        if(idPecaInserida.equals("")) {

        	out.println("<h1>Erro a inserir equipamento. Tente outra vez.</h1>");
        }
        
        else {
        	out.println("<h1>Equipamento Inserido com Sucesso</h1>");
        }
    	out.println("<h2><a href='InserirPeca'>Voltar à página anterior</a></h2>");
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
