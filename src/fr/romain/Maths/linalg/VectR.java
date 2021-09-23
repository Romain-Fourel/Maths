package fr.romain.Maths.linalg;

import fr.romain.Maths.linalg.algstruct.Field;
import fr.romain.Maths.utils.Reals;

public class VectR extends Vector<Double>{
	
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
	
	public VectR dot(double k) {
		return (VectR) times(k, f);
	}
	
	public double norm2() {
		return Reals.norm2(getValues());
	}
	
	public double max() {
		return Reals.normInf(getValues());
	}
	
	public double min() {
		double min = get(0);
		for (int i = 0; i < dim(); i++) {
			min = Double.min(min, get(i));
		}
		return min;
	}
	
	public double sum() {
		return Reals.sum(getValues());
	}
	
	public double mean() {
		return sum()/dim();
	}
	
	public VectR elmtWiseInv() {
		VectR inv = new VectR(dim());
		for (int i = 0; i < dim(); i++) {
			inv.set(i, 1./get(i));
		}
		return inv;
	}
	
	public VectR elmtWiseProd(VectR v) {
		
		if(dim()!=v.dim()) {
			throw new NotSameDimensionsException(dim(), v.dim());
		}
		
		VectR prod = new VectR(dim());
		for (int i = 0; i < dim(); i++) {
			prod.set(i, get(i)*v.get(i));
		}
		return prod;
	}
	
	public static class NotMatchingDimensionsException extends RuntimeException {
		private static final long serialVersionUID = 1L;
		
		public NotMatchingDimensionsException(String arg0) {
			super("Dimensions doesn't match: \n"+arg0);
		}
		
	}
	
	public static class NotSameDimensionsException extends NotMatchingDimensionsException{
		private static final long serialVersionUID = -8078123872845459679L;
		
		public NotSameDimensionsException(int dim1,int dim2) {
			super(dim1+" != "+dim2);
		}
	}
}



