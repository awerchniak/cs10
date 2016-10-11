package SA12;

import java.util.Scanner;

/**
 * Plays the game of Chips.  The game starts with a pile containing an odd
 * number of chips.  The first player can take between 1 and half of the pile.
 * The players then alternate, taking between 1 and twice the number of chips
 * that the other player took on the previous move.  When no chips are left,
 * the player who took an even number of chips wins.
 * 
 * Based on earlier versions written by Scot Drysdale and THC.
 * 
 * @author Scot Drysdale
 */
public class Chips {
  public static void main(String[] args) {
    String response;  // Holds the answer to the question asked below
    Scanner input = new Scanner(System.in);  // To read input
        
    do {
      playAGame();

      System.out.print("\n\nWant to play another game (\"yes\" if you do)? ");
      response = input.nextLine();
    }
    while ((response.toLowerCase()).equals("yes"));
  }

  
  /**
   * Plays a single game of Chips
   */
  private static void playAGame() {
    Game chipsGame;           // State of the Chips game
    Player player1, player2;  // Player objects for the players
    Scanner input = new Scanner(System.in);  // To read input

    player1 = makePlayer("What is the first player's name? ", input);
    player2 = makePlayer("What is the second player's name? ", input);

    int initChips = Game.inputInitChips(); // Initial number of chips in the pile.
    System.out.println();

    chipsGame = new Game(initChips, player1, player2);

    // The main loop makes moves until the game is over.
    while (!chipsGame.isOver()) {
      Player currentPlayer = chipsGame.getPlayerToMove();
      describeState(currentPlayer, chipsGame.getOtherPlayer(), 
          chipsGame.getNumChips());

      int move = currentPlayer.getMove(chipsGame);
      
      // Report move made
      System.out.print(currentPlayer.getName() + " takes " +
          move + " chip");
      if (move != 1)
        System.out.print("s");
      System.out.println();
      
      chipsGame.makeMove(move);

      System.out.println();
    }

    // All done, so announce the winner.
    System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * *");
    printPlayerCount(chipsGame.getPlayerToMove());
    printPlayerCount(chipsGame.getOtherPlayer());
    System.out.println(chipsGame.getWinner().getName() + " wins!");
  }
  
  /**
   * Gets a player's name (after prompting) and constructs the player.
   * Computer player if name contains ComputerPlayer.COMPUTER_NAME
   * Otherwise human player.
   * @param prompt message requesting a name
   * @param input the Scanner to read from System.in
   * @return a Player with the given name (possibly a computer player).
   */
  public static Player makePlayer(String prompt, Scanner input) {
    System.out.print(prompt);
    String name = input.nextLine();
    if (name.contains(ComputerPlayer.COMPUTER_NAME))
      return new ComputerPlayer(name);
    else
      return new HumanPlayer(name);
  }

  /**
   * Describe the state of the game: how many chips remain, how many chips
   * each player has, and whose turn it is.
   * @param player1 first player
   * @param player2 second player
   * @param chips number of chips remaining
   */
  // 
  public static void describeState(Player player1, Player player2, int chips) {
    System.out.println("It is " + player1.getName() + "'s move.");

    if (chips == 1)
      System.out.println("There is 1 chip remaining.");
    else
      System.out.println("There are " + chips + " chips remaining.");

    printPlayerCount(player1);
    printPlayerCount(player2);
  }

  /*
   * Print out how many chips a player has.
   */
  private static void printPlayerCount(Player thePlayer) {
    System.out.print(thePlayer.getName() + " has " + thePlayer.getChips()
        + " chip");
    if (thePlayer.getChips() != 1)
      System.out.print("s");
    System.out.println(".");
  }
}