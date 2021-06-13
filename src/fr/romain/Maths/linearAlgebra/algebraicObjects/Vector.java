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
	 * WARNING: this minus def works for the reals ring but not for every rings
	 * Be aware of your field when you use this function
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
	
	@SafeVarargs
	public static<K> K determinant(Ring<K> r,Vector<K>... vectors) throws IllegalArgumentException{
		if(vectors.length!=vectors[0].dim())
			throw new IllegalArgumentException("It must be as much vectors as the dimension");
		return Matrix.byLines(vectors).recDet(r);
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

}




