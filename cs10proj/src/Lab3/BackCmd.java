package Lab3;

import java.awt.*;
/**
 * Subclass of Command, used to send Shapes to the back of the picture
 * @author andywerchniak
 *
 */
public class BackCmd extends Command {
	
	/**
	 * call Command's constructor
	 */
	public BackCmd(){
		super();
	}
	
	/**
	 * if a drawing is clicked on, put it at the back of the list
	 */
  public void executeClick(Point p, Drawing dwg) {
  	Shape shape = dwg.getFrontmostContainer(p);
  	if (shape != null){
  		dwg.pushToBack(shape);
  	}
  }
}