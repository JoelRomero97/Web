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
@WebServlet("/Delete")
public class Delete extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private ArrayList <User> usuarios;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		//Inicializar la lista de usuarios
		this.usuarios = new ArrayList <User> ();
		//Especificar el tipo de texto que se va a enviar al cliente
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession();
		String [] users = request.getParameterValues("id");
		System.out.print("USUARIOS A BORRAR: ");
		User usuario = new User();
		this.usuarios = usuario.getUsers();
		ArrayList <User> aux = new ArrayList <User> ();
		for (int i = (users.length - 1); i >= 0 ; i --)
		{
			aux.add(this.usuarios.get(Integer.parseInt(users [i])));
			this.usuarios.remove(Integer.parseInt(users [i]));
		}
		usuario.writeUsers(this.usuarios);
		//Recuperar el nombre del usuario logueado
		String nombre = (String) session.getAttribute("nombre");
		String ruta = "http://localhost:8088/Proyecto/LoginForm.html";
		for (User u : usuarios)
		{
			if ((u.getNombre()).equals(nombre))
			{
				ruta = "http://localhost:8088/Proyecto/Select?Action=Delete";
				break;
			}
		}
		show_modal (response, ruta, aux);
	}
	
	public void show_modal(HttpServletResponse response, String ruta, ArrayList <User> aux) throws IOException
	{
		//Especificar el tipo de texto que se va a enviar al cliente
		response.setContentType("text/html;charset=UTF-8");
		//Da el canal desde el servidor hacia cliente
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel='stylesheet' href='https://www.w3schools.com/w3css/4/w3.css'>");
		out.println("<link rel='stylesheet' href='https://fonts.googleapis.com/css?family=Lobster'>");
		out.println("<link rel='stylesheet' type='text/css' href='Estilos.css'>");
		out.println("<script src='Scripts.js' type='text/javascript'></script>");
		out.println("</head>");
		out.println("<body>");
		out.println("<script>");
		//Mostrar modal al cargar la página
		out.println("window.addEventListener('load', function() {showModal(document);});</script>");
		//MODAL
		out.println("<div id='modal' class='w3-modal'>");
		out.println("<div class='w3-modal-content w3-card-4 w3-animate-zoom' id='modal_card'>");
		out.println("<header class='w3-container w3-teal'>");
		out.println("<span onclick='closeModalEdit(document,'" + ruta + "');' class='w3-button w3-display-topright w3-xlarge'>&times;</span>");
		out.println("<h3>Los usuarios fueron eliminados correctamente</h3>");
		out.println("</header>");
		out.println("<div class='w3-container'>");
		out.println("<ul class='w3-ul w3-card-4'>");
		for (int i = 0; i < aux.size(); i ++)
		{
			out.println("<li class='w3-bar'>");
			out.println("<img src='images/" + aux.get(i).getGenero() + ".png' class='w3-bar-item w3-circle w3-hide-small' id='avatar'/>");
			out.println("<div class='w3-bar-item'>");
			out.println("<span class='w3-large'>" + aux.get(i).getNombre() + "</span><br/>");
			out.println("<span>" + aux.get(i).getEmail() + "</span><br/>");
			out.println("<span>" + aux.get(i).getTipo() + "</span><br/>");
			out.println("</div>");
			out.println("</li>");
		}
		out.println("</ul>");
		out.println("</div>");
		out.println("</div>");
		out.println("</div>");
		out.println("<script>clickOutsideEdit('" + ruta + "');</script>");
		out.println("</body>");
		out.println("</html>");
	}
}
