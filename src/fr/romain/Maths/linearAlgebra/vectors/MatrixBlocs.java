package fr.romain.Maths.linearAlgebra.vectors;

import fr.romain.Maths.linearAlgebra.algebraicObjects.Matrix;

/**
 * This class represents a matrix defined by blocs
 * This is thus a matrix of matrices
 * @author romai
 *
 */
public class MatrixBlocs extends Matrix<Matrix<Double>> {

	public MatrixBlocs(int n, int m) {
		super(n, m);
	}

	public MatrixBlocs(int[] dim) {
		super(dim);
	}

	public MatrixBlocs(Matrix<Double>[][] values) {
		super(values);
	}

	
}
