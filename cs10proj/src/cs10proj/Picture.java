package cs10proj;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class.  
 * 
 * This version modified by Scot Drysdale to demonstrate arraylists.
 * (used in reduceTo8).  See end of listing.
 * 
 * Copyright Georgia Institute of Technology 2004-2008
 * @author Barbara Ericson ericson@cc.gatech.edu
 * Modified by Scot Drysdale to eliminate some warnings.
 */
public class Picture extends SimplePicture { 

  ///////////////////// constructors //////////////////////////////////
  
  /**
   * Constructor that takes no arguments 
   */
  public Picture () {
    
    /* not needed but use it to show students the implicit call to super()
     * child constructors always call a parent constructor 
     */
    super();  
  }
  
  /**
   * Constructor that takes a file name and creates the picture 
   * @param fileName the name of the file to create the picture from
   */
  public Picture(String fileName) {
    // let the parent class handle this fileName
    super(fileName);
  }
  
  /**
   * Constructor that takes the width and height
   * @param width the width of the desired picture
   * @param height the height of the desired picture
   */
  public Picture(int width, int height) {
    // let the parent class handle this width and height
    super(width,height);
  }
  
  /**
   * Constructor that takes a picture and creates a 
   * copy of that picture
   */
  public Picture(Picture copyPicture) {
    // let the parent class do the copy
    super(copyPicture);
  }
  
  /**
   * Constructor that takes a buffered image
   * @param image the buffered image to use
   */
  public Picture(BufferedImage image) {
    super(image);
  }
  
  ////////////////////// methods ///////////////////////////////////////
  
  /**
   * Method to return a string with information about this picture.
   * @return a string with information about the picture such as fileName,
   * height and width.
   */
  public String toString() {
    String output = "Picture, filename " + getFileName() + 
      " height " + getHeight() 
      + " width " + getWidth();
    return output;
    
  }
  
   /**
   * Class method to let the user pick a file name and then create the picture 
   * and show it
   * @return the picture object
   */
  public static Picture pickAndShow() {
    String fileName = FileChooser.pickAFile();
    Picture picture = new Picture(fileName);
    picture.show();
    return picture;
  }
  
  /**
   * Class method to create a picture object from the passed file name and 
   * then show it
   * @param fileName the name of the file that has a picture in it
   * @return the picture object
   */
  public static Picture showNamed(String fileName) {
    Picture picture = new Picture(fileName);
    picture.show();
    return picture;
  }
  
  /**
   * A method create a copy of the current picture and return it
   * @return the copied picture
   */
  public Picture copy()
  {
    return new Picture(this);
  }
  
  /**
   * Method to increase the red in a picture.
   */
  public void increaseRed() {
    Pixel [] pixelArray = this.getPixels();
    for (Pixel pixelObj : pixelArray) {
      pixelObj.setRed(pixelObj.getRed()*2);
    }
  }
  
  /**
   * Method to negate a picture
   */
  public void negate() {
    Pixel [] pixelArray = this.getPixels();
    int red,green,blue;
    
    for (Pixel pixelObj : pixelArray) {
      red = pixelObj.getRed();
      green = pixelObj.getGreen();
      blue = pixelObj.getBlue();
      pixelObj.setColor(new Color(255-red, 255-green, 255-blue));
    }
  }
  
  /**
   * Method to flip a picture 
   */
  public Picture flip() {
    Pixel currPixel = null;
    Pixel targetPixel = null;
    Picture target = 
      new Picture(this.getWidth(),this.getHeight());
    
    for (int srcX = 0, trgX = getWidth()-1; 
         srcX < getWidth();
         srcX++, trgX--) {
      for (int srcY = 0, trgY = 0; 
           srcY < getHeight();
           srcY++, trgY++) {
        
        // get the current pixel
        currPixel = this.getPixel(srcX,srcY);
        targetPixel = target.getPixel(trgX,trgY);
        
        // copy the color of currPixel into target
        targetPixel.setColor(currPixel.getColor());
      }
    }
    return target;
  }
  
  /**
   * Method to decrease the red by half in the current picture
   */
  public void decreaseRed() {
  
    Pixel pixel = null; // the current pixel
    int redValue = 0;       // the amount of red

    // get the array of pixels for this picture object
    Pixel[] pixels = this.getPixels();

    // start the index at 0
    int index = 0;

    // loop while the index is less than the length of the pixels array
    while (index < pixels.length) {

      // get the current pixel at this index
      pixel = pixels[index];
      // get the red value at the pixel
      redValue = pixel.getRed();
      // set the red value to half what it was
      redValue = (int) (redValue * 0.5);
      // set the red for this pixel to the new value
      pixel.setRed(redValue);
      // increment the index
      index++;
    }
  }
  
  /**
   * Method to decrease the red by an amount
   * @param amount the amount to change the red by
   */
  public void decreaseRed(double amount) {
 
    Pixel[] pixels = this.getPixels();
    Pixel p = null;
    int value = 0;

    // loop through all the pixels
    for (int i = 0; i < pixels.length; i++) {
 
      // get the current pixel
      p = pixels[i];
      // get the value
      value = p.getRed();
      // set the red value the passed amount time what it was
      p.setRed((int) (value * amount));
    }
  }
  
  /**
   * Method to compose (copy) this picture onto a target picture
   * at a given point.
   * @param target the picture onto which we copy this picture
   * @param targetX target X position to start at
   * @param targetY target Y position to start at
   */
  public void compose(Picture target, int targetX, int targetY) {
 
    Pixel currPixel = null;
    Pixel newPixel = null;

    // loop through the columns
    for (int srcX=0, trgX = targetX; srcX < this.getWidth();
         srcX++, trgX++) {
  
      // loop through the rows
      for (int srcY=0, trgY=targetY; srcY < this.getHeight();
           srcY++, trgY++) {

        // get the current pixel
        currPixel = this.getPixel(srcX,srcY);

        /* copy the color of currPixel into target,
         * but only if it'll fit.
         */
        if (trgX < target.getWidth() && trgY < target.getHeight()) {
          newPixel = target.getPixel(trgX,trgY);
          newPixel.setColor(currPixel.getColor());
        }
      }
    }
  }
  
  /**
   * Method to scale the picture by a factor, and return the result
   * @param factor the factor to scale by (1.0 stays the same,
   *    0.5 decreases each side by 0.5, 2.0 doubles each side)
   * @return the scaled picture
   */
  public Picture scale(double factor) {
    
    Pixel sourcePixel, targetPixel;
    Picture canvas = new Picture(
                                 (int) (factor*this.getWidth())+1,
                                 (int) (factor*this.getHeight())+1);
    // loop through the columns
    for (double sourceX = 0, targetX=0;
         sourceX < this.getWidth();
         sourceX+=(1/factor), targetX++) {
      
      // loop through the rows
      for (double sourceY=0, targetY=0;
           sourceY < this.getHeight();
           sourceY+=(1/factor), targetY++) {
        
        sourcePixel = this.getPixel((int) sourceX,(int) sourceY);
        targetPixel = canvas.getPixel((int) targetX, (int) targetY);
        targetPixel.setColor(sourcePixel.getColor());
      }
    }
    return canvas;
  }
  
  /**
   * Method to do chromakey using an input color for the background
   * and a point for the upper left corner of where to copy
   * @param target the picture onto which we chromakey this picture
   * @param bgColor the color to make transparent
   * @param threshold within this distance from bgColor, make transparent
   * @param targetX target X position to start at
   * @param targetY target Y position to start at
   */
  public void chromakey(Picture target, Color bgColor, int threshold,
                        int targetX, int targetY) {
 
    Pixel currPixel = null;
    // loop through the columns
    for (int srcX=0, trgX=targetX;
        srcX<getWidth() && trgX<target.getWidth();
        srcX++, trgX++) {

      // loop through the rows
      for (int srcY=0, trgY=targetY;
        srcY<getHeight() && trgY<target.getHeight();
        srcY++, trgY++) {

        // get the current pixel
        currPixel = this.getPixel(srcX,srcY);

        /* if the color at the current pixel is within threshold of
         * the input color, then don't copy the pixel
         */
        if (currPixel.colorDistance(bgColor)>threshold) {
          target.getPixel(trgX,trgY).setColor(currPixel.getColor());
        }
      }
    }
  }
  
    /**
   * Method to do chromakey assuming a blue background 
   * @param target the picture onto which we chromakey this picture
   * @param targetX target X position to start at
   * @param targetY target Y position to start at
   */
  public void blueScreen(Picture target,
                        int targetX, int targetY) {

    Pixel currPixel = null;
    // loop through the columns
    for (int srcX=0, trgX=targetX;
         srcX<getWidth() && trgX<target.getWidth();
         srcX++, trgX++) {

      // loop through the rows
      for (int srcY=0, trgY=targetY;
           srcY<getHeight() && trgY<target.getHeight();
           srcY++, trgY++) {

        // get the current pixel
        currPixel = this.getPixel(srcX,srcY);

        /* if the color at the current pixel mostly blue (blue value is
         * greater than red and green combined), then don't copy pixel
         */
        if (currPixel.getRed() + currPixel.getGreen() > currPixel.getBlue()) {
          target.getPixel(trgX,trgY).setColor(currPixel.getColor());
        }
      }
    }
  }
  
  /**
   * Method to change the picture to gray scale with luminance
   */
  public void grayscaleWithLuminance()
  {
    Pixel[] pixelArray = this.getPixels();
    Pixel pixel = null;
    int luminance = 0;
    double redValue = 0;
    double greenValue = 0;
    double blueValue = 0;

    // loop through all the pixels
    for (int i = 0; i < pixelArray.length; i++)
    {
      // get the current pixel
      pixel = pixelArray[i];

      // get the corrected red, green, and blue values
      redValue = pixel.getRed() * 0.299;
      greenValue = pixel.getGreen() * 0.587;
      blueValue = pixel.getBlue() * 0.114;

      // compute the intensity of the pixel (average value)
      luminance = (int) (redValue + greenValue + blueValue);

      // set the pixel color to the new color
      pixel.setColor(new Color(luminance,luminance,luminance));

    }
  }
  
  /** 
   * Method to do an oil paint effect on a picture
   * @param dist the distance from the current pixel 
   * to use in the range
   * @return the new picture
   */
  public Picture oilPaint(int dist) {
    
    // create the picture to return
    Picture retPict = new Picture(this.getWidth(),this.getHeight());
    
    // declare pixels
    Pixel currPixel = null;
    Pixel retPixel = null;
    
    // loop through the pixels
    for (int x = 0; x < this.getWidth(); x++) {
      for (int y = 0; y < this.getHeight(); y++) {
        currPixel = this.getPixel(x,y);
        retPixel = retPict.getPixel(x,y);
        retPixel.setColor(currPixel.getMostCommonColorInRange(dist));
      }
    }
    return retPict;
  }
  
  /***********
   * Methods added by Scot Drysdale to demonstrate ArrayLists
   **********/
 
 /**
  * Reduces the number of colors to 8 by picking two values for red,
  * two for green, and two for blue.  The two red values chosen are the
  * average of the pixel red value that are greater than a threshold
  * and the average of the pixel red values less than or equal to the threshold.
  * The same is done for green and blue
  */
 public void reduceTo8() {
   Pixel [] pixelArray = this.getPixels();  // Array of all pixels in the image
   final int THRESHOLD = 126;     // Dividing line between low and high color values
   
   for(int colorNum = 1; colorNum <= 3; colorNum++) {
     ArrayList<Pixel> lowValues = new ArrayList<Pixel>();
     ArrayList<Pixel> highValues = new ArrayList<Pixel>();
     
     for(Pixel pixel : pixelArray) {
       // Split the pixels into low and high color values for color colorNum
       if(getColor(pixel, colorNum) <= THRESHOLD)
         lowValues.add(pixel);
       else
         highValues.add(pixel);
     } 
       
     int lowAve = Math.round(averageColors(lowValues, colorNum));
     int highAve = Math.round(averageColors(highValues, colorNum));
       
     // Reset the color values to the average values
     for (Pixel lowPix : lowValues)
       setColor(lowPix, lowAve, colorNum);
     for (Pixel highPix : highValues)
       setColor(highPix, highAve, colorNum);                                 
   }
 }
 
 /**
  * Gets the value of the color corresponding to colorNum.
  * In an ideal world this would be added to the Pixel class
  * Precondition - colorNum is 1, 2, or 3.  (We will learn to throw exceptions later.)
  * 
  * @param pixel the pixel whose color is returned
  * @param colorNum the color to choose: 1 = red, 2 = green, 3 = blue
  */
 public static int getColor(Pixel pixel, int colorNum) {
   if(colorNum == 1)
     return pixel.getRed();
   else if (colorNum == 2)
     return pixel.getGreen();
   else
     return pixel.getBlue();
 }
 
   /**
  * Sets the value of the color corresponding to colorNum to newValue.
  * In an ideal world this would be added to the Pixel class
  * Precondition - colorNum is 1, 2, or 3.  (We will learn to throw exceptions later.)
  * 
  * @param pixel the pixel whose color is set
  * @param newValue the new value for the color
  * @param colorNum the color to choose: 1 = red, 2 = green, 3 = blue
  */
 public static void setColor(Pixel pixel, int newValue, int colorNum) {
   if(colorNum == 1)
     pixel.setRed(newValue);
   else if (colorNum == 2)
     pixel.setGreen(newValue);
   else
     pixel.setBlue(newValue);
 }
 
 /**
  * Averages the chosen color for all the pixels in an ArrayList.
  * Returns 0 if ArrayList is empty.
  * Precondition - colorNum is 1, 2, or 3.  (We will learn to throw exceptions later.)
  * 
  * @param pixels the list of pixels to be averaged
  * @param colorNum the color to average: 1 = red, 2 = green, 3 = blue
  * @return the average of the chosen color value
  */
 public static float averageColors(ArrayList<Pixel> pixels, int colorNum) {
   float sum = 0.0f;
   
   for(int i = 0; i < pixels.size(); i++)
     sum += getColor(pixels.get(i), colorNum);
   
   if(pixels.size() > 0)
     return sum/pixels.size();
   else
     return 0.0f;
 }
  
 /* 
  * End of additional methods added by Scot Drysdale.
  */
 
 /*
  * New methods added by Andy Werchniak
  */
  /**
   * Takes the weighted average of the surrounding pixels of each pixel
   * and convolves the image
   * @param matrix matrix of weights for surrounding values
   * @param title title of new picture
   */
	public Picture convolve(double [][] matrix, String title){
		int i,j,width,height;
		
		//make a copy of the original picture and set its title
		Picture newPic = this.copy();
		newPic.setTitle(title);
		
		//declare a picture object
		Pixel thisPix = null;
		
		//get height and width for array bounds
		width = this.getWidth();
		height = this.getHeight();
		
		//perform task on every pixel
		for(i=1; i<width-1;i++){				//indices to omit borders
			for(j=1; j<height-1;j++){			//indices to omit borders
				thisPix = newPic.getPixel(i, j);
				weightPix(i,j,matrix,thisPix, this);
			}
		}
		
		//return the new modified picture
		return newPic;
	}
	
	/**
	 * For use with convolve. The method for each individual pixel
	 * @param i x index of pixel
	 * @param j y index of pixel
	 * @param matrix matrix of weights
	 * @param thisPix Pixel object of the pixel being modified
	 * @param pic the original picture
	 */
	private static void weightPix(int i, int j, double [][] matrix, Pixel thisPix, Picture pic){
		int red=0, green=0, blue=0;
		
		for(int x=0;x<3;x++){			//indices:array x= 0 to 2, pixel i-1 to i+1
			for(int y=0;y<3;y++){		//indices: array y=0 to 2, pixel j-1 to j+1
				red+=matrix[x][y]*pic.getPixel(i+(x-1),j+(y-1)).getRed();				//multiply the red by the weight and add it
				green+=matrix[x][y]*pic.getPixel(i+(x-1),j+(y-1)).getGreen();		//multiply the green by the weight and add it
				blue+=matrix[x][y]*pic.getPixel(i+(x-1),j+(y-1)).getBlue();			//multiply the blue by the weight and add it
			}
		}
		
		//correct the values to make sure that they make sense
		red = Pixel.correctValue(red);
		green = Pixel.correctValue(green);
		blue = Pixel.correctValue(blue);
		
		//set the color of your pixel
		thisPix.setColor(new Color(red,green,blue));
		
	}
	/**
	 * Creates a new picture using only colors specified
	 * Chooses new colors based on their distance from the original colors
	 * @param colors ArrayList of colors to map to
	 * @return new picture in reduced (poster) format
	 */
	public Picture mapToColorList(ArrayList<Color> colors){
		int i,j,width,height;
		
		if(colors!=null){
			//make a copy of the original picture and set its title
			Picture newPic = this.copy();
		
			//declare a picture object
			Pixel thisPix = null;
		
			//get height and width for array bounds
			width = this.getWidth();
			height = this.getHeight();
		
			//perform task on every pixel
			for(i=0; i<width;i++){
				for(j=0; j<height;j++){
					thisPix = newPic.getPixel(i, j);
					normPix(i,j,colors,thisPix);
				}
			}
		
			//return the new modified picture
			return newPic;
		} else{
			
			//in case an empty list is passed in
			System.err.println("Error: @param ArrayList<Color> colors is empty");
			return null;
		}
	}
	
	/**
	 * For use with mapToColors. Finds the closest allowed color to the pixel's 
	 * color and changes the pixel to this new color
	 * @param i	x value of pixel
	 * @param j	y value of pixel
	 * @param colors	list of colors to change the pixel to
	 * @param thisPix	the pixel to change
	 */
	private static void normPix(int i, int j, ArrayList<Color> colors, Pixel thisPix){
		int k;
		double [] distances = new double[colors.size()];
		
		//check distance of each color in the ArrayList to find the closest one
		for(k=0; k<colors.size(); k++)
			distances[k]=Pixel.colorDistance(thisPix.getColor(), colors.get(k));
		
		//change the color of the pixel to the closest allowed color
		thisPix.setColor(colors.get(arrayMinIndex(distances)));
	}
	
	/**
	 * Takes an array of doubles and returns the index of its minimum element
	 * @param distances double[] array to check
	 * @return integer index of the minimum value in the array
	 */
	private static int arrayMinIndex(double [] distances){
		int i, length;
		length = Array.getLength(distances);
		double min = distances[0];
		int minIndex = 0;
		
		for (i=0;i<length;i++){
				if (distances[i]<min){
					minIndex = i;
					min = distances[i];
				}
				
		}
		
		return minIndex;
	}
	
	/**
	 * Takes a String n input and generates a random list of n colors
	 * @param count number of random colors in the generated list
	 * @return the list
	 */
	private static ArrayList<Color> randomColorList(int count){
		int i,r,g,b;
		Random rand = new Random();
		
			ArrayList<Color> list = new ArrayList<Color>(count);
			
			for (i=0; i<count;i++){
				r= rand.nextInt(255);
				g= rand.nextInt(255);
				b= rand.nextInt(255);
				
				//ensure no repeats
				if(!list.contains(new Color(r,g,b)))
					list.add(new Color(r,g,b));
				else i--;
			}
			
			return list;
	}
	
	/**
	 * returns an arraylist of the first k unique colors in an image
	 * @param number number of colors with which to populate the arraylist
	 * @return arraylist of first unique colors in the image
	 */
	private ArrayList<Color> firstKUnique(int number){
		ArrayList<Color> colors = new ArrayList<Color>();
		int i,j;
		
		//scan pixels heightwise first and widthwise second
		//	find first k unique ones and add them to colors ArrayList
		//	if it has found the correct number, stop
		for(i=0;i<this.getWidth() && colors.size()<number;i++){
			for(j=0;j<this.getHeight() && colors.size()<number;j++){
				if(!colors.contains(this.getPixel(i,j).getColor()))
					colors.add(this.getPixel(i, j).getColor());
				if(colors.size()==number)
					return colors;
			}
		}
		
		//in case the picture contains less than your desired # of colors
		if(colors.size()<number){
			for(i=colors.size();i<number;i++){
				colors.add(randomColorList(1).get(0));
			}
			return colors;
		}
		
		return null;	//default return statement to make compiler happy
	}
	
	/**
	 * Takes an image and reduces it to a certain number of colors
	 * @param number number of colors to reduce to
	 * @return new image with reduced colors
	 */
	public Picture reduceColors(int number){
		return(this.mapToColorList(computeColors(number)));
	}
	
	/**
	 * Returns an arraylist (of specified length) of the best centroids
	 * to represent the colors in an image
	 * @param number number of centroids to return
	 * @return ArrayList<Color> of centroids
	 */
	public ArrayList<Color> computeColors(int number){
		int r,g,b,i,j,k=0;
		double[] distances = new double[number];
		ArrayList<ArrayList<Color>> clusters = new ArrayList<ArrayList<Color>>();
		ArrayList<Color> centroids;
		ArrayList<Color> compare = null;
		
		//initialize clusters
		for(i=0;i<number;i++)
			clusters.add(new ArrayList<Color>());
		
		//use a random generator to create an initial set of centroids
		//	although it does not create nearly as accurate of a depiction
		//	of the image as the k-means method, this approach is much
		//	faster
		//centroids = randomColorList(number);
		
		//find the first k unique colors to initialize centroids
		//	this method works much better than using random colors input,
		//	as with random colors you can guess entirely wrong to start
		//	and end up with empty clusters off the bat. With this method,
		//	you are guaranteed to use colors that are in the picture
		centroids = firstKUnique(number);
		
		//perform task until it doesn't change the centroids
		while(!centroids.equals(compare)){
			compare = centroids;
			
			//clear the clusters
			for(i=0;i<number;i++)
				clusters.get(i).clear();
			
			//repopulate the new clusters
			for(i=0;i<this.getWidth();i++){
				for(k=0;k<this.getHeight();k++){
					//resize distances array in case # of centroids has changed
					distances = new double[centroids.size()];
				  //fill array of distances from color of pixel to the centroid colors
					for(j=0;j<centroids.size();j++){
						distances[j]=relColorDistance(this.getPixel(i, k).getColor(),centroids.get(j));
					}
					//add the pixel's color to the closest centroid's cluster
					clusters.get(arrayMinIndex(distances)).add(this.getPixel(i,k).getColor());
				}
			}
			
			//set centroids to a new ArrayList
			centroids = new ArrayList<Color>();
			//find new centroids and set them
			for(i=0;i<number;i++){
				r=0;
				g=0;
				b=0;
				
				//only add a set a centroid to a cluster's avg if the centroid
				//	isn't empty
				if(clusters.get(i).size()!=0){
					//compute the average of the colors in the cluster
					for(j=0;j<clusters.get(i).size();j++){
						r+=clusters.get(i).get(j).getRed();
						g+=clusters.get(i).get(j).getGreen();
						b+=clusters.get(i).get(j).getBlue();
					}
				
					r/=clusters.get(i).size();
					g/=clusters.get(i).size();
					b/=clusters.get(i).size();
					
					//set centroid to that average
					centroids.add(new Color(r,g,b));
				}
			}
		}
		//print out the number of centroids to assess accuracy of method
		System.out.println(centroids.size());
		return(centroids);
	}
	
	/**
	 * A faster version of Pixel.colorDistance() method
	 * @param color1 first Color to compare
	 * @param color2 second Color to compare
	 * @return distance^2 between them
	 */
	private static double relColorDistance(Color color1, Color color2){
		double redDistance = color1.getRed() - color2.getRed();
	  double greenDistance = color1.getGreen() - color2.getGreen();
	  double blueDistance = color1.getBlue() - color2.getBlue();
	  
	  return(redDistance * redDistance + 
           greenDistance * greenDistance +
           blueDistance * blueDistance);
	}
	
	public static void main(String[] args)
  {

		//automatic color mapping
		Picture origPic, poster1,poster2;
		//number of colors to reduce to
		int colorNum = 8;
    
    //pick a picture to edit
    String name = new String(FileChooser.pickAFile());
    origPic = new Picture(name);
		
    //perform operation and display new image
    poster1 = origPic.reduceColors(colorNum);
    poster1.setTitle("First 8 Unique Colors");
    poster1.explore();
    
    colorNum = 256;
    poster2 = origPic.reduceColors(colorNum);
    poster2.setTitle("First 256 Unique Colors");
    poster2.explore();

		
		/*
		//random colors mapping
		Picture origPic,poster1,poster2;
		int colorNum;
		
		//pick a picture to edit
		String name = new String(FileChooser.pickAFile());
		origPic = new Picture(name);
		
		colorNum=8;
		poster1 = origPic.mapToColorList(randomColorList(colorNum));
		poster1.setTitle("8 Random Colors");
		poster1.explore();
		
		colorNum=256;
		poster2 = origPic.mapToColorList(randomColorList(colorNum));
		poster2.setTitle("256 Random Colors");
		poster2.explore();
    */
		
		/*
		//original 8 colors mapping
		ArrayList<Color> colors = new ArrayList<Color>(8);
		Picture origPic, poster;
		colors.add(Color.red);
		colors.add(Color.green);
		colors.add(Color.blue);
		colors.add(Color.cyan);
		colors.add(Color.orange);
		colors.add(Color.yellow);
		colors.add(Color.black);
		colors.add(Color.white);
		
    //pick a picture to edit
    String name = new String(FileChooser.pickAFile());
    origPic = new Picture(name);
    origPic.setTitle("Original");
    origPic.explore();
		
    poster = origPic.mapToColorList(colors);
    poster.setTitle("Poster!");
    poster.explore();
    */
    
		/*
		//my 8 colors mapping
		ArrayList<Color> colors = new ArrayList<Color>(8);
		Picture origPic, poster;
		colors.add(new Color(155,198,229));	//light blue
		colors.add(new Color(131,149,60));	//green
		colors.add(new Color(58,60,43));		//shadowed green
		colors.add(new Color(160,192,175));	//light teal
		colors.add(new Color(186,186,167));	//light tan
		colors.add(new Color(175,150,104));	//tan
		colors.add(new Color(146,175,187));	//slightly darker blue
		colors.add(new Color(105,132,129));	//blue/green
		
    //pick a picture to edit
    String name = new String(FileChooser.pickAFile());
    origPic = new Picture(name);
    origPic.setTitle("Original");
    origPic.explore();
		
    poster = origPic.mapToColorList(colors);
    poster.setTitle("Poster!");
    poster.explore();
		*/
  }
	
	/*
	 * End of new methods added by Andy Werchniak
	 */
} // this } is the end of class Picture, put all new methods before this
 