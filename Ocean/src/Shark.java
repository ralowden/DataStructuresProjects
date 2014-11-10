/* Shark.java */

/**
 *  The Shark class extends the Cell class.  In addition to everything a Cell object knows, 
 *  a Shark knows it's hunger
 *  There are two constructors:
 *  
 *    public Shark(int x, int y, int item);
 *
 *  This creates a new Shark with hunger = 0
 *
 *    public Shark(int x, int y, int item, int hunger);
 *
 *  This creates a new Shark with hunger = hunger
 */

public class Shark extends Cell {
	
  private int hunger = 0;

  /**
   *  Shark() is a constructor that creates a shark at the position (x, y) 
   *  in the Ocean with a hunger = 0.
   *  @param x is the x-coordinate of the Ocean
   *  @param y is the y-coordinate of the Ocean
   *  @param item is the item of the cell (will always be SHARK)
   */
  
  public Shark(int x, int y, int item) {
    super(x, y, item);
  }
  
  /**
   *  Shark() is a constructor that creates a shark at the position (x, y)
   *  in the Ocean with a hunger = hunger.
   *  @param x is the x-coordinate of the Ocean
   *  @param y is the y-coordinate of the Ocean
   *  @param item is the item of the cell (will always be SHARK)
   *  @param hunger is the hunger of the Shark
   */

  public Shark(int x, int y, int item, int hunger) {
    super(x, y, item);
    this.hunger = hunger;
  }
  
  /**
   *  setHunger() sets the hunger of the shark to the input
   *  @param hunger is the hunger to set to this.hunger
   */

  public void setHunger(int hunger) {
    this.hunger = hunger;
  }
  
  /**
   *  getHunger() returns the hunger of the shark
   *  @return returns the hunger of the shark
   */

  public int getHunger() {
    return hunger;
  }

  /**
   *  increaseHunger() increases the hunger of the shark by 1
   */
  
  public void increaseHunger() {
    hunger += 1; 
  }

  /**
   *  resetHunger() resets the shark's hunger back to 0 
   *  (used if the shark just ate a Fish)
   */

  public void resetHunger() {
    hunger = 0; 
  }  
  
  /**
   * toString() prints out the cell with the shark's hunger
   * @return a String representing the cell, with the format (x, y, item, hunger)
   */

  public String toString() {
	  return "(" + x + ", " + y + ", " + item + ", " + hunger + ")";
  }
}
  
