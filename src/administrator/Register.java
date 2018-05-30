package administrator;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jdom2.JDOMException;

import shared.User;

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
		//Creamos un usuario con los parámetros recibidos del formulario
		User usuario = new User (request.getParameter("nombre"), request.getParameter("email"), 
								 request.getParameter("password"), request.getParameter("tipo"), 
								 request.getParameter("genero"));
		//Se recupera la sesión
		HttpSession session = request.getSession();
		//Se guarda a nivel de sesión el username ingresado (clave - objeto)
		session.setAttribute("email", usuario.getEmail());
		RegisterUser registro = new RegisterUser (usuario);
		try
		{
			if (registro.addUser())
				System.out.println("Usuario agregado");
			else
				System.out.println("Usuario NO agregado");
		} catch (JDOMException e)
		{
			e.printStackTrace();
		}
	}

}
