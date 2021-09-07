package fr.romain.Maths.linalg.algstruct;

import java.util.function.BinaryOperator;
import java.util.function.Function;

import fr.romain.Maths.linalg.Matrix;
import fr.romain.Maths.utils.Complex;

/**
 * <p>
 * This interface represents the algebraic structure of Ring on the set K.
 * This ring doesn't aim to be necessarily a commutative one
 * </p>
 * <p>
 * In order to avoid having to create several identical interfaces, we allow ourselves
 * to implement in this file any structure composed similar to rings (like pseudo-ring and half-ring)
 * </p>
 *
 * @param <K> the set on which we add two laws: + and x
 */
public interface Ring<K> {
	
	
	/**
	 * <li>This function has to be associative <br>
	 *  -> sum(e1,sum(e2,e3)) == sum(sum(e1,e2),e3) <br>
	 * <li>It also has to be commutative: <br>
	 *  -> sum(e1,e2) == sum(e2,e1) <br>
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
	 * <ul>
	 * <li>This function has to be associative <br>
	 * -> prod(e1,prod(e2,e3)) == prod(prod(e1,e2),e3)
	 * <li>This operator has to be left-distributive and right-distributive on the + operator of the field:<br>
	 * -> prod(e1,sum(e2,e3)) == sum(prod(e1,e2),prod(e1,e3))<br>
	 * -> prod(sum(e1,e2),e3) == sum(prod(e1,e3),prod(e2,e3))
	 * <li>In order to make a commutative field, it also has to be commutative (facultative)<br>
	 * -> prod(e1,e2) == prod(e2,e1)
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
	 * ->for any e in E, prod(e,one()) == prod(one(),e) == e <br>
	 * If one() is not defined, the Ring will be a pseudo-ring (like matrices ring)
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
	
		
	public static<K> Ring<K> of(BinaryOperator<K> sum,BinaryOperator<K> prod, 
								K zero, K one,Function<K, K> sumInv){
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
	 * If dims[0] != dims[1], this ring is a pseudo-ring because it has no neutral element for the
	 * multiplication
	 * @param <T>
	 * @param r
	 * @param dims
	 * @return
	 */
	public static<T> Ring<Matrix<T>> matricesRing(Ring<T> r,int... dims){
		if(dims[0]==dims[1]) {
			return squareMatricesRing(r, dims[0]);
		}
		return of((Matrix<T> m1,Matrix<T> m2)->m1.plus(m2, r), 
				(Matrix<T> m1,Matrix<T> m2)->m1.prod(m2, r), 
				Matrix.zeros(r, dims), 
				null, 
				(Matrix<T> m)->m.times(r.sumInv(r.one()), r));
	}
	
	public static<T> Ring<Matrix<T>> squareMatricesRing(Ring<T> r,int dim){
		return of((Matrix<T> m1,Matrix<T> m2)->m1.plus(m2, r), 
				(Matrix<T> m1,Matrix<T> m2)->m1.prod(m2, r), 
				Matrix.zeros(r, dim,dim), 
				Matrix.id(r, dim), 
				(Matrix<T> m)->m.times(r.sumInv(r.one()), r));
	}
	
	
	/**
	 * <strong>WARNING</strong>: this structure is not a ring! <br>
	 * Therefore, we put the sum inv on null <br>
	 * It is only an half-ring and, particularly, a Dioïde. <br>
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





