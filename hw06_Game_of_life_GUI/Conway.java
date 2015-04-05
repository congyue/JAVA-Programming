import static java.lang.System.out;
import java.util.Arrays;

public class Conway
{	
	private int[][] matrix;
	private int row, column;
	//Create matrix with size x * y
	public boolean createWorld(int x, int y)
	{
		row = x;
		column = y;
		matrix = new int[x+2][y+2];
		return true;
	}

	//Set val to point (x,y)
	public boolean setValue (int x, int y, int val)
	{
		matrix[x+1][y+1] = val;
		return true;
	}

	public int getValue (int x, int y)
	{
		return matrix[x+1][y+1];
	}

	public int getRow ()
	{
		return row;
	}

	public int getColumn()
	{
		return column;
	}

	//print out current matrix
	public void printWorld()
	{
		for (int i = 1; i < matrix.length - 1; i++)
		{
			for (int j = 1; j < matrix[i].length - 1; j++)
				out.print(matrix[i][j] == 0 ? "-" : "*");
			out.println();
		}
	}
	
	//calculate next status and update
	public void nextStatus()
	{
		int count = 0;
		
		//create local matrix copy for calculating
		int[][] matrixCopy;
		matrixCopy = new int[matrix.length][];
		for (int i = 0; i < matrix.length; i ++)
			matrixCopy[i] = Arrays.copyOf(matrix[i], matrix[i].length);

		//Analyze the matrix copy and update original matrix
		for (int i = 1; i < matrixCopy.length - 1 ; i++)
		{
			for (int j = 1; j < matrixCopy[i].length - 1; j++)
			{
				//count = sum of surrounding points
				count = matrixCopy[i-1][j-1] +
					matrixCopy[i-1][j] +
					matrixCopy[i-1][j+1] +
					matrixCopy[i][j-1] +
					matrixCopy[i][j+1] +
					matrixCopy[i+1][j-1] +
					matrixCopy[i+1][j] +
					matrixCopy[i+1][j+1];

				//set point (i,j) accordingly
				if (matrix[i][j]==1)
					setValue (i-1,j-1,count==2 || count == 3 ? 1 : 0);
				else
					setValue (i-1,j-1,count==3 ? 1 : 0);
			}
		}	
	}
}
