package administrator;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jdom2.JDOMException;

/**
 * Servlet implementation class Login
 */
@WebServlet("/ConfigUser")
public class ConfigUser extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private ArrayList <String> nombres;
	private ArrayList <String> tipos;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		this.nombres = new ArrayList <String> ();
		this.tipos = new ArrayList <String> ();
	}

}
