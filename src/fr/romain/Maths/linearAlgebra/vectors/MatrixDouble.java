package fr.romain.Maths.linearAlgebra.vectors;

import fr.romain.Maths.linearAlgebra.Reals;
import fr.romain.Maths.linearAlgebra.algebraicObjects.Matrix;
import fr.romain.Maths.linearAlgebra.algebraicStructure.Algebra;
import fr.romain.Maths.linearAlgebra.algebraicStructure.Field;

/**
 * This class doesn't aims to implements any algebraic structure
 * Indeed, this class only aims to allows the user to use matrices as an object without having to choose 
 * a particular algebraic structure.
 * On the other hand, this class cannot be used by a user who don't know what he will put in his matrix
 * Because of this, there are algebraic structures of matrices made for this kind of using
 * 
 * 
 */
public class MatrixDouble extends Matrix<Double> {
	
	
	private final static Field<Double> f = Field.realsField();
	
	public static MatrixDouble of(Matrix<Double> m){
		return new MatrixDouble(m.getValues());
	}
	
	public MatrixDouble(Double[][] values) {
		super(values);	
	}

	public MatrixDouble(int n, int m) {
		super(n, m);
	}


	public MatrixDouble(int[] dim) {
		super(dim);
	}
	
	
	public MatrixDouble plus(MatrixDouble matrix) {
		return (MatrixDouble) plus(matrix, f);
	}
	

	public MatrixDouble prod(MatrixDouble matrix) {
		return (MatrixDouble) prod(matrix, f);
	}
	
	public MatrixDouble pow(int k) throws IllegalArgumentException{
		if(!isSquare()) {
			throw new IllegalArgumentException("Only a square matrix can be raised to a power");
		}
		return (MatrixDouble) (Algebra.matricesAlgebra(dimLines(),f).pow(this, k));
	}
	
	public MatrixDouble times(double k) {
		return (MatrixDouble) times(k, f);
	}
	
	public double trace() {
		return trace(f);
	}
	
	public double det() {
		return recDet(f);
	}
	
	public MatrixDouble inv() {
		return (MatrixDouble) gaussInv(f, Math::abs, Reals::equals);
	}
	
	public double scalarProd(MatrixDouble m) {
		return scalarProd(m, f);
	}
	
	@Override
	public String toString() {
		String s = "(";
		for (int i = 0; i < dimLines(); i++) {
			
			if(i>0) {
				s+="\n ";
			}
			
			for (int j = 0; j < dimCols(); j++) {
				
				if (get(i, j)==get(i, j).intValue()) {
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
			MatrixDouble m = (MatrixDouble) obj;
			return equals(m, Reals::equals);
		} catch (Exception e) {
			return false;
		}
	}
	
	public static MatrixDouble zero(int... dims) {
		return (MatrixDouble) Matrix.zero(f, dims);
	}
	
	public static MatrixDouble one(int... dims) {
		return (MatrixDouble) Matrix.one(f, dims);
	}
}



