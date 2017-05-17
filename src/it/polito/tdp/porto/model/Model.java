package it.polito.tdp.porto.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.porto.db.PortoDAO;

public class Model {
	
	private List <Author> autori;
	private PortoDAO dao;
	private AuthorIdMap authorIdMap;
	private UndirectedGraph<Author,DefaultEdge> graph;
	
	public Model(){
		dao = new PortoDAO();
		authorIdMap = new AuthorIdMap();
	}
	
	public List <Author> getAllAutori(){
		if(this.autori==null) {
			this.autori = dao.getAllAutori(authorIdMap) ;
		}
		return this.autori ;
		
	}

	public List<Author> getCoautori(Author autore) {
		
		UndirectedGraph<Author, DefaultEdge> g = this.getGrafo() ;

		List<Author> list = new ArrayList<>() ;

		for(Author a : Graphs.neighborListOf(g, autore))
			list.add(a);
		
		return list ;
	}

	private UndirectedGraph<Author, DefaultEdge> getGrafo() {
		if(this.graph==null) {
			this.creaGrafo();
		}
		return this.graph ;
	}

	private void creaGrafo() {
		
		this.graph = new SimpleGraph<>(DefaultEdge.class) ;
		
		Graphs.addAllVertices(graph, this.getAllAutori());
		
		for(AuthorPair coaut : dao.listCoautori(authorIdMap)) {
			graph.addEdge(coaut.getA1(), coaut.getA2()) ;
		}
		
		//System.out.println(graph);
	}

	public Collection<Author> getNonCoautori(Author autore) {
		
		List<Author> coautori = this.getCoautori(autore);
		List <Author> ris = new ArrayList<Author>();
		
		autori.removeAll(coautori);
		
		for(Author a : this.getAllAutori())
			if(!coautori.contains(a) && !a.equals(autore))
				ris.add(a);
		
		return ris;
	}

	public List<Paper> trovaSequenza(Author primo, Author secondo) {
		
		List <Paper> ris = new ArrayList <Paper>();
		
		DijkstraShortestPath<Author,DefaultEdge> path = new DijkstraShortestPath<Author,DefaultEdge>(graph,primo,secondo);
		
		List<Paper> intersezione;
		
		for(DefaultEdge e : path.getPathEdgeList()){
			intersezione = new ArrayList <Paper>(graph.getEdgeSource(e).getPapers());
			intersezione.retainAll(graph.getEdgeTarget(e).getPapers());
			ris.add(intersezione.get(0));
		}
		return ris;
	}

}
