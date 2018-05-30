

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class InserirPeca
 */
@WebServlet("/InserirPeca")
public class InserirPeca extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		
        out.println("<html>");
        out.println("<head><title>Dados da Pe�a</title></head><body>");
        out.println("<h1>Dados da Pe�a</h1>");
        
        out.println("<form action='ProcessarInsercao'>");
        out.println("Sec��o: <select name='seccao' required><option value='Homem'>Homem</option>"
        		+ "<option value='Mulher'>Mulher</option>"
        		+ "<option value='Crian�a'>Crian�a</option>"
        		+ "<option value='Acess�rios'>Acess�rios</option>"
        		+ "</select>");
        
        out.println("<p>Vestu�rio <input name=\'tipo\' type=\'radio\'"
        		+ " value=\'Vestu�rio\' required/>Cal�ado <input name=\"tipo\" "
        		+ "type=\"radio\" value=\"Cal�ado\" required/></p>");
        
        out.println("<p>Designa��o: <input name=\'designacao\' type=\'text\' required/></p>");
        
        out.println("<p>Marca: <input name=\'marca\' type=\'text\' placeholder=\'Insira a marca da pe�a\' required/></p>");
        
        out.println("<p>Descri��o: <textarea rows=\'4\' cols=\'100\' placeholder=\'Insira a descri��o do produto\' required></textarea></p>");
	
        out.println("<p>Imagem: <input type=\'file\' name=\'image\' accept=\'image/*\'></p>");
	
        out.println("<p><input type=\'submit\' value=\'Adicionar\' ></p>");
        
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
