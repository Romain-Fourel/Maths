package fr.romain.Maths.linearAlgebra.algebraicObjects;

import fr.romain.Maths.linearAlgebra.algebraicStructure.Ring;

public class Vector<K> {
	
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
	public Matrix<K> toMatrix(){
		Matrix<K> matrix = new Matrix<K>(1, dim());
		
		for (int i = 0; i < dim(); i++) {
			matrix.set(0, i, get(i));
		}
		return matrix;
	}
	
	public static<K> Vector<K> usualSum(Vector<K> v1, Vector<K> v2, Ring<K> f) throws IllegalArgumentException{
		if(v1.dim()!=v2.dim())
			throw new IllegalArgumentException("the two vectors doesn't have the same dimensions. The sum is thus impossible");
		
		Vector<K> sum = new Vector<K>(v1.dim());
		for (int i = 0; i < sum.dim(); i++) {
			sum.set(i, f.sum(v1.get(i), v2.get(i)));
		}
		
		return sum;
	}
	
	public static<K> Vector<K> usualTimes(K k,Vector<K> v1,Ring<K> f){
		Vector<K> times = new Vector<K>(v1.dim());
		for (int i = 0; i < times.dim(); i++) {
			times.set(i, f.prod(k, v1.get(i)));
		}
		return times;
	}
	
	public static<K> Vector<K> usualZero(int dim,Ring<K> f){
		Vector<K> v = new Vector<K>(dim);
		for (int i = 0; i < dim; i++) {
			v.set(i, f.zero());
		}
		return v;
	}
	
	public static<K> K usualScalarProd(Vector<K> v1,Vector<K> v2,Ring<K> f) throws IllegalArgumentException{
		if(v1.dim()!=v2.dim())
			throw new IllegalArgumentException("the two vectors doesn't have the same dimensions. The scalar product is thus impossible");
		
		K scalarProd = f.zero();
		for (int i = 0; i < v1.dim(); i++) {
			scalarProd = f.sum(scalarProd, f.prod(v1.get(i), v2.get(i)));
		}
		
		return scalarProd;
	}
	
	@SafeVarargs
	public static<K> K determinant(Ring<K> r,Vector<K>... vectors) {
		return Matrix.determinant(Matrix.byLines(vectors), r);
	}

}




