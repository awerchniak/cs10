package SA8;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents expressions that are variables
 * You must use Variable.make to create an object of this class.
 * 
 * @author Scot Drysdale
 * @author Tom Cormen made many changes.
 */
public class Variable implements Expression { 
  private String myName;  // the name of this variable
  
  private static Map<String, Double> symbolTable = new HashMap<String, Double>();
  
  /**
   * Constructs this variable
   * @param name the name of the variable
   */
  private Variable(String name) {
    myName = name;
  }
  
  /**
   * Creates a variable, but only if there isn't one with the same name yet.
   * @param name the name of the variable
   * @param initial value for the variable
   * @return a reference to the variable
   */
  public static Variable define(String name, Double value) {
    if (symbolTable.containsKey(name))
      throw new MultiplyDefinedVariableException("Multiple definitions for variable " + name);
    else {
      symbolTable.put(name, value);
      return new Variable(name);
    }
  }
  
  /**
   * Creates a variable, but only if there isn't one with the same name yet.
   * @param name the name of the variable
   * @return a reference to the variable
   */
  public static Variable define(String name) {
    return define(name, null);
  }
  
  /**
   * Converts variable to a string
   * @return the variable name
   */
  public String toString() {
    return myName;
  }

  /**
   * Assign the variable the given value.
   * If previously assigned, replaces the old value.
   * @param variable the name of the variable
   * @param value the value to assign to it.
   */
  public static void assign(String variable, double value) {
    if (symbolTable.containsKey(variable))
      symbolTable.put(variable, value);
    else
      throw new UndefinedVariableException("Undefined variable: " + variable);
  }
  
  /**
   * Evaluates this variable using the value saved in symbolTable
   * @return the value of this variable
   */
  public double eval() {
    Double value = symbolTable.get(myName);
 
    if (value == null)
      throw new UnassignedVariableException("Evaluated unassigned variable " + myName);
    else
      return value;  // uses unboxing
  }
  
  /**
   * Take the derivative of this variable.
   * @param v the variable with respect to which the derivative is taken
   * @return the derivative or this variable with respect to the variable v
   */
  public Expression deriv(String v) {
    String myVar = toString();    // get the name of this variable
    
    if (v.equals(myVar))
      return Constant.define(1.0);
    else
      return Constant.define(0.0);
  }
}
