/* Vertex.java */

package graph;

import list.DListNode;
import list.EList;

public class Vertex implements Comparable<Vertex>{
	
  protected int id;
  protected boolean visited;
  private int color;
  protected EList links;

  /**
   *  Vertex() is a constructor that creates a new Vertex object with an id, a color, 
   *  visted = false, and a new links EList()
   *  @param id is the id where you want to place the Vertex in the Graph
   *  @param color is the color of the player (Black or White)
   */
	
  public Vertex(int id, int color) {
    this.id = id;
    this.color = color;
    visited = false;
    links = new EList();
  }

  /**
   *  getLinks() returns the EList of connected Vertices (Links)
   *  @return the EList of connected Vertice (links)
   */

  public EList getLinks() {
    return links;
  }

  /**  
   *  visit() sets visited to be the opposite of what it is, so true = false and false = true
   */
	
  public void visit() {
    visited = !visited;
  }

  /**
   *  visited() returns the boolean value that determines if the Vertex has been visited or not
   *  @return a boolean true or false
   */	

  public boolean visited() {
    return visited;
  }

  public void reset(){
	  visited = false;
  }
  /**
   *  id() returns the id of the Vertex (That represents it's place in the graph)
   */

  public int id() {
    return id;
  }

  /**
   *  color() returns the color of the Vertex (That represents which player the Vertex belongs to)
   */
	
  public int color() {
    return color;
  }
	
  /**
   *  equals() compares two Vertices.  In their id and color are the same, it retuns true, otherwise it returns false
   *  @param that is a Vertex that you are comparing to
   *  @param a boolean true or false
   */
  public boolean equals(Object that) {
    return (that instanceof Vertex) ? this.id() == ((Vertex) that).id() && this.color() == ((Vertex) that).color() : false;
  }
	
  /**
   *  compareTo() returns -1 if the id of this is less than the other Vertex's id, 1 if the id is greater than the other Vertex's
   *  id and 0 if the id is equal to the other Vertex's id
   *  @param that is a Vertex that you are comparing to
   *  @return an int -1, 0 or 1
   */

  public int compareTo(Vertex that) {
    if (this.id() < that.id()) {
      return -1;
    } else if (this.id() > that.id()) {
        return 1;
      } else {
	  return 0;
        }
  }
	
  /**
   *  toString() prints out the Vertex
   */

  public String toString() {
    String color;
		
    if (this.color == -1) {
      color = "INVALID";
    } else if (this.color == 0) {
        color = "BLACK";
      } else if (this.color == 1) {
	  color = "WHITE";
	} else {
	    color = "EMPTY";
	  }
    return "ID: " + id + " " + color + " ";  
  }
}