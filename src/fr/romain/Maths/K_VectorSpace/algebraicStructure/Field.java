package fr.romain.Maths.K_VectorSpace.algebraicStructure;

import java.util.function.BinaryOperator;

/**
 * This interface represents the algebraic structure of Field on the set E.
 * This field doesn't aim to necessarily a commutative one
 *
 * @param <K> the set on which we add two laws: + and x
 */
public interface Field<K> {
	
	
	/**
	 * This function has to be associative
	 * -> sum(e1,sum(e2,e3)) == sum(sum(e1,e2),e3)
	 * It also has to be commutative:
	 * -> sum(e1,e2) == sum(e2,e1)
	 * @param e1
	 * @param e2
	 * @return e1 + e2 with the plus defined by the binaryOperator at the construction of the field.
	 */
	K sum(K e1,K e2);
	
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
	
	
	/**
	 * This function returns the neutral element for the "+" operator in the field
	 * It has to verify:
	 * -> for any element e1 in E, there is one other element e2 in E where sum(e1,e2) == zero()
	 * ->for any e in E, sum(e,zero()) == sum(zero(),e) == e
	 * @return the neutral element for the "+" operator in the field
	 */
	K zero();
	
	/**
	 * This function returns the neutral element for the "*" operator in the field
	 * It has to verify:
	 * -> for any element e1 in E, there is one other element e2 in E where prod(e1,e2) == one()
	 * ->for any e in E, prod(e,one()) == prod(one(),e) == e
	 * @return the neutral element for the "*" operator in the field
	 */
	K one();
	
	public static<K> Field<K> of(BinaryOperator<K> sum,BinaryOperator<K> prod, K zero, K one){
		return new Field<K>() {

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
		};
	}
	
	public static Field<Double> realField(){
		return of(Double::sum, (e1, e2)->e1*e2, 0.,1.);
	}
	
	/**
	 * WARNING: this structure is not a field!
	 * It is only an half-ring and, particularly, a Dioïde
	 * This structure is however really interessant because it is useful to
	 * study Petri Networks
	 * @return
	 */
	public static Field<Double> realDioide(){
		return of(Math::max, Double::sum, Double.NEGATIVE_INFINITY, 0.);
	}
	
	public static Field<Boolean> boolField(){
		return of((e1,e2)->e1||e2, (e1,e2)->e1&&e2, false, true);
	}


}





