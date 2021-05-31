package fr.romain.Maths.K_VectorSpace.algebraicObjects;

import fr.romain.Maths.K_VectorSpace.algebraicStructure.Field;

/**
 * This class represents matrices which contains elements of type K
 *
 * @param <K> the type of elements in the matrix
 */
public class Matrix<K> {

	private Object[][] values;
	
	
	public Matrix(int n,int m){
		values = new Object[n][m];
	}
	
	public Matrix(K[][] values) {
		this.values = values;
	}
	
	public Matrix(int[] dim) {
		this(dim[0],dim[1]);
	}

	@SuppressWarnings("unchecked")
	public K[][] getValues(){
		return (K[][]) values;
	}
	
	public K get(int i, int j) {
		return getValues()[i][j];
	}
	
	public void set(int i,int j, K value) {
		values[i][j] = value;
	}

	public int dimLines() {
		return values.length;
	}

	public int dimCols() {
		return values[0].length;
	}
	
	/**
	 * @return a tab which contains the lines dimensions and the column dimensions
	 */
	public int[] dims() {
		return new int[] {dimLines(),dimCols()};
	}
	
	
	public boolean hasDim(int[] dims) {
		return dimLines() == dims[0] && dimCols() == dims[1];
	}
	
	public boolean isSquare() {
		return dimLines() == dimCols();
	}
	
	public boolean canBeProdTo(Matrix<K> matrix) {
		return dimCols() == matrix.dimLines();
	}
	
	public Matrix<K> transpose() {
		Matrix<K> transpose = new Matrix<K>(dimCols(),dimLines());
		
		for (int i = 0; i < dimLines(); i++) {
			for (int j = 0; j < dimCols(); j++) {
				transpose.set(j, i, get(i, j));
			}
		}
		return transpose;
	}
	
	
	//######################### below are defined all useful operator on matrices ###########################
	
	/**
	 * The usual zero in the vector space of matrices for a field f
	 * @param <K>
	 * @param dims
	 * @param f
	 * @return
	 */
	public static<K> Matrix<K> usualZero(int[] dims, Field<K> f){
		Matrix<K> zero = new Matrix<K>(dims);
		for (int i = 0; i < zero.dimLines(); i++) {
			for (int j = 0; j < zero.dimCols(); j++) {
				zero.set(i, j, f.zero());
			}
		}
		return zero;
	}

	/**
	 * The usual sum in the vector space of matrices for a field f
	 * @param <K>
	 * @param e1
	 * @param e2
	 * @param f
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static<K> Matrix<K> usualSum(Matrix<K> e1, Matrix<K> e2,Field<K> f) throws IllegalArgumentException{
		
		if(e1.hasDim(e2.dims())) {
			Matrix<K> sumMatrix = new Matrix<K>(e1.dims());
			
			for (int i = 0; i < sumMatrix.dimLines(); i++) {
				for (int j = 0; j < sumMatrix.dimCols(); j++) {
					sumMatrix.set(i, j, f.sum(e1.get(i, j), e2.get(i, j)));
				}
			}
			
		}
		throw new IllegalArgumentException("The matrices must have the same dimensions to be summable");
		
	}

	/**
	 * the usual scalar multiplication in the vector space of matrices for a field f
	 * @param <K>
	 * @param k
	 * @param e
	 * @return
	 */
	public static<K> Matrix<K> usualTimes(K k,Matrix<K> e, Field<K> f) {
		Matrix<K> matrix = new Matrix<K>(e.dims());
		
		for (int i = 0; i < matrix.dimLines(); i++) {
			for (int j = 0; j < matrix.dimCols(); j++) {
				matrix.set(i, j, f.prod(k, e.get(i,j)));
			}
		}
		return matrix;
		
	}

	public static<K> Matrix<K> usualOne(int[] dims,Field<K> f){
		
		Matrix<K> one = new Matrix<K>(dims);
		
		for (int i = 0; i < one.dimLines(); i++) {
			for (int j = 0; j < one.dimCols(); j++) {
				if(i==j) {
					one.set(i, j, f.one());
				}
				else {
					one.set(i, j, f.zero());
				}
			}
		}
		return one;
	}

	/**
	 * @param <K>
	 * @param e1
	 * @param e2
	 * @param f
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static<K> Matrix<K> usualProd(Matrix<K> e1,Matrix<K> e2,Field<K> f) throws IllegalArgumentException{
		
		if(e1.canBeProdTo(e2)) {
			
			
			Matrix<K> prod = new Matrix<K>(e1.dimLines(),e2.dimCols());
	
			for (int i = 0; i < e1.dimLines(); i++) {
	
				for (int j = 0; j < e2.dimCols(); j++) {
	
					K sum = f.zero();
	
					for (int k = 0; k < e1.dimCols(); k++) {
						sum = f.sum(sum, f.prod(e1.get(i, k), e2.get(k, j)));
					}
					prod.set(i, j, sum);
				}
			}
			return prod;
		}
		throw new IllegalArgumentException("two matrices are multipliable only if e1.dimCols == e2.dimLines");
	}
	
	public static<K> K trace(Matrix<K> matrix,Field<K> f) throws IllegalArgumentException{
		if (!matrix.isSquare()) {
			throw new IllegalArgumentException("the trace for a non square matrix is not defined");
		}
		K trace = f.zero();
		for (int i = 0; i < matrix.dimLines(); i++) {
			trace = f.sum(trace, matrix.get(i, i));
		}
		return trace;
	}
	
	public static<K> K usualScalarProd(Matrix<K> m1,Matrix<K> m2, Field<K> f) throws IllegalArgumentException{
		if(!m1.hasDim(m2.dims())) {
			throw new IllegalArgumentException("The two matrices has to have the same dimensions");
		}
		return trace(usualProd(m1.transpose(), m2, f), f);
		
	}
		
}








