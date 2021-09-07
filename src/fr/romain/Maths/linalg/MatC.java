package fr.romain.Maths.linalg;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;

import fr.romain.Maths.linalg.algstruct.Field;
import fr.romain.Maths.utils.Complex;

public class MatC extends Matrix<Complex> {

	private final static Field<Complex> f = Field.complexField();
	
	private final static Function<Complex, Double> abs = c->c.module();
	private final static BiPredicate<Complex, Complex> equals = (z1,z2)->z1.equals(z2);
	
	public static MatC of(Matrix<Complex> m){
		return new MatC(m.getValues());
	}
	
	public MatC(Complex[][] values) {
		super(values);	
	}

	public MatC(int n, int m) {
		super(n, m);
	}


	public MatC(int[] dim) {
		super(dim);
	}
	
	
	public MatC plus(MatC matrix) {
		return (MatC) plus(matrix, f);
	}
	

	public MatC prod(MatC matrix) {
		return (MatC) prod(matrix, f);
	}
	
	public MatC pow(int k){
		return (MatC) pow(k, f);
	}
	
	public MatC times(Complex k) {
		return (MatC) times(k, f);
	}
	
	public Complex trace() {
		return trace(f);
	}
	
	public Complex det() {
		return recDet(f);
	}
	
	public MatC inv() {
		return (MatC) gaussInv(f, abs, equals);
	}
	
	public int rank() {
		return rank(f, equals);
	}
	
	public List<Vector<Complex>> ker(){
		return ker(f, abs, equals);
	}
	
	public Complex scalarProd(MatC m) {
		return scalarProd(m, f);
	}
	
	public static MatC zeros(int... dims) {
		return (MatC) Matrix.zeros(f, dims);
	}
	
	public static MatC id (int dim) {
		return (MatC) Matrix.id(f, dim);
	}
	
}
