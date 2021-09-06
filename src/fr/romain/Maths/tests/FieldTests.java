package fr.romain.Maths.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import fr.romain.Maths.linalg.algebraicStructure.Field;
import fr.romain.Maths.utils.Reals;

class FieldTests {

	@Test
	void RealsTest() {
		Field<Double> f = Field.realsField();
		for (double i = 0; i < 10; i++) {
			for (double j = 0; j < 10; j++) {
				assertEquals(i+j, f.sum(i, j));
				assertEquals(i*j, f.prod(i, j));
				assertEquals(i-j, f.minus(i, j));
				if(j!=0) {
					assertTrue(Reals.equals(i/j, f.div(i, j)));
				}		
			}
			assertEquals(-i, f.sumInv(i));
			assertEquals(1/i, f.prodInv(i));
		}
		assertEquals(0., f.zero());
		assertEquals(1., f.one());
		assertThrows(IllegalArgumentException.class, ()->f.div(5., f.zero()));
		
	}

}
