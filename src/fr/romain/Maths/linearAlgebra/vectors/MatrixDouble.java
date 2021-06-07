package fr.romain.Maths.linearAlgebra.vectors;

import fr.romain.Maths.linearAlgebra.algebraicObjects.Matrix;
import fr.romain.Maths.linearAlgebra.algebraicStructure.Algebra;
import fr.romain.Maths.linearAlgebra.algebraicStructure.Euclidean;

/**
 * This class doesn't aims to implements any algebraic structure
 * Indeed, this class only aims to allows the user to use matrices as an object without having to choose 
 * a particular algebraic structure.
 * On the other hand, this class cannot be used by a user who don't know what he will put in his matrix
 * Because of this, there are algebraic structures of matrices made for this kind of using
 * 
 */
public class MatrixDouble extends Matrix<Double> {
	

	
	private Euclidean<Matrix<Double>> e;
	
	
	public MatrixDouble(Double[][] values) {
		super(values);
		e = Euclidean.matricesEuclidean(values.length, values[0].length);
		
	}

	public MatrixDouble(int n, int m) {
		super(n, m);
		e = Euclidean.matricesEuclidean(n, m);
	}


	public MatrixDouble(int[] dim) {
		super(dim);
		e = Euclidean.matricesEuclidean(dim[0], dim[1]);
	}
	
	
	public MatrixDouble plus(MatrixDouble matrix) {
		MatrixDouble sum = (MatrixDouble) e.sum(this,matrix);
		sum.e = e;
		return sum;
	}
	

	public MatrixDouble prod(MatrixDouble matrix) {
		MatrixDouble prod =  (MatrixDouble) Matrix.usualProd(this, matrix, e.field());
		
		//the dimension of the euclidean space is changed here
		prod.e = Euclidean.matricesEuclidean(dimLines(), matrix.dimCols());
		return prod;
	}
	
	public MatrixDouble pow(int k) throws IllegalArgumentException{
		if(!isSquare()) {
			throw new IllegalArgumentException("Only a square matrix can be raised to a power");
		}
		MatrixDouble power = (MatrixDouble) (Algebra.matricesAlgebra(dimLines(), e.field()).pow(this, k));
		power.e = e;
		return power;
	}
	
	public MatrixDouble times(double k) {
		MatrixDouble m =  (MatrixDouble) e.times(k, this);
		m.e = e;
		return m;
	}
	
	
	

}



