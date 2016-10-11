package cs10proj;

/**
 * TimerDriver.java
 * A driver program to test the DigitalTimer class.
 * part of a short assignment

 * @author Scot Drysdale on 4/6/00.  Modified on 4/9/04, 12/31/11
 */
public class TimerDriver
{
	public static void main(String args[]) 
	{	
		DigitalTimer t1, t2;	 // Create variables that can reference two timers.
										
		t1 = new DigitalTimer();
		t2 = new DigitalTimer(9, 58);
		
		// Test the default constructor
		for (int i = 0; i < 5; i++)		
		{
			System.out.println(t1);									
			t1.tick();
		}
					
		// Test the other constructor, plus minute wrap-around
		
		System.out.println("\n\nTest minute wrap-around:");		
		
		for (int i = 0; i < 5; i++)		
		{
			System.out.println(t2);									
			t2.tick();
		}
		
		// Test reset, plus hour wrap-around
		
		System.out.println("\n\nTest hour wrap-around:");		
		t1.set(23, 58);
		
		for (int i = 0; i < 5; i++)		
		{
			System.out.println(t1);									
			t1.tick();
		}
	}
}