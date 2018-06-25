package professor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jdom2.JDOMException;

import professor.RegisterGame;
import shared.Game;

/**
 * Servlet implementation class Login
 */
@WebServlet("/CreateGame")
public class CreateGame extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private Game juego;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		//Especificar el tipo de texto que se va a enviar al cliente
		response.setContentType("text/html;charset=UTF-8");
		//Da el canal desde el servidor hacia cliente
		PrintWriter out = response.getWriter();
		//Recuperamos los parámetros del formulario
		String name = request.getParameter("name");
		HttpSession session = request.getSession();
		this.juego = new Game (name, (String) session.getAttribute("nombre"));
		RegisterGame registro = new RegisterGame (this.juego);
		try
		{
			if (registro.addGame())
			{
				System.out.println("Juego creado correctamente");
			}else
			{
				System.out.println("Ocurrió un error al crear el juego");
			}
		} catch (JDOMException e)
		{
			e.printStackTrace();
		}
		String audio_correct = request.getParameter("audio_correcto");
		String audio_incorrect = request.getParameter("audio_incorrecto");
		String audio_correcto = "", audio_incorrecto = "";
		String [] imagenes = request.getParameterValues("animal");
		if (audio_correct.equals("correct1"))
			audio_correcto = "audios/" + audio_correct + ".wav";
		else
			audio_correcto = "audios/" + audio_correct + ".mp3";
		if (audio_incorrect.equals("incorrect1"))
			audio_incorrecto = "audios/" + audio_incorrect + ".wav";
		else
			audio_incorrecto = "audios/" + audio_incorrect + ".mp3";
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset='UTF-8'>");
		out.println("<link rel='stylesheet' href='https://fonts.googleapis.com/css?family=Lobster'>");
		out.println("<link rel='stylesheet' href='https://www.w3schools.com/w3css/4/w3.css'>");
		out.println("<link rel='stylesheet' href='https://fonts.googleapis.com/icon?family=Material+Icons'>");
		out.println("<link rel='stylesheet' type='text/css' href='Estilos.css'>");
		out.println("<link rel='shortcut icon' type='image/x-icon' href='images/favicon.ico'>");
		out.println("<script src='Scripts.js' type='text/javascript'></script>");
		out.println("<title>Add Game</title>");
		out.println("</head>");
		//BARRA DE NAVEGACIÓN
		out.println("<div class='w3-bar w3-border w3-large'>");
		out.println("<a href='Professor' class='w3-bar-item w3-button w3-pale-blue w3-hover-blue' id='links_barra'><i class='material-icons'>&#xe88a;</i>Home</a>");
		out.println("<a href='#' class='w3-bar-item w3-button w3-amber w3-hover-pale-yellow' id='links_barra'><i class='material-icons'>&#xe148;</i>Add Game</a>");
		out.println("<a href='SelectGame?Action=Edit' class='w3-bar-item w3-button w3-pale-red w3-hover-pink' id='links_barra'><i class='material-icons'>&#xe150;</i>Edit Game</a>");
		out.println("<a href='SelectGame?Action=Edit' class='w3-bar-item w3-button w3-pale-green w3-hover-light-green' id='links_barra'><i class='material-icons'>&#xe16c;</i>Delete Game</a>");
		out.println("<a href='LoginForm' class='w3-bar-item w3-button w3-sand w3-hover-brown' id='links_barra'><i class='material-icons'>&#xe879;</i>Logout</a>");
		out.println("</div>");
		out.println("<div align='center'>");
		out.println("<p class='w3-xxlarge'>" + name + "</p>");
		out.println("</div>");
		out.println("<div class='form-group col-sm-10' align='center'>");
		out.println("<p class='w3-text-dark-gray w3-large'>Selected audios:</p>");
		out.println("<audio controls>");
		out.println("<source src='" + audio_correcto + "' type='audio/wav'>");
		out.println("</audio><br/>");
		out.println("<audio controls>");
		out.println("<source src='" + audio_incorrecto + "' type='audio/mp3'>");
		out.println("</audio><br/>");
		out.println("<p class='w3-text-dark-gray w3-large'>Selected images:</p>");
		out.println("<div class='flex_container'>");
		for (int i = 0; i < imagenes.length; i ++)
		out.println("<img src='images/" + imagenes[i] + ".png' draggable='true' ondragstart='drag(event);' id='drag' width='200' height='200'>");
		out.println("</div><br>");
		out.println("<div class='flex_container'>");
		for (int i = 0; i < imagenes.length; i ++)
		out.println("<div class='target' ondrop='drop(event);' ondragover='allowDrop(event);'></div>");
		out.println("</div>");
		out.println("</div>");
		out.println("<div class='w3-text-dark-gray' align='center'>");
		out.println("<br/><button id='boton_final' type='submit' class='w3-button w3-border w3-border-dark-gray w3-hover-pale-green w3-round-xxlarge'>Finish</button>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	}
}
