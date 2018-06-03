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
@WebServlet("/EditUser")
public class EditUser extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private ArrayList <User> usuarios;
	private User usuario;

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
		String id_usuario = (String) session.getAttribute("id");
		int id = Integer.parseInt(id_usuario);
		this.usuario = new User();
		System.out.println("Id formulario: '" + id + "'");
		System.out.println("Tipo formulario: '" + request.getParameter("tipo") + "'");
		System.out.println("Nombre formulario: '" + request.getParameter("nombre") + "'");
		System.out.println("Email formulario: '" + request.getParameter("email") + "'");
		System.out.println("Password formulario: '" + request.getParameter("password") + "'");
		System.out.println("Genero formulario: '" + request.getParameter("genero") + "'");
		if (request.getParameter("nombre").equals(""))
			this.usuario.setNombre(this.usuarios.get(id).getNombre());
		else
			this.usuario.setNombre(request.getParameter("nombre"));
		if (request.getParameter("email").equals(""))
			this.usuario.setNombre(this.usuarios.get(id).getEmail());
		else
			this.usuario.setEmail(request.getParameter("email"));
		if (request.getParameter("password").equals(""))
			this.usuario.setNombre(this.usuarios.get(id).getPassword());
		else
			this.usuario.setPassword(request.getParameter("password"));
		if (request.getParameter("tipo") != null)
			this.usuario.setTipo(request.getParameter("tipo"));
		else
			this.usuario.setNombre(this.usuarios.get(id).getTipo());
		if (request.getParameter("genero") != null)
			this.usuario.setGenero(request.getParameter("genero"));
		else
			this.usuario.setNombre(this.usuarios.get(id).getGenero());
		this.usuario.setId(id);
		this.usuarios.set(id, this.usuario);
		show_modal (response);
	}
	
	public void show_modal(HttpServletResponse response) throws IOException
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
		out.println("<span onclick='closeModalEdit(document);' class='w3-button w3-display-topright w3-xlarge'>&times;</span>");
		out.println("<h3>El usuario fue modificado correctamente</h3>");
		out.println("</header>");
		out.println("<div class='w3-container'>");
		out.println("<div class='w3-center'>");
		out.println("<img src='images/" + this.usuario.getGenero() + ".png' class='w3-bar-item w3-circle w3-hide-small' id='modal_avatar'/>");
		out.println("</div>");
		out.println("<h4 class='w3-text-teal'><b>Nombre</b></h4>" + this.usuario.getNombre());
		out.println("<h4 class='w3-text-teal'><b>Email</b></h4>" + this.usuario.getEmail());
		out.println("<h4 class='w3-text-teal'><b>Type</b></h4>" + this.usuario.getTipo());
		out.println("</div><br/>");
		out.println("</div>");
		out.println("</div>");
		out.println("<script>clickOutsideEdit();</script>");
		out.println("</body>");
		out.println("</html>");
	}
}
