package administrator;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletContext;
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
		//Ruta para redireccionar después de editar el usuario
		String ruta = "";
		//Inicializar la lista de usuarios
		this.usuarios = new ArrayList <User> ();
		//Especificar el tipo de texto que se va a enviar al cliente
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession();
		this.usuarios = (ArrayList<User>) session.getAttribute("usuarios");
		//Recuperar el nombre del usuario logueado
		String nombre = (String) session.getAttribute("nombre");
		String id_usuario = (String) session.getAttribute("id");
		int id = Integer.parseInt(id_usuario);
		ServletContext context = request.getServletContext();
		this.usuario = new User(context.getRealPath("/") + "Usuarios.xml");
		modify_user(request, id);
		this.usuarios.set(id, this.usuario);
		this.usuario.writeUsers(usuarios);
		if ((this.usuarios.get(id).getNombre()).equals(nombre))
			ruta = "LoginForm.html";
		else
			ruta = "Select?Action=Edit";
		show_modal (response, ruta);
	}
	
	public void show_modal(HttpServletResponse response, String ruta) throws IOException
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
		out.println("<link rel='shortcut icon' href='images/favicon.ico' type='image/x-icon'>");
		out.println("<link rel='stylesheet' type='text/css' href='Estilos.css'>");
		out.println("<script src='Scripts.js' type='text/javascript'></script>");
		out.println("</head>");
		out.println("<body>");
		//Mostrar modal al cargar la página
		out.println("<script>window.addEventListener('load', function() {showModal(document);});</script>");
		//MODAL
		out.println("<div id='modal' class='w3-modal'>");
		out.println("<div class='w3-modal-content w3-card-4 w3-animate-zoom' id='modal_card'>");
		out.println("<header class='w3-container w3-teal'>");
		out.println("<span onclick='closeModal('" + ruta + "');' class='w3-button w3-display-topright w3-xlarge'>&times;</span>");
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
		out.println("<script>clickOutside('" + ruta + "');</script>");
		out.println("</body>");
		out.println("</html>");
	}
	
	public void modify_user(HttpServletRequest request, int id)
	{
		String genero = request.getParameter("genero");
		String tipo = request.getParameter("tipo");
		String nombre = request.getParameter("nombre");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		//Se almacena el id del usuario
		this.usuario.setId(id);
		//Se almacena el genero del usuario
		genero = genero == null ? this.usuarios.get(id).getGenero() : genero;
		this.usuario.setGenero(genero);
		//Se almacena el tipo del usuario
		tipo = tipo == null ? this.usuarios.get(id).getTipo() : tipo;
		this.usuario.setTipo(tipo);
		//Se almacena el nombre del usuario
		nombre = nombre.isEmpty() ? this.usuarios.get(id).getNombre() : nombre;
		this.usuario.setNombre(nombre);
		//Se almacena el email del usuario
		email = email.isEmpty() ? this.usuarios.get(id).getEmail() : email;
		this.usuario.setEmail(email);
		//Se almacena el password del usuario
		password = password.isEmpty() ? this.usuarios.get(id).getPassword() : password;
		this.usuario.setPassword(password);
	}
}
