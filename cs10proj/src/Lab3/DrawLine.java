package Lab3;

import java.awt.*;
/**
 * Command class to draw a line for Lab3 package
 * @author Andy Werchniak and Rob Sayegh
 *
 */
public class DrawLine extends Command {
	private Point initial = null;
	
	public DrawLine(){
		super();
	}
	
	//do nothing when clicked
  public void executeClick(Point p, Drawing dwg) {}
  
  /**
   * press to start a new line
   */
  public void executePress(Point p, Drawing dwg) {
  	initial = p;
  	dwg.addElement(new Segment(dwg.getColor(), this.initial.x, this.initial.y, this.initial.x, this.initial.y));
  }
  
  /**
   * drag to set new dimensions. See DrawEllipse for more detailed explanation
   */
  public void executeDrag(Point p, Drawing dwg) {
  	dwg.set(dwg.sizeOfArrayList()-1, new Segment(dwg.getColor(), initial.x, initial.y, p.x, p.y));
  }
}