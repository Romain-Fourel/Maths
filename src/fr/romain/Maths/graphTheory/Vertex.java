package fr.romain.Maths.graphTheory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.romain.Maths.linearAlgebra.algebraicStructure.Field;

/**
 * This class represents a vertex in a graph
 *
 * @param <V> the type of the vertices values
 * @param <K> the type of the valued edge
 */
public class Vertex<V,K extends Comparable<K>> {
	
	/**
	 * The information stored in the vertex
	 */
	V value;
	
	/**
	 * This maps contains as values all vertices for which the current vertex is bound to them
	 * by an outgoing edge. The values of this map are the weight of those edges.
	 */
	Map<Vertex<V, K>, K> neighbours;
	
	public Vertex(V v) {
		value = v;
	}
	
	public Set<Vertex<V, K>> getNeighbours(){
		return neighbours.keySet();
	}
	
		
	public boolean connectTo(Vertex<V, K> vertex,K weight) {
		return neighbours.putIfAbsent(vertex, weight)==null;
	}
	
	public boolean isConnectedTo(Vertex<V,K> vertex) {
		return neighbours.get(vertex)!=null;
	}
	
	
	public Set<Edge<V, K>> getOutGoingEdges(){
		Set<Edge<V, K>> edges = new HashSet<Edge<V,K>>();
		for (Vertex<V, K> neighbor : getNeighbours()) {
			edges.add(new Edge<>(this, neighbor, getWeightTo(neighbor)));
		}
		return edges;
	}
	
	/**
	 * @param vertex
	 * @return <li>null if there is no edge between this and vertex
	 * 		   <li>the weight of the edge which out go from this and income to vertex
	 */
	public K getWeightTo(Vertex<V, K> vertex) {
		return neighbours.get(vertex);
	}
	
	public boolean isLeaf() {
		return neighbours.isEmpty();
	}
	
	/**
	 * WARNING: the list is reversed, the arrival is at the beginning and the first at the end
	 * @param <V>
	 * @param <K>
	 * @param list
	 * @param f
	 * @return <li>null if the list given is null
	 * @throws IllegalArgumentException
	 */
	public static<V,K extends Comparable<K>> K pathLength(List<Vertex<V, K>> list, Field<K> f) 
			throws IllegalArgumentException{
		
		if(list==null) {
			return null;
		}
		
		K length = f.zero();
		
		for (int i = list.size()-1; i >0 ; i--) {
			K weight = list.get(i).getWeightTo(list.get(i-1));
			if (weight==null) {
				throw new IllegalArgumentException("The list gave in argument doesn't represents a path");
			}
			length = f.sum(length, weight);
		}
		return length;
	}
	
	
	public List<Vertex<V,K>> shortestPathTo(Vertex<V,K> v,Field<K> f){
		return this.shortestPathRec(null,new ArrayList<Vertex<V,K>>(), v,f);
	}
	
	/**
	 * If the function returns null, that means that there is no path from this vertex to the arrival
	 * @param blackList
	 * @param arrival
	 * @param f
	 * @return
	 */
	protected List<Vertex<V, K>> shortestPathRec(Graph<V, K> graph, List<Vertex<V, K>> blackList, Vertex<V, K> arrival, Field<K> f){
		blackList.add(this);
		
		if(getNeighbours().contains(arrival)) {
			return List.of(arrival,this);
		}
			
		List<Vertex<V, K>> shortestPath = null;
		K min = null;
		
		for (Vertex<V, K> vertex : getNeighbours()) {
			
			if(graph!=null && !graph.contains(vertex))
				continue;
			
			if(blackList.contains(vertex)) {
				continue;
			}
			
			//calculation of the shortest path for each neighbours
			List<Vertex<V,K>> path = vertex.shortestPathRec(graph,blackList, arrival, f);
			K length = pathLength(path, f);
			
			if(length == null)
				continue;
			
			//to initialize the comparative process
			if(shortestPath == null) {
				min = length;
				shortestPath = path;
			}
			//if the length is shortest than the last shortest, we take this path
			else if (length.compareTo(min)<0) {
				min = length;
				shortestPath = path;
			}
		}
		if(shortestPath != null)
			shortestPath.add(this);
		return shortestPath;
	}
	
	
	/**
	 * Calculate the distance from this to vertex
	 * WARNING: because of the graph is oriented, d(a,b) =/= d(b,a)
	 * @param vertex
	 * @return <li> the sum of all weight of edges which make the shortest way from this to vertex
	 * 		   <li> null if there is no way from this to vertex
	 */
	public K distanceTo(Vertex<V, K> vertex,Field<K> f) {
		return pathLength(shortestPathTo(vertex,f), f);
	}
	
}










