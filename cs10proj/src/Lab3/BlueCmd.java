package Lab3;
/**
 * BlueButton class for Lab3 package
 */
import java.awt.*;

public class BlueCmd extends Command {
	//super constructor
	public BlueCmd(){
		super();
	}
	
	/**
	 * if there is a shape where the user clicked, make it blue
	 */
  public void executeClick(Point p, Drawing dwg) {
  	if(dwg.getFrontmostContainer(p)!=null)
  		dwg.getFrontmostContainer(p).setColor(Color.BLUE);
  }
  //do nothing when pressed
  public void executePress(Point p, Drawing dwg) {
  }
  //do nothing when dragged
  public void executeDrag(Point p, Drawing dwg) {
  }
}