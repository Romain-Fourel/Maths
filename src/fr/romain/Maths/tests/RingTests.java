package fr.romain.Maths.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import fr.romain.Maths.linalg.algebraicStructure.Ring;

class RingTests {

	@Test
	void RealsTests() {
		Ring<Double> r = Ring.realsRing();
		
		for (double i = 0; i < 10; i++) {
			for (double j = 0; j < 10; j++) {
				assertEquals(i+j, r.sum(i, j));
				assertEquals(i*j, r.prod(i, j));
				assertEquals(i-j, r.minus(i, j));
			}
			assertEquals(-i, r.sumInv(i));
		}
		assertEquals(0., r.zero());
		assertEquals(1., r.one());
		
		assertEquals(6*5*11, r.prod(6.,5.,11.));
		assertEquals(5*4*7*3*6, r.prod(5.,4.,7.,3.,6.));
		
		assertEquals(Math.pow(5, 6), r.pow(5., 6));
		assertEquals(5, r.pow(5., 1));
		assertEquals(1, r.pow(Math.PI, 0));
		
	}

}
