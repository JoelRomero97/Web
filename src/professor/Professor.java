package professor;

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
@WebServlet("/Professor")
public class Professor extends HttpServlet
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
		out.println("<link rel='stylesheet' href='https://fonts.googleapis.com/css?family=Lobster'>");
		out.println("<link rel='stylesheet' href='https://www.w3schools.com/w3css/4/w3.css'>");
		out.println("<link rel='stylesheet' href='https://fonts.googleapis.com/icon?family=Material+Icons'>");
		out.println("<link rel='stylesheet' type='text/css' href='Estilos.css'>");
		out.println("<link rel='shortcut icon' type='image/x-icon' href='images/favicon.ico'>");
		out.println("<script src='Scripts.js' type='text/javascript' language='javascript'></script>");
		out.println("<title>Professor</title>");
		out.println("</head>");
		out.println("<body>");
		//BARRA DE NAVEGACIÓN
		out.println("<div class='w3-bar w3-border w3-large'>");
		out.println("<a href='#' class='w3-bar-item w3-button w3-blue w3-hover-pale-blue' id='links_barra'><i class='material-icons'>&#xe88a;</i>Home</a>");
		out.println("<a href='AddGame.html' class='w3-bar-item w3-button w3-pale-yellow w3-hover-amber' id='links_barra'><i class='material-icons'>&#xe148;</i>Add Game</a>");
		out.println("<a href='SelectGame?Action=Edit' class='w3-bar-item w3-button w3-pale-red w3-hover-pink' id='links_barra'><i class='material-icons'>&#xe150;</i>Edit Game</a>");
		out.println("<a href='SelectGame?Action=Delete' class='w3-bar-item w3-button w3-pale-green w3-hover-light-green' id='links_barra'><i class='material-icons'>&#xe16c;</i>Delete Game</a>");
		out.println("<a href='LoginForm.html' class='w3-bar-item w3-button w3-sand w3-hover-brown' id='links_barra'><i class='material-icons'>&#xe879;</i>Logout</a>");
		out.println("</div>");
		//WELCOME
		out.println("<div align='center'>");
		out.println("<p class='w3-xxlarge'>Welcome Professor "+nombre+"!</p>");
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