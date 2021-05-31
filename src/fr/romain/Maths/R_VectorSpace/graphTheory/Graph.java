package fr.romain.Maths.R_VectorSpace.graphTheory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.romain.Maths.R_VectorSpace.linearAlgebra.Matrix;

/**
 * This implementation of Graph can represent the most of kind of graphs.
 * Only multigraphs are not handled because an edge between two vertices is unique
 * 
 * Furthermore, a graph can be seen as a K-vector space, with K the type of edges weight
 * We have thus chosen here to make graphs which are R-vector spaces
 *
 * @param <V>
 */
public class Graph<V> {

	/**
	 * All vertices which are in the graph
	 * Connections between vertices are not defined here, but directly
	 * in each vertices objects
	 */
	Set<Vertex<V>> vertices;
	
	public Graph() {
		vertices = new HashSet<Vertex<V>>();
		
	}
	
	/**
	 * 
	 * @param v
	 * @return true if v was not already in the graph, false otherwise
	 */
	public boolean addVertex(Vertex<V> v) {
		v.setLabel(vertices.size());
		return vertices.add(v);
	}
	
	/**
	 * This constructors implies that vertices are already connected between each of them
	 * @param vertices
	 */
	@SafeVarargs
	public Graph(Vertex<V>... vertices) {
		this.vertices = new HashSet<Vertex<V>>();
		for (Vertex<V> vertex : vertices) {
			addVertex(vertex);
		}
	}
	
	/**
	 * This constructors build the graph along with the vertices which will contains it
	 * The adjacency between vertices is defined by the adjacency matrix
	 * @param vertices
	 * @param adjacency
	 */
	public Graph(List<V> values,double[][] adjacency) throws IllegalArgumentException{
		this(values, new Matrix(adjacency));
	}
	
	/**
	 * This constructors build the graph along with the vertices which will contains it
	 * The adjacency between vertices is defined by the adjacency matrix
	 * @param vertices
	 * @param adjMatrix
	 */
	public Graph(List<V> values,Matrix adjMatrix) throws IllegalArgumentException{
		
		if (values.size()!=adjMatrix.dimLine()) {
			throw new IllegalArgumentException("the list of values and the numbers of lines of the matrix has to be the same!");
		}
		
		vertices = new HashSet<Vertex<V>>();
		
		values.forEach((value)->{
			Graph.this.addVertex(new Vertex<V>(value));
		});
		
		for (Vertex<V> v1: vertices) {
			for (Vertex<V> v2 : vertices) {
				
				if(adjMatrix.get(v1.getLabel(), v2.getLabel())>0) {
					Edge.connect(v1, v2, adjMatrix.get(v1.getLabel(), v2.getLabel()));
				}
				
			}
		}
	}
	
	
	public Set<Vertex<V>> getVertices(){
		return vertices;
	}
	
	public void setVertices(Set<Vertex<V>> vertices) {
		this.vertices = vertices;
	}
	
	public Set<Edge<V>> getEdges(){	
		Set<Edge<V>> edges = new HashSet<Edge<V>>();
		
		for (Vertex<V> vertex : vertices) {

			for (Edge<V> edge : vertex.getOutEdges()) {
				if (vertices.contains(edge.getDest())) {
					edges.add(edge);
				}
			}
		}
		return edges;
	}
	
	public Matrix getAdjacencyMatrix() {
		Matrix adjMatrix = Matrix.zero(order());
		
		for (Vertex<V> vertex : vertices) {	
			for (Vertex<V> vertex2 : vertices) {
				
				if(vertex.isConnectedTo(vertex2)) {
					adjMatrix.set(vertex.getLabel(), vertex2.getLabel(), 1);
				}
			}
		}
		
		return adjMatrix;
	}
	
	
	/**
	 * 
	 * @param vertex: the vertex to remove
	 * @return true if the vertex is inside the graph
	 */
	public boolean remove(Vertex<V> vertex) {
		int label = vertex.getLabel();
		
		for (Vertex<V> vertex2 : vertices) {
			if(vertex2.getLabel()>label) {
				vertex2.setLabel(vertex2.getLabel()-1);
			}
		}
		
		return vertices.remove(vertex);
	}
	
	public boolean remove(int vertexLabel) {
		Vertex<V> toRemove = new Vertex<V>(null);
		toRemove.setLabel(vertexLabel);
		return remove(toRemove);
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
		return getEdges().size();
	}
	
}

