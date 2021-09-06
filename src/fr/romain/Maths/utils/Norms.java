package fr.romain.Maths.utils;

import java.util.function.Function;

public class Norms {

	@SafeVarargs
	public static<K> Double norm1(Function<K, Double> abs,K... values) {
		double norm = 0;
		for (K k : values) {
			norm+=abs.apply(k);
		}
		return norm;
	}
	
	@SafeVarargs
	public static<K> Double normInf(Function<K, Double> abs,K... values) {
		double norm = abs.apply(values[0]);
		for (K k : values) {
			norm = Math.max(norm, abs.apply(k));
		}
		return norm;
	}
	
	@SafeVarargs
	public static<K> Double norm2(Function<K, Double> abs,K... values) {
		double norm = 0;
		for (K k : values) {
			norm += abs.apply(k)*abs.apply(k);
		}
		return Math.sqrt(norm);
	}
	

}
