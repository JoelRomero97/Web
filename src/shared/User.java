package shared;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import administrator.RegisterUser;

public class User
{
	private int id;
	private String nombre;
	private String email;
	private String password;
	private String tipo;
	private String genero;
	private String ruta = "C:/Users/Joel_/Desktop/ESCOM/Tecnolog�as para la Web/Proyecto/Usuarios.xml";
	
	/*
	 * Constructor utilizado para crear un usuario vac�o
	 */
	public User ()
	{
	}
	
	/*
	 * Constructor utilizado para el login (solo se manda email y contrase�a)
	 */
	public User (String email, String password)
	{
		this.setEmail(email);
		this.setPassword(password);
	}
	
	/*
	 * Constructor utilizado para eliminar/editar usuarios
	 */
	public User (String nombre, String email, String password, String tipo, String genero)
	{
		this.setNombre(nombre);
		this.setEmail(email);
		this.setPassword(password);
		this.setTipo(tipo);
		this.setGenero(genero);
	}
	
	public ArrayList <User> getUsers()
	{
		ArrayList <User> usuarios = new ArrayList <User> ();
		Document documento;
		SAXBuilder builder = new SAXBuilder ();
		List <Element> users;
		File archivo = new File (this.ruta);
		try
		{
			//Objeto de tipo Document para manipular archivo XML
			documento = builder.build(archivo);
			//Obtenemos el nodo ra�z del documento XML
			Element raiz = documento.getRootElement();
			//Obtenemos la lista de todos los elementos 'usuario' del nodo ra�z
			users = raiz.getChildren("usuario");
			//Recorremos la lista de usuarios obtenida
			for (Element user : users)
			{
				//Se crea un usuario vac�o
				User usuario = new User ();
				usuario.setId(Integer.parseInt(user.getAttributeValue("id")));
				usuario.setTipo(user.getAttributeValue("tipo"));
				usuario.setNombre(user.getChildText("nombre"));
				usuario.setEmail(user.getChildText("email"));
				usuario.setPassword(user.getChildText("password"));
				usuario.setGenero(user.getChildText("genero"));
				usuarios.add(usuario);
			}
		}catch (IOException e)
		{
			e.printStackTrace();
		}catch (JDOMException e)
		{
			e.printStackTrace();
		}
		return usuarios;
	}
	
	public void writeUsers (ArrayList <User> usuarios)
	{
		Document documento;
		XMLOutputter xml = new XMLOutputter ();
		FileWriter writer;
		try
		{
			//Obtenemos el nodo ra�z del documento XML
			Element raiz = new Element ("USUARIOS");
			for (User usuario : usuarios)
			{
				RegisterUser r = new RegisterUser ();
				raiz.addContent(r.createUser(usuario));
			}
			documento = new Document (raiz);
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
	
	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getTipo()
	{
		return tipo;
	}

	public void setTipo(String tipo)
	{
		this.tipo = tipo;
	}

	public String getGenero()
	{
		return genero;
	}

	public void setGenero(String genero)
	{
		this.genero = genero;
	}

	public void setNombre (String nombre)
	{
		this.nombre = nombre;
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