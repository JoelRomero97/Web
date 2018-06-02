package administrator;

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
@WebServlet("/Administrator")
public class Administrator extends HttpServlet
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
		String nombre = (String) session.getAttribute("nombre");
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset='UTF-8'>");
		out.println("<link rel='stylesheet' type='text/css' href='https://fonts.googleapis.com/css?family=Lobster'>");
		out.println("<link rel='stylesheet' type='text/css' href='https://www.w3schools.com/w3css/4/w3.css'>");
		out.println("<link rel='stylesheet' type='text/css' href='https://fonts.googleapis.com/icon?family=Material+Icons'>");
		out.println("<link rel='stylesheet' type='text/css' href='https://www.w3schools.com/lib/w3-colors-camo.css'>");
		out.println("<link rel='stylesheet' type='text/css' href='Estilos.css'>");
		out.println("<script src='Scripts.js' type='text/javascript' language='javascript'></script>");
		out.println("<title>Home</title>");
		out.println("</head>");
		out.println("<body>");
		//BARRA DE NAVEGACIÓN
		out.println("<div class='w3-bar w3-light-gray w3-border w3-large' id='barra'>");
		out.println("<a href='#' class='w3-bar-item w3-button w3-camo-gray w3-hover-blue-gray' id='links_barra'><i class='material-icons'>&#xe88a;</i>Home</a>");
		out.println("<a href='http://localhost:8088/Proyecto/AddUserForm.html' class='w3-bar-item w3-button w3-hover-sand' id='links_barra'><i class='material-icons'>&#xe148;</i>Add User</a>");
		out.println("<a href='#' class='w3-bar-item w3-button w3-hover-pale-yellow' id='links_barra'><i class='material-icons'>&#xe150;</i>Edit User</a>");
		out.println("<a href='#' class='w3-bar-item w3-button w3-hover-sand' id='links_barra'><i class='material-icons'>&#xe16c;</i>Delete User</a>");
		out.println("<a href='#' class='w3-bar-item w3-button w3-hover-pale-yellow' id='links_barra'><i class='material-icons'>&#xe879;</i>Logout</a>");
		out.println("</div>");
		//WELCOME
		out.println("<div align='center'>");
		out.println("<p class='w3-xxlarge'>Welcome Administrator" + nombre + "!</p>");
		out.println("</div>");
		//CARRUSEL DE IMÁGENES
		out.println("<div align='center'>");
		for (int i = 1; i <= 3; i ++)
		out.println("<img class='slideshow' src='images/Img" + i + ".jpeg'>");
		out.println("<script>carousel();</script>");
		out.println("</body>");
		out.println("</html>");
	}
}
