package fr.romain.Maths.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.function.BiPredicate;

import org.junit.jupiter.api.Test;

import fr.romain.Maths.linearAlgebra.Reals;
import fr.romain.Maths.linearAlgebra.algebraicObjects.Matrix;
import fr.romain.Maths.linearAlgebra.algebraicStructure.Field;

class MatrixTests {
	
	public final static Field<Double> f = Field.realsField();
	
	public final static BiPredicate<Double, Double> p = Reals::equals;
	
	public static Matrix<Double> A = new Matrix<Double>(new Double[][] {{2.,1.},
																	    {0.,-1.}});
	
	public static Matrix<Double> B = new Matrix<Double>(new Double[][] {{4.,3.}});
	
	public static Matrix<Double> C = new Matrix<Double>(new Double[][] {{1.5},
																	    {-2.}});
	
	public static Matrix<Double> m1 = new Matrix<Double>(new Double[][] {{1.,2.,0.},
	      														         {-1.,3.,4.}});

	public static Matrix<Double> m2 = new Matrix<Double>(new Double[][] {{1.,4.,-1.},
		  													             {-2.,0.,0.},
		  													             {-3.,1.,7.}});
	
	public static Matrix<Double> m3 = new Matrix<Double>(new Double[][] {{1.,2.,0.}});
	
	public static Matrix<Double> m4 = new Matrix<Double>(new Double[][] {{4.},
														  				 {5.},
														  				 {1.}});
	
	public static Matrix<Double> m5 = new Matrix<Double>(new Double[][] {{3.},
		  																 {2.},
		  																 {1.}});
	
	public static Matrix<Double> m6 = new Matrix<Double>(new Double[][] {{4.,1.,1.}});
	
	public static Matrix<Double> m7 = new Matrix<Double>(new Double[][] {{1.,4.,7.},
		  													    		 {120.,84.,-72.},
		  													    		 {7.,Math.PI,42.}});
	
	public static Matrix<Double> zero = Matrix.zero(f,2,2);
	public static Matrix<Double> id2 = Matrix.one(f,2,2);
	public static Matrix<Double> id3  = Matrix.one(f, 3,3);

	@Test
	void Timestest() {
		
		assertTrue(A.equals(A.times(1.,f), p));
		
		Matrix<Double> A3 = A.times(3.,f);
		assertTrue(A.hasDim(A3.dims()));
		for (int i = 0; i < A.dimLines(); i++) {
			for (int j = 0; j < A.dimCols(); j++) {
				assertEquals(A.get(i, j)*3, A3.get(i, j));
			}
		}
		
		assertTrue((zero.equals(A.times(0., f),p)));
		
	}
	
	@Test
	void plusTest() {
		Matrix<Double> plus = new Matrix<Double>(new Double[][] {{4.,2.},{0.,-2.}});
		assertTrue(plus.equals(A.plus(A, f), p));
		assertTrue(A.equals(A.plus(zero, f), p));
		
		assertThrows(Matrix.NotSameDimensionsException.class, ()->A.plus(B,f));
	}
	
	@Test
	void prodTest() {
	
		
		Matrix<Double> expected = new Matrix<Double>(new Double[][] {{-3.,4.,-1.},
			 														 {-19.,0.,29.}});
		
		assertTrue(m1.prod(m2, f).equals(expected, p));
		
		Matrix<Double> exp2 = new Matrix<Double>(new Double[][] {{14.}});
		
		assertTrue(m3.prod(m4, f).equals(exp2, p));
		
		Matrix<Double> exp3 = new Matrix<Double>(new Double[][] {{12.,3.,3.},
			  {8.,2.,2.},
			  {4.,1.,1.}});
		
		assertTrue(m5.prod(m6, f).equals(exp3, p));
		
		assertTrue(m7.prod(id3, f).equals(m7, p));
		assertTrue(id3.prod(m7, f).equals(m7, p));
		
		assertThrows(Matrix.NotMultipliableMatricesException.class,()->A.prod(B, f));
		
	}

	
	@Test
	void augmentedTest() {
		Matrix<Double> augmented = A.augmented(C);
		
		Matrix<Double> expected = new Matrix<Double>(new Double[][] {{2.,1.,1.5},
		    														 {0.,-1.,-2.0}});
		assertTrue(expected.equals(augmented, p));
		
		assertThrows(Matrix.NotMatchingDimensionsException.class, ()->A.augmented(id3));
				
	}
	

	@Test
	void splitTest() {
		Matrix<Double> toExtract = new Matrix<Double>(new Double[][] {{2.,1.,1.5},
			 														  {0.,-1.,-2.0}});
		
		List<Matrix<Double>> l = toExtract.split(2);
		assertTrue(l.get(0).equals(A, p));
		assertTrue(l.get(1).equals(C, p));
	}
	
	@Test
	void gaussReduceTest() {
		Matrix<Double> gauss = A.gaussReduce(f, Math::abs, p);
		
		assertTrue(gauss.equals(id2, p));
		
		Matrix<Double> b = new Matrix<Double>(new Double[][] {{6.8,-200.},
															  {0.02,5.23}});
		
		assertTrue(b.gaussReduce(f, Math::abs, p).equals(id2, p));
		
		Matrix<Double> gauss2 = b.augmented(C).gaussReduce(f, Math::abs, p);
		
		assertTrue(gauss2.split(2).get(0).equals(id2, p));
		
		Matrix<Double> res = gauss2.split(2).get(1);
		assertTrue(Reals.equals(C.get(0, 0), b.get(0, 0)*res.get(0, 0)+b.get(0, 1)*res.get(1, 0)));
	}
	
	
	@Test
	void gaussInvTest() {
		Matrix<Double> inv = A.gaussInv(f, Math::abs, p);
		assertTrue(A.prod(inv, f).equals(id2, p));
		
		//TODO: return an error of divByZeroException
		Matrix<Double> notInversible = new Matrix<Double>(new Double[][] {{1.,1.},
			  															  {1.,1.}});
		
		assertThrows(Matrix.NotInversibleMatrixException.class, ()->notInversible.gaussInv(f, Math::abs, p));
	}
	
	
	@Test
	void getMatTest() {
		
		Matrix<Double> mat00 = new Matrix<Double>(new Double[][] {{0.,0.},
			  												      {1.,7.}});
		
		Matrix<Double> mat11 = new Matrix<Double>(new Double[][] {{1.,-1.},
		      												      {-3.,7.}});
		
		Matrix<Double> mat22 = new Matrix<Double>(new Double[][] {{1.,4.},
		      													  {-2.,0.}});
		
	    assertTrue(m2.getMat(0, 0).equals(mat00, p));
	    
	    assertTrue(m2.getMat(1, 1).equals(mat11, p));
	    
	    assertTrue(m2.getMat(2, 2).equals(mat22, p));
		
	}
	
	
	@Test
	void detTest() {
		double detA = A.recDet(f);
		
		assertEquals(-2., detA);
		
		Double[][] t1 = {{45.}};
		
		assertEquals(45., Matrix.of(t1).recDet(f));
		assertEquals(58., m2.recDet(f));
		
		assertThrows(Matrix.NotSquareMatrixException.class, ()->B.recDet(f));
	}
	
	@Test
	void comInvTest() {
		
		Matrix<Double> inv = A.comInv(f,p);	
		assertEquals(id2, A.prod(inv, f));
		assertTrue(A.prod(inv, f).equals(id2, p));
	}
	
}





