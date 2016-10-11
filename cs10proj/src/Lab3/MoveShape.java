package Lab3;

import java.awt.*;
/**
 * Command to move a shape in Drawing. For the Lab3 package
 * @author Andy Werchniak and Rob Sayegh
 */
public class MoveShape extends Command {
	
	private Shape shape = null;
	private Point point = null;
	
	public MoveShape(){
		super();
	}
	
	//do nothing when clicked
  public void executeClick(Point p, Drawing dwg) {
  }
  
  /**
   * When pressed, find the shape to move and its initial point
   */
  public void executePress(Point p, Drawing dwg) {
  	shape = dwg.getFrontmostContainer(p);
  	point = p;
  	
  }
  
  /**
   * if there is a shape, move it to the new point
   */
  public void executeDrag(Point p, Drawing dwg) {
  	if (shape != null){
  		shape.move(p.x - point.x, p.y - point.y);
  		point = p;
  	}
  }
}