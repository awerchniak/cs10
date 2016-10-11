package Lab3;

import java.awt.*;

/**
 * Ellipse command class for Lab3
 * @author Andy Werchniak and Rob Sayegh
 */
public class DrawEllipse extends Command {
	//instance variable
	private Point initial = null;
	
	//super constructor
	public DrawEllipse(){
		super();
	}
	
	//do nothing when clicked
  public void executeClick(Point p, Drawing dwg) {}
  
  /**
   * When the user pressed, add an Ellipse to the shapes ArrayList in dwg
   */
  public void executePress(Point p, Drawing dwg) {
  	initial = p;
  	dwg.addElement(new Ellipse(dwg.getColor(), p.x, p.y, 0, 0));
  	
  }
  
  /**
   * When the user drags, set the element of the same index to a new Ellipse
   * 	based on dimensions of initial point and new point. Garbage collection will clean up
   * 	old Ellipse object
   */
  public void executeDrag(Point p, Drawing dwg) {
  	dwg.set(dwg.sizeOfArrayList()-1, new Ellipse(dwg.getColor(), Math.min(p.x, this.initial.x), Math.min(p.y, this.initial.y), Math.abs(p.x - this.initial.x), Math.abs(p.y - this.initial.y)));
  }
}