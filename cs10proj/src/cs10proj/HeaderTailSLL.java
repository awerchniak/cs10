package cs10proj;

/**
 * SLL.java
 * 
 * Implementation of singly linked list.
 * WARNING: This implementation is guaranteed to work only if always given
 * immutable objects (or at least ones that do not change).
 * 
 * @author THC
 * @author Scot Drysdale converted to Java
 * @author Scot Drysdale, THC have made a number of modifications.
 * @author Scot Drysdale most recently modified on 1/12/2011
 */
public class HeaderTailSLL<T> implements CS10LinkedList<T> {
  // Instance variables.
  private Element<T> currentPred;    // current position in the list
  private Element<T> head;       		 // sentinel
  
  /**
   * A private class inner representing the elements in the list.
   */
  public static class Element<T> {
    // Because this is a private inner class, these can't be seen from outside SLL.
    private T data;         // reference to data stored in this element
    private Element<T> next;   // reference to next item in list
    
    /**
     * Constructor for a linked list element, given an object.
     * @param obj the data stored in the element.
     */
    public Element(T obj) {
      next = null;          // no element after this one, yet
      data = obj;           // OK to copy reference, since obj references an immutable object
    }

    /**
     * @return the String representation of a linked list element.
     */
    public String toString() {
      return data.toString();
    }
  }

  /**
   * Constructor to create an empty singly linked list.
   */
  public HeaderTailSLL() {
  	head = new Element<T>(null);
    clear();
  }

  /**
   * @see CS10LinkedList#clear()
   */
  public void clear() {
  	currentPred = head;
  	head.next = null;
  }

  /**
   * @see CS10LinkedList#add()
   */
  public void add(T obj) {
  	Element<T> x = new Element<T>(obj);
  	
  	if(!isEmpty()){
  		x.next = currentPred.next.next;
  		currentPred.next.next = x;
  		currentPred = currentPred.next;
  	}else{
  		x.next = currentPred.next;
  		currentPred.next = x;
  	}
  }

  /**
   *   * @see CS10LinkedList#remove()
   */
  public void remove() {
  	if(hasCurrent())
  		currentPred.next = currentPred.next.next;
  	else System.err.println("Can't call remove if there is no current!");
  }

  /**
   * @return the String representation of this list.
   */
  public String toString() {
    String result = "";
    
    for (Element<T> x = head.next; x!= null; x = x.next) 
      result += x.toString() + "\n"; 
    
    return result;
  }

  /**
   * @see CS10LinkedList#contains()
   */
  public boolean contains(T obj) {
    Element<T> x = null;
  
    if(!isEmpty())
    	for (x = head; x.next != null && !x.next.data.equals(obj); x = x.next) 
      ;
    
    // We dropped out of the loop either because we ran off the end of the list
    // (in which case x.next == null) or because we found s (and so x.next != null).
    if (x.next != null)
      currentPred = x;
  
    return x.next != null;
  }

  /**
   * @see CS10LinkedList#isEmpty()
   */
  public boolean isEmpty() {
    return head.next == null;
  }
  
  /**
   * @see CS10LinkedList#hasCurrent()
   */
  public boolean hasCurrent() {
    return currentPred.next != null;
  }
  
  /**
   * @see CS10LinkedList#hasNext()
   */
  public boolean hasNext() {
    return hasCurrent() && currentPred.next.next != null;
  }
  
  /**
   * @see CS10LinkedList#getFirst()
   */
  public T getFirst() { 
    if(isEmpty()) {
      System.err.println("The list is empty");
      return null;
    }
    currentPred = head;
    return get();
  }
  
  //private T findLast(){}
  
  /**
   * @see CS10LinkedList#getLast()
   */
  public T getLast() {
    if (isEmpty()) {
      System.err.println("The list is empty");
      return null;
    }
    else {
      for (currentPred = head; currentPred.next.next!=null;currentPred=currentPred.next);
    	return get();
    }
  }

  /**
   * @see CS10LinkedList#addFirst()
   */
  public void addFirst(T obj) {
  	Element<T> x = new Element<T>(obj);
  	x.next = head.next;
  	head.next = x;
  	currentPred = head;
  	
  }

  /**
   * @see CS10LinkedList#addLast()
   */
  public void addLast(T obj) {
  	if (isEmpty()) {
      System.err.println("The list is empty");
    }
    else {
    	for (currentPred = head; currentPred.next.next!=null;currentPred=currentPred.next);
      add(obj);
      currentPred = currentPred.next;
    }
  }
  
  /**
   * @see CS10LinkedList#get()
   */
  public T get() {
    if (hasCurrent()) {
      return currentPred.next.data;
    }
    else {
      System.err.println("No current item");
      return null;
    }
  }
  
  /**
   * @see CS10LinkedList#set()  
   */
  public void set(T obj) {
    if (hasCurrent())
    	currentPred.next.data = obj;
    else
      System.err.println("No current item");
  }
  
  /**
   * @see CS10LinkedList#next()
   */
  public T next() {
    if (hasNext()) {
      currentPred = currentPred.next;
      return currentPred.next.data;
    }
    else {
      System.err.println("No next item");
      return null;
    }
  }
}