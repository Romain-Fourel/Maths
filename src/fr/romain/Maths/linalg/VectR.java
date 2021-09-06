package fr.romain.Maths.linalg;

import fr.romain.Maths.linalg.algstruct.Field;
import fr.romain.Maths.utils.Reals;

public class VectR extends Vector<Double> {
	
	private final Field<Double> f = Field.realsField();
	
	public VectR(int n) {
		super(n);
	}

	public VectR(Double... values) {
		super(values);
	}
	
	public VectR plus(VectR v) {
		return (VectR) plus(v, f);
	}
	
	public VectR minus(VectR v) {
		return (VectR) minus(v, f);
	}
	
	public VectR times(double k) {
		return (VectR) times(k, f);
	}
	
	public double norm() {
		return Reals.norm2(getValues());
	}

}
