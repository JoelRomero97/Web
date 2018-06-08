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
@WebServlet("/Edit")
public class Edit extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private ArrayList <User> usuarios;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		//Inicializar la lista de usuarios
		this.usuarios = new ArrayList <User> ();
		//Especificar el tipo de texto que se va a enviar al cliente
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession();
		this.usuarios = (ArrayList<User>) session.getAttribute("usuarios");
		int usuario = Integer.parseInt(request.getParameter("id"));
		//Guardamos a nivel de sesión el id del usuario seleccionado y la lista de usuarios
		session.setAttribute("id", String.valueOf(usuario));
		show_html (usuario, response);
	}
	
	public void show_html(int id, HttpServletResponse response) throws IOException
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
		out.println("<link rel='stylesheet' href='https://www.w3schools.com/lib/w3-colors-camo.css'/>");
		out.println("<link rel='shortcut icon' href='images/favicon.ico' type='image/x-icon'>");
		out.println("<link rel='stylesheet' href='Estilos.css' type='text/css'/>");
		out.println("<title>Select User</title>");
		out.println("</head>");
		out.println("<body>");
		//BARRA DE NAVEGACIÓN
		out.println("<div class='w3-bar w3-light-gray w3-border w3-large' id='barra'>");
		out.println("<a href='Administrator' class='w3-bar-item w3-button w3-hover-sand' id='links_barra'><i class='material-icons'>&#xe88a;</i>Home</a>");
		out.println("<a href='AddUserForm.html' class='w3-bar-item w3-button w3-hover-pale-yellow' id='links_barra'><i class='material-icons'>&#xe148;</i>Add User</a>");
		out.println("<a href='#' class='w3-bar-item w3-button w3-camo-gray w3-hover-blue-gray' id='links_barra'><i class='material-icons'>&#xe150;</i>Edit User</a>");
		out.println("<a href='Select?Action=Delete' class='w3-bar-item w3-button w3-hover-sand' id='links_barra'><i class='material-icons'>&#xe16c;</i>Delete User</a>");
		out.println("<a href='LoginForm.html' class='w3-bar-item w3-button w3-hover-pale-yellow' id='links_barra'><i class='material-icons'>&#xe879;</i>Logout</a>");
		out.println("</div>");
		//USUARIO SELECCIONADO
		out.println("<div class='w3-container w3-lobster' align='center'>");
		out.println("<br/><p class='w3-xlarge'>Selected User</p>");
		out.println("</div>");
		out.println("<div class='w3-container'>");
		out.println("<ul class='w3-ul w3-card-4'>");
		out.println("<li class='w3-bar'>");
		out.println("<img src='images/" + this.usuarios.get(id).getGenero() + ".png' class='w3-bar-item w3-circle w3-hide-small' id='avatar'/>");
		out.println("<div class='w3-bar-item'>");
		out.println("<span class='w3-large'>" + this.usuarios.get(id).getNombre() + "</span><br/>");
		out.println("<span>" + this.usuarios.get(id).getEmail() + "</span><br/>");
		out.println("<span>" + this.usuarios.get(id).getTipo() + "</span><br/>");
		out.println("</div>");
		out.println("</li>");
		out.println("</ul>");
		out.println("</div>");
		//FORM FOR EDIT USER
		out.println("<div class='w3-container w3-lobster' align='center'>");
		out.println("<br/><p class='w3-xlarge'>Edit User</p>");
		out.println("</div>");
		out.println("<form action='EditUser' method='post'>");
		out.println("<div class='form-group col-sm-10' align='center'>");
		out.println("<input type='name' class='w3-input w3-border w3-hover-light-gray w3-round-xxlarge w3-animate-input' id='campo' placeholder='Name' name='nombre'>");
		out.println("<input type='email' class='w3-input w3-border w3-hover-light-gray w3-round-xxlarge w3-animate-input' id='campo' placeholder='Email' name='email'>");
		out.println("<input type='password' class='w3-input w3-border w3-hover-light-gray w3-round-xxlarge w3-animate-input' id='campo' placeholder='Password' name='password'>");
		out.println("<p class='w3-text-dark-gray w3-large'>Type:</p>");
		out.println("<input type='radio' name='tipo' value='Administrator'><label> Administrator</label><br/>");
		out.println("<input type='radio' name='tipo' value='Professor'><label> Professor</label><br/>");
		out.println("<p class='w3-text-dark-gray w3-large'>Gender:</p>");
		out.println("<input type='radio' name='genero' value='Male'><label> Male </label><br/>");
		out.println("<input type='radio' name='genero' value='Female'><label> Female</label>");
		out.println("</div>");
		out.println("<div class='w3-text-dark-gray' align='center'>");
		out.println("<br/><button id='boton_final' type='submit' class='w3-button w3-border w3-border-dark-gray w3-hover-pale-green w3-round-xxlarge'>Finish</button>");
		out.println("</div>");
		out.println("</form>");
		out.println("</body>");
		out.println("</html>");
	}
}
