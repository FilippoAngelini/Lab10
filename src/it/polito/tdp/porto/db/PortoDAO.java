package it.polito.tdp.porto.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.AuthorIdMap;
import it.polito.tdp.porto.model.AuthorPair;
import it.polito.tdp.porto.model.Paper;

public class PortoDAO {

	/*
	 * Dato l'id ottengo l'autore.
	 */
	public Author getAutore(int id) {

		final String sql = "SELECT * FROM author where id=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, id);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {

				Author autore = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				return autore;
			}

			return null;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	/*
	 * Dato l'id ottengo l'articolo.
	 */
	public Paper getArticolo(int eprintid) {

		final String sql = "SELECT * FROM paper where eprintid=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, eprintid);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				Paper paper = new Paper(rs.getInt("eprintid"), rs.getString("title"), rs.getString("issn"),
						rs.getString("publication"), rs.getString("type"), rs.getString("types"));
				return paper;
			}

			return null;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	
	public List<Author> getAllAutori(AuthorIdMap authorIdMap){
		
		final String sql = "SELECT * FROM author ORDER BY lastname ASC";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();
			
			List<Author> list = new ArrayList<>() ;

			while (rs.next()) {
				Author autore = new Author(rs.getInt("id"),rs.getString("lastname"),rs.getString("firstname"));
				autore = authorIdMap.put(autore);
				list.add(autore) ;
			}

			return list;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	
	public List<AuthorPair> listCoautori (AuthorIdMap authorIdMap){
		final String sql = "select distinct c1.authorid as id1, a1.lastname as lastname1,a1.firstname as firstname1, c2.authorid as id2, a2.lastname as lastname2, a2.firstname as firstname2, paper.eprintid as eprintid, title, issn, publication, type, types from creator c1, creator c2, author a1, author a2, paper where paper.eprintid=c1.eprintid AND c1.eprintid=c2.eprintid AND c1.authorid!=c2.authorid and c1.authorid=a1.id and c2.authorid=a2.id order by c1.authorid asc";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
						
			ResultSet res = st.executeQuery() ;
			
			List<AuthorPair> list = new ArrayList<>() ;
			
			while(res.next()) {
				Author a1 = authorIdMap.get(res.getInt("id1")) ;
				if(a1==null) {
					a1 = new Author(res.getInt("id1"), res.getString("lastname1"), res.getString("firstname1")) ;
					a1 = authorIdMap.put(a1);
				}
				
				Author a2 = authorIdMap.get(res.getInt("id2")) ;
				if(a2==null) {
					a2 = new Author(res.getInt("id2"), res.getString("lastname2"), res.getString("firstname2")) ;
					a2 = authorIdMap.put(a2);
				}
				
				Paper paper = new Paper (res.getInt("eprintid"),res.getString("title"),res.getString("issn"),res.getString("publication"),res.getString("type"),res.getString("types"));
				
				a1.addPaper(paper);
				a2.addPaper(paper);

				list.add(new AuthorPair(a1, a2)) ;
			}
			
			res.close();
			conn.close();

			return list ;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
}