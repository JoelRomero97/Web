package professor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
	
	public RegisterGame (Game juego, String ruta)
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
					+ "<!ELEMENT juego (nombre,creador,audio_correcto,audio_incorrecto,imagen+)>\n<!ELEMENT nombre (#PCDATA)>\n<!ELEMENT creador (#PCDATA)>"
					+ "\n<!ELEMENT audio_correcto (#PCDATA)>\n<!ELEMENT audio_incorrecto (#PCDATA)>\n<!ELEMENT imagen (#PCDATA)>\n<!ATTLIST juego id CDATA #REQUIRED>\n");
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
	 * Crea un elemento XML 'juego' para ser a�adido a alg�n otro elemento
	 * Recibe: Game juego con todos los par�metros listos para ser a�adido
	 * Retorna: Elemento XML 'game'
	 */
	public Element createGame (Game juego)
	{
		Element game = new Element ("juego");
		game.setAttribute("id", Integer.toString(juego.getId()));
		Element nombre = new Element ("nombre");
		Element creador = new Element ("creador");
		Element audio_correcto = new Element ("audio_correcto");
		Element audio_incorrecto = new Element ("audio_incorrecto");
		nombre.setText(juego.getNombre());
		creador.setText(juego.getCreador());
		audio_correcto.setText(juego.getAudio_correcto());
		audio_incorrecto.setText(juego.getAudio_incorrecto());
		game.addContent(nombre);
		game.addContent(creador);
		game.addContent(audio_correcto);
		game.addContent(audio_incorrecto);
		for (int i = 0; i < juego.getImagenes().size(); i ++)
		{
			Element imagen = new Element ("imagen");
			imagen.setText(juego.getImagenes().get(i));
			game.addContent(imagen);
		}
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
			//Obtenemos el nodo ra�z del documento XML
			Element raiz = this.documento.getRootElement();
			//Obtenemos la lista de todos los elementos 'juego' del nodo ra�z
			this.games = raiz.getChildren("juego");
			//Se crea un juego vac�o
			Game juego = new Game (this.ruta);
			//Recorremos la lista de juegos obtenida
			for (Element game : this.games)
			{
				juego.setId(Integer.parseInt(game.getAttributeValue("id")));
				juego.setNombre(game.getChildText("nombre"));
				juego.setCreador(game.getChildText("creador"));
				juego.setAudio_correcto(game.getChildText("audio_correcto"));
				juego.setAudio_incorrecto(game.getChildText("audio_incorrecto"));
				List <Element> imagenes = game.getChildren("imagen");
				ArrayList <String> aux = new ArrayList <String> ();
				for (Element img : imagenes)
				{
					aux.add(img.getText());
				}
				juego.setImagenes(aux);
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
