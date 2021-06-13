package fr.romain.Maths.linearAlgebra.algebraicStructure;

import java.util.function.Function;

import fr.romain.Maths.linearAlgebra.algebraicObjects.Complex;

/**
 * This interface represents the algebraic structure of field on the set E
 * Therefore, we add to the ring the division operator because a field is a ring where
 * every elements except the zero has an inverse on the multiplication operator
 * @author romain
 *
 * @param <K>
 */
public interface Field<K> extends Ring<K> {

	/**
	 * It has to verify:
	 * prod(k,prodInv(k)) == one()
	 * @param k
	 * @return
	 */
	K prodInv(K k);
	
	/**
	 * We can therefore define the division
	 * @return
	 */
	default K div(K k1, K k2) throws IllegalArgumentException{
		if(k2.equals(zero()))
			throw new IllegalArgumentException("Division by zero is not possible due to the construction of a field");
		return prod(k1, prodInv(k2));
	}
	
	public static<K> Field<K> of(Ring<K> r,Function<K, K> prodInv){
		return new Field<K>() {

			@Override
			public K sum(K e1, K e2) {
				return r.sum(e1, e2);
			}

			@Override
			public K prod(K e1, K e2) {
				return r.prod(e1, e2);
			}

			@Override
			public K zero() {
				return r.zero();
			}

			@Override
			public K one() {
				return r.one();
			}

			@Override
			public K sumInv(K e) {
				return r.sumInv(e);
			}

			@Override
			public K prodInv(K k) {
				return prodInv.apply(k);
			}
		};
	}
	
	
	public static Field<Double> realsField(){
		return of(Ring.realsRing(), x->1/x);
	}
	
	public static Field<Complex> complexField(){
		return of(Ring.complexRing(), z->z.inv());
	}
	
	public static Field<Boolean> boolsField(){
		return of(Ring.boolsRing(), e->true);
	}
	
}
