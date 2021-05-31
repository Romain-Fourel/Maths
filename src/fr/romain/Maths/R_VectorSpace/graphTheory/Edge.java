package fr.romain.Maths.R_VectorSpace.graphTheory;

public class Edge<V> {
	
	//the value of the edge
	double weight;

	//the vertex which the edge come from
	Vertex<V> prev;
	
	//the vertex which the edge go to
	Vertex<V> dest;
	
	public Edge(Vertex<V> prev,Vertex<V> dest,double weight) {
		this.prev = prev;
		this.dest = dest;
		this.weight = weight;
	}
	
	
	public double getWeight() {
		return weight;
	}


	public void setWeight(double weight) {
		this.weight = weight;
	}


	public Vertex<V> getPrev() {
		return prev;
	}


	public void setPrev(Vertex<V> prev) {
		this.prev = prev;
	}


	public Vertex<V> getDest() {
		return dest;
	}


	public void setDest(Vertex<V> dest) {
		this.dest = dest;
	}


	public static<V> Edge<V> connect(Vertex<V> prev,Vertex<V> dest){
		prev.connectTo(dest);
		return new Edge<V>(prev, dest, 1);
	}
	
	public static<V> Edge<V> bind(Vertex<V> v1,Vertex<V> v2){
		connect(v1, v2);
		connect(v2, v1);
		return new Edge<V>(v1, v2, 1);
	}
	
	
	public static<V> Edge<V> connect(Vertex<V> prev,Vertex<V> dest,double weight){
		prev.connectTo(dest, weight);
		return new Edge<V>(prev, dest, weight);
	}
	
	public static<V> Edge<V> bind(Vertex<V> v1,Vertex<V> v2,double weight){
		connect(v1, v2,weight);
		connect(v2, v1,weight);
		return new Edge<V>(v1, v2, weight);
	}
	
	
	public boolean isAdjacentTo(Edge<V> edge) {
		return prev==edge.dest || dest == edge.prev;
	}
	
	
}
