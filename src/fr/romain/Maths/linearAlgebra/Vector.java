
package fr.romain.Maths.linearAlgebra;

import java.util.Arrays;
import java.util.List;

import fr.romain.Maths.Calcul;

/**
 *
 * @author romain
 */
public class Vector extends Matrix{

	
    public Vector(Double... coords) {
    	this(Arrays.asList(coords));
    }
    
    public Vector(List<Double> coords) {
    	super(1, coords.size());
    	for (int i = 0; i < coords.size(); i++) {
			values[0][i] = coords.get(i);
		}
    }
    
    public static Vector of(double a, double b) {
    	return new Vector(a, b);
    }
    
    public double getX(){
    	return get(0);
    }
    
    public double getY(){
        return get(1);
    }
    
    public double getZ() {
    	return get(2);
    }
    
    /**
     * Cette méthode renvoie la ième valeur du vecteur, à isomorphisme près
     * @param i
     */
    public double get(int i) {
    	if (dimLine()==1) {
    		return values[0][i];
    	}
    	return values[i][0];
    }
    
    public void set(int i, double value) {
    	if (dimLine()==1) {
    		values[0][i] = value;
    	}
    	values[i][0] = value;
    }
    
    
    public Vector plus(Vector v) throws IllegalArgumentException {
    	if(dim()!=v.dim()) {
    		throw new IllegalArgumentException("The two vectors must be isomorphic to each other");
    	}
    	try {
			return (Vector) super.plus(v);
		} catch (IllegalArgumentException e) {
			return (Vector) super.plus(v.transpose());
		}
    }
    
    public Vector minus(Vector v) throws IllegalArgumentException{
    	return plus((Vector)v.times(-1));
    }

            
    public double prodVec(Vector v){
        return this.getX()*v.getY()-this.getY()*v.getX();
    }
    
    public boolean isColinear(Vector v){
        return Calcul.isNul(this.prodVec(v));
    }
    
    /**
     * Renvoie un vecteur orthogonal au vecteur u en dimension 2
     * @param v
     * @return
     */
    public Vector getOrthogonal() {
        
        double Vx,Vy;
        if (Calcul.isNul(getY())) {
            Vx = 0;
            Vy = 1;
        }
        else if (Calcul.isNul(getX())){
            Vx = 1;
            Vy = 0;
        }
        else {
            Vx = -1/getX();
            Vy = 1/getY();
        }
        return new Vector(Vx, Vy);
    }
}
