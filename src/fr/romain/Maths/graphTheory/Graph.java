package fr.romain.Maths.graphTheory;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * This implementation of Graph can represent the most of kind of graphs.
 * Only multigraphs are not handled because an edge between two vertices is unique
 *
 * @param <V>
 * @param <E>
 */
public class Graph<V,E> {

	/**
	 * All vertices which are in the graph
	 * Connections between vertices are not defined here, but directly
	 * in each vertices objects
	 */
	Set<Vertex<V, E>> vertices;
	
	public Graph() {
		vertices = new LinkedHashSet<Vertex<V,E>>();
	}
	
	/**
	 * This constructors implies that vertices are already connected between each of them
	 * @param vertices
	 */
	@SafeVarargs
	public Graph(Vertex<V, E>... vertices) {
		this.vertices = new LinkedHashSet<Vertex<V, E>>();
		for (Vertex<V, E> vertex : vertices) {
			this.vertices.add(vertex);
		}
	}
	
	
	public Set<Vertex<V, E>> getVertices(){
		return vertices;
	}
	
	public void setVertices(Set<Vertex<V, E>> vertices) {
		this.vertices = vertices;
	}
	
	public Set<Edge<V, E>> getEdges(){
		
		
		
		return null;
	}
	
	/**
	 * 
	 * @param vertex: the vertex to add to the graph
	 * @return false if the vertex was already in the graph, true otherwise
	 */
	public boolean add(Vertex<V, E> vertex) {
		return vertices.add(vertex);
	}
	
	/**
	 * 
	 * @param vertex: the vertex to remove
	 * @return true if the vertex is inside the graph
	 */
	public boolean remove(Vertex<V, E> vertex) {
		return vertices.remove(vertex);
	}
	
	
	/**
	 * the order of a graph is the number of vertices which contains it
	 * @return
	 */
	public int order() {
		return vertices.size();
	}
	
	/**
	 * the size of a graph is the number of edges which contains this
	 * @return
	 */
	public int size() {
		//TODO
		return 0;
	}
	
}

