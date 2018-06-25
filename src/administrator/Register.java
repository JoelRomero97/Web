package administrator;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jdom2.JDOMException;

import shared.User;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Register")
public class Register extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private User usuario;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		//Creamos un usuario con los parámetros recibidos del formulario
		this.usuario = new User (request.getParameter("nombre"), request.getParameter("email"), 
								 request.getParameter("password"), request.getParameter("tipo"), 
								 request.getParameter("genero"));
		//Se recupera la sesión
		HttpSession session = request.getSession();
		//Se guarda a nivel de sesión el username ingresado (clave - objeto)
		session.setAttribute("email", this.usuario.getEmail());
		RegisterUser registro = new RegisterUser (this.usuario);
		String color = "";
		String mensaje = "";
		try
		{
			if (registro.addUser())
			{
				color = "teal";
				mensaje = "Usuario registrado correctamente";
			}else
			{
				color = "red";
				mensaje = "Ocurrió un error al registrar al usuario";
			}
		} catch (JDOMException e)
		{
			e.printStackTrace();
		}
		show_modal(color, mensaje, response);
	}
	
	public void show_modal(String color, String mensaje, HttpServletResponse response) throws IOException
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
		out.println("<header class='w3-container w3-" + color + "'>");
		out.println("<button type='button' onclick='closeModal('AddUserForm.html');' class='w3-button w3-display-topright w3-xlarge'>&times;</button>");
		out.println("<h3>" + mensaje + "</h3>");
		out.println("</header>");
		out.println("<div class='w3-container'>");
		out.println("<div class='w3-center'>");
		out.println("<img src='images/" + this.usuario.getGenero() + ".png' class='w3-bar-item w3-circle w3-hide-small' id='modal_avatar'/>");
		out.println("</div>");
		out.println("<h4 class='w3-text-" + color + "'><b>Nombre</b></h4>" + this.usuario.getNombre());
		out.println("<h4 class='w3-text-" + color + "'><b>Email</b></h4>" + this.usuario.getEmail());
		out.println("<h4 class='w3-text-" + color + "'><b>Type</b></h4>" + this.usuario.getTipo());
		out.println("</div><br/>");
		out.println("</div>");
		out.println("</div>");
		out.println("<script>clickOutside('AddUserForm.html');</script>");
		out.println("</body>");
		out.println("</html>");
	}
}
