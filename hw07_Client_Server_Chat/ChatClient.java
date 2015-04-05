import java.net.*;
import java.io.IOException;
import javax.swing.SwingUtilities;
import java.util.Date;

/**
 * Server side controller of Chat program
 */
public class ChatClient
{
	/**
	 * main function accept arguments to set host, port and username
	 * @param args[0] IP address of host or "localhost"
	 * @param args[1] port number used
	 * @param args[2] username to display
	 */
	public static void main(String[] args)
	{
		String name = "client";
		int port = 5678;
		String host = "localhost";
		String gotMessage;

		//Process  commanline arguments
		for (int i = 0; i < args.length; ++i) 
		{
			switch (i)
			{
				case 0: host = args[0];
				case 1: port = Integer.parseInt(args[1]); break;
				case 2: name = args[2]; break;
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

		mainFrame.println("[system] connecting to "+ chatSocketObj.getHost() +
				" at port " + chatSocketObj.getPort() + " ...");

			try
			{	//Connect to server
				chatSocketObj.connectAsClient();
			}
			catch (IOException exception)
			{
				//Notify user if host cannot be reached
				mainFrame.println("[error] failed to connect to host.");
				while(true);
			}
			
			//If IO stream is ready, print success message and name of others
			if(chatSocketObj.isConnect())
				mainFrame.println("[system] connection established with " + 
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
					mainFrame.println("[System] please check host and restart this client later!");
					break;
				}	
			}
		
	}		
}


