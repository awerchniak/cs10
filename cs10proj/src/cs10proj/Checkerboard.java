package cs10proj;

/**
 * Checkerboard.java
 *
 * Starting point for a Short Assignment.
 * Draws a checkerboard with black and red squares. 
 * @author Tom Cormen.  Converted to JApplet by Scot Drysdale
 * 
 * Edited by Andy Werchniak to include the blinking yellow square 01/20/2016
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;

public class Checkerboard extends JApplet implements MouseListener, ActionListener {
	private static final long serialVersionUID = 1L;
	private final int DIMENSION = 8;		// want 8 x 8 board
  private final int SQUARE_SIZE = 30; // each square is 30 x 30 pixels

  //Instance variable for the point where the mouse was clicked.
	private Point clickPoint = null;
	
	private Timer timer;  	// signals an action at given time intervals
	private Boolean blink; 	// tells whether to paint a yellow square or not
	
	/**
	 * Set up the canvas.
	 */
	public void init() {
		// Set the size of the applet to be exactly the checkerboard size.
		setSize(DIMENSION * SQUARE_SIZE, DIMENSION * SQUARE_SIZE);

		addMouseListener(this);		//make sure your applet can receive mouse clicks
		
		timer = new Timer(500, this);		//set the timer to go every half second
		blink = true;										//a yellow square appears right when you click
		
		Container cp = getContentPane();    // content pane holds components
		cp.add(new Canvas());               // the canvas is the only component
		setVisible(true);   // makes the applet (and its components) visible
	}

	/**
	 * Sets instance variable clickPoint to whenever the mouse is clicked.
	 * Then redraws the window with that as the upper left corner of the Mac.
	 * @param event the event that caused this callback.
	 */
	public void mouseClicked(MouseEvent event) {
		//System.out.println("a mouse click has occurred");
		clickPoint = event.getPoint();
		timer.start();		//start the timer
	}

	// Provide empty definitions for unused event methods in MouseListener
	public void mousePressed(MouseEvent event) {}
	public void mouseReleased(MouseEvent event) {}
	public void mouseEntered(MouseEvent event) {}
	public void mouseExited(MouseEvent event) {}
	
	/**
	 * Rotates the colors by 1 and repaints
	 * @param event the timer event that caused this callback.
	 */
	public void actionPerformed(ActionEvent event) {
		//System.out.println(iter);
		blink = !blink;		//every time the timer ticks, switch blink on/off
		repaint();  			// signal that JApplet needs to be redrawn
	}
	
	/**
	 * Serves as the drawing canvas for the checkerboard.
	 */
	private class Canvas extends JPanel {
		private static final long serialVersionUID = 1L;
		
		/**
		 * Paints the checkerboard.
		 * 
		 * @param page the Graphics object that we draw on.
		 */
		public void paintComponent(Graphics page) {
			super.paintComponent(page);
			
			//only draw the yellow square if the user has clicked
			if(clickPoint!=null){
				drawCheckerboard(page);
				drawYellowSquare(page);
			} else drawCheckerboard(page);
		}

		/**
		 * Draw the entire checkerboard in black and red.
		 * 
		 * @param page the graphics object to draw on
		 */
		public void drawCheckerboard(Graphics page) {
			Color squareColor; // Color of the current square

			for (int row = 0; row < DIMENSION; row++) {
				for (int column = 0; column < DIMENSION; column++) {
					// A square is black if the row and column numbers are both even
					// or both odd. Otherwise, the square is red.
					if (row % 2 == column % 2)
						squareColor = Color.black;
					else
						squareColor = Color.red;

					drawSquare(page, row, column, squareColor);
				}
			}
		}
		/**
		 * Draw a blinking yellow square where the user has clicked
		 * 
		 * @param pagethe graphics object to draw on
		 */
		public void drawYellowSquare(Graphics page){
			int row = clickPoint.y/SQUARE_SIZE;
			int column = clickPoint.x/SQUARE_SIZE;
			Color squareColor;
			
			//draw a yellow square when blink is true, and a normal
			//square when it is false
			if(blink==true)
				squareColor= Color.yellow;
			else {
				if (row % 2 == column % 2)
					squareColor = Color.black;
				else
					squareColor = Color.red;
			}
			
			drawSquare(page,row,column,squareColor);
		}
		/**
		 * Draw a given square of the checkerboard. Row and column numbers start
		 * from 0.
		 * 
		 * @param page graphics object to draw on
		 * @param row the row number of the square
		 * @param column the column number of the square
		 * @param color the color of the square
		 */
		public void drawSquare(Graphics page, int row, int column, Color color) {
			page.setColor(color);
			page.fillRect(column * SQUARE_SIZE, row * SQUARE_SIZE, SQUARE_SIZE,
					SQUARE_SIZE);
		}
	}
}