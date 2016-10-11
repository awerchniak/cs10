package Lab3;

import java.awt.*;
/**
 * Green command class for Lab3 package
 * @author Andy Werchniak and Rob Sayegh
 */
public class GreenCmd extends Command {
	public GreenCmd(){
		super();
	}

	/**
	 * When the user clicks, if there are shapes there, set the top one to green
	 */
  public void executeClick(Point p, Drawing dwg) {
  	if(dwg.getFrontmostContainer(p)!=null)
  		dwg.getFrontmostContainer(p).setColor(Color.GREEN);
  }
  
  //do nothing when pressed
  public void executePress(Point p, Drawing dwg) {}
  
  //do nothing when dragged
  public void executeDrag(Point p, Drawing dwg) {}
}