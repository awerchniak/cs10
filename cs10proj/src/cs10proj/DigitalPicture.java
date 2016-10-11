package cs10proj;

import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * Interface to describe a digital picture.  A digital picture can have a 
 * associated file name.  It can have a title.  It has pixels 
 * associated with it and you can get and set the pixels.  You 
 * can get an Image from a picture or a BufferedImage.  You can load
 * it from a file name or image.  You can show a picture.  You can 
 * create a new image for it.
 * 
 * @author Barbara Ericson
 */
public interface DigitalPicture 
{
 /** get the file name that the picture came from */
 public String getFileName(); 
 /** get the title of the picture */
 public String getTitle(); 
 /** set the title of the picture */
 public void setTitle(String title); 
 /** get the width of the picture in pixels */
 public int getWidth(); 
 /** get the height of the picture in pixels */
 public int getHeight(); 
 /** get the image from the picture */
 public Image getImage(); 
 /** get the buffered image */
 public BufferedImage getBufferedImage();
 /** get the pixel information as an int */
 public int getBasicPixel(int x, int y);   
 /** set the pixel information */
 public void setBasicPixel(int x, int y, int rgb); 
 /** get the pixel information as an object */
 public Pixel getPixel(int x, int y); 
 /** load the image into the picture */
 public void load(Image image); 
 /** load the picture from a file */
 public boolean load(String fileName); 
 /** show the picture */
 public void show(); 
}