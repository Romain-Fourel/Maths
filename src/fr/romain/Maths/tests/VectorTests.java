package fr.romain.Maths.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.function.BiPredicate;

import org.junit.jupiter.api.Test;

import fr.romain.Maths.linalg.algstruct.Field;
import fr.romain.Maths.linalg.objects.Vector;
import fr.romain.Maths.utils.Reals;

class VectorTests {
	
	public final static Field<Double> f = Field.realsField();
	public final static BiPredicate<Double, Double> p = Reals::equals;
	
	public final static Vector<Double> zero = Vector.zero(3, f);

	@Test
	void timesTest() {
		Vector<Double> v = new Vector<Double>(1.,5.,3.,4.);
		Vector<Double> v3 = new Vector<Double>(1.*3,5.*3,3.*3,4.*3);
		
		assertTrue(v3.equals(v.times(3., f), p));
		
		assertTrue(zero.equals(zero.times(1254., f), p));
	}
	
	@Test
	void plusTest() {
		Vector<Double> v1 = new Vector<Double>(1.,2.,4.,8.);
		Vector<Double> v2 = new Vector<Double>(2.,5.,-4.,0.);
		
		Vector<Double> v3 = new Vector<Double>(3.,7.,0.,8.);
		Vector<Double> v4 = new Vector<Double>(1.,3.,-8.,-8.);
		
		assertTrue(v3.equals(v1.plus(v2, f), p));
		
	}
	
	@Test
	void minusTest() {
		Vector<Double> v1 = new Vector<Double>(1.,2.,4.,8.);
		Vector<Double> v2 = new Vector<Double>(2.,5.,-4.,0.);
		
		Vector<Double> v4 = new Vector<Double>(1.,3.,-8.,-8.);
		
		assertTrue(v4.equals(v2.minus(v1, f), p));
	}
	
	@Test
	void nbLeftZerosTest() {
		Vector<Double> v1 = new Vector<Double>(0.,0.,0.,8.);
		Vector<Double> v2 = new Vector<Double>(1.,0.,0.,8.);
		Vector<Double> v3 = new Vector<Double>(0.,0.,0.,0.);
		Vector<Double> v4 = new Vector<Double>(0.,-154.,0.000000005,8.);
		Vector<Double> v5 = new Vector<Double>(0.,0.,0.,0.,0.,0.,0.,0.,0.,0.,0.,0.,8.);
		Vector<Double> v6 = new Vector<Double>(0.,0.,0.,2.,0.,0.,0.,0.,0.,0.,0.,0.,8.);
		
		assertEquals(3, v1.nbLeftZeros(f, p));
		assertEquals(0, v2.nbLeftZeros(f, p));
		assertEquals(4, v3.nbLeftZeros(f, p));
		assertEquals(1, v4.nbLeftZeros(f, p));
		assertEquals(12, v5.nbLeftZeros(f, p));
		assertEquals(3, v6.nbLeftZeros(f, p));
		
	}

}
