package fr.romain.Maths.geom2D;

import fr.romain.Maths.linalg.algebraicObjects.Vector;
import fr.romain.Maths.linalg.algebraicStructure.Euclidean;
import fr.romain.Maths.utils.Reals;

public class Line {
	
	private static final Euclidean<Vector<Double>> e = Euclidean.vectorsEuclidean(2);
	
	Point p1;
	Point p2;
	
	/**
	 * Construct a line from 2 points
	 * in a plan
	 * @param p2
	 */
	public Line(Point p1, Point p2) {
		this.p1 = p1;
		this.p2 = p2;
	}
	
	/**
	 * Construct a Line from its equation
	 * in a plan
	 */
	public Line(double a,double b,double c) {
        if (Reals.isNull(b) && Reals.isNull(a)) {
            p1 = new Point(0,0);
            p2 = new Point(0,0);
        }
        else if (Reals.isNull(b)) {
            p1 = new Point(-c/a,0);
            p2 = new Point(-c/a,1);
        }     
        else {
            p1 = new Point(0,-c/b);
            p2 = new Point(1,-(c+a)/b);
        } 
	}
	
	public Line(Vector2D v, Point p) {
		this(v.getY(), -v.getX(), v.getX()*p.y - v.getY()*p.x);
	}


	public Point getP1() {
		return p1;
	}


	public void setP1(Point p1) {
		this.p1 = p1;
	}


	public Point getP2() {
		return p2;
	}


	public void setP2(Point p2) {
		this.p2 = p2;
	}


	public Vector2D vec() {
		return Vector2D.of(p1, p2);
	}
	
	/**
	 * get a from the equation of the line
	 * in a plan: ax+by+c=0
	 * @return a
	 */
	public double getA() {
		return vec().getY();
	}
	
	/**
	 * get b from the equation of the line
	 * in a plan: ax+by+c=0
	 * @return a
	 */
    public double getB(){
        return -1*vec().getX();
    }
    
    public double getC() {
        return vec().getX()*p1.y - vec().getY()*p1.x;
    }
    
    /**
     * 
     * @return a in the equation of the line
     * y = ax+b
     */
    public double steeringCoef() {
    	return -getA()/getB();
    }
    
    public double ordOrigin() {
    	return getC()/vec().getX();
    }
    
    /**
     * Test if the point's coordinates verify
     * the line equation:
     * ax+by+c=0
     * @param p
     * @return
     */
    public boolean contains(Point p) {
    	return Reals.isNull(p.getX()*getA()+p.getY()*getB()+getC());
    }
    
    public boolean isParallel(Line l) {
    	return e.areColinear(vec(), l.vec());
    }
    
    public Point intersection(Line l) throws IllegalArgumentException {
        if (isParallel(l)) {
        	throw new IllegalArgumentException("The two lines are parallels, there is thus no intersection");
        }
        if(Reals.isNull(getB()))
        	return new Point(p1.x, l.steeringCoef()*p1.x+l.ordOrigin());
        else if(Reals.isNull(l.getB()))
        	return new Point(l.getP1().x, steeringCoef()*l.getP1().x+ordOrigin());
        else {
        	double a1 = steeringCoef();
        	double b1 = ordOrigin();
        	double a2 = l.steeringCoef();
        	double b2 = l.ordOrigin();
        	
        	double px = (b2-b1)/(a1-a2);
        	return new Point(px, a1*px+b1);
        }
    }
    
    public double distanceTo(Point p) {
    	return p.distanceTo(p.projected(this));
    }
    
    @Override
    public boolean equals(Object obj) {
        Line d;
        try{
            d = (Line) obj;
        }
        catch(Exception e) {
            return false;
        }
        if (Reals.isNull(getA()-d.getA())
            && Reals.isNull(getB()-d.getB())
            && Reals.isNull(getC()-d.getC()))
        {
            return true;
        }
        return false;       
    }
    
    @Override
    public String toString() {
        return "(A: "+getA()+", B: "+getB()+", C:"+getC()+") y="+steeringCoef()+"*x + "+ordOrigin();
    }

}
