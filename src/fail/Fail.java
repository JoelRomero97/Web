package fail;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Fail")
public class Fail extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		//Especificar el tipo de texto que se va a enviar al cliente
		response.setContentType("text/html;charset=UTF-8");
		//Da el canal desde el servidor hacia cliente
		PrintWriter out = response.getWriter();
		//Se recupera la sesión
		HttpSession session = request.getSession();
		//Se recupera el atributo nombre
		String username = (String) session.getAttribute("email");
		//Se recupera el parámetro error del servlet 'Login'
		String kind = request.getParameter("error");
		//Para almacenar el mensaje que se dará al usuario
		String error = "";
		if (kind.equals("tipo"))
			error = "Ocurrió un error al obtener el tipo del usuario " + username;
		else if (kind.equals("pass"))
			error = "La contraseña del usuario " + username + " es incorrecta";
		else
			error = "El usuario " + username + " no está registrado";
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Fail</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>AN ERROR HAS OCCURED: </h1>");
		out.println("<h2>" + error + "</h2>");
		out.println("<a href='LoginForm.html'>Return</a>");
		out.println("</body>");
		out.println("</html>");
	}

}
