package fr.romain.Maths.graphTheory;

import java.util.Map;

/**
 * This class represents a vertice in a graph
 *
 * @param <V> the type of the vertices values
 * @param <E> the type of the edges values
 */
public class Vertex<V,E> {

	//the value of the vertice
	V value;
	
	/**
	 * As values: all vertices which are connected (incoming edge) to this 
	 * As keys: the values of type E of the Edge which go from this to the other vertice.
	 */
	Map<Vertex<V, E>, E> neighbours;

}
