package cs10proj;
import java.text.DecimalFormat;

public class DigitalTimer {
	
	private int hour;	// hour variable
	private int min;	// minute variable
	private final static int HOUR_LIM = 24;	// reset hours after 24
	private final static int MIN_LIM = 60;	// reset minutes after 60
	private final static String FORMAT = "00";	// output times with two digits
	
	/**
	 * Creates a timer initialized to 00:00
	 */
	public DigitalTimer(){
		this.hour = 0;
		this.min = 0;
	}
	
	/**
	 * Creates a timer initialized to hours:minutes
	 * Precondition: 0 <= hours <= 23 and 0 <= minutes <= 59
	 *  
	 * @param hours value for hours part of timer
	 * @param minutes value of minutes part of timer
	 */
	public DigitalTimer(int hours, int minutes){
		this.hour = hours;
		this.min = minutes;
	}
	
	
	/**
	 * @return the time in hours:minutes format
	 */
	public String toString(){
		DecimalFormat fmt = new DecimalFormat(FORMAT);
		return fmt.format(this.hour) + ":" + fmt.format(this.min);
	}
	
	
	/**
	 * Increases the time by a minute, wrapping if necessary
	 */
	public void tick(){
		this.min++;
		if(this.min == MIN_LIM){
			this.min = 0;
			this.hour++;
			if(this.hour == HOUR_LIM)
				this.hour = 0;
		}
	}
	
	/**
	 * Sets the current time to hours:minutes.
	 * Precondition: 0 <= hours <= 23 and 0 <= minutes <= 59
	 * 
	 * @param hours value for hours part of timer 
	 * @param minutes value for minutes part of timer
	 */
	public void set(int hours, int minutes){
		this.hour = hours;
		this.min = minutes;
	}
}