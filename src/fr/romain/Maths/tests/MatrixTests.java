package fr.romain.Maths.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Random;
import java.util.function.BiPredicate;

import org.junit.jupiter.api.Test;

import fr.romain.Maths.linalg.algebraicObjects.Matrix;
import fr.romain.Maths.linalg.algebraicObjects.Vector;
import fr.romain.Maths.linalg.algebraicStructure.Field;
import fr.romain.Maths.utils.Reals;

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
	
	public static Matrix<Double> zero = Matrix.zeros(f,2,2);
	public static Matrix<Double> id2 = Matrix.id(f,2,2);
	public static Matrix<Double> id3  = Matrix.id(f, 3,3);

	@Test
	void timesTest() {
		
		assertTrue(A.equals(A.times(1.,f), p));
		
		Matrix<Double> A3 = A.times(3.,f);
		assertTrue(A.hasDim(A3.dims()));
		for (int i = 0; i < A.dimRows(); i++) {
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
		Matrix<Double> augmented = A.augmRow(C);
		
		Matrix<Double> expected = new Matrix<Double>(new Double[][] {{2.,1.,1.5},
		    														 {0.,-1.,-2.0}});
		assertTrue(expected.equals(augmented, p));
		
		assertThrows(Matrix.NotMatchingDimensionsException.class, ()->A.augmRow(id3));
				
	}
	

	@Test
	void splitTest() {
		Matrix<Double> toExtract = new Matrix<Double>(new Double[][] {{2.,1.,1.5},
			 														  {0.,-1.,-2.0}});
		
		List<Matrix<Double>> l = toExtract.splitRows(2);
		assertTrue(l.get(0).equals(A, p));
		assertTrue(l.get(1).equals(C, p));
	}
	
	@Test
	void swapRowsTest() {
		
		Matrix<Double> expected = new Matrix<Double>(new Double[][] {{-1.,3.,4.},
																	 {1.,2.,0.}});
		
		Matrix<Double> m1Swapped12 = m1.clone();
		m1Swapped12.swapRows(0, 1);
		
		assertTrue(m1Swapped12.equals(expected, p));
		
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
	void recDetTest() {
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
	
	static boolean assertEchelonned(Matrix<Double> m) {
		for (int i = 1; i < m.dimRows(); i++) {
			
			if(m.getRow(i).equals(Vector.zero(m.dimCols(), f), p)) {
				return assertAreNulls(m, i);
			}	
			
			if(!(m.getRow(i).nbLeftZeros(f, p)>m.getRow(i-1).nbLeftZeros(f, p))) {
				return false;
			}
		}
		return true;
	}
	
	static boolean assertAreNulls(Matrix<Double> m,int line) {
		for (int i = line; i < m.dimRows(); i++) {
			if(!m.getRow(i).equals(Vector.zero(m.dimCols(), f), p)) {
				return false;
			}
		}
		return true;
	}

	static boolean assertIsReducedEchelonned(Matrix<Double> m) {
		boolean isEchelonned = assertEchelonned(m);
		if(!isEchelonned) {
			return false;
		}
		for (int i = 0; i < m.dimRows(); i++) {
			int pivotCol = m.getRow(i).nbLeftZeros(f, p);
			
			//because we know that the matrix is echelonned
			if(pivotCol == m.dimRows() || pivotCol == m.dimCols()) {
				return true;
			}
			
			if(!p.test(m.get(i,pivotCol),1.)) {
				System.out.println(m.get(i,pivotCol)+" != 1");
				return false;
			}
			else {
				for (int i2 = 0; i2 < m.dimRows(); i2++) {
					if(i2!=pivotCol && !p.test(m.get(i2, pivotCol), 0.)) {
						System.out.println(m.get(i2,pivotCol)+" != 0");
						return false;
					}
				}
			}
			
		}
		return true;
	}
	
	@Test
	void testRowEchelonForm() {
		Matrix<Double> ech1 = A.toRowEchelonForm(f,p);
		
		assertTrue(assertEchelonned(ech1));
		
		Double[][] tab = {{1.,5.,9.,0.,2.,1.,5.},
						  {0.,0.,4.,5.,0.,0.,8.},
						  {-1.,-8.,-6.,0.,0.,7.,9.}};
		
		Matrix<Double> m = new Matrix<Double>(tab);
		assertTrue(assertEchelonned(m.toRowEchelonForm(f, p)));
		
		
		Matrix<Double> ech2 = m7.toRowEchelonForm(f,p);	
		assertTrue(assertEchelonned(ech2));
		
		Double[][] tab2 = {new Double[24],new Double[24],new Double[24],new Double[24],
						   new Double[24],new Double[24],new Double[24],new Double[24],
						   new Double[24],new Double[24],new Double[24],new Double[24],
						   new Double[24],new Double[24],new Double[24],new Double[24],
						   new Double[24],new Double[24],new Double[24],new Double[24]};
		
		for (int i = 0; i < tab2.length; i++) {
			for (int j = 0; j < 24; j++) {
				tab2[i][j] = new Random().nextDouble()*100;
			}
		}
		
		Matrix<Double> hardcore = new Matrix<Double>(tab2);
		Matrix<Double> ech3 = hardcore.toRowEchelonForm(f, p);
		
		assertTrue(assertEchelonned(ech3));
		
		Double[][] moreLines = {{1.,2.,3.},
							{1021.,2.,3.},
							{0.,0.,0.},
							{0.,2.,3.},
							{1.,5.,-7.},
							{1.,0.,3.}};
		
		assertTrue(assertEchelonned(Matrix.of(moreLines).toRowEchelonForm(f, p)));	
		
	}
	
	@Test
	void testRank() {
		assertEquals(1, m4.rank(f, p));
		
		Double[][] tab = {{1.,1.},{3.,3.}};
		Matrix<Double> matrix = new Matrix<Double>(tab);
		assertEquals(1, matrix.rank(f, p));
		
		Double[][] tab2 = {{1.,0.},{0.,1.}};
		Matrix<Double> matrix2 = new Matrix<Double>(tab2);
		assertEquals(2, matrix2.rank(f, p));
		
		Double[][] tab3 = {{1.,0.},{0.,1.},{4.,6.}};
		Matrix<Double> matrix3 = new Matrix<Double>(tab3);
		assertEquals(2, matrix3.rank(f, p));
		
		Double[][] tab4 = {{1.,2.},
						   {0.,0.},
						   {0.,0.}};
		Matrix<Double> matrix4 = new Matrix<Double>(tab4);
		assertEquals(1, matrix4.rank(f, p));
		
	}
	
	@Test
	void testReducedRowEchelonnedForm() {
		
		
		Double[][] tab = {{1.,5.,9.,0.,2.,1.,5.},
				  {0.,0.,4.,5.,0.,0.,8.},
				  {-1.,-8.,-6.,0.,0.,7.,9.}};

		Matrix<Double> m = new Matrix<Double>(tab);
		
		Double[][] moreLines = {{1.,2.,3.},
				{1021.,2.,3.},
				{0.,0.,0.},
				{0.,2.,3.},
				{1.,5.,-7.},
				{1.,0.,3.}};
		
		Matrix<Double> mPrime = Matrix.of(moreLines);
		
		assertTrue(assertIsReducedEchelonned(m7.toReducedRowEchelonForm(f,Math::abs,p)));
		assertTrue(assertIsReducedEchelonned(m2.toReducedRowEchelonForm(f,Math::abs, p)));
		assertTrue(assertIsReducedEchelonned(m1.toReducedRowEchelonForm(f,Math::abs, p)));
		assertTrue(assertIsReducedEchelonned(m.toReducedRowEchelonForm(f, Math::abs,p)));
		assertTrue(assertIsReducedEchelonned(mPrime.toReducedRowEchelonForm(f,Math::abs,p)));
	}
	
	
	@Test
	void testKer() {
		Double[][] tab = {{1.,5.,9.,0.,2.,1.,5.},
				  {0.,0.,4.,5.,0.,0.,8.},
				  {-1.,-8.,-6.,0.,0.,7.,9.}};

		Matrix<Double> m = new Matrix<Double>(tab);
		
		Double[][] moreLines = {{1.,2.,3.},
				{1021.,2.,3.},
				{0.,0.,0.},
				{0.,2.,3.},
				{1.,5.,-7.},
				{1.,0.,3.}};
		
		Matrix<Double> mPrime = Matrix.of(moreLines);
		
		List<Matrix<Double>> matrices = List.of(m,mPrime,m1,m2,m3,m4,m5,m6,m7);
		
		for (Matrix<Double> matrix : matrices) {
			List<Vector<Double>> ker = matrix.ker(f, Math::abs, p);
			for (Vector<Double> root : ker) {
				Matrix<Double> result = matrix.prod(root.toMatrix().transpose(), f);
				System.out.println(result);
				assertTrue(result.equals(Matrix.zeros(f, new int[] {matrix.dimRows(),1}), p));
			}
		}
	}
	
}





