package professor;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
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
	private File file;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		//Especificar el tipo de texto que se va a enviar al cliente
		response.setContentType("text/html;charset=UTF-8");
		//Da el canal desde el servidor hacia cliente
		PrintWriter out = response.getWriter();
		ServletContext context = request.getServletContext();
		//Recuperamos los parámetros del formulario
		ArrayList <String> imagenes = new ArrayList <String> ();
		String audio_correcto = "";
		String audio_incorrecto = "";
		String name = request.getParameter("name");
		String rutaAudios = context.getRealPath("/") + "audios\\";
		String rutaImagenes = context.getRealPath("/") + "images\\";
		HttpSession session = request.getSession();
		if (!ServletFileUpload.isMultipartContent(request))
			System.out.println("ERROR");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(4 * 1024);
		factory.setRepository(new File(rutaAudios));
		DiskFileItemFactory factoryImg = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(400 * 2048);
		try
		{
			List items = upload.parseRequest(request);
			Iterator i = items.iterator();
			int aux = 0;
			//Escribir audios
			while (i.hasNext())
			{
				FileItem item = (FileItem) i.next();
				if (!item.isFormField())
				{
					String fieldName = item.getFieldName();
					String fileName = item.getName();
					String contentType = item.getContentType();
					long size = item.getSize();
					String [] tipoArchivo = fileName.split("[.]");
					if (tipoArchivo[1].equals("mp3") || tipoArchivo[1].equals("wav"))
					{
						if (fileName.lastIndexOf("\\") >= 0)
						{
							this.file = new File (rutaAudios + fileName.substring(fileName.lastIndexOf("\\")));
							if (aux == 0)
							{
								audio_correcto = fileName;
								aux = aux + 1;
							}else if (aux == 1)
								audio_incorrecto = fileName;
						}else
						{
							this.file = new File (rutaAudios + fileName.substring(fileName.lastIndexOf("\\") + 1));
							if (aux == 0)
							{
								audio_correcto = fileName;
								aux = aux + 1;
							}else if (aux == 1)
								audio_incorrecto = fileName;
						}
						item.write(this.file);
					}else if (tipoArchivo[1].equals("jpg") || tipoArchivo[1].equals("png"))
					{
						if (fileName.lastIndexOf("\\") >= 0)
							this.file = new File (rutaImagenes + fileName.substring(fileName.lastIndexOf("\\")));
						else
							this.file = new File (rutaImagenes + fileName.substring(fileName.lastIndexOf("\\") + 1));
						imagenes.add(fileName);
						item.write(this.file);
					}
				}
			}
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		this.juego = new Game (name, (String) session.getAttribute("nombre"), context.getRealPath("/") + "Juegos.xml");
		RegisterGame registro = new RegisterGame (this.juego, context.getRealPath("/") + "Juegos.xml");
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
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
		out.println("<p class='w3-xxlarge'>" + this.juego.getNombre() + "</p>");
		out.println("</div>");
		out.println("<div class='form-group col-sm-10' align='center'>");
		out.println("<p class='w3-text-dark-gray w3-large'>Selected audios:</p>");
		out.println("<audio controls src='audios/" + audio_correcto + "'><br>");
		out.println("<audio controls src='audios/" + audio_incorrecto + "'><br>");
		out.println("<p class='w3-text-dark-gray w3-large'>Selected images:</p>");
		out.println("<div class='flex_container'>");
		for (int i = 0; i < imagenes.size(); i ++)
		out.println("<img src='images/" + imagenes.get(i) + "' draggable='true' ondragstart='drag(event);' id='drag' width='100' height='100'>");
		out.println("</div><br>");
		out.println("<div class='flex_container'>");
		for (int i = 0; i < imagenes.size(); i ++)
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
