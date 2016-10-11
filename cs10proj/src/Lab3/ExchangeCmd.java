package Lab3;
import java.awt.*;

/**
 * ExchangeCmd.java
 * Command class to perform an exchange command.
 * 
 * Written by THC for CS 5 Lab Assignment 3.
 * @author Thomas H. Cormen
 * @see Command
 */
public class ExchangeCmd extends Command {
  private Shape firstShape; // the first Shape clicked
  
  /**
   * When the mouse is clicked, find the frontmost Shape in the drawing
   * that contains the mouse position. If there is such a Shape, then
   * if this is the first click in the pair of clicks (indicated by
   * firstShape being null), save it in firstShape. Otherwise, exchange
   * the centers of this newly clicked Shape and firstShape.
   * 
   * @param p the coordinates of the click
   * @param dwg the drawing being clicked
   */
  public void executeClick(Point p, Drawing dwg) {
    // Find the frontmost shape containing p.
    Shape s = dwg.getFrontmostContainer(p);
    
    if (s != null) { // was there a Shape containing p?
      if (firstShape == null){
        firstShape = s; // save this Shape for when there's another click
      }
      else {
        // We have two Shapes to exchange. Get their centers.
        Point firstCenter = firstShape.getCenter();
        //System.out.print("firstCenter = " + firstShape);		//bug checking
        Point secondCenter = s.getCenter();
        //System.out.print("secondCenter=" + s);							//bug checking
        

        // Exchange their centers.
        firstShape.setCenter(secondCenter);
        //System.out.print("new firstCenter = " + firstShape);	//bug checking
        s.setCenter(firstCenter);
        //System.out.print("new secondCenter = " + s);					//bug checking

        // Now we get to start all over.
        firstShape = null;
      }
    }
  }
}