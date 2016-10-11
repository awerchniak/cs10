package Connect4;

/**
 * Implements a computer player that chooses moves using
 *   game tree search with alpha-beta pruning
 * Based on ComputerKalahPlayerAB written by Scot Drysdale 2011
 * @author Andy Werchniak
 */
public class ComputerPlayerAB extends Player {
	private int depth;  // The amount to look ahead
	
	/**
	 * @param name player's name
	 * @param lookahead Number of moves to look ahead in search
	 */
	public ComputerPlayerAB(String name, int lookahead) {
		super(name);
		depth = lookahead;
	}

	public String toString(){
		return super.getName();
	}

	/**
	 * Chooses the best move for the computer to make
	 */
	public int getMove(Connect4State state, Connect4View view) {
		// Returns the computer play's choice using alpha-beta  search

		int move = pickMove(state, depth, -Integer.MAX_VALUE, Integer.MAX_VALUE, view).move;
		view.reportMove(move, state.getPlayerToMove().getName());

		return move;
	}

	/**
	 * Uses game tree search with alpha-beta pruning to pick player's move
	 * low and high define the current range for the best move.
	 * The current player has another move choice which will get him at least low,
	 * and his opponent has another choice that will hold his losses to high.
	 * 
	 * Modeled off of Scot Drysdale's pickMove method for his Kalah game
	 * 
	 * @param state current state of the game
	 * @param depth number of moves to look ahead in game tree search
	 * @param low a value that the player can achieve by some other move
	 * @param high a value that the opponent can force by a different line of play
	 * @param view a view to print out while testing the method
	 * @return the move chosen
	 */
	private Connect4Move pickMove (Connect4State state, int depth, int low, int high, Connect4View view) {
		//Instance variables
		Connect4Move currentMove;      // Hold current move and its value
		Connect4Move bestMove;         // Hold best move found and its value
		char [][] board = state.getBoard();
		int playerToMove = state.getPlayerNum();

		//print values to test static evaluation
		//System.out.println("high: " + high + " low: " + low);
		
		// A dummy move that will be replaced when a real move is evaluated, 
		// so the pit number is irrelevant.
		bestMove = new Connect4Move(-Integer.MAX_VALUE, 0);   

		// Run through possible moves 
		for (int col = 0; bestMove.value < high && col <= state.COLS; col++){   
			if (state.isValidMove(col))               // See if legal move
			{
				// Make a scratch copy of state
				Connect4State copy = new Connect4Game(playerToMove, state.getPlayers(), board);   
				copy.makeMove(col);             // Make the move

				//print values to test static evaluation
				//view.display(state);
				//System.out.println("Position: " + staticEvaluation(state));
				
				// Find the value of this board by evaluating if game over or looking ahead if not
				if (copy.gameIsOver())
					// Evaluate the true score, and multiply it by a huge constant so that the 
					// program will choose a sure win over a potentially larger speculative win 
					// and a possible loss over a sure loss.  
					currentMove = new Connect4Move(Integer.MAX_VALUE, col);
				else if (depth > 0)               // Player changed, so reduce search depth 
				{
					currentMove = pickMove(copy, depth - 1, -high, -low, view);
					currentMove.value = -currentMove.value;   // Good for opponent is bad for me 
					currentMove.move = col;                   // Remember move made
				}
				else    // Depth exhausted, so estimate who is winning by comparing state values
					currentMove = new Connect4Move(comparePlayerStates(state), col);

				if (currentMove.value > bestMove.value)  {  // Found a new best move?
					bestMove = currentMove;
					low = Math.max(low, bestMove.value);   // Update the low value, also
				}
			} 
		}
		return bestMove;
	}
	
	/**
	 * private helper method to compare how good a state is for each player
	 * @param state state to check
	 * @return difference in values of states
	 */
	private int comparePlayerStates(Connect4State state){
		int playerOne = staticEvaluation(state);
		Connect4State copy = new Connect4Game((state.getPlayerNum()+1)%2, state.getPlayers(), state.getBoard());
		int playerTwo = staticEvaluation(copy);
		return playerOne - playerTwo;
	}
	
	/**
	 * Static Evaulation Method
	 * Checks the board in blocks of 4. If there are none of the opponent's chips,
	 * 	then it adds weighted points corresponding to number of chips out of the 4
	 * 	that it has.
	 * @param state state to check
	 * @return value the state is evaluated to
	 */
	private int staticEvaluation(Connect4State state){
		//instance variables
		int stateValue = 0;
		int tempCount;
		char playerMark = '/';		//initialize these two to keep eclipse happy
		char opponentMark = '/';
		char [][] board = state.getBoard();
		int playerNum = state.getPlayerNum();

		//choose which mark represents the player
		switch(playerNum){
			case 0: playerMark = 'x';
				opponentMark = 'o';
				break;
			case 1: playerMark = 'o';
				opponentMark = 'x';
				break;
			default: System.err.println("invalid player num");
		}

		//search rows
		for(int i=0; i<state.ROWS; i++){
			for(int j = 0; j<=state.COLS-4; j++){
				boolean opponentBlock = false;
				if(board[i][j] == opponentMark || board[i][j+1] == opponentMark || board[i][j+2] == opponentMark || board[i][j+3] == opponentMark)
					opponentBlock = true;
				if(!opponentBlock){
					tempCount = 0;
					
					for(int k = 0; k<4; k++)
						if(board[i][j+k] == playerMark)
							tempCount++;
					
					switch(tempCount){
						case 1: stateValue++;
										break;
						case 2: stateValue += 3;
										break;
						case 3: stateValue += 8;
										break;
						case 4: stateValue += Integer.MAX_VALUE;
										break;
		}	}	}	}

		//search columns
		for(int j=0; j<state.COLS; j++){
			for(int i = 0; i<=state.ROWS-4; i++){
				boolean opponentBlock = false;
				if(board[i][j] == opponentMark || board[i+1][j] == opponentMark || board[i+2][j] == opponentMark || board[i+3][j] == opponentMark)
					opponentBlock = true;
				if(!opponentBlock){
					tempCount = 0;
					
					for(int k = 0; k<4; k++)
						if(board[i+k][j] == playerMark)
							tempCount++;
					
					switch(tempCount){
					case 1: stateValue++;
									break;
					case 2: stateValue += 3;
									break;
					case 3: stateValue += 8;
									break;
					case 4: stateValue += Integer.MAX_VALUE;
									break;
		}	}	}	}

		//search diagonal right
		for (int i =state.ROWS-4; i+3<state.ROWS; i++){
			for(int j = 0; j+3<state.COLS; j++){
	  		boolean opponentBlock = false;
	  		if(board[i][j]==opponentMark || board[i+1][j+1] == opponentMark || board[i+2][j+2] ==opponentMark || board[i+3][j+3] == opponentMark)
	  			opponentBlock = true;
	  		if(!opponentBlock){
	  			tempCount = 0;
	  			
	  			for(int k = 0; k<4; k++)
	  				if(board[i+k][j+k] == playerMark)
	  					stateValue++;
	  				
	  			switch(tempCount){
					case 1: stateValue++;
									break;
					case 2: stateValue += 3;
									break;
					case 3: stateValue += 8;
									break;
					case 4: stateValue += Integer.MAX_VALUE;
									break;
	  }	}	}	}

		//search diagonal left
		for(int i = state.ROWS-4; i+3<state.ROWS; i++){
	  	for (int j = state.COLS-1; j-3>=0; j--){
	  		boolean opponentBlock = false;
	  		if(board[i][j] == opponentMark || board[i+1][j-1] == opponentMark || board[i+2][j-2] == opponentMark || board[i+3][j-3] == opponentMark)
	  			opponentBlock = true;
	  		if(!opponentBlock){
	  			tempCount = 0;
	  				
	  			for(int k = 0; k<4; k++)
	 					if(board[i+k][j-k] == playerMark)
	 						stateValue++;
	  					
  				switch(tempCount){
  					case 1: stateValue++;
										break;
						case 2: stateValue += 3;
										break;
						case 3: stateValue += 8;
										break;
						case 4: stateValue += Integer.MAX_VALUE;
										break;
	  }	}	}	}

		return stateValue;
	}
	
	/**
	 * private class to store a move and its value as a node in the tree
	 * @author andywerchniak
	 */
	private class Connect4Move{
		public int value;       // Game value of this move
		public int move;        // Number of pit to be emptied

		public Connect4Move(int value, int move)
		{
			this.value = value;
			this.move = move;
		}
	}
}