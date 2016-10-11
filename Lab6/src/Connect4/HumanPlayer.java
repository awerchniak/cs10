package Connect4;

/**
 * Holds information about and interacts with a human player
 * Similar to Scot Drysdale's HumanKalahPlayer.java
 * @author Andy Werchniak
 */
public class HumanPlayer extends Player {

	/**
	 * Constructor
	 * @param name name of the human player
	 */
	public HumanPlayer (String name) {
		super(name);
	}
	
	/**
	 * Gets and returns the player's choice of move
	 * @param state current game state
	 * @param view the object that displays the game
	 * @return move chosen by the player, in the range
	 *   0 to Connect4State-1
	 */
	public int getMove(Connect4State state, Connect4View view){
			// Get a move for the user 
			return view.getUserMove(state);
	}
	
	/**
	 * @return the player's name as a String
	 */
	public String toString(){
		return super.getName();
	}
}