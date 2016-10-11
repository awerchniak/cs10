package cs10proj;

import java.util.*;

/**
 * Program to calculate number of answers to the "n queens" problem
 * Written by Andy Werchniak 19 February 2016
 * @author andywerchniak
 */
public class NQueens{
	//instance variables
	private int order;
	ArrayList<int[]> solutions;
	
	/**
	 * Constructor
	 * @param n number of queens & board size
	 */
	public NQueens(int n){
		this.order = n;
		this.solutions = new ArrayList<int[]>();
	}
	
	/**
	 * bulk of the program. Recursive method using the backtracking algorithm
	 * @param partialSolution the partial solution to check
	 * @param i the order of the partial solution
	 * Lots of old print statements for bug checking
	 * Based on the pseudocode given in class
	 */
	private void backtrack(int [] partialSolution, int i){
		//printSolutions();
		int [] newSolution;
		//System.out.println("got here");
		
		if(isValid(partialSolution) && partialSolution.length==order){
			//System.out.println(arrayToString(partialSolution)+" is valid and of length " + order);
			solutions.add(partialSolution);
			//printSolutions();
		}
		
		else{
			for(int j=0; j<order;j++){
				newSolution = new int[partialSolution.length+1];
				newSolution = fillArray(partialSolution);
				newSolution[i] = j;
				
				//System.out.println(arrayToString(newSolution));
				if(isValid(newSolution)){
					//System.out.println(arrayToString(newSolution)+" is valid");
					backtrack(newSolution,i+1);
				}
				
			}
		}
	}
	
	/**
	 * private helper method to copy contents (not reference) of an array
	 * @param old array to copy
	 * @return new array
	 */
	private int[] fillArray(int [] old){
		int[] newArray = new int[old.length+1];
		
		for(int i=0;i<old.length;i++){
			newArray[i]=old[i];
		}
		
		return newArray;
	}
	
	/**
	 * a toString() method for arrays (since theirs isn't overridden)
	 * @return a string of the contents of the array
	 */
	private String arrayToString(int[] array){
		String returnString = "";
		
		for(int i=0;i<array.length;i++){
			returnString+= (" " + (array[i]+1));
		}
		
		return returnString;
	}
	
	/**
	 * private helper method to test if a queens arrangement is valid
	 * @param indices array of row indices
	 * @return whether or not it's valid
	 */
	private static Boolean isValid(int[] indices){
		Set<Integer> rows = new TreeSet<Integer>();
		
		//check if any in the same row
		for(int i=0;i<indices.length;i++){
			if (rows.contains(indices[i])){
				//System.out.println("row problem");
				return false;
			}
			else rows.add(indices[i]);
		}
			
		//check if any diagonals
		for(int i=0;i<indices.length;i++){
			for(int j=i+1;j<indices.length;j++){
				//System.out.println("i="+i+" j="+j);
				if(Math.abs((double)(indices[j]-indices[i])/(j-i)) == 1){
					//System.out.println("indices[i]= "+indices[i]+" indices[j]="+indices[j]);
					//System.out.println("(indices[j]-indices[i])/(j-i)=" +(indices[j]-indices[i])/(j-i));
					//System.out.println("diagonal problem");
					return false;
				}
			}
		}
		
		//check if any in same column
		//	this is automatic because of the way we represent the data
		
		//if none of these things, you're good!
		return true;
	}
	
	/**
	 * toString() method to print the class's answers
	 * @return string of arraylist of solutions
	 */
	public String toString(){
		String returnString = "NQueens found the following solutions:\n";
		if(!solutions.isEmpty() && (order>0 && order<9)){
			returnString+="" + solutions.size() + " solutions\n";
			for(int i = 0; i<solutions.size(); i++){
				returnString += "{";
				returnString += arrayToString(solutions.get(i));
				returnString += "}\n";
			}	
		}else if (order>8)
			return "There are " + solutions.size() + " solutions\n";
		else returnString += "The solution list is empty\n";
		
		return returnString;
	}
	
	/**
	 * super method that actually performs the task!
	 */
	public void calculate(){
		int[] partialSolution = new int[0];
		backtrack(partialSolution,0);
	}
	
	/**
	 * Driver
	 */
	public static void main(String[] args){
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		int n;
		
		
		System.out.println("Welcome to NQueens!\n\n");
		System.out.print("Enter a number of queens (-1 to quit): ");
		n = scan.nextInt();
		
		do{
			if(n>=0){
				NQueens test = new NQueens(n);
				test.calculate();
			
				System.out.println(test);
			
				System.out.print("Enter a number of queens (-1 to quit): ");
				n = scan.nextInt();
			}
		} while (n>=0);
		
		System.out.println("Bye!");
		
//		int[] test = {1,3,5,7,4,0};
//		System.out.println(isValid(test));
	}
	
	//private helper method for testing
//	private void printSolutions(){
//	
//		if(!solutions.isEmpty()){
//			String returnString = "Solutions:\n";
//			for(int i=0;i<solutions.size();i++){
//				returnString+=arrayToString(solutions.get(i));
//				returnString+="\n";
//			}
//	
//			System.out.println(returnString);
//		}
//	}
}