package SA8;

/**
 * Thrown when a variable is defined after the first time.
 * 
 * @author Tom Cormen
 */
public class MultiplyDefinedVariableException extends RuntimeException {
  public MultiplyDefinedVariableException (String message) {
    super (message);
  }
  public static final long serialVersionUID = 1L;
}
