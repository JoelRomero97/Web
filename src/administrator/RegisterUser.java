package administrator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class RegisterUser
{
	private String name, email, password, tipo;
	private List <Element> users;
	private SAXBuilder builder;
	private Document documento;
	private FileWriter writer;
	private XMLOutputter xml;
	private Element usuario;
	private File archivo;
	private Element raiz;
	private String ruta;
	private int lastID;
	
	public RegisterUser (String name, String email, String password, String tipo)
	{
		//Ruta del archivo a leer/escribir
		this.ruta = "C:/Users/Joel_/Desktop/ESCOM/Tecnologías para la Web/Proyecto/Usuarios.xml";
		//Se guardan los datos del nuevo usuario
		this.name = name;
		this.email = email;
		this.password = password;
		this.tipo = tipo;
		//Se inicializa el objeto SAXBuilder
		this.builder = new SAXBuilder ();
		//Se abre el archivo XML que contiene a los usuarios
		this.archivo = new File (this.ruta);
	}
	
	public boolean addUser () throws JDOMException, IOException
	{
		this.raiz = new Element ("USUARIOS");
		//this.raiz.addContent(createUser (1, "Joel Romero", "joelrg1288@gmail.com", "12345", "admin"));
		//this.raiz.addContent(createUser (2, "Yamani Alvarez", "yamalvarez23@gmail.com", "politecnico", "admin"));
		addPastUsers ();
		this.usuario = createUser (this.lastID + 1, this.name, this.email, this.password, this.tipo);
		this.raiz.addContent(this.usuario);
		this.documento = new Document(this.raiz);
		this.xml = new XMLOutputter ();
		this.writer = new FileWriter (this.ruta);
		xml.setFormat(Format.getPrettyFormat());
		this.xml.output(this.documento, this.writer);
		this.writer.flush();
		this.writer.close();
		return true;
	}
	
	public Element createUser (int id, String name, String email, String password, String tipo)
	{
		Element aux = new Element ("usuario");
		aux.setAttribute("id", Integer.toString(id));
		aux.setAttribute("tipo", tipo);
		Element nombre = new Element ("nombre");
		Element user = new Element ("username");
		Element pass = new Element ("password");
		nombre.setText(name);
		user.setText(email);
		pass.setText(password);
		aux.addContent(nombre);
		aux.addContent(user);
		aux.addContent(pass);
		return aux;
	}
	
	public void addPastUsers ()
	{
		try
		{
			//Objeto de tipo Document para manipular archivo XML
			this.documento = builder.build(this.archivo);
			//Obtenemos el nodo raíz del documento XML
			Element raiz = this.documento.getRootElement();
			//Obtenemos la lista de todos los elementos 'usuario' del nodo raíz
			this.users = raiz.getChildren("usuario");
			//Recorremos la lista de usuarios obtenida
			for (Element usuario : this.users)
			{
				//Obtenemos el username del i-ésimo elemento de la lista
				this.lastID = Integer.parseInt(usuario.getAttributeValue("id"));
				String tipo = usuario.getAttributeValue("tipo");
				String nombre = usuario.getChildText("nombre");
				String email = usuario.getChildText("username");
				String password = usuario.getChildText("password");
				this.raiz.addContent(createUser (this.lastID, nombre, email, password, tipo));
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
}
