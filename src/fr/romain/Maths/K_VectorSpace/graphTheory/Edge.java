package fr.romain.Maths.K_VectorSpace.graphTheory;

/**
 * This class is not used to implement graphs or vertices. <br>
 * It is just used to work on them after having created
 * them
 * @author romain
 *
 * @param <V>
 * @param <K>
 */
public class Edge<V,K> {
	
	Vertex<V, K> prev;
	Vertex<V, K> dest;
	
	K weight;

	public Edge(Vertex<V, K> prev, Vertex<V, K> dest, K weight) {
		this.prev = prev;
		this.dest = dest;
		this.weight = weight;
	}

	public Vertex<V, K> getPrev() {
		return prev;
	}

	public void setPrev(Vertex<V, K> prev) {
		this.prev = prev;
	}

	public Vertex<V, K> getDest() {
		return dest;
	}

	public void setDest(Vertex<V, K> dest) {
		this.dest = dest;
	}

	public K getWeight() {
		return weight;
	}

	public void setWeight(K weight) {
		this.weight = weight;
	}
	
	public static<V,K> Edge<V, K> connect(Vertex<V, K> prev,Vertex<V, K> dest, K weight){
		prev.connectTo(dest, weight);
		return new Edge<V, K>(prev, dest, weight);
	}
	
	public static<V,K> Edge<V, K> bind(Vertex<V, K> v1,Vertex<V, K> v2,K weight){
		connect(v1, v2, weight);
		return connect(v2, v1, weight);
	}
	
	public boolean isAdjacentTo(Edge<V,K> edge) {
		return prev==edge.dest || dest == edge.prev;
	}
	
	/**
	 * An oriented edge means that the both vertices are only bound in one way,
	 * However, if the both vertices are bound in the two ways and the weight of the both edges are the same,
	 * so, the two oriented edges are equivalent to an non-oriented one <br>
	 * Given that we can build an edge which doesn't really exists yet, this function returns false if the edge
	 * simply doesn't exists.
	 * @return <li>true if the edge is oriented
	 * 		   <li>false if the edge is not oriented or doesn't exists
	 */
	public boolean isOriented() {
		
		//first, we test if the edge does really exists
		if(!prev.isConnectedTo(dest) && !dest.isConnectedTo(prev)) {
			return false;
		}
		//then, we test if the two vertices are bound in the two ways
		if (!(prev.isConnectedTo(dest) && dest.isConnectedTo(prev)))
			return false;
		/**
		 * And finally we test if the weights of those two edges are the same
		 * This implies that the user has to redefine the equal if K is a self-made type
		 */
		return prev.getWeightTo(dest).equals(dest.getWeightTo(prev));
	}
	
}
