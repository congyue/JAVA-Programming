import java.net.*;
import java.util.Date;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.IOException;

/**
 * ChatSocket class is used to handle low level network protocol <br>
 * Will be called by ChatFrame or ChatServer/Client
 */ 
public class ChatSocket
{
	private String name;
	private int port;
	private String host;

	private ServerSocket server;
	private Socket otherSide;

	private String message;
	private String otherName;

	volatile private Scanner in;
	volatile private PrintWriter out;

	/**
	 * Create instance of this class and initialize variables
	 */
	public ChatSocket (String name, int port, String host)
	{
		this.name = name;
		this.port = port;
		this.host = host;
	}


	/**
	 * Method to establish a connection as a client <br>
	 * Connect to a specified host at specified port <br>
	 * Get IO stream, then exchange names with each other.
	 */
	public boolean connectAsClient () throws IOException
	{
		if(server != null)
			server.close();
		otherSide = new Socket(host, port);
		setupIO();
		out.println(name);
		out.flush();
		otherName = in.nextLine();
		return isConnect();		
	}

	/**
	 * Method to get IO stream.
	 */
	public void setupIO() throws IOException
	{
		in = new Scanner(otherSide.getInputStream());
		out = new PrintWriter(otherSide.getOutputStream());
	}

	/**
	 * Method to send message through out stream
	 * @param message The message ready to send.
	 */
	public void sendMessage (String message)
	{
		out.println(name);
		out.println(message);	
		out.flush();
	}

	/**
	 * Method to receive message through in stream
	 */
	public String receiveMessage ()
	{
		try
		{
			otherName = in.nextLine();
			return (in.nextLine());
		}

		catch(java.util.NoSuchElementException e)
		{
			return null;
		}

	}

	/**
	 * Get value of host
	 */
	public String getHost()
	{
		return host;
	}
	
	/**
	 * Get value of port
	 */
	public int getPort()
	{
		return port;
	}

	/**
	 * Get value of name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Get name of the other side
	 */
	public String getOtherName()
	{
		return otherName;
	}

	public void closeHost() throws IOException
	{
			otherSide.close();
			server.close();
	}

	/**
	 * Check if IO stream is valid to use
	 */
	public boolean isConnect()
	{
		if(in != null && out != null)
			return true;

		return false;
	}
}
