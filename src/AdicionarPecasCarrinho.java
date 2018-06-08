

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        out.println("<head><title>Carrinho</title><style>\r\n"+
        			"table, th, td {\r\n"+
        			"border: 1px solid black;\r\n"+
        			"border-collapse: collapse;\r\n"+
        			"}\r\n"+
        			"th, td {\r\n"+
        			"padding: 5px;\r\n"+
        			"}\r\n"+
        			"</style></head>");
        out.println("<body>");
        
        out.println("<h1>Carrinho</h1>");
        
        String idPeca = request.getParameter("idPeca");
        String[] tamanhos = request.getParameterValues("valor");
		String[] quantidades = request.getParameterValues("quantidade");
		
		//TODO Adicionar todas as peças
		for(int i = 0; i < tamanhos.length; i++) {
			ClienteTCP.AdicionarCarrinho(Integer.parseInt(idPeca), Integer.parseInt(quantidades[i]), tamanhos[i]);
		}
		
		//TODO mostrar carrinho apos alteraçoes
		Node carrinho = ClienteTCP.Carrinho();
		
		out.println("<table><tr><th>Designação</th><th>Secção</th><th>Tamanho</th><th>Quantidade</th></tr>");
		NodeList pecasCarrinho = carrinho.getChildNodes();
		for(int j = 0; j < pecasCarrinho.getLength(); j++) {
			String idPecaCarrinho = pecasCarrinho.item(j).getAttributes().getNamedItem("ID").getTextContent();
			Node peca = ClienteTCP.PecaID(idPecaCarrinho);
			String designacao = peca.getAttributes().getNamedItem("Designação").getTextContent();
			String seccao = peca.getAttributes().getNamedItem("Secção").getTextContent();
			String tamanho = pecasCarrinho.item(j).getAttributes().getNamedItem("Tamanho").getTextContent();
			String quantidade = pecasCarrinho.item(j).getAttributes().getNamedItem("Quantidade").getTextContent();
			//Node peca = ClienteTCP.PecaByID(idPeca);
			out.println("<tr><th>" + designacao +"</th><th>" + seccao + "</th><th>" + tamanho + "</th><th>" + quantidade + "</th></tr>");
			//out.println("<h1>" + peca.getAttributes().getNamedItem("Designação").getTextContent() + "</h1>");
		}
		out.println("</table>");
		
		out.println("<br>");
		
		out.println("<h2><a href='Utilizador?nif=" + ClienteTCP.utilizador.getAttributes().getNamedItem("NIF").getTextContent() + "'>Voltar à HomePage</a></h2>");
		
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
