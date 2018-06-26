package administrator;

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

import shared.User;

public class RegisterUser
{
	private List <Element> users;
	private SAXBuilder builder;
	private Document documento;
	private FileWriter writer;
	private XMLOutputter xml;
	private File archivo;
	private User usuario;
	private Element raiz;
	private DocType dtd;
	private String ruta;
	private int lastID;
	
	public RegisterUser (String ruta)
	{
		//Ruta del archivo a leer/escribir
		setRuta (ruta);
	}
	
	public RegisterUser (User usuario, String ruta)
	{
		//Ruta del archivo a leer/escribir
		setRuta (ruta);
		//Se guardan los datos del nuevo usuario
		this.usuario = usuario;
		//Se inicializa el objeto SAXBuilder
		this.builder = new SAXBuilder ();
		//Se abre el archivo XML que contiene a los usuarios
		this.archivo = new File (this.ruta);
	}
	
	/*
	 * Agrega a un usuario al archivo XML al final del mismo
	 * Recibe: void
	 * Retorna: void
	 */
	public boolean addUser () throws JDOMException, IOException
	{
		boolean res = true;
		try
		{
			this.raiz = new Element ("USUARIOS");
			//Se agrega a los usuarios que ya estaban registrados
			addPastUsers ();
			//Se actualiza el valor del ID del nuevo usuario
			this.usuario.setId(this.lastID + 1);
			//Se agrega al nuevo usuario al archivo XML
			this.raiz.addContent(createUser (this.usuario));
			//Tipo de documento
			this.dtd = new DocType (this.raiz.getName());
			this.dtd.setInternalSubset("<!ELEMENT USUARIOS (usuario+)>\n"
					+ "<!ELEMENT usuario (nombre,email,password,genero)>\n<!ELEMENT nombre (#PCDATA)>\n"
					+ "<!ELEMENT email (#PCDATA)>\n<!ELEMENT password (#PCDATA)>\n<!ELEMENT genero (#PCDATA)>\n"
					+ "<!ATTLIST usuario id CDATA #REQUIRED>\n<!ATTLIST usuario tipo (Administrator|Professor|Student) #REQUIRED>\n");
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
	 * Crea un elemento XML 'usuario' para ser añadido a algún otro elemento
	 * Recibe: User usuario con todos los parámetros listos para ser añadido
	 * Retorna: Elemento XML 'usuario'
	 */
	public Element createUser (User usuario)
	{
		Element user = new Element ("usuario");
		user.setAttribute("id", Integer.toString(usuario.getId()));
		user.setAttribute("tipo", usuario.getTipo());
		Element nombre = new Element ("nombre");
		Element email = new Element ("email");
		Element password = new Element ("password");
		Element genero = new Element ("genero");
		nombre.setText(usuario.getNombre());
		email.setText(usuario.getEmail());
		password.setText(usuario.getPassword());
		genero.setText(usuario.getGenero());
		user.addContent(nombre);
		user.addContent(email);
		user.addContent(password);
		user.addContent(genero);
		return user;
	}
	
	/*
	 * Agrega al archivo XML a todos los usuarios que ya estaban registrados
	 * Recibe: void
	 * Retorna: void
	 */
	public void addPastUsers ()
	{
		try
		{
			//Objeto de tipo Document para manipular archivo XML
			this.documento = this.builder.build(this.archivo);
			//Obtenemos el nodo raíz del documento XML
			Element raiz = this.documento.getRootElement();
			//Obtenemos la lista de todos los elementos 'usuario' del nodo raíz
			this.users = raiz.getChildren("usuario");
			//Se crea un usuario vacío
			User usuario = new User (this.ruta);
			//Recorremos la lista de usuarios obtenida
			for (Element user : this.users)
			{
				usuario.setId(Integer.parseInt(user.getAttributeValue("id")));
				usuario.setTipo(user.getAttributeValue("tipo"));
				usuario.setNombre(user.getChildText("nombre"));
				usuario.setEmail(user.getChildText("email"));
				usuario.setPassword(user.getChildText("password"));
				usuario.setGenero(user.getChildText("genero"));
				this.raiz.addContent(createUser (usuario));
				this.lastID = usuario.getId();
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
	
	public void setRuta (String ruta)
	{
		this.ruta = ruta;
	}
}
