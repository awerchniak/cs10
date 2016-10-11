package net.datastructures;

import java.util.Map;
import java.util.HashMap;

/**
 * Creates a HashMap of all of the vertices in an AdjacencyMap to speed up searching
 * @author andywerchniak
 *
 */
public class NamedAdjacencyMapGraph<V,E> extends AdjacencyMapGraph<V,E>{
	Map<V,Vertex<V>> namedMap;
	
	public NamedAdjacencyMapGraph(boolean directed){
		super(directed);
		namedMap = new HashMap<V, Vertex<V>>();
	}
	
	public Vertex<V> getVertex(V name){
		//returns the Vertex object corresponding to the name in the parameter, or null if there is no vertex with that name.
		return namedMap.get(name);
	}
	
	public boolean vertexInGraph(V name){
		return getVertex(name)!=null;
	}
	
	public Edge<E> insertEdge(V uName, V vName, E element) throws IllegalArgumentException{
		//inserts an edge whose vertices have the names uName and vName into the graph. Like the insertEdge method of AdjacencyMapGraph, it throws an IllegalArgumentException if there is already an edge (u, v) in the graph.
		Edge<E> edge = super.insertEdge(namedMap.get(uName), namedMap.get(vName), element);
		return edge;
	}
	
	public Edge<E> getEdge(V uName, V vName) throws IllegalArgumentException{
		//returns the edge whose endpoints are named by uName and vName, or null if the graph contains no such edge.
		return super.getEdge(namedMap.get(uName), namedMap.get(vName));
	}
	
	public Vertex<V> insertVertex (V element){
		Vertex<V> vertex = super.insertVertex(element);
		namedMap.put(element, vertex);
		return vertex;
	}
	
	public void removeVertex(Vertex<V> v) throws IllegalArgumentException{
		//override superclass method
	}
	
	//A testing method
	public static void main(String [] args) {
		boolean isDirected = false;
		NamedAdjacencyMapGraph<String, String> baconGraph;
 		
		do {
			baconGraph = new NamedAdjacencyMapGraph<String, String>(isDirected);

			System.out.println("\nisDirected = " + isDirected); 
 			
			baconGraph.insertVertex("Kevin Bacon");
			baconGraph.insertVertex("Laura Linney");
			baconGraph.insertVertex("Tom Hanks");
			baconGraph.insertVertex("Liam Neeson");
			baconGraph.insertEdge("Laura Linney","Kevin Bacon", "Mystic River");
			baconGraph.insertEdge("Liam Neeson", "Laura Linney", "Kinsey");
			baconGraph.insertEdge( "Tom Hanks", "Kevin Bacon", "Apollo 13");

			System.out.println("\nvertexInGraph for Laura Linney = " + 
				baconGraph.vertexInGraph("Laura Linney"));

			System.out.println("\nvertexInGraph for L. Linney = " + 
				baconGraph.vertexInGraph("L. Linney"));

			System.out.println("\ngetEdge between Laura Linney and Tom Hanks = " + 
    		baconGraph.getEdge("Laura Linney", "Tom Hanks"));
 			
			System.out.println("\ngetEdge between Laura Linney and Kevin Bacon = " + 
				baconGraph.getEdge("Laura Linney", "Kevin Bacon").getElement());
 			
			Edge<String> e = baconGraph.getEdge("Kevin Bacon", "Laura Linney");
			if(e == null)
				System.out.println("\nNo edge between Kevin Bacon and Laura Linney");
			else
				System.out.println("\ngetEdge between between Kevin Bacon and Laura Linney = " + 
					e.getElement()); 
 				

			System.out.println("\nInDegree of Laura Linney = " + 
				baconGraph.inDegree(baconGraph.getVertex("Laura Linney")));

			System.out.println("\nOutDegree of Laura Linney = " +
				baconGraph.outDegree(baconGraph.getVertex("Laura Linney")));

			System.out.println("\nEdges into to Laura Linney:");
			for(Edge<String> edge : baconGraph.incomingEdges(baconGraph.getVertex("Laura Linney"))) 
				System.out.println(edge.getElement());

			System.out.println("\nEdges out of to Laura Linney:");
			for(Edge<String> edge : baconGraph.outgoingEdges(baconGraph.getVertex("Laura Linney"))) 
				System.out.println(edge.getElement()); 

			System.out.println("\nThe entire graph:");
			for(Vertex<String> vertex : baconGraph.vertices()) {

				System.out.println("\nEdges into " + vertex.getElement() + ":");
				for(Edge<String> edge : baconGraph.incomingEdges(vertex)) 
					System.out.println(edge.getElement()); 

				System.out.println("\nEdges out of " + vertex.getElement() + ":");
				for(Edge<String> edge : baconGraph.outgoingEdges(vertex)) 
					System.out.println(edge.getElement());  				
			}  
			
			baconGraph.removeVertex(baconGraph.getVertex("Kevin Bacon"));
 			
			System.out.println("\nCalled removeVertex for Kevin Bacon");
			System.out.println("getVertex for Kevin Bacon returns: " +
				baconGraph.getVertex("Kevin Bacon"));
 			
			System.out.println("\nThe entire graph after Kevin Bacon removed:");
			for(Vertex<String> vertex : baconGraph.vertices()) {

				System.out.println("\nEdges into " + vertex.getElement() + ":");
				for(Edge<String> edge : baconGraph.incomingEdges(vertex)) 
					System.out.println(edge.getElement()); 

				System.out.println("\nEdges out of " + vertex.getElement() + ":");
				for(Edge<String> edge : baconGraph.outgoingEdges(vertex)) 
					System.out.println(edge.getElement());  				
			} 
			isDirected = !isDirected;
		} while(isDirected);
   
		try{
			baconGraph.insertVertex("Laura Linney");
		}
		catch(IllegalArgumentException ex) {
			System.out.println("\nCaught exception for duplicate vertex name: " +
				ex.getMessage());
		}
	}
}