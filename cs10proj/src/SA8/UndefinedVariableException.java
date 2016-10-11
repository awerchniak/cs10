package SA8;

/**
 * Thrown when a variable in an expression is undefined.
 * 
 * @author Tom Cormen
 */
public class UndefinedVariableException extends RuntimeException {
  public UndefinedVariableException (String message) {
    super (message);
  }
  public static final long serialVersionUID = 1L;
}
