package Connect4;

import java.util.*;

/**
 * A text implementation of the "view" part of a model-view-controller 
 *   version of a Connect4 program.
 * 
 * @author Andy Werchniak
 */
public class Connect4ViewText implements Connect4View {
	//one instance variable
  private Scanner scan;

	/**  
   * Displays the current board
   * @param state current state of the game
   */
	public void display (Connect4State state){
		char board[][] = state.getBoard();
		int row = state.ROWS;
		int col = state.COLS;
		
		for(int i = 0; i<row; i++){
			for (int j = 0; j<col; j++){
				System.out.print(board[i][j] + " ");
			}
			System.out.print("\n");
		}
	}
	
	/**
	 * Asks the user for a move
	 * The move will be in the range 0 to Connect4State.COLS-1.
	 * @param state current state of the game
	 * @return the number of the move that player chose
	 */
	public int getUserMove(Connect4State state){
		int move;

		int bound = state.COLS-1;
		
		scan = new Scanner(System.in);
		do{
			System.out.print("Enter column # to insert chip: ");
			move = scan.nextInt();
			
			if(move > bound || move < 0)
				System.out.println("Please enter a column number between 0 and " + bound);
		} while(move > bound || move <0);
		
		return move;
		
	}

	/**
	 * Reports the move that a player has made.
	 * The move should be in the range 0 to Connect4State.COLS-1.
	 * @param chosenMove the move to be reported
	 * @param name the player's name
	 */
	public void reportMove (int chosenMove, String name){
		System.out.println(name + " placed a chip into Column " + chosenMove);
	}
  
	/**
	 * Ask the user the question and return the answer as an int
	 * @param question the question to ask
	 * @return The depth the player chose
	 */
  public int getIntAnswer (String question){
  	scan = new Scanner(System.in);
  	
  	reportToUser(question);
  	return scan.nextInt();
  }
  
  /**
   * Convey a message to user
   * @param message the message to be reported
   */
  public void reportToUser(String message){
  	System.out.println(message);
  }
  
  /**
   * Ask the question and return the answer
   * @param question the question to ask
   * @return the answer to the question
   */
  public String getAnswer(String question){
  	scan = new Scanner(System.in);
  	
  	reportToUser(question);
  	return scan.nextLine();
  }
}