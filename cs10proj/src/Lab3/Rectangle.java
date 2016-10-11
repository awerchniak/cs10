package Lab3;

import java.awt.*;

/**
 * Rectangle class (a type of shape) for Lab3 package
 * @author Andy Werchniak and Rob Sayegh
 *
 */
public class Rectangle extends Shape {
	//instance variables. Necessary for calling methods
	private int height, width, left, top;
	
	/**
	 * Constructor sets Shape's Color and Rectangle's instance variables
	 */
  public Rectangle(Color c, int left, int top, int width, int height) {
		super(c);
  	this.left = left;
  	this.top = top;
  	this.width = width;
  	this.height = height;
	}
  
  /**
   * fill a rectangle from the original point to the final point
   */
	public void drawShape(Graphics page) {
		page.fillRect(this.left, this.top, this.width, this.height);
	}

	/**
	 * is the point inside of this rectangle?
	 */
	public boolean containsPoint(Point p) {
		return (p.x >= this.left && p.x <= (this.left+this.width) && p.y>=this.top && p.y<=this.top+this.height);
	}

	/**
	 * Move the Rectangle by deltaX,deltaY
	 */
	public void move(int deltaX, int deltaY) {
		this.left+=deltaX;
		this.top+=deltaY;
	}

	/**
	 * Get the center of the Rectangle
	 */
	public Point getCenter() {
		return new Point(this.left+this.width/2, this.top+this.height/2);
	}
	
	/**
	 * toString() class override. For bug checking
	 */
	public String toString(){
		return "Rectangle centered at (" + (this.left+this.width/2) + "," + (this.top+this.height/2) + ")\n";
	}
	
}