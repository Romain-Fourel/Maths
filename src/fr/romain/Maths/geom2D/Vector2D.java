package fr.romain.Maths.geom2D;

import fr.romain.Maths.linalg.VectR;
import fr.romain.Maths.linalg.Vector;
import fr.romain.Maths.linalg.algstruct.Euclidean;
import fr.romain.Maths.utils.Reals;

public class Vector2D extends VectR {
	
	private final static Euclidean<Vector<Double>> e = Euclidean.vectorsEuclidean(2);
	
	public Vector2D(double x, double y) {
		super(x,y);
	}
	
	public static Vector2D of(Point p) {
		return new Vector2D(p.getX(), p.getY());
	}
	
	public static Vector2D of(Point p1, Point p2) {
		return (Vector2D) e.minus(of(p2), of(p1));
	}
	
	public double getX() {
		return get(0);
	}
	
	public double getY() {
		return get(1);
	}
	
	/**
	 * 
	 * @return a vector orthogonal to this in a plan
	 */
    public Vector2D getOrthogonal() {
        
        double Vx,Vy;
        if (Reals.isNull(getY())) {
            Vx = 0;
            Vy = 1;
        }
        else if (Reals.isNull(getX())){
            Vx = 1;
            Vy = 0;
        }
        else {
            Vx = -1/getX();
            Vy = 1/getY();
        }
        return new Vector2D(Vx, Vy);
    }
	
	
}
