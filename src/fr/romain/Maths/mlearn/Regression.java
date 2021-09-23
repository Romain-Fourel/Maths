package fr.romain.Maths.mlearn;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

import fr.romain.Maths.linalg.MatR;
import fr.romain.Maths.linalg.Matrix.NotInversibleMatrixException;
import fr.romain.Maths.linalg.Matrix.NotSquareMatrixException;
import fr.romain.Maths.linalg.VectR;

public class Regression {
	
	enum Res {
		GRADIENT_DESCENT((regression)->regression.gradientDescent()),
		NORMAL_EQUATION((regression)->regression.normalEquation());
		
		Consumer<Regression> consumer;
		
		Res(Consumer<Regression> consumer) {
			this.consumer = consumer;
		}
		
		public void apply(Regression reg) {
			consumer.accept(reg);
		}
	}
	
	MatR theta;
	MatR designMatrix;
	MatR outputs;
	
	double learningRate;

	public Regression(MatR X,MatR Y, double alpha, Res r) {
		learningRate = alpha;
		
		designMatrix = X;
		outputs = Y;
		
		init(r);

	}
	
	public Regression(String filename,double alpha, Res r) {
		learningRate = alpha;
		String regex = ";";
		File file = new File(filename);
		
		List<String[]> lines = new ArrayList<String[]>();
		try {
			Scanner scan = new Scanner(file);
			
			while (scan.hasNextLine()) {
				String[] values = scan.nextLine().split(regex);
				lines.add(values);
			}
			
			int m = lines.size();
			int n = lines.get(0).length;
			designMatrix = new MatR(m, n);
			outputs = new MatR(m, 1);
			
			for (int i = 0; i < m; i++) {
				String[] line = lines.get(i);
				for (int j = 0; j < n-1; j++) {
					designMatrix.set(i, j, Double.parseDouble(line[j]));
				}
				outputs.set(i, 1, Double.parseDouble(line[n-1]));
			}
			
			scan.close();
			
			init(r);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("The file used is not correctly written");
			e.printStackTrace();
		}
		
	}
	
	public void init(Res r) {
		featureNormalize();
		
		designMatrix = MatR.concat(MatR.ones(designMatrix.dimRows(),1),designMatrix);
		
		try {
			r.apply(this);
		} catch (NotSquareMatrixException | NotInversibleMatrixException e) {
			gradientDescent();
		}

	}
	
	public void featureNormalize() {
		
		VectR means = designMatrix.colsMeans();
		
		//a vector of 1/(max-min) for each features (each columns)
		final VectR valuesLength = designMatrix.colsMax().minus(designMatrix.colsMin()).elmtWiseInv();
		
		designMatrix.forEachCols((col)->{
			col = col.minus(means).elmtWiseProd(valuesLength);
			return col;
		});
		
	}
	
	public void normalEquation() {
		MatR xT = designMatrix.t();
		
		theta = xT.dot(designMatrix).inv().dot(xT).dot(outputs);
	}
	
	public double costFunction(MatR theta) {
		
		MatR term = designMatrix.dot(theta).minus(outputs);
		double m = outputs.dimCols()*outputs.dimRows();
		
		return term.t().dot(term).dot(1./(2*m)).get(0, 0);
	}
	
	/**
	 * grad J is the gradient of the cost function J
	 * @return the gradient of the cost function for the current thetaMin
	 */
	public MatR gradJ(MatR theta) {
		MatR xT = designMatrix.t();
		
		double m = outputs.dimCols()*outputs.dimRows();
		
		return xT.dot(designMatrix.dot(theta).minus(outputs)).dot(1./m);
	}
	
	public void gradientDescent() {
		int m = outputs.dimCols()*outputs.dimRows();
		
		theta = MatR.zeros(m,1);
		
		MatR gradJ = gradJ(theta);
		
		while (!gradJ.equals(MatR.zeros(m,1))) {
			
			theta = theta.minus(gradJ.dot(learningRate));
			gradJ = gradJ(theta);
		}
	}
	
	public VectR getSol() {
		return (VectR) theta.toVec();
	}
	
	public double predictOutput(VectR input) {
		return theta.t().dot((MatR)input.toRowMat()).get(0, 0);
	}

}
