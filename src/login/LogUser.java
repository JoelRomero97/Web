package login;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class LogUser
{
	private HashMap <String, String> usuarios;
	private SAXBuilder builder;
	private Document documento;
	private File archivo;
	private Element raiz;
	private List <Element> users;
	private String nombre, user, tipo;
	
	public LogUser (String user) throws JDOMException, IOException
	{
		//Se almacena el nombre del usuario
		setUser (user);
		//Se inicializa la lista de usuarios <email, password>
		this.usuarios = new HashMap <String, String> ();
		//Se inicializa el objeto SAXBuilder
		this.builder = new SAXBuilder ();
		//Se abre el archivo XML que contiene a los usuarios
		this.archivo = new File ("C:/Users/Joel_/Desktop/ESCOM/Tecnologías para la Web/Proyecto/Usuarios.xml");
		//Obtenemos todos los usuarios con sus contraseñas
		getUsers ();
	}
	
	/*
	 * Almacena todos los usuarios con su contraseña en un HashMap
	 * Recibe: HashMap donde se almacenarán los usuarios y contraseñas
	 * Retorna: void
	 */
	public void getUsers () throws JDOMException, IOException
	{
		try
		{
			//Objeto de tipo Document para manipular archivo XML
			this.documento = builder.build(this.archivo);
			//Obtenemos el nodo raíz del documento XML
			this.raiz = this.documento.getRootElement();
			//Obtenemos la lista de todos los elementos 'usuario' del nodo raíz
			this.users = this.raiz.getChildren("usuario");
			//Recorremos la lista de usuarios obtenida
			for (Element usuario : this.users)
			{
				//Obtenemos el username del i-ésimo elemento de la lista
				String user = usuario.getChildText("username");
				//Obtenemos el password del i-ésimo elemento de la lista
				String pass = usuario.getChildText("password");
				//Almacenamos el usuario y password obtenidos del archivo XML
				this.usuarios.put(user, pass);
				//Almacenamos el tipo del usuario (administrador/profesor)
				if (user.equals(this.user))
				{
					setTipo (usuario.getAttributeValue("tipo"));
					setNombre (usuario.getChildText("nombre"));
				}
			}
		}catch (IOException e)
		{
			e.printStackTrace();
		}catch (JDOMException e)
		{
			e.printStackTrace();
		}
	}
	
	/*
	 * Valida que el login sea correcto
	 * Recibe: String usuario y String contraseña
	 * Retorna: 0 -> El usuario existe y la contraseña corresponde a ese usuario
	 * 			1 -> El usuario existe pero la contraseña es incorrecta
	 * 			2 -> El usuario no existe
	 */
	public int validateUser (String user, String password)
	{
		//Se valida que exista el usuario
		if (this.usuarios.containsKey(user))
		{
			//Se valida que la contraseña almacenada sea igual a la recibida
			if ((this.usuarios.get(user)).equals(password))
				return 0;
			return 1;
		}
		return 2;
	}

	public String getNombre()
	{
		return this.nombre;
	}

	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}

	public String getTipo()
	{
		return this.tipo;
	}

	public void setTipo(String tipo)
	{
		this.tipo = tipo;
	}

	public String getUser()
	{
		return user;
	}

	public void setUser(String user)
	{
		this.user = user;
	}
}