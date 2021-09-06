package fr.romain.Maths.linalg.algstruct;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;

import fr.romain.Maths.linalg.Matrix;
import fr.romain.Maths.linalg.Vector;
import fr.romain.Maths.utils.Complex;

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
	 * Default implementation is due to -1*a = -a
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
	 * @return a basis of this vector space. 
	 * <br> It is better that if the basis is a canonical one
	 */
	List<E> getBasis();
	
	/**
	 * 
	 * @return the dimension of this vector space
	 */
	default int dim() {
		return getBasis().size();
	}
	
	
	public static<K,E> VectorSpace<K, E> of(BinaryOperator<E> sum, BiFunction<K, E, E> times, 
											E zero,Field<K> f,List<E> basis, Function<E, E> sumInv){
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
			public E sumInv(E e) {
				return sumInv.apply(e);			
			}

			@Override
			public List<E> getBasis() {
				return basis;
			}
		};
		
	}

	
	public static<K,E> VectorSpace<K, E> of(BinaryOperator<E> sum, BiFunction<K, E, E> times, 
			E zero,Field<K> f,List<E> basis){
			
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
			public List<E> getBasis() {
				return basis;
			}

		};
	}

	
	
	public static<K> VectorSpace<K, Matrix<K>> matricesVS(int dimLines, int dimCols, Field<K> f){
		return of((e1,e2)->e1.plus(e2,f),
						   (k,e)->e.times(k,f), 
						   Matrix.zeros(f,dimLines,dimCols),
						   f,
						   Matrix.canonicalBasis(f, dimLines*dimCols));
	}
	
	
	public static<K> VectorSpace<K, Vector<K>> vectorsVS(int dim, Field<K> f){
		return of((v1,v2)->v1.plus(v2,f),
							  (k,v)->v.times(k,f), 
							  Vector.zero(dim, f), 
							  f, 
							  Vector.canonicalBasis(dim, f));
	}
	
	public static VectorSpace<Double, Complex> complexVS(){
		Field<Double> f = Field.realsField();
		return of((z1,z2)->z1.plus(z2),
				              (r,z)->z.prod(Complex.of(r)),
				              Complex.zero,
				              f,
				              List.of(Complex.one, Complex.i));
	}
	
}





