import java.net.*;
import java.util.Date;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.IOException;

public class ChatSocket
{
	private String name;
	private int port;
	private String host;
	private volatile int mode; //0:Stay current mode; 1:Switch to Server mode; 2:Switch to Client mode;

	private ServerSocket server;
	private Socket otherSide;

	private String message;
	private String otherName;

	private Scanner in;
	private PrintWriter out;

	public ChatSocket (String name, int port, String host)
	{
		this.name = name;
		this.port = port;
		this.host = host;
		mode = 1;	
	}

	
	public boolean connectAsServer () throws IOException
	{
			server = new ServerSocket(port);
			otherSide = server.accept();
			setupIO();
			out.println(name);
			out.flush();
			otherName = in.nextLine();
			return isConnect();
	}

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

	public void setupIO() throws IOException
	{
		in = new Scanner(otherSide.getInputStream());
		out = new PrintWriter(otherSide.getOutputStream());
	}

	public void sendMessage (String message)
	{
		out.println(name);
		out.println(message);	
		out.flush();
	}

	public String receiveMessage ()
	{
		if(in.hasNextLine())
		{
			otherName = in.nextLine();
			//return (otherName + " " + new Date());
			return (in.nextLine());
		}
		return null;
	}


	public String getHost()
	{
		return host;
	}

	public int getPort()
	{
		return port;
	}

	public String getName()
	{
		return name;
	}

	public String getOtherName()
	{
		return otherName;
	}

	public boolean isConnect()
	{
		if(in != null && out != null)
			return true;

		return false;
	}

	public void setMode(int mode)
	{
		this.mode = mode;
	}

	public int getMode()
	{
		return mode;
	}

}
