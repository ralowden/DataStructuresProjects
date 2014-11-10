package player;

import java.util.*;

public class HumanPlayer extends Player {

	private int color;
	private int numMoves = 0;
	
    public HumanPlayer(int color) {
	this.color = color;
    }

	@Override
	public Move chooseMove() {
		Scanner in = new Scanner(System.in);
		int x, y;
		if(numMoves < 10){
			System.out.print("Please enter the x of the location you would like to move: ");
			x = in.nextInt();
			System.out.print("Please enter the y of the location you would like to move: ");
			y = in.nextInt();
			System.out.println("");
			Move m = new Move(x, y);
			return m;
		}else{
			System.out.print("Please enter the x of the piece you would like to move: ");
			int xx = in.nextInt();
			System.out.print("Please enter the y of the piece you would like to move: ");
			int yy = in.nextInt();
			System.out.print("Please enter the x of the new location: ");
			x = in.nextInt();
			System.out.print("Please enter the y of the new location: ");
			y = in.nextInt();
			System.out.println("");
			Move mo = new Move(xx, yy, x, y);
			return mo;
		}
		
	}

	@Override
	public boolean opponentMove(Move m) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean forceMove(Move m) {
		// TODO Auto-generated method stub
		return false;
	}

}
