package cs10proj;

/**
 * File: PsychBoxes.java
 * 
 * Draws rectangles that seem to move inward
 * 
 * @author THC, converted to Java and JApplet by Scot Drysdale.
 */

import java.awt.*;            // needed for the drawing code
import java.awt.event.*;
import javax.swing.Timer;
import javax.swing.JApplet;
import javax.swing.JPanel;

public class PsychBoxes extends JApplet implements ActionListener {
	private static final long serialVersionUID = 1L;

	private Timer timer;  // signals an action at given time intervals
	private int iter;     // keeps track of offset for colors.

	/**
	 * Initialze the applet
	 */
	public void init() {
		setSize(350, 350);
		timer = new Timer(100, this);     // 100 milliseconds between redrawing the boxes
		iter = 0;

		Container cp = getContentPane();    // content pane holds components
		cp.add(new PsychBoxesCanvas());     // the canvas is the only component

		timer.start();
		setVisible(true);   // makes the applet (and its components) visible
	}

	/**
	 * Rotates the colors by 1 and repaints
	 * @param event the timer event that caused this callback.
	 */
	public void actionPerformed(ActionEvent event) {
		iter = (iter + 1) % 4;  // offset between 0 and 3
		repaint();  // signal that JApplet needs to be redrawn
	}

	/**
	 * Create the drawing canvas to be added to the JApplet content panel 
	 */
	private class PsychBoxesCanvas extends JPanel {
		private static final long serialVersionUID = 1L;
		/**
		 * Paints the boxes on the graphics page.
		 * Note that it can see iter from the outer class.
		 * @param page the graphic object of the panel for drawing on
		 */
		public void paintComponent(Graphics page) {
			final int RECTS = 55;             // draw this many concentric rectangles
			final int WIDTH = 1;              // pen width
			final int DELTA = WIDTH + 2;      // how far apart the Rects are
			final int BOUND = DELTA * RECTS;  // used for determining Rect boundary

			super.paintComponent(page);

			for (int i = 1; i <= RECTS; i++) {
				// Rotate among four colors.
				switch ((i + iter) % 4) {
				case 0 :
					page.setColor(Color.yellow);
					break;

				case 1:
					page.setColor(Color.green);
					break;

				case 2:
					page.setColor(Color.blue);
					break;

				case 3:
					page.setColor(Color.magenta);
					break;
				}

				page.drawRect(BOUND - DELTA*i, BOUND - DELTA*i,  // try drawOval() also!
						2*DELTA*i, 2*DELTA*i);              
			}
		}
	}
}