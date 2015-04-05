import java.net.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Vector;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Class that implement a command-line chatroom server <br>  
 * Server  : main thread that keep accept new client and create new producer thread <br>
 * Producer: every thread that monitor each client <br>
 * Consumer: one message dispatch thread created by createConsumerThread() 
 */
public class ChatRoomServer
{
	//DEBUG= true:Enable printing debug info in system console; false:Disable debug info
	private boolean DEBUG = true;
	private int port;
	private ServerSocket server;
	private Socket otherSide;

	/**
	 * Inner class used to store client infomation <br>  
	 * clientName  : username of the client <br>
	 * clientSocket: socket used to communicate to client <br>
	 * in          : input stream from client <br>
	 * out	       : output stream to client
	 */
	public class ClientInfo
	{
		private String clientName;
		private Socket clientSocket;
		private Scanner in;
		private PrintWriter out;

		public ClientInfo (String name, Socket otherSide, Scanner in, PrintWriter out)
		{
			this.clientName = name;
			this.clientSocket = otherSide;
			this.in = in;
			this.out = out;
		}

		public ClientInfo (String name)
		{
			this.clientName = name;
		}
	}

	/**
	 * Inner class used to store pending messages <br>  
	 * speaker: ClientInfo object with information of a client <br>
	 * message: String object contains messages to send
	 */
	public class MessagePending
	{
		private ClientInfo speaker;
		private String message;
		
		public MessagePending(ClientInfo speaker, String message)
		{
			this.speaker = speaker;
			this.message = message;
		}

	}

	//use thread-safe Vector to store ClientInfo objects
	private Vector<ClientInfo> clientList;
	//use LinkedBlockingQueue to store MessagePending objects
	private final BlockingQueue<MessagePending> messageQueue;

	/**
	 * Constructor for ChatRoomServer class <br>  
	 * Initialize port, clientList and messageQueue, then create ServerSocket <br>
	 */
	public ChatRoomServer (int port)
	{
		this.port = port;
		clientList = new Vector<ClientInfo>();
		messageQueue = new LinkedBlockingQueue<MessagePending>(1000);
		try
		{
			server = new ServerSocket(port);
		}
		catch(IOException e)
		{
			System.out.println("[Error] Failed to create ServerSocket!");
			System.out.println(e.getMessage());
			System.exit(0);
		}
	}

	/**
	 * Method to print current clientList to system console <br>  
	 * Only called if DEBUG is true
	 */
	public void printClientList()
	{
		synchronized(clientList)
		{
			System.out.println("Current client list:");
			for(ClientInfo c : clientList)
				System.out.println("  "+c.clientName);
		}
	}

	/**
	 * Method to print current messageQueue to system console <br>  
	 * Only called if DEBUG is true 
	 */
	public void printMessageQueue()
	{
		System.out.println("Current messageQueue:");
		for(MessagePending c : messageQueue)
			System.out.println("  "+c.speaker.clientName+": "+c.message);
	}

	/**
	 * Method to put MessagePending object to messageQueue <br>  
	 * Called by producer thread
	 */
	public void messageEnqueue(MessagePending nextMessage)
	{
		try 
		{
			messageQueue.put(nextMessage);
			if(DEBUG)
				printMessageQueue();
		}
		catch (InterruptedException e)
		{
			System.out.println("[Error] Failed to put message to queue!");
			System.out.println(e.getMessage());
			System.exit(0);
		}
	}

	/**
	 * Method to create 1 consumer thread to dispatch messages <br>  
	 * It will iterate clientList, send message to all clients except speaker itself
	 */
	public void createConsumerThread()
	{
		new Thread()
		{
			private MessagePending nextMessage;
			public void run()
			{
				while(true)
				{
					try 
					{
						nextMessage = messageQueue.take();
						if(DEBUG)
							printMessageQueue();
					}
					catch (InterruptedException e)
					{
						System.out.println("[Error] Failed to take message from queue!");
						System.out.println(e.getMessage());
						System.exit(0);
					}
					
					for (ClientInfo c : clientList)
					{
						if (c == nextMessage.speaker)
							continue;
						c.out.println(nextMessage.speaker.clientName);
						c.out.println(nextMessage.message);
						c.out.flush();
					}
				}
			}
		}.start();
	}

	/**
	 * Loop method to keep accept new client and create new thread for it
	 */
	public void run()
	{
		//only create 1 consumer thread
		createConsumerThread();

		while(true)
		{
			if (DEBUG)
				System.out.println("[Info] Wait for client at port " + port);

			//get clientSocket from waitConnection method
			final Socket clientSocket = waitConnection();

			/**
			 * Anonymous thread created for each client to monitor input stream <br>  
			 * If return value from Scanner is valid, put it to the messageQueue <br>
			 * Otherwise, send message to indicate client is disconnected
			 */
			new Thread()
			{
				private ClientInfo currentClient;
				public void run()
				{
					//Initialize IO stream for client, get client's name
					try
					{
						Scanner in = new Scanner(otherSide.getInputStream());
						PrintWriter out = new PrintWriter(otherSide.getOutputStream());
						out.println("Server");
						out.flush();
						String clientName = in.nextLine();
						currentClient = new ClientInfo (clientName, clientSocket,in,out);
					}
					catch (IOException e)
					{
						System.out.println("[Error] Failed to create IO stream!");
						System.out.println(e.getMessage());
						System.exit(0);
					}

					//add client to clientList
					synchronized(clientList)
					{
						clientList.add(currentClient);
					}

					//send system message to indicate new member comes in
					messageEnqueue(new MessagePending(new ClientInfo("[Server]"),
								currentClient.clientName+" enters the chatroom!"));
			
					if (DEBUG)
					{
						System.out.println("[Info] Connect with: " + 
								currentClient.clientName);									    printClientList();	
					}
					
					while(true)
					{
						//Monitor input stream of client
						String clientMessage = receiveMessage();
						//if return value is null, means IO stream no longer valid
						if(clientMessage == null)
							break;

						//put message to messageQueue
						messageEnqueue(new MessagePending(currentClient, clientMessage));
						if(DEBUG)
								printMessageQueue();
					}
					
					//Remove currentClient from clientList when it disconnect
					clientList.remove(currentClient);

					//Send system message to indicate member's leave
					messageEnqueue(new MessagePending(new ClientInfo("[Server]"),
								currentClient.clientName+" leaves the chatroom!"));

					if (DEBUG)
					{
						System.out.println("[Info] Disconnect with: " + 
								currentClient.clientName);
						printClientList();
					}

				}

				/**
				 * Method to update current clientName and return incomming message
				 */ 
				public String receiveMessage ()
				{
					try
					{
						//client will send it's username before message everytime
						currentClient.clientName = currentClient.in.nextLine();
						return (currentClient.in.nextLine());
					}
					catch (java.util.NoSuchElementException e)
					{
						return null;
					}
				}
			}.start();
		}

	}
	
	/**
	 * Method to establish a server and wait for client
	 * @return socket that connect to client
	 */
	public Socket waitConnection () 
	{
		try
		{
			otherSide = server.accept();
		}
		catch (IOException e)
		{
			System.out.println("[Error] Failed to make connection to client!");
			System.out.println(e.getMessage());
			System.exit(0);
		}			
		return otherSide;
	}

	/**
	 * Static main thread to handle input arguments and invoke chatRoomServer
	 */
	public static void main(String[] args) 
	{
		int port = 5678;

		//Process  commanline arguments
		if (args.length != 0)
			port = Integer.parseInt(args[0]);

		ChatRoomServer chatRoomServer = new ChatRoomServer(port);
		chatRoomServer.run();		
	}

}
