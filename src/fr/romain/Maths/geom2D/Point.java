package fr.romain.Maths.geom2D;

import fr.romain.Maths.gui.Board;
import fr.romain.Maths.utils.Reals;

public class Point implements Drawable{
	
	double x;
	double y;
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	

	public void translate(Vector2D v) {
		x+=v.getX();
		y+=v.getY();
	}

	@Override
	public void drawOn(Board b) {
		b.drawPoint(this);
		
	}

	public double distanceTo(Point p) {
		return Vector2D.of(this, p).norm();
	}
	
	public Point projected(Line l) {
		Line perpendicular = new Line(l.vec().getOrthogonal(), this);
		return perpendicular.intersection(l);
	}
	
	@Override
	public boolean equals(Object obj) {
		try {
			Point p = (Point) obj;
			return Reals.equals(x, p.getX()) && Reals.equals(y, p.getY());
		} catch (Exception e) {
			return false;
		}
	}
	
		
	@Override
	public String toString() {
		return "("+x+","+y+")";
	}
	
	public Point clone() {
		return new Point(x, y);
	}
}




