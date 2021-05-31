package fr.romain.Maths.K_VectorSpace.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import fr.romain.Maths.K_VectorSpace.algebraicObjects.Complex;

class ComplexTests {
	
	static Complex z1 = new Complex(6, -4.5);
	static Complex z2 = Complex.byExpForm(Math.PI/4, 2.5);

	@Test
	void equalsTests() {
		
		Complex zero = new Complex(0, 0);
		Complex one = new Complex(1, 0);
		Complex i = new Complex(0, 1);
		
		assertEquals(zero, Complex.zero);
		assertEquals(one, Complex.one);
		assertEquals(i, Complex.i);
		assertEquals(z1, new Complex(6.000000000000000000000000000000000001, -4.50000000000000000000000005));
		
		
	}
	
	@Test
	void toStringTest() {
		System.out.println(Complex.i);
		System.out.println(Complex.i.prod(Complex.i));
		System.out.println(Complex.one);
		System.out.println(Complex.zero);
		System.out.println(z1);
		System.out.println(z2);
	}
	
	@Test
	void prodTest() {

		assertEquals(-1., Complex.i.prod(Complex.i));
		
	}

}
