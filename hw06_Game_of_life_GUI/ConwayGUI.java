import java.awt.*;
import javax.swing.*;
import static java.lang.System.out;
public class ConwayGUI
{
	public static void main (String[] args)
	{
		int row, column;

		//Handle commandline arguments
		if (args.length >= 2)
		{
			row = Integer.parseInt(args[0]);
			column = Integer.parseInt(args[1]);
		}
		else
		{
			row = 8;
			column = 20;
		}

		//Create instance of Conway and initialize from file
		final Conway conway = new Conway();
		conway.createWorld(row, column);

		SwingUtilities.invokeLater(new Runnable()
				{
					public void run()
					{
						MyFrame window = new MyFrame(conway);
						window.setSize(800, 400);
						window.setLocationRelativeTo(null);
						window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						window.setVisible(true);
					}
				});
	}
}
