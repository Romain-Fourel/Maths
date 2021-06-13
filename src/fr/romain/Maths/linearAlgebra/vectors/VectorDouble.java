package fr.romain.Maths.linearAlgebra.vectors;

import fr.romain.Maths.geom2D.Vector2D;
import fr.romain.Maths.linearAlgebra.Reals;
import fr.romain.Maths.linearAlgebra.algebraicObjects.Vector;
import fr.romain.Maths.linearAlgebra.algebraicStructure.Field;

public class VectorDouble extends Vector<Double> {
	
	private final Field<Double> f = Field.realsField();
	
	public VectorDouble(int n) {
		super(n);
	}

	public VectorDouble(Double... values) {
		super(values);
	}
	
	public Vector2D plus(Vector2D v) {
		return (Vector2D) plus(v, f);
	}
	
	public Vector2D minus(Vector2D v) {
		return (Vector2D) minus(v, f);
	}
	
	public Vector2D times(double k) {
		return (Vector2D) times(k, f);
	}
	
	public double norm() {
		return Reals.norm2(getValues());
	}

}
