/* ListIter.java */

package list;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *  ListIter.java is a custom Iterator for the EList class
 *  The constructor takes an EList:
 *
 *    ListIter(EList list)
 *
 */

class ListIter implements Iterator<DListNode> {
  protected EList list;
  protected DListNode current;
	

  /**
   *  ListIter() is a constructor that assigns the EList list to this.list
   *  @param list is the EList that you want to iterate through
   */

  public ListIter(EList list) {
    this.list = list;
    current = this.list.head;
  }

  /**
   *  hasNext() checks to see if there is a "next" node
   *  @return a boolean true or false depending on if there is a "next" node
   */
	
  public boolean hasNext() {
    if (list.size == 0) {
      return false;
    } else {
      return (current.next != list.head) ? true : false;
    }
  }

  /** 
   *  next() updates the current DListNode to be the next one in the EList
   *  if there is is a next node.  Checks this through hasNext()  If there is
   *  no next move, it throws a new NoSuchElementException()
   *  @return DListNode that is the new current node
   */
	
  public DListNode next() {
    if (hasNext()) {
      current = current.next;
      return current;
    } else {
      throw new NoSuchElementException();
      }
  }

  /**
   *  remove() just throws an UnsupportedOperationException() because you should
   *  not be able to remove a DListNode from the EList in this class
   */ 
  public void remove() {
    throw new UnsupportedOperationException();
  }
	
}
