package fr.romain.Maths.linalg;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;

import fr.romain.Maths.linalg.algstruct.Field;
import fr.romain.Maths.linalg.algstruct.Ring;
 
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
	public static<K> Matrix<K> byRows(Vector<K>...vectors){
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
		return byRows(vectors).transpose();
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
	
	public Vector<K> getRow(int i){
		return new Vector<K>(getValues()[i]);
	}
	
	public void setRow(int i, Vector<K> row) {
		values[i] = row.getValues();
	}
	
	
	public List<Vector<K>> getRows(){
		List<Vector<K>> rows = new ArrayList<Vector<K>>();
		for (int i = 0; i < dimRows(); i++) {
			rows.add(getRow(i));
		}
		return rows;
	}
	
	public void setCol(int j,Vector<K> col) {
		for (int i = 0; i < dimRows(); i++) {
			values[i][j]=col.get(i);
		}
	}
	
	public Vector<K> getCol(int j){
		Vector<K> col = new Vector<>(dimRows());
		for (int i = 0; i < dimRows(); i++) {
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
	 * @return the number of rows
	 */
	public int dimRows() {
		return values.length;
	}

	/**
	 * 
	 * @return the number of columns
	 */
	public int dimCols() {
		return values[0].length;
	}
	
	/**
	 * @return a tab which contains the rows dimensions and the column dimensions
	 */
	public int[] dims() {
		return new int[] {dimRows(),dimCols()};
	}
	
	
	public boolean hasDim(int[] dims) {
		return dimRows() == dims[0] && dimCols() == dims[1];
	}
	
	public boolean isSquare() {
		return dimRows() == dimCols();
	}
	
	public boolean canBeProdTo(Matrix<K> matrix) {
		return dimCols() == matrix.dimRows();
	}
	
	/**
	 * the usual external product in the vector space of matrices for a ring r
	 */
	public Matrix<K> times(K k, Ring<K> r) {
		Matrix<K> matrix = new Matrix<K>(dims());
		
		for (int i = 0; i < matrix.dimRows(); i++) {
			for (int j = 0; j < matrix.dimCols(); j++) {
				matrix.set(i, j, r.prod(k, get(i,j)));
			}
		}
		return matrix;
		
	}
	
	
	/**
	 * The usual sum in the vector space of matrices for a ring r
	 */
	public Matrix<K> plus(Matrix<K> m,Ring<K> r){
		
		if(hasDim(m.dims())) {
			Matrix<K> sumMatrix = new Matrix<K>(dims());
			
			for (int i = 0; i < sumMatrix.dimRows(); i++) {
				for (int j = 0; j < sumMatrix.dimCols(); j++) {
					sumMatrix.set(i, j, r.sum(get(i, j), m.get(i, j)));
				}
			}
			return sumMatrix;
			
		}
		throw new NotSameDimensionsException(dims(), m.dims());
		
	}
	
	public Matrix<K> minus(Matrix<K> m,Ring<K> r){
		if(!hasDim(m.dims())) {
			throw new NotSameDimensionsException(dims(), m.dims());
		}
		return plus(m.times(r.sumInv(r.one()), r), r);
	}
	
	
	/**
	 * The usual product on matrices for the ring r
	 */
	public Matrix<K> prod(Matrix<K> m,Ring<K> r){
		
		if(canBeProdTo(m)) {	
			Matrix<K> prod = new Matrix<K>(dimRows(),m.dimCols());
	
			for (int i = 0; i < dimRows(); i++) {
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
	
	
	public Matrix<K> pow(int k, Ring<K> r){
		if(!isSquare()) {
			throw new NotSquareMatrixException("power");
		}
		if(k==0) {
			return id(r, dimRows());
		}
		if(k==1) {
			return this;
		}
		if(k%2==0) {
			return pow(k/2, r).prod(pow(k/2, r), r);
		}
		
		return prod(pow((k-1)/2, r).prod(pow((k-1)/2, r), r),r);	
	}
	
	
	
	
	public Matrix<K> transpose() {
		Matrix<K> transpose = new Matrix<K>(dimCols(),dimRows());
		
		for (int i = 0; i < dimRows(); i++) {
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
		for (int i = 0; i < dimRows(); i++) {
			trace = r.sum(trace, get(i, i));
		}
		return trace;
	}
	
	
	/**
	 * The usual scalar prod : Tr(tAxB)
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
		if (dimRows()==1){
			return new Vector<>(getValues()[0]);
		}
		else if (dimCols()==1) {
			Vector<K> vec = new Vector<>(dimRows());
			for (int i = 0; i < dimRows(); i++) {
				vec.set(i, get(i, 0));
			}
			return vec;
		}
		throw new NotMatchingDimensionsException("This matrix can't been seen as a vector");
	}
	
	
	
	/**
	 * 
	 * @param i the row to delete
	 * @param j the column to delete
	 * @return the same matrix of this but without the row i and the column j
	 */
	public Matrix<K> getMat(int i,int j){
		if(i>=dimRows()) {
			throw new IllegalArgumentException("The number of the row is too high");
		}
		if(j>dimCols()) {
			throw new IllegalArgumentException("The number of the columns is too high");
		}
		Matrix<K> matrix = new Matrix<>(dimRows()-1, dimCols()-1);
		
		int offsetI = 0;
		int offsetJ = 0;
		
		for (int k = 0; k < dimRows()-1; k++) {
			
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
		
		if(dimRows()==1) {
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
	 * @param i the row of the cofactor
	 * @param j the column of the cofactor
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
		for (int i = 0; i < dimRows(); i++) {
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
	 * the comatrix, this is thus not an optimized method when n>4, but funny.
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
	
	
	//############## Below: all elementary operations on matrices for rows and columns #######
	
	
	/**
	 * Swap the two rows l1 and l2 in the matrix
	 * Useful for gaussian elimination
	 * (one of the three elementary operations : swap, add and times for rows)
	 * @param i
	 * @param j
	 */
	public void swapRows(int l1, int l2) {
		Vector<K> row1 = getRow(l1);
		setRow(l1, getRow(l2));
		setRow(l2, row1);
	}
	
	/**
	 * Put into the line l1, the sum of the line l1 and the line l2 timed k
	 * Useful for gaussian elimination
	 * (one of the three elementary operations: swap, add and times for rows)
	 * @param l1 changed by l1+k*l2
	 * @param l2 
	 * @param k
	 * @param r
	 */
	public void addRows(int l1,int l2,K k, Ring<K> r) {
		setRow(l1, getRow(l1).plus(getRow(l2).times(k, r),r));
	}
	
	/**
	 * Put into the line l the line of l timed k
	 * Useful for gaussian elimination
	 * (one of the three elementary operations: swap, add and scaling for rows)
	 * @param l changed by l*k
	 * @param k
	 * @param r
	 */
	public void scalingRow(int l, K k, Ring<K> r) {
		setRow(l, getRow(l).times(k, r));
	}
	
	
	/**
	 * Swap the two cols c1 and c2 in the matrix
	 * Useful for gaussian elimination
	 * (one of the three elementary operations : swap, add and times for cols)
	 * @param i
	 * @param j
	 */
	public void swapCols(int c1, int c2) {
		Vector<K> row1 = getRow(c1);
		setRow(c1, getRow(c2));
		setRow(c2, row1);
	}
	
	/**
	 * Put into the col l1, the sum of the col l1 and the col l2 timed k
	 * Useful for gaussian elimination
	 * (one of the three elementary operations: swap, add and times for cols)
	 * @param c1 changed by l1+k*l2
	 * @param l2 
	 * @param k
	 * @param r
	 */
	public void addCols(int c1,int c2,K k, Ring<K> r) {
		setRow(c1, getRow(c2).times(k, r));
	}
	
	
	/**
	 * Put into the col l the col of l timed k
	 * Useful for gaussian elimination
	 * (one of the three elementary operations: swap, add and scaling for cols)
	 * @param c changed by l*k
	 * @param k
	 * @param r
	 */
	public void scalingCols(int c, K k, Ring<K> r) {
		setRow(c, getRow(c).times(k, r));
	}
	
	
	/**
	 * Calculate the augmented matrix on rows: (this|X)
	 * Usually for the purpose of performing the same elementary rows operations on
	 * each of the given matrices
	 * @param X
	 * @return
	 */
	public Matrix<K> augmRow(Matrix<K> x){
		if(dimRows() != x.dimRows()) {
			throw new NotMatchingDimensionsException("there is not the same number of rows in the two matrices");
		}
		Matrix<K> m = new Matrix<>(dimRows(),dimCols()+x.dimCols());
		for (int i = 0; i < dimRows(); i++) {
			
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
	 * Calculate the augmented matrix on cols:<br> (this    <br>
	 * 											    ----    <br>
	 *                                                X  )  <br>
	 * Which is equal to : t(tA|tB)
	 * @param x
	 * @return
	 */
	public Matrix<K> augmCol(Matrix<K> x){
		
		return transpose().augmRow(x.transpose()).transpose();
	}
	
	
	/**
	 * Returns the two matrices of dim (dimRows,i) and (dimRows,dimCols-i)
	 * Usually for the purpose of extracting the matrices from an augmented matrix after
	 * having performing elementary rows operations on the augmented matrix
	 * @param split : the last row of the first matrix
	 * @return
	 */

	public List<Matrix<K>> splitRows(int split){
		Matrix<K> m1 = new Matrix<>(dimRows(), split);
		Matrix<K> m2 = new Matrix<>(dimRows(),dimCols()-split);
		
		for (int i = 0; i < m1.dimRows(); i++) {
			for (int j = 0; j < m1.dimCols(); j++) {
				m1.set(i, j, get(i, j));
			}
		}
		for (int i = 0; i < m2.dimRows(); i++) {
			for (int j = 0; j < m2.dimCols(); j++) {
				m2.set(i, j, get(i,split+j));
			}
		}
		return List.of(m1,m2);
	}
	
	public List<Matrix<K>> splitCols(int split){
		List<Matrix<K>> splitted = transpose().splitRows(split);
		return List.of(splitted.get(0).transpose(), splitted.get(1).transpose());
	}

	
	/**
	 * A row echelon form of a matrix is the matrix got only by elementary operations
	 * which, at each rows, the first not null element from the left of the row is after
	 * the first not null element of the row above <br>
	 * Complexity : O(n²) (= n+2n²)
	 * @return the matrix in one of its row echelon form
	 */
 	public Matrix<K> toRowEchelonForm(Field<K> f, BiPredicate<K, K> equals){

		Matrix<K> echelon = clone();
		
		int lastRow = dimRows()-1;
		
		//first, we put all null rows (full of zero) at the end of the matrix
		for (int i = 0; i <= lastRow; i++) {
			if(getRow(i).isNull(f, equals)) {
				echelon.swapRows(i, lastRow);
				lastRow--; // we will no longer change  the rows after this one because they are all full of zero by construction
			}
		}
		
		int numPivot = 0;
		
		for (int numCol = 0; numCol < dimCols(); numCol++) {
			
			if (equals.test(echelon.get(numPivot, numCol), f.zero())) {
				int rowSwap = numPivot+1;
				
				if(rowSwap>lastRow)
					continue;
				
				boolean isEchelonned = false;
				
				while (equals.test(echelon.get(rowSwap, numCol), f.zero())) {
					rowSwap++;
					if(rowSwap>lastRow) {
						isEchelonned = true;
						break;
					}
				}
				if(isEchelonned)
					continue;
				
				echelon.swapRows(numPivot, rowSwap);				
			}
			K pivot = echelon.get(numPivot, numCol);
			
			for (int i = numPivot+1; i <= lastRow; i++) {
				
				if(!equals.test(echelon.get(i, numCol), f.zero())) {					
					echelon.addRows(i, numPivot, f.sumInv(f.div(echelon.get(i, numCol), pivot)), f);		
				}
			}
			numPivot++;
			if(numPivot>lastRow) {
				return echelon;
			}		
		}
		
		return echelon;
	}
	
	
	
	public Matrix<K> toReducedRowEchelonForm(Field<K> f,Function<K, Double> abs, BiPredicate<K, K> equals){
		Matrix<K> reduced = clone();
		
		int numPivot = -1;
		for (int j = 0; j < dimCols() && numPivot<dimRows()-1; j++) {
			
			//searching of the maximum in this column : 
			int rowMax = numPivot+1;
			double max = abs.apply(reduced.get(rowMax, j));
			for (int i = numPivot+1; i < dimRows(); i++) {
				double absij = abs.apply(reduced.get(i, j));
				if(absij>max){
					rowMax = i;
					max = absij;
				}
			}

			K pivot = reduced.get(rowMax, j);
			
			if(!equals.test(pivot, f.zero())) {
				numPivot++;
				
				reduced.scalingRow(rowMax,f.prodInv(pivot), f);
				
				if(rowMax != numPivot) {
					reduced.swapRows(rowMax, numPivot);
				}
				for (int i = 0; i < dimRows(); i++) {
					if(i != numPivot) {
						reduced.addRows(i, numPivot, f.sumInv(reduced.get(i, j)), f);
					}
				}
				
			}
			
		}
		
		return reduced;
	}
	
	

	
	/**
	 * This method calculate the inverse of this matrix for the usual product and the
	 * usual times on square matrices
	 * 
	 * In order to optimize the algorithm, an absolute value on K is needed
	 * (in order to find the biggest element in a row)
	 * 
	 * @param f the field on scalar, has to be a commutative one
	 * @return the inverse of this matrix
	 */
	public Matrix<K> gaussInv(Field<K> f, Function<K, Double> abs,BiPredicate<K, K> equals){
		
		if(!isSquare()) {
			throw new NotSquareMatrixException("inversion of a matrix");
		}
		
		Matrix<K> augmMatrix = augmRow(id(f, dimRows()));
		
		Matrix<K> reduced = augmMatrix.toReducedRowEchelonForm(f, abs, equals);

		List<Matrix<K>> results = reduced.splitRows(dimRows());
		
		if(!results.get(0).equals(id(f, dimRows()), equals)) {
			throw new NotInversibleMatrixException();
		}
		return results.get(1);
		
	}
	
	
	
	public int rank(Field<K> f,BiPredicate<K, K> equals) {
		Matrix<K> echelon = toRowEchelonForm(f, equals);
		
		int rank = dimRows();
		
		for (int i = dimRows()-1; i >= 0; i--) {
			if(echelon.getRow(i).isNull(f, equals)) {
				rank--;
			}
			else {
				return rank;
			}
		}
		
		return rank;
	}
	
	public List<Vector<K>> ker(Field<K> f,Function<K, Double> abs,BiPredicate<K, K> equals){
		
		List<Vector<K>> kerBasis = new ArrayList<Vector<K>>();
		
		Matrix<K> augmented = augmCol(id(f, dimCols()));
		
		Matrix<K> augmReduced = augmented.transpose()
										 .toReducedRowEchelonForm(f, abs, equals)
										 .transpose();
		
		List<Matrix<K>> results = augmReduced.splitCols(dimRows());
		
		for (int j = 0; j < dimCols(); j++) {
			if(results.get(0).getCol(j).isNull(f, equals)) {
				kerBasis.add(results.get(1).getCol(j));
			}
		}
		
		return kerBasis;
	}
	
	/**
	 * TODO
	 * @param f
	 * @param abs
	 * @param equals
	 * @return
	 */
	public List<Vector<K>> im(Field<K> f, Function<K, Double> abs, BiPredicate<K, K> equals){
		
		List<Vector<K>> imBasis = new ArrayList<Vector<K>>();
		
		return imBasis;
	}
	
	
	/**
	 * Warning: this function test the equality of each term in the matrix
	 * by using the equals method on K
	 * If you want to test the equality with an other comparator, uses the equals
	 * which uses a given predicate
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		try {
			Matrix<K> m = (Matrix<K>) obj;
			
			if(!hasDim(m.dims()))
				return false;
			
			for (int i = 0; i < dimRows(); i++) {
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
		
		for (int i = 0; i < dimRows(); i++) {
			for (int j = 0; j < dimCols(); j++) {
				
				if (!equals.test(get(i, j),m.get(i, j))) {
					return false;
				}
			}
		}
		return true;
		
	}
	
	@Override
	public String toString() {
		String s = "(";
		for (int i = 0; i < dimRows(); i++) {
			
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
	
	
	public Matrix<K> clone(){
		Matrix<K> clone = new Matrix<>(dims());
		for (int i = 0; i < dimRows(); i++) {
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
	public static<K> Matrix<K> zeros(Ring<K> r,int... dims){
		Matrix<K> zero = new Matrix<K>(dims);
		for (int i = 0; i < zero.dimRows(); i++) {
			for (int j = 0; j < zero.dimCols(); j++) {
				zero.set(i, j, r.zero());
			}
		}
		return zero;
	}


	public static<K> Matrix<K> ones(Ring<K> r,int... dims){
		Matrix<K> one = new Matrix<K>(dims);
		for (int i = 0; i < one.dimRows(); i++) {
			for (int j = 0; j < one.dimCols(); j++) {
				one.set(i, j, r.one());
			}
		}
		return one;
	}

	/**
	 * The usual one in the vector space of matrices for a ring r
	 * @param <K>
	 * @param dims
	 * @param r
	 * @return
	 */
	public static<K> Matrix<K> id(Ring<K> r,int dim){
		
		Matrix<K> one = new Matrix<K>(dim,dim);
		
		for (int i = 0; i < one.dimRows(); i++) {
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
	
	public static<K> Matrix<K> oneAt(Ring<K> r,int i,int j,int... dims){
		Matrix<K> m = zeros(r, dims);
		m.set(i, j, r.one());
		return m;
	}

	public static<K> List<Matrix<K>> canonicalBasis(Ring<K> r,int... dims){
		List<Matrix<K>> basis = new ArrayList<Matrix<K>>();
		for (int i = 0; i < dims[0]; i++) {
			for (int j = 0; j < dims[1]; j++) {
				basis.add(oneAt(r, i, j, dims));
			}
		}
		return basis;
	}
	
	
	
	
	//######################### below: operations on matrices less usual ###########################
	
	/**
	 * The element-wise product on matrices. <br>
	 * It is the hadamard product
	 * @param matrix
	 * @param r
	 * @return
	 */
	public Matrix<K> hadamardProd(Matrix<K> matrix, Ring<K> r){
		if (!hasDim(matrix.dims())) {
			throw new NotSameDimensionsException(dims(), matrix.dims());
		}
		Matrix<K> hadMatrix = new Matrix<>(dims());
		
		for (int i = 0; i < dimRows(); i++) {
			for (int j = 0; j < dimCols(); j++) {
				hadMatrix.set(i, j, r.prod(get(i, j), matrix.get(i, j)));
			}
		}
		return hadMatrix;	
	}
		
	
	
	
	/**
	 * Calculate the inverse of this matrix for the product on matrices of Hadamard
	 * @param f
	 * @return
	 */
	public Matrix<K> hadamardInv(Field<K> f){
		Matrix<K> inv = new Matrix<>(dims());
		for (int i = 0; i < dimRows(); i++) {
			for (int j = 0; j < dimCols(); j++) {
				
				inv.set(i, j, f.prodInv(get(i, j)));
			}
		}
		return inv;
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





