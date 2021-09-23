package fr.romain.Maths.linalg;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

import fr.romain.Maths.linalg.algstruct.Ring;

public class Vector<K>{
	
	private Object[] values;
	
	public Vector(int n) {
		values = new Object[n];
	}
	
	@SafeVarargs
	public Vector(K... values) {
		this.values = values;
	}
	
	@SuppressWarnings("unchecked")
	public K[] getValues(){
		return (K[]) values;
	}
	
	public K get(int i) {
		return getValues()[i];
	}
	
	public void set(int i, K value) {
		values[i] = value;
	}
	
	public int dim() {
		return values.length;
	}
	
	/**
	 * All vectors can be seen as a matrix
	 * @return
	 */
	public Matrix<K> toRowMat(){
		Matrix<K> matrix = new Matrix<K>(1, dim());
		matrix.setRow(0, this);
		return matrix;
	}
	
	public Matrix<K> toColMat(){
		Matrix<K> matrix = new Matrix<K>(dim(),1);
		matrix.setCol(0, this);
		return matrix;
	}
	
	public Vector<K> plus(Vector<K> v, Ring<K> r) throws IllegalArgumentException{
		if (dim()!=v.dim())
			throw new IllegalArgumentException("the two vectors doesn't have the same dimensions. The sum is thus impossible");
		
		Vector<K> sum = new Vector<K>(dim());
		for (int i = 0; i < sum.dim(); i++) {
			sum.set(i, r.sum(get(i), v.get(i)));
		}
		
		return sum;
	}
	
	/**
	 * It is due to -1*a=-a and a-b := a+(-b)
	 * @param v
	 * @param r
	 * @return
	 */
	public Vector<K> minus(Vector<K> v,Ring<K> r){
		return plus(v.times(r.sumInv(r.one()), r), r);
	}
	
	public Vector<K> times(K k, Ring<K> r){
		Vector<K> times = new Vector<K>(dim());
		for (int i = 0; i < times.dim(); i++) {
			times.set(i, r.prod(k, get(i)));
		}
		return times;
	}
	
	public boolean isNull(Ring<K> r, BiPredicate<K, K> equals) {
		return equals(zero(dim(), r), equals);
	}
	
	public static<K> Vector<K> zero(int dim,Ring<K> r){
		Vector<K> v = new Vector<K>(dim);
		for (int i = 0; i < dim; i++) {
			v.set(i, r.zero());
		}
		return v;
	}
	
	public K scalarProd(Vector<K> v,Ring<K> r) throws IllegalArgumentException{
		if(dim()!=v.dim())
			throw new IllegalArgumentException("the two vectors doesn't have the same dimensions. The scalar product is thus impossible");
		
		K scalarProd = r.zero();
		for (int i = 0; i < dim(); i++) {
			scalarProd = r.sum(scalarProd, r.prod(get(i), v.get(i)));
		}
		
		return scalarProd;
	}
	
	/**
	 * @return the number of zeros before the first non null element
	 */
	public int nbLeftZeros(Ring<K> r, BiPredicate<K, K> equals) {
		int nbLeftZeros = 0;
		for (K k : getValues()) {
			if(equals.test(k, r.zero()))
				nbLeftZeros++;
			else {
				return nbLeftZeros;
			}
		}
		return nbLeftZeros;
	}
	
	@SafeVarargs
	public static<K> K determinant(Ring<K> r,Vector<K>... vectors) throws IllegalArgumentException{
		if(vectors.length!=vectors[0].dim())
			throw new IllegalArgumentException("It must be as much vectors as the dimension");
		return Matrix.byRows(vectors).recDet(r);
	}
	
	public static<K> Vector<K> oneAt(int i,int dim,Ring<K> r){
		Vector<K> v = new Vector<K>(dim);
		for (int k = 0; k < dim; k++) {
			if(k==i) {
				v.set(i, r.one());
			}
			else {
				v.set(i, r.zero());
			}
		}
		return v;
	}
	
	public static<K> List<Vector<K>> canonicalBasis(int dim,Ring<K> r){
		List<Vector<K>> basis = new ArrayList<Vector<K>>();
		for (int i = 0; i < dim; i++) {
			basis.add(oneAt(i, dim, r));
		}
		return basis;
	}
	
	@Override
	public String toString() {
		String s = "(";
		for (int i = 0; i < dim(); i++) {
			s+=get(i)+"";
			if(i<dim()-1)
				s+=", ";
		}
		return s+")";
	}
	
	
	/**
	 * Warning: this function test the equality of each term in the vector
	 * by using the equals method on K
	 * If you want to test the equality with an other comparator, uses the equals
	 * which uses a given predicate
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		try {
			
			Vector<K> v = (Vector<K>) obj;
			
			if(dim()!=v.dim())
				return false;
			
			for (int i = 0; i < dim(); i++) {
				if(!get(i).equals(v.get(i)))
					return false;
			}
			return true;
			
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean equals(Vector<K> v,BiPredicate<K, K> equals) {
		if(dim()!=v.dim())
			return false;
		
		for (int i = 0; i < dim(); i++) {
			if (!equals.test(get(i),v.get(i))) {
				return false;
			}
		}
		return true;
		
	}

}




