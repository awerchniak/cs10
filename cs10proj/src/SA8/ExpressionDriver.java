package SA8;

/**
 * A driver program to illustrate the use of the Expression interface.
 * A parser that would read expressions and parse them would be nicer, but
 *   the complications would hide the point of this example.
 * 
 * @author Scot Drysdale
 * @author Tom Cormen made many changes.
 */
public class ExpressionDriver {  
  public static void main(String[] args) {  
  
    Variable xVar = define("x", 2.0);
    Variable yVar = define("y", 6.0);

    System.out.println("Variable assignments " + xVar + " = " + xVar.eval()
        + ", " + yVar + " = " + yVar.eval());
    
    Expression first = plus(constant(17.5), over(constant(5.0), xVar));
        
    System.out.println("The value of the expression:");
    System.out.println(first + " = " + first.eval());     
    System.out.println();
    
    Expression second = minus(yVar, constant(4.0));
    System.out.println("The value of the expression:");
    System.out.println(second + "  = " + second.eval());    
    System.out.println();
    
    Expression third = times(first, second);
    System.out.println("The value of their product:");
    System.out.println(third + "  = " + third.eval());  
    System.out.println();
    
    Expression fourth = times(plus(constant(17.5), over(constant(5.0), xVar)),
        minus(yVar, constant(4.0)));
    System.out.println("The value of their product again:");
    System.out.println(fourth + "  = " + fourth.eval());  
    System.out.println();
    
    assign("x", -1.25);
    assign("y", 14.0);
    System.out.println("Variable assignments " + xVar + " = " + xVar.eval()
        + ", " + yVar + " = " + yVar.eval());
    
    System.out.println("The value of their product:");
    System.out.println(third + "  = " + third.eval());  
    System.out.println();
    
    System.out.print("The derivative with respect to y of ");
    System.out.print(second);  
    System.out.println(" equals " + second.deriv("y"));
    System.out.println();
        
    System.out.print("The derivative with respect to x of ");
    System.out.print(first);  
    System.out.println(" equals " + first.deriv("x")); 
    System.out.println();
        
    System.out.print("The derivative with respect to x of ");
    System.out.print(third);  
    System.out.println(" equals " + third.deriv("x")); 
    System.out.println();
    
    Variable zVar = define("z");
    
    // Examples of try-catch.
    try {
      System.out.println(zVar.eval());
    }
    catch (UnassignedVariableException ex) {
      System.err.println(ex.getMessage());
    }
    
    try {
      assign("bozo", 7.5);
    }
    catch (UndefinedVariableException ex) {
      System.err.println(ex.getMessage());
    }
    
    try {
      Variable anotherXVar = define("x");
    }
    catch (MultiplyDefinedVariableException ex) {
      System.err.println(ex.getMessage());
    }
        
    System.out.println("The second derivative with respect to x of " + third  + " equals " + (third.deriv("x")).deriv("x"));
  }

  // Wrappers for the static methods in other classes.
  public static Variable define(String variable, Double value) {
    return Variable.define(variable, value);
  }
  
  public static Variable define(String variable) {
    return Variable.define(variable);
  }
  
  public static void assign(String variable, Double value) {
    Variable.assign(variable, value);
  }
  
  public static Expression constant(double value) {
    return Constant.define(value);
  }

  public static Expression plus(Expression first, Expression second) {
    return Sum.make(first, second);
  }
  
  public static Expression minus(Expression first, Expression second) {
    return Difference.make(first, second);
  }
  
  public static Expression times(Expression first, Expression second) {
    return Product.make(first, second);
  }

  public static Expression over(Expression first, Expression second) {
    return Quotient.make(first, second);
  }
  
  public static Expression negate(Expression in){
  	return Negate.make(in);
  }
}
