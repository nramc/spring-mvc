package com.network.algorithm;

import java.util.ArrayList;
import java.util.List;
import com.network.entity.Edge;
import com.network.entity.Node;

public class BellmanFordAlgorithm {
	
	private static void initialize(List<Node> listNode) {
		for (Node node : listNode) {
			node.setDistFromSrc( node.isSrcNode() ? 0 : Integer.MAX_VALUE );
		}
	}
	private static Node getNode(List<Node> listNode, String nodeID) {
		Node n = null;
		for (Node node : listNode) {
			if ( node.getId().equalsIgnoreCase(nodeID) ) {
				n = node;
				break;
			}
		}
		return n;
	}
	private static void displayResult(List<Node> listNode) {
		System.out.println( "\tSource\tPrev Node\tDistance" );
		System.out.println( "----------------------------------------------------" );
		for (Node node : listNode) {
			System.out.println( node );
		}
	}
	
	public static void perform(List<Node> listNode, List<Edge> listEdge) {

    // Step 1: Initialize distances from src to all other vertices as INFINITE
    initialize(listNode);

    // Step 2: Relax all edges |V| - 1 times.
    // A simple shortest path from src to any other vertex can have at-most |V| - 1 edges
    for (int idx = 1; idx < listNode.size(); idx++) {
    	for (Edge edge : listEdge) {
				Node u = getNode(listNode, edge.getFrom());
				Node v = getNode(listNode, edge.getTo());
				if ( u.getDistFromSrc() != Integer.MAX_VALUE
						&& u.getDistFromSrc()+edge.getDist() < v.getDistFromSrc() ) {
        	v.setDistFromSrc( u.getDistFromSrc() + edge.getDist() );
        	v.setPrev(u);
        	v.setPrevEdge(edge);
        }
			}
    }

    // Step 3: check for negative-weight cycles.
    // The above step guarantees shortest distances if graph doesn't contain negative weight cycle.
    // If we get a shorter path, then there is a cycle.
    for (Edge edge : listEdge) {
    	Node u = getNode(listNode, edge.getFrom());
			Node v = getNode(listNode, edge.getTo());
		  if (u.getDistFromSrc() != Integer.MAX_VALUE && u.getDistFromSrc()+edge.getDist() < v.getDistFromSrc() )
        System.out.println("Graph contains negative weight cycle");
		}
    
    displayResult(listNode);
	}
	
	public static void main(String[] args) {
		List<Node> listNode = new ArrayList<Node>();
		List<Edge> listEdge = new ArrayList<Edge>();
		Node node = new Node("0");
		node.setSrcFlg(true);
		listNode.add(node);
		
		listNode.add(new Node("1"));
		listNode.add(new Node("2"));
		listNode.add(new Node("3"));
		listNode.add(new Node("4"));
		
		listEdge.add(new Edge("0", "1", -1));
		listEdge.add(new Edge("0", "2", 4));
		listEdge.add(new Edge("1", "2", 3));
		listEdge.add(new Edge("1", "3", 2));
		listEdge.add(new Edge("1", "4", 2));
		listEdge.add(new Edge("3", "2", 5));
		listEdge.add(new Edge("3", "1", 1));
		listEdge.add(new Edge("4", "3", -3));
		
		BellmanFordAlgorithm.perform(listNode, listEdge);
	}

}
