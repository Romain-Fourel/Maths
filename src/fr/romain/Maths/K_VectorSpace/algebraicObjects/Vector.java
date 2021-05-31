package fr.romain.Maths.K_VectorSpace.algebraicObjects;

public class Vector<K> {
	
	private Object[] values;
	
	public Vector(int n) {
		values = new Object[n];
	}
	
	@SafeVarargs
	public Vector(K... values) {
		this.values = values;
	}
	
	@SuppressWarnings("unchecked")
	public K[][] getValues(){
		return (K[][]) values;
	}
	
	public K get(int i, int j) {
		return getValues()[i][j];
	}
	
	public void set(int i, K value) {
		values[i] = value;
	}
	
	public int dim() {
		return values.length;
	}

}
