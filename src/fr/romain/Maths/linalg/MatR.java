package fr.romain.Maths.linalg;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;

import fr.romain.Maths.linalg.algstruct.Field;
import fr.romain.Maths.utils.Reals;


public class MatR extends Matrix<Double> {
	
	
	private final static Field<Double> f = Field.realsField();
	
	private final static Function<Double, Double> abs = Math::abs;
	private final static BiPredicate<Double, Double> equals = Reals::equals;
	
	public static MatR of(Matrix<Double> m){
		return new MatR(m.getValues());
	}
	
	public MatR(Double[][] values) {
		super(values);	
	}

	public MatR(int n, int m) {
		super(n, m);
	}


	public MatR(int[] dim) {
		super(dim);
	}
	
	
	public MatR plus(MatR matrix) {
		return (MatR) plus(matrix, f);
	}
	

	public MatR prod(MatR matrix) {
		return (MatR) prod(matrix, f);
	}
	
	public MatR pow(int k){
		return (MatR) pow(k, f);
	}
	
	public MatR times(double k) {
		return (MatR) times(k, f);
	}
	
	public double trace() {
		return trace(f);
	}
	
	public double det() {
		return recDet(f);
	}
	
	public MatR inv() {
		return (MatR) gaussInv(f, abs, equals);
	}
	
	public int rank() {
		return rank(f, equals);
	}
	
	public List<Vector<Double>> ker(){
		return ker(f, abs, equals);
	}
	
	public double scalarProd(MatR m) {
		return scalarProd(m, f);
	}
	
	
	@Override
	public String toString() {
		String s = "(";
		for (int i = 0; i < dimRows(); i++) {
			
			if(i>0) {
				s+="\n ";
			}
			
			for (int j = 0; j < dimCols(); j++) {
				
				if (Reals.equals(get(i, j),(double)get(i, j).intValue())) {
					s+=get(i, j).intValue();
				}
				else {
					s+=get(i, j);
				}
				
				if(j<dimCols()-1) {
					s+=" ";
				}
			}
		}
		s+=")";
		return s;
	}
	
	@Override
	public boolean equals(Object obj) {
		try {
			MatR m = (MatR) obj;
			return equals(m, equals);
		} catch (Exception e) {
			return false;
		}
	}
	
	public static MatR zeros(int... dims) {
		return (MatR) Matrix.zeros(f, dims);
	}
	
	public static MatR id(int... dims) {
		return (MatR) Matrix.id(f, dims);
	}
}



