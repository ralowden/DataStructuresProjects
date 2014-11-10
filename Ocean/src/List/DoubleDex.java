package List;
/** This is a class that holds 2 index values. The index of a node in a list, and the Position inside the node.
 * 
 *
 */


public class DoubleDex {
	int DListpos;
	int Nodepos;
	
	/**
	 * DListpos is the index value for the node in DList
	 * NodePos is how far into the "size" of a node you need to iterate to
	 * @param args
	 */
	
	public DoubleDex(int x, int y){
		this.DListpos = x;
		this.Nodepos = y;
	}
	
	public int getDListPos(){
		return this.DListpos;
	}
	
	public int getNodePos(){
		return this.Nodepos;
	}
	
	public void setDListPos(int x){
		this.DListpos = x;
	}
	
	public void setNodepos(int x){
		this.Nodepos = x;
	}
	
	public String toString() {
		return "DListpos = " + DListpos + ", Nodepos = " + Nodepos;	
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
