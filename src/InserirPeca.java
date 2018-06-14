

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
        out.println("<head><title>Dados da Peça</title></head>");
        out.println("<link rel=\"stylesheet\" href=\"css/main.css\" />");
        
        out.println("<div class=\"cabecalho\">" 
				+"  <img id=\"logo\" src=\"images/RDDSports.png\"/>" 
				+"  <nav>" 
				+"    <ul>" 
				+"      <li><a href='Utilizador?nif=" + ClienteTCP.utilizador.getAttributes().getNamedItem("NIF").getTextContent() + "'>Home</a></li>" 
				+"      <li><a id='active'>Inserir Peça</a></li>" 
				+"      <li><a href=\"ModificarPeca\">Modificar Peça</a></li>" 
				+"      <li><a href=\"TerminarSessao\">Logout</a></li>" 
				+"    </ul>" 
				+"  </nav>" 
				+"</div>");
        

        String seccao = request.getParameter("seccao");
   
        out.println("<body><div class='corpo'><h1>Dados da Peça</h1>");
        if(seccao == null) {
	        out.println("<form action='InserirPeca'>");
	        out.println("Secção: <select name='seccao' onchange='this.form.submit()'><option value='Homem'>Homem</option>"
	        		+ "<option value='Mulher'>Mulher</option>"
	        		+ "<option value='Crianca'>Criança</option>"
	        		+ "<option value='Acessorios'>Acessórios</option>"
	        		+ "</select></form>");
	
        }

        if(seccao != null) {
            out.println("<form action='ProcessarInsercao'>");
        	out.println("Secção: <select name='seccao' disabled><option value='" + seccao + "'>" + seccao + "</option></select>");
        	if(!seccao.equals("Acessorios")) {
		        out.println("<p>Vestuário <input name=\'tipo\' type=\'radio\'"
		        		+ " value=\'Vestuario\' required/>Calçado <input name=\"tipo\" "
		        		+ "type=\"radio\" value=\"Calcado\" required/></p>");
        	}
	        out.println("<p>Designação: <input name=\'designacao\' type=\'text\' required/></p>");
	        
	        out.println("<p>Marca: <input name=\'marca\' type=\'text\' placeholder=\'Insira a marca da peça\' required/></p>");
	        
	        out.println("<p><textarea name=\'descricao\' rows=\'4\' cols=\'100\' placeholder=\'Insira uma descrição do produto\' required></textarea></p>");
	        
	        out.println("<p>Preço: <input type=\'number\' name=\'preco\' required/> &euro;</p>");
		
	        out.println("<p>Imagem: <input type=\'file\' name=\'image\' accept=\'image/*\'></p>");
	        
	        out.println("<input type='hidden' name='seccao' value='" + seccao + "'></input>");
	        
	        out.println("<p><input class='button2' type=\'submit\' value=\'Adicionar\' ></p></form>");

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
