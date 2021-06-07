package fr.romain.Maths.linearAlgebra.vectors;

import fr.romain.Maths.geom2D.Vector2D;
import fr.romain.Maths.linearAlgebra.algebraicObjects.Vector;
import fr.romain.Maths.linearAlgebra.algebraicStructure.Euclidean;

public class VectorDouble extends Vector<Double> {
	
	private Euclidean<Vector<Double>> e;
	
	public VectorDouble(int n) {
		super(n);
		e = Euclidean.vectorsEuclidean(n);
	}

	public VectorDouble(Double... values) {
		super(values);
		e = Euclidean.vectorsEuclidean(dim());
	}
	
	public Vector2D plus(Vector2D v) {
		return (Vector2D) e.sum(this,v);
	}
	
	public Vector2D minus(Vector2D v) {
		return (Vector2D) e.minus(this, v);
	}
	
	public Vector2D times(double k) {
		return (Vector2D) e.times(k, this);
	}
	
	public double norm() {
		return e.norm(this);
	}

}
