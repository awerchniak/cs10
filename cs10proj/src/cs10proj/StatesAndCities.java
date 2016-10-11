package cs10proj;

/**
 * Demonstrates the use of a Map to associate states with sets of cities in 
 *   each state.
 * Inspired by an example in the CS AP "acorn" book.
 * 
 * This version has method bodies removed as part of a short assignment.
 * 
 * @author Scot Drysdale
 */

import java.util.*;

public class StatesAndCities
{
	// Maps state names to sets of cities.
	private Map<String, Set<String>> stateCityMap;  
	Set<String> states;
	
	/**
	 * Constructs empty map
	 */
	public StatesAndCities()
	{
		stateCityMap = new HashMap<String, Set<String>>();
		states = stateCityMap.keySet();
	}

	/**
	 * Adds a state/city pair to the atlas.
	 * @param state the state to add to
	 * @param city the city to add
	 */
	public void addPair(String state, String city)
	{
		
		//if the state is already on the map, just add to its set of cities
		if (states.contains(state)){
			stateCityMap.get(state).add(city);
		}
		
		//if not, add the state to the map and add the city to a new set
		else{
			stateCityMap.put(state, new HashSet<String>());
			stateCityMap.get(state).add(city);
		}
	}


	/**
	 * Is the city is associated with the state in the map
	 * @param state the state to look for
	 * @param city the city to look for
	 * @return true if city is in state
	 */
	public boolean isCityInState(String state, String city)
	{
		//first check if the state exists, then see if the city is in there
		if(stateCityMap.containsKey(state))
			return stateCityMap.get(state).contains(city);
		else return false;
	}

	/**
	 * Returns a string describing the entire map
	 */
	public String toString()
	{
		Iterator<String> iter1, iter2;
		String out = new String();
		String state, city;
		
		if (stateCityMap.isEmpty())
      System.out.println("The map is empty");
    else {
      
    	//nexted loops, use iterators for states and cities
      iter1 = states.iterator();
      
      while (iter1.hasNext()) {
      	state = iter1.next();
        out+=state + ": ";
        
        //set iter2 to be an iterator in the set mapped to that state
        iter2 = stateCityMap.get(state).iterator();
        
        while(iter2.hasNext()){
        	city = iter2.next();
        	out+= city + " ";
        }
        //only print new line if it's not the last one
        if(iter1.hasNext())
        	out += "\n";
      }
    }
		return out;
	}


	/**
	 * For testing purposes
	 */
	public static void main(String args[])
	{
		StatesAndCities atlas = new StatesAndCities();  // Hold the state/city data
		char command = ' ';                 // a command
		String state;                       // a state name
		String city;					      				// a city name
		Scanner input = new Scanner(System.in);

		while (command != 'q') 
		{
			System.out.print("Command (q, a, i, P, ?): ");
			command = input.nextLine().charAt(0);

			switch (command) 
			{
			case 'q':                   // Quit
				System.out.println("Bye");
				break;

			case 'a':                   // add a city/state pair
				System.out.print("Enter state: ");
				state = input.nextLine();
				System.out.print("Enter city in the state: ");
				city = input.nextLine();
				atlas.addPair(state, city);
				break;

			case 'i':                   // isCityInState
				System.out.print("Enter state: ");
				state = input.nextLine();
				System.out.print("Enter city that might be in the state: ");
				city = input.nextLine();

				if(atlas.isCityInState(state, city))
					System.out.println(city + " is in  " + state);
				else
					System.out.println(city + " is not listed as being in " + state);
				break;


			case 'P':                   // print
				System.out.println(atlas);
				break;

			case '?':                   // Print all the commands
				System.out.println("Commands are\n  q: quit\n  a: addPair\n  i: isCityInState\n  " +
				"P: print\n  ");
				break;

			default:
				System.out.println("Huh?");
			}
		}
	}
}