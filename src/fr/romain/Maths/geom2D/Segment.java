package fr.romain.Maths.geom2D;

import fr.romain.Maths.utils.Reals;

public class Segment extends Line {

	public Segment(Point p1, Point p2) {
		super(p1, p2);
	}
	
	public Line associatedLine() {
		return new Line(p1, p2);
	}
	
	@Override
	public boolean contains(Point p) {
		if(associatedLine().contains(p)) {
			if(Reals.isNull(getB())) {
				return ( p1.y<=p2.y && p.y >= p1.y && p.y<=p2.y) || (p1.y>=p2.y && p.y<=p1.y && p.y>=p2.y);
			}
			return ( p1.x<=p2.x && p.x >= p1.x && p.x<=p2.x) || (p1.x>=p2.x && p.x<=p1.x && p.x>=p2.x);
		}
		return false;
	}
	
	@Override
	public double distanceTo(Point p) {
		Point proj = p.projected(associatedLine());
		
		if(p1.x>p2.y) {
			Point p3 = p1.clone();
			p1 = p2.clone();
			p2 = p3;
		}
		if(contains(proj)) {
			return p.distanceTo(proj);
		}
		return Math.min(p.distanceTo(p1), p.distanceTo(p2));
	}
	
	@Override
	public boolean equals(Object obj) {
		try {
			Segment s = (Segment) obj;
			return p1.equals(s.getP1()) && p2.equals(s.getP2());
		} catch (Exception e) {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return "P1:"+p1.toString()+" ; P2: "+p2.toString();
	}
	
}
