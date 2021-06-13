package fr.romain.Maths.linearAlgebra.algebraicStructure;

import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;

import fr.romain.Maths.linearAlgebra.algebraicObjects.Complex;
import fr.romain.Maths.linearAlgebra.algebraicObjects.Matrix;
import fr.romain.Maths.linearAlgebra.algebraicObjects.Vector;

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
	 * WARNING: Use this default sum inv only if it works in your vector space
	 * Otherwise, it can be overwritten
	 * @param e1
	 * @param e
	 * @return
	 */
	default E sumInv(E e) {
		K minusOne =  field().sumInv(field().one());
		return times(minusOne, e);
	}
	

	default E minus(E e1,E e2) {
		return sum(e1, sumInv(e2));
	}
	
	/**
	 * 
	 * @return the dimension of this vector space
	 */
	int dim();
	
	
	public static<K,E> VectorSpace<K, E> of(BinaryOperator<E> sum, BiFunction<K, E, E> times, 
											E zero,Field<K> f,int dim, Function<E, E> sumInv){
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
			
			@Override
			public E sumInv(E e) {
				return sumInv.apply(e);			
			}
		};
		
	}

	
	public static<K,E> VectorSpace<K, E> of(BinaryOperator<E> sum, BiFunction<K, E, E> times, 
			E zero,Field<K> f,int dim){
			
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
			public Field<K> field() {
				return f;
			}

			@Override
			public int dim() {
				return dim;
			}
		};
	}

	
	
	public static<K> VectorSpace<K, Matrix<K>> matricesVS(int dimLines, int dimCols, Field<K> f){
		return of((e1,e2)->e1.plus(e2,f),
						   (k,e)->e.times(k,f), 
						   Matrix.zero(f,dimLines,dimCols),
						   f,
						   dimLines*dimCols);
	}
	
	
	public static<K> VectorSpace<K, Vector<K>> vectorsVS(int dim, Field<K> f){
		return of((v1,v2)->v1.plus(v2,f),
							  (k,v)->v.times(k,f), 
							  Vector.zero(dim, f), 
							  f, 
							  dim);
	}
	
	public static VectorSpace<Double, Complex> complexVS(){
		Field<Double> f = Field.realsField();
		return of((z1,z2)->z1.plus(z2),
				              (r,z)->z.prod(Complex.of(r)),
				              Complex.zero,
				              f,
				              2);
	}
	
}





