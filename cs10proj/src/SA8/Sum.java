package SA8;

/**
 * This class defines expressions that are sums.
 * You must use Sum.make to construct an instance of this class.
 * 
 * @author Scot Drysdale
 */
public class Sum extends BinaryOp {   
  /**
   * Construct the sum: first + second.
   * @param first the first expression
   * @param second the second expression 
   */
  private Sum(Expression first, Expression second) {
    super(first, second);
  }
  
  /**
   * Creates the sum of first and second, but simplifies if possible.
   * @param first the first expression
   * @param second the second expression
   * @return the simplified sum
   */
  public static Expression make(Expression first, Expression second) {    
    if (first instanceof Constant && second instanceof Constant)
      return Constant.define(first.eval() + second.eval());
    else if (first instanceof Constant && first.eval() == 0.0)
      return second;
    else if (second instanceof Constant && second.eval() == 0.0)
      return first;
    //no +-(expression), just -(expression)
    else if (second instanceof Negate)
    	return Difference.make(first, Negate.make(second));
    else 
      return new Sum(first, second);
  }
  
  /** 
   * Computes the sum of the arguments.
   * @param firstOp the first operand
   * @param secondOp the second operand
   * @return the sum of firstOp and secondOp
   */
  public double doOperation(double firstOp, double secondOp) {
    return firstOp + secondOp;
  }

  /**
   * Returns the symbol representing addition.
   * @return string representing addition
   */
  public String getOperator() {
      return  " + ";
  }
  
  /**
   * Take the derivative of this sum.
   * @param v the variable with respect to which the derivative is taken
   * @return the derivative of this sum with respect to the variable v
   */
  public Expression deriv(String v) {
    return Sum.make(getFirst().deriv(v), getSecond().deriv(v));
  }
}
