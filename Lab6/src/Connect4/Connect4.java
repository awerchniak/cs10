package Connect4;

/**
 * Driver for the game. Based on Scot Drysdale's Kalah.java
 * @author andywerchniak
 *
 */
public class Connect4{
	public static void main(String args[]) 
	{
		int winnerNum;                   // Hold final score in game

		// Hold the view methods.  Also whether the update should be 
		// incremental (display changes as they occur) or all at once.
		Connect4View view = new Connect4ViewText(); 

		//this is a two player game
		Player [] players = new Player[2];
		players[0] = makePlayer(view, "first");
		players[1] = makePlayer(view, "second");

		// Hold current game state
		Connect4Game state = new Connect4Game(0, players, view);  

		//output so the user knows whats going on
		view.display(state);
		System.out.println(state.getPlayerToMove() + "'s turn.");
		
		//play until the game is over
		while (!state.gameIsOver()) 
		{
			//get the user's move and make it
			int col = state.getPlayerToMove().getMove(state, view);
			state.makeMove(col);
			
			//display new state and what's happened
			view.display(state);
  		view.reportMove(col, players[(state.getPlayerNum()+1)%2].toString());
  		System.out.println(state.getPlayerToMove() + "'s turn.");
		}

		//if there's a winner, print the player. else print that it was a draw
		if(state.isWinner()){
			winnerNum = state.getPlayerNum();
			System.out.println(players[winnerNum] + " is the winner!");
		}
		else System.out.println("Game over. It is a draw.");
	}

	/** 
	 * Constructs a Connect4 player.  If the name contains "Computer" it
	 * constructs a computer player; else a human player
	 * @param view the view to use to communicate to the world
	 * @param playerMsg the player to ask for 
	 * @return
	 */
	public static Player makePlayer(Connect4View view, String playerMsg) {
		String playerName = view.getAnswer("Enter the name of the " + playerMsg + 
				" player." + "\n(Include 'Computer' in the name of a computer player) ");
		if(playerName.contains("Computer")) {
			int depth = view.getIntAnswer("How far should I look ahead? ");
			return new ComputerPlayerAB(playerName, depth);
		}
		else
			return new HumanPlayer(playerName);
	}
}