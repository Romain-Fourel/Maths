package fr.romain.Maths.R_VectorSpace.graphTheory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This class represents a vertex in a graph
 *
 * @param <V> the type of the vertices values
 */
public class Vertex<V> {
	
	//To recognize the vertex in a graph and in its adjacency matrix
	int label;

	//the value of the vertex
	V value;
	
	/**
	 * As values: all vertices which are connected (incoming edge) to this 
	 * As keys: the values of type E of the Edge which go from this to the other vertex.
	 */
	Map<Vertex<V>, Double> neighbours;
	
	public Vertex(V value) {
		neighbours = new HashMap<Vertex<V>, Double>();
		this.value = value;
	}
	
	public void setLabel(int l) {
		label = l;
	}
	
	public int getLabel() {
		return label;
	}
	
	public Set<Vertex<V>> getNeighbours(){
		return neighbours.keySet();
	}
	
	/**
	 * Get all outgoing edges
	 */
	public Set<Edge<V>> getOutEdges() {
		Set<Edge<V>> edges = new HashSet<Edge<V>>();
		
		System.out.println("#################### for "+this+" ###################\n");
		
		System.out.println(getNeighbours());
		System.out.println(neighbours);
		
		for (Vertex<V> v : getNeighbours()) {
			System.out.println(v);
			System.out.println(neighbours.get(v));
			edges.add(new Edge<V>(this, v, neighbours.get(v)));
		}
		return edges;
	}
	
	
	
	public void connectTo(Vertex<V> vertex) {
		connectTo(vertex, 1);
	}
	
	public void connectTo(Vertex<V> vertex,double weight) {
		neighbours.put(vertex, weight);
	}
	
	public boolean isConnectedTo(Vertex<V> vertex) {
		return neighbours.get(vertex)!=null;
	}
	
	@Override
	public int hashCode() {
		return label;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		try {
			return label == ((Vertex<V>)obj).label;
		} catch (Exception e) {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return label+"("+value+")";
	}

}
