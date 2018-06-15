
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author PFilipe
 *
 */
public class ClienteTCP {

	public final static String DEFAULT_HOSTNAME = "localhost";

	public final static int DEFAULT_PORT = 5025;
	
	public static Node utilizador;

	/**
	 * @param sock
	 */
	public static void menu(Socket sock) {
		char op;
		Scanner sc = new Scanner(System.in);
		do {
			System.out.println();
			System.out.println();
			System.out.println("*** Login Loja ***");
			System.out
					.println("1 - Login");
			System.out
					.println("2 - Registar");
			System.out.println("0 - Terminar!");
			String str = sc.nextLine();
			if (str != null && str.length() > 0)
				op = str.charAt(0);
			else
				op = ' ';
			switch (op) {
			case '1':
				NodeList teste = PecasTotal(sock);
				String nif = menuLogin(sc);
				Login(nif);
				if(utilizador != null) {
					String tipoUtilizador = utilizador.getChildNodes().item(0).getNodeName();
					if(tipoUtilizador.equals("Cliente")){
						menuCliente(sock, sc, nif);
					}else if(utilizador.getChildNodes().item(0).getAttributes().getNamedItem("Local").getTextContent().equals("Loja")) menuFuncionarioLoja(sock, sc, nif);
					else if(utilizador.getChildNodes().item(0).getAttributes().getNamedItem("Local").getTextContent().equals("Caixa")) menuFuncionarioCaixa(sock, sc, nif);
					
				}
				else  {
					System.out.println("NIF inexistente no sistema.");
				}
				

				break;
			case '2':
				menuRegistar(sock, sc);
				break;
			case '0':
				break;
			default:
				System.out.println("Op��o inv�lida, esolha uma op��o do menu.");
			}
		} while (op != '0');
		sc.close();
		System.out.println("Terminou a execu��o.");
		System.exit(0);
	}
	
	private static boolean menuRegistar(Socket sock, Scanner sc) {
		System.out.println("Insira o seu nome completo.");
		String nome = sc.nextLine();
		System.out.println("Insira o seu NIF.");
		String nif = sc.nextLine();
		if(!validarNif(nif)) {
			System.out.println("Nif inserido inv�lido ou j� existente na base de dados.");
			return false;
		}
		System.out.println("Insira a sua data de nascimento (aaaa-mm-dd) ");
		String dataNasc = sc.nextLine();
		if (dataNasc.charAt(0) == '(') {
			dataNasc = dataNasc.substring(1, dataNasc.length()-1);
		}
		return Registar(nif, nome, dataNasc);
	}
	
	public static void menuCliente(Socket sock, Scanner sc, String nif) {
		char op;
		Node catalogo = Loja.getPecas().getDocumentElement().cloneNode(true);
		do {
			System.out.println();
			System.out.println();
			System.out.println("*** Catalogo Loja ***");
			System.out
					.println("1 - Equipamentos Masculinos.");
			System.out
					.println("2 - Equipamentos Femininos.");
			System.out
					.println("3 - Equipamentos Crian�a.");
			System.out.println("4 - Acess�rios");
			System.out.println("5 - Ver Carrinho De Compras.");
			System.out.println("6 - Terminar Sess�o.");
			System.out.println("0 - Terminar!");
			String str = sc.nextLine();
			if (str != null && str.length() > 0)
				op = str.charAt(0);
			else
				op = ' ';
			switch (op) {
			case '1':
				NodeList pecasHSocket = Catalogo("Homem");
				//NodeList pecasH = getNodesByTag(catalogo, "Sec��o", "Homem");
				menuEquipamentos(sock, sc, nif, pecasHSocket, "Homem");
				break;
				
			case '2':
				NodeList pecasMSocket = Catalogo("Mulher");
				//NodeList pecasM = getNodesByTag(catalogo, "Sec��o", "Mulher");
				menuEquipamentos(sock, sc, nif, pecasMSocket, "Mulher");
				break;
				
			case '3':
				NodeList pecasCSocket = Catalogo("Crian�a");
				//NodeList pecasC = getNodesByTag(catalogo, "Sec��o", "Crian�a");
				menuEquipamentos(sock, sc, nif, pecasCSocket, "Crian�a");
				break;
				
			case '4':
				NodeList pecasASocket = Catalogo("Acess�rios");
				//NodeList pecasA = getNodesByTag(catalogo, "Sec��o", "Acessorio");
				menuPecas(sock,sc,nif,pecasASocket,"Acess�rios","Acess�rios");
				
				//TODO
				break;
				
			case '5':
				//TODO
				Node pecasCarrinho = Carrinho();
				//menuCarrinho(sock,sc,nif,pecasCarrinho);
				break;
				
			
			case '6':
				utilizador = null;
				menu(sock);
				break;
				
			case '0':
				break;
			default:
				System.out.println("Op��o inv�lida, esolha uma op��o do menu.");
			}
		} while (op != '0');
		sc.close();
		System.out.println("Terminou a execu��o.");
		System.exit(0);
	}
	
	
	public static void menuFuncionarioCaixa(Socket sock, Scanner sc, String nif) {
		char op;
		do {
			System.out.println();
			System.out.println();
			System.out.println("*** Funcion�rio Caixa ***");
			System.out
					.println("1 - Consultar carrinhos de compras por aprovar.");
			System.out.println("2 - Terminar Sess�o.");
			System.out.println("0 - Terminar!");
			String str = sc.nextLine();
			if (str != null && str.length() > 0)
				op = str.charAt(0);
			else
				op = ' ';
			switch (op) {
			
			case '1':
				NodeList carrinhos = mostrarTodosCarrinhos();
				
				//TODO
				break;
				
			case '2':
				menu(sock);
				break;
				
			case '0':
				break;
			default:
				System.out.println("Op��o inv�lida, esolha uma op��o do menu.");
			}
		} while (op != '0');
		sc.close();
		System.out.println("Terminou a execu��o.");
		System.exit(0);
	}
	
	public static void menuFuncionarioLoja(Socket sock, Scanner sc, String nif) {
		char op;
		do {
			System.out.println();
			System.out.println();
			System.out.println("*** Funcion�rio Loja ***");
			System.out
					.println("1 - Adicionar nova pe�a.");
			System.out
					.println("2 - Modificar pre�o de pe�a existente.");
			System.out
					.println("3 - Modificar quantidade de pe�a existente.");
			System.out.println("4 - Terminar Sess�o.");
			System.out.println("0 - Terminar!");
			String str = sc.nextLine();
			if (str != null && str.length() > 0)
				op = str.charAt(0);
			else
				op = ' ';
			switch (op) {
			
			case '1':
				//apresenta();
				break;
				
			case '4':
				menu(sock);
				break;
				
			case '0':
				break;
			default:
				System.out.println("Op��o inv�lida, esolha uma op��o do menu.");
			}
		} while (op != '0');
		sc.close();
		System.out.println("Terminou a execu��o.");
		System.exit(0);
	}
	
	public static void menuEquipamentos(Socket sock, Scanner sc, String nif, NodeList pecas, String seccao) {
		char op;

		do {
			System.out.println();
			System.out.println();
			System.out.println("*** Vestu�rio/Cal�ado ***");
			System.out.println("1 - Vestu�rio.");
			System.out.println("2 - Cal�ado.");
			System.out.println("3 - Voltar.");
			System.out.println("0 - Terminar!");
			String str = sc.nextLine();
			if (str != null && str.length() > 0)
				op = str.charAt(0);
			else
				op = ' ';
			switch (op) {
			
			case '1':
				menuPecas(sock, sc, nif, pecas, "Vestu�rio", seccao);
				break;
				
			case '2':
				menuPecas(sock, sc, nif, pecas, "Cal�ado", seccao);
				break;
				
			case '3':
				menuCliente(sock, sc, nif);
				break;
				
			case '0':
				break;
			default:
				System.out.println("Op��o inv�lida, esolha uma op��o do menu.");
			}
		} while (op != '0');
		sc.close();
		System.exit(0);
	}
	
	public static void menuPecas(Socket sock, Scanner sc, String nif, NodeList pecas, String tipo, String seccao) {
		boolean escolhaExiste = false; //TODO alterar forma de fazer isto ou alterar nome da variavel
		
		while(!escolhaExiste) {
			System.out.println();
			System.out.println();
			System.out.println("*** " + tipo + " ***");
			System.out.println("");
			
			int numPecas = 0;
			for(int i = 0; i < pecas.getLength(); i++) {
				if(pecas.item(i).hasAttributes() && pecas.item(i).getChildNodes().item(5).getNodeName().equals(tipo))
				System.out.println(++numPecas + " - " + pecas.item(i).getAttributes().getNamedItem("Designa��o").getTextContent());
			}
			
			if (numPecas == 0) {
				System.out.println("N�o existem pe�as de " + tipo + " dispon�veis na sec��o de " + seccao + "! Voltando ao men� anterior...");
				return;
			}
			
			
			//Handle do input	
			System.out.println();
			System.out.println("Escreva o n�mero da pe�a para ver a sua descri��o. Ou escreva \"0\" para voltar ao men� anterior.");
			String input = apenasNumeros(sc, numPecas);
		

			if (input.equals("0")) {
				return;
			}
			
			int indexAux = 0;
			for(int i = 0; i < pecas.getLength(); i++) {
				if(pecas.item(i).hasAttributes() && pecas.item(i).getChildNodes().item(5).getNodeName().equals(tipo)) {
					indexAux++;
					if (input.equals(Integer.toString(indexAux))) {
						escolhaExiste = true;						
						mostrarPeca(sock, sc, pecas.item(i), nif, tipo);
						//TODO mostrar descri��o do item e fazer um novo menu para comprar um tamanho ou voltar
					}
				}
			}
			
		}
	}
	
	public static void menuCarrinho(Socket sock, Scanner sc,String nif,NodeList pecasCarrinho) {
		char op;
		
		if(!utilizador.getFirstChild().hasChildNodes()){
			System.out.println("##########################");
			System.out.println();
			System.out.println("N�o Tem Pe�as no Carrinho");
			System.out.println();
			System.out.println("##########################");
			return;
		}
		
		NodeList n = utilizador.getFirstChild().getChildNodes();
		
		
		//System.out.println(n.item(0).getChildNodes().item(0).getAttributes().getNamedItem("ID"));
		
		
		
		
		
		
		do {
			int numPecas =0;
			for(int i = 0; i < utilizador.getFirstChild().getChildNodes().item(0).getChildNodes().getLength(); i++) {
				System.out.println(++numPecas + " - " + utilizador.getFirstChild().getChildNodes().item(0).getChildNodes().item(i).getAttributes().getNamedItem("ID"));
			}
			System.out.println();
			System.out.println();
			System.out.println("*** Caso queira retirar uma pe�a escolha o n�mero da pe�a a retirar ***");
			System.out.println("! - Voltar.");
			System.out.println("0 - Terminar!");
			String str = sc.nextLine();
			if (str != null && str.length() > 0)
				op = str.charAt(0);
			else
				op = ' ';
			switch (op) {
			
			
			case '!':
				return;
				
			case '0':
				break;
			default:
				System.out.println("Op��o inv�lida, esolha uma op��o do menu.");
			}
		} while (op != '0');
		sc.close();
		System.exit(0);
	}
		//TODO N�o est� finalizado
		
	
	
	private static String apenasNumeros(Scanner sc, int numPecas) {//TODO alterar nome
		String input = sc.nextLine();
		boolean nums = false;
		
		while (!nums) {
			for(int i = 0; i < input.length(); i++) {
				if (!Character.isDigit(input.charAt(i))) {
					System.out.println("\nIntroduza apenas n�meros! Valor entre 0 e " + numPecas + "\n");
					input = sc.nextLine();
					nums = false;
					break;
				}else nums = true;
			}if (!nums) continue;
			
			int valorIn = Integer.parseInt(input);
			if (valorIn < 0 || valorIn > numPecas) {
				nums = false;
				System.out.println("\nIntroduza um valor entre 0 e " + numPecas + "\n");
				input = sc.nextLine();
			}
		}
		return input;
	}
	
	private static NodeList getNodesByTag(Node root, String tag, String tagValor) {
		NodeList nl = root.getChildNodes();
		System.out.println(root.getChildNodes().getLength());
		for(int i = 0; i < nl.getLength(); i++) {
			if(nl.item(i).getNodeType() == Node.ELEMENT_NODE && !nl.item(i).getAttributes().getNamedItem(tag).getTextContent().equals(tagValor)) {
				root.removeChild(nl.item(i));
			}
		}
		System.out.println(root.getChildNodes().getLength());

		return root.getChildNodes();
	}
	
	private static String menuLogin(Scanner sc) {
		System.out.println("Introduza o seu NIF.");
		String nif = sc.nextLine();
		
		while(!validarNif(nif)) {
			if (nif.equals("0")) break;
			System.out.println("NIF introduzido inv�lido. Introduza novamente o NIF.");
			nif = sc.nextLine();
		}
		
		return nif;
	}

	public static boolean validarNif(String nif) {
		if (nif.length() == 9) {
			for (int i = 0; i < nif.length(); i++) {
				if(!Character.isDigit(nif.charAt(i))) return false;
			}
			return true;
		}
		return false;
	}	
	
	public static boolean Registar(String nif, String nome, String dataNasc) {
		Socket sock = null;
		
		try {
			sock = new Socket(DEFAULT_HOSTNAME, DEFAULT_PORT);
			
			comando cmd = new comando();
			Document request = cmd.requestRegistar(nif, nome, dataNasc);
			
			XMLReadWrite.documentToSocket(request, sock);
			
			Document reply = XMLReadWrite.documentFromSocket(sock);
			
			if(reply.getElementsByTagName("Utilizador").item(0) != null) {
				utilizador = reply.getElementsByTagName("Utilizador").item(0);
				return true;
			}
			return false;
		}catch(UnknownHostException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return false;
		
	}

	public static NodeList mostrarTodosCarrinhos() {
		Socket sock = null;
		NodeList carrinhos = null;
		
		try {
			sock = new Socket(DEFAULT_HOSTNAME, DEFAULT_PORT);
			comando cmd = new comando();
			
			Document request = cmd.requestTodosCarrinhos();
			
			XMLReadWrite.documentToSocket(request, sock);
			
			Document reply = XMLReadWrite.documentFromSocket(sock);
			
			carrinhos = reply.getElementsByTagName("Carrinho");
			
		}catch(UnknownHostException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
		return carrinhos;
	}
	
	public static void Login(String nif) {
		Socket sock = null;
		
		try {

			sock = new Socket(DEFAULT_HOSTNAME, DEFAULT_PORT);
			comando cmd = new comando();
			Document request = cmd.requestLogin(nif);
			// envia pedido
			XMLReadWrite.documentToSocket(request, sock);
			// obt�m resposta
			Document reply = XMLReadWrite.documentFromSocket(sock);
			NodeList utilizadores = reply.getElementsByTagName("Utilizador");
			
			Node item = utilizadores.item(0);
			utilizador = item;
		}catch(UnknownHostException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static NodeList Catalogo(String seccao) {
		Socket sock = null;
		NodeList pecas = null;
		try {
			sock = new Socket(DEFAULT_HOSTNAME, DEFAULT_PORT);
			comando cmd = new comando();
			Document request = cmd.requestCatalogo(seccao);
			//XMLDoc.writeDocument(request, "request.xml");
			//envia pedido
			XMLReadWrite.documentToSocket(request, sock);
			//obt�m resposta
			Document reply = XMLReadWrite.documentFromSocket(sock);
			//XMLDoc.writeDocument(reply, "reply.xml");
			pecas = reply.getElementsByTagName("Pe�a");
		}catch(UnknownHostException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
		return pecas;
	}
	
	//TODO fazer alterar peca

	public static Node AlterarPreco(String idPeca, String precoNovo) {
		Socket sock = null;
		Node peca = null;
		
		try {
			sock = new Socket(DEFAULT_HOSTNAME, DEFAULT_PORT);
			comando cmd = new comando();
			Document request = cmd.requestModificarPreco(idPeca, precoNovo);
			XMLReadWrite.documentToSocket(request, sock);
			
			Document reply = XMLReadWrite.documentFromSocket(sock);
			
			peca = reply.getElementsByTagName("Pe�a").item(0);
		}catch(UnknownHostException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
		return peca;
	}
	
	public static Node AlterarQuantidadeAcessorio(String idPeca, String quantidadeNova) {
		Socket sock = null;
		Node peca = null;
		
		try {
			sock = new Socket(DEFAULT_HOSTNAME, DEFAULT_PORT);
			comando cmd = new comando();
			Document request = cmd.requestModificarQuantidadeAcessorio(idPeca, quantidadeNova);
			XMLReadWrite.documentToSocket(request, sock);
			
			Document reply = XMLReadWrite.documentFromSocket(sock);
			
			peca = reply.getElementsByTagName("Pe�a").item(0);
		}catch(UnknownHostException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
		return peca;
	}
	
	public static Node AlterarQuantidade(String idPeca, String valor, String quantidadeNova) {
		Socket sock = null;
		Node peca = null;
		
		try {
			sock = new Socket(DEFAULT_HOSTNAME, DEFAULT_PORT);
			comando cmd = new comando();
			Document request = cmd.requestModificarQuantidade(idPeca, valor, quantidadeNova);
			XMLReadWrite.documentToSocket(request, sock);
			
			Document reply = XMLReadWrite.documentFromSocket(sock);
			
			peca = reply.getElementsByTagName("Pe�a").item(0);
		}catch(UnknownHostException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
		return peca;
	}
	
	
	public static Node PecaID(String idPeca) {
		Socket sock = null;
		Node peca = null;
		
		try {
			sock = new Socket(DEFAULT_HOSTNAME, DEFAULT_PORT);
			comando cmd = new comando();
			Document request = cmd.requestPecaID(idPeca);
			//XMLDoc.writeDocument(request, "request.xml");
			//envia pedido
			XMLReadWrite.documentToSocket(request, sock);
			//obt�m resposta
			Document reply = XMLReadWrite.documentFromSocket(sock);
			peca = reply.getElementsByTagName("Pe�a").item(0);
		}catch(UnknownHostException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
		return peca;
	}

	
	public static Node Carrinho(){
		Socket sock = null;
		Node carrinho = null;
		try {
			sock = new Socket(DEFAULT_HOSTNAME, DEFAULT_PORT);
			comando cmd = new comando();
			Document request = cmd.requestCarrinho(utilizador.getAttributes().getNamedItem("NIF").getTextContent());
			//XMLDoc.writeDocument(request, "request.xml");
			//envia pedido
			XMLReadWrite.documentToSocket(request, sock);
			//obt�m resposta
			Document reply = XMLReadWrite.documentFromSocket(sock);
			carrinho = reply.getElementsByTagName("Carrinho").item(0);
			//XMLDoc.writeDocument(reply, "reply.xml");
		}catch(UnknownHostException e) {
            e.printStackTrace();
        }catch(IOException e) {
            e.printStackTrace();
        }
		
		return carrinho;
	}
	

	

	public static String AdicionarPeca(String seccao, String tipo, String designacao, String marca, String descricao, String preco, String base64) {
        Socket sock = null;
        String idPeca = "";
        try {
            sock = new Socket(DEFAULT_HOSTNAME, DEFAULT_PORT);
           
            comando cmd = new comando();
           
            Document request = cmd.requestAdicionarPeca(seccao, tipo, designacao, marca, descricao, preco, base64);
           
            XMLReadWrite.documentToSocket(request, sock);
           
            Document reply = XMLReadWrite.documentFromSocket(sock);
           
            NodeList pecas = reply.getElementsByTagName("Pe�a");
            Node peca = pecas.item(pecas.getLength() - 1);
            idPeca = peca.getAttributes().getNamedItem("idPe�a").getTextContent();
        }catch(UnknownHostException e) {
            e.printStackTrace();
        }catch(IOException e) {
            e.printStackTrace();
        }
   
        return idPeca;
    }


	private static NodeList PecasTotal(Socket sock) {
		//TODO
		comando cmd = new comando();
		Document request = cmd.requestPecasTotal();
		
		XMLDoc.writeDocument(request, "requestPecas.xml");
		
		XMLReadWrite.documentToSocket(request, sock);
		
		Document reply = XMLReadWrite.documentFromSocket(sock);
		
		XMLDoc.writeDocument(reply, "replyPecas.xml");
		return null;
	}
	
	
	public static Node AdicionarCarrinho(int idPeca, int quantidade, String tamanho) {
		Socket sock = null;
		Node carrinho = null;
		try {
			sock = new Socket(DEFAULT_HOSTNAME, DEFAULT_PORT);
			comando cmd = new comando();
			
			Document request = cmd.requestAdicionarCarrinho(utilizador.getAttributes().getNamedItem("NIF").getTextContent(), idPeca, tamanho, quantidade);
			
			XMLReadWrite.documentToSocket(request, sock);
					
			Document reply = XMLReadWrite.documentFromSocket(sock);
					
			carrinho = reply.getElementsByTagName("Carrinho").item(0);
			
			//XMLDoc.writeDocument(reply, "replyCarrinho.xml");
		}catch(UnknownHostException e) {
            e.printStackTrace();
        }catch(IOException e) {
            e.printStackTrace();
        }
	
		return carrinho;
	}

	public static Node RemoverPecaCarrinho(String idPeca, String tamanho, String quantidade) {
		Socket sock = null;
		Node carrinho = null;
		try {
			sock = new Socket(DEFAULT_HOSTNAME, DEFAULT_PORT);
			comando cmd = new comando();
			
			Document request = cmd.requestRemoverPecaCarrinho(utilizador.getAttributes().getNamedItem("NIF").getTextContent(), idPeca, tamanho, quantidade);
			
			XMLReadWrite.documentToSocket(request, sock);
			
			Document reply = XMLReadWrite.documentFromSocket(sock);
			
			carrinho = reply.getElementsByTagName("Carrinho").item(0);
		}catch(UnknownHostException e) {
            e.printStackTrace();
        }catch(IOException e) {
            e.printStackTrace();
        }
	
		return carrinho;
	}
	
	public static NodeList AprovarCarrinho(String nif) {
		Socket sock = null;
		NodeList carrinhos = null;
		try {
			sock = new Socket(DEFAULT_HOSTNAME, DEFAULT_PORT);
			comando cmd = new comando();
			
			Document request = cmd.requestAprovarCarrinho(nif);
			
			XMLReadWrite.documentToSocket(request, sock);
					
			Document reply = XMLReadWrite.documentFromSocket(sock);
					
			carrinhos = reply.getElementsByTagName("Carrinho");
		
		}catch(UnknownHostException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return carrinhos;
	}
	

	
	public static int quantidadePretendida(Scanner sc, int quantMax) {
		System.out.println("\nIntroduza a quantidade de itens pretendida (min 0 e m�x " + quantMax + ")");
		int input = Integer.parseInt(apenasNumeros(sc, quantMax));
		
		return input;
	}
	
	
	public static void mostrarPeca(Socket sock, Scanner sc, Node peca, String nif, String tipo) {
		Element pecaElement = (Element) peca.getChildNodes();
		Element descricao = (Element) pecaElement.getElementsByTagName("Descri��o").item(0).getChildNodes();
		String caracteristicas = descricao.getElementsByTagName("Caracteristica").item(0).getTextContent();
		
		System.out.println("\nDescri��o do item:\n" + caracteristicas);
		
		if(tipo.equals("Acess�rios")) {			
			Element AcessoriosElement = (Element) pecaElement.getElementsByTagName("Acess�rios").item(0).getChildNodes();
			String quantidade = AcessoriosElement.getElementsByTagName("Quantidade").item(0).getAttributes().getNamedItem("Quantidade").getTextContent();
			System.out.println("\nQuantidade dispon�vel: " + quantidade + " em stock");
			
			System.out.println("\nIntroduza 1 para adicionar este item ao seu carrinho ou 0 para voltar ao menu anterior.");
			String input = apenasNumeros(sc, 1);
			if(input.equals("0")) return;
			else {
				int idPeca = Integer.parseInt(peca.getAttributes().getNamedItem("idPe�a").getTextContent());
				int quantidadePecas = quantidadePretendida(sc, Integer.parseInt(quantidade));
				
				//AdicionarCarrinho(sock, idPeca, quantidadePecas, "");
			}
			
		}else {
			NodeList tamanhos;
			
			if(tipo.equals("Vestu�rio")) {
				Element Vestu�rioElement = (Element) pecaElement.getElementsByTagName("Vestu�rio").item(0).getChildNodes();
				tamanhos = Vestu�rioElement.getElementsByTagName("Tamanho");
			}else {
				Element Vestu�rioElement = (Element) pecaElement.getElementsByTagName("Cal�ado").item(0).getChildNodes();
				tamanhos = Vestu�rioElement.getElementsByTagName("Tamanho");
			}
			
			String tamanho;
			String quantidade;
			
			System.out.println("\nTamanhos e quantidades dispon�veis:\n");
			
			for(int i = 0; i < tamanhos.getLength(); i++) {
				tamanho = tamanhos.item(i).getAttributes().getNamedItem("Valor").getTextContent();
				quantidade = tamanhos.item(i).getAttributes().getNamedItem("Quantidade").getTextContent();
				System.out.println(1+i + " -> tamanho " + tamanho + ": " + quantidade + " em stock");
			}
			
			System.out.println("\nIntroduza o n�mero do item que deseja adicionar ao seu carrinho ou 0 para voltar ao menu anterior.");
			String input = apenasNumeros(sc, tamanhos.getLength());
			if(input.equals("0")) return;
			else {
				int idPeca = Integer.parseInt(peca.getAttributes().getNamedItem("idPe�a").getTextContent());
				int quantidadePecas = quantidadePretendida(sc, Integer.parseInt(tamanhos.item(Integer.parseInt(input)-1).getAttributes().getNamedItem("Quantidade").getTextContent()));
				String tamanhoPretendido = tamanhos.item(Integer.parseInt(input)-1).getAttributes().getNamedItem("Valor").getTextContent();
				
				//AdicionarCarrinho(sock, idPeca, quantidadePecas, tamanhoPretendido);
			}
		}
	}
	
	public static void main(String[] args) {

		String host = DEFAULT_HOSTNAME; // M�quina onde reside a aplica��o
										// servidora
		int port = DEFAULT_PORT; // Porto da aplica��o servidora

		if (args.length > 0) {
			host = args[0];
		}

		if (args.length > 1) {
			try {
				port = Integer.parseInt(args[1]);
				if (port < 1 || port > 65535)
					port = DEFAULT_PORT;
			} catch (NumberFormatException e) {
				System.err.println("Erro no porto indicado");
			}
		}

		System.out.println("-> " + host + ":" + port);

		Socket socket = null;

		try {
			socket = new Socket(host, port);
			
			if (XMLDoc.validDocXSD("utilizador.xml", "utilizador.xsd") &&
					XMLDoc.validDocXSD("pe�a.xml", "pe�a.xsd"))menu(socket);
		} catch (Exception e) {
			System.err.println("Erro na liga��o " + e.getMessage());
		} finally {
			// No fim de tudo, fechar os streams e o socket
			try {
				// if (os != null) os.close();
				// if (is != null) is.close();
				if (socket != null)
					socket.close();
			} catch (Exception e) {
				// if an I/O error occurs when closing this socket
				System.err
						.println("Erro no fecho da liga��o:" + e.getMessage());
			}
		} // end finally

	} // end main




} // end ClienteTCP



