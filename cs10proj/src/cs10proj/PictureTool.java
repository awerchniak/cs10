package cs10proj;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Stack;

/**
 * Class that demonstrates creating a graphical
 * user interface to work with pictures
 * @author Barb Ericson
 */
public class PictureTool extends JFrame {
  
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** picture for the label */
  Picture picture = new Picture(300,300);
  
  /** picture label */
  JLabel pictureLabel = null;
  
  /** stack to hold old pictures for undo */
  Stack<Picture> pictureStack = new Stack<Picture>();
  
  /**
   * Constructor that sets up the GUI
   */
  public PictureTool() {
    
    // set title on JFrame
    super("Picture Tool");
   
    // set up the menu
    setUpMenu();
    
    // set the picture in the label
    pictureLabel = new JLabel();
    setPicture(picture);
    this.getContentPane().add(pictureLabel,BorderLayout.CENTER);
    
    // set the frame size and show it
    this.pack();
    this.setVisible(true);
  }
  
  /**
   * Method to set up the menu
   */
  private void setUpMenu() {
    
    // set up the menu bar
    JMenuBar menuBar = new JMenuBar();
    setJMenuBar(menuBar);
    
    // create the file menu
    JMenu fileMenu = new JMenu("File");
    menuBar.add(fileMenu);
    JMenuItem openItem = new JMenuItem("Open");
    JMenuItem saveItem = new JMenuItem("Save");
    JMenuItem saveAsItem = new JMenuItem("Save As");
    fileMenu.add(openItem);
    fileMenu.add(saveItem);
    fileMenu.add(saveAsItem);
    
    // handle the open
    openItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addPictureToStack();
        String file = FileChooser.pickAFile();
        setPicture(new Picture(file));
      }
    });
    
    // handle the save 
    saveItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String file = picture.getFileName();
        picture.write(file);
      }
    });
    
    // handle the save as
    saveAsItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String file = SimpleInput.getString("Enter filename");
        picture.write(FileChooser.getMediaPath(file));
      }
    });
    
    // create the tools menu
    JMenu toolsMenu = new JMenu("Tools");
    menuBar.add(toolsMenu);
    JMenuItem negateItem = new JMenuItem("Negate");
    JMenuItem flipItem = new JMenuItem("Flip");
    JMenuItem undoItem = new JMenuItem("Undo");
    toolsMenu.add(negateItem);
    toolsMenu.add(flipItem);
    toolsMenu.add(undoItem);
    
    // handle negate
    negateItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addPictureToStack();
        picture.negate();
        setPicture(picture);
      }
    });
    
    // handle flip
    flipItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addPictureToStack();
        Picture flippedPict = picture.flip();
        setPicture(flippedPict);
      }
    });
    
    // handle undo
    undoItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (!pictureStack.empty()) { 
          Picture pict = pictureStack.pop();
          setPicture(pict);
        }
      }
    });
    
  }
  
  /**
   * Method to save the current picture on the stack
   */
  private void addPictureToStack() {
    pictureStack.push(picture.copy());
  }
  
  /**
   * Method to set the picture to a new picture
   * @param p the new picture to use
   */
  public void setPicture(Picture p) {
    picture = p;
    pictureLabel.setIcon(new ImageIcon(p.getImage()));
    this.pack(); // resize for the new picture
  }
  
  /** 
   * Main method for testing
   */
  public static void main(String[] args) {
    PictureTool pictTool = new PictureTool();
    if (args.length > 0) {
      pictTool.setPicture(new Picture(args[0]));
    }
  }
}