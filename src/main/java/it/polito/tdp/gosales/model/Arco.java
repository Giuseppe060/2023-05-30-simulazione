package it.polito.tdp.gosales.model;

public class Arco implements Comparable<Arco>{
	
	int peso;
	Retailers r1;
	Retailers r2;
	
	
	
	public int getPeso() {
		return peso;
	}

	public Retailers getR1() {
		return r1;
	}

	public Retailers getR2() {
		return r2;
	}



	public Arco(int peso, Retailers r1, Retailers r2) {
		super();
		this.peso = peso;
		this.r1 = r1;
		this.r2 = r2;
	}

	@Override
	public int compareTo(Arco o) {
		
		return this.peso-o.peso;
	}
	
	

}
