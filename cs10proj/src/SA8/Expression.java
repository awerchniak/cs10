package SA8;

/**
 * This is an interface that specifies the operations (methods) that arithmetic 
 * expressions must be able to perform.
 * 
 * @author Scot Drysdale
 */    

public interface Expression { 
  /**
   * Evaluates this expression.
   * @return the value of this Expression
   */
  abstract public double eval();

  /**
   * Take the derivative of this expression.
   * @param v the variable with respect to which the derivative is taken
   * @return the derivative of this Expression with respect to the variable v
   */
  abstract public Expression deriv(String v); 
}
