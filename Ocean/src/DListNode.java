
/** DListNode is a class of objects that comprise a DList. 
 * They know what their previous and next nodes are and contain an object. 
 * 
 * @author Rachel Lowden, Pat Stefaniak 
 *
 */

package List;

public class DListNode {

	protected DListNode prev, next;
	protected Object item; 
	
	public DListNode(DListNode p, DListNode n, Object o) {
		this.prev = p;
		this.next = n;
		this.item = o; 
	}
	
	public DListNode() {
		this(null, null, null);
	}
	
	public Object getItem() {
		return item;
	}
	
	public DListNode next() {
		return next;
	}

}
