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
	private String audio_correcto;
	private String audio_incorrecto;
	private ArrayList <String> imagenes;
	private String ruta;
	
	/*
	 * Constructor utilizado para crear un juego vacío
	 */
	public Game (String ruta)
	{
		this.imagenes = new ArrayList <String> ();
		this.setRuta(ruta);
	}
	
	/*
	 * Constructor utilizado para crear juego (se manda nombre y creador)
	 */
	public Game (String nombre, String creador, String audio_correcto, String audio_incorrecto, ArrayList <String> imagenes, String ruta)
	{
		this.imagenes = new ArrayList <String> ();
		this.setRuta(ruta);
		this.setNombre(nombre);
		this.setCreador(creador);
		this.setAudio_correcto(audio_correcto);
		this.setAudio_incorrecto(audio_incorrecto);
		this.setImagenes(imagenes);
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
				Game juego = new Game (this.ruta);
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
				RegisterGame r = new RegisterGame (this.ruta);
				raiz.addContent(r.createGame(juego));
			}
			dtd = new DocType (raiz.getName());
			dtd.setInternalSubset("<!ELEMENT JUEGOS (juego+)>\n"
					+ "<!ELEMENT juego (nombre,creador,audio_correcto,audio_incorrecto,imagen+)>\n<!ELEMENT nombre (#PCDATA)>\n<!ELEMENT creador (#PCDATA)>"
					+ "\n<!ELEMENT audio_correcto (#PCDATA)>\n<!ELEMENT audio_incorrecto (#PCDATA)>\n<!ELEMENT imagen (#PCDATA)>\n<!ATTLIST juego id CDATA #REQUIRED>\n");
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
	
	public void setRuta (String ruta)
	{
		this.ruta = ruta;
	}

	public String getAudio_correcto() {
		return audio_correcto;
	}

	public void setAudio_correcto(String audio_correcto) {
		this.audio_correcto = audio_correcto;
	}

	public String getAudio_incorrecto() {
		return audio_incorrecto;
	}

	public void setAudio_incorrecto(String audio_incorrecto) {
		this.audio_incorrecto = audio_incorrecto;
	}
	
	public void setImagenes (ArrayList <String> imagenes) {
		this.imagenes = imagenes;
	}
	
	public ArrayList <String> getImagenes () {
		return this.imagenes;
	}
}