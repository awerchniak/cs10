package Lab3;

import java.awt.*;
import java.util.ArrayList;
/**
 * Drawing class for use within lab3 package
 * @author Andy Werchniak and Rob Sayegh
 */

public class Drawing{
	//instance variables
	private Color c;
	private ArrayList<Shape> shapes = new ArrayList<Shape>();
	
	/**
	 * simple constructor
	 * @param c Color to add shapes to the array with
	 */
	public Drawing(Color c){
		this.c = c;
	}
	
	/**
	 * draw shapes to canvas from back to front
	 * @param page canvas to draw shapes on
	 */
	public void draw(Graphics page){
		int i;
		for(i=0;i<shapes.size();i++){
			shapes.get(i).draw(page);
		}
	}
	
	/**
	 * Method to return the top shape where the user has clicked
	 * @param p point where the user has clicked
	 * @return the front shape where the user clicked
	 */
	public Shape getFrontmostContainer(Point p){
		if (this.shapes.size() > 0){												//ensure the arraylist isn't empty
				return this.get(getFrontmostIndex(p));					//get the index of the front shape
		} else return null;																	//if none found, return null
	}
	
	/**
	 * Delete the top element at point p
	 * @param p
	 */
	public void deleteElement(Point p){	
		
		int i = getFrontmostIndex(p);			//get the index of the front shape
		
		//theta(n) time... better way to do this? Tough with an ArrayList
		if(i!=-1)
			shapes.remove(i);								//if it's in there, remove it
		
		//else System.err.println("No shape at that point");
	}
	
	/**
	 * Helper method to find the index of an element
	 * @param p Point where the user clicked
	 * @return index of element or -1 if not found
	 */
	private int getFrontmostIndex(Point p){
		int i; 
		
		//start at end of ArrayList so that the method will remove the top shape
	  //short circuit to prevent out of bounds
		for(i=shapes.size()-1;i>-1 && !shapes.get(i).containsPoint(p);i--);	
		
		return i;
	}
	/**
	 * Add a shape to the end of the list (front of picture)
	 * @param s shape to add
	 */
	public void addElement(Shape s){
		shapes.add(s);
	}
	
	/**
	 * Get color of drawing (the color new shapes will be drawn as)
	 * @return	Color of drawing
	 */
	public Color getColor(){
		return c;
	}
	
	/**
	 * Set the color of the drawing (the color new shapes will be drawn as)
	 * @param c Color to change to
	 */
	public void setColor(Color c){
		this.c = c;
	}
	
	/**
	 * get the shape at the end of the array
	 * @return frontmost shape
	 */
	public Shape get(){
		return shapes.get(shapes.size()-1);
	}
	
	/**
	 * get the shape at a given index
	 * @param i index of shape
	 * @return shape at that index or null if there is no shape at that index
	 */
	public Shape get(int i){
		if(i>-1 && i<shapes.size())
			return shapes.get(i);
		else{
			//System.err.println("No element of index " + i);
			return null;
		}
	}
	
	/**
	 * Brings a shape to the end of the list
	 * @param shape Shape to move to end of the list
	 */
	public void bringToFront(Shape shape){
		//find its index
		int index = this.shapes.indexOf(shape);
		
		//if it's in the list, remove it from its position and put it at the end
		//theta(n) time... better way to do this? Darn ArrayLists
		if(index>-1 && index<shapes.size())
			shapes.add(shapes.remove(index));
	}
	
	/**
	 * Put a shape at the first index of the array (back of drawing)
	 * @param shape shape to move
	 */
	public void pushToBack(Shape shape){
		//find its index
		int index = this.shapes.indexOf(shape);
		//remove it from where it is and put it in the front
		shapes.add(0,shapes.remove(index));
	}
	
	/**
	 * get the size of the drawing's list
	 * @return number of elements in the list
	 */
	public int sizeOfArrayList(){
		return shapes.size();
	}
	
	/**
	 * Set an element at a given index to an input object
	 * @param i index of element to change
	 * @param s Shape object to change it to
	 */
	public void set(int i, Shape s){
		shapes.set(i, s);
	}
}