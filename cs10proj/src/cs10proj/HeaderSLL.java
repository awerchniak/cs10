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
public class HeaderSLL<T> implements CS10LinkedList<T> {
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
	   //only use return the data held in the object (and not its pointer)
      return data.toString();
    }
  }

  /**
   * Constructor to create an empty singly linked list.
   */
  public HeaderSLL() {
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
  	
  	//check if we are adding the first element or adding a different one
  	//	difference: adding a first element doesn't require us to move currentPred pointer
  	//	because we have a sentinel at the beginning
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
  	//simply skip the current item in the list, Java garbage collection will deal with 
  	//	freeing memory
  	if(hasCurrent())
  		currentPred.next = currentPred.next.next;
  	else System.err.println("Can't call remove if there is no current!");
  }

  /**
   * @return the String representation of this list.
   */
  public String toString() {
    String result = "";
    
    //append each element in the list to a string and return it
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
  	//check to make sure list isn't empty, first element is at head.next
    if(isEmpty()) {
      System.err.println("The list is empty");
      return null;
    }
    currentPred = head;
    return get();
  }
  
  /**
   * @see CS10LinkedList#getLast()
   */
  public T getLast() {
  	//check to make sure list isn't empty, check list until an element points to null
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
  	//put the element in between the sentinel and first element
  	x.next = head.next;
  	head.next = x;
  	currentPred = head;
  	
  }

  /**
   * @see CS10LinkedList#addLast()
   */
  public void addLast(T obj) {
  	//like getLast but add instead of get
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
  	//return the data at an element if it exists
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
  	//if there is an object, set its data
    if (hasCurrent())
    	currentPred.next.data = obj;
    else
      System.err.println("No current item");
  }
  
  /**
   * @see CS10LinkedList#next()
   */
  public T next() {
  	//move to the next element in the list
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