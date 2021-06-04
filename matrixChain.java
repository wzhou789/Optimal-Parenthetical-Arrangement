import java.util.*;
import java.io.*;

public class matrixChain {
	static class matrixMult{
		public static final int length = 1000;
		static int label;
		static int count;
		static int popular;

	  // Finds optimal arrangement for multiplication and parentheses placement. 
	  static void optimizeMult(int dim[], int n){
		  int[][] min = new int[n][n]; //To record minimum cost
		  int[][] parens = new int[n][n]; //To record optimal parenthesis placement
		  int[] record = new int[length]; //To record most common iteration values
		  int counter = 0;
	      // Record max number of operations needed in the array
		  for (int i = 1; i < n; i++) {
			  for(int j = 0; j < n; j++) {
				  if(i == j) {
					  min[i][i] = 0;
					  }else {
						  min[i][j] = Integer.MAX_VALUE;
					  }	
				  }
		  }
	      // Finds cost of multiplication and optimal parenthesis placement.
		  for (int len = 2; len < n; len++){
			  for (int i = 1; i < n - len + 1; i++){
				  int j = i + len - 1;
				  for (int k = i; k < j; k++){
				// If cost of multiplication tested is less than the value in
				// the max operations, then record the cost in min. Record the
					  // iteration in parens and record.
				int q = min[i][k] + min[k+1][j] + dim[i-1] * dim[k] * dim[j];
					  if (q < min[i][j]){
						  min[i][j] = q;
						  parens[i][j] = k;
						  record[counter] = k;
						  counter++;
					  }
				  }
			  }
		  }	 
		  //First matrix is labelled as 1
		  label = 1;
		  findPopular(record);
		  printParenthesis(1, n - 1, parens);
	  }
	  
	  // Prints the optimal parenthesization
	static void printParenthesis(int i, int j, int[][] parentheses){
		// Print when there is one single matrix
		if (i == j){
			System.out.print("M" + label);		
			label++;
			return;
		}else {
		// Recursively prints parentheses around the left side of the
		// optimal arrangement
			if(parentheses[i][j] != popular) {
				System.out.print("(");
			}
			printParenthesis(i, parentheses[i][j], parentheses);
			System.out.print("*");
		// Recursively prints parentheses around the right side of the
		// optimal arrangement
			printParenthesis(parentheses[i][j] + 1, j, parentheses);
			if(parentheses[i][j] != popular) {
				System.out.print(")");
			}
					
		}
	}
			
	  // Finds the most frequently occuring element in an array and stores in popular.
	  public static void findPopular(int[] arr)
	  {
		  int popCount = 1;
		  int tempCount;
		  popular = arr[0];
		  int temp = 0;
		  for (int i = 0; i < (arr.length - 1); i++){
			  if (arr[i] != 0) {
				  temp = arr[i];
				  tempCount = 0;
				  for (int j = 1; j < arr.length; j++){
					  if (temp == arr[j])
						  tempCount++;
				  }
				  if (tempCount > popCount){
					  popular = temp;
					  popCount = tempCount;
				  }
			  }
		  }
	  }
	  
	  public static void main(String[] args) throws IOException{
		  String input = null;
		  int dimensions[] = new int[length];
		  String strDimensions[] = new String[length];
		  count = 0;
		  // Read user input until "#" is encountered
		  while(input != "#") {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				try {
					  input = br.readLine();
				}catch(IOException ioe) {
					  System.out.println("Error trying to read input!");
				}
				if("#".contentEquals(input)) {
					br.close();
					break;
				}
				// Store the matrix indexes into the dimension array
				strDimensions = input.split("\\*");
				if (count == 0) {
					dimensions[count] = Integer.parseInt(strDimensions[0]);
					count++;
					dimensions[count] = Integer.parseInt(strDimensions[1]);
					count++;
					continue;
				}
				dimensions[count] = Integer.parseInt(strDimensions[1]);
				count++;	
			}
	    optimizeMult(dimensions, count);
	  }
	}
}
