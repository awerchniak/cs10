package Lab3;

import java.awt.*;

/**
 * Bring a shape to the front command for Lab3 package
 * @author Andy Werchniak and Rob Sayegh
 */
public class FrontCmd extends Command {
	
	public FrontCmd(){
		super();
	}
	
	/**
	 * If there is a shape where the user clicked, call Drawing's bringToFront() method
	 */
  public void executeClick(Point p, Drawing dwg) {
  	Shape shape = dwg.getFrontmostContainer(p);
  	if (shape != null){
  		dwg.bringToFront(shape);
  	}
  }
  
}