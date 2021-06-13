package fr.romain.Maths.linearAlgebra.algebraicStructure;

import java.util.function.BinaryOperator;
import java.util.function.Function;

import fr.romain.Maths.linearAlgebra.algebraicObjects.Complex;

/**
 * This interface represents the algebraic structure of Ring on the set K.
 * This ring doesn't aim to be necessarily a commutative one
 * 
 * In order to avoid having to create several identical interfaces, we allow ourselves
 * to implement in this file any structure composed of two binary operation
 *
 * @param <K> the set on which we add two laws: + and x
 */
public interface Ring<K> {
	
	
	/**
	 * This function has to be associative
	 * -> sum(e1,sum(e2,e3)) == sum(sum(e1,e2),e3)
	 * It also has to be commutative:
	 * -> sum(e1,e2) == sum(e2,e1)
	 * @param e1
	 * @param e2
	 * @return e1 + e2 with the plus defined by the binaryOperator at the construction of the ring.
	 */
	K sum(K e1,K e2);
	
	
	@SuppressWarnings("unchecked")
	default K sum(K... values) {
		K sum = zero();
		for (K k : values) {
			sum = sum(sum, k);
		}
		return sum;
	}
	
	/**
	 * This function has to be associative
	 * -> prod(e1,prod(e2,e3)) == prod(prod(e1,e2),e3)
	 * In order to make a commutative field, it also has to be commutative (facultative)
	 * -> prod(e1,e2) == prod(e2,e1)
	 * This operator has to be left-distributive and right-distributive on the + operator of the field:
	 * -> prod(e1,sum(e2,e3)) == sum(prod(e1,e2),prod(e1,e3))
	 * -> prod(sum(e1,e2),e3) == sum(prod(e1,e3),prod(e2,e3))
	 * @param e1
	 * @param e2
	 * @return e1 * e2 with the prod operator defined by the binaryOperator at the construction of the field.
	 */
	K prod(K e1, K e2);
	
	
	@SuppressWarnings("unchecked")
	default K prod(K... values) {
		K prod = one();
		for (K k : values) {
			prod = prod(prod, k);
		}
		return prod;
	}
	
	/**
	 * @param k
	 * @return
	 */
	default K pow(K e,int k) {
		if(k==0) {
			return one();
		}
		if(k==1)
			return e;
		if (k%2==0) {
			return prod(pow(e,k/2),pow(e, k/2));
		}
		
		return prod(prod(pow(e,(k-1)/2),pow(e, (k-1)/2)),e);
	}
	
	
	/**
	 * This function returns the neutral element for the "+" operator in the ring
	 * It has to verify:
	 * -> for any element e1 in E, there is one other element e2 in E where sum(e1,e2) == zero()
	 * ->for any e in E, sum(e,zero()) == sum(zero(),e) == e
	 * @return the neutral element for the "+" operator in the field
	 */
	K zero();
	
	/**
	 * This function returns the neutral element for the "*" operator in the ring
	 * It has to verify:
	 * -> for any element e1 in E, there is one other element e2 in E where prod(e1,e2) == one()
	 * ->for any e in E, prod(e,one()) == prod(one(),e) == e
	 * @return the neutral element for the "*" operator in the field
	 */
	K one();
	
	
	/**
	 * This function returns the inverse of e for the sum operation
	 * It has to verify:
	 * -> sum(e,sumInv(e)) == zero()
	 * @param e
	 * @return
	 */
	K sumInv(K e);
	
	default K minus(K e1, K e2) {
		return sum(e1, sumInv(e2));
	}
	
	
	
	public static<K> Ring<K> of(BinaryOperator<K> sum,BinaryOperator<K> prod, K zero, K one,Function<K, K> sumInv){
		return new Ring<K>() {

			@Override
			public K sum(K e1, K e2) {
				return sum.apply(e1, e2);	
			}

			@Override
			public K prod(K e1, K e2) {
				return prod.apply(e1, e2);
			}

			@Override
			public K zero() {
				return zero;
			}

			@Override
			public K one() {
				return one;
			}

			@Override
			public K sumInv(K e) {
				return sumInv.apply(e);
			}
		};
	}
	

	
	public static Ring<Double> realsRing(){
		return of(Double::sum, (e1, e2)->e1*e2, 0.,1.,e->-1*e);
	}
	
	public static Ring<Complex> complexRing(){
		return of((z1,z2)->z1.plus(z2),
				  (z1,z2)->z1.prod(z2),
				  Complex.zero,
				  Complex.one,
				  z->z.prod(Complex.of(-1)));
	}
	
	
	public static Ring<Boolean> boolsRing(){
		return of((e1,e2)->e1||e2, (e1,e2)->e1&&e2, false, true,e->false);
	}
	
	
	/**
	 * WARNING: this structure is not a ring!
	 * Therefore, we put the sum inv on null
	 * It is only an half-ring and, particularly, a Dioïde
	 * This structure is however really interessant because it is useful to
	 * study Petri Networks
	 * @return
	 */
	public static Ring<Double> realsDioideMax(){
		return of(Math::max, Double::sum, Double.NEGATIVE_INFINITY, 0.,null);
	}
	
	public static Ring<Double> realsDioideMin(){
		return of(Math::min, Double::sum, Double.POSITIVE_INFINITY, 0.,null);
	}
	


}





