package login;

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
		//Recuperamos los parámetros user y password del formulario
		String user = request.getParameter("username");
		String pass = request.getParameter("password");
		//Se recupera la sesión
		HttpSession session = request.getSession();
		//Se guarda a nivel de sesión el username ingresado (clave - objeto)
		session.setAttribute("email", user);
		try
		{
			LogUser usuario = new LogUser (user);
			if (usuario.validateUser(user, pass) == 0)
			{
				//Se guarda a nivel de sesión el nombre del usuario (clave - objeto)
				session.setAttribute("nombre", usuario.getNombre());
				//Enviamos al usuario al servlet correspondiente según su tipo
				if ((usuario.getTipo()).equals("admin"))
					response.sendRedirect("Administrator");
				else if ((usuario.getTipo()).equals("prof"))
					response.sendRedirect("Professor");
				else
					response.sendRedirect("Fail?error=tipo");
			}else if (usuario.validateUser(user, pass) == 1)
				response.sendRedirect("Fail?error=pass");
			else
				response.sendRedirect("Fail?error=user");
		}catch (JDOMException e)
		{
			e.printStackTrace();
		}
	}

}
