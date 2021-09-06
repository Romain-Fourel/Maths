package fr.romain.Maths.linalg.algstruct;

import java.util.List;
import java.util.function.BiFunction;

import fr.romain.Maths.linalg.objects.Complex;
import fr.romain.Maths.linalg.objects.Matrix;
import fr.romain.Maths.linalg.objects.Vector;
import fr.romain.Maths.utils.Reals;

public interface Euclidean<E> extends VectorSpace<Double, E> {

	/**
	 * The scalar product has to verify:
	 * -> scalarProd(e1,e2) == scalarprod(e2,e1)
	 * -> scalarProd(e,e) == 0 ==> e = zero()
	 * -> scalarProd(e,e) >=0
	 * @param e1
	 * @param e2
	 * @return
	 */
	double scalarProd(E e1, E e2);
	
	default double norm(E e) {
		return Math.sqrt(scalarProd(e, e));
	}
	 
	default double distance(E e1, E e2) {
		return norm(minus(e1, e2));
	}
	
	default double cos(E e1, E e2) {
		return scalarProd(e1, e2)/(norm(e1)*norm(e2));
	}
	
	default double sin(E e1,E e2) {
		return Math.sqrt(1-cos(e1, e2));
	}
	
	default boolean areOrthogonal(E e1,E e2) {
		return Reals.equals(scalarProd(e1, e2),0.);
	}
	
	default boolean areColinear(E e1,E e2) {
		return Reals.equals(cos(e1, e2), 1.);
	}
	
	/**
	 * 
	 * @param e1
	 * @param e2
	 * @return the rotation matrix defined by the angle between the two vectors
	 */
	default Matrix<Double> rotationMatrix(E e1,E e2) {
		
		return new Matrix<Double>(new Double[][] {{cos(e1,e2),-sin(e1,e2)},
												  {sin(e1,e2),cos(e1,e2)}});
		
	}
	
	public static<E> Euclidean<E> of(VectorSpace<Double, E> vs, BiFunction<E, E, Double> scalarProd, Field<Double> f){
		return new Euclidean<E>() {

			@Override
			public E sum(E e1, E e2) {
				return vs.sum(e1, e2);
			}

			@Override
			public E times(Double k, E e) {
				return vs.times(k, e);
			}

			@Override
			public E zero() {
				return vs.zero();
			}

			@Override
			public double scalarProd(E e1, E e2) {
				return scalarProd.apply(e1, e2);
			}

			@Override
			public Field<Double> field() {
				return f;
			}

			@Override
			public List<E> getBasis() {
				return vs.getBasis();
			}
		};
	}
	
	
	public static Euclidean<Matrix<Double>> matricesEuclidean(int dimLines, int dimCols){
		return of(VectorSpace.matricesVS(dimLines, dimCols, Field.realsField()), 
				 (m1,m2)->m1.scalarProd(m2, Field.realsField()),
				 Field.realsField());
	}
	
	public static Euclidean<Vector<Double>> vectorsEuclidean(int dim){
		return of(VectorSpace.vectorsVS(dim, Field.realsField()),
				  (v1,v2)->v1.scalarProd(v2,Field.realsField()), 
				  Field.realsField());
	}
	
	
	public static Euclidean<Complex> complexEuclidean(){
		return of(VectorSpace.complexVS(), 
				  (z1, z2)->z1.getReal()*z2.getReal()+z1.getIm()*z2.getIm(),
				  Field.realsField());
	}
	
}








