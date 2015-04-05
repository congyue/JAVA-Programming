import static java.lang.System.out;
import java.util.Scanner;
import java.util.Arrays;
import java.io.File;


public class ConwayDemo
{
	public static void main (String[] args)
		throws java.io.FileNotFoundException	
	{
		out.println("Start of the game!");
		//Define default file path and running time
		String path = "life.txt";
		int time = 10;

		//Handle commandline arguments
		if (args.length == 1)
			path = args[0];
		else if (args.length >= 2)
		{
			path = args[0];
			time = Integer.parseInt(args[1]);
		}

		//Create instance of Conway and initialize from file
		Conway conway = new Conway();
		readFromFile (path, conway);
		conway.printWorld(); //print the original matrix
		
		//Calculate next n times of matrix
		while (time-->0)
		{
			conway.nextStatus();
			out.println("====================");
			conway.printWorld();
		}
	}

	//Set matrix from file
	public static void readFromFile(String path, Conway conway)
		throws java.io.FileNotFoundException
	{
		Scanner scan = new Scanner (new File(path));
		
		//get matrix size from file
		int row = scan.nextInt();
		int column = scan.nextInt();
		String line = scan.nextLine(); //move to next line
		
		//Create a dummy matrix with expect size
		conway.createWorld(row, column);

			for (int i = 0; i < row; i++) //row loop
			{	
				//break when get end of file
				if (!scan.hasNextLine())
					break;

				line = scan.nextLine();
				for (int j = 0; j < column; j++) //column loop
				{
					//break if file is "short"
					if (j >= line.length())
						break;
					
					//set value if detect "*"
					else if (line.charAt(j) == "*".charAt(0))
						conway.setValue(i, j, 1);			
				}
			}
	
	}
}
