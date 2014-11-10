package dict;

import list.*;
import player.*;
import graph.*;

public class Item {
    Move bestMove; 
    Board currentBoard;
    int alpha, beta; 
    int color, score;
    DList possMoves;
    public int depth; 

    public Item(Move bestMove, Board currentBoard, int color) {
    	this.bestMove = bestMove;
    	this.currentBoard = currentBoard;
    	this.color = color; 
    	this.score = 0;
    	this.alpha = Integer.MIN_VALUE;
    	this.beta = Integer.MAX_VALUE;
    	this.possMoves = new DList();
    	this.depth = 0;
    }

    public void updateAlpha(int alpha) {
      	this.alpha = alpha;
      }
      
      public void updateBeta(int beta) {
        	this.beta = beta;
        }

      public int alpha() {
      	return alpha;
      }
      
      public int beta() {
        	return beta;
        }
      
      public int score() {
    	  return this.score;
      }
      
      public void updateScore(int score) {
    	  this.score = score; 
      }

 
    public Move bestMove() {
    	return bestMove;
    }
    
    public void updateBestMove(Move m) {
    	this.bestMove = m; 
    }

    public Board board() {
		return this.currentBoard;
    }
    
    public Graph graph() {
    	return this.currentBoard.getBoard();
    }
    
    public DList possMoves() {
    	return this.possMoves;
    }
    
    public void updatePossMoves(DList d) {
    	this.possMoves = d; 
    }
    
    public int color() {
    	return this.color;
    }
    
    public String toString() {
    	String str = color + "; " + ((possMoves == null) ? "{none}" : bestMove.toString());
    	//System.out.println(currentBoard.getBoard().prettyPrint());
    	return str;
    }

}