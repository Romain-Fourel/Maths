
package fr.romain.Maths.linearAlgebra;


/**
 * This class aims to contains all useful function on reals numbers
 *
 */
public class Reals {
    static final double precision = 0.0000000001;
    
    public static boolean isNul(double d){
        return Math.abs(d)<precision;
    }
    
    public static boolean equals(double d1, double d2) {
    	return isNul(d1-d2);
    }
    
    public static double norm2(double... values){
        double norm2 = 0;
        for (double d : values) {
			norm2+=d*d;
		}
        return Math.sqrt(norm2);
    }
    
    public static double norm1(double... values) {
    	double norm1 = 0;
    	for (double d : values) {
			norm1 += d;
		}
    	return norm1;
    }
    
    /**
     * The "infinity norm"
     * @param values
     * @return
     */
    public static double normInf(double... values) {
    	double normInf = 0;
    	for (double d : values) {
			normInf = Math.max(normInf, d);
		}
    	return normInf;
    }

}
