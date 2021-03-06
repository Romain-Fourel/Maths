
package fr.romain.Maths.utils;

/**
 * This class aims to contains all useful function on reals numbers
 *
 */
public class Reals {
    static final double precision = 0.00000000001;
    
    public static boolean isNull(double d){
        return Math.abs(d)<precision;
    }
    
    public static boolean equals(double d1, double d2) {
    	return isNull(d1-d2);
    }
    
    public static double norm2(Double... values){
        return Norms.norm2(Math::abs, values);
    }
    
    public static double norm1(Double... values) {
    	return Norms.norm1(Math::abs, values);
    }
    

    public static double normInf(Double... values) {
    	return Norms.normInf(Math::abs, values);
    }
    
    public static double sum(Double... values) {
    	double sum = 0;
    	for (Double value : values) {
			sum+=value;
		}
    	return sum;
    }

}
