/** Dict is an extension of a DList. It exists to store Objects of type
 * "Item" and therefore modifies DList's methods to store Items. 
 * 
 */

package list;

import player.*;
import dict.*;

public class Dict extends DList{
    
	// Inherits head, size; 
	
    public Dict() {
    	super();
    }
    
    protected DListNode newNode(Object item, DListNode prev, DListNode next) {
        return super.newNode(item, prev, next);
    }
    
    /**
     *  insertFront() inserts an item at the front of this DList.
     *  @param item is the item to be inserted.
     *  Performance:  runs in O(1) time.
     */
    public void insertFront(Move m, Board currentBoard, int color) {
    	Item item = new Item(m, currentBoard, color);
    	super.insertFront(item);
    }

    /**
     *  insertBack() inserts an item at the back of this DList.
     *  @param item is the item to be inserted.
     *  Performance:  runs in O(1) time.
     */
    public void insertBack(Move m, Board currentBoard, int color) {
    	Item item = new Item(m, currentBoard, color);
    	super.insertBack(item);
    }
   
    public Item first() {
    	return (Item) super.front().item;
    }
    
    public Item last() {
    	return (Item) super.back().item;
    }

    /**
     *  nextItem() returns the node's item following "node" in this DList.  If "node" is
     *  null, or "node" is the last node in this DList, return null.
     */
    public Item nextItem(DListNode node) {
      return (Item) super.next(node).item;
    }
    
    /**
     *  prevItem() returns the node's item prior to "node" in this DList.  If "node" is
     *  null, or "node" is the first node in this DList, return null.
     */
    public Item prevItem(DListNode node) {
      return (Item) super.prev(node).item;
    }
    
    public static void main(String[] args) {
    	Dict test = new Dict();
    	test.insertBack(null, null, 1);
    	System.out.println(test);
    	test.insertFront(null, null, 5);
    	System.out.println(test);
    	test.insertBack(null, null, 4);
    	System.out.println(test);
    	test.insertBack(null, null, 2);
    	System.out.println(test);
    	test.insertFront(null, null, 7);
    	System.out.println(test);
    	test.remove(test.head.next.next);
    	System.out.println(test);
    	System.out.println(test.first());
    	System.out.println(test.last());
    	System.out.println(test.nextItem(test.head.next));
    	System.out.println(test.prevItem(test.head));
    	System.out.println(test.last() == test.head.prev.item);
    	
    }
}
