/* MachinePlayer.java */

package player;

import dict.Minimax;

/**
 *  An implementation of an automatic Network player.  Keeps track of moves
 *  made by both players.  Can select a move for itself.
 */
public class MachinePlayer extends Player {

    Board currentBoard;
    int color;
    int opponent;
    int searchDepth;
    // String myName inherited from Player.

  /**
   *  Creates a machine player with the given color.
   *  @param color the color of the player making the move.  Can be white or black.  White moves first.
   */
  public MachinePlayer(int color) {
	this(color, 3);
  }

  // Creates a machine player with the given color and search depth.  Color is
  // either 0 (black) or 1 (white).  (White has the first move.)
  public MachinePlayer(int color, int searchDepth) {
	  currentBoard = new Board();
      this.color = color;
	  opponent = (color == 1) ? 0 : 1;
      this.searchDepth = searchDepth;
  }

  // Returns a new move by "this" player.  Internally records the move (updates
  // the internal game board) as a move by "this" player.
  public Move chooseMove() {
	  Minimax mm = new Minimax(currentBoard, 3, color);
	  Move m = new Move(0, 0);
	  if (currentBoard.getPieces() < 2) {
		  if (currentBoard.getPieces() == 20) {
				while (!currentBoard.validateRules(m, color)) {
			      m = new Move((int) (Math.random() * 8),(int) (Math.random() * 8), (int) (Math.random() * 8),(int) (Math.random() * 8));
				}
			  }
			  else {
				while (!currentBoard.validateRules(m, color)) {
				  m = new Move((int) (Math.random() * 8),(int) (Math.random() * 8));
				}
			  }
			  System.out.println("true piece " + m);
			  currentBoard.placePiece(m, color);
			  System.out.println("numberOfPieces: " + currentBoard.getPieces());
	  }
	  else  {
		  m = mm.minimax(currentBoard.getBoard().getEList().front(), 3, color);
	  }
	  return m;
  } 

  // If the Move m is legal, records the move as a move by "this" player
  // (updates the internal game board) and returns true.  If the move is
  // illegal, returns false without modifying the internal state of "this"
  // player.  This method is used to help set up "Network problems" for your
  // player to solve.
  public boolean forceMove(Move m) {
		if (currentBoard.validateRules(m, color)) {
			currentBoard.placePiece(m, color);
			return true;
		}
		else  {
			return false;
		}
  }
  /**
   * If the Move m is legal, records the move as a move by the opponent
   * (updates the internal game board) and returns true.  If the move is
  	* illegal, returns false without modifying the internal state of "this"
  	* player.  This method allows your opponents to inform you of their moves.
   */
	@Override
	public boolean opponentMove(Move m) {
		if (currentBoard.validateRules(m, opponent)) {
		    System.out.println("true piece " + m);
			currentBoard.placePiece(m, opponent);
			System.out.println("numberOfPieces: " + currentBoard.getPieces());
			return true;
		}
		else  {
			return false;
		}
	}
}
