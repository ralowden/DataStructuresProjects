package dict;

import graph.*;
import player.Move;

public class Items {
    int score;
    Move original;
    Move current;
    Graph currentBoard;

    public Items(Move original, Graph currentBoard) {
	this.original = original;
	this.current = original;
	this.currentBoard = currentBoard;
    }

    public void updateCurrent(Move current) {
	this.current = current;
    }

    public void updateScore(int score) {
	this.score = score;
    }

    public int score() {
	return score;
    }

    public Move original() {
	return original;
    }

    public Move current() {
	return current;
    }

    public Graph board() {
	return board();
    }

}