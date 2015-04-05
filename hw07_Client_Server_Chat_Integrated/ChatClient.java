import java.net.*;
import java.util.Date;
import java.util.Scanner;
import java.io.*;

public class ChatClient
{
	private String host;
	private int port;
	private Socket server;

	private String message;
	private String name;
	private String otherName;

	private Scanner in;
	private PrintWriter out;

	public ChatClient (String name, String host, int port) 
	{
		this.name = name;
		this.port = port;
		this.host = host;
	
		connect();
	}

	public void connect()
	{
		try
		{
			server = new Socket(host, port);
			System.out.println("[System] Connecting to "+ host +" at port " + port + " ...");
		}
		catch (IOException e)
		{
			System.out.println("[Error] Connecting error!");
			e.printStackTrace();
		}

		System.out.println("[System] Connection established.");
	}

	public void disconnect()
	{
		try
		{
			server.close();
		}
		catch (IOException e)
		{
			System.out.println("[Error] Closing error!");
			e.printStackTrace();
		}
	
		server = null;
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
