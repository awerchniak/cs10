package Lab3;

import java.awt.*;

/**
 * What to do when the user presses the red button
 * @author Andy Werchniak and Rob Sayegh
 *
 */
public class RedCmd extends Command {
	
	public RedCmd(){
		super();
	}
	
	/**
	 * If the user clicks on a shape, change its color
	 */
  public void executeClick(Point p, Drawing dwg) {
  	if(dwg.getFrontmostContainer(p)!=null)
  		dwg.getFrontmostContainer(p).setColor(Color.RED);
  }
  
  //do nothing for press or drag
  public void executePress(Point p, Drawing dwg) {}
  public void executeDrag(Point p, Drawing dwg) {}
  
}