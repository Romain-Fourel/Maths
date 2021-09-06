package fr.romain.Maths.linalg.algebraicStructure;

/**
 * This interface is used to represents a vector space to which we add a norm
 * This interface is useful for user who want to work with distances but not with angles <br>
 * 
 * <p style="color:red"> WARNING : MAYBE USELESS </p>
 * 
 * @author romain
 *
 * @param <K> the scalar of the vector space
 * @param <E> the vectors of the vector space
 */
public interface NormedVS<K,E> extends VectorSpace<K, E> {
	
	/**
	 * In order to make a normed vector space, we have to add a absolute value to the scalar field
	 * @param k
	 * @return
	 */
	double abs(K k);
	
	/**
	 * the norm has to verify:
	 * -> norm(e) == 0 ==> e == zero()
	 * -> norm(times(k,e)) == abs(k)*norm(e)
	 * -> norm(sum(e1,e2)) <= sum(norm(e1),norm(e2))
	 * @param e
	 * @return
	 */
	double norm(E e);
	
	

}
