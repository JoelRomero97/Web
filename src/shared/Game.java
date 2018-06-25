package shared;

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

import professor.RegisterGame;

public class Game
{
	private int id;
	private String nombre;
	private String creador;
	private String ruta = "C:/Users/Joel_/Desktop/ESCOM/Tecnologías para la Web/Proyecto/Juegos.xml";
	
	/*
	 * Constructor utilizado para crear un juego vacío
	 */
	public Game ()
	{
	}
	
	/*
	 * Constructor utilizado para crear juego (se manda nombre y creador)
	 */
	public Game (String nombre, String creador)
	{
		this.setNombre(nombre);
		this.setCreador(creador);
	}
	
	public ArrayList <Game> getGames()
	{
		ArrayList <Game> juegos = new ArrayList <Game> ();
		Document documento;
		SAXBuilder builder = new SAXBuilder ();
		List <Element> games;
		File archivo = new File (this.ruta);
		try
		{
			//Objeto de tipo Document para manipular archivo XML
			documento = builder.build(archivo);
			//Obtenemos el nodo raíz del documento XML
			Element raiz = documento.getRootElement();
			//Obtenemos la lista de todos los elementos 'juego' del nodo raíz
			games = raiz.getChildren("juego");
			//Recorremos la lista de juegos obtenida
			for (Element game : games)
			{
				//Se crea un juego vacío
				Game juego = new Game ();
				juego.setId(Integer.parseInt(game.getAttributeValue("id")));
				juego.setNombre(game.getChildText("nombre"));
				juego.setCreador(game.getChildText("creador"));
				juegos.add(juego);
			}
		}catch (IOException e)
		{
			e.printStackTrace();
		}catch (JDOMException e)
		{
			e.printStackTrace();
		}
		return juegos;
	}
	
	public void writeGames (ArrayList <Game> juegos)
	{
		Document documento;
		XMLOutputter xml = new XMLOutputter ();
		FileWriter writer;
		DocType dtd;
		try
		{
			//Obtenemos el nodo raíz del documento XML
			Element raiz = new Element ("JUEGOS");
			for (Game juego : juegos)
			{
				RegisterGame r = new RegisterGame ();
				raiz.addContent(r.createGame(juego));
			}
			dtd = new DocType (raiz.getName());
			dtd.setInternalSubset("<!ELEMENT JUEGOS (juego+)>\n"
					+ "<!ELEMENT juego (nombre,creador)>\n<!ELEMENT nombre (#PCDATA)>\n<!ELEMENT creador (#PCDATA)>"
					+ "<!ATTLIST juego id CDATA #REQUIRED>\n");
			//Se escribe el documento XML
			documento = new Document(raiz, dtd);
			writer = new FileWriter (this.ruta);
			xml.setFormat(Format.getPrettyFormat());
			xml.output(documento, writer);
			writer.flush();
			writer.close();
		}catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public String getNombre ()
	{
		return this.nombre;
	}

	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}

	public String getCreador()
	{
		return this.creador;
	}

	public void setCreador(String creador)
	{
		this.creador = creador;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}
}