package it.polito.tdp.porto.model;

public class AuthorPair {
	
	private Author a1;
	private Author a2;
	
	public AuthorPair(Author a1, Author a2) {
		super();
		this.a1 = a1;
		this.a2 = a2;
	}

	public Author getA1() {
		return a1;
	}

	public void setA1(Author a1) {
		this.a1 = a1;
	}

	public Author getA2() {
		return a2;
	}

	public void setA2(Author a2) {
		this.a2 = a2;
	}
	
	

}
