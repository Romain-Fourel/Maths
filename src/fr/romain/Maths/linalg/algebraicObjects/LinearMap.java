package fr.romain.Maths.linalg.algebraicObjects;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;

import fr.romain.Maths.linalg.algebraicStructure.VectorSpace;

/**
 * 
 * @author romain
 * 
 * <p style="color:orange"> WARNING : NOT OPERATIONNAL </p>
 *
 * @param <V>
 * @param <W>
 * @param <K>
 */
public class LinearMap<V,W,K> {
	
	Function<V, W> f;
	
	VectorSpace<K, V> vs1;
	VectorSpace<K, W> vs2;

	public LinearMap(Function<V, W> linearMap,VectorSpace<K, V> vs1, VectorSpace<K, W> vs2) {
		f = linearMap;
	}
	
	public W apply(V v) {
		return f.apply(v);
	}
	
	public LinearMap<V, W, K> plus(LinearMap<V, W, K> l){
		return new LinearMap<V,W,K>(v->vs2.sum(apply(v), l.apply(v)), vs1, vs2);
	}
	

	/**
	 * 
	 * @return the matrix of this linear map in basis given by vs1 and vs2
	 */
	public Matrix<K> getMat(){
		Matrix<K> m = new Matrix<K>(vs1.dim(), vs2.dim());
		for (int i = 0; i < m.dimRows(); i++) {
			
			W w = apply(vs1.getBasis().get(i));
			
			for (int j = 0; j < m.dimCols(); j++) {
				//TODO: le but, projeter w dans chaque élément de la base de vs2
			}
		}
		return m;
	}
	
	public List<V> ker(Function<K, Double> abs,BiPredicate<K, K> equals){
		
		List<Vector<K>> vecKer = getMat().ker(vs1.field(), abs, equals); 
		
		List<V> ker = new ArrayList<V>();
		
		for (Vector<K> v : vecKer) {
			V value = vs1.zero();
			for (int i = 0; i < v.dim(); i++) {
				value = vs1.sum(value, vs1.times(v.get(i),vs1.getBasis().get(i)));
			}
			ker.add(value);
		}
		return ker;
	}

}






