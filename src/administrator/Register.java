package administrator;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jdom2.JDOMException;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Register")
public class Register extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		//Recuperamos los parámetros nombre, user, password y tipo de usuario del formulario
		String name = request.getParameter("name");
		String user = request.getParameter("username");
		String pass = request.getParameter("password");
		String tipo = request.getParameter("tipo");
		//Se recupera la sesión
		HttpSession session = request.getSession();
		//Se guarda a nivel de sesión el username ingresado (clave - objeto)
		session.setAttribute("email", user);
		RegisterUser usuario = new RegisterUser (name, user, pass, tipo);
		try
		{
			if (usuario.addUser())
				System.out.println("Usuario agregado");
			else
				System.out.println("Usuario NO agregado");
		} catch (JDOMException e)
		{
			e.printStackTrace();
		}
	}

}
