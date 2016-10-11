package Lab5;

import java.util.*;
import java.util.ArrayList;
import java.util.Map;
import net.datastructures.*;
import java.io.*;

/**
 * Kevin Bacon Game
 * Fills an undirected graph with edges as movies and vertices as actors
 * Uses Breadth First Search to fill tree and find shortest path to Kevin Bacon
 * @author Andy Werchniak and Rob Sayegh
 * Created 02/24/2016
 */
public class KevinBaconGame{
	private Map<Integer, String> actorMap, movieMap;
	private Map<Integer, ArrayList<Integer>> movieActorMap;
	private NamedAdjacencyMapGraph<String, String> baconGraph, bfsGraph;
	
	/**
	 * Constructor initializes instances variables
	 */
	public KevinBaconGame(){
		actorMap = new HashMap<Integer, String>();
		movieMap = new HashMap<Integer, String>();
		movieActorMap = new HashMap<Integer, ArrayList<Integer>>();
		baconGraph = new NamedAdjacencyMapGraph<String,String>(false);
		bfsGraph = new NamedAdjacencyMapGraph<String,String>(true);
	}
	
	/**
	 * read an input file and fill one of the three maps with it
	 * @param choice which map to fill
	 */
	private void fillMap(String choice){
		ArrayList<Integer> temp;
		String line, value;
		int num;
		File file;
		
		//these are hard coded because getFilePath() is super finicky
		if(choice.equals("actors"))
			file = new File("src/lab5/actors.txt");
		else if (choice.equals("movies"))
			file = new File("src/lab5/movies.txt");
		else if (choice.equals("movieactors"))
			file = new File("src/lab5/movie-actors.txt");
		else	file = null;
		
		try{
			Scanner scan = new Scanner(file);
		
			while(scan.hasNextLine()){
				line = scan.nextLine();
			
				//separate the number/value from the input file format
				num = Integer.parseInt(getNum(line));
				value = getName(line);
				
				//put the information into the appropriate map
				if(choice.equals("actors"))
					actorMap.put(num, value);
				else if (choice.equals("movies"))
					movieMap.put(num, value);
				else if (choice.equals("movieactors")){
					if(movieActorMap.containsKey(num))
						movieActorMap.get(num).add(Integer.parseInt(value));
					else{
						temp = new ArrayList<Integer>();
						temp.add(Integer.parseInt(value));
						movieActorMap.put(num,temp);
					}
				}
				else System.err.println("Invalid string input");
				
			}
			
			scan.close();
		} catch(FileNotFoundException e){
			System.err.println(e);
		}
	}
	
	/**
	 * Create the kevin bacon graph
	 */
	public void makeGraph(){
		int temp;
		ArrayList<Integer> list;
		
		//fill the actor, movie, and actor-movie maps
		fillMap("actors");
 		fillMap("movies");
 		fillMap("movieactors");
 		
 		Iterator<Integer> iter = actorMap.keySet().iterator();

 		//fill graph vertices
 		while(iter.hasNext()){
 			try{
 				baconGraph.insertVertex(actorMap.get(iter.next()));
 			} catch(IllegalArgumentException e){}
 		}
 		
 		//fill edges in graph
 		iter = movieActorMap.keySet().iterator();
 		while(iter.hasNext()){
 			temp = iter.next();
 			list = movieActorMap.get(temp);
 			for(int i=0;i<list.size();i++){
 				for(int j=0;j<list.size();j++){
 					try{
 						baconGraph.insertEdge(actorMap.get(list.get(i)), actorMap.get(list.get(j)), movieMap.get(temp));
 					} catch(IllegalArgumentException e){}
 				}
 			}
 		}
	}
	
	/**
	 * private helper method to get the number before the | from the input file
	 * @param line line of input
	 * @return the number in the file
	 */
	private String getNum(String line){
		String num = "";
		int i;
		for(i=0;line.charAt(i)!='|' && i<line.length(); i++){
			num+=line.charAt(i);
		}
		
		return num;
	}
	
	/**
	 * private helper method to get the value after the | from the input file
	 * @param line line of input
	 * @return the String after the |
	 */
	private String getName(String line){
		String name = "";
		int i;
		for(i=0;line.charAt(i)!='|' && i<line.length(); i++){
		}
		
		i++;	//omit the "|"
		
		while(i<line.length()){
			name+=line.charAt(i++);
		}
		
		return name;
	}
	
	/**
	 * Construct the breadth first search tree out of a directed NamedAdjacencyMapGraph
	 */
	public void makeTree(){
		//create a queue out of a sentinel doubly linked list
		SentinelDLL<String> queue = new SentinelDLL<String>();
		
		//make kevin bacon the root of our tree
		queue.add(baconGraph.getVertex("Kevin Bacon").getElement());
		bfsGraph.insertVertex(queue.getFirst());
		
		//construct the tree top down
		while (!queue.isEmpty()){
			String parent = queue.getFirst();
			//get an iterable collection of edges with the parent as the destination
			Iterable<Edge<String>> edges = baconGraph.incomingEdges(baconGraph.getVertex(parent));

			//take the first element out of the queue
			queue.remove();
						
			//iterate through the collection
			for (Edge<String> edge : edges){
				//the child is the vertex on the other side of the edge
				String child = baconGraph.opposite(baconGraph.getVertex(parent), edge).getElement();
				
				//if the child isn't already in the tree, insert it under its parent & push to the back of the queue
				if (!bfsGraph.vertexInGraph(child)){
					bfsGraph.insertVertex(child);
					bfsGraph.insertEdge(child, parent, edge.getElement());
					queue.addLast(child);
				}
			}
		}
	}
	
	/**
	 * Method to traverse the bfsGraph & find a particular actor's kevin bacon number
	 * @param actor
	 */
	public void traverseTree(String actor){
		//make sure the actor is a valid entry
		if (baconGraph.vertexInGraph(actor) && this.bfsGraph.vertexInGraph(actor)){
			
			//find the actor's vertex in the tree
			Vertex<String> v = bfsGraph.getVertex(actor);
			//create an iterator along an iterable collection of outgoing edges from the actor's vertex
			Iterator<Edge<String>> edgeIter = bfsGraph.outgoingEdges(v).iterator();
			
			String tempActor = actor;
			int count = 0;
			
			//traverse the path to kevin bacon and print it along the way, while incrementing the kevin bacon number
			while(edgeIter.hasNext()){
				Edge<String> e = edgeIter.next();
				v = bfsGraph.opposite(v, e);
				System.out.println(tempActor+" was in "+e.getElement()+" with "+v.getElement());
				tempActor = v.getElement();
				count += 1;
				edgeIter = bfsGraph.outgoingEdges(v).iterator();
			}
			
				System.out.println(actor+"'s Kevin Bacon number is "+count);
			
		}else if(baconGraph.vertexInGraph(actor) && !bfsGraph.vertexInGraph(actor)){
			System.out.println(actor+"'s Kevin Bacon number is infinity");
		}else{
			System.err.println(actor + " is not in the database.");
		}
	}
	
 /**
  * Driver for the game
  */
 	public static void main(String[] args){
 		KevinBaconGame test = new KevinBaconGame();
 		test.makeGraph();
 		test.makeTree();
// 		System.out.println(test.bfsGraph);
 		Scanner input = new Scanner(System.in);
 		String name;
 		char stop;
 		
 		do{
 			System.out.println("Enter the name of the actor to check. Spaces and capitals matter.");
 			name = input.nextLine();
 			test.traverseTree(name);
 			System.out.print("Continue? (y or n): ");
 			stop = input.nextLine().charAt(0);
 		} while (stop != 'n');
 		
 		System.out.println("Bye!");
 		input.close();
 	}
 	
}