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
	
	public MatR minus(MatR matrix) {
		return (MatR) minus(matrix, f);
	}
	

	public MatR dot(MatR m) {
		return (MatR) prod(m, f);
	}
	
	/**
	 * the element-wise product on matrices
	 * @param m
	 * @return
	 */
	public MatR hDot(MatR m) {
		return (MatR) hadamardProd(m, f);
	}
	
	public MatR dot(double k) {
		return (MatR) times(k, f);
	}
	
	public MatR t() {
		return (MatR) transpose();
	}
	
	
	public MatR pow(int k){
		return (MatR) pow(k, f);
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
	
	/**
	 * the element-wise inverse :<br>
	 * hInv() = (1/aij)
	 * @return
	 */
	public MatR hInv() {
		return (MatR) hadamardInv(f);
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
	
	public VectR colsMax() {
		VectR colsMax = new VectR(dimCols());
		List<Vector<Double>> cols = getCols();
		
		for (int i = 0; i < cols.size(); i++) {
			colsMax.set(i, ((VectR)cols.get(i)).max());
		}
		return colsMax;
	}
	
	public VectR colsMin() {
		VectR colsMin = new VectR(dimCols());
		List<Vector<Double>> cols = getCols();
		
		for (int i = 0; i < cols.size(); i++) {
			colsMin.set(i, ((VectR)cols.get(i)).min());
		}
		return colsMin;
	}
	
	public VectR colsMeans() {
		VectR colsMeans = new VectR(dimCols());
		List<Vector<Double>> cols = getCols();
		
		for (int i = 0; i < cols.size(); i++) {
			colsMeans.set(i, ((VectR)cols.get(i)).mean());
		}
		return colsMeans;
	}
	
	public void forEachCols(Function<VectR, VectR> f) {
		for (int i = 0; i < dimCols(); i++) {
			setCol(i, f.apply(((VectR)getCol(i))));
		}
	}
	
	public void forEachElmt(Function<Double, Double> f) {
		for (int i = 0; i < dimRows(); i++) {
			for (int j = 0; j < dimCols(); j++) {
				set(i, j, f.apply(get(i, j)));
			}
		}
	}
	
	public MatR elmtWise(Function<Double, Double> f) {
		MatR res = new MatR(dims());
		for (int i = 0; i < dimRows(); i++) {
			for (int j = 0; j < dimCols(); j++) {
				res.set(i, j,f.apply(get(i, j)));
			}
		}
		return res;
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
	
	public static MatR ones(int... dims) {
		return (MatR) Matrix.ones(f, dims);
	}
	
	public static MatR id(int dim) {
		return (MatR) Matrix.id(f, dim);
	}
	
	public static MatR concat(MatR... matrices) {
		MatR concatened = matrices[0];
		for (int i = 1; i < matrices.length; i++) {
			concatened = (MatR) concatened.augmRow(matrices[i]);
		}
		return concatened;
	}
}



