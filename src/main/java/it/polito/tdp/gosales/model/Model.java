package it.polito.tdp.gosales.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.gosales.dao.GOsalesDAO;

public class Model {
	
	GOsalesDAO dao = new GOsalesDAO();
	Graph<Retailers,DefaultWeightedEdge> grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
	
	
	public void creaGrafo(String nazione,int anno, int M) {
		
		List<Retailers> allVertex = dao.getAllVertex(nazione);
		
		Graphs.addAllVertices(grafo, allVertex);
		
		
		
		for(Retailers r1 : grafo.vertexSet()) {
			for(Retailers r2 : grafo.vertexSet()) {
				
				if(!r1.equals(r2) && r1.getCode()>r2.getCode()) {
					
					if(dao.getEdge(r1.getCode(), r2.getCode(), anno, M) != 0) {
						int peso = dao.getEdge(r1.getCode(), r2.getCode(), anno, M);
						Graphs.addEdge(grafo, r1, r2, peso);
					}
				}
			}
		}
		
		
	}
	
	public int nVertici() {
		return grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return grafo.edgeSet().size();
	}
	
	public List<Arco> getListaArchi(){
		
		List<Arco> lista = new LinkedList<>();
		
		for(DefaultWeightedEdge e : grafo.edgeSet()) {
			lista.add( new Arco( (int)grafo.getEdgeWeight(e), grafo.getEdgeSource(e), grafo.getEdgeTarget(e) )  );
		}
			return lista;
	}
	
	public int nComponentiConnesse (Retailers r) {
		
		ConnectivityInspector<Retailers, DefaultWeightedEdge> inspector =
				new ConnectivityInspector<Retailers, DefaultWeightedEdge>(this.grafo);
		Set<Retailers> connessi = inspector.connectedSetOf(r);
		return connessi.size();
	}
	
    public int totalePesoArchi (Retailers r) {
		
		 ConnectivityInspector<Retailers, DefaultWeightedEdge> inspector =
				new ConnectivityInspector<Retailers, DefaultWeightedEdge>(this.grafo);
		 Set<Retailers> connessi = inspector.connectedSetOf(r);
		 
		 int peso = 0;
		
		 for(DefaultWeightedEdge e : grafo.edgeSet()) {
			 if(connessi.contains( grafo.getEdgeSource(e)) && connessi.contains(grafo.getEdgeTarget(e))) {
				 peso+= grafo.getEdgeWeight(e);
			 }
		 }
		 return peso;
	}

	
}
