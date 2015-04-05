import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import javax.swing.SwingUtilities;
import java.util.Date;

public class ChatFrame extends JFrame
{

	private int width = 500;
	private int height = 400;
	private ImageIcon iconPicture = new ImageIcon("icon.png");

	final ChatSocket chatSocketObj;
	JTextArea textLog;

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

	public void drawTextArea()
	{
		textLog = new JTextArea();
		textLog.setLineWrap(true);
		JScrollPane textAreaScroll = new JScrollPane(textLog);
		
		textAreaScroll.setBounds(5,60,width-15,height-150);

		add(textAreaScroll);
	}

	public void drawControlPanel()
	{
		final JTextField textInput = new JTextField();
		final JButton buttonSend = new JButton("Send");
		final JButton buttonConnect = new JButton("Connect");

		textInput.setBounds(5,height-85,width-15,24);
		buttonSend.setBounds(width-85,height-55,75,24);
		buttonConnect.setBounds(width-180,height-55,90,24);

		buttonSend.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent event)
					{
						String message = textInput.getText();
						if (message != null)
						{
							chatSocketObj.sendMessage(message);
							textInput.setText("");
							println(chatSocketObj.getName() + "  " + new Date());
							println("  " + message);
						}
					}

				});

		buttonConnect.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent event)
					{
						if (chatSocketObj.isConnect() == false)
							chatSocketObj.setMode(2);
						println("Mode now is: " + chatSocketObj.getMode());		
					}
				});

		add(textInput);
		add(buttonSend);
		add(buttonConnect);
	}

	public void println (String msg)
	{
		textLog.append(msg);
		textLog.append("\n");
		repaint();
	}



}
