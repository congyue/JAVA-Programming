import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import static java.lang.System.out;

/**
 * Derived JFrame class for Conway GUI
 */
public class MyFrame extends JFrame 
{
	//print out debug info in console
	private static final boolean DEV_MODE = false;
	private Conway conway;
	final private int row;
	final private int column;
	private JButton[][] matrixButton;

	/**
	 * Constructor for MyFrame <br>
	 * Create title, reference to conway object, and control buttons
	 */
	public MyFrame(Conway conway)
	{

		
		setTitle("Conway's game of life");
		this.conway= conway;
		row = conway.getRow();
		column = conway.getColumn();
		createMatrixButton(row, column);
		createControlPanel();
	}

	/**
	 * Create matrix button part of GUI and its ActionListener
	 */
	public void createMatrixButton(int row, int column)
	{	
		final JPanel matrixPanel = new JPanel();
		matrixPanel.setLayout(new GridLayout(row,column));
		matrixButton = new JButton[row][column];
		
		class matrixButtonListener implements ActionListener
		{
			private int buttonRow, buttonColumn;
			public matrixButtonListener(int row, int column)
			{
				buttonRow = row;
				buttonColumn = column;
			}
			@Override
			public void actionPerformed(ActionEvent event)
			{
				if (matrixButton[buttonRow][buttonColumn].getText() == "*")
				{
					matrixButton[buttonRow][buttonColumn].setText("");
					conway.setValue(buttonRow,buttonColumn,0);
				}

				else
				{
					matrixButton[buttonRow][buttonColumn].setText("*");
					conway.setValue(buttonRow,buttonColumn,1);
				}

				if(DEV_MODE == true)
					{
						out.println("Current world is:");
						conway.printWorld();
					}
			}
		}
		

		for (int i=0; i < row; i++)
			for(int j=0; j < column; j++)
			{
				matrixButton[i][j] = new JButton();
				matrixButton[i][j].addActionListener(new matrixButtonListener(i,j));
				matrixPanel.add(matrixButton[i][j]);
			}
		add(matrixPanel, BorderLayout.CENTER);

	}
	
	/**
	 * helper method for ActionListener of control buttons
	 */
	public void nextEvent()
	{
		conway.nextStatus();
		for (int i=0; i < row; i++)
		{
			for(int j=0; j < column; j++)
			{
				if (conway.getValue(i,j) == 1)
					matrixButton[i][j].setText("*");
				else
					matrixButton[i][j].setText("");	
			}
		}
		repaint();
		if (DEV_MODE == true)
		{
			out.println("Current world is:");
			conway.printWorld();
		}	
	}

	/**
	 * Create the top control panel with ActionListeners
	 */
	public void createControlPanel()
	{
		final JPanel controlPanel = new JPanel();
	
		//Button "Next stage"
		JButton nextButton = new JButton("Next stage");
		nextButton.addActionListener(new ActionListener() 
				{
					//Call nextEvent() to get backend array and GUI updated
					@Override
					public void actionPerformed(ActionEvent e)
					{
						nextEvent();
					}
				});
		controlPanel.add(nextButton);

		//Button "Jump"
		final JButton jumpButton = new JButton("Jump");
		final JTextField jumpTextField = new JTextField(5);

		/**
		 * ActionLisener for Jump Button <br>
		 * iteratively call nextEvent() with Timer delay
		 */
		class jumpListener implements ActionListener
		{
			private int step = 0;
			private Timer timer;
			public jumpListener()
			{
				timer = new Timer(500, this);
			}
			
				public void actionPerformed(ActionEvent e)
					{
						String text;
						text = jumpTextField.getText();
						for (int i=0; i<text.length();i++)
							if (text.charAt(i) > '9' || text.charAt(i) < '0')
							{
								JOptionPane.showMessageDialog(null, "Illegal number of steps!",
								  "Error", JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							else
							{
								step = Integer.parseInt(text);	
							}

							if(step-->0)
							{
								jumpTextField.setText(Integer.toString(step));
								nextEvent();
								timer.start();
							}
							else
								timer.stop();
					}
		}
		jumpButton.addActionListener(new jumpListener());
		controlPanel.add(jumpButton);
		controlPanel.add(new Label("to "));
		
		controlPanel.add(jumpTextField);
		controlPanel.add(new Label("stages later"));

		//Button "Reset"
		JButton resetButton = new JButton("Reset");
		resetButton.addActionListener(new ActionListener()
				{
					//Clear all backend data and GUI
					@Override
					public void actionPerformed(ActionEvent event)
					{
						for (int i=-1; i < row+1; i++)
							for(int j=-1; j < column+1; j++)
								conway.setValue(i,j,0);	

						for (int i=0; i < row; i++)
							for(int j=0; j < column; j++)
								matrixButton[i][j].setText("");				
							
						repaint();
							if (DEV_MODE == true)
							{
								out.println("Current world is:");
								conway.printWorld();
							}	

					}
				});
		controlPanel.add(resetButton);
		add(controlPanel, BorderLayout.NORTH);
		
	}
}
