
/** DList is an ADT that connects DListNodes together to form a list. 
 * It has a head node which has no object that serves as a "starting point" in the list--
 * an empty DList, therefore, has only a head node and a size of 0. 
 * A DList knows its size (the number of nodes other than the head 
 * and can access any of its nodes through a series of "prev" and "next" commands.  
 * 
 * @author Rachel Lowden, Pat Stefaniak 
 *
 */

package List; 

public class DList {

	protected DListNode head; 
	protected int size;
	
	public DList() {
		this.head = new DListNode();
		this.head.next = head;
		this.head.prev = head;
		this.size = 0;
	}
	/** goTo(int pos) returns node at position int, if greater than size returns error message
	 * 
	 */
	public DListNode goTo(int pos){
		if(pos > this.size){
			System.out.println("goTo(): position does not exist");
			return null;
		}
		else{
			DListNode find = head.next;
			int count = 1;
			while(count < pos){
				find = find.next;
				count ++;
			}
			return find;
		}
			
	}
	/** add(Object o, int pos) inserts a DListNode, with item pointing to Object o, at the position indicated by the int
	 * if the position does not exist a message saying so will appear and the program will terminate
	 * if the position is one greater than the length of the list it will add a DListNode to the end
	 * if the position inputed is less than 1 it will add to the front
	 */
	public void add(Object o, int pos){
		if(pos == (this.size + 1)){
			this.addEnd(o);
			return;
		}
		if(pos > this.size){
			System.out.println("Could not add, position does not exist");
			return;
		}
		DListNode theNext = this.goTo(pos);
		DListNode thePrev = theNext.prev;
		thePrev.next = new DListNode(thePrev, theNext, o);
		theNext.prev = thePrev.next;
		size ++;
	}
	
	/** remove(int pos) removes the DListNode at position int, 
	 * if the position does not exist a message saying so will appear and the program will terminate
	 * if the position inputed is less than 1 it will remove the first node
	 */
	public void remove(int pos){
		if(pos > this.size){
			System.out.println("Could not remove, position does not exist");
			return;
		}
		DListNode toRemove = this.goTo(pos);
		DListNode before = toRemove.prev;
		DListNode after = toRemove.next;
		before.next = after;
		after.prev = before;
		size --;
	}
	
	
	/** addEnd(Object o) inserts a DListNode, with item pointing to Object o, at the end of the DList and increments the size 
	 * of the DList by 1. 
	 * 
	 */
	
	public void addEnd(Object o){
		DListNode newNode = new DListNode(head.prev, head, o);
		head.prev.next = newNode;
		head.prev = newNode; 
		size++;
	}
	
	public DListNode getFront() {
		return head.next;
	}
	
	public DListNode getHead() {
		return head;
	}
	
	public DListNode getBack() {
		return head.prev;
	}
	
	public int getSize() {
		return size;
	}
	
	public boolean isEmpty() {
		return (size == 0);
	}

	/** listToString() prints out a Dlist's DNode objects in order.
	 * 
	 */
	
	public String toString() {
		String myString = "[ ";
		DListNode currentNode = head.next;
		if(currentNode == head) {
			myString += "]";
			return myString;
		} else {
			for(int i = 1; i <= size; i ++) {
				myString += currentNode.item + " ";
				currentNode = currentNode.next;
			}
			myString += "]";
			return myString;
		}
	}
	
	/** reverseToString() prints out a DList's DNode objects in reverse order
	 * (intended for testing purposes).
	 * 
	 */
	
	public void reverseToString() {
		DListNode currentNode = head.prev;
		if(currentNode == head) {}
		else {
			System.out.print("[ ");
			for(int i = 1; i <= size; i++) {
				System.out.print(currentNode.item + " ");
				currentNode = currentNode.prev;
			}
			System.out.println("]");
		}
	}
	
	public static void main(String[] args) {
		DList tester = new DList();
		System.out.println(tester);
		tester.addEnd(7);
		System.out.println(tester);
		tester.addEnd(8);
		System.out.println(tester);
		System.out.println("Size of list: " + tester.size);
		tester.reverseToString();
		DListNode testNode = tester.goTo(1);
		System.out.println(testNode.item);
		System.out.println(tester);
		tester.addEnd(9);
		tester.addEnd(10);
		System.out.println(tester);
		tester.add(4, 0);
		System.out.println(tester);
		tester.remove(-2);
		System.out.println(tester);
	}
}
