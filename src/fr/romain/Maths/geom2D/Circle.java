package fr.romain.Maths.geom2D;

import fr.romain.Maths.gui.Board;

public class Circle implements Drawable {
	
	Point center;
	Point border;

	public Circle(Point center,Point border) {
		this.center = center;
		this.border = border;
	}
	
	public Circle(Point center,double radius) {
		this.center = center;
		border = center;
		
	}
	
	public double getRadius() {
		return center.distanceTo(border);
	}
	
	@Override
	public void drawOn(Board b) {
		b.drawCircle(center, getRadius());	
	}

	
	public double distanceTo(Point p) {
		return Math.abs(center.distanceTo(p)-getRadius());
	}
	
	public boolean hasInside(Point p) {
		return center.distanceTo(p)<getRadius();
	}

}
