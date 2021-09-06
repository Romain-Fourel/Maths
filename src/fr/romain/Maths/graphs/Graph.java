package fr.romain.Maths.graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.romain.Maths.linalg.algebraicObjects.Matrix;
import fr.romain.Maths.linalg.algebraicStructure.Algebra;
import fr.romain.Maths.linalg.algebraicStructure.Field;

/**
 * This implementation of Graph can represent the most of kind of graphs.
 * Only multigraphs are not handled because an edge between two vertices is unique
 * 
 * Furthermore, a graph can be seen as a K-vector space, with K the type of edges weight
 *
 * @param <V> the informations put in the nodes
 * @param <K> the scalar set bound to the vector space of graphs (isomorphic to the vector space of matrices)
 */
public class Graph<V,K extends Comparable<K>> {
	
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
		this(f);
		alg = Algebra.matricesAlgebra(vertices.length, f);
		for (Vertex<V, K> vertex : vertices) {
			this.addVertex(vertex);
		}
			
	}
	
	/**
	 * Create a graph which will contains all vertices connected to v
	 * @param <V> 
	 * @param <K>
	 * @param v the root of the graph
	 * @return
	 */
	public static<V,K extends Comparable<K>> Graph<V, K> from(Vertex<V, K> v,Field<K> f){
		Graph<V, K> graph = new Graph<V, K>(f);
		
		for (Vertex<V, K> neighbor : v.getNeighbours()) {
			graph.addVertices(Graph.from(neighbor, f).getVertices());
		}
		return graph;
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
		
		if (values.size()!=adjMatrix.dimRows()) {
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
	
	/**
	 * 
	 * @return the undirected graph which is assiocated to this one, if the graph is already undirected, returns a copy of it
	 */
	public Graph<V, K> toUndirected(){
		Graph<V, K> undirectedGraph = new Graph<>(alg.field());
		//TODO
		return null;
	}
	

	
	public boolean addVertex(Vertex<V, K> vertex) {
		
		//order() here is equal to order()-1 after added the vertex
		Integer oldValue = vertices.putIfAbsent(vertex, order());
		
		changeDim(order());
		
		return oldValue == null;
	}
	
	public void addVertices(Set<Vertex<V, K>> vertices) {
		for (Vertex<V, K> vertex : vertices) {
			addVertex(vertex);
		}
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
	 * @param v
	 * @return if the vertex is a leaf for the graph
	 * Therefore, outgoing edges from v to a vertex which is not in the graph are ignored
	 */
	public boolean hasLeaf(Vertex<V, K> v) {
		if (v.isLeaf())
			return true;
		for (Vertex<V, K> neighbor : v.getNeighbours()) {
			if (contains(neighbor))
				return false;
		}
		return true;
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
	
	/**
	 * calculate the shortest path between v1 and v2 using the path in the graph
	 * @param v1
	 * @param v2
	 * @return <li>null if there is no path between those two vertices or if v2 is not in the graph
	 *         <li> the reversed list of the path (from the arrival to the beginning)
	 */
	public List<Vertex<V, K>> shortestPath(Vertex<V, K> v1, Vertex<V, K> v2) throws IllegalArgumentException{
		if (!contains(v1))
			throw new IllegalArgumentException("WARNING: your first vertex is not in the graph");
		if(!contains(v2))
			return null;
		return v1.shortestPathRec(this, new ArrayList<Vertex<V,K>>(), v2, alg.field());
	}
	
	
	public K distance(Vertex<V, K> v1, Vertex<V, K> v2) {
		return Vertex.pathLength(shortestPath(v1, v2), alg.field());
	}
	
	public boolean isOriented() {

		for (Edge<V, K> edge : getEdges()) {
			if(!edge.isOriented())
				return false;
		}
		return true;
		
	}
	
	/**
	 * Calculate the excentricity of the vertex v in the current graph
	 * The excentricity of a vertex is the maximum of all distances with other vertices in the graph
	 * @param v
	 * @return
	 */
	public K excentricityOf(Vertex<V, K> v) {
		
		K max = null;
		
		for (Vertex<V, K> vertex : getVertices()) {
			K dist = distance(v, vertex);
			if(max == null || dist.compareTo(max)>0)
				max = dist;
		}
		return max;
	}
	
	/**
	 * The diameter of a graph is the maximum excentricity
	 * @return the diameter of the graph
	 */
	public K diameter() {
		
		K diameter = null;
		
		for (Vertex<V, K> vertex : getVertices()) {
			K excentricity = excentricityOf(vertex);
			if (diameter == null || excentricity.compareTo(diameter)>0)
				diameter = excentricity;
		}
		return diameter;
	}
	
	/**
	 * The radius of a graph is the minimum excentricity
	 * @return the radius of the graph
	 */
	public K radius() {
		
		K radius = null;
		
		for (Vertex<V, K> vertex : getVertices()) {
			K excentricity = excentricityOf(vertex);
			if (radius == null || excentricity.compareTo(radius)<0)
				radius = excentricity;
		}
		return radius;
	}
	
	
	public List<Vertex<V, K>> center(){
		K radius = radius();
		List<Vertex<V, K>> center = new ArrayList<Vertex<V,K>>();
		
		for (Vertex<V, K> vertex : getVertices()) {
			if(excentricityOf(vertex).compareTo(radius)==0) {
				center.add(vertex);
			}
		}
		return center;
	}
	
	
	public boolean isConnected() {
		//TODO
		return false;
	}
	
	public boolean isCircular() {
		//TODO
		return false;
	}
	
	
}









