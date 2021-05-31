package fr.romain.Maths.K_VectorSpace.graphTheory;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This class represents a vertex in a graph
 *
 * @param <V> the type of the vertices values
 * @param <K> the type of the valued edge
 */
public class Vertex<V,K> {
	
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
	
	/**
	 * Calculate the distance between two vertices
	 * @param vertex
	 * @return <li> the sum of all weight of edges which make the shortest way from this to vertex
	 * 		   <li> null if there is no way from this to vertex
	 */
	public K distanceTo(Vertex<V, K> vertex) {
		//TODO
		return null;
	}
	
}










