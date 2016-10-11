package SA8;

/**
 * This is an abstract class that defines expressions which are binary
 * operations (operators like + and * that act on two expressions).
 * 
 * @author Scot Drysdale
 */
abstract public class BinaryOp implements Expression {    
  // First and second operands for the binary expression.
  private Expression myFirst, mySecond;
  
  /** 
   * Construct a binary expression.
   * @param first the first operand
   * @param second the second operand
   */
  public BinaryOp(Expression first, Expression second) {
    myFirst = first;
    mySecond = second;
  }
  
  /**
   * Get the string representation of the operator.
   * @return a string representing the binary operator.
   */
  abstract public String getOperator();
  
  /**
   * Performs a binary operation.
   * 
   * @param first the first operand
   * @param second the second operand
   * @return the value obtained by applying the binary operation 
   *   to the two operands
   */
  abstract public double doOperation(double first, double second);
  
  /**
   * Convert expression to a string.
   * @return a string representing the expression
   */
  public String toString() {
    return "(" + myFirst.toString() + getOperator() 
        + mySecond.toString() + ")";
  }

  /**
   * Evaluate this binary operation.
   * Example of the template pattern - this is the template.
   * @return the value of this Expression.
   */
  public double eval() {
    return doOperation(myFirst.eval(), mySecond.eval());
  }
  
  /**
   * Get the first operand of this binary operation.
   * @return the first operand
   */
  public Expression getFirst() {
    return myFirst;
  }
  
  /**
   * Get the second operand of this binary operation.
   * @return the second operand
   */
  public Expression getSecond() {
    return mySecond;
  }
}
