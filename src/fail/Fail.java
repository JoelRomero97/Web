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
			error = "The password from the user " + username + " is incorrect";
		else
			error = "The user " + username + " is not registered";
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Fail</title>");
		out.println("<link rel='stylesheet' href='https://fonts.googleapis.com/css?family=Lobster'>");
		out.println("<link rel='stylesheet' href='https://www.w3schools.com/w3css/4/w3.css'>");
		out.println("<link rel='stylesheet' href='https://fonts.googleapis.com/icon?family=Material+Icons'>");
		out.println("<link rel='stylesheet' type='text/css' href='Estilos.css'>");
		out.println("<link rel='shortcut icon' type='image/x-icon' href='images/favicon.ico'>");
		out.println("</head>");
		out.println("<body>");
		out.println("<br/>");
		out.println("<br/>");
		out.println("<br/>");
		out.println("<div align='center'>");
		out.println("<h1><i class='material-icons'>error_outline</i> Oh no! An error has ocurred! <i class='material-icons'>sentiment_very_dissatisfied</i></h1>");
		out.println("<h2>" + error + "</h2>");
		out.println("<a href='LoginForm.html'><h3>Return</h3></a>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	}
}