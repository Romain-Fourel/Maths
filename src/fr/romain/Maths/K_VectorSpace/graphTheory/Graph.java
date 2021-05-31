package fr.romain.Maths.K_VectorSpace.graphTheory;

import java.util.HashSet;
import java.util.List;
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
	
	Set<Vertex<V, K>> vertices;
	
	Algebra<K, Matrix<K>> alg;
	
	public Graph(Field<K> f) {
		vertices = new HashSet<Vertex<V,K>>();
		alg = Algebra.matricesAlgebra(0, f);
	}
	
	public boolean addVertex(Vertex<V, K> vertex) {
		boolean isAdded = vertices.add(vertex);
		
		//TODO: change the algebra dimension !
		
		return isAdded;
	}
	
	@SafeVarargs
	public Graph(Field<K> f,Vertex<V, K>... vertices) {
		this.vertices = new HashSet<Vertex<V,K>>();
		for (Vertex<V, K> vertex : vertices) {
			this.vertices.add(vertex);
		}
		
		alg = Algebra.matricesAlgebra(vertices.length, f);
	}
	
	
	public Graph(Field<K> f,List<V> values,Matrix<K> adjMatrix) throws IllegalArgumentException{
		
		this(f);
		
		if (values.size()!=adjMatrix.dimLines()) {
			throw new IllegalArgumentException("the list of values and the numbers of lines of the matrix has to be the same!");
		}
		
		values.forEach(v->{
			addVertex(new Vertex<>(v));
		});
	}
	
	
	
	
	public Set<Vertex<V, K>> getVertices() {
		return vertices;
	}

	public void setVertices(Set<Vertex<V, K>> vertices) {
		this.vertices = vertices;
	}

	/**
	 * the order of a graph is the number of vertices which contains it
	 * @return
	 */
	public int order() {
		return vertices.size();
	}
	
}









