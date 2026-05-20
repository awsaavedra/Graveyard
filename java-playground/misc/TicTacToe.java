// package whatever; // don't place package name!
import java.util.Scanner;

class MyCode {
 public static void main(String[] args) {
     Game game = new Game();
     game.play();
 }
}


/*
Game class methods:

play(): begins a new game.
playRound(row, col): executes a single round of the game by placing a piece at the position of row, col.
printCurrentPlayersTurn(): prints who's turn it is.
decrementRounds(): reduces the number of rounds remaining.
delcareWinner(player): declares the given player as the winner.
declareTie(): declares a tie.
printInvalidMove(): declares a move invalid.
printCurrentMove(row, col): prints the move just made.
switchPlayer(): changes current player.
*/
class Game {

 private Board board = new Board();
 private char currentPlayer = 'x';
 private int numOfRound = 0;

 /*
 Start a New Game.
 Display the Empty Board.
 Announce current player is X.
 Prompt user to place a piece.
 Display the board after piece is placed.
 Announce current player is O.
 ...
 */
 public void play() {
     board.printBoard();
     while (numOfRound < 9) {
         printCurrentPlayersTurn();
         playRound();
         board.printBoard();
         if (board.checkWinCondition(currentPlayer)) {
             delcareWinner(currentPlayer);
             break;
         }else {
             decrementRounds();
         }
         switchPlayer();
     }
     if(numOfRound == 9){
         declareTie();
     }
 }


 public void playRound() {
     Scanner scan = new Scanner(System.in);  
     System.out.println("Enter Row");
     int row = scan.nextInt(); 
     System.out.println("Enter Col");
     int col = scan.nextInt();  
     if (board.canPlacePiece(row, col))
         board.placePiece(row, col, this.currentPlayer);
     else {
         playRound();
     }

 }


 public void printCurrentPlayersTurn() {
     System.out.print("Round " + this.numOfRound + ": Player " + this.currentPlayer + "'s Turn:\n");
 }


 public void decrementRounds() {
     numOfRound++;
 }

 public void delcareWinner(char player) {
     System.out.print("Player " + currentPlayer + " wins!");

 }

 public void declareTie() {
     System.out.print("Ties!");
 }


 public void printInvalidMove() {

 }


 public void printCurrentMove(int row, int col) {

 }

 public void switchPlayer() {
     if (this.currentPlayer == 'x') {
         this.currentPlayer = 'o';
     } else {
         this.currentPlayer = 'x';
     }

 }


}


/*
Board class methods:

printBoard(): prints out the current state of the board.
canPlacePiece(row, col): determines whether a given move is valid.
placePiece(row, col, player): places a piece on the board for a given player.
checkWinCondition(player): checks if the given player has won.
checkDiagonals(player): checks if the given player has 3 pieces on either diagonal.
checkRows(player): checks if the given player has 3 pieces on any of the rows.
checkColumns(player): checks if the given player has 3 pieces on any of the columns.
*/
class Board {


 private static int MAX_ROW = 3;
 private static int MAX_COL = 3;

 char[][] board;


 Board() {
     board = new char[MAX_ROW][MAX_COL];
 }


 public void printBoard() {
     for (int i = 0; i < board.length; i++) {
         for (int j = 0; j < board[i].length; j++) {
             System.out.print("["+board[i][j] + "]");
         }
         System.out.println("");
     }
 }


 public boolean canPlacePiece(int row, int col) {
     if (row < 0 || col < 0 || row >= MAX_ROW || col >= MAX_COL) {
    	 System.out.println("Invalid Input.Row and Column value should be in 0-2 range");
         return false;
     }
     if (board[row][col] != '\u0000') {
    	 System.out.println("Cell ["+row+","+col+"] already filled.Enter different value");
         return false;
     } else {
         return true;
     }
 }


 public void placePiece(int row, int col, char player) {
     this.board[row][col] = player;
 }

 public boolean checkWinCondition(char player) {
     if (checkColumns(player) || checkDiagonals(player) || checkRows(player)) {
         return true;
     } else {
         return false;
     }
 }

 private boolean checkDiagonals(char player) {
     if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
         return true;
     } else if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
         return true;
     } else {
         return false;
     }
 }

 private boolean checkRows(char player) {
     for (int i = 0; i < MAX_ROW; i++) {
         if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
             return true;
         }
     }
     return false;
 }

 private boolean checkColumns(char player) {
     for (int i = 0; i < MAX_COL; i++) {
         if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
             return true;
         }
     }
     return false;
 }

}

