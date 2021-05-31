package fr.romain.Maths.K_VectorSpace.graphTheory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.romain.Maths.K_VectorSpace.algebraicObjects.Matrix;
import fr.romain.Maths.K_VectorSpace.algebraicStructure.Algebra;
import fr.romain.Maths.K_VectorSpace.algebraicStructure.Field;

/**
 * This implementation of Graph can represent the most of kind of graphs.
 * Only multigraphs are not handled because an edge between two vertices is unique
 * 
 * Furthermore, a graph can be seen as a K-vector space, with K the type of edges weight
 *
 * @param <V> the informations put in the nodes
 * @param <K> the scalar set bound to the vector space of graphs (isomorphic to the vector space of matrices)
 */
public class Graph<V,K> {
	
	/**
	 * In order to get the place of the vertex in the graph, 
	 * we bind to each vertex an integer which will be its label in this particular graph
	 */
	Map<Vertex<V, K>, Integer> vertices;
	
	Algebra<K, Matrix<K>> alg;
	
	public Graph(Field<K> f) {
		vertices = new HashMap<Vertex<V,K>, Integer>();
		alg = Algebra.matricesAlgebra(0, f);
	}
	
	
	@SafeVarargs
	public Graph(Field<K> f,Vertex<V, K>... vertices) {
		alg = Algebra.matricesAlgebra(vertices.length, f);
		
		this.vertices = new HashMap<Vertex<V,K>, Integer>();
		for (Vertex<V, K> vertex : vertices) {
			this.addVertex(vertex);
		}
			
	}
	
	/**
	 * For this constructor, we assume that a vertex can be connected to an other vertex
	 * with an edge valuated by the <code>zero()</code> of the field chosen.<br>
	 * Therefore, to represent a lack of edge between two nodes, we will use the value <code>null</code>
	 * 
	 * Furthermore, the matrix represents connection from vertices in lines to vertices in columns
	 * For example, the matrix: ((0,1),(0,0)) represents the following graph: <br>
	 * a->b <br>
	 * And not : b->a <br>
	 * This adjacency matrix is thus actually a "post" matrix 
	 * @param f
	 * @param values
	 * @param adjMatrix
	 * @throws IllegalArgumentException
	 */
	public Graph(Field<K> f,List<V> values,Matrix<K> adjMatrix) throws IllegalArgumentException{
		
		this(f);
		
		if (values.size()!=adjMatrix.dimLines()) {
			throw new IllegalArgumentException("the list of values and the numbers of lines of the matrix has to be the same!");
		}
		if (!adjMatrix.isSquare()) {
			throw new IllegalArgumentException("The matrix has to be a square ! (the same nodes are represented in lines and in columns)");
		}
		
		values.forEach(v->{
			addVertex(new Vertex<>(v));
		});
		
		for (Vertex<V, K> vL : getVertices()) {
			for (Vertex<V, K> vC : getVertices()) {
				K weight = adjMatrix.get(get(vL), get(vC));
				
				if(weight!=null) {
					
				}
			}
		}
	}
	
	public void changeDim(int dim) {
		alg = Algebra.matricesAlgebra(dim, alg.field());
	}
	
	public boolean addVertex(Vertex<V, K> vertex) {
		
		//order() here is equal to order()-1 after added the vertex
		Integer oldValue = vertices.putIfAbsent(vertex, order());
		
		changeDim(order());
		
		return oldValue == null;
	}
	
	public void setVertices(Set<Vertex<V, K>> vertices) {
		this.vertices = new HashMap<Vertex<V,K>, Integer>();
		for (Vertex<V, K> vertex : vertices) {
			this.addVertex(vertex);
		}
	}
	
	
	public Set<Vertex<V, K>> getVertices() {
		return vertices.keySet();
	}
	
	public boolean contains(Vertex<V, K> vertex) {
		return vertices.containsKey(vertex);
	}
	
	public Set<Edge<V, K>> getEdges(){
		Set<Edge<V, K>> edges = new HashSet<Edge<V,K>>();
		
		for (Vertex<V, K> vertex : getVertices()) {
			for (Edge<V, K> edge : vertex.getOutGoingEdges()) {
				//we add the edge only if it bind the vertex to an other vertex IN the graph
				if (getVertices().contains(edge.getDest())) {
					edges.add(edge);
				}
			}
		}
		return edges;
	}
	
	public boolean contains(Edge<V, K> edge) {
		return getEdges().contains(edge);
	}
	
	/**
	 * Get the number associated of the vertex in the graph
	 * @param vertex
	 * @return <li> the number of the vertex in the graph
	 * 		   <li> null if the vertex is not in the graph
	 */
	public Integer get(Vertex<V, K> vertex) {
		return vertices.get(vertex);
	}
	
	/**
	 * 
	 * @param vertex : the vertex to remove
	 * @return <li> true if the vertex was in the graph
	 * <li> false otherwise
	 */
	public boolean remove(Vertex<V, K> vertex) {
		Integer place = vertices.remove(vertex);
		if (place == null)
			return false;
		for (Vertex<V, K> v : getVertices()) {
			if (get(v)>place) {
				vertices.put(v, get(v)-1);
			}
		}
		return true;
	}
	
	/**
	 * search and remove the vertex which have the place "place" in the graph
	 * @param place
	 * @return
	 */
	public boolean remove(int place) {
		for (Vertex<V, K> v : getVertices()) {
			if (get(v)==place)
				return remove(v);
		}
		return false;
	}

	/**
	 * the order of a graph is the number of vertices which contains it
	 * @return
	 */
	public int order() {
		return vertices.size();
	}
	
	/**
	 * The size of a graph is the number of edges which bind vertices in the graph
	 * @return
	 */
	public int size() {
		return getEdges().size();
	}
	
	/**
	 * 
	 * @return the adjacency matrix of the graph
	 */
	public Matrix<K> getAdjMatrix(){
		Matrix<K> adjMatrix = new Matrix<K>(order(),order());
		
		for (Vertex<V, K> v1 : getVertices()) {
			for (Vertex<V, K> v2 : getVertices()) {
				adjMatrix.set(get(v1), get(v2), v1.getWeightTo(v2));
			}
		}
		return adjMatrix;
	}
	
}









