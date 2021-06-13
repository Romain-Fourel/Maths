package fr.romain.Maths.linearAlgebra.algebraicObjects;

import fr.romain.Maths.linearAlgebra.Reals;

public class Complex {
	
	double real;
	double im;
	
	public static final Complex i = new Complex(0, 1);
	public static final Complex zero = new Complex(0, 0);
	public static final Complex one = new Complex(1, 0);

	public Complex(double real, double im) {
		this.real = real;
		this.im = im;
	}
	
	public static Complex byExpForm(double arg, double module) {
		return new Complex(module*Math.cos(arg), module*Math.sin(arg));
	}
	
	public static Complex byTrigForm(double cos, double sin, double module) throws IllegalArgumentException{
		if (cos< -1 || cos>1 || sin<-1 || sin>1) {
			throw new IllegalArgumentException("Cosinus and Sinus have to be between [-1;1]");
		}
		return new Complex(module*cos, module*sin);
	}
	
	public static Complex of(double real) {
		return new Complex(real, 0);
	}
	
	public double getReal() {
		return real;
	}

	public void setReal(double real) {
		this.real = real;
	}

	public double getIm() {
		return im;
	}

	public void setIm(double im) {
		this.im = im;
	}
	
	public Complex plus(Complex z) {
		return new Complex(real+z.getReal(), im+z.getIm());
	}
	
	public Complex prod(Complex z) {
		return new Complex(real*z.getReal()-im*z.getIm(), 
						   real*z.getIm()+im*z.getReal());
	}
	
	public Complex inv() throws IllegalArgumentException{
		if (equals(zero))
			throw new IllegalArgumentException("Division by zero error");
		
		double squareMod = module()*module();
		
		return new Complex(real/squareMod,-im/squareMod);
	}
	

	public Complex div(Complex z) {
		return prod(z.inv());
	}
	
	public double module() {
		return Reals.norm2(real, im);
	}
	
	public double cos() {
		return getReal()/module();
	}
	
	public double sin() {
		return getIm()/module();
	}
	
	public double arg() {
		return Math.acos(cos());
	}
	
	public Complex conjugate() {
		return new Complex(real, -im);
	}


	@Override
	public String toString() {
		if (equals(zero)){
			return "0";
		}
		if (equals(i)) {
			return "i";
		}
		if (im==0)
			return real+"";
		if (real==0)
			return im+"i";
		if (im<0)
			return real+" - i"+(-im); 
		
		return real+" + i"+im;
	}
	

	@Override
	public boolean equals(Object obj) {
		try {
			Complex c = (Complex) obj;
			return Reals.equals(real, c.getReal()) 
				&& Reals.equals(im, c.getIm());
		} catch (Exception e) {
			return false;
		}
	}

}



