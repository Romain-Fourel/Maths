package fr.romain.Maths.mlearn;

import fr.romain.Maths.linalg.MatR;
import fr.romain.Maths.linalg.VectR;

public class LinearRegression extends Regression{
	
	
	public LinearRegression(MatR designMatrix,MatR outputs) {
		super(designMatrix, outputs);
	}
	
	protected LinearRegression(String filename) {
		super(filename);
	}
	
	public void normalEquation() {
		MatR xT = X.t();
		
		theta = xT.dot(X).inv().dot(xT).dot(y);
	}

	@Override
	public MatR gradJ(MatR theta) {
		MatR xT = X.t();
		
		double m = y.dimCols()*y.dimRows();
		
		return xT.dot(X.dot(theta).minus(X)).dot(1./m);
	}

	@Override
	public double costFunction(MatR theta) {
		MatR term = X.dot(theta).minus(y);
		double m = y.dimCols()*y.dimRows();
		
		return term.t().dot(term).dot(1./(2*m)).asScalar();
	}

	@Override
	public double predictOutput(VectR input) {
		return theta.t().dot((MatR)input.toRowMat()).asScalar();
	}
	

}
