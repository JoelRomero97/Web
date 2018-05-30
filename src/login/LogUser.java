package login;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import shared.User;

public class LogUser
{
	private HashMap <String, String> usuarios;
	private SAXBuilder builder;
	private Document documento;
	private File archivo;
	private Element raiz;
	private List <Element> users;
	
	public LogUser (User usuario) throws JDOMException, IOException
	{
		//Se inicializa la lista de usuarios <email, password>
		this.usuarios = new HashMap <String, String> ();
		//Se inicializa el objeto SAXBuilder
		this.builder = new SAXBuilder ();
		//Se abre el archivo XML que contiene a los usuarios
		this.archivo = new File ("C:/Users/Joel_/Desktop/ESCOM/Tecnologías para la Web/Proyecto/Usuarios.xml");
		//Obtenemos todos los usuarios con sus contraseñas
		getUsers (usuario);
	}

	/*
	 * Almacena todos los usuarios con su contraseña en un HashMap
	 * Recibe: HashMap donde se almacenarán los usuarios y contraseñas
	 * Retorna: void
	 */
	public void getUsers (User usuario) throws JDOMException, IOException
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
			for (Element user : this.users)
			{
				//Obtenemos el email del i-ésimo elemento de la lista
				String email = user.getChildText("email");
				//Obtenemos el password del i-ésimo elemento de la lista
				String pass = user.getChildText("password");
				//Almacenamos el usuario y password obtenidos del archivo XML
				this.usuarios.put(email, pass);
				//Almacenamos el tipo del usuario (administrador/profesor)
				if (email.equals(usuario.getEmail()))
				{
					usuario.setId(Integer.parseInt(user.getAttributeValue("id")));
					usuario.setTipo (user.getAttributeValue("tipo"));
					usuario.setGenero (user.getChildText("genero"));
					usuario.setNombre(user.getChildText("nombre"));
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
	 * Recibe: String email y String contraseña
	 * Retorna: 0 -> El usuario existe y la contraseña corresponde a ese usuario
	 * 			1 -> El usuario existe pero la contraseña es incorrecta
	 * 			2 -> El usuario no existe
	 */
	public int validateUser (String email, String password)
	{
		//Se valida que exista el usuario
		if (this.usuarios.containsKey(email))
		{
			//Se valida que la contraseña almacenada sea igual a la recibida
			if ((this.usuarios.get(email)).equals(password))
				return 0;
			return 1;
		}
		return 2;
	}
}