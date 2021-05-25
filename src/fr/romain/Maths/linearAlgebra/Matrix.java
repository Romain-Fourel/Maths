package fr.romain.Maths.linearAlgebra;

import fr.romain.Maths.Calcul;


public class Matrix {

	double[][] values;

	
	
	public Matrix(int n, int m) {
		values = new double[n][m];
	}

	public Matrix(double[][] values) {
		this.values = values;
	}

	public Matrix(int[] dim) {
		this(dim[0], dim[1]);
	}

	public double get(int i, int j) {
		return values[i][j];
	}

	public void set(int i, int j, double value) {
		values[i][j] = value;
	}
	
		
	
	
	/**
	 * Returns the addition neutral matrix: A matrix full of zero
	 * 
	 * @param dim
	 * @return
	 */
	public static Matrix zero(int dim) {
		Matrix zero = new Matrix(dim, dim);
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				zero.set(i, j, 0);
			}
		}
		return zero;
	}

	public static Matrix diag(double... values) {
		Matrix diag = new Matrix(values.length, values.length);
		for (int i = 0; i < values.length; i++) {
			for (int j = 0; j < values.length; j++) {
				if (i == j) {
					diag.set(i, i, values[i]);
				} else {
					diag.set(i, j, 0);
				}
			}

		}
		return diag;
	}

	/**
	 * Calculate the identity matrix in dimension dim
	 * 
	 * @param dim
	 * @return
	 */
	public static Matrix id(int dim) {
		Matrix id = new Matrix(dim, dim);
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				if (i == j) {
					id.set(i, j, 1);
				} else {
					id.set(i, j, 0);
				}
			}
		}
		return id;
	}
	
	
	public static Matrix byLines(Vector... vectors) {
		Matrix matrix = new Matrix(vectors[0].dim(), vectors.length);
		
		for (int i = 0; i < matrix.dimLine(); i++) {
			for (int j = 0; j < matrix.dimCol(); j++) {
				matrix.set(i, j, vectors[i].get(j));
			}
		}
		return matrix;
	}
	
	public static Matrix byCols(Vector... vectors) {
		
		return byLines(vectors).transpose();
	}
	
	/**
	 * Calculate the rotation matrix of the angle between v1 and v2 in dimension two
	 * 
	 * @param v1
	 * @param v2
	 * @return the rotation matrix
	 */
	public static Matrix rotation(Vector v1, Vector v2) {
		double cosTheta = v1.scalar(v2) / (v1.getNorm2() * v2.getNorm2());
		double sinTheta = v1.prodVec(v2) / (v1.getNorm2() * v2.getNorm2());

		return new Matrix(new double[][] { { cosTheta, -sinTheta }, 
										   { sinTheta, cosTheta  }});
	}



	
	/**
	 * @return the line dimension times the column dimension
	 */
	public int dim() {
		return dimLine()*dimCol();
	}
	
	
	/**
	 * @return a tab which contains the lines dimensions and the column dimensions
	 */
	public int[] dims() {
		return new int[] {dimLine(),dimCol()};
	}

	public int dimLine() {
		return values.length;
	}

	public int dimCol() {
		return values[0].length;
	}

	public boolean hasSameDim(Matrix matrix) {
		return dimLine() == matrix.dimLine() && dimCol() == matrix.dimCol();
	}

	public boolean isSquare() {
		return dimLine() == dimCol();
	}

	public Matrix plus(Matrix matrix) throws IllegalArgumentException {

		Matrix sum = new Matrix(dims());

		if (hasSameDim(matrix)) {
			for (int i = 0; i < dimLine(); i++) {
				for (int j = 0; j < dimCol(); j++) {
					sum.values[i][j] = get(i, j) + matrix.get(i, j);
				}
			}
			return sum;
		} else {
			throw new IllegalArgumentException(
					"The both matrix have not the same dimensions and are therefore not summable");
		}
	}
	
	public Matrix minus(Matrix matrix) throws IllegalArgumentException{
		return plus(matrix.times(-1));
	}

	public Matrix times(double k) {

		Matrix matrix = new Matrix(dims());

		for (int i = 0; i < dimLine(); i++) {
			for (int j = 0; j < dimCol(); j++) {
				matrix.set(i, j, get(i, j) * k);
			}
		}
		return matrix;
	}

	public boolean canBeProdTo(Matrix matrix) {
		return dimCol() == matrix.dimLine();
	}

	public Matrix prod(Matrix matrix) throws IllegalArgumentException {
		if (!canBeProdTo(matrix)) {
			throw new IllegalArgumentException(
					"columns of the first matrix has to be equals" + "with lines of the second matrix");
		}
		Matrix prod = new Matrix(dimLine(),matrix.dimCol());

		for (int i = 0; i < dimLine(); i++) {

			for (int j = 0; j < matrix.dimCol(); j++) {

				double sum = 0;

				for (int k = 0; k < dimCol(); k++) {
					sum += get(i, k) * matrix.get(k, j);
				}
				prod.set(i, j, sum);
			}
		}
		return prod;
	}

	public Matrix transpose() {
		Matrix transpose = new Matrix(dimCol(), dimLine());
		for (int i = 0; i < dimCol(); i++) {
			for (int j = 0; j < dimLine(); j++) {
				transpose.set(i, j, get(j, i));
			}
		}
		return transpose;
	}

	public double trace() throws IllegalArgumentException {
		if (!isSquare()) {
			throw new IllegalArgumentException("The matrix has to be a square (dim[0]=dim[1]");
		}
		double trace = 0;
		for (int i = 0; i < dimLine(); i++) {
			trace += get(i, i);
		}
		return trace;
	}

	
	public double scalar(Matrix m) throws IllegalArgumentException {
		if (!hasSameDim(m)) {
			throw new IllegalArgumentException("Both matrix has to have the same dimension to do the scalar product");
		}
		return this.transpose().prod(m).trace();
	}

	
	public double getNorm2() {
		return Math.sqrt(this.scalar(this));
	}

	public double distance(Matrix m) {
		return minus(m).getNorm2();
	}
	
	public boolean isOrthogonal() {
		return transpose().prod(this).equals(id(dimLine()));
	}
	
	public boolean isSymetric() {
		return equals(transpose());
	}
	
	

	public Matrix pow(int pow) throws IllegalArgumentException{
		if(!isSquare()) {
			throw new IllegalArgumentException("Only square matrix can be multiplied by themself!");
		}
		
		if (pow==1) {
			return this;
		}
		if (pow % 2 == 0) {
			return pow(pow / 2).prod(pow(pow / 2));
		}
		else {
			return this.prod(pow((pow-1)/2)).prod(pow((pow-1)/2));
		}
	}
	
	
	
	@Override
	public boolean equals(Object obj) {
		Matrix m = (Matrix) obj;
		if(!hasSameDim(m)) {
			return false;
		}

		for (int i = 0; i < dimLine(); i++) {
			for (int j = 0; j < dimCol(); j++) {
				if (!Calcul.isNul(get(i, j) - m.get(i, j))) {
					return false;
				}
			}
		}
		return true;
	}
	
	@Override
	public String toString() {
		String s = "(";
		for (int i = 0; i < dimLine(); i++) {
			
			if(i>0) {
				s+="\n ";
			}
			
			for (int j = 0; j < dimCol(); j++) {
				
				if (get(i, j)==(int)get(i, j)) {
					s+=(int)get(i, j);
				}
				else {
					s+=get(i, j);
				}
				
				if(j<dimCol()-1) {
					s+=" ";
				}
			}
		}
		s+=")";
		return s;
	}

}





