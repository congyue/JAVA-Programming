import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import javax.swing.SwingUtilities;
import java.util.Date;

/**
 * The GUI Frame class of Chat
 */
public class ChatFrame extends JFrame
{

	private int width = 500;
	private int height = 400;
	private ImageIcon iconPicture = new ImageIcon("icon.png");

	final ChatSocket chatSocketObj;
	JTextArea textLog;
	final JTextField textInput = new JTextField();
	final JButton buttonSend = new JButton("Send");
	final JButton buttonConnect = new JButton("Connect");

	/**
	 * Constructor of ChatFrame <br>
	 * Call each sub-routines to create each part on the screen
	 */
	public ChatFrame(ChatSocket chatSocketObj)
	{
		this.chatSocketObj = chatSocketObj;
		try 
		{
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());	
	       	} 
		catch (Exception e) 
		{
		       	System.err.println("Error:" + e);   
		}
		setTitle("Chat");
		setSize(width,height);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLayout(null);
		drawUserInfo();
		drawTextArea();
		drawControlPanel();
	}

	/**
	 * Create upper left part to display user information (icon, username).
	 */
	public void drawUserInfo()
	{
		iconPicture.setImage(iconPicture.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT)); 
		JLabel icon = new JLabel(iconPicture);
		JLabel username = new JLabel(chatSocketObj.getName());
		username.setFont(new Font(username.getFont().getFontName(), Font.BOLD, 14));

		icon.setBounds(5,5,50,50);
		username.setBounds(60,25,width-60,24);
		

		add(icon);
		add(username);
	}

	/**
	 * Create text area to display dialogue of each other <br>
	 * With JScroll and text are read only.
	 */
	public void drawTextArea()
	{
		textLog = new JTextArea();
		textLog.setLineWrap(true);
		textLog.setEditable(false);
		JScrollPane textAreaScroll = new JScrollPane(textLog);
		
		textAreaScroll.setBounds(5,60,width-15,height-150);

		add(textAreaScroll);
	}

	/**
	 * Create control panel include "Send" button and input text field <br>
	 * Add action listener to Send button and key listener to textfield to respond to ENTER
	 */ 
	public void drawControlPanel()
	{
		textInput.setBounds(5,height-85,width-15,24);
		buttonSend.setBounds(width-85,height-55,75,24);

		textInput.addKeyListener(new KeyListener()
				{
					@Override
					public void keyPressed(KeyEvent event)
					{
						if (event.getKeyCode() == KeyEvent.VK_ENTER)
							send();
					}

					@Override
					public void keyReleased(KeyEvent event)
					{}

					@Override
					public void keyTyped(KeyEvent event)
					{}
				});
		buttonSend.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent event)
					{
						send ();
					}
				});

		add(textInput);
		add(buttonSend);
	}


	/**
	 * Call low level network operation from ChatSocket object to send message and echo message at local text area
	 */
	public void send ()
	{
		String message = textInput.getText();
		//if the IO stream available and textfield is not empty, then send the message.
		if (message.length() > 0 && chatSocketObj.isConnect())
		{
			chatSocketObj.sendMessage(message);
			textInput.setText("");
			println(chatSocketObj.getName() + "  " + new Date());
			println("  " + message);
		}
	}

	/**
	 * Print String arguments to text area
	 * @param msg The message ready to print at text area
	 */
	public void println (String msg)
	{
		textLog.append(msg);
		textLog.append("\n");
		repaint();
	}



}
