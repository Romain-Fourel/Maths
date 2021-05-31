package fr.romain.Maths.K_VectorSpace.algebraicStructure;

import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

import fr.romain.Maths.K_VectorSpace.algebraicObjects.Matrix;

/**
 * This interface represents the K-vector space algebraic structure on the set E
 * 
 * @param <K> the scalar bound to the vector space on E
 * @param <E> the vectors set on which we add two operations : "." and "+"
 */
public interface VectorSpace<K,E> {
	
	
	/**
	 * This function represents the sum of two vectors in a vector space.
	 * It has to be associative
	 * -> sum(e1,sum(e2,e3)) == sum(sum(e1,e2),e3)
	 * It also has to be commutative:
	 * -> sum(e1,e2) == sum(e2,e1)
	 * @param e1
	 * @param e2
	 * @return e1 + e2 with the plus defined by the binaryOperator at the construction of the field.
	 */
	E sum(E e1,E e2);
	
	/**
	 * This function represents the scalar multiplication in a vector space and has therefore
	 * to follow restrictions of a such operator.
	 * @param e
	 * @param k
	 * @return
	 */
	E times(K k,E e);
	
	/**
	 * This function returns the neutral element for the "+" operator in the field
	 * It has to verify:
	 * -> for any element e1 in E, there is one other element e2 in E where sum(e1,e2) == zero()
	 * ->for any e in E, sum(e,zero()) == sum(zero(),e) == e
	 * @return the neutral element for the "+" operator in the field
	 */
	E zero();
	
	/**
	 * @return the field associated to the vector space
	 */
	Field<K> field();
	
	/**
	 * 
	 * @return the dimension of this vector space
	 */
	int dim();
	
	
	public static<K,E> VectorSpace<K, E> of(BinaryOperator<E> sum, BiFunction<K, E, E> times, E zero,Field<K> f,int dim){
		return new VectorSpace<K, E>() {

			@Override
			public E sum(E e1, E e2) {
				return sum.apply(e1, e2);
			}

			@Override
			public E times(K k, E e) {
				return times.apply(k, e);
			}

			@Override
			public E zero() {
				return zero;
			}

			@Override
			public int dim() {
				return dim;
			}

			@Override
			public Field<K> field() {
				return f;
			}
		};
		
	}
	
	 
	public static<K> VectorSpace<K, Matrix<K>> matricesVectorSpace(int dimLines, int dimCols, Field<K> f){
		return VectorSpace.of((e1,e2)->Matrix.usualSum(e1, e2, f),
							  (k,e)->Matrix.usualTimes(k, e, f), 
							  Matrix.usualZero(new int[] {dimLines,dimCols}, f),
							  f,
							  dimLines*dimCols);
	}
}





