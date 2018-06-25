package login;

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
@WebServlet("/Login")
public class Login extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		//Recuperamos los par�metros user y password del formulario
		String user = request.getParameter("email");
		String pass = request.getParameter("password");
		//Se crea un usuario nuevo con los par�metros de email y contrase�a
		User usuario = new User (user, pass);
		//Se recupera la sesi�n
		HttpSession session = request.getSession();
		//Se guarda a nivel de sesi�n el email del usuario (clave - objeto)
		session.setAttribute("email", usuario.getEmail());
		try
		{
			LogUser login = new LogUser (usuario);
			if (login.validateUser(user, pass) == 0)
			{
				//Se guarda a nivel de sesi�n el nombre del usuario (clave - objeto)
				session.setAttribute("nombre", usuario.getNombre());
				//Enviamos al usuario al servlet correspondiente seg�n su tipo
				if ((usuario.getTipo()).equals("Administrator") || (usuario.getTipo()).equals("Professor") || (usuario.getTipo()).equals("Student"))
					response.sendRedirect(usuario.getTipo());
				else
					response.sendRedirect("Fail?error=tipo");
			}else if (login.validateUser(user, pass) == 1)
				response.sendRedirect("Fail?error=pass");
			else
				response.sendRedirect("Fail?error=user");
		}catch (JDOMException e)
		{
			e.printStackTrace();
		}
	}

}
