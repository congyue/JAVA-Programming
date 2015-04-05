/*** NYU-Poly
   * CS9053 Homework 1
   * Congyue Zhang
   * 0486449 cz733@nyu.edu
*/
#include<stdio.h>
#include<stdlib.h>
#include<string.h>

//initiate conway's world from input file.
char *	initMatrix (char * path, int * rows, int * columns);

//print current world.
void	printMatrix (char * array, int rows, int columns);

//set value to point: (row, column).
void	setPoint (char * array, int row, int column, int rMax, int cMax, char val);

//calculate next state of world and update.
int	nextState (char * array, int rMax, int cMax);

//calculate next state of point: (row, column).
int	pointEval (char * array, int row, int column, int rMax, int cMax);

int main(int argc, char * argv[])
{	int rMax=0, cMax=0, i=0, num = 10;	//rMax, cMax: matrix boundaries.
	char * array=0;				//array: pointer to matrix.
	char * path = "life.txt";  //Set default parameters: path = ./life.txt, number = 10.
	
	if (argc != 1)	//arguments scanner
	{
		if (!strcmp(argv[1],"-n"))	
		{	//scanner for ./conway -n num file.txt
			num = atoi(argv[2]);
			if (argv[3])
				path = argv [3];
		}
		else
		{	//scanner for ./conway file.txt -n num
			path = argv[1];
			if (argv[2] && argv[3] && (!strcmp(argv[2],"-n")))
				num = atoi(argv[3]);
		}
	}
	
	//print out parameters
	printf("\nPath = %s, Number = %d\nStart of Conway's game!\n",path,num);

	//get the pointer to the matrix described by input file.
	array=initMatrix(path, &rMax, &cMax);

	//exit if allocation failed.
	if (!array)
	{
		printf("Failed to allocate matrix!");
		exit(1);
	}

	//print original world.
	printMatrix (array, rMax, cMax);

	//generate "num" times states and print them out.
	for (i=0;i<num;i++)
	{
		nextState (array, rMax, cMax);
		printf("=========%d==========\n",i+1);
		printMatrix(array, rMax, cMax);
	}
	return 1;
}

char * initMatrix (char * path, int * rows, int * columns)
{
	char * array = 0, val = 0;	//array: pointer to matrix. val: value from file.
	int size = 0, i = 0, j = 0;	//size: allocation size for matrix. i,j: loop variables.

	FILE * inputFile;		//inputFile: pointer to input file.

	//exit if file open failed.
	if ((inputFile = fopen(path,"r")) == NULL)
	{
		printf("Error:Failed to open input file!\n");
		exit(1);
	}
	
	//read in rows and columns of the world
	fscanf(inputFile,"%d %d",rows,columns);
	//change to next line
	while (fgetc(inputFile) != '\n');

	//calculate allocation size
	size = (*rows+2)*(*columns+2)*sizeof(char);
	//allocate space for matrix and initiate to all 0
	array = (char *) malloc(size);
	array = (char *) memset(array, 0, size);
	
	for (i = 0; i < *rows; i++) //row loop
	{
		for (j = 0; j < *columns; j++) //column loop
		{
			//get 1 char from file
			val = fgetc(inputFile); 
			//set value to current point if "*"
			if (val == '*')
				setPoint(array,i,j,*rows,*columns,1);
			//break if meet "\n" or EOF
			else if (val == '\n' || val == EOF)
				break;	
		}
		//break if meet end of file
		if (val == EOF)
			break;
		//change to next row if meet \n
		else if (val == '\n')
			continue;

		//ignore until next line if row is "long"
		else
			while ((val=fgetc(inputFile)) != '\n');				
	}
	return array;
}

void printMatrix (char * array, int rows, int columns)
{
	int i=0,j=0;
	for (i=0;i<rows;i++)
	{
		for (j=0;j<columns;j++)
		{
			//evaluate point value in extended matrix
			//and print * or - to screen
			if (array[(i+1)*(columns+2)+(j+1)])
				printf("*");
			else
				printf("-");
		}

		printf("\n");
	}


}

void setPoint (char * array, int row, int column, int rMax, int cMax, char val)
{
	//convert row and coumn to extended matrix coordinate
	//then assign the value
	array[(row+1)*(cMax+2)+(column+1)]=val;

}


int	nextState (char * array, int rMax, int cMax)
{
	int i=0,j=0;
	int size=(rMax+2)*(cMax+2)*sizeof(char);  //define allocation size

	//create copy of matrix for calculation
	char * arrayCopy = (char *) malloc(size);
	if(!memcpy(arrayCopy,array,size))
		printf("Array copy failed!");

	//calculate next state for each point
	for (i=0; i<rMax; i++)
		for (j=0; j<cMax; j++)
			array[(i+1)*(cMax+2)+j+1]=pointEval(arrayCopy, i, j, rMax, cMax);
    return 1;
}

int	pointEval (char * array, int row, int column, int rMax, int cMax)
{
	int livingCells=0;
	int realRow = row+1, realColumn = column+1;
	
	//calculate livingCells in adjacent 8 points.
	livingCells = 
	(array[(realRow-1)*(cMax+2)+realColumn-1]) +
	(array[(realRow-1)*(cMax+2)+realColumn]) +
	(array[(realRow-1)*(cMax+2)+realColumn+1]) +
	(array[(realRow)*(cMax+2)+realColumn-1]) +
	(array[(realRow)*(cMax+2)+realColumn+1]) +
	(array[(realRow+1)*(cMax+2)+realColumn-1]) +
	(array[(realRow+1)*(cMax+2)+realColumn]) +
	(array[(realRow+1)*(cMax+2)+realColumn+1]);

	//decide live or dead by livingCells.
	if (array[realRow*(cMax+2)+realColumn])
		if (livingCells == 2 || livingCells == 3)
			return 1;
		else
			return 0;
	else
		if (livingCells == 3)
			return 1;
		else
			return 0;
	}
