import javax.xml.XMLConstants;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class comando {
	Document cmd = null; 
	
	public comando() {
		cmd = XMLReadWrite.documentFromString("<?xml version='1.0' encoding='ISO-8859-1'?><protocol></protocol>");
	}
	
	public comando(Document D) {
		cmd = D;
	}
	
	public void show() {
		XMLReadWrite.writeDocument(cmd, System.out);
	}
	
	public Document requestRegistar(String nif, String nome, String dataNasc) {
		Element registar = cmd.createElement("registar");
		Element request = cmd.createElement("request");
		registar.appendChild(request);
		
		Element protocol = (Element) cmd.getElementsByTagName("protocol").item(0);
		
		protocol.appendChild(registar);
		
		Element nifElem = cmd.createElement("nif");
		nifElem.appendChild(cmd.createTextNode(nif));
		Element nomeElem = cmd.createElement("nome");
		nomeElem.appendChild(cmd.createTextNode(nome));
		Element dataElem = cmd.createElement("dataNasc");
		dataElem.appendChild(cmd.createTextNode(dataNasc));
		
		request.appendChild(nifElem);
		request.appendChild(nomeElem);
		request.appendChild(dataElem);
		
		return cmd;
	}
	
	public Document replyRegistar() {
		Document utilizadores = Loja.getUtilizadores();
		String nif = cmd.getElementsByTagName("nif").item(0).getTextContent();
		String nome = cmd.getElementsByTagName("nome").item(0).getTextContent();
		String dataNasc = cmd.getElementsByTagName("dataNasc").item(0).getTextContent();
		
		Element utilizador = utilizadores.createElement("Utilizador");
		Element cliente = utilizadores.createElement("Cliente");
		
		utilizador.setAttribute("NIF", nif);
		utilizador.setAttribute("Nome", nome);
		utilizador.setAttribute("DataNasc", dataNasc);
		
		utilizador.appendChild(cliente);
		utilizadores.getDocumentElement().appendChild(utilizador);
		
		Element reply = cmd.createElement("reply");
	
		Element registar = (Element) cmd.getElementsByTagName("registar").item(0);

		Element utilizadorVazio = cmd.createElement("Utilizador");
		
		registar.appendChild(reply);
		
		try {
			if(!XMLDoc.validDoc(utilizadores, "utilizador.xsd", XMLConstants.W3C_XML_SCHEMA_NS_URI)) {
				utilizadores.getDocumentElement().removeChild(utilizador);
				reply.appendChild(utilizadorVazio);
				return cmd;
			}
			XMLDoc.writeDocument(utilizadores, "utilizador.xml");
			
			NodeList todosUtilizadores = utilizadores.getElementsByTagName("Utilizador");
			for(int i = 0; i < todosUtilizadores.getLength(); i++) {
				if(todosUtilizadores.item(i).getAttributes().getNamedItem("NIF").getTextContent().equals(nif)) {
					Element clone = (Element) cmd.importNode(todosUtilizadores.item(i), true);
					reply.appendChild(clone);
				}
			}
			return cmd;
		} catch (SAXException e) {
			//e.printStackTrace();
			utilizadores.getDocumentElement().removeChild(utilizador);
			reply.appendChild(utilizadorVazio);
			return cmd;
		}
	}
	
	public Document requestCarrinho(String nif) {
		Element carrinhos = cmd.createElement("carrinho");
		Element request = cmd.createElement("request");
		carrinhos.appendChild(request);
		
		Element protocol = (Element) cmd.getElementsByTagName("protocol").item(0);
		
		protocol.appendChild(carrinhos);
		
		Element nifElem = cmd.createElement("nif");
		nifElem.appendChild(cmd.createTextNode(nif));
		
		request.appendChild(nifElem);
		return cmd;
	}
	
	public Document replyCarrinho() {
		Document utilizadores = Loja.getUtilizadores();
		String nif = cmd.getElementsByTagName("nif").item(0).getTextContent();
		Element reply = cmd.createElement("reply");
		
		NodeList todosUtilizadores = utilizadores.getElementsByTagName("Utilizador");
		
		for(int i = 0; i < todosUtilizadores.getLength(); i++) {
			if(todosUtilizadores.item(i).getAttributes().getNamedItem("NIF").getTextContent().equals(nif)) {
				Node carrinhoUtilizador = ((Element)todosUtilizadores.item(i)).getElementsByTagName("Carrinho").item(0);
				Element clone = (Element) cmd.importNode(carrinhoUtilizador, true);
				reply.appendChild(clone);
			}
		}
		Element carrinhosReply = (Element) cmd.getElementsByTagName("carrinho").item(0);
		carrinhosReply.appendChild(reply);
		return cmd;
	}
	
	public Document requestPecasTotal() {
		Element pecasTotal = cmd.createElement("pecasTotal");
		Element request = cmd.createElement("request");
		pecasTotal.appendChild(request);
		Element protocol = (Element) cmd.getElementsByTagName("protocol").item(0);
		protocol.appendChild(pecasTotal);
		return cmd;
	}
	
	public Document replyPecasTotal() {
		//TODO
		Document catalogo = Loja.getPecas();
		Element reply = cmd.createElement("reply");
		
		NodeList pecas = catalogo.getElementsByTagName("Pe�a");
		
		for(int i = 0; i < pecas.getLength(); i++) {
			Element clone = (Element) cmd.importNode(pecas.item(i), true);
			reply.appendChild(clone);
		}
		Element pecasTotal = (Element) cmd.getElementsByTagName("pecasTotal").item(0);
		pecasTotal.appendChild(reply);
		return cmd;
	}
	
	public Document requestLogin(String nif) {
		Element login = cmd.createElement("login");
		Element request = cmd.createElement("request");
		login.appendChild(request);
		Element protocol = (Element) cmd.getElementsByTagName("protocol").item(0);
		protocol.appendChild(login);
		
		Element nifElem = cmd.createElement("nif");	
		nifElem.appendChild(cmd.createTextNode(nif));
		request.appendChild(nifElem);

		return cmd;
	}
	
	public Document replyLogin() {
		Document utilizadores = Loja.getUtilizadores();
		NodeList util = utilizadores.getElementsByTagName("Utilizador");
		
		Element reply = cmd.createElement("reply");
		
		for(int i = 0; i < util.getLength(); i++) {
			if(util.item(i).getAttributes().getNamedItem("NIF").getTextContent().equals(cmd.getElementsByTagName("nif").item(0).getTextContent())) {
				Element clone = (Element) cmd.importNode(util.item(i), true);
				reply.appendChild(clone);
			}
		}
		Element login = (Element)cmd.getElementsByTagName("login").item(0);
		login.appendChild(reply);
		return cmd;
	}
	
	public Document requestPecaID(String idPeca) {
		Element pecaID = cmd.createElement("pecaID");
		Element request = cmd.createElement("request");
		
		pecaID.appendChild(request);
		
		Element protocol = (Element) cmd.getElementsByTagName("protocol").item(0);
		protocol.appendChild(pecaID);
		
		Element idPecaElem = cmd.createElement("idPeca");
		idPecaElem.appendChild(cmd.createTextNode(idPeca));
		request.appendChild(idPecaElem);
		
		return cmd;
	}
	
	public Document replyPecaID() {
		Document pecas = Loja.getPecas();
		NodeList todasPecas = pecas.getElementsByTagName("Pe�a");
		String idPeca = cmd.getElementsByTagName("idPeca").item(0).getTextContent();
		Element reply = cmd.createElement("reply");
		for(int i = 0; i < todasPecas.getLength(); i++) {
			if(todasPecas.item(i).getAttributes().getNamedItem("idPe�a").getTextContent().equals(idPeca)) {
				Element clone = (Element) cmd.importNode(todasPecas.item(i), true);
				reply.appendChild(clone);
			}
		}
		Element pecaID = (Element)cmd.getElementsByTagName("pecaID").item(0);
		pecaID.appendChild(reply);
		
		
		return cmd;
	}
	
	public Document requestCatalogo(String seccao) {
		Element catalogo = cmd.createElement("catalogo");
		Element request = cmd.createElement("request");
		catalogo.appendChild(request);
		Element protocol = (Element) cmd.getElementsByTagName("protocol").item(0);
		protocol.appendChild(catalogo);
		
		Element seccaoElem = cmd.createElement("seccao");
		seccaoElem.appendChild(cmd.createTextNode(seccao));
		request.appendChild(seccaoElem);

		return cmd;
	}
	
	public Document replyCatalogo() {
		Document catalogo = Loja.getPecas();
		NodeList pecas = catalogo.getElementsByTagName("Pe�a");
		
		Element reply = cmd.createElement("reply");
		
		for(int i = 0; i < pecas.getLength(); i++) {
			if(pecas.item(i).getAttributes().getNamedItem("Sec��o").getTextContent().equals(cmd.getElementsByTagName("seccao").item(0).getTextContent())) {
				Element clone = (Element) cmd.importNode(pecas.item(i), true);
				reply.appendChild(clone);
			}
		}
		Element catal = (Element)cmd.getElementsByTagName("catalogo").item(0);
		catal.appendChild(reply);
		
		return cmd;
	}
	
	
	public Document requestAdicionarCarrinho(String nif, int idPeca, String tamanho, int quantidade) {
		Element adicionarCarrinho = cmd.createElement("adicionarCarrinho");
		Element request = cmd.createElement("request");
		adicionarCarrinho.appendChild(request);
		
		Element nifElem = cmd.createElement("nif");
		Element idPecaElem = cmd.createElement("idPeca");
		Element tamanhoElem = cmd.createElement("tamanho");
		Element quantidadeElem = cmd.createElement("quantidade");
		
		nifElem.appendChild(cmd.createTextNode(nif));
		idPecaElem.appendChild(cmd.createTextNode(String.valueOf(idPeca)));
		tamanhoElem.appendChild(cmd.createTextNode(tamanho));
		quantidadeElem.appendChild(cmd.createTextNode(String.valueOf(quantidade)));
		
		request.appendChild(nifElem);
		request.appendChild(idPecaElem);
		request.appendChild(tamanhoElem);
		request.appendChild(quantidadeElem);
		
		Element protocol = (Element) cmd.getElementsByTagName("protocol").item(0);
		protocol.appendChild(adicionarCarrinho);
		
		return cmd;
	}
	
	public Document replyAdicionarCarrinho() {
		//TODO
		Document todosUtilizadores = Loja.getUtilizadores();
		NodeList pecas = Loja.getPecas().getElementsByTagName("Pe�a");
		NodeList utilizadores = todosUtilizadores.getElementsByTagName("Utilizador");
		String idPeca = cmd.getElementsByTagName("idPeca").item(0).getTextContent();
		String nif = cmd.getElementsByTagName("nif").item(0).getTextContent();
		String tamanho = cmd.getElementsByTagName("tamanho").item(0).getTextContent();
		String quantidade = cmd.getElementsByTagName("quantidade").item(0).getTextContent();
		
		Node carrinho = null;
		Node utilizador = null;
		for(int i = 0; i < utilizadores.getLength(); i++) {
			if(utilizadores.item(i).getAttributes().getNamedItem("NIF").getTextContent().equals(nif)) {
				utilizador = utilizadores.item(i).getFirstChild().getNextSibling();
				if(((Element)utilizadores.item(i)).getElementsByTagName("Carrinho").getLength() != 0) {
					carrinho = ((Element)utilizadores.item(i)).getElementsByTagName("Carrinho").item(0);
				}
				break;
			}
		}
		
		Node peca = null;
		for(int i = 0; i < pecas.getLength(); i++) {
			if(pecas.item(i).getAttributes().getNamedItem("idPe�a").getTextContent().equals(idPeca)) {
				peca = pecas.item(i);
				break;
			}
		}
		
		if(peca == null) {
			return cmd;
		}
		
		Element pecaElem = todosUtilizadores.createElement("Pe�a");
		pecaElem.setAttribute("ID", idPeca);
		pecaElem.setAttribute("Tamanho", tamanho);
		pecaElem.setAttribute("Quantidade", quantidade);
	
		Element reply = cmd.createElement("reply");
		
		if(carrinho != null) {
			carrinho.appendChild(pecaElem);
			Element clone = (Element)cmd.importNode(carrinho, true);
			reply.appendChild(clone);
		}
		else {
			Element carrinhoElem = todosUtilizadores.createElement("Carrinho");
			carrinhoElem.appendChild(pecaElem);
			utilizador.appendChild(carrinhoElem);
			Element clone = (Element)cmd.importNode(carrinhoElem, true);
			reply.appendChild(clone);
		}
		
		Element adicionarCarrinho = (Element)cmd.getElementsByTagName("adicionarCarrinho").item(0);
		adicionarCarrinho.appendChild(reply);
		try {
			if(!XMLDoc.validDoc(todosUtilizadores, "utilizador.xsd", XMLConstants.W3C_XML_SCHEMA_NS_URI)) {
				//todosUtilizadores.getDocumentElement().removeChild(utilizador);
				//reply.appendChild(utilizadorVazio);
				return cmd;
			}
			XMLDoc.writeDocument(todosUtilizadores, "utilizador.xml");
			
			/*NodeList todosUtilizadores = todosUtilizadores.getElementsByTagName("Utilizador");
			for(int i = 0; i < todosUtilizadores.getLength(); i++) {
				if(todosUtilizadores.item(i).getAttributes().getNamedItem("NIF").getTextContent().equals(nif)) {
					Element clone = (Element) cmd.importNode(todosUtilizadores.item(i), true);
					reply.appendChild(clone);
				}
			}*/
			return cmd;
		} catch (SAXException e) {
			//e.printStackTrace();
			//todosUtilizadores.getDocumentElement().removeChild(utilizador);
			//reply.appendChild(utilizadorVazio);
			return cmd;
		}
	}
	
		
	public Document requestTodosCarrinhos() {
		Element todosCarrinhos = cmd.createElement("mostrarTodosCarrinhos");
		Element request = cmd.createElement("request");
		todosCarrinhos.appendChild(request);
		Element protocol = (Element) cmd.getElementsByTagName("protocol").item(0);
		protocol.appendChild(todosCarrinhos);
		return cmd;
	}

	public Document replyTodosCarrinhos() {
		Document utilizadores = Loja.getUtilizadores();
		NodeList carrinhos = utilizadores.getElementsByTagName("Carrinho");
		
		Element reply = cmd.createElement("reply");
		
		for(int i = 0; i < carrinhos.getLength(); i++) {
			String nif = carrinhos.item(i).getParentNode().getParentNode().getAttributes().getNamedItem("NIF").getTextContent();
			((Element)carrinhos.item(i)).setAttribute("nif", nif);
			Element carrinhoClone = (Element)cmd.importNode(carrinhos.item(i), true);
			reply.appendChild(carrinhoClone);
		}
		
		Element todosCarrinhos = (Element) cmd.getElementsByTagName("mostrarTodosCarrinhos").item(0);
		
		todosCarrinhos.appendChild(reply);
		
		return cmd;
	}
	/*
	private Document replyConsultar() {  // usar no servidor
		//TODO
		//mudar para loja e tal
		Document titulos=Poema.Consultar();
		NodeList T = titulos.getElementsByTagName("t�tulo");
		Element reply = cmd.createElement("reply");
		
		if(carrinho != null) {
			carrinho.appendChild(pecaElem);
			Element clone = (Element) cmd.importNode(carrinho, true);
			reply.appendChild(clone);
		}
		else {
			Element carrinhoElem = todosUtilizadores.createElement("Carrinho");
			carrinhoElem.appendChild(pecaElem);
			utilizador.appendChild(carrinhoElem);
			Element clone = (Element)cmd.importNode(carrinhoElem, true);
			reply.appendChild(clone);
		}
		
		Element adicionarCarrinho = (Element)cmd.getElementsByTagName("adicionarCarrinho").item(0);
		adicionarCarrinho.appendChild(reply);
		try {
			if(!XMLDoc.validDoc(todosUtilizadores, "utilizador.xsd", XMLConstants.W3C_XML_SCHEMA_NS_URI)) {
				//todosUtilizadores.getDocumentElement().removeChild(utilizador);
				//reply.appendChild(utilizadorVazio);
				return cmd;
			}
			XMLDoc.writeDocument(todosUtilizadores, "utilizador.xml");
			
			/*NodeList todosUtilizadores = todosUtilizadores.getElementsByTagName("Utilizador");
			for(int i = 0; i < todosUtilizadores.getLength(); i++) {
				if(todosUtilizadores.item(i).getAttributes().getNamedItem("NIF").getTextContent().equals(nif)) {
					Element clone = (Element) cmd.importNode(todosUtilizadores.item(i), true);
					reply.appendChild(clone);
				}
			}
			return cmd;
		} catch (SAXException e) {
			//e.printStackTrace();
			//todosUtilizadores.getDocumentElement().removeChild(utilizador);
			//reply.appendChild(utilizadorVazio);
			return cmd;
		}
	}
	*/
	
	
	public Document requestAprovarCarrinho(String nif) {
		Element aprovarCarrinho = cmd.createElement("aprovarCarrinho");
		Element request = cmd.createElement("request");
		aprovarCarrinho.appendChild(request);
		Element protocol = (Element) cmd.getElementsByTagName("protocol").item(0);
		protocol.appendChild(aprovarCarrinho);
		
		Element nifElem = cmd.createElement("nif");	
		nifElem.appendChild(cmd.createTextNode(nif));
		request.appendChild(nifElem);

		return cmd;
	}
	
	public Document replyAprovarCarrinho() {
		Document utilizadores = Loja.getUtilizadores();
		Document pecas = Loja.getPecas();
		String nif = cmd.getElementsByTagName("nif").item(0).getTextContent();
		NodeList util = utilizadores.getElementsByTagName("Utilizador");
		Node utilizador = null;
		for(int i = 0; i < util.getLength(); i++) {
			if(util.item(i).getAttributes().getNamedItem("NIF").getTextContent().equals(nif)) {
				utilizador = util.item(i);
				break;
			}
		}
		
		Node carrinho = utilizador.getChildNodes().item(1).getChildNodes().item(1);
		NodeList pecasCarrinho = ((Element)carrinho).getElementsByTagName("Pe�a");
		NodeList todasPecas = pecas.getElementsByTagName("Pe�a");

		for(int i = 0; i < pecasCarrinho.getLength(); i++) {
			Node peca = null;
			int quantidade = Integer.parseInt(pecasCarrinho.item(i).getAttributes().getNamedItem("Quantidade").getTextContent());
			String tamanho = pecasCarrinho.item(i).getAttributes().getNamedItem("Tamanho").getTextContent();
			if(pecasCarrinho.item(i).getNodeName().equals("Pe�a")) {
				String idPeca = pecasCarrinho.item(i).getAttributes().getNamedItem("ID").getTextContent();
				for(int j = 0; j < todasPecas.getLength(); j++) {
					if(todasPecas.item(j).getAttributes().getNamedItem("idPe�a").getTextContent().equals(idPeca)){
						peca = todasPecas.item(j);
						break;
					}
				}
			}
			
			NodeList tamanhos = ((Element)peca).getElementsByTagName("Tamanho");
			for(int j = 0; j < tamanhos.getLength(); j++) {
				if(tamanhos.item(j).getAttributes().getNamedItem("Valor").getTextContent().equals(tamanho)) {
					int quantidadeExistente = Integer.parseInt(tamanhos.item(j).getAttributes().getNamedItem("Quantidade").getTextContent());
					if(quantidadeExistente < quantidade) {
						Element reply = cmd.createElement("reply");
						NodeList carrinhos = utilizadores.getElementsByTagName("Carrinho");
						for(int k = 0; k < carrinhos.getLength(); k++) {
							Element clone = (Element) cmd.importNode(carrinhos.item(k), true);
							reply.appendChild(clone);
						}
						Element aprovarCarrinho = (Element) cmd.getElementsByTagName("aprovarCarrinho").item(0);
						aprovarCarrinho.appendChild(reply);
						return cmd;
					}
					else {
						((Element)tamanhos.item(j)).setAttribute("Quantidade", String.valueOf(quantidadeExistente-quantidade));
					}
				}
			}
		}
		
		utilizador.getChildNodes().item(1).removeChild(carrinho);
				
		Element reply = cmd.createElement("reply");
		
		Element aprovarCarrinho = (Element) cmd.getElementsByTagName("aprovarCarrinho").item(0);
		
		aprovarCarrinho.appendChild(reply);
		
		try {
			if(!XMLDoc.validDoc(utilizadores, "utilizador.xsd", XMLConstants.W3C_XML_SCHEMA_NS_URI)) {
				return cmd;
			}
			XMLDoc.writeDocument(utilizadores, "utilizador.xml");
			XMLDoc.writeDocument(pecas, "pe�a.xml");
			
			NodeList todosCarrinhos = utilizadores.getElementsByTagName("Carrinho");
			for(int i = 0; i < todosCarrinhos.getLength(); i++) {
				Element clone = (Element) cmd.importNode(todosCarrinhos.item(i), true);
				reply.appendChild(clone);
			}
			return cmd;
		} catch (SAXException e) {
			//e.printStackTrace();
			
			return cmd;
		}
	}
	
	
	public Document requestAdicionarPeca(String seccao, String tipo, String designacao, String marca, String descricao, String preco, String base64) {
		Element adicionarPeca = cmd.createElement("adicionarPeca");
		Element request = cmd.createElement("request");
		adicionarPeca.appendChild(request);
		
		Element designacaoElem = cmd.createElement("designacao");
		Element seccaoElem = cmd.createElement("seccao");
		Element precoElem = cmd.createElement("preco");
		Element tipoElem = cmd.createElement("tipo");
		Element marcaElem = cmd.createElement("marca");
		Element descricaoElem = cmd.createElement("descricao");
		Element base64Elem = cmd.createElement("foto");
		
		designacaoElem.appendChild(cmd.createTextNode(designacao));
		seccaoElem.appendChild(cmd.createTextNode(seccao));
		precoElem.appendChild(cmd.createTextNode(preco));
		tipoElem.appendChild(cmd.createTextNode(tipo));
		marcaElem.appendChild(cmd.createTextNode(marca));
		descricaoElem.appendChild(cmd.createTextNode(descricao));
		base64Elem.appendChild(cmd.createTextNode(base64));
		
		request.appendChild(designacaoElem);
		request.appendChild(seccaoElem);
		request.appendChild(precoElem);
		request.appendChild(tipoElem);
		request.appendChild(marcaElem);
		request.appendChild(descricaoElem);
		request.appendChild(base64Elem);
		
		Element protocol = (Element)cmd.getElementsByTagName("protocol").item(0);
		protocol.appendChild(adicionarPeca);
		return cmd;
	}
	
	public Document replyAdicionarPeca() {
		Document pecas = Loja.getPecas();
		String designacao = cmd.getElementsByTagName("designacao").item(0).getTextContent();
		String seccao = cmd.getElementsByTagName("seccao").item(0).getTextContent();
		String preco = cmd.getElementsByTagName("preco").item(0).getTextContent();
		String tipo = cmd.getElementsByTagName("tipo").item(0).getTextContent();
		String marca = cmd.getElementsByTagName("marca").item(0).getTextContent();
		String descricao = cmd.getElementsByTagName("descricao").item(0).getTextContent();
		String fotoBase64 = cmd.getElementsByTagName("foto").item(0).getTextContent();
		
		int maxID = 0;
		for(int i = 0; i < pecas.getElementsByTagName("Pe�a").getLength(); i++) {
			int idPeca = Integer.parseInt(pecas.getElementsByTagName("Pe�a").item(i).getAttributes().getNamedItem("idPe�a").getTextContent());
			if(idPeca > maxID) {
				maxID = idPeca;
			}
		}
		int idPecaCriar = maxID + 1;
		
		Element pecaElem = pecas.createElement("Pe�a");
		Element fotoElem = pecas.createElement("Foto");
		Element descricaoElem = pecas.createElement("Descri��o");
		Element caractElem = pecas.createElement("Caracteristica");
		Element tipoElem = pecas.createElement(tipo);
		
	
		pecaElem.setAttribute("idPe�a", String.valueOf(idPecaCriar));
		pecaElem.setAttribute("Designa��o", designacao);
		pecaElem.setAttribute("Marca", marca);
		pecaElem.setAttribute("Sec��o", seccao);
		pecaElem.setAttribute("Pre�o", preco);
		
		caractElem.appendChild(pecas.createTextNode(descricao));
		descricaoElem.appendChild(caractElem);
		fotoElem.appendChild(pecas.createTextNode(fotoBase64));
		pecaElem.appendChild(fotoElem);
		pecaElem.appendChild(descricaoElem);
		pecaElem.appendChild(tipoElem);
		
		pecas.getDocumentElement().appendChild(pecaElem);

		Element reply = cmd.createElement("reply");
		
		XMLDoc.writeDocument(pecas, "pe�a.xml");
		if(!XMLDoc.validDocXSD("pe�a.xml", "pe�a.xsd")) {
			pecas.getDocumentElement().removeChild(pecaElem);
			XMLDoc.writeDocument(pecas, "pe�a.xml");	
			Element pecaClone = (Element)cmd.importNode(pecaElem, true);
			
			Element adicionarPeca = (Element)cmd.getElementsByTagName("adicionarPeca").item(0);
			
			reply.appendChild(pecaClone);
			
			adicionarPeca.appendChild(reply);
			
			return cmd;
		}
		else {
			
			Element pecaClone = (Element)cmd.importNode(pecaElem, true);
			
			Element adicionarPeca = (Element)cmd.getElementsByTagName("adicionarPeca").item(0);
			
			reply.appendChild(pecaClone);
			
			adicionarPeca.appendChild(reply);
			
			return cmd;

		}
	}
	
	public Document requestModificarPreco(String idPeca, String preco) {
		Element modificarPreco = cmd.createElement("modificarPreco");
		Element request = cmd.createElement("request");
		modificarPreco.appendChild(request);
		
		Element protocol = (Element)cmd.getElementsByTagName("protocol").item(0);
		protocol.appendChild(modificarPreco);
		
		Element idPecaElem = cmd.createElement("idPeca");
		Element precoElem = cmd.createElement("preco");
		idPecaElem.appendChild(cmd.createTextNode(idPeca));
		precoElem.appendChild(cmd.createTextNode(preco));
		
		request.appendChild(idPecaElem);
		request.appendChild(precoElem);
		return cmd;
	}
	
	public Document replyModificarPreco() {
		Document pecas = Loja.getPecas();
		String idPeca = cmd.getElementsByTagName("idPeca").item(0).getTextContent();
		String preco = cmd.getElementsByTagName("preco").item(0).getTextContent();
		
		String oldPreco = null;
		Node peca = null;
		for(int i = 0; i < pecas.getElementsByTagName("Pe�a").getLength(); i++) {
			if(pecas.getElementsByTagName("Pe�a").item(i).getAttributes().getNamedItem("idPe�a").getTextContent().equals(idPeca)) {
				peca = pecas.getElementsByTagName("Pe�a").item(i);
				oldPreco = peca.getAttributes().getNamedItem("Pre�o").getTextContent();
				break;
			}
		}
		((Element)peca).setAttribute("Pre�o", preco);
		
		Element reply = cmd.createElement("reply");
	
		XMLDoc.writeDocument(pecas, "pe�a.xml");
		if(!XMLDoc.validDocXSD("pe�a.xml", "pe�a.xsd")) {
			((Element)peca).setAttribute("Pre�o", oldPreco);
			XMLDoc.writeDocument(pecas, "pe�a.xml");	
			Element pecaClone = (Element)cmd.importNode(peca, true);
			
			Element modificarPreco = (Element)cmd.getElementsByTagName("modificarPreco").item(0);
			
			reply.appendChild(pecaClone);
			
			modificarPreco.appendChild(reply);
			
			return cmd;
		}
		else {
			
			Element pecaClone = (Element)cmd.importNode(peca, true);
			
			Element modificarPreco = (Element)cmd.getElementsByTagName("modificarPreco").item(0);
			
			reply.appendChild(pecaClone);
			
			modificarPreco.appendChild(reply);
			
			return cmd;
		}
	}
	
	public Document requestModificarQuantidade(String idPeca, String tamanho, String quantidade) {
		Element modificarQuantidade = cmd.createElement("modificarQuantidade");
		Element request = cmd.createElement("request");
		modificarQuantidade.appendChild(request);
		Element protocol = (Element) cmd.getElementsByTagName("protocol").item(0);
		protocol.appendChild(modificarQuantidade);
		
		Element idPecaElem = cmd.createElement("idPeca");
		Element tamanhoElem = cmd.createElement("tamanho");
		Element quantidadeElem = cmd.createElement("quantidade");
		
		idPecaElem.appendChild(cmd.createTextNode(idPeca));
		tamanhoElem.appendChild(cmd.createTextNode(tamanho));
		quantidadeElem.appendChild(cmd.createTextNode(quantidade));
		
		request.appendChild(idPecaElem);
		request.appendChild(tamanhoElem);
		request.appendChild(quantidadeElem);
		return cmd;
	}
	
	public Document replyModificarQuantidade() {
		Document pecas = Loja.getPecas();
		String idPeca = cmd.getElementsByTagName("idPeca").item(0).getTextContent();
		String tamanho = cmd.getElementsByTagName("tamanho").item(0).getTextContent();
		String quantidade = cmd.getElementsByTagName("quantidade").item(0).getTextContent();
		
		Node peca = null;
		for(int i = 0; i < pecas.getElementsByTagName("Pe�a").getLength(); i++) {
			if(pecas.getElementsByTagName("Pe�a").item(i).getAttributes().getNamedItem("idPe�a").getTextContent().equals(idPeca)) {
				peca = pecas.getElementsByTagName("Pe�a").item(i);
				break;
			}
		}
		NodeList tamanhos = ((Element)peca).getElementsByTagName("Tamanho");
		Node tamanhoAlterar = null;
		for(int i = 0; i < tamanhos.getLength(); i++) {
			if(tamanhos.item(i).getAttributes().getNamedItem("Valor").getTextContent().equals(tamanho)) {
				tamanhoAlterar = tamanhos.item(i);
				break;
			}
		}
		Element reply = cmd.createElement("reply");
		if(tamanhoAlterar == null) {
			Element tamanhoElem = pecas.createElement("Tamanho");
			tamanhoElem.setAttribute("Valor", tamanho);
			tamanhoElem.setAttribute("Quantidade", quantidade);
			peca.getChildNodes().item(5).appendChild(tamanhoElem);
			XMLDoc.writeDocument(pecas, "pe�a.xml");
			if(!XMLDoc.validDocXSD("pe�a.xml", "pe�a.xsd")) {
				peca.getChildNodes().item(5).removeChild(tamanhoElem);
				XMLDoc.writeDocument(pecas, "pe�a.xml");	
				Element pecaClone = (Element)cmd.importNode(peca, true);
				
				Element modificarQuantidade = (Element)cmd.getElementsByTagName("modificarQuantidade");
				
				reply.appendChild(pecaClone);
				
				modificarQuantidade.appendChild(reply);
				
				return cmd;
			}
			else {
				
				Element pecaClone = (Element)cmd.importNode(peca, true);
				
				Element modificarQuantidade = (Element)cmd.getElementsByTagName("modificarQuantidade").item(0);
				
				reply.appendChild(pecaClone);
				
				modificarQuantidade.appendChild(reply);
				
				return cmd;
			}
		}
		
		else {
			String oldQuantidade = tamanhoAlterar.getAttributes().getNamedItem("Quantidade").getTextContent();
			((Element)tamanhoAlterar).setAttribute("Quantidade", quantidade);
			
			XMLDoc.writeDocument(pecas, "pe�a.xml");
			if(!XMLDoc.validDocXSD("pe�a.xml", "pe�a.xsd")) {
				((Element)tamanhoAlterar).setAttribute("Quantidade", oldQuantidade);
				XMLDoc.writeDocument(pecas, "pe�a.xml");	
				Element pecaClone = (Element)cmd.importNode(peca, true);
				
				Element modificarQuantidade = (Element)cmd.getElementsByTagName("modificarQuantidade");
				
				reply.appendChild(pecaClone);
				
				modificarQuantidade.appendChild(reply);
				
				return cmd;
			}
			else {
				
				Element pecaClone = (Element)cmd.importNode(peca, true);
				
				Element modificarQuantidade = (Element)cmd.getElementsByTagName("modificarQuantidade").item(0);
				
				reply.appendChild(pecaClone);
				
				modificarQuantidade.appendChild(reply);
				
				return cmd;
			}
		}
	}
	
	
	public Document reply() {  // usar no servidor
		Document com = null;
		show();
		if(cmd.getElementsByTagName("login").getLength()==1)
			com = replyLogin();
		
		if(cmd.getElementsByTagName("catalogo").getLength()==1)
			com = replyCatalogo();
		
		if(cmd.getElementsByTagName("registar").getLength()==1)
			com = replyRegistar();
		
		if(cmd.getElementsByTagName("pecasTotal").getLength()==1)
			com = replyPecasTotal();
		
		if(cmd.getElementsByTagName("mostrarTodosCarrinhos").getLength()==1)
			com = replyTodosCarrinhos();
		
		if(cmd.getElementsByTagName("aprovarCarrinho").getLength()==1)
			com = replyAprovarCarrinho();
		
		if(cmd.getElementsByTagName("pecaID").getLength()==1)
			com = replyPecaID();
		
		if(cmd.getElementsByTagName("adicionarPeca").getLength()==1)
			com = replyAdicionarPeca();
		
		if(cmd.getElementsByTagName("adicionarCarrinho").getLength()==1)
			com = replyAdicionarCarrinho();
		
		if(cmd.getElementsByTagName("carrinho").getLength()==1)
			com = replyCarrinho();
		
		if(cmd.getElementsByTagName("modificarPreco").getLength()==1)
			com = replyModificarPreco();
		
		if(cmd.getElementsByTagName("modificarQuantidade").getLength()==1)
			com = replyModificarQuantidade();

		
		if(com==null)
			return cmd;
		else {
			try {
				if (XMLDoc.validDoc(com, Loja.contexto+"protocol.xsd", XMLConstants.W3C_XML_SCHEMA_NS_URI))
					System.out.println("\nValida��o com XSD realizada com sucesso!");
			} catch (SAXException e) {
				System.out.println("\nFalhou a valida��o com XSD (protocol.xsd)!"+e.getMessage());
				e.printStackTrace();
			}
			return com;
		}
	}
}
