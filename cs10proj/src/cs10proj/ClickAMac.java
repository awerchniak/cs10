package cs10proj;

/**
 * ClickAMac.java
 *  
 * Demonstrates events and listeners.
 * This version uses the applet itself as the listener.
 * Inspired by Dot.java by Lewis and Loftus
 * @author Scot Drysdale on 4/11/00.  Modified to use JApplets 1/15/2012/ 
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Because the ClickAMac class will handle mouse clicks itself (instead of
// using another class) it must implement MouseListener.

public class ClickAMac extends JApplet implements MouseListener {
	private static final long serialVersionUID = 1L;
	private final int APPLET_WIDTH = 500;
	private final int APPLET_HEIGHT = 500;

	// Instance variable for the point where the mouse was clicked.
	private Point clickPoint = null;

	/**
	 * Method to initialize variable and add components and listeners
	 */
	public void init() {
		addMouseListener(this);
		setSize(APPLET_WIDTH, APPLET_HEIGHT);

		Container cp = getContentPane(); 	// content pane holds components
		cp.add(new ClickAMacCanvas());		// the canvas is the only component

		setVisible(true);   // makes the applet (and its components) visible
	}

	/**
	 * Sets instance variable clickPoint to whenever the mouse is clicked.
	 * Then redraws the window with that as the upper left corner of the Mac.
	 * @param event the event that caused this callback.
	 */
	public void mouseClicked(MouseEvent event) {
		clickPoint = event.getPoint();
		repaint();          // will redraw it at the new clickPoint.
	}

	// Provide empty definitions for unused event methods.
	public void mousePressed(MouseEvent event) {}
	public void mouseReleased(MouseEvent event) {}
	public void mouseEntered(MouseEvent event) {}
	public void mouseExited(MouseEvent event) {}

	/**
	 * Provides the canvas for drawing graphics
	 */
	private class ClickAMacCanvas extends JPanel {
		private static final long serialVersionUID = 1L;

		/**
		 * Paints a Mac at the position given by clickPoint.
		 * @param page the graphic object to draw on.
		 */
		public void paintComponent(Graphics page) {
			super.paintComponent(page);

			if (clickPoint != null)
				drawAMac(clickPoint, page);
		}

		/**
		 * Draws a Mac on the page with p as the upper left corner.
		 * This method is declared private because it is called only by paintComponent.
		 * 
		 * @param p point that is the upper left corner of Mac drawn
		 * @param page the graphics object to draw on
		 */
		private void drawAMac(Point p, Graphics page) {
			page.setColor(Color.blue);
			page.drawRoundRect(p.x, p.y, 160, 210, 10, 10); // draw the Mac outline

			page.drawLine(p.x, p.y + 175, p.x + 160, p.y + 175);

			// Draw floppy drive
			page.setColor(Color.red);
			page.drawLine(p.x + 70, p.y + 147, p.x + 70, p.y + 143);
			page.drawLine(p.x + 70, p.y + 143, p.x + 120, p.y + 143);
			page.drawLine(p.x + 120, p.y + 143, p.x + 120, p.y + 138);
			page.drawLine(p.x + 120, p.y + 138, p.x + 145, p.y + 138);
			page.drawLine(p.x + 145, p.y + 138, p.x + 145, p.y + 152);
			page.drawLine(p.x + 145, p.y + 152, p.x + 120, p.y + 152);
			page.drawLine(p.x + 120, p.y + 152, p.x + 120, p.y + 148);
			page.drawLine(p.x + 120, p.y + 148, p.x + 70, p.y + 148);

			page.drawRect(p.x + 15, p.y + 145, 20, 20);   // Apple Logo box

			page.setColor(Color.green);
			page.drawRoundRect(p.x + 20, p.y + 15, 120, 100, 20, 20);  // screen outline

			page.setColor(Color.gray);
			page.fillRoundRect(p.x + 30, p.y + 25, 100, 80, 20, 20);
		}
	}
}