import java.net.*;
import java.util.Date;
import java.util.Scanner;
import java.io.PrintWriter;

public class ChatServer 
{
	private int port;
	private ServerSocket server;
	private Socket client;

	private String message;
	private String name;
	private String otherName;

	private Scanner in;
	private PrintWriter out;


	public ChatServer (String name, int port) throws java.io.IOException
	{
		this.name = name;
		this.port = port;
		server = new ServerSocket(port);	
		connect();		
	}

	public void connect () throws java.io.IOException
	{
		System.out.println("[System] Monitor port "+ port +" for connection...");
		client = server.accept();
		in = new Scanner(client.getInputStream());
		out = new PrintWriter(client.getOutputStream());
		otherName = in.nextLine();
		System.out.println("[System] Connection established.");
	}

	public void disconnect() throws java.io.IOException
	{
		client.close();
		client = null;
	}


	public void sendMessage (String message)
	{
		out.println(name);
		out.println(message);	
		out.flush();
	}

	public void run()
	{
		if(in.hasNext())
		{
			otherName = in.nextLine();
			System.out.println(otherName + " " + new Date());
			System.out.println("\t"+in.next());
		}
	}

	public void changeName(String newName)
	{
		name = newName;
	}

}
