package fr.romain.Maths.R_VectorSpace.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import fr.romain.Maths.R_VectorSpace.Calcul;
import fr.romain.Maths.R_VectorSpace.linearAlgebra.Matrix;
import fr.romain.Maths.R_VectorSpace.linearAlgebra.Vector;


class MatrixTests {
	
	static Matrix A = new Matrix(new double[][] {{2,0,5},
		  										 {0,1,2},
		  										 {5,4,2}});
	
	static Matrix B = new Matrix(new double[][] {{2,2,1},
		  										 {1,1,1},
		  										 {3,3,0}});
	
	static Matrix P1 = new Matrix(new double[][] {{2,4,1},
			 									  {1,0,6}});

	static Matrix P2 = new Matrix(new double[][] {{3,4,2,1},
		  										  {2,6,1,0},
		  										  {5,1,0,2}});
		  										  
	
	@Test
	void zeroTest() {
		
		Matrix zero3 = new Matrix(new double[][] {{0.,0.,0.},
											      {0.,0.,0.},
											      {0.,0.,0.}});
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				assertEquals(0, Matrix.zero(3).get(i, j));
			}
		}
		
		assertEquals(zero3, Matrix.zero(3));
		
	}
	
	@Test
	void idTest() {
		Matrix identity = Matrix.id(4);
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if(i==j) {
					assertEquals(1, identity.get(i, j));
				}
				else {
					assertEquals(0, identity.get(i, j));
				}
			}
		}
		
	}
	
	@Test
	void diagTest() {
		
		assertEquals(Matrix.id(4), Matrix.diag(1,1,1,1));
		
		assertEquals(Matrix.zero(4), Matrix.diag(0,0,0,0));
		
		assertEquals(Matrix.id(4).times(6), Matrix.diag(6,6,6,6));
		
	}
	
	@Test
	void plusTest() {
		Matrix sum = new Matrix(new double[][] {{4,2,6},
												{1,2,3},
												{8,7,2}});	
		assertEquals(sum, A.plus(B));
		
	}
	
	@Test
	void prodTest() {
		assertEquals(Matrix.id(4), Matrix.id(4).prod(Matrix.id(4)));
		
		Matrix prod = new Matrix(new double[][] {{19,33,8,4},
												 {33,10,2,13}});
		
		assertTrue(P1.canBeProdTo(P2));
		assertEquals(prod, P1.prod(P2));
		
		Matrix v1 = new Matrix(new double[][] {{Math.sqrt(2)/2},
											   {Math.sqrt(2)/2}});
		Matrix v2 = new Matrix(new double[][] {{Math.sqrt(2)/2,Math.sqrt(2)/2}});
		
		assertTrue(v1.canBeProdTo(v2));
		Matrix prod2 = new Matrix(new double[][] {{1./2,1./2},
												  {1./2,1./2}});
		
		assertEquals(prod2, v1.prod(v2));
		assertEquals(2, v1.prod(v2).dimLine());
		assertEquals(2, v1.prod(v2).dimCol());
		
		Matrix prod3 = new Matrix(new double[][] {{1}});
		
		assertEquals(1, v2.prod(v1).dimLine());
		assertEquals(1, v2.prod(v1).dimCol());
		assertEquals(prod3, v2.prod(v1));
		
	}
	
	@Test
	void transposeTest() {
		for (int i = 0; i < A.dimLine(); i++) {
			for (int j = 0; j < A.dimCol(); j++) {
				assertEquals(A.get(i, j), A.transpose().get(j, i));
			}
		}
		Matrix prod = new Matrix(new double[][] {{1./2,1./2},
			  									  {1./2,1./2}});
		Matrix v = new Matrix(new double[][] {{Math.sqrt(2)/2,Math.sqrt(2)/2}});
		
		assertTrue(v.transpose().canBeProdTo(v));
		assertEquals(2, v.transpose().dimLine());
		assertEquals(1, v.transpose().dimCol());
		
		assertEquals(prod, v.transpose().prod(v));
			
	}
	
	@Test
	void traceTest() {
		assertEquals(5, A.trace());
		assertEquals(3, B.trace());
		assertEquals(10, Matrix.id(10).trace());
		assertEquals(0, Matrix.zero(15).trace());
		
		Matrix v = new Matrix(new double[][] {{Math.sqrt(2)/2,Math.sqrt(2)/2}});
		
		assertTrue(Calcul.isNul(v.transpose().prod(v).trace()-1));
	}
	
	@Test
	void scalarTest() {
		double cosPi4 = Math.sqrt(2)/2;
		double cosPi3 = Math.sqrt(3)/2;
		
		Vector v1 = Vector.of(1, 0);
		Vector v2 = Vector.of(0, 1);	
		Vector v4 = Vector.of(1./2, cosPi3);
		Vector v5 = Vector.of(cosPi3, 1./2);
		
		Vector v3 = Vector.of(cosPi4,cosPi4);
		Matrix v = new Matrix(new double[][] {{Math.sqrt(2)/2,Math.sqrt(2)/2}});
		
		assertEquals(v, v3);
		
		assertTrue(Calcul.isNul(v.scalar(v)-1));
		assertTrue(Calcul.isNul(v1.scalar(v1)-1));
		assertTrue(Calcul.isNul(v2.scalar(v2)-1));
		assertTrue(Calcul.isNul(v3.scalar(v3)-1));
		assertTrue(Calcul.isNul(v4.scalar(v4)-1));
		assertTrue(Calcul.isNul(v5.scalar(v5)-1));
		
	}
	
	@Test
	void norm2Test() {	
		double cosPi4 = Math.sqrt(2)/2;
		double cosPi3 = Math.sqrt(3)/2;
		
		Vector v1 = Vector.of(1, 0);
		Vector v2 = Vector.of(0, 1);
		Vector v3 = Vector.of(cosPi4,cosPi4);
		Vector v4 = Vector.of(1./2, cosPi3);
		Vector v5 = Vector.of(cosPi3, 1./2);
		
		assertTrue(Calcul.isNul(v1.getNorm2()-1));
		assertTrue(Calcul.isNul(v2.getNorm2()-1));
		assertTrue(Calcul.isNul(v3.getNorm2()-1));
		assertTrue(Calcul.isNul(v4.getNorm2()-1));
		assertTrue(Calcul.isNul(v5.getNorm2()-1));
		
	}
	
	@Test
	void rotationTest() {
		
		double cosPi4 = Math.sqrt(2)/2;
		
		Matrix rotation = Matrix.rotation(Vector.of(1, 0),Vector.of(cosPi4,cosPi4));
		
		double theta = Math.PI/4;
		
		Matrix expected = new Matrix(new double[][] {{Math.cos(theta),-Math.sin(theta)},
													 {Math.sin(theta),Math.cos(theta)}});
		
		assertEquals(expected, rotation);
		
	}

}






