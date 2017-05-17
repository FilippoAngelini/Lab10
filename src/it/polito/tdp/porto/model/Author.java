package it.polito.tdp.porto.model;

import java.util.HashSet;
import java.util.Set;

public class Author {

	private int id;
	private String lastname;
	private String firstname;
	private Set<Paper> papers;
		
	public Author(int id, String lastname, String firstname) {
		super();
		this.id = id;
		this.lastname = lastname;
		this.firstname = firstname;
		papers = new HashSet <Paper>();
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getLastname() {
		return lastname;
	}


	public void setLastname(String lastname) {
		this.lastname = lastname;
	}


	public String getFirstname() {
		return firstname;
	}


	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	@Override
	public String toString() {
		//return "Author [id=" + id + ", lastname=" + lastname + ", firstname=" + firstname + "]";
		return id + " " + lastname + " " + firstname;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Author other = (Author) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	public void addPaper(Paper paper){
		this.papers.add(paper);
	}

	public Set<Paper> getPapers() {
		return papers;
	}
	
	
}
