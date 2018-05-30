package shared;

public class User
{
	private int id;
	private String nombre;
	private String email;
	private String password;
	private String tipo;
	private String genero;
	
	/*
	 * Constructor utilizado para crear un usuario vacío
	 */
	public User ()
	{
	}
	
	/*
	 * Constructor utilizado para el login (solo se manda email y contraseña)
	 */
	public User (String email, String password)
	{
		this.setEmail(email);
		this.setPassword(password);
	}
	
	/*
	 * Constructor utilizado para eliminar/editar usuarios
	 */
	public User (String nombre, String email, String password, String tipo, String genero)
	{
		this.setNombre(nombre);
		this.setEmail(email);
		this.setPassword(password);
		this.setTipo(tipo);
		this.setGenero(genero);
	}
	
	public String getNombre ()
	{
		return this.nombre;
	}
	
	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getTipo()
	{
		return tipo;
	}

	public void setTipo(String tipo)
	{
		this.tipo = tipo;
	}

	public String getGenero()
	{
		return genero;
	}

	public void setGenero(String genero)
	{
		this.genero = genero;
	}

	public void setNombre (String nombre)
	{
		this.nombre = nombre;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}
}