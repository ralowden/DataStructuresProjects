/* Ocean.java */
import java.io.*;
/**
 *  The Ocean class defines an object that models an ocean full of sharks and
 *  fish.  Descriptions of the methods you must implement appear below.  They
 *  include a constructor of the form
 *
 *      public Ocean(int i, int j, int starveTime);
 *
 *  that creates an empty ocean having height i and width j, in which sharks
 *  starve after starveTime timesteps.
 *
 *  See the README file accompanying this project for additional details.
 */

public class Ocean {

  /**
   *  Do not rename these constants.  WARNING:  if you change the numbers, you
   *  will need to recompile Test4.java.  Failure to do so will give you a very
   *  hard-to-find bug.
   */

  public final static int EMPTY = 0;
  public final static int SHARK = 1;
  public final static int FISH = 2;
  public static int count = 0;

  /**
   *  Define any variables associated with an Ocean object here.  These
   *  variables MUST be private.
   */

  private int width;
  private int height;
  private int starveTime;
  private Cell[][] ocean;

  /**
   *  The following methods are required for Part I.
   */

  /**
   *  Ocean() is a constructor that creates an empty ocean having height i and
   *  width j, in which sharks starve after starveTime timesteps.
   *  @param i is the width of the ocean.
   *  @param j is the height of the ocean.
   *  @param starveTime is the number of timesteps sharks survive without food.
   */

  public Ocean(int i, int j, int starveTime) {
    width = i;
    height = j;
    this.starveTime = starveTime;
    ocean = new Cell[width][height];
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        ocean[x][y] = new Cell(x, y);
      }
    }
  }
  
  /**
   *  copyOcean() creates an exact copy of another Ocean (same width, height, starveTime, and each cell in the new Ocean
   *  contains the same contents as the respective cell in the old Ocean)
   *  @parm old is the old Ocean
   */

  private void copyOcean(Ocean old) {
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        if (old.ocean[x][y] instanceof Shark) {
          this.addShark(x, y, ((Shark) old.ocean[x][y]).getHunger());
        } else {
          this.ocean[x][y].setItem(old.cellContents(x, y));
        }
      }
    }
  }

  /**
   *  width() returns the width of an Ocean object.
   *  @return the width of the ocean.
   */

  public int width() {
    return width;
  }

  /**
   *  height() returns the height of an Ocean object.
   *  @return the height of the ocean.
   */

  public int height() {
    return height;
  }

  /**
   *  starveTime() returns the number of timesteps sharks survive without food.
   *  @return the number of timesteps sharks survive without food.
   */
  
  public int starveTime() {
    return starveTime;
  }

  /**
   *  wrapAroundWidth() returns 
   *  the appropriate y coordinate if the cell is "wraped around" the Ocean
   *  or in other words, the y coordinate modulo width
   *  @param y is the potential y coordinate
   *  @return y modulo width, or the y coordinate if the cell is "wraped around"
   */ 

  private int wrapAroundWidth(int x) {
    if (x < 0) {
      return (width + (x % width)) % width;
    } else {
      return  x % width;
    }
  }

 /**
   *  wrapAroundHeight() returns 
   *  the appropriate x coordinate if the cell is "wraped around" the Ocean
   *  or in other words, the x coordinate  modulo height
   *  @param x is the potential x coordinate
   *  @return x modulo height, or the x coordinate if the cell is "wraped around"
   */

  private int wrapAroundHeight(int y) {
    if (y < 0) {
      return (height + (y % height)) % height;
    } else {  
      return y % height;
    } 
  }

  /**
   *  addFish() places a fish in cell (x, y) if the cell is empty.  If the
   *  cell is already occupied, leave the cell as it is.
   *  @param x is the x-coordinate of the cell to place a fish in.
   *  @param y is the y-coordinate of the cell to place a fish in.
   */

  public void addFish(int x, int y) {
    if(cellContents(x, y) == EMPTY) {
      ocean[x][y].setItem(FISH);
    }
  }

  /**
   *  addShark() (with two parameters) places a newborn shark in cell (x, y) if
   *  the cell is empty.  A "newborn" shark is equivalent to a shark that has
   *  just eaten.  If the cell is already occupied, leave the cell as it is.
   *  This two parameters method just calls the addShark method with three parameters.
   *  @param x is the x-coordinate of the cell to place a shark in.
   *  @param y is the y-coordinate of the cell to place a shark in.
   */

  public void addShark(int x, int y) {
    this.addShark(x, y, 0);
  }

  /**
   *  cellContents() returns EMPTY if cell (x, y) is empty, FISH if it contains
   *  a fish, and SHARK if it contains a shark.
   *  @param x is the x-coordinate of the cell whose contents are queried.
   *  @param y is the y-coordinate of the cell whose contents are queried.
   */

  public int cellContents(int x, int y) {
    return ocean[x][y].getItem();
  }
  
  /**
   *  timeStep() performs a simulation timestep as described in README.
   *  @return an ocean representing the elapse of one timestep.
   */

  public Ocean timeStep() {
    //++count;  Dubugging code
	
    Ocean newOcean = new Ocean(width, height, starveTime);
    newOcean.copyOcean(this);
    
    for (Cell[] ac : this.ocean) {
      for (Cell c : ac) {
	// this counts up the number of Fish and the number of Sharks in the cell's neighboring cells
        int numOfFish = 0, numOfSharks = 0;
        int i, j;
        for (i = (c.getX() - 1); i < (c.getX() + 2); i++) {
          for (j = (c.getY() - 1); j < (c.getY() + 2); j++) {
            if ((i == c.getX()) && (j == c.getY())) {
            } else {
              if (this.ocean[wrapAroundWidth(i)][wrapAroundHeight(j)].getItem() == SHARK) {
                numOfSharks++;
              } else if (this.ocean[wrapAroundWidth(i)][wrapAroundHeight(j)].getItem() == FISH) {
                     numOfFish++;
		     } else {}
              }
          }
        }
        // rules for if the cell contains a Shark
        if (c instanceof Shark) {
    	  if (numOfFish > 0) {
            ((Shark) newOcean.ocean[c.x][c.y]).resetHunger(); // #1
          } else if (((Shark) c).getHunger() == starveTime) {
    	           Cell newCell = new Cell(c.getX(), c.getY());
    	           newOcean.ocean[c.getX()][c.getY()] = newCell;
                 } else {
                     ((Shark) newOcean.ocean[c.x][c.y]).increaseHunger(); // #2
                   }
	// rules for if the cell contains a Fish
        } else if (c.getItem() == FISH) {
                 if (numOfSharks == 1) { // 3 is implied
                   newOcean.ocean[c.getX()][c.getY()].setItem(EMPTY); // #5
                 } else if (numOfSharks > 1) {
                          newOcean.ocean[c.getX()][c.getY()].setItem(EMPTY);
                          newOcean.addShark(c.getX(), c.getY());  // #4
                        } else {}
                 // rules for if the cell is Empty
                 } else {
		     if (numOfFish < 2) {
                     }  else if ((numOfFish >= 2) && (numOfSharks <= 1)) { // #7
            	               newOcean.ocean[c.getX()][c.getY()].setItem(FISH);
                             } else if ((numOfFish >= 2) && (numOfSharks >= 2)) { // #8
                        	      newOcean.addShark(c.x,  c.y);  
                                    } else {}
                   } 
      }
    }

    /* Debugging code
    if (count == 19) {
      paint(this);
      paint(newOcean);
      System.out.println();
    }*/

    return newOcean;
  }

  /**
   *  The following method is required for Part II.
   */

  /**
   *  addShark() (with three parameters) places a shark in cell (x, y) if the
   *  cell is empty.  The shark's hunger is represented by the third parameter.
   *  If the cell is already occupied, leave the cell as it is.  You will need
   *  this method to help convert run-length encodings to Oceans.
   *  @param x is the x-coordinate of the cell to place a shark in.
   *  @param y is the y-coordinate of the cell to place a shark in.
   *  @param hunger is an integer that indicates the shark's hunger.  It is the amount
   *         of timesteps that have passed since the shark last ate
   */

  public void addShark(int x, int y, int hunger) {
    x = wrapAroundWidth(x);
    y = wrapAroundHeight(y);
    if(cellContents(x, y) == EMPTY) {
      ocean[x][y] = new Shark(x, y, SHARK, hunger);
    }
  }

  /**
   *  The following method is required for Part III.
   */

  /**
   *  sharkFeeding() returns an integer that indicates the hunger of the shark
   *  in cell (x, y), using the same "feeding" representation as the parameter
   *  to addShark() described above.  If cell (x, y) does not contain a shark,
   *  then its return value is undefined--that is, anything you want.
   *  Normally, this method should not be called if cell (x, y) does not
   *  contain a shark.  You will need this method to help convert Oceans to
   *  run-length encodings.
   *  @param x is the x-coordinate of the cell whose contents are queried.
   *  @param y is the y-coordinate of the cell whose contents are queried.
   *  @return returns number of timeSteps since shark last fed or returns -1 if not a shark
   */

  public int sharkFeeding(int x, int y) {
    if (cellContents(x, y) == SHARK) {
      return ((Shark) ocean[wrapAroundWidth(x)][wrapAroundHeight(y)]).getHunger();
    } else { 
        return -1; 
      }
  }

  /**
   *  paint() prints out the ocean
   *  This was used to debug the code and print out the Ocean at specific timeSteps
   *  @param sea is the Ocean to be printed
   */

  public static void paint(Ocean sea) {
    if (sea != null) {
      int width = sea.width();
      int height = sea.height();

      /* Draw the ocean. */
      for (int x = 0; x < width + 2; x++) {
        System.out.print("-");
      }
      System.out.println();
      for (int y = 0; y < height; y++) {
	System.out.print("|");
        for (int x = 0; x < width; x++) {
          int contents = sea.cellContents(x, y);
          if (contents == Ocean.SHARK) {
            System.out.print('S');
          } else if (contents == Ocean.FISH) {
	           System.out.print('~');
	         } else {
	             System.out.print(' ');
	           }
	}
        System.out.println("|");
      }
      for (int x = 0; x < width + 2; x++) {
        System.out.print("-");
      }
    System.out.println();
    }
  }
}
