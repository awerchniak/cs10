package SA8;

/**
 * Thrown when a variable in an expression is unassigned.
 * 
 * @author Scot Drysdale
 */
public class UnassignedVariableException extends RuntimeException {
  public UnassignedVariableException (String message) {
    super (message);
  }
  public static final long serialVersionUID = 1L;
}
