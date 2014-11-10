/** 
 * Minimax is the class that controls the heuristic and alphaBetaPruning algorithms. Items of this
 * class have a "Dict" which holds all possible moves spawned from the main function "minimax". 

 * We also presume that possibleMoves (the method which spawns all possible 
 * moves) returns a dictionary (AS IT SHOULD!) :) AND takes in a board

 */

package dict;

import graph.*; 
import player.*;
import list.*;
import java.util.*;

public class Minimax {

	Dict dict = new Dict();
	Board board;
    int depth;
    int alpha = Integer.MIN_VALUE;
    int beta = Integer.MAX_VALUE;
    boolean max = true; 
    int player;
    Move bestMove;
    
    static int count = 0;
    
    public Minimax(Board board, int depth, int player) {
    	this.board = board; 
    	this.depth = depth;
    	this.player = player;
    	this.dict.insertBack(null, board, player);
    	((Item) dict.back().item()).updatePossMoves(possibleMoves(board,player));
    }
    public Minimax(Board board, int player) {
    	this(board, 3, player);
    }
    
    public Minimax(Dict newDict) {
    	this.dict = newDict;
    }
 
    private boolean isMax(int color) {
    	boolean max = true;
    	if(color != player) max = false;
    	return max;  
    }
    
 /**
  *   
  * @param depth the number of turns to search through 
  * @param color the color of the player whose turn it is
  * @return the BEST move as determined from the heuristic after searching n (depth) turns
  */
    public Move minimax() {
    	DListNode current = this.dict.front();
    	int depth = this.depth;
    	int nextPlayer = this.player; 
    	boolean running = true;
    	Move bestestMove = new Move();
    	boolean pruned = false;
        boolean updated = false;
        int minScore = 0, maxScore = 0;
    	
//////////beginning of while loop
    	while(running){
    		count++;
    		Item currItem = (Item) current.item();
    	//WHERE PROGRAM ENDS!	
    		if(currItem.possMoves().isEmpty()) {
    			//System.out.println("No more possible moves");
    			//System.out.println("Ending...");
    			bestestMove = bestMove;
    			running = false;
    			break;
    		}
		//Updating bestMove
    		if(current == dict.front()) {
    			currItem.updateBestMove((Move) currItem.possMoves().front().item());
    		//	System.out.println("Best move of " + bestMove + " with score of " + currItem.score());
    		//	System.out.println("Current's best move = " + currItem.bestMove());
    		}
    	//Getting to depth	
    		while(depth != 0) {
    			if(currItem.alpha() > currItem.beta()) {
    				//System.out.println("Moving up the tree (not at child");
    				current = moveUp(current);
    				currItem = (Item) current.item();
    				depth = currItem.depth;
    				nextPlayer = switchPlayer(currItem.color());
    				bestMove = currItem.bestMove();
    				continue;
    			}
    			//System.out.println("Creating new node--------------------");
    			Board currentBoard = currItem.board().copyOf(currItem.board());
    			Move nextMove = (Move) currItem.possMoves().front().item();
    			currentBoard.placePiece(nextMove, nextPlayer);
    			nextPlayer = switchPlayer(nextPlayer); //switching players because currPlayer already added piece
    	//adding new entry in this.dict for enemy player
    			this.dict.insertBack(currItem.bestMove(), currentBoard, nextPlayer);
    			((Item) dict.back().item()).updatePossMoves(possibleMoves(currentBoard, nextPlayer));
    			((Item) dict.back().item()).updateAlpha(currItem.alpha());
    			((Item) dict.back().item()).updateBeta(currItem.beta());
    			currItem.possMoves().remove(currItem.possMoves().front());
    			current = dict.back();
    			currItem.depth = depth;
    			depth = depth - 1;
    			currItem = (Item) current.item();
    		}
       	//Reached depth; assign first possible move
    		DListNode child = currItem.possMoves().front();
    		
    	//Iterate through possMoves until needs pruned or goes through all possibilities
    		//System.out.println("Before check parent, current color = " + currItem.color());
    		while(child != currItem.possMoves.head()) {
    			Board tempBoard = currItem.board().copyOf(currItem.board());
    			tempBoard.placePiece((Move) child.item(), player);
    			int score = heuristic(tempBoard, player);
    			//System.out.println(child + "'s score is " + score);
    	//alpha-beta pruning
    			if(isMax(currItem.color())) {
    				if(score > currItem.beta() ) {
    					pruned = true;
    					break;	
    					//stop exploring children
   					}
   					if(score > currItem.alpha()) { 
    					currItem.updateAlpha(score);
    					currItem.updateScore(score);
    					alpha = currItem.alpha();
    					bestMove = (current == dict.front()) ? (Move) child.item() : currItem.bestMove();
   						updated = true;
    				}
   					else if(score > maxScore) maxScore = score;
    			} else {
					if(score < currItem.alpha() ) {
						pruned = true;
						break;
						//stop exploring children
					}    					
					if(score < currItem.beta()) {
    					currItem.updateBeta(score);
    					currItem.updateScore(score);
    					beta = currItem.beta();
    					bestMove = (current == dict.front()) ? (Move) child.item() : currItem.bestMove();
    					updated = true;
    				}
					else if(score < minScore) minScore = score;
    			}
   				child = child.next();
   			
    		}
    		//System.out.println("bottom node's score: " + currItem.score() + "__________________");
    		//here's where go when break 
    //Assigning score to current node
    		if(!updated && !pruned) {
    			System.out.println("Never updated or pruned");
    			if(isMax(currItem.color())) currItem.updateScore(maxScore);
    			else currItem.updateScore(minScore);
    		}
    		
    		
    /////DOOOOOOONE			
    		if(current == dict.front()) {
    			//System.out.println("*Ending...");
    			bestestMove = bestMove;
        		running = false;
        		break;
    		} else {
    //Traversing back up tree	
    			//System.out.println("Moving up the tree");
    			current = moveUp(current);
    			currItem = (Item) current.item();
    			depth = currItem.depth;
    			nextPlayer = currItem.color();
    			bestMove = currItem.bestMove();
    			//System.out.println("Successfully moved up tree");
    			continue;
   			}   		
   		} 
    	//System.out.println("Ended");
    	return bestestMove;
    }
    
    private int switchPlayer(int player) {
    	return 1 - player;
    }
    
    private DListNode moveUp(DListNode current) {
    	Item currItem = (Item) current.item();
		int tempDepth = currItem.depth + 1;
		DListNode parent = current.prev();
		Item parentItem = (Item) parent.item();
		if(parent != dict.front()) {
			//System.out.println("parent is not root");
//Go until parent's possible moves are no more 			
			while(parentItem.possMoves().isEmpty()) {
				//System.out.println("Parent has no more poss moves");
				if(parent == dict.front()) {
					//System.out.println("Parent is dict.front... return this node!");
					parentItem.depth = tempDepth;
					return parent; 
				}
				parent = parent.prev();
				current = parent;
				parentItem = (Item) parent.item();
				currItem = (Item) current.item();
				if(isMax(parentItem.color())) { //IF MAX
					if(currItem.score() > parentItem.score()) {
						parentItem.updateScore(currItem.score);
						parentItem.updateAlpha(parentItem.score());
						parentItem.updateBestMove(currItem.bestMove());
						alpha = parentItem.alpha();
					}	
				} else {
					if(currItem.score() < parentItem.score()) {
						parentItem.updateScore(currItem.score);
						parentItem.updateBeta(parentItem.score());
						parentItem.updateBestMove(currItem.bestMove());
						beta = parentItem.beta();
					} 	
				}
				dict.remove(dict.back());
				tempDepth++;
				parentItem.depth = tempDepth;
			} 
		
		}
		//System.out.println("More moves available");
		if(isMax(parentItem.color())) { //IF MAX
			if(currItem.score() > parentItem.score()) {
				parentItem.updateScore(currItem.score);
				parentItem.updateAlpha(parentItem.score());
				parentItem.updateBestMove(currItem.bestMove());
				alpha = parentItem.alpha();
			}	
		} else {
			if(currItem.score() < parentItem.score()) {
				parentItem.updateScore(currItem.score);
				parentItem.updateBeta(parentItem.score());
				parentItem.updateBestMove(currItem.bestMove());
				beta = parentItem.beta();
			} 	
		
		}
		dict.remove(dict.back());
		parentItem.depth = tempDepth;	
		return parent; 
    }
     
/**
 * Returns a DList of all possible moves for a given board    
 * @param bd current board
 * @param player whose turn it is 
 * @return
 */
    public DList possibleMoves(Board bd, int player) {
        DList ans = new DList();
        Move newMove;

	// this is for each "add move"
        if(bd.getPieces() < 20) {
	    for(int i = 0; i < 8; i++) {
		for(int j = 0; j < 8; j++) {
		    newMove = new Move(i, j);
		    if(bd.validateRules(newMove, player)) {
			ans.insertFront(newMove);
		    }
		}
	    }
	    // this is for each "step move"
        } else {
	    for(DListNode d : bd.getBoard().getEList()) {
		for(int i = 0; i < 8; i++) {
		    for(int j = 0; j < 8; j++) {
			newMove = new Move(i, j, ((Vertex) d.item).id() / 10, ((Vertex) d.item).id() % 10);
			if(bd.validateRules(newMove, player)) {
			    ans.insertFront(newMove);
			}
		    }
		}
	    }
        }
        return ans;
    }    

/*
 * The scoring heuristic will create a score based on these factors
 * if you have a win +100
 * if the opponent has a win -100
 * for n number of pieces in your goal,  +(n * 1)
 * for n number of groups of two pieces next to each other, -(n * 2)
 * for n  max depth from your goal, +(n * 5)
 * for opponents n max depth from goal, -(n * 5)
 * for n number of edges of yours, + n
 * for n number of edges opponents', - n
 */
    public int heuristic(Board board, int color) {
		//int score = 0;
		//int enemy = 0;
		//if (color == 0){
		//enemy = 1;
		//} 
		//if color wins
		//if ( /*color wins*/ true){
		//score += 100;
		//    }
		//if enemy wins
		//if(/*enemy wins*/ true){
		//score -= 100;
		//}
		//number of pieces in your goal
		//score += board.getBoard().numberInGoal(color);
		//number of pieces next to each other
		//score -= /*stuff*/ 0;
		//your depth
		//score += /*stuff*/ 0;
		//their depth
		//score -= /*stuff*/ 0;
		//your edges
		//score += board.getBoard().numOfEdges(color) * 3;
		//their edges
		//score -= board.getBoard().numOfEdges(enemy) * 3;
		//return score; 
    	return (int) (Math.random() * 100);
		
}
   
 public static void main(String[] args) {
	 Board bd = new Board();
	 bd.placePiece(new Move(2,4), 1);
	 bd.placePiece(new Move(2,5), 1);
	 bd.placePiece(new Move(1,4), 0);
	 /*
	 bd.placePiece(new Move(1,1), 1);
	 bd.placePiece(new Move(2,1), 1);
	 bd.placePiece(new Move(3,4), 1);
	 bd.placePiece(new Move(4,6), 1);
	 bd.placePiece(new Move(5,3), 1);
	 bd.placePiece(new Move(6,1), 1);
	 bd.placePiece(new Move(4,1), 1);
	 bd.placePiece(new Move(2,6), 0);
	 bd.placePiece(new Move(3,3), 0);
	 bd.placePiece(new Move(3,5), 0);
	 bd.placePiece(new Move(3,2), 0);
	 bd.placePiece(new Move(5,2), 0);
	 bd.placePiece(new Move(5,6), 0);
	 bd.placePiece(new Move(6,2), 0);
	 bd.placePiece(new Move(6,4), 0);
	 bd.placePiece(new Move(4,4), 1);
	 bd.placePiece(new Move(6,6), 1); */
	 
	 System.out.println(bd.getBoard().prettyPrint());
	 Minimax mm = new Minimax(bd, 3, 1);
	 Move bestMove = mm.minimax();
	 System.out.println(bestMove);
	 System.out.println(count);	 
 }
}
 