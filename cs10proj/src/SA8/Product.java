package SA8;

/**
 * This defines expressions that are products.
 * You must use Product.make to construct an instance of this class.
 *
 * @author Scot Drysdale

 */
public class Product extends BinaryOp {   
  /**
   * Construct the product: first * second.
   * @param first the first expression
   * @param second the second expression 
   */
  private Product(Expression first, Expression second) {
    super(first, second);
  }
  
  /**
   * Creates the product of first and second, but simplifies if possible.
   * @param first the first expression
   * @param second the second expression
   * @return the simplified product
   */
  public static Expression make(Expression first, Expression second) {
    if (first instanceof Constant && second instanceof Constant)
      return Constant.define(first.eval() * second.eval());
    else if ((first instanceof Constant && first.eval() == 0.0) ||
        (second instanceof Constant && second.eval() == 1.0))
      return first;
    else if ((second instanceof Constant && second.eval() == 0.0) ||
        (first instanceof Constant && first.eval() == 1.0))
      return second;
    //no -this*-that, just this*that
    else if (first instanceof Negate && second instanceof Negate)
    	return new Product(Negate.make(first), Negate.make(second));
    else 
      return new Product(first, second);
  }
  
  /** 
   * Computes the product of the arguments.
   * @param firstOp the first operand
   * @param secondOp the second operand
   * @return the product of firstOp and secondOp
   */
  public double doOperation(double firstOp, double secondOp) {
    return firstOp * secondOp;
  }

  /**
   * Returns the symbol representing multiplication.
   * @return string representing multiplication
   */
  public String getOperator() {
    return  " * ";
  }

  /**
   * Take the derivative of this product.
   * @param v the variable with respect to which the derivative is taken
   * @return the derivative of this product with respect to the variable v
   */
  public Expression deriv(String v) {
    return Sum.make(Product.make(getFirst(), getSecond().deriv(v)),
             Product.make(getFirst().deriv(v), getSecond()));
  }
}
