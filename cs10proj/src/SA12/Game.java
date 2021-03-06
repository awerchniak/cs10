package SA12;

import java.util.Scanner;

/**
 * Represents the state of a game of Chips.
 * Comments describing the game are in Chips.java
 * 
 * Based on earlier versions written by Scot Drysdale and THC.
 * 
 * @author Scot Drysdale
 */

public class Game {
  // Instance variables.
  private int chips; // Number of chips left
  private int maxMove; // Max number of chips in next legal move
  private Player playerToMove; // Player whose turn it is to move
  private Player otherPlayer; // Player who does not move next

  /**
   * Initialize the state of the game:
   * @param numChips the number of chips in the pile
   * @param player1 the first player
   * @param player2 the second player
   */
  public Game(int numChips, Player player1, Player player2) {
    chips = numChips;
    maxMove = chips-1;
    playerToMove = player1;
    otherPlayer = player2;
  }

  /**
   * Return current number of chips in the pile
   * @return number of chips in the pile
   */
  public int getNumChips() {
    return chips;
  }

  /**
   * Return which player's move it is
   * @return the play whose move it is
   */
  public Player getPlayerToMove() {
    return playerToMove;
  }

  /**
   * Return other player
   * @return the player not on move
   */
  public Player getOtherPlayer() {
    return otherPlayer;
  }

  /**
   * Return the maximum number of chips that the current player can take
   * @return the maximum number of chips that can be taken this move
   */
  public int getMaxMove() {
    return maxMove;
  }

  /**
   * Is the game is over?
   * @return true if the game is over
   */
  public boolean isOver() {
    return chips == 0;
  }

  /**
   * Update the state of the game based on a move.
   * Assumes that move is legal.
   * @param move the move to make
   */
  public void makeMove(int move) {
    chips -= move; // Remove the chips taken from the pile
    playerToMove.addChips(move); // and update player's pile

    // Change whose move it is. 
    Player temp = playerToMove;
    playerToMove = otherPlayer;
    otherPlayer = temp;

    // And update the max legal move for the next turn.
    maxMove = Math.min(chips, 2 * move);
  }

  /**
   * Which player won the game?
   * @return the winning player
   */
  public Player getWinner() {
    // The winner is the player with an even number of chips.
    if (playerToMove.getChips() == 0)
      return playerToMove;
    else
      return otherPlayer;
  }

  /**
   * Input a valid number of chips for the initial pile.
   * It is static so that it can be called before the game object exists.
   * @return the number of chips chosen
   */
  public static int inputInitChips() {
    final int MIN_CHIPS = 3; // Have to start with at least 3 chips
    Scanner input = new Scanner(System.in);

    System.out.print("How many chips does the initial pile contain? ");
    int chips = input.nextInt();
    
    while (chips < MIN_CHIPS) {
    	System.out.print("You have to start with at least " + MIN_CHIPS
    			+ " chips.  Choose another number: ");
      chips = input.nextInt();
    }
    
    return chips;
  }
  
  /**
   * @return a copy of the game
   */
  public Game makeCopy() {
    Game copy = new Game(chips, playerToMove.makeCopy(), 
                                otherPlayer.makeCopy());
    copy.maxMove = maxMove;
    return copy;   
  }
}