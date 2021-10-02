package fr.romain.Maths.mlearn;

import java.util.function.Function;

import fr.romain.Maths.linalg.MatR;
import fr.romain.Maths.linalg.VectR;

/**
 * This class represents a BINARY classification (y is either 0 or 1)
 * @author romai
 */
public class Classification extends Regression {

	static Function<Double, Double> logisticFct = x->1./(1+Math.exp(-x));
	
	public Classification(String filename) {
		super(filename);
	}
	
	public Classification(MatR designMatrix, MatR outputs) {
		super(designMatrix,outputs);
	}
	
	@Override
	public double costFunction(MatR theta) {	
		MatR gOfXTheta = X.dot(theta).elmtWise(logisticFct);
		
		MatR ones = MatR.ones(m,1);
		
		return y.dot(-1.).t().dot(gOfXTheta.elmtWise(Math::log))
				.minus(
				ones.minus(y).t().dot(ones.minus(gOfXTheta)))
				.asScalar()/m;
	}
	

	@Override
	public MatR gradJ(MatR theta) {
		MatR gOfXTheta = X.dot(theta).elmtWise(logisticFct);
		
		return X.t().dot(gOfXTheta.minus(y)).dot(1./m);
	}


	@Override
	public double predictOutput(VectR input) {
		double proba = logisticFct.apply(theta.t().dot((MatR)input.toColMat()).asScalar());
		
		return proba >= 0.5 ? 1:0;
	}

}
