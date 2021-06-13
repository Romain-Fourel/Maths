package fr.romain.Maths.linearAlgebra.algebraicObjects;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;

import fr.romain.Maths.linearAlgebra.algebraicStructure.Field;
import fr.romain.Maths.linearAlgebra.algebraicStructure.Ring;
import fr.romain.Maths.linearAlgebra.vectors.MatrixDouble;
 
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
	
	public static<K> Matrix<K> of(K[][] tab){
		return new Matrix<K>(tab);
	}
	
	@SafeVarargs
	public static<K> Matrix<K> byLines(Vector<K>...vectors){
		Matrix<K> matrix = new Matrix<K>(vectors.length, vectors[0].dim());
		for (int i = 0; i < vectors.length; i++) {
			for (int j = 0; j < vectors[0].dim(); j++) {
				matrix.set(i, j, vectors[i].get(j));
			}
		}
		return matrix;
	}
	
	@SafeVarargs
	public static<K> Matrix<K> byCols(Vector<K>...vectors){
		return byLines(vectors).transpose();
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
	
	public Vector<K> getLine(int i){
		return new Vector<K>(getValues()[i]);
	}
	
	public void setLine(int i, Vector<K> line) {
		values[i] = line.getValues();
	}
	
	
	public List<Vector<K>> getLines(){
		List<Vector<K>> lines = new ArrayList<Vector<K>>();
		for (int i = 0; i < dimLines(); i++) {
			lines.add(getLine(i));
		}
		return lines;
	}
	
	public Vector<K> getCol(int j){
		Vector<K> col = new Vector<>(dimCols());
		for (int i = 0; i < dimLines(); i++) {
			col.set(i, get(i, j));
		}
		return col;
	}
	
	public List<Vector<K>> getCols(){
		List<Vector<K>> cols = new ArrayList<Vector<K>>();
		for (int j = 0; j < dimCols(); j++) {
			cols.add(getCol(j));
		}
		return cols;
	}
	

	/**
	 * 
	 * @return the number of elements in a line
	 */
	public int dimLines() {
		return values.length;
	}

	/**
	 * 
	 * @return the number of elements in a column
	 */
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
	
	/**
	 * the usual scalar multiplication in the vector space of matrices for a field f
	 * @param <K>
	 * @param k
	 * @param e
	 * @return
	 */
	public Matrix<K> times(K k, Ring<K> r) {
		Matrix<K> matrix = new Matrix<K>(dims());
		
		for (int i = 0; i < matrix.dimLines(); i++) {
			for (int j = 0; j < matrix.dimCols(); j++) {
				matrix.set(i, j, r.prod(k, get(i,j)));
			}
		}
		return matrix;
		
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
	public Matrix<K> plus(Matrix<K> m,Ring<K> f){
		
		if(hasDim(m.dims())) {
			Matrix<K> sumMatrix = new Matrix<K>(dims());
			
			for (int i = 0; i < sumMatrix.dimLines(); i++) {
				for (int j = 0; j < sumMatrix.dimCols(); j++) {
					sumMatrix.set(i, j, f.sum(get(i, j), m.get(i, j)));
				}
			}
			return sumMatrix;
			
		}
		throw new NotSameDimensionsException(dims(), m.dims());
		
	}
	
	
	/**
	 * The usual product on matrices for the ring r
	 * @param <K>
	 * @param e1
	 * @param m
	 * @param r
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Matrix<K> prod(Matrix<K> m,Ring<K> r){
		
		if(canBeProdTo(m)) {
			
			
			Matrix<K> prod = new Matrix<K>(dimLines(),m.dimCols());
	
			for (int i = 0; i < dimLines(); i++) {
	
				for (int j = 0; j < m.dimCols(); j++) {
	
					K sum = r.zero();
	
					for (int k = 0; k < dimCols(); k++) {
						sum = r.sum(sum, r.prod(get(i, k), m.get(k, j)));
					}
					prod.set(i, j, sum);
				}
			}
			return prod;
		}
		throw new NotMultipliableMatricesException(dims(), m.dims());
	}
	
	
	/**
	 * The hadamard product on matrices.
	 * it is a term by term product
	 * @param matrix
	 * @param r
	 * @return
	 */
	public Matrix<K> hadamardProd(Matrix<K> matrix, Ring<K> r){
		if (!hasDim(matrix.dims())) {
			throw new NotSameDimensionsException(dims(), matrix.dims());
		}
		Matrix<K> hadMatrix = new Matrix<>(dims());
		
		for (int i = 0; i < dimLines(); i++) {
			for (int j = 0; j < dimCols(); j++) {
				hadMatrix.set(i, j, r.prod(get(i, j), matrix.get(i, j)));
			}
		}
		return hadMatrix;	
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
	
	
	
	public K trace(Ring<K> r){
		if (!isSquare()) {
			throw new NotSquareMatrixException("Trace");
		}
		K trace = r.zero();
		for (int i = 0; i < dimLines(); i++) {
			trace = r.sum(trace, get(i, i));
		}
		return trace;
	}
	
	
	/**
	 * The usual scalar prod : Tr(tAxB)
	 * @param <K>
	 * @param m1
	 * @param m
	 * @param r
	 * @return
	 * @throws IllegalArgumentException
	 */
	public K scalarProd(Matrix<K> m, Ring<K> r){
		if(!hasDim(m.dims())) {
			throw new NotSameDimensionsException(dims(), m.dims());
		}
		return transpose().prod(m, r).trace(r);
		
	}	
	
	
	/**
	 * If the matrix has dimensions like 1xn or nx1,
	 * It can thus be seen as a vector
	 * @return
	 * @throws IllegalArgumentException: if the matrix is like nxm with n!=1 and m!=1
	 */
	public Vector<K> toVec(){
		if (dimLines()==1){
			return new Vector<>(getValues()[0]);
		}
		else if (dimCols()==1) {
			Vector<K> vec = new Vector<>(dimLines());
			for (int i = 0; i < dimLines(); i++) {
				vec.set(i, get(i, 0));
			}
			return vec;
		}
		throw new NotMatchingDimensionsException("This matrix can't been seen as a vector, dimensions doesn't match");
	}
	
	
	
	/**
	 * 
	 * @param i the line to delete
	 * @param j the column to delete
	 * @return the same matrix of this but without the line i and the column j
	 */
	public Matrix<K> getMat(int i,int j){
		if(i>=dimLines() || j>=dimCols()) {
			throw new IllegalArgumentException("The number of the line or the column is too high");
		}
		Matrix<K> matrix = new Matrix<>(dimLines()-1, dimCols()-1);
		
		int offsetI = 0;
		int offsetJ = 0;
		
		for (int k = 0; k < dimLines()-1; k++) {
			
			offsetI = (k >= i) ? 1:0;
			
			for (int k2 = 0; k2 < dimCols()-1; k2++) {
				
				offsetJ = (k2 >= j) ? 1 : 0;
				
				matrix.set(k, k2, get(k+offsetI, k2+offsetJ));
			}

		}
		return matrix;
	}
	
	
	/**
	 * The determinant is calculated recursively
	 * TODO: Bareiss Algorithm
	 * @param <K>
	 * @param matrix
	 * @param r
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public K recDet(Ring<K> r){
		if(!isSquare())
			throw new NotSquareMatrixException("Determinant");
		
		if(dimLines()==1) {
			return get(0, 0);
		}
		K sign = r.sumInv(r.one());;
		K det = r.zero();
		
		for (int j = 0; j < dimCols(); j++) {
			if(get(0, j).equals(r.zero()))
				continue;
			sign = r.sumInv(sign);
			K detRec = getMat(0, j).recDet(r);
			det = r.sum(det, r.prod(sign,get(0, j),detRec));
			 
		}
		
		return det;
	}
	
	
	/**
	 * 
	 * @param r the ring of the scalar
	 * @param i the line of the cofacteur
	 * @param j the column of the cofacteur
	 * @return the value of the cofactor of aij
	 */
	public K cofactor(Ring<K> r,int i,int j) {
		
		K sign = r.pow(r.sumInv(r.one()), i+j);	
		return r.prod(sign, getMat(i, j).recDet(r));
	}
	
	
	/**
	 * 
	 * @param r
	 * @return the comatrix of this matrix
	 */
	public Matrix<K> comatrix(Ring<K> r){
		Matrix<K> comatrix = new Matrix<>(dims());
		for (int i = 0; i < dimLines(); i++) {
			for (int j = 0; j < dimCols(); j++) {
				comatrix.set(i, j, cofactor(r, i, j));
			}
		}
		return comatrix;
	}
	
	
	public boolean isInversible(Ring<K> r,BiPredicate<K, K> equals) {
		return !equals.test(recDet(r),r.zero());
	}
	
	/**
	 * This method calculate the inverse of this matrix for the usual product on
	 * square matrices
	 * 
	 * This calculate the inverse of this matrix with the formula which uses
	 * the comatrix, this is thus not an optimized method when n>4, but funny
	 * It is better to use the inv method which uses the gaussian elimination
	 * @param f the field on scalar, has to be a commutative one
	 * @return the inverse of this matrix
	 */
	public Matrix<K> comInv(Field<K> f,BiPredicate<K, K> equals){
		K det = recDet(f);
		if(equals.test(det, f.zero())) {
			throw new NotInversibleMatrixException();
		}
		
		return comatrix(f).transpose().times(f.prodInv(det), f);
	}
	
	
	
	/**
	 * Swap the two lines i and j in the matrix
	 * Useful for gaussian elimination
	 * (one of the three operations allowed: swap, add and times for lines)
	 * @param i
	 * @param j
	 */
	public void swap(int l1, int l2) {
		Vector<K> line1 = getLine(l1);
		setLine(l1, getLine(l2));
		setLine(l2, line1);
	}
	
	/**
	 * Calculate the augmented matrix (this|X)
	 * Usually for the purpose of performing the same elementary rows operations on
	 * each of the given matrices
	 * @param X
	 * @return
	 */
	public Matrix<K> augmented(Matrix<K> x){
		if(dimLines() != x.dimLines()) {
			throw new NotMatchingDimensionsException("there is not the same number of lines in the two matrices");
		}
		Matrix<K> m = new Matrix<>(dimLines(),dimCols()+x.dimCols());
		for (int i = 0; i < dimLines(); i++) {
			
			for (int j = 0; j < dimCols(); j++) {
				m.set(i, j, get(i, j));
			}
	
			for (int j = 0; j < x.dimCols(); j++) {
				m.set(i,dimCols()+j, x.get(i, j));
			}
		}
		return m;
	}
	
	/**
	 * Returns the two matrices of dim (dimLines,i) and (dimLines,dimCols-i)
	 * Usually for the purpose of extracting the matrices from an augmented matrix after
	 * having performing elementary rows operations on the augmented matrix
	 * @param split the last line of the first matrix
	 * @return
	 */
	public List<Matrix<K>> split(int split){
		Matrix<K> m1 = new Matrix<>(dimLines(), split);
		Matrix<K> m2 = new Matrix<>(dimLines(),dimCols()-split);
		
		for (int i = 0; i < m1.dimLines(); i++) {
			for (int j = 0; j < m1.dimCols(); j++) {
				m1.set(i, j, get(i, j));
			}
		}
		for (int i = 0; i < m2.dimLines(); i++) {
			for (int j = 0; j < m2.dimCols(); j++) {
				m2.set(i, j, get(i,split+j));
			}
		}
		return List.of(m1,m2);
	}

	
	
	/**
	 * This function reduce the left-square matrix of this, the right matrix
	 * is thus, after having performed the gaussian reduction, the result of the equation
	 * symbolized by the augmented matrix. If this is not an augmented Matrix, the gaussReduce
	 * transform this matrix to the identity matrix
	 * 
	 * In order to optimize the algorithm, an absolute value on K is needed
	 * (in order to find the biggest element in a line)
	 * 
	 * 
	 * @param X the matrix we want the coefficients after having reduced this matrix
	 * @param f
	 * @return 
	 * @throws IllegalArgumentException
	 */
	public Matrix<K> gaussReduce(Field<K> f,Function<K, Double> abs, BiPredicate<K, K> equals){
		
		if(dimLines()>dimCols())
			throw new NotMatchingDimensionsException("there is more lines than columns !");
		
		Matrix<K> gauss = clone();
		
		int r = -1; // the line we want to echelon
		for (int j = 0; j < dimLines(); j++) {
			
			//first, we search the maximum:
			int k = r+1;
			double max = abs.apply(gauss.get(k, j));
			for (int i = r+1; i < dimLines(); i++) {
				if(abs.apply(gauss.get(i, j))>max) {
					max = abs.apply(gauss.get(i, j));
					k = i;
				}
			}
			
			K pivot = gauss.get(k, j);
			
			//we can't reduce the line if the max is zero because we can't divide by zero
			if(!equals.test(pivot, f.zero())) {
				r += 1;
				gauss.setLine(k, gauss.getLine(k).times(f.prodInv(pivot), f));
				
				if(k!=r) {
					gauss.swap(k, r);
				}
				for (int i = 0; i < dimLines(); i++) {
					if(i != r) {
						Vector<K> reduced = gauss.getLine(i).minus(gauss.getLine(r).times(gauss.get(i, j), f), f);
						gauss.setLine(i, reduced);
					}
				}
				
			}
			
		}
		return gauss;
	}

	
	/**
	 * This method calculate the inverse of this matrix for the usual product and the
	 * usual times on square matrices
	 * 
	 * In order to optimize the algorithm, an absolute value on K is needed
	 * (in order to find the biggest element in a line)
	 * 
	 * @param f the field on scalar, has to be a commutative one
	 * @return the inverse of this matrix
	 */
	public Matrix<K> gaussInv(Field<K> f, Function<K, Double> abs,BiPredicate<K, K> equals){
		
		Matrix<K> augmMatrix = augmented(one(f, dims()));
		
		Matrix<K> reduced = augmMatrix.gaussReduce(f, abs, equals);

		List<Matrix<K>> results = reduced.split(dimLines());
		
		if(!results.get(0).equals(one(f, dims()), equals)) {
			throw new NotInversibleMatrixException();
		}
		return results.get(1);
		
	}
	
	
	/**
	 * Calculate the inverse of this matrix for the product on matrices of Hadamard
	 * @param f
	 * @return
	 */
	public Matrix<K> hadamardInv(Field<K> f){
		Matrix<K> inv = new Matrix<>(dims());
		for (int i = 0; i < dimLines(); i++) {
			for (int j = 0; j < dimCols(); j++) {
				
				inv.set(i, j, f.prodInv(get(i, j)));
			}
		}
		return inv;
	}
	
	
	
	/**
	 * Warning: this function test the equality of each term in the matrix
	 * by using the equals method on K
	 * If you want to test the equality with an other comparator, uses the equals
	 * which uses a comparator
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		try {
			Matrix<K> m = (Matrix<K>) obj;
			
			if(!hasDim(m.dims()))
				return false;
			
			for (int i = 0; i < dimLines(); i++) {
				for (int j = 0; j < dimCols(); j++) {
					
					if(!get(i, j).equals(m.get(i, j)))
						return false;
				}
			}
			return true;
			
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean equals(Matrix<K> m,BiPredicate<K, K> equals) {
		if(!hasDim(m.dims()))
			return false;
		
		for (int i = 0; i < dimLines(); i++) {
			for (int j = 0; j < dimCols(); j++) {
				
				if (!equals.test(get(i, j),m.get(i, j))) {
					return false;
				}
			}
		}
		return true;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String toString() {
		try {
			return MatrixDouble.of((Matrix<Double>) this).toString();
		} catch (Exception e) {
			String s = "(";
			for (int i = 0; i < dimLines(); i++) {
				
				if(i>0) {
					s+="\n ";
				}
				
				for (int j = 0; j < dimCols(); j++) {
					
					s+=get(i, j);
					
					if(j<dimCols()-1) {
						s+=" ";
					}				
				}
			}
			s+=")";
			return s;
		}
	}
	
	
	public Matrix<K> clone(){
		Matrix<K> clone = new Matrix<>(dims());
		for (int i = 0; i < dimLines(); i++) {
			for (int j = 0; j < dimCols(); j++) {
				clone.set(i, j, get(i, j));
			}
		}
		return clone;
	}
	
	//######################### below are defined all useful constant matrices ###########################
	
	/**
	 * The usual zero in the vector space of matrices for a field f
	 * @param <K>
	 * @param dims
	 * @param r
	 * @return
	 */
	public static<K> Matrix<K> zero(Ring<K> r,int... dims){
		Matrix<K> zero = new Matrix<K>(dims);
		for (int i = 0; i < zero.dimLines(); i++) {
			for (int j = 0; j < zero.dimCols(); j++) {
				zero.set(i, j, r.zero());
			}
		}
		return zero;
	}



	/**
	 * The usual one in the vector space of matrices for a ring r
	 * @param <K>
	 * @param dims
	 * @param r
	 * @return
	 */
	public static<K> Matrix<K> one(Ring<K> r,int... dims){
		
		Matrix<K> one = new Matrix<K>(dims);
		
		for (int i = 0; i < one.dimLines(); i++) {
			for (int j = 0; j < one.dimCols(); j++) {
				if(i==j) {
					one.set(i, j, r.one());
				}
				else {
					one.set(i, j, r.zero());
				}
			}
		}
		return one;
	}
	
	
	public static<K> Matrix<K> hadamardOne(Ring<K> r,int... dims){
		Matrix<K> one = new Matrix<K>(dims);
		for (int i = 0; i < one.dimLines(); i++) {
			for (int j = 0; j < one.dimCols(); j++) {
				one.set(i, j, r.one());
			}
		}
		return one;
	}
	

	public static<K> List<Matrix<K>> canonicalBasis(Ring<K> r,int... dims){
		List<Matrix<K>> basis = new ArrayList<Matrix<K>>();
		for (int i = 0; i < dims[0]; i++) {
			for (int j = 0; j < dims[1]; j++) {
				Matrix<K> m = zero(r, dims);
				m.set(i, j, r.one());
				basis.add(m);
			}
		}
		return basis;
	}
		
	
	
	
	public static class NotInversibleMatrixException extends RuntimeException{
		private static final long serialVersionUID = -8561739294682823439L;
		
		public NotInversibleMatrixException() {
			super("This matrix is not inversible");
		}
		
		public NotInversibleMatrixException(String arg0) {
			super(arg0);
		}
		
	}
	
	public static class NotSquareMatrixException extends RuntimeException{
		private static final long serialVersionUID = 4194140256598407548L;
		
		public NotSquareMatrixException(String operation) {
			super("The matrix has to be a square for the operation "+operation);
		}
		
	}
	
	public static class NotMatchingDimensionsException extends RuntimeException{
		private static final long serialVersionUID = -1340805826016144255L;
		
		public NotMatchingDimensionsException(String arg0) {
			super("Dimensions doesn't match: \n"+arg0);
		}
		
		
	}
	
	public static class NotSameDimensionsException extends NotMatchingDimensionsException{
		private static final long serialVersionUID = 3889835293860092255L;
		
		public NotSameDimensionsException(int[] dim1,int[] dim2) {
			super("("+dim1[0]+","+dim1[1]+") != "+"("+dim2[0]+","+dim2[1]+")");
			
		}
		
	}
	
	public static class NotMultipliableMatricesException extends NotMatchingDimensionsException{
		private static final long serialVersionUID = 1L;
		
		public NotMultipliableMatricesException(int[] dim1,int[] dim2) {
			super(""+dim1[1]+" != "+dim2[0]);
		}
		
	}
}





