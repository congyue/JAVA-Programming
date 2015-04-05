import java.net.*;
import java.io.IOException;
import javax.swing.SwingUtilities;
import java.util.Date;

/**
 * Server side controller of Chat program
 */
public class ChatServer
{
	/**
	 * main function accept arguments to set port and username
	 * @param args[0] port number used
	 * @param args[1] username to display
	 */
	public static void main(String[] args)
	{
		String name = "Server";
		int port = 5678;
		String host = "localhost";
		String gotMessage;
		
		
		//Process  commanline arguments
		for (int i = 0; i < args.length; ++i) 
		{
			switch (i)
			{
				case 0: port = Integer.parseInt(args[0]); break;
				case 1: name = args[1]; break;
				default: break;
			}
		}

		final ChatSocket chatSocketObj = new ChatSocket(name, port, host);
		final ChatFrame mainFrame = new ChatFrame(chatSocketObj);

		/**
		 * GUI thread for mainFrame to run
		 */
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				ChatFrame newFrame = mainFrame;
				newFrame.setVisible(true);
			}
		});

		//1st loop to keep trying set up server when no connection exist
		while (true)
		{
			mainFrame.println("[System] Monitor port "+ chatSocketObj.getPort() +" for connection...");
			try
			{	//Create local server
				chatSocketObj.connectAsServer();
			}	
			catch (IOException e)
			{
				//Notify user if port is conflict
				if (e.getMessage().contains("Address already in use"))			
					mainFrame.println("[Error] Port is occupied, change the port or connect as client.");
				else
					mainFrame.println(e.getMessage());
			}	
			//If IO stream is ready, print success message and name of others
			if(chatSocketObj.isConnect())
				mainFrame.println("[System] Connection established with " + 
						chatSocketObj.getOtherName());

			//2nd loop to keep scanning incoming message when connection is valid
			while(chatSocketObj.isConnect())
			{					
				gotMessage = chatSocketObj.receiveMessage();
				//Print the message if received something
				if (gotMessage != null)
				{
					mainFrame.println(chatSocketObj.getOtherName() + "  " + new Date());
					mainFrame.println("  " + gotMessage);
					gotMessage = null;	
				}
				else
				{
					mainFrame.println("[Error] connection loss!");	
					try
					{
						chatSocketObj.closeHost();
					}
					catch(IOException exception)
					{
						mainFrame.println("[Error] Failed to reconnect!");
						System.exit(0);
					}
					break;
				}	
			}	
				
		}
	}		
}

