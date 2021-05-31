package fr.romain.Maths.K_VectorSpace.algebraicObjects;

/**
 * In this class, we represent a rational number by two integers:
 * <li>the numerator (symbolized by p)
 * <li>the denominator (symbolized by q)
 * 
 * This class automatically transform the rational number to its irreductible form at the 
 * construction of the object
 * @author romain
 *
 */
public class Rational {
	
	
	int p; //the numerator
	int q; //the denominator

	public final static Rational zero = new Rational(0, 1);
	public final static Rational one = new Rational(1, 1);
	

	/**
	 * TODO: simplify the expression
	 * @param p
	 * @param q
	 * @throws IllegalArgumentException
	 */
	public Rational(int p, int q) throws IllegalArgumentException{
		if (q==0) {
			throw new IllegalArgumentException("Division by zero error");
		}
		this.p = p;
		this.q = q;
	}
	
	public static Rational of(int a) {
		return new Rational(a, 1);
	}


	public int getP() {
		return p;
	}

	public int getQ() {
		return q;
	}

	public Rational plus(Rational r) {
		return new Rational(p*r.q+r.p*q, 
				            q*r.q);
	}
	
	public Rational prod(Rational r) {
		return new Rational(p*r.p, q*r.q);
	}
	
	public Rational inv() {
		return new Rational(q, p);
	}
	
	public Rational div(Rational r) {
		return prod(r.inv());
	}


	@Override
	public String toString() {
		return p+"/"+q;
	}

}
