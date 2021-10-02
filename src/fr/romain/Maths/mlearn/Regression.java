package fr.romain.Maths.mlearn;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fr.romain.Maths.linalg.MatR;
import fr.romain.Maths.linalg.VectR;

public abstract class Regression {
	
	MatR theta;
	MatR X;
	MatR y;
	int m;

	public Regression(MatR designMatrix,MatR outputs) {
		
		X = designMatrix;
		y = outputs;	
		m = outputs.dimCols()*outputs.dimRows();
	
	}
	
	public Regression(String filename) {
		String regex = ";";
		File file = new File(filename);
		
		List<String[]> lines = new ArrayList<String[]>();
		try {
			Scanner scan = new Scanner(file);
			
			while (scan.hasNextLine()) {
				String[] values = scan.nextLine().split(regex);
				lines.add(values);
			}
			
			m = lines.size();
			int n = lines.get(0).length;
			X = new MatR(m, n);
			y = new MatR(m, 1);
			
			for (int i = 0; i < m; i++) {
				String[] line = lines.get(i);
				for (int j = 0; j < n-1; j++) {
					X.set(i, j, Double.parseDouble(line[j]));
				}
				y.set(i, 1, Double.parseDouble(line[n-1]));
			}
			
			scan.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("The file used is not correctly written");
			e.printStackTrace();
		}
	}
	
	/**
	 * normalize features and add a column full of zeros on the left of X (represents x0's)
	 */
	public void init(){
		featureNormalize();
		X = MatR.concat(MatR.ones(X.dimRows(),1),X);
	}
	
	public void featureNormalize() {
		
		VectR means = X.colsMeans();
		
		//a vector of 1/(max-min) for each features (each columns)
		final VectR valuesLength = X.colsMax().minus(X.colsMin()).elmtWiseInv();
		
		X.forEachCols((col)->{
			col = col.minus(means).elmtWiseProd(valuesLength);
			return col;
		});
		
	}
	
	public void gradientDescent(double alpha) {
		
		theta = MatR.zeros(m,1);
		
		MatR gradJ = gradJ(theta);
		
		while (!gradJ.equals(MatR.zeros(m,1))) {
			
			theta = theta.minus(gradJ.dot(alpha));
			gradJ = gradJ(theta);
		} 
	}
	
	/**
	 * grad J is the gradient of the cost function J
	 * @return the gradient of the cost function for the current thetaMin
	 */
	public abstract MatR gradJ(MatR theta);

	public abstract double costFunction(MatR theta);
	
	public abstract double predictOutput(VectR input);
	
}
