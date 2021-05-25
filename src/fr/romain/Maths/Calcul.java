
package fr.romain.Maths;


/**
 * 
 * @author romain
 *
 */
public class Calcul {
    static final double precision = 0.0000000001;
    
    public static boolean isNul(double d){
        return Math.abs(d)<precision;
    }
    
    public static double norm2(double a, double b){
        return Math.sqrt(a*a+b*b);
    }

}
