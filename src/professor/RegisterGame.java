package professor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.jdom2.DocType;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import shared.Game;

public class RegisterGame
{
	private List <Element> games;
	private SAXBuilder builder;
	private Document documento;
	private FileWriter writer;
	private XMLOutputter xml;
	private File archivo;
	private Game juego;
	private Element raiz;
	private DocType dtd;
	private String ruta;
	private int lastID;
	
	public RegisterGame (String ruta)
	{
		setRuta(ruta);
	}
	
	public RegisterGame (Game juego)
	{
		//Ruta del archivo a leer/escribir
		setRuta(ruta);
		//Se guardan los datos del nuevo juego
		this.juego = juego;
		//Se inicializa el objeto SAXBuilder
		this.builder = new SAXBuilder ();
		//Se abre el archivo XML que contiene a los juegos
		this.archivo = new File (this.ruta);
	}
	
	/*
	 * Agrega a un juego al archivo XML al final del mismo
	 * Recibe: void
	 * Retorna: void
	 */
	public boolean addGame () throws JDOMException, IOException
	{
		boolean res = true;
		try
		{
			this.raiz = new Element ("JUEGOS");
			//Se agrega a los juegos que ya estaban registrados
			addPastGames ();
			//Se actualiza el valor del ID del nuevo juego
			this.juego.setId(this.lastID + 1);
			//Se agrega al nuevo juego al archivo XML
			this.raiz.addContent(createGame (this.juego));
			//Tipo de documento
			this.dtd = new DocType (this.raiz.getName());
			dtd.setInternalSubset("<!ELEMENT JUEGOS (juego+)>\n"
					+ "<!ELEMENT juego (nombre,creador)>\n<!ELEMENT nombre (#PCDATA)>\n<!ELEMENT creador (#PCDATA)>"
					+ "<!ATTLIST juego id CDATA #REQUIRED>\n");
			//Se escribe el documento XML
			this.documento = new Document(this.raiz, this.dtd);
			this.xml = new XMLOutputter ();
			this.writer = new FileWriter (this.ruta);
			this.xml.setFormat(Format.getPrettyFormat());
			this.xml.output(this.documento, this.writer);
			this.writer.flush();
			this.writer.close();
		}catch (IOException e)
		{
			res = false;
			e.printStackTrace();
		}
		return res;
	}
	
	/*
	 * Crea un elemento XML 'juego' para ser añadido a algún otro elemento
	 * Recibe: Game juego con todos los parámetros listos para ser añadido
	 * Retorna: Elemento XML 'game'
	 */
	public Element createGame (Game juego)
	{
		Element game = new Element ("juego");
		game.setAttribute("id", Integer.toString(juego.getId()));
		Element nombre = new Element ("nombre");
		Element creador = new Element ("creador");
		nombre.setText(juego.getNombre());
		creador.setText(juego.getCreador());
		game.addContent(nombre);
		game.addContent(creador);
		return game;
	}
	
	/*
	 * Agrega al archivo XML a todos los juegos que ya estaban registrados
	 * Recibe: void
	 * Retorna: void
	 */
	public void addPastGames ()
	{
		try
		{
			//Objeto de tipo Document para manipular archivo XML
			this.documento = builder.build(this.archivo);
			//Obtenemos el nodo raíz del documento XML
			Element raiz = this.documento.getRootElement();
			//Obtenemos la lista de todos los elementos 'juego' del nodo raíz
			this.games = raiz.getChildren("juego");
			//Se crea un juego vacío
			Game juego = new Game (this.ruta);
			//Recorremos la lista de juegos obtenida
			for (Element game : this.games)
			{
				juego.setId(Integer.parseInt(game.getAttributeValue("id")));
				juego.setNombre(game.getChildText("nombre"));
				juego.setCreador(game.getChildText("creador"));
				this.raiz.addContent(createGame (juego));
				this.lastID = juego.getId();
			}
			return;
		}catch (IOException e)
		{
			e.printStackTrace();
		}catch (JDOMException e)
		{
			e.printStackTrace();
		}
	}
	
	public void setRuta(String ruta)
	{
		this.ruta = ruta;
	}
}
