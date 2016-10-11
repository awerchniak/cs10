package Connect4;

/**
 * Represents the state of the Connect4 game.  It is the model in the
 *   model-view-controller pattern.
 * Largely based off of the KalahGame.java class written by Scot Drysdale 8/2011
 * @author andywerchniak
 */
public class Connect4Game implements Connect4State {
	private char [][] board;				//Hold the game board
	private int playerToMoveNum;    // 0 or 1 for first and second player
	private Player[] players;
	private Connect4View view;
	private int counter;
	
	/**
	 * Constructor for a new game
	 * @param playerNum player's move
	 * @param players	array of players
	 * @param view		Connect4View (text or graphical)
	 */
	public Connect4Game(int playerNum, Player [] players, Connect4View view){
		char[][] initBoard = new char[][]{
		  { '/', '/', '/', '/', '/', '/', '/' },
		  { '/', '/', '/', '/', '/', '/', '/' },
		  { '/', '/', '/', '/', '/', '/', '/' },
		  { '/', '/', '/', '/', '/', '/', '/' },
		  { '/', '/', '/', '/', '/', '/', '/' },
		  { '/', '/', '/', '/', '/', '/', '/' },
		};
		
		initialize(playerNum, players, initBoard);
		this.view = view;
	}
	
	/**
	 * Constructor given a board
	 * @param playerNum player's move
	 * @param players		array of players
	 * @param initBoard	connect4 board
	 */
	public Connect4Game(int playerNum, Player [] players, char [][] initBoard)   {
		initialize(playerNum, players, initBoard);
		
		this.view = new Connect4ViewText();
	}
	
	/**
	 * Initializes the instance variables
	 * @param playerNum	player's move
	 * @param players	array of players
	 * @param initBoard	connect4 board
	 */
	private void initialize(int playerNum, Player [] players, char [][] initBoard) {
		board = new char[ROWS][COLS];

		for (int i = 0; i < ROWS; i++)
			for(int j = 0; j<COLS; j++){
				board[i][j] = initBoard[i][j];         
		}

		playerToMoveNum = playerNum;
		this.players = players;
		//pitToHighlight?
	}
	
	/**
   * Gets a 2-D array representing the board.
   * The first subscript is the row number and the second the column number.
   * The bottom of the board is row 0 and the top is row ROWS-1.
   * The left side of the board is column 0 and the right side is column COLS-1.
   * @return the board
   */
  public char [][] getBoard(){
  	return board;
  }
  
  /**
   * Gets an array holding 2 Player objects
   * @return the players
   */
  public Player [] getPlayers(){
  	return players;
  }
  
  /**
   * Gets the number of the player whose move it is
   * @return the number of the player whose move it is
   */
  public int getPlayerNum (){
  	return playerToMoveNum;
  }
  
  /**
   * Gets the Player whose turn it is to move
   * @return the Player whose turn it is to move
   */
  public Player getPlayerToMove(){
  	return players[playerToMoveNum];
  }

  /**
   * Is this move valid?
   * @param col column where we want to move
   * @return true if the move is valid
   */
  public boolean isValidMove(int col){
  	if ((col < 0 || col >COLS-1) || columnIsFull(col))
  		return false;
  	else return true;
  }
  
  /**
   * Make a move, dropping a checker in the given column
   * @param col the column to get the new checker
   */
  public void makeMove(int col){
  	char mark = '/';
  	Boolean stop = false;
  	int temp = playerToMoveNum;
  	
  	switch(playerToMoveNum){
  		case 0: mark = 'x';
  						break;
  		
  		case 1: mark = 'o';
  						break;
  		
  		default: System.err.println("Invalid playerToMoveNum value");
  	}
  	
  	if(!columnIsFull(col)){
  		//find index of row to place checker in
  		for(int i=0;i<ROWS && !stop;i++){
  			if (i==ROWS-1 || board[i+1][col] != '/'){
  				board[i][col] = mark;
  				if(!isWinner()){
  					counter++;
  					playerToMoveNum = counter%2;
  				}
  				stop = true;
  			}
  		}
  	
  	} else System.err.println("Cannot place a checker in that column!");
  }
  
 /**
  * Check if a column is full (so you can't place more chips in there)
  * @param col the column number to check
  * @return
  */
 private boolean columnIsFull(int col){
	 for(int i = 0; i<ROWS; i++){
		 if(board[i][col] == '/')
			 return false;
	 }
	 return true;
 }
  
  /**
   * Is the board full?
   * @return true if the board is full
   */
  public boolean isFull(){
  	for (int i = 0; i<ROWS; i++){
  		for (int j = 0; j<COLS; j++){
  			if (board[i][j]=='/')
  				return false;
  		}
  	}
  	
  	return true;
  }
  
  
  /**
   * Is there a winner?
   * @return true if there is a winner
   */
  public boolean isWinner(){
  	//check if there are four horizontal
  	for(int i = 0; i<ROWS; i++){
  		for(int j = 0; j<=COLS-4;j++){
  			if(board[i][j]!= '/' && board[i][j] == board[i][j+1] && board[i][j] == board[i][j+2] &&board[i][j] == board[i][j+3])
  				return true;
  		}
  	}
  	
  	//check if there are four vertical
  	for(int j = 0; j<COLS; j++){
  		for(int i = 0; i<=ROWS-4;i++){
  			if(board[i][j]!= '/' && board[i][j] == board[i+1][j] && board[i][j] == board[i+2][j] &&board[i][j] == board[i+3][j])
  				return true;
  		}
  	}
  	
  	//check if there are four diagonal
  	//1. diagonal right
  	for (int i = ROWS-4; i+3<ROWS; i++){
  		for(int j = 0; j+3<COLS; j++)
  			if(board[i][j]!= '/' && board[i][j] == board[i+1][j+1] && board[i][j] == board[i+2][j+2] && board[i][j] == board[i+3][j+3])
  				return true;
  	}
  	
  	//2. diagonal left
  	for(int i = ROWS-4; i+3<ROWS; i++){
  		for (int j = COLS-1; j-3>=0; j--)
  			if(board[i][j]!= '/' && board[i][j] == board[i+1][j-1] && board[i][j] == board[i+2][j-2] && board[i][j] == board[i+3][j-3])
  				return true;
  	}
  	
  	return false;
  }

  /**
   * Decides if the game is over
   * @return true iff the game is over
   */
  public boolean gameIsOver(){
  	//check if there is a winner
  	if (isWinner())
  		return true;
  	//check if the board is full
  	if (isFull())
  		return true;
  	
  	return false;
  }
}