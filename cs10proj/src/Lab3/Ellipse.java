package Lab3;

import java.awt.*;

/**
 * Ellipse.java
 * Class for an ellipse.
 * 
 * Written by THC for CS 5 Lab Assignment 3.
 *
 * @author Thomas H. Cormen
 * @author methods added by Andy Werchniak and Rob Sayegh for CS10Lab3 16W
 * @see Shape
 */
public class Ellipse extends Shape {
	private int height, width, left, top;
	
	//constructor
	public Ellipse(Color c, int left, int top, int width, int height){
		super(c);
		this.height = height;
		this.width = width;
		this.top = top;
		this.left = left;
	}
	
	// draw the Shape from original point to final point
  public void drawShape(Graphics page){
  	page.fillOval(this.left, this.top, this.width, this.height);
  }
  
  public boolean containsPoint(Point p){
  	return pointInEllipse(p, this.left, this.top, this.width, this.height);
  	
  } // does the Shape contain Point p?
  
  public void move(int deltaX, int deltaY){
  	this.left+=deltaX;
  	this.top+=deltaY;
  } // move the Shape
  
  public Point getCenter(){									 // return the Shape's center
  	return new Point(this.left +this.width/2, this.top +this.height/2);
  }
  
  // Helper method that returns whether Point p is in an Ellipse with the given
  // top left corner and size.
  private static boolean pointInEllipse(Point p, int left, int top, int width,
      int height) {
    double a = width / 2.0; // half of the width
    double b = height / 2.0; // half of the height
    double centerx = left + a; // x-coord of the center
    double centery = top + b; // y-coord of the center
    double x = p.x - centerx; // horizontal distance between p and center
    double y = p.y - centery; // vertical distance between p and center

    // Now we just apply the standard geometry formula.
    // (See CRC, 29th edition, p. 178.)
    return Math.pow(x / a, 2) + Math.pow(y / b, 2) <= 1;
  }
  
  /**
   * toString() class for bug checking ExchangeCmd
   */
  public String toString(){
		return "Ellipse centered at (" + (this.left +this.width/2) + "," + (this.top +this.height/2) + ")\n";
	}
  
}