package SA8;

/**
 * This class defines expressions that are constants.
 * You must use Constant.define to create an object of this class.
 * 
 * @author Scot Drysdale
 * @author Changes by Tom Cormen
 */
public class Constant implements Expression {   
  private double myValue;   // the value of the constant
  
  /**
   * Private Constructor, cannot be called outside of class
   * @param value the value of this Constant
   */
  private Constant(double value) {
    myValue = value;
  }
  
  /**
   * Creates a constant, but simplifies if possible
   * @param value the value of the constant
   * @return the simplified constant
   * Edited by Andy Werchniak: All constants must be positive, otherwise they are Negates
   */
  //no negative constants; all are negates
  public static Expression define(double value) {
  	if (value>=0)
  		return new Constant(value);
  	else return (Negate.make(new Constant(-value)));
  }
  
  /**
   * Evaluates this constant
   * @return the value of this constant
   */
  public double eval() {
    return myValue;
  }
  
  /**
   * Converts this constant to a string
   * @return the string representation of this constant
   */
  public String toString() {
    return "" + myValue;  // a sneaky way to convert a double to a String
  }
  
  /**
   * Take the derivative of this constant
   * @param v the variable with respect to which the derivative is taken (irrelevant)
   * @return the derivative of this constant, which is 0.0
   */
  public Expression deriv(String v) {
    return new Constant(0.0); // derivative of a constant is 0
  }
}
