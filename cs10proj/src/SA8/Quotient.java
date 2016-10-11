package SA8;

/**
 * This class defines expressions that are quotients.
 * You must use Quotient.make to construct an instance of this class.
 * 
 * @author Scot Drysdale
 */
public class Quotient extends BinaryOp {
  /**
   * Construct the quotient: first / second
   * @param first the first expression
   * @param second the second expression 
   */
  private Quotient(Expression first, Expression second) {
    super(first, second);
  }
  
  /**
   * Creates the quotient of first and second, but simplifies if possible.
   * @param first the first expression
   * @param second the second expression
   * @return the simplified quotient
   */
  public static Expression make(Expression first, Expression second) {  
    if (first instanceof Constant && second instanceof Constant)
      return Constant.define(first.eval() / second.eval());
    else if ((first instanceof Constant && first.eval() == 0.0) ||
        (second instanceof Constant && second.eval() == 1.0))
      return first;
    //if it's a double negate, call Negate's make method which will return the two inner expressions
    //	to avoid double negate
    else if (first instanceof Negate && second instanceof Negate)
    	return new Quotient(Negate.make(first), Negate.make(second));
    else 
      return new Quotient(first, second);
  }

  /** 
   * Computes the quotient of the arguments.
   * @param firstOp the first operand
   * @param secondOp the second operand
   * @return the quotient of firstOp and secondOp
   */
  public double doOperation(double firstOp, double secondOp) {
    // A more practical version would handle division by 0.
    return firstOp / secondOp;
  }

  /**
   * Returns the symbol representing division.
   * @return string representing division
   */
  public String getOperator() {
      return  " / ";
  }
  
  /**
   * Take the derivative of this quotient.
   * @param v the variable with respect to which the derivative is taken
   * @return the derivative of this quotient with respect to the variable v
   */
  public Expression deriv(String v) {
    return Quotient.make(
          Difference.make(Product.make(getFirst().deriv(v), getSecond()),
                   Product.make(getFirst(), getSecond().deriv(v))),
          Product.make(getSecond(), getSecond()));
  }
}
