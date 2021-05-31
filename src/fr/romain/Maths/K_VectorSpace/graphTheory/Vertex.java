package fr.romain.Maths.K_VectorSpace.graphTheory;

import java.util.Set;

/**
 * This class represents a vertex in a graph
 *
 * @param <V> the type of the vertices values
 * @param <K> the type of the valued edge
 */
public class Vertex<V,K> {
	
	V value;
	
	Set<Vertex<V, K>> neighbours;
	
	public Vertex(V v) {
		value = v;
	}
	
}
