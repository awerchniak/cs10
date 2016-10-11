package SA8;

/**
 * This class represents expressions that are differences of their operands.
 * You must use Difference.make to construct an instance of this class.
 * 
 * @author Scot Drysdale
 */
public class Difference extends BinaryOp {    
  /**
   * Construct the difference: first - second.
   * @param first the first expression
   * @param second the second expression 
   */
  private Difference(Expression first, Expression second) {
    super(first, second);
  }
  
  /**
   * Creates the difference of first and second, but simplifies if possible.
   * @param first the first expression
   * @param second the second expression
   * @return the simplified difference
   */
  public static Expression make(Expression first, Expression second) {
    if (first instanceof Constant && second instanceof Constant)
      return Constant.define(first.eval() - second.eval());
    else if (second instanceof Constant && second.eval() == 0.0)
      return first;
    //instead of --(expression), just +(expression)
    else if (second instanceof Negate)
    	return Sum.make(first, Negate.make(second));
    else
      return new Difference(first, second);
  }
  
  /** 
   * Computes the difference of the arguments.
   * @param firstOp the first operand
   * @param secondOp the second operand
   * @return the difference of firstOp and secondOp
   */
  public double doOperation(double firstOp, double secondOp) {
    return firstOp - secondOp;
  }

  /**
   * Returns the symbol representing subtraction.
   * @return string representing subtraction
   */
  public String getOperator() {
      return  " - ";
  }
  
  /**
   * Take the derivative of this difference.
   * @param v the variable with respect to which the derivative is taken
   * @return the derivative of this difference with respect to the variable v
   */
  public Expression deriv(String v)  {
    return Difference.make(getFirst().deriv(v), getSecond().deriv(v));
  }
}
