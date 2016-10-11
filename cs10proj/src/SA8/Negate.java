package SA8;
/**
 * Unary negate operator to go with the classes that implement Expression
 * @author andywerchniak
 *
 */
public class Negate implements Expression{
	//one instance variable (the expression to make negative)
	private Expression express;
	
	//private constructor
	private Negate(Expression in){
		this.express = in;
	}
	
	/**
	 * Make method to call constructor
	 * @param in Expression to negate
	 * @return either a negate object or the expression of the object put in (avoid double negate)
	 */
	public static Expression make(Expression in){
		if(in instanceof Negate){
			return ((Negate)in).express;
		}
		else return new Negate(in);
	}
	
	@Override//evaluate the expression and return the negative
	public double eval() {
		 return -express.eval();
	}

	@Override//the derivative of a Negate will be the Negate of the derivative
	public Expression deriv(String v) {
		return Negate.make(express.deriv(v));
	}
	
	public String toString(){
		return ""+eval();
	}
	
}