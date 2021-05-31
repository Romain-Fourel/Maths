package fr.romain.Maths.K_VectorSpace.algebraicStructure;

import java.util.function.BinaryOperator;

import fr.romain.Maths.K_VectorSpace.algebraicObjects.Matrix;

/**
 * This interface represents the algebraic structure : algebra over a Field
 * This structure extends the k-vector space
 * Indeed, we define a k-algebra by : (E,+,.,*) with (E,+,.) a k-vector space
 * @author Romain
 * @param K the scalar set
 * @param E the vector space
 *
 */
public interface Algebra<K,E> extends VectorSpace<K, E>{
	
	/**
	 * This operator only has to be a binary operation on the set E (E X E -> E)
	 * And, more specifically, it has to be bilinear (left-distributive and right-distributive on + )
	 * @param e1
	 * @param e2
	 * @return
	 */
	E prod(E e1, E e2);
	
	/**
	 * The power operator follows directly from the definition of the product operator 
	 * 
	 * This way to calculate the power is due to the prod operation is bilinear
	 * 
	 * @param e the vector to be raised to power
	 * @param k the number of times e is multiplied by itself
	 * @return the product of e by itself k times
	 */
	default E pow(E e, int k) {
		if(k==1)
			return e;
		if (k%2==0) {
			return prod(pow(e,k/2),pow(e, k/2));
		}
		
		return prod(prod(pow(e,(k-1)/2),pow(e, (k-1)/2)),e);
		
	}
	
	
	
	/**
	 * In an algebra, the neutral element for the multiplicative operation is not necessary,
	 * So, this function can return null if the operation prod doesn't make any neutral element
	 * @return null or the neutral element of the operation "prod"
	 */
	E one();
	
	public static<K,E> Algebra<K, E> of(VectorSpace<K, E> vs, BinaryOperator<E> prod, E one, Field<K> f){
		return new Algebra<K, E>() {

			@Override
			public E sum(E e1, E e2) {
				return vs.sum(e1, e2);
			}

			@Override
			public E times(K k, E e) {
				return vs.times(k, e);
			}

			@Override
			public E zero() {
				return vs.zero();
			}

			@Override
			public int dim() {
				return vs.dim();
			}

			@Override
			public E prod(E e1, E e2) {
				return prod.apply(e1, e2);
			}

			@Override
			public E one() {
				return one;
			}

			@Override
			public Field<K> field() {
				return f;
			}
		};
	}

	
	public static<K> Algebra<K, Matrix<K>> matricesAlgebra(int dim,Field<K> f){
		return Algebra.of(VectorSpace.matricesVectorSpace(dim, dim, f), 
				          (m1,m2)->Matrix.usualProd(m1, m2, f),
				          Matrix.usualOne(new int[] {dim,dim}, f), 
				          f);
	}
}








