package fr.romain.Maths.linearAlgebra.algebraicObjects;

/**
 * This class represents a matrix defined by blocs
 * This is thus a matrix of matrices
 * @author romai
 *
 */
public class MatrixBlocs<K> extends Matrix<Matrix<K>> {

	public MatrixBlocs(int n, int m) {
		super(n, m);
	}

	public MatrixBlocs(int[] dim) {
		super(dim);
	}

	public MatrixBlocs(Matrix<K>[][] values) {
		super(values);
	}
	
	/**
	 * The dimension of the matrix resulting of this blocs matrix
	 * @return
	 */
	public int[] toMatDim() {
		int dimLines = 0;
		for (int i = 0; i < dimLines(); i++) {
			dimLines += get(i, 0).dimLines();
		}
		int dimCols = 0;
		for (int j = 0; j < dimCols; j++) {
			dimCols+= get(0, j).dimCols();
		}
		return new int[] {dimLines,dimCols};
	}
	
	public Matrix<K> toMatrix(){
		Matrix<K> m = new Matrix<K>(toMatDim());
		
		int line = 0;
		int column = 0;
		for (int i = 0; i < dimLines(); i++) {
			for (int j = 0; j < dimCols(); j++) {
				
				column += get(i, j).dimCols();
			}
			//TODO
		}
		return null;
	}

	
}






