
/* Graph.java */
/*  THIS IS THE GRAPH ADT.  NOTE: An id of a vertex is denoted by its location on the board by coordinate ij like below.
 * 
                 -----------------------------------------
                 |    | 10 | 20 | 30 | 40 | 50 | 60 |    |
                 -----------------------------------------
                 | 01 | 11 | 21 | 31 | 41 | 51 | 61 | 71 |
                 -----------------------------------------
                 | 02 | 12 | 22 | 32 | 42 | 52 | 62 | 72 |
                 -----------------------------------------
                 | 03 | 13 | 23 | 33 | 43 | 53 | 63 | 73 |
                 -----------------------------------------
                 | 04 | 14 | 24 | 34 | 44 | 54 | 64 | 74 |
                 -----------------------------------------
                 | 05 | 15 | 25 | 35 | 45 | 55 | 65 | 75 |
                 -----------------------------------------
                 | 06 | 16 | 26 | 36 | 46 | 56 | 66 | 76 |
                 -----------------------------------------
                 |    | 17 | 27 | 37 | 47 | 57 | 67 |    |
                 -----------------------------------------
 */

package graph;

import list.*;

public class Graph {

  private int size;  // number of vertices
  private EList adj; // contains a EList of all the vertices (the core structure of the Graph)

  /**
   *  Graph() is a constructor that creates a new EList adj that represents the Board.  It is a EList of Vertices
   *  It also initiates the size to be the length of that EList adj
   */

  public Graph() {
    adj = new EList();
    size = adj.length();
  }
  
  /**
   *  updateSize() modifies the size variable to be the new length of the EList
   */

  private void updateSize() {
    size = adj.length();
  }

  /**
   *  getElist() returns the EList adj of the graph
   *  @return the EList adj of the graph
   */

  public EList getEList() {
    return this.adj;
  }

  /**
   *  incidentEdges() takes an id and returns the connected Vertices (or Links) of that Vertex
   *  @param int id of the Vertex that you want to find the Links of
   *  @return an EList of the Vertices that the Vertex at id is linked to
   */
  public EList incidentEdges(int id) {
    return adj.findVertex(id).getLinks();
  }

  /**
   *  insertVertex() Inserts a vertex into this graph.  If the vertex already exists do nothing.
   *  @param int id of the Vertex representing the location that you want to place the Vertex
   *  @param int color representing which player the Vertex (piece) belongs to
   */

  public void insertVertex(int id, int color) {
    if (adj.findVertex(id) != null) {
      return;
    }
    Vertex newOne = new Vertex(id, color);
    adj.insertBack(newOne);
    this.updateSize();
    findEdges(newOne);
  }

  /**
   *  getSize() returns the size of the graph
   *  @return int size of the graph
   */  

  public int getSize() {
    return this.size;
  }

  /**
   *  areAdjacent() checks to see if two Vertices are connected and have a link
   *  @param int vid is the id of Vertex v
   *  @param int wid is the id of Vertex w
   *  @return returns true if Vertex at id vid and Vertex at id wid are connected
   */

  public boolean areAdjacent(int vid, int wid) {
    EList e = adj.findVertex(vid).getLinks();
		
    if (e == null) {
      return false;
    }	
    Vertex found = e.findVertex(wid);
    if (found == null) {
      return false;
    } else {
      return true;
    }
  }
	
  /**
   *  insertEdge() Links/connects two vertices with an edge.  This will add each vertex to the other's internal links EList
   *  @param int vid is the id of the first vertex
   *  @param int wid is the id of the second vertex
   */

  public void insertEdge(int vid, int wid) {
    Vertex e = adj.findVertex(vid);
    Vertex f = adj.findVertex(wid);
		
    if (e != null && f != null) {
      e.getLinks().insertBack(f);
      f.getLinks().insertBack(e);
    }
  }
	
  /**
   *  removeVertex() removes a vertex from this graph.  If the vertex does not exist, this method does nothing.
   *  @param int id is the id of the vertex to be removed.
   */

  public void removeVertex(int id) {
    Vertex e = adj.findVertex(id);
    //create edges that will be opened up by this removal
    removeFindEdges(e);
    // first remove links to this vertex in the graph.  Then remove links to that vertex.
    if (e != null) {
      while (e.getLinks().length() != 0) {
        DListNode d = e.getLinks().front();
	this.removeEdge(e.id(), ((Vertex) d.item).id());
      }
    }
    // finally remove the Vertex and update the size.
    adj.removeVertex(e);
    this.updateSize();
  }

  /**
   *  removeEdge() removes an Edge between two Vertices.  If one or both of the vertices doesn't exist, it does nothing
   *  @param int vid is the id of the first vertex
   *  @param int wid is the id of the second vertex
   */
	
  public void removeEdge(int vid, int wid) {
    Vertex e = adj.findVertex(vid);
    Vertex f = adj.findVertex(wid);
		
    if (e != null && f != null) {
      e.getLinks().removeVertex(f);
      f.getLinks().removeVertex(e);
    }
  }

  /**
   *  findPiece looks in the adj EList to see if a Vertex at id id exists.  If it exists, it will return it, otherwise it returns null
   *  @param id is the id of the Vertex that you are looking for
   *  @return the Vertex if it is found in the graph, otherwise null
   */

  public Vertex findPiece(int id) {
    Vertex e = adj.findVertex(id);
    return (e == null) ? null : e;
  }

  /**
   *  toString() prints out the graph
   *  @return returns the graph as a String
   */
	
  public String toString() {
    return adj.printDeep();
  }

  /**
   *  prettyPrint() prints out the graph so that it looks like a game board
   *  @return returns the graph as a String
   */

  public String prettyPrint() {
    String ans = "---------------------------------";
    for (int i = 0; i < 8; i++) {
      ans = ans + "\n| ";
      for (int j = 0; j < 8; j++) {
        Vertex found = findPiece(i + (10 * j));
	if (found != null) {
          if (found.color() == 1) {
	    ans = ans + "W" + " | ";
          } else {
	      ans = ans + "B" + " | ";
	    }
	} else {
	    ans = ans + "  | ";
	  } 
      }
      ans = ans + "\n---------------------------------";
    }
    return ans;
  }

  /**
   *dfs(Vertex v) (Depth-first search) -  starts at v and explores as far as possible along each branch 
   *before backtracking.
   * @param v is the vertex to start at
   */
   

    public void dfs(Vertex v){		
        v.visit();
        System.out.println(v);
        for(DListNode d : v.getLinks()){
            if(((Vertex) d.item).visited() == false){
            	dfs((Vertex) d.item);
            }
        }
    }
    
    public void resetVisit(){
    	for (DListNode d : adj) {
    	   ((Vertex) d.item).reset();
    	}
    }
    //returns boolean
    //it starts from the top or left side of the board depending on what color
    //uses search algorithm to find vertex in opposite end 
    //if it finds something in an end it checks to see if the length is at least 6
    //if two pieces in a row are the same slope it doesnt move on
    public boolean isWin(int color){
        int[] feed;
    	if(color == 0){
    		for(int x = 10; x < 80; x += 10){
    			if(findPiece(x) != null){
                            feed = new int[10];
                            feed[0] = findPiece(x).id();
                            System.out.println("starting at vertex: " + findPiece(x));
			    return isWin2(findPiece(x), null, 1, feed);
    			}
    		}
    	}
    	if(color == 1){
    		for(int x = 1; x < 8; x ++){
    			if(findPiece(x) != null){
                            feed = new int[10];
                            feed[0] = findPiece(x).id();
                            System.out.println("starting at vertex: " + findPiece(x));
			    return isWin2(findPiece(x), null, 1, feed);
    			}
    		}
    	}
    	resetVisit();
    	return false;
    }
    
    public boolean isWin2(Vertex v, Vertex prev, int count, int[] path){
         System.out.println(v + " length is: " + count);
         System.out.println("looking at Vertex " + v + "the path is: " + java.util.Arrays.toString(path)); 
         if (count > 9) {
        	 return false;
         }
         if(isInEnd(v) && count != 1){
        	 if(count >= 6){
	        	 return true;
	        	 }
        	 else {
        		 return false;
        	 }
         }
         System.out.println("Links " + v.getLinks());
         DListNode d = v.getLinks().front();
         for(DListNode dl: v.getLinks()) {
        	 Vertex vx  = (Vertex) dl.item();
             System.out.println("d item " + d.item);
             boolean visit = false;
             for(int j : path) {
                 if(j == vx.id()) {
                     visit = true;
                 }
             }
		     if(prev != null){
		    	 if(visit == false
		    			 && getSlope(prev, v) != getSlope(v, vx)){
		    		 path[count] = vx.id();
		    		 if(isWin2(vx, v, count + 1, path)) {
		    			 return true;
		    		 } else {		    			 
		    			 path[count] = 0;
		    		 }
		    	 }
		     } else if(visit == false){
		    		 path[count] = vx.id();
			     if (isWin2(vx, v, count + 1, path)) {
			    	 return true;
			     } 
			     else {			    	 
			    	 path[count] = 0;
			     }
			 }
	     }
         
	     return false;
	 } 

    
    //finds depth based on edges, exactly the same as isWin only returns int
    public int findDepth(int color){
    	if(color == 0){
    		for(int x = 10; x < 80; x += 10){
    			if(findPiece(x) != null){
    				return findDepth2(findPiece(x), null, 1);
    			}
    		}
    	}
    	if(color == 1){
    		for(int x = 1; x < 8; x ++){
    			if(findPiece(x) != null){
    				return findDepth2(findPiece(x), null, 1);
    			}
    		}
    	}
    	resetVisit();
    	return 0;
    }
    
    public int findDepth2(Vertex v, Vertex prev, int count){
        System.out.println(v + " length is: " + count);
        if((isInEnd(v) && count != 1) || (v.visited()) && count != 1){
         resetVisit();
       	 return count;
        }
        v.visit();
        for(DListNode d : v.getLinks()){
       	 if(prev != null){
       		 if(((Vertex) d.item).visited() == false 
       				 && getSlope(prev, v) != getSlope(v, (Vertex) d.item)){
       			
       			 return findDepth2((Vertex) d.item, v, count + 1);
       		 }
       	 } else{
       		 if(((Vertex) d.item).visited() == false){
       			
       			 return findDepth2((Vertex) d.item, v, count + 1);
       		 }
       	 }
        }
        resetVisit();
        return count;
    }
    
    
    //gives the slope between two vertices 
    //finds y first because it is the one place of the int id
    //if the slope is undefined, returns 10 (slope will never be near that because board is 8x8)
    //if both the same point, return n-10
    public static float getSlope(Vertex one, Vertex two){
    	float oneY = one.id % 10;
    	float oneX = (one.id - oneY) / 10;
    	float twoY = two.id % 10;
    	float twoX = (two.id - twoY) / 10;
    	if (oneX == twoX && oneY != twoY){
    		return 10;
    	}
    	if (oneX == twoX && oneY == twoY){
    		return -10;
    	}
    	return (twoY - oneY) / (twoX - oneX);
    }

    //returns distanceTo between two vertices
    //if they are the same return 11
    public static double distanceTo(Vertex one, Vertex two){
    	if(one.equals(two)){
    		return 11;
    	}
    	int oneY = one.id() % 10;
    	int oneX = (one.id() - oneY) / 10;
    	int twoY = two.id() % 10;
    	int twoX = (two.id() - twoY) / 10;
  //  	System.out.println("oneY " + oneY + " oneX " + oneX + " twoY " + twoY + " twoX " + twoX);
    	return  Math.sqrt(Math.pow(twoX - oneX,2) + Math.pow(twoY - oneY,2));
    }
    //finds all of the vertices this vertex is attached to in the graph
    //goes through list of vertices and keeps closest instance in each direction
    //after going through the list it makes edges with all of the pieces of the same color
    public void findEdges(Vertex x){
    	Vertex upLeft = x;
    	Vertex up = x;
    	Vertex upRight = x;
    	Vertex midLeft = x;
    	Vertex midRight = x;
    	Vertex downLeft = x;
    	Vertex down = x;
    	Vertex downRight = x;
    	float slope = 0;
    	//iterates through adj, finds closest vertex in each direction 
    	for (DListNode d : adj) {
   // 		System.out.println("d: " + d.item);
    		slope = getSlope(x, ((Vertex) d.item));
   // 		System.out.println("slope: " + slope);
    		if (slope == 0){
   // 			System.out.println("slope is 0!");
    			if(x.id > (((Vertex) d.item).id())){
    				if(distanceTo(x, ((Vertex) d.item)) < (distanceTo(x, midLeft))){
    					midLeft = ((Vertex) d.item);
    				}
    			}
    			else{
    				if(distanceTo(x, ((Vertex) d.item)) < (distanceTo(x, midRight))){
    					midRight = ((Vertex) d.item);
    				}
    			}
    		}
    		else if (slope == -1){
  //  			System.out.println("slope is -1!");
    			if(x.id < (((Vertex) d.item).id())){
    				if(distanceTo(x, ((Vertex) d.item)) < (distanceTo(x, upRight))){
    					upRight = ((Vertex) d.item);
    				}
    			}
    			else{
    				if(distanceTo(x, ((Vertex) d.item)) < (distanceTo(x, downLeft))){
    					downLeft = ((Vertex) d.item);
    				}
    			}
    		}
    		else if (slope == 1){
   // 			System.out.println("slope is 1!");
    			if(x.id > (((Vertex) d.item).id())){
    				if(distanceTo(x, ((Vertex) d.item)) < (distanceTo(x, upLeft))){
    					upLeft = ((Vertex) d.item);
    				}
    			}
    			else{
    				if(distanceTo(x, ((Vertex) d.item)) < (distanceTo(x, downRight))){
    					downRight = ((Vertex) d.item);
    				}
    			}
    		}
    		else if (slope == 10 ){
    //			System.out.println("slope is 10 for id: " + x.id() + " and " + down.id());
    //			System.out.println("x.id is: " + x.id() + "d.item: " + (((Vertex) d.item).id()));
    			if(x.id > (((Vertex) d.item).id())){
   // 				System.out.println("dist x to d.item: " + distanceTo(x, ((Vertex) d.item)) );
   // 				System.out.println("dist x to up: " + (distanceTo(x, up)));
    				if(distanceTo(x, ((Vertex) d.item)) < (distanceTo(x, up))){
    //					System.out.println("going up");
    					up = ((Vertex) d.item);
    				}
    			}
    			else{
    				if(distanceTo(x, ((Vertex) d.item)) < (distanceTo(x, down))){
    		//			System.out.println("distance between " + x.id() + " and " + ((Vertex) d.item).id() + " is " + distanceTo(x, ((Vertex) d.item)) );
    					down = ((Vertex) d.item);
    				}
    			}
    		}
    	}
  //  	System.out.println("pointers" + upLeft.id() + " " + up.id() + " " + upRight.id() + " ");
  //  	System.out.println( midLeft.id() + " " + midRight.id() + " ");
  //  	System.out.println(downLeft.id() + " " + down.id() + " " + downRight.id() + " ");
    	//finds any existing edges that were disrupted and removes them
    	if(midLeft.color() == midRight.color()){
    		removeEdge(midLeft.id(), midRight.id());
    	}
    	if(upLeft.color() == downRight.color()){
    		removeEdge(upLeft.id(), downRight.id());
    	}
    	if(downLeft.color() == upRight.color()){
    		removeEdge(downLeft.id(), upRight.id());
    	}
    	if(up.color() == down.color()){
    		removeEdge(up.id(), down.id());
    	}
    	//inserts edges with pieces of same color
//    	System.out.println("x.color is: " + x.color() + " up.color is" + up.color());
    	
    	if(x.color() == upLeft.color() && x != upLeft){
    		insertEdge(x.id(), upLeft.id());
    	}
    	if(x.color() == upRight.color() && x != upRight){
    		insertEdge(x.id(), upRight.id());
    	}
    	if(x.color() == up.color() && x != up){
    		if(!(up.color() == 1 && (up.id() < 7 || up.id() > 70))){
    		this.insertEdge(x.id(), up.id());
    		}
    	}
    	if(x.color() == midLeft.color() && x != midLeft){
    		if(!(midLeft.color() == 0 && (midLeft.id() % 10 == 0 || midLeft.id() % 10 == 7))){
       		insertEdge(x.id(), midLeft.id());
    		}
    	}
    	if(x.color() == midRight.color() && x != midRight){
    		if(!(midRight.color() == 0 && (midRight.id() % 10 == 0 || midRight.id() % 10 == 7))){
         	insertEdge(x.id(), midRight.id());
    		}
    	}
    	if(x.color() == downLeft.color() && x != downLeft){
    		insertEdge(x.id(), downLeft.id());
    	}
    	System.out.println("id: " + x.id() + " down: " + down.id());
    	if(x.color() == down.color() && x != down){
 //   		System.out.println("Slope of " + x.id() + " to down " + down.id());
    		if(!(x.color() == 1 && (x.id() < 7 || x.id() > 70))){
  //  			System.out.println("inserting down");
    		insertEdge(x.id(), down.id());
    		}
    	}
    	if(x.color() == downRight.color() && x != downRight){
//    		System.out.println("Slope of " + x.id() + " to down right " + downRight.id() + " is " +  getSlope(x , downRight));
    		insertEdge(x.id(), downRight.id());
    	}
    	
   // 	System.out.println("end");
    }
    
    public void removeFindEdges(Vertex x){
    	Vertex upLeft = x;
    	Vertex up = x;
    	Vertex upRight = x;
    	Vertex midLeft = x;
    	Vertex midRight = x;
    	Vertex downLeft = x;
    	Vertex down = x;
    	Vertex downRight = x;
    	float slope = 0;
    	//iterates through adj, finds closest vertex in each direction 
    	for (DListNode d : adj) {
  //  		System.out.println("d: " + d.item);
    		slope = getSlope(x, ((Vertex) d.item));
 //   		System.out.println("slope: " + slope);
    		if (slope == 0){
//    			System.out.println("slope is 0!");
    			if(x.id > (((Vertex) d.item).id())){
    				if(distanceTo(x, ((Vertex) d.item)) < (distanceTo(x, midLeft))){
    					midLeft = ((Vertex) d.item);
    				}
    			}
    			else{
    				if(distanceTo(x, ((Vertex) d.item)) < (distanceTo(x, midRight))){
    					midRight = ((Vertex) d.item);
    				}
    			}
    		}
    		else if (slope == -1){
 //   			System.out.println("slope is -1!");
    			if(x.id < (((Vertex) d.item).id())){
    				if(distanceTo(x, ((Vertex) d.item)) < (distanceTo(x, upRight))){
    					upRight = ((Vertex) d.item);
    				}
    			}
    			else{
    				if(distanceTo(x, ((Vertex) d.item)) < (distanceTo(x, downLeft))){
    					downLeft = ((Vertex) d.item);
    				}
    			}
    		}
    		else if (slope == 1){
  //  			System.out.println("slope is 1!");
    			if(x.id > (((Vertex) d.item).id())){
    				if(distanceTo(x, ((Vertex) d.item)) < (distanceTo(x, upLeft))){
    					upLeft = ((Vertex) d.item);
    				}
    			}
    			else{
    				if(distanceTo(x, ((Vertex) d.item)) < (distanceTo(x, downRight))){
    					downRight = ((Vertex) d.item);
    				}
    			}
    		}
    		else if (slope == 10 ){
  //  			System.out.println("slope is 10!");
   // 			System.out.println("x.id is: " + x.id() + "d.item: " + (((Vertex) d.item).id()));
    			if(x.id > (((Vertex) d.item).id())){
    				System.out.println("dist x to d.item: " + distanceTo(x, ((Vertex) d.item)) );
    				System.out.println("dist x to up: " + (distanceTo(x, up)));
    				if(distanceTo(x, ((Vertex) d.item)) < (distanceTo(x, up))){
    					System.out.println("going up");
    					up = ((Vertex) d.item);
    				}
    			}
    			else{
    				if(distanceTo(x, ((Vertex) d.item)) < (distanceTo(x, down))){
    					down = ((Vertex) d.item);
    				}
    			}
    		}
    	}
    	//finds any existing edges that were unblocked and creates them
    	if(midLeft.color() == midRight.color()){
    		insertEdge(midLeft.id(), midRight.id());
    	}
    	if(upLeft.color() == downRight.color()){
    		insertEdge(upLeft.id(), downRight.id());
    	}
    	if(downLeft.color() == upRight.color()){
    		insertEdge(downLeft.id(), upRight.id());
    	}
    	if(up.color() == down.color()){
    		insertEdge(up.id(), down.id());
    	}
    }
    
    public int numberInGoal(int color){
    	int count = 0;
    	if(color == 1){
    		for (DListNode d : adj) {
    			if(((Vertex) d.item).id() < 10 || ((Vertex) d.item).id() > 70 ){
    				count ++;
    			}
    		}
    	}
    	else {
    		for (DListNode d : adj) {
    			if(((Vertex) d.item).id() % 10 == 0 || ((Vertex) d.item).id() % 10 == 7){
    				count ++;
    			}
    		}
    	}
    	return count;
    }
    //iterates through the graph and counts the number of edges for one color
    //divides by two at the end because each edge is counted twice, once at two vertices
    public int numOfEdges(int color){
    	int count = 0;
    	for (DListNode d : adj) {
    		if (((Vertex) d.item).color() == color){
    			count += ((Vertex) d.item).links.length();
    		}
    	}
    	return count / 2;
    }
    //returns boolean whether or not a piece is in its endzone
    public static boolean isInEnd(Vertex v){
    	if(v.color() == 0){
    		if(v.id() % 10 == 0 || v.id() % 10 == 7)
    			return true;
    	}
    	else if(v.id() < 8 || v.id() > 70)
    		return true;
        return false;
    }
    public static void main(String[] args) {
	Graph board = new Graph();
    
   /* 
    board.insertVertex(02, 1);
    board.insertVertex(13, 1);
    board.insertVertex(16, 1);*/
	    //   board.insertVertex(56, 1);
	    //board.insertVertex(34, 1);
	    // board.insertVertex(74, 1);
    board.insertVertex(45, 0);
    board.insertVertex(47, 0);
    // board.insertVertex(65, 0);
    //  board.insertVertex(74, 0);
    board.insertVertex(43, 0);
    board.insertVertex(21, 0); 
    board.insertVertex(51, 0);
    board.insertVertex(50, 0);
    board.insertVertex(63, 0);
    System.out.println(board.prettyPrint());
    System.out.println(board);
    System.out.println("numOfEdges white: " + board.numOfEdges(1));
    System.out.println("numOfEdges black: " + board.numOfEdges(0));
    System.out.println("numInGoal black: " + board.numberInGoal(0));
    System.out.println("numInGoal white: " + board.numberInGoal(1));
    System.out.println("depth black: " + board.findDepth(0));
    System.out.println("depth white: " + board.findDepth(1));
   // System.out.println("white wins: " + board.isWin(1));
    System.out.println("black wins: " + board.isWin(0));
  
    }

 


}
