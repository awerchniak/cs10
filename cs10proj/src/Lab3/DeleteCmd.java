package Lab3;

import java.awt.*;

/**
 * Delete command class for Lab3 package
 * @author Andy Werchniak and Rob Sayegh
 *
 */
public class DeleteCmd extends Command {
	
	public DeleteCmd(){
		super();
	}
	
	/**
	 * If the user clicks a Shape, call Drawing's deleteElement() method
	 */
  public void executeClick(Point p, Drawing dwg) {
  	dwg.deleteElement(p);
  }
  
  //empty- do nothing when pressed or dragged
  public void executePress(Point p, Drawing dwg) {}
  public void executeDrag(Point p, Drawing dwg) {}
}