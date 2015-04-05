import java.net.*;
import java.io.IOException;
import javax.swing.SwingUtilities;
import java.util.Date;

public class Chat
{
	
	public static void main(String[] args)
	{
		String name = "Anonymous";
		int port = 5678;
		String host = "localhost";

		String gotMessage;


		final ChatSocket chatSocketObj = new ChatSocket(name, port, host);
		final ChatFrame mainFrame = new ChatFrame(chatSocketObj);

		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				ChatFrame newFrame = mainFrame;
				newFrame.setVisible(true);
			}
		});

		while(true)
		{
			while(chatSocketObj.isConnect())
			{			
				gotMessage = chatSocketObj.receiveMessage();
				System.err.println(gotMessage);
				if (gotMessage != null)
				{
					mainFrame.println(chatSocketObj.getOtherName() + "  " + new Date());
					mainFrame.println("  " + gotMessage);
					gotMessage = null;	
				}	
			
			}
			if (chatSocketObj.getMode() == 1)
			{	System.out.println("Mode now is: " + chatSocketObj.getMode());
				mainFrame.println("[System] Monitor port "+ chatSocketObj.getPort() +" for connection...");
				try
				{
					chatSocketObj.connectAsServer();
				}	
				catch (IOException e)
				{
					if (e.getMessage().contains("Address already in use"))		
					{	
						mainFrame.println("[Error] Port is occupied, change the port or connect as client.");
					}
					else
						mainFrame.println(e.getMessage());
				}	
				if(chatSocketObj.isConnect())
					mainFrame.println("[System] Connection established with " + 
										chatSocketObj.getOtherName());
				chatSocketObj.setMode(0);
			}


			else if (chatSocketObj.getMode() == 2)
			{	System.out.println("Mode now is: " + chatSocketObj.getMode());
				mainFrame.println("[System] Connecting to "+ chatSocketObj.getHost() +
								" at port " + chatSocketObj.getPort() + " ...");
				try
				{
					chatSocketObj.connectAsClient();
				}
				catch (IOException exception)
				{
					mainFrame.println("[Error] Failed to connect to host.");
				}

				if(chatSocketObj.isConnect())
					mainFrame.println("[System] Connection established with " + 
										chatSocketObj.getOtherName());	
				chatSocketObj.setMode(0);
			}
			
		}
		
		
	}
}
