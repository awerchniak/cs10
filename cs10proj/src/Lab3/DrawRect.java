package Lab3;

import java.awt.*;
/**
 * Rectangle drawing command class for Lab3
 * @author Andy Werchniak and Rob Sayegh
 *
 */
public class DrawRect extends Command {
	
	private Point initial = null;
	
	public DrawRect(){
		super();
	}
	
	//do nothing
  public void executeClick(Point p, Drawing dwg) {}
  
  /**
   * When pressed, set initial point and add a new rectangle to the drawing
   */
  public void executePress(Point p, Drawing dwg) {
  	initial = p;
  	dwg.addElement(new Rectangle(dwg.getColor(), p.x, p.y, 0, 0));
  }
  
  /**
   * When dragged, create a new Rectangle object with new dimensions and replace old one
   */
  public void executeDrag(Point p, Drawing dwg) {
  	Rectangle newRect = new Rectangle(dwg.getColor(), Math.min(p.x, this.initial.x), Math.min(p.y, this.initial.y), Math.abs(p.x - this.initial.x), Math.abs(p.y - this.initial.y));
  	dwg.set(dwg.sizeOfArrayList()-1, newRect);
  }
}