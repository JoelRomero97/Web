package administrator;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import shared.User;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Select")
public class Select extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private ArrayList <User> usuarios;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		//Inicializar la lista de usuarios
		this.usuarios = new ArrayList <User> ();
		//Especificar el tipo de texto que se va a enviar al cliente
		response.setContentType("text/html;charset=UTF-8");
		String action = request.getParameter("Action");
		String button_type = "";
		String mensaje = "";
		String boton = "";
		if (action.equals("Edit"))
		{
			button_type = "radio";
			mensaje = "Select the user to edit";
			boton = "Edit";
		}else
		{
			button_type = "checkbox";
			mensaje = "Select the user(s) to delete";
			boton = "Delete";
		}
		User aux = new User();
		this.usuarios = aux.getUsers();
		//Guardamos a nivel de sesión la lista de usuarios
		HttpSession session = request.getSession();
		session.setAttribute("usuarios", this.usuarios);
		show_html (button_type, mensaje, boton, response);
	}
	
	public void show_html(String button_type, String mensaje, String boton, HttpServletResponse response) throws IOException
	{
		//Da el canal desde el servidor hacia cliente
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset='UTF-8'>");
		out.println("<link rel='stylesheet' href='https://www.w3schools.com/w3css/4/w3.css'>");
		out.println("<link rel='stylesheet' href='https://fonts.googleapis.com/icon?family=Material+Icons'>");
		out.println("<link rel='stylesheet' href='https://fonts.googleapis.com/css?family=Lobster'>");
		out.println("<link rel='shortcut icon' href='images/favicon.ico' type='image/x-icon'>");
		out.println("<link rel='stylesheet' href='Estilos.css' type='text/css'/>");
		out.println("<title>Select User</title>");
		out.println("</head>");
		out.println("<body>");
		//BARRA DE NAVEGACIÓN
		out.println("<div class='w3-bar w3-border w3-large' id='barra'>");
		out.println("<a href='Administrator' class='w3-bar-item w3-button w3-pale-blue w3-hover-blue' id='links_barra'><i class='material-icons'>&#xe88a;</i>Home</a>");
		out.println("<a href='AddUserForm.html' class='w3-bar-item w3-button w3-pale-yellow w3-hover-amber' id='links_barra'><i class='material-icons'>&#xe148;</i>Add User</a>");
		if (boton.equals("Edit"))
		{
			out.println("<a href='#' class='w3-bar-item w3-button w3-pink w3-hover-pale-red' id='links_barra'><i class='material-icons'>&#xe150;</i>Edit User</a>");
			out.println("<a href='Select?Action=Delete' class='w3-bar-item w3-button w3-pale-green w3-hover-light-green' id='links_barra'><i class='material-icons'>&#xe16c;</i>Delete User</a>");
		}else
		{
			out.println("<a href='Select?Action=Edit' class='w3-bar-item w3-button w3-pale-red w3-hover-pink' id='links_barra'><i class='material-icons'>&#xe150;</i>Edit User</a>");
			out.println("<a href='#' class='w3-bar-item w3-button w3-light-green w3-hover-pale-green' id='links_barra'><i class='material-icons'>&#xe16c;</i>Delete User</a>");
		}
		out.println("<a href='LoginForm.html' class='w3-bar-item w3-button w3-sand w3-hover-brown' id='links_barra'><i class='material-icons'>&#xe879;</i>Logout</a>");
		out.println("</div>");
		//TABLA DE USUARIOS
		out.println("<form action='" + boton + "' method='post'>");
		out.println("<p class='w3-xxlarge' align='center'>" + mensaje + "</p>");
		out.println("<div class='w3-container'>");
		out.println("<ul class='w3-ul w3-card-4'>");
		for (int i = 0; i < this.usuarios.size(); i ++)
		{
			out.println("<li class='w3-bar'>");
			out.println("<span class='w3-bar-item w3-transparent w3-xlarge w3-right'><input class='w3-check' type='" + button_type + "' name='id' value='" + i + "'/></span>");
			out.println("<img src='images/" + this.usuarios.get(i).getGenero() + ".png' class='w3-bar-item w3-circle w3-hide-small' id='avatar'/>");
			out.println("<div class='w3-bar-item'>");
			out.println("<span class='w3-large'>" + this.usuarios.get(i).getNombre() + "</span><br/>");
			out.println("<span>" + this.usuarios.get(i).getEmail() + "</span><br/>");
			out.println("<span>" + this.usuarios.get(i).getTipo() + "</span><br/>");
			out.println("</div>");
			out.println("</li>");
		}
		out.println("</ul>");
		out.println("</div>");
		//BUTTON TO DELETE/EDIT USER(S)
		out.println("<div class='form-group col-sm-offset-2 col-sm-10' align='center'><br/>");
		out.println("<button type='submit' id='boton_final' class='w3-button w3-border w3-border-dark-gray w3-hover-sand w3-round-xxlarge'>" + boton + "</button>");
		out.println("</div>");
		out.println("</form>");
		out.println("</body>");
		out.println("</html>");
	}
}
