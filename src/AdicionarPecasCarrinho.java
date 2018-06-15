

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ParseConversionEvent;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Servlet implementation class MostrarCarrinho
 */
@WebServlet("/AdicionarPecasCarrinho")
public class AdicionarPecasCarrinho extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
        
        PrintWriter out = response.getWriter();
        
        out.println("<html>");
        out.println("<link rel=\"stylesheet\" href=\"css/main.css\" />");
		out.println("<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\">");

        
        String idPeca = request.getParameter("idPeca");
        String[] tamanhos;
		String[] quantidades;
		
		//TODO Adicionar todas as peças
		if (!idPeca.equals("vazio")) {
			
			tamanhos = request.getParameterValues("valor");
			quantidades = request.getParameterValues("quantidade");
			for(int i = 0; i < tamanhos.length; i++) {
				ClienteTCP.AdicionarCarrinho(Integer.parseInt(idPeca), Integer.parseInt(quantidades[i]), tamanhos[i]);
			}
		}
		
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
				+"      <li><a id='active'>Carrinho</a></li>" 
				+"      <li><a href=\"TerminarSessao\">Logout</a></li>" 
				+"    </ul>" 
				+"  </nav>" 
				+"</div>");
		
		out.println("<body>");
        
        out.println("<div class='corpo'><h1>Carrinho</h1></div>");
		
		//TODO mostrar carrinho apos alteraçoes
		Node carrinho = ClienteTCP.Carrinho();
		
		float total = 0;
		out.println("<form id='formRemover' action='RemoverPeca'>");
		out.println("<table id='tables'><tr><th>Designação</th><th>Secção</th><th>Tamanho</th><th>Quantidade</th><th>Preço / Unidade</th><th>Preço Total</th><th>Remover</th></tr>");
		
		NodeList pecasCarrinho = carrinho.getChildNodes();
		for(int j = 0; j < pecasCarrinho.getLength(); j++) {
			String idPecaCarrinho = pecasCarrinho.item(j).getAttributes().getNamedItem("ID").getTextContent();
			Node peca = ClienteTCP.PecaID(idPecaCarrinho);
			String designacao = peca.getAttributes().getNamedItem("Designação").getTextContent();
			String seccao = peca.getAttributes().getNamedItem("Secção").getTextContent();
			String tamanho = pecasCarrinho.item(j).getAttributes().getNamedItem("Tamanho").getTextContent();
			String quantidade = pecasCarrinho.item(j).getAttributes().getNamedItem("Quantidade").getTextContent();
			String preco = peca.getAttributes().getNamedItem("Preço").getTextContent();
			//Node peca = ClienteTCP.PecaByID(idPeca);
			out.println("<tr><td>" + designacao +"</td><td>" + seccao + "</td><td>" + tamanho + "</td><td>" + quantidade + "</td><td>" + preco + " &euro;</td><td>" + Float.parseFloat(preco)*(Integer.parseInt(quantidade)) + " &euro;" + "</td>");
			out.println("<td><input type='checkbox' name='idRow' value='" + j + "' onclick='toggleCheckbox(this)'></input></td>");
			out.println("<input type='hidden' id='tamanhoHidden" + j + "' name='tamanho' value='" + tamanho + "'></input></tr>");
			out.println("<input type='hidden' id='idPecaHidden" + j + "' name='idPeca' value='" + idPecaCarrinho + "'></input></tr>");
			out.println("<input type='hidden' id='quantidadeHidden" + j + "' name='quantidade' value='" + quantidade + "'></input>");
			total += (Float.parseFloat(preco)*Integer.parseInt(quantidade));

		}
		out.println("<tr style='background-color: white !important;'><td style='border: none; background: white;'></td><td style='border: none; background: white;'></td><td style='border: none; background: white;'></td><td style='border: none; background: white;'></td><th>Total</th><td>" + total + " &euro;" + "</td></tr>");
		out.println("</table>");
		out.println("</form>'");
			
		out.println("<script>function toggleCheckbox(cb){var idRowCheck = cb.value; var tamanhoGet = 'tamanhoHidden'+idRowCheck+''; var quantidadeGet = 'quantidadeHidden'+idRowCheck+'';"
				+ "var tamanho = document.getElementById(tamanhoGet).value; var quantidade = document.getElementById(quantidadeGet).value;"
				+ "var idPecaGet = 'idPecaHidden'+idRowCheck+''; var idPeca = document.getElementById(idPecaGet).value;"
				+ "window.open('RemoverPeca?idPeca='+idPeca+'&tamanho='+tamanho+'&quantidade='+quantidade+'','_self');}</script>");
		
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
